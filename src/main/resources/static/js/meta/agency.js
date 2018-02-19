function moveToPageForAgency(pageNo) {
    var options = {
        "url"      : "/meta/agency?page=" + pageNo,
        "type"     : "GET",
        "callback" : agencyCallback
    };
    cms.ajaxCall(options);
}

function agencyCallback(result){

    $("#tableResult").empty();
    for(var i = 0; i < result.data.content.length; i++){
        var str = "<tr name='agencyTr'>";
        str += "<td class='text-center' name='agencyId'>"+result.data.content[i].agencyId+"</td>";
        str += "<td class='text-center' name='agencyName'>"+result.data.content[i].agencyName+"</td>";
        str += "<td class='text-center' name='agencyNameEn'>";
        if(result.data.content[i].agencyNameEn != null)
            str += result.data.content[i].agencyNameEn;
        str += "<a type='button' class='btn btn-xs btn-danger btnDelete pull-right' style='padding: 0px 3px;;'><i class='ace-icon fa fa-times'></i></a></td>";
        str += "</tr>";

        $("#tableResult").append(str);
    }


    var blockSize = 5;
    var firstBlock = parseInt((result.data.number / blockSize) / blockSize);
    var lastBlock = result.data.totalPages - 1;
    var curBlock = parseInt(result.data.number / blockSize);
    var start = parseInt(curBlock * blockSize);
    var end = start + blockSize - 1;
    var isFirst = false;
    var isLast = false;

    if(curBlock == 0)
        isFirst = true;

    if(result.data.totalPages <= end + 1) {
        isLast = true;
        end = result.data.totalPages - 1;
    }

    var pageStr = "";
    if(isFirst === false) {
        pageStr += "<li class=\"pagination_button previous\"><a href=\"javascript:moveToPageForAgency("+ (start - 1) +")\"><i class=\"fa fa-chevron-left\"></i></a></li>";
        pageStr +=  "<li class=\"pagination_button previous\"><a href=\"javascript:moveToPageForAgency(0)\">1</a></li>";
        pageStr += "<li><span class='paginationEllipsis'>...</span></li>";
    }

    for(var i = start; i <= end; i++) {
        if(result.data.number === i) {
            pageStr += "<li class=\"pagination_button active\">";
        } else {
            pageStr += "<li class=\"pagination_button\">";
        }

        pageStr += "<a href=\"javascript:moveToPageForAgency(" + i + ")\">" + (i + 1) + "</a> </li>";
    }

    if(isLast === false) {
        pageStr +=  "<li><span class='paginationEllipsis'>...</span></li>";
        pageStr +=  "<li class=\"pagination_button next\"> <a href=\"javascript:moveToPageForAgency(" + lastBlock + ")\">"+ (lastBlock + 1) +"</a></li>";
        pageStr +=  "<li class=\"pagination_button next\"> <a href=\"javascript:moveToPageForAgency(" + (end + 1) + ")\"><i class=\"fa fa-chevron-right\"></i></a> </li>";
    }

    $("#ajaxPaging").html(pageStr);
    $.fn.agencyEdit();
    $.fn.deleteAgency();
}

$.fn.agencyEdit = function () {
    $('tr[name=agencyTr]').on('click',function () {

        if($(this).hasClass('customActive')){
            $('#btnAgencyCreate').show();
            $('#btnAgencyUpdate').hide();
            $('[name=agencyInput]').val('');
            $(this).removeClass('customActive');
        }else{
            $('tr[name=agencyTr]').removeClass('customActive');
            $('#btnAgencyCreate').hide();
            $('#btnAgencyUpdate').show();
            $('#editorAgencyId').val($(this).find('[name=agencyId]').text());
            $('#editorAgencyName').val($(this).find('[name=agencyName]').text());
            $('#editorAgencyNameEn').val($(this).find('[name=agencyNameEn]').text());

            $(this).addClass('customActive');
        }
    });
};

