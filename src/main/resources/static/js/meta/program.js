// 제공사, 프로그램명 변수 선언
var selectTarget = $('#corporatorCategory, #programCategory');
var nation_data;
$.ajax({
    url: "/json/nation_code.json",
    type: "GET",
    success : function (result) {
        nation_data = result;
        $('#editorNation').select2({data:nation_data});
    }
});

$('#selectProgramSearchType').on('change',function () {
    if($(this).val() == '0'){
        $('#corporatorCategory, #programCategory').prop('disabled',false);
        $('#searchInput').prop('disabled', true).val('');
    }else{
        $('#searchInput').prop('disabled', false).val('');
        $('#corporatorCategory, #programCategory').prop('disabled',true);
        selectTarget.val('');
        selectTarget.each(function(idx){
            $(this).parent().find(".select2-selection__rendered").empty().append($(this).find('option:selected').text());
        });
    }
});

// 제공사, 프로그램 select2 적용
$('#selectProgramSearchType').select2({
    'placeholder': '검색 타입 선택',
    'minimumResultsForSearch': Infinity
});

if($('#type').val() == ''){
    $('#corporatorCategory, #programCategory').prop('disabled',false);
    $('#searchInput').prop('disabled', true);
}else{
    $('#selectProgramSearchType').val('1').parent().find(".select2-selection__rendered").empty().append($('#selectProgramSearchType').find('option:selected').text());
    $('#searchInput').prop('disabled', false);
    $('#corporatorCategory, #programCategory').prop('disabled',true);
}

$('[name=selectCorporator], [name=selectProgram]').select2();
$('#editorNation').select2({
    tokenseparators: ',',
    placeholder: '국가 검색',
    allowClear : true,
    minimumResultsForSearch : true
});

// 프로그램명으로 검색할 경우
$('#searchInput').on('focus',function () {
    selectTarget.val('');
    selectTarget.each(function(idx){
        $(this).parent().find(".select2-selection__rendered").empty().append($(this).find('option:selected').text());
    });
    $('[name=selectType]').val(16);
});

// 방송사, 프로그램 선택을 할 경우
selectTarget.on('change', function () {
   $('#searchInput').val('');
   $('[name=selectType]').val('');
});

// 리스트 클릭시 -> 등록, 수정에 값 넣기
$('tr[name=list]').on('click',function () {

    if($(this).hasClass('customActive')){
        $('#btnProgramUpdate').prop('disabled',true);
        $('[name=programInput]').val('');
        $('#editorCorporator').val('').prop('disabled',false);
        $('#editorCorporator').parent().find(".select2-selection__rendered").empty().append($('#editorCorporator').find('option:selected').text());
        $('#editorNation').val('').trigger('change');
        $(this).removeClass('customActive');
    }else{
        $('tr[name=list]').removeClass('customActive');
        $('#btnProgramUpdate').prop('disabled',false);
        $('#editorProgramId').val($(this).find('[name=programId]').text());
        $('#editorProgramName').val($(this).find('[name=programTitle]').text());
        $('#editorCorporator').val($(this).find('[name=programCorporator]').text()).prop('disabled',true);
        $('#editorCorporator').parent().find(".select2-selection__rendered").empty().append($('#editorCorporator').find('option:selected').text());
        $('#editorProgramNameEn').val($(this).find('[name=programTitleEn]').text());
        $('#editorNation').val($(this).find('[name=limitNation]').text());

        var nationValue = $(this).find('[name=limitNation]').text().split(',');
        $('#editorNation').val(nationValue).trigger('change');

        $(this).addClass('customActive');
    }
});

// 프로그램 추가
$("#btnProgramCreate").on('click',function(){
    if($("#programForm").parsley().validate()){

        var nationValue = "";
        nationValue += $("#editorNation").val();

        var param = {};
        param.corporator     = $('#editorCorporator').val();
        param.programTitle   = $("#editorProgramName").val().trim();
        param.programTitleEn = $("#editorProgramNameEn").val().trim();
        param.limitnation = nationValue;

        console.log(JSON.stringify(param));

        var options = {
            "url"         : "/meta/program",
            "type"        : "POST",
            "data"        : JSON.stringify(param),
            "contentType" : "application/json; charset=utf-8",
            "dataType"    : "json",
            "callback" : function acallback(result){
                location.reload();
            }
        };

        if(confirm("추가하시겠습니까?"))
            cms.ajaxCall(options);
    }
    return false;
});

// 프로그램 수정
$("#btnProgramUpdate").on('click',function(){
    if($("#programForm").parsley().validate()){

        var nationValue = "";
        nationValue += $("#editorNation").val();

        var param = {};
        param.programId      = $('#editorProgramId').val();
        param.corporator     = $('#editorCorporator').val();
        param.programTitle   = $("#editorProgramName").val().trim();
        param.programTitleEn = $("#editorProgramNameEn").val().trim();
        param.limitnation = nationValue;

        var formData = new FormData();
        formData.append('body', JSON.stringify(param));

        var options = {
            "url"         : "/meta/program/"+ param.programId,
            "type"        : "PUT",
            "data": formData,
            "processData": false,
            "contentType": false,
            "callback" : function acallback(result){
                window.location.href = decodeURIComponent(document.location.href);
            }
        };
        if(confirm("수정하시겠습니까?"))
            cms.ajaxCall(options);
    }
    return false;
});

// 프로그램 삭제
$("[name=btnDelete]").on('click',function(){
    var programId = $(this).parent().parent().find("[name=programId]").text();
    var options = {
        "url"         : "/meta/program/"+ programId,
        "type"        : "DELETE",
        "callback" : function acallback(result){
            window.location.href = decodeURIComponent(document.location.href);
        }
    };
    var result = confirm('삭제하시겠습니까?');
    if(result){
        cms.ajaxCall(options);
    }
    return false;
});