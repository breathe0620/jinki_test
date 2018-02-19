if(document.location.href.indexOf('?') > -1){
    var originUrl = decodeURIComponent(document.location.href);
    var decodeUrl = originUrl.split("?");
    var searchUrl = decodeUrl[1].split('&');
    var array = [];
    var urlCheck = true;

    for(var i = 0; i < searchUrl.length; i++){
        array[i] = searchUrl[i].split('=');
    }

    for(var i = 0; i < array.length-1; i ++) {

        if(array[i][1] != $('#'+array[i][0]+'').val()){
            if(array[i][0] == 'start' || array[i][0] == 'end')
                array[i][1] = array[i][1].replace( /\+/gi, " ");

            urlCheck = false;
        }
    }
    if(urlCheck == false){
        for(var i = 0; i < array.length-1; i ++) {
            if(array[i][0] == 'start' || array[i][0] == 'end')
                $('#'+array[i][0]+'').val(moment(new Date(array[i][1])).format('YYYY-MM-DD'));
            else
                $('#'+array[i][0]+'').val(array[i][1]);

        }
    }
}
$('select[name=selectType]').select2({
    "minimumResultsForSearch": Infinity
}).on('change',function () {
    $('#searchInput').prop('disabled', false);
    if($('select[name=selectType]').val() == 1 || $('select[name=selectType]').val() == 2)
        $('#searchInput').attr('type', "number");
    else
        $('#searchInput').attr('type', "text");
});

$('select[name=selectCorporator], select[name=selectProgram]').select2();
$('select[name=selectStateCategory], select[name=selectCategory]').select2({
    "minimumResultsForSearch": Infinity
});

// 새로고침
$("#pageRefresh").on('click',function(){

    $('#searchText, #start, #end, #type, select').val("");
    $("select option:eq(0)").attr("selected", "selected");

    $("select").each(function(idx){
        $(this).parent().find(".select2-selection__rendered").empty().append($(this).find('option:selected').text());
    });

    $(":input[type=text], :input[type=number]").each(function(idx){
        $(this).val("");
    });

    $('#searchInput').prop("disabled", true);
});

// 검색 버튼 클릭시
$("#searchList").on("click",function() {
    searchFilter();
    goToPage(0);
});

// 검색 엔터 누를시
$('#searchInput').on('keypress',function (e) {
   if(e.which == 13){
       searchFilter();
       goToPage(0);
   }
});

// Excel 버튼 클릭시
$('#btnExcel').on('click',function () {
    var $form = $('<form></form>');
    $form.attr('action','/meta/clip/excel');
    $form.attr('method','GET');
    $('.formExcel').empty().append($form);

    var str = '';
        str +='<input type="hidden" name="searchText" value="'+$('#searchText').val()+'"/>';
        str +='<input type="hidden" name="type"       value="'+$('#type').val()+'"/>';
        str +='<input type="hidden" name="corporator" value="'+$('#corporator').val()+'"/>';
        str +='<input type="hidden" name="program"    value="'+$('#program').val()+'"/>';
        str +='<input type="hidden" name="clipType"   value="'+$('#clipType').val()+'"/>';
        str +='<input type="hidden" name="enableState"   value="'+$('#enableState').val()+'"/>';
        str +='<input type="hidden" name="clipClassify"   value="'+$('#clipClassify').val()+'"/>';

    $form.append(str);
    $form.submit();
});

if($('#corporator').val() != ""  && $('#corporator').val() != null){
    $('#corporatorCategory, #corporator').val(getParameterByName("corporator"));
    $('#corporatorCategory').parent().find(".select2-selection__rendered").empty().append($('#corporatorCategory').find('option:selected').text());
    $('#corporatorCategory').programListView();
}else{
    $('#corporatorCategory option:eq(0)').attr("selected", "selected");
}

if($('#clipType').val() != "" ){
    $('#clipCategory').val($('#clipType').val());
    $('#clipCategory').parent().find(".select2-selection__rendered").empty().append($('#clipCategory').find('option:selected').text());
}else{
    $('#clipCategory option:eq(0)').attr("selected", "selected");
}

if($('#uploadState').val() != "" ){
    $('#uploadCategory').val($('#uploadState').val());
    $('#uploadCategory').parent().find(".select2-selection__rendered").empty().append($('#uploadCategory').find('option:selected').text());
}else{
    $('#uploadCategory option:eq(0)').attr("selected", "selected");
}

if($('#transcodeState').val() != "" ){
    $('#transcodeCategory').val($('#transcodeState').val());
    $('#transcodeCategory').parent().find(".select2-selection__rendered").empty().append($('#transcodeCategory').find('option:selected').text());
}else{
    $('#transcodeCategory option:eq(0)').attr("selected", "selected");
}