// 소속사 삭제
$.fn.deleteAgency = function(){
    $(".btnDelete").unbind().bind().on('click',function(){
        var agencyId = $(this).parent().parent().find("[name=agencyId]").text();
        console.log(agencyId);
        var options = {
            "url"         : "/meta/agency/"+agencyId,
            "type"        : "DELETE",
            "data"        : agencyId,
            "callback" : function acallback(result){
                $('#btnAgencyCreate').show();
                $('#btnAgencyUpdate').hide();
                $('[name=agencyInput]').val('');
                $("#tableResult").getAgency();
            }
        };
        var result = confirm('삭제하시겠습니까?');
        if(result){
            cms.ajaxCall(options);
        }
        return false;
    });
};

// 소속사 리스트
$.fn.getAgency = function(){
    moveToPageForAgency(0);
};

// 소속사 팝업
$("#btnAagency").on('click',function(){
    $("#divModal").modal();
    $(this).getAgency(0);
});

$('#modalAgencySearch').on('keyup', function (e) {

    $("#tableResult").empty();
    $('#btnAgencyCreate').show();
    $('#btnAgencyUpdate').hide();
    $(".pagination_button").removeClass('active');

    if($(this).val().toLowerCase().length > 0) {
        var options = {
            "url": "/meta/agency/" + $(this).val().toLowerCase(),
            "callback": function resultCallback(result) {
                $("#tableResult").empty();
                if(result.data != undefined && result.data.length != 0){
                    for(var i = 0; i < result.data.length; i++){

                         var str="";
                         str += "<tr name='agencyTr'>";
                         str += "<td class='text-center' name='agencyId'>"+result.data[i].agencyId+"</td>";
                         str += "<td class='text-center' name='agencyName'>"+result.data[i].agencyName+"</td>";
                         str += "<td class='text-center' name='agencyNameEn'>";
                         if(result.data[i].agencyNameEn != null)
                            str += result.data[i].agencyNameEn;
                         str +="<a type='button' class='btn btn-xs btn-danger btnDelete pull-right' style='padding: 0px 3px;;' href='#'><i class='ace-icon fa fa-times'></i></a></td></tr>";

                         $("#tableResult").append(str);
                    }
                    $.fn.agencyEdit();
                    $.fn.deleteAgency();
                }
            }
        };

        cms.ajaxSearch(options);
    }else if($(this).val().toLowerCase().length == 0){
        $("#tableResult").getAgency();
    }
});

// 소속사 추가
$("#btnAgencyCreate").on('click',function(){
    if($("#agencyForm").parsley().validate()){
        var param = {};
        param.agencyName   = $("#editorAgencyName").val().trim();
        param.agencyNameEn = $("#editorAgencyNameEn").val().trim();
        var options = {
            "url"         : "/meta/agency",
            "type"        : "POST",
            "data"        : JSON.stringify(param),
            "contentType" : "application/json; charset=utf-8",
            "dataType"    : "json",
            "callback" : function acallback(result){
                $('#btnAgencyCreate').show();
                $('#btnAgencyUpdate').hide();
                $('[name=agencyInput]').val('');
                $("#tableResult").getAgency();
            }
        };
        if(confirm("추가하시겠습니까?"))
            cms.ajaxCall(options);
    }
    return false;
});

// 소속사 수정
$("#btnAgencyUpdate").on('click',function(){
    if($("#agencyForm").parsley().validate()){

        var param = {};
        param.agencyId     = $('#editorAgencyId').val();
        param.agencyName   = $("#editorAgencyName").val().trim();
        param.agencyNameEn = $("#editorAgencyNameEn").val().trim();

        var options = {
            "url"         : "/meta/agency/"+ $('#editorAgencyId').val(),
            "type"        : "PUT",
            "data"        : JSON.stringify(param),
            "contentType" : "application/json; charset=utf-8",
            "dataType"    : "json",
            "callback" : function acallback(result){
                $('#btnAgencyCreate').show();
                $('#btnAgencyUpdate').hide();
                $('[name=agencyInput]').val('');
                $("#tableResult").getAgency();
            }
        };
        if(confirm("수정하시겠습니까?"))
            cms.ajaxCall(options);
    }
    return false;
});


