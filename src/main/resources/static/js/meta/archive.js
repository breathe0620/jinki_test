// var oEditors = []; // 개발되어 있는 소스에 맞추느라, 전역변수로 사용하였지만, 지역변수로 사용해도 전혀 무관 함.
// // Editor Setting
// nhn.husky.EZCreator.createInIFrame({
//     oAppRef: oEditors, // 전역변수 명과 동일해야 함.
//     elPlaceHolder: "naverEditor", // 에디터가 그려질 textarea ID 값과 동일 해야 함.
//     sSkinURI: "/vendor/smarteditor2-master/workspace/SmartEditor2Skin.html", // Editor HTML
//     fCreator: "createSEditor2", // SE2BasicCreator.js 메소드명이니 변경 금지 X
//     htParams: { // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
//         bUseToolbar: true, // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
//         bUseVerticalResizer: true, // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
//         bUseModeChanger: true
//     }
// });
//
// $('#test').on('click',function () {
//     console.log( oEditors.getById["naverEditor"].getIR());
// });

function xml(xml, video) {
    var options = {
        "type" : "GET",
        "url" : "/meta/archive/edit?xml="+xml+"&video="+video,
        "callback" : function xmlSetup(result) {
            $("#xml").val(result.xml);
            console.log(result);
        }
    };
    cms.ajaxCall(options);
}
function video(video) {
    window.open("/meta/archive/video?video=" + video,'_blank','width=720, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=no');
}

// select2 적용
$('select[name=editorSelect]').select2({"minimumResultsForSearch": Infinity});

// 방송일 Datepicker 적용
$('#broadDtPicker').datepicker({
    startView: "2",
    format: "yyyy-mm-dd",
    language: "ko",
    orientation: "bottom auto",
    autoclose: true
});

// 프로그램명 노출
$('#editorCorporator').on('change',function(){
    var corporatorsName = $(this).find("option:selected").text();
    var options = {
        'type'        : 'GET',
        'url'         : '/meta/programs/'+corporatorsName,
        "callback" : function(result){
            $('#editorProgram').empty().append('<option value="" selected>프로그램명</option>');
            for(var i = 0; i < result.data.length; i++){
                $('#editorProgram').append('<option value="'+result.data[i].programId+'">'+result.data[i].programTitle+'</option>');
            }
        }
    };
    cms.ajaxCall(options);
});

// 제공사 유형별 form 재배치
$('#editorProviderType').on('change',function(){
    var value = $('#editorProviderType').val();

    if(value == '01'){
        $('.divSelectAgency').hide();
        $('.divSelectCorporator').show();

        $('.divSelectAgency select').removeAttr('data-parsley-required');
        $('.divSelectCorporator select').attr('data-parsley-required','true');
    }else if(value == '02'){
        $('.divSelectAgency').show();
        $('.divSelectCorporator').hide();

        $('.divSelectAgency select').attr('data-parsley-required','true');
        $('.divSelectCorporator select').removeAttr('data-parsley-required');
    }else{
        $('.divSelectAgency').hide();
        $('.divSelectCorporator').hide();
    }
});

// datatable 옵션
var otable = $('#resultTable').DataTable({
    processing: true,
    responsive: true,
    dom: "frtp",
    lengthMenu: [10],
    buttons : ['excel'],
    language: {
        "sEmptyTable": "데이터가 없습니다",
        "sInfo": "_START_ - _END_ / _TOTAL_",
        "sInfoEmpty": "0 - 0 / 0",
        "sInfoFiltered": "(총 _MAX_ 개)",
        "sInfoPostFix": "",
        "sInfoThousands": ",",
        "sLengthMenu": "목록 : _MENU_",
        "sLoadingRecords": "읽는중...",
        "sProcessing": "처리중...",
        "sSearch": "검색 : ",
        "sZeroRecords": "검색 결과가 없습니다",
        "oPaginate": {
            "sFirst": "처음",
            "sLast": "마지막",
            "sNext": "다음",
            "sPrevious": "이전"
        },
        "oAria": {
            "sSortAscending": ": 오름차순 정렬",
            "sSortDescending": ": 내림차순 정렬"
        }
    }
});

// DataTable 클릭시 xml 표시
$('#resultTable tr[name=list]').on('click', function () {
    if(!$(this).hasClass('customActive')){

        $(this).parent().find('.customActive').removeClass('customActive');
        $(this).addClass('customActive');
        $('#editorProviderType').val('01').parent().find(".select2-selection__rendered").empty().append($('#editorProviderType').find('option:selected').text());
        $('#editorAgency').removeAttr('data-parsley-required');

        var xml = $(this).find('input[name=originXML]').val(),
            video = $(this).find('input[name=originVideo]').val();

        var options = {
            "type" : "GET",
            "url" : "/meta/archive/edit?xml="+xml+"&video="+video,
            "callback" : function xmlSetup(result) {
                $(".resultXML").val('');
                $(".resultXML").val(result.xml);
                $('#editorClipTitle').val(result.title);
                $('#editorBroadDate').val(moment(result.broadDate).format('YYYY-MM-DD'));
                $('#editorPlaytime').val(result.playtime);
                $('.divSelectCorporator').show();
            }
        };

        cms.ajaxCall(options);
    }else{
        $('.divSelectCorporator').hide();
        $('#editorAgency').attr('data-parsley-required','true');
        $(this).parent().find('.customActive').removeClass('customActive');
        $(".resultXML, #editorClipTitle, #editorBroadDate, #editorPlaytime").val('');
        $('#editorProviderType').val('').parent().find(".select2-selection__rendered").empty().append($('#editorProviderType').find('option:selected').text());
    }
});

// Archive, Agency 등록
$('#btnEditor').on('click',function(){

    var param = {};
    param.originXML      = $('input[name=originXML]').val();
    param.originVideo    = $('input[name=originVideo]').val();
    param.providerType   = $('#editorProviderType option:selected').val();
    param.clipCategory   = $('#editorClipCategory option:selected').val();
    param.playtime       = $('#editorPlaytime').val();
    param.broadDate      = ($("#editorBroadDate").val().length > 0 ? moment($("#editorBroadDate").val()).format('YYYYMMDD') : null);
    param.clipTitle      = $('#editorClipTitle').val().trim();
    param.clipTitleEn    = $('#editorClipTitleEn').val().trim();

    if(param.providerType == '01'){
        param.corporator     = $('#editorCorporator option:selected').val();
        param.programId      = $('#editorProgram option:selected').val();
    }else if(param.providerType == '02'){
        param.corporator    = $('#editorAgency option:selected').text();
        param.agencyId      = $('#editorAgency option:selected').val();
    }else{
        alert('제공사 유형을 선택해주시길 바랍니다.');
        return false;
    }

    var formData = new FormData();
    formData.append("video", $("#editorVideoUpload")[0].files[0]);
    formData.append("thumbImg",$("#editorImgUpload")[0].files[0]);
    formData.append("body", JSON.stringify(param));

    console.log(JSON.stringify(param));
    if($('#formArchive').parsley().validate()){
        var options = {
            "type": "PUT",
            "url": "/meta/archive/regist",
            "data": formData,
            "processData": false,
            "contentType": false,
            "callback": function () {
                window.location.href = decodeURIComponent(document.location.href);
            }
        };
        var isConfirm = confirm('등록하시겠습니까?');
        if (isConfirm) {
            cms.ajaxCall(options);
        }else{
            return false;
        }
    }
});