if($('#enableState').val() != "" ){
    $('#enableCategory').val($('#enableState').val());
    $('#enableCategory').parent().find(".select2-selection__rendered").empty().append($('#enableCategory').find('option:selected').text());
}else{
    $('#enableCategory option:eq(0)').attr("selected", "selected");
}

if($('#clipClassify').val() != "" ){
    $('#clipClassifyCategory').val($('#clipClassify').val());
    $('#clipClassifyCategory').parent().find(".select2-selection__rendered").empty().append($('#clipClassifyCategory').find('option:selected').text());
}else{
    $('#clipClassifyCategory option:eq(0)').attr("selected", "selected");
}

if($('#type').val() != ""){

    $('select[name=selectType]').val($('#type').val());
    $('select[name=selectType]').parent().find(".select2-selection__rendered").empty().append($('#type').val());
}

if($('#searchText').val().length > 0){

    $('#searchInput, #searchText').val(getParameterByName('searchText'));
    $('#searchInput').prop('disabled', false);
}

if($('#type').val() != ''){
    $('select[name=selectType]').val($('#type').val());
    $('select[name=selectType]').parent().find('.select2-selection__rendered').empty().append($('select[name=selectType]').find('option:selected').text());
}

var now = new Date ();
var startDate = null;
var endDate = null;

if($('#start').val() != null && $('#start').val().length > 0) {
    startDate = $('#start').val();
    $("#searchDtStart").val(moment(startDate).format("YYYY-MM-DD"));
} else {
    startDate = new Date(now.getFullYear(), now.getMonth(), 1);
}

if($('#end').val() != null && $('#end').val().length > 0) {
    endDate = $('#end').val();
    $("#searchDtEnd").val(moment(endDate).format("YYYY-MM-DD"));
} else {
    endDate = new Date(now.getFullYear(), now.getMonth() + 1, 0);
}

$('#corporatorCategory').on('change',function () {

    $('#corporator').val($(this).find("option:selected").val());
    $('#program').val('');
    $(this).programListView();
});

$('#programCategory').on('change',function () {
    $('#program').val($('#programCategory').find("option:selected").text());
});

$('[name=selectType]').on('change',function () {

   if($(this).val() == ''){

       $("#searchInput").val('');
       $("#searchText").val('');
       $('#searchInput').prop('disabled',true);
   }
});

// 시작일
$('#searchDtStart').datepicker({
    format: "yyyy-mm-dd",
    language:"ko",
    orientation: "bottom auto",
    autoclose: true,
    endDate : new Date(),
    clearBtn : true
}).on('changeDate', function () {
    $('#searchDtEnd').datepicker('setStartDate',$(this).val());
});

// 종료일
$('#searchDtEnd').datepicker({
    format: "yyyy-mm-dd",
    language:"ko",
    orientation: "bottom auto",
    autoclose: true,
    endDate : new Date(),
    clearBtn : true
}).on('changeDate',function () {
    $('#searchDtStart').datepicker('setEndDate', $(this).val());
});

function searchFilter() {

    if($('[name=selectType]').val() != null ) {
        if ($('#searchInput').val() == "") {
            $('#type').val('');
            $('#searchText').val('');
        } else {
            $('#type').val($('[name=selectType]').val());
            $('#searchText').val($("#searchInput").val());
        }
    }

    if($('#searchDtStart') != null && $('#searchDtStart').length != 0 && $("#searchDtStart").val().length > 0)
        $('#start').val(moment($("#searchDtStart").val()).format("LLLL"));
    else
        $('#start').prop('disabled', true);

    if($('#searchDtEnd') != null && $('#searchDtEnd').length != 0 && $("#searchDtEnd").val().length > 0)
        $('#end').val(moment($("#searchDtEnd").val()).format("LLLL"));
    else
        $('#end').prop('disabled', true);

    if($('#corporatorCategory').val() != null)
        $('#corporator').val($('#corporatorCategory').find('option:selected').val());

    if($('#programCategory').val() != null)
        $('#program').val($('#programCategory').find('option:selected').val());

    if($('#clipCategory').val() != null)
        $('#clipType').val($('#clipCategory').find('option:selected').val());

    if($('#uploadCategory').val() != null)
        $('#uploadState').val($('#uploadCategory').find('option:selected').val());

    if($('#transcodeCategory').val() != null)
        $('#transcodeState').val($('#transcodeCategory').find('option:selected').val());

    if($('#enableCategory').val() != null)
        $('#enableState').val($('#enableCategory').find('option:selected').val());

    if($('#clipClassifyCategory').val() != null)
        $('#clipClassify').val($('#clipClassifyCategory').find('option:selected').val());
}