$.fn.deleteTableInfo = function () {
    $(this).on('click',function () {
        $(this).parent().parent().remove();
        $('#resultMubeatDjClip tr').each(function (idx) {
            $(this).find('td').eq(0).text(idx+1);
        });
    });
};

// 홈 뮤빗 Dj 클립 정보 삭제 버튼 클릭 시
$('a[name=btnDelete]').deleteTableInfo();

// 저장 클릭 시
$('#btnSave').on('click', function () {
    var array = [];

    $('#resultMubeatDjClip tr').each(function () {
        var object = {};
        object['recommendHomeId'] = $(this).find('td').eq(0).text();
        object['clipMetaId'] = $(this).find('td').eq(1).text();
        object['clipTitle'] = $(this).find('td').eq(2).text();
        object['enableYn'] = ($(this).find('td').eq(3).text() == '노출')? 'Y':'N';
        array.push(object);
    });

    if(array.length > 0){
        var options = {
            'url' : '/recommend/home',
            'type': 'PUT',
            'data': JSON.stringify(array),
            'contentType': 'application/json; charset=utf-8',
            'dataType': 'json',
            'callback': function acallback(result) {
                location.reload();
            }
        };
        if(confirm('등록하시겠습니까?'))
            cms.ajaxCall(options);
        else
            return false;
    }else {
        alert('등록할 클립정보가 없습니다.');
    }
});

// select2 적용
$('#selectSearchType').select2({
    'minimumResultsForSearch': Infinity
}).on('change',function () {
    if($('#selectSearchType').val() == 2)
        $('#searchInput').attr('type', 'number');
    else
        $('#searchInput').attr('type', 'text');
});

// 검색결과 내 등록 버튼 클릭시
$(document).on('click', 'a[name=btnInsert]', function () {

    var str = $(this).parent().parent().html();
    var targetId = $(this).parent().parent().find('td[name=clipMetaId]').text();
    var check = false;

    $('#resultMubeatDjClip tr').each(function (index) {
        if($(this).find('td[name=clipMetaId]').text() == targetId){
            alert('같은 클립이 존재합니다.');
            check = true;
        }else if(index >= 4){
            alert('최대 5개까지 등록 가능합니다.');
            check = true;
        }
    });

    if($(this).parent().parent().find('td').eq(3).text() === '미노출'){
        alert('노출된 클립만 등록 가능합니다.');
        check = true;
    }

    if(check === false){
        str = str.replace('<a name="btnInsert" class="btn btn-xs btn-info">등록</a>','<a name="btnDelete" class="btn btn-xs btn-danger">삭제</a>');
        $('#resultMubeatDjClip').append('<tr><td name="recommendId"></td>'+ str + '</tr>');
        $('#resultMubeatDjClip tr').each(function (idx) {
            $(this).find('td').eq(0).text(idx+1);
        });
        $('a[name=btnDelete]').deleteTableInfo();
    }else{
        return false;
    }

});

// 뮤빗 DJ 클립 등록 검색버튼 클릭시
$('#searchList').on('click', function () {
    if($('#selectSearchType').val() === ''){
        alert('검색 조건을 선택해 주세요.')
        $('#selectSearchType').focus();
    }else if($('#selectSearchType').val() !== '' && $('#searchInput').val() === ''){
        alert('검색할 클립명을 입력해주세요.');
        $('#searchInput').focus();
    }else if($('#selectSearchType').val() !== '' && $('#searchInput').val() !== ''){
        var type = $('#selectSearchType').val();
        var text = $('#searchInput').val();
        $('#type').val(type);
        $('#text').val(text);
        moveToPageResultSearch(0, type, text);
    }
});

// 뮤빗 DJ 클립 등록 검색 엔터 누를 경우
$('#searchInput').on('keypress', function (e) {
    if(e.which == 13){
        if($('#selectSearchType').val() === ''){
            alert('검색 조건을 선택해 주세요.')
            $('#selectSearchType').focus();
            return false;
        }else if($('#selectSearchType').val() !== '' && $('#searchInput').val() === ''){
            alert('검색할 클립명을 입력해주세요.');
            $('#searchInput').focus();
            return false;
        }else if($('#selectSearchType').val() !== '' && $('#searchInput').val() !== ''){
            var type = $('#selectSearchType').val();
            var text = $('#searchInput').val();
            $('#type').val(type);
            $('#text').val(text);
            moveToPageResultSearch(0, type, text);
        }else{
            return false;
        }
    }
});

function moveToPageResultSearch(pageNo, searchType, searchText) {
    var options = {
        'url'      : '/recommend/home/search?size=10&page=' + pageNo + '&type=' + searchType + '&searchText=' + searchText,
        'type'     : 'GET',
        'callback' : searchCallback
    };
    cms.ajaxCall(options);
}
function searchCallback(result){

    if(result.pageResult !== null && result.pageResult !== undefined){
        $("#tableSearchResult").empty();
        var str = '<thead class="th-text-center"><tr><th width="13%">고유번호</th><th width="35%">클립명</th><th width="35%">클립명<span class="red">(영문)</span></th><th width="10%">노출상태</th><th width="7%"></th></tr></thead>';

        for(var i = 0; i < result.pageResult.content.length; i++){
            str += '<tr class="text-center">';
            str += '<td name="clipMetaId"><a onclick="window.open(\'/meta/clip/detail/' + result.pageResult.content[i].clipMetaId +'\',\'_blank\',\'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes\')">'+ result.pageResult.content[i].clipMetaId +'</a></td>';
            str += '<td class="text-left table-list-td-fixed">'+ result.pageResult.content[i].title +'</td>';
            str += '<td class="text-left table-list-td-fixed">'+ result.pageResult.content[i].titleEn +'</td>';
            str += '<td>'+ ((result.pageResult.content[i].enableYn == 'Y')? '<span class="label label-success">노출</span>': '<span class="label label-danger">미노출</span>')+'</td>';
            str += '<td class="table-home-td-delete"><a name="btnInsert" class="btn btn-xs btn-info">등록</a></td>';
            str += '</tr>';
        }

        $("#tableSearchResult").append(str);

        var blockSize = 10;
        var firstBlock = parseInt((result.pageResult.number / blockSize) / blockSize);
        var lastBlock = result.pageResult.totalPages - 1;
        var curBlock = parseInt(result.pageResult.number / blockSize);
        var start = parseInt(curBlock * blockSize);
        var end = start + blockSize - 1;
        var isFirst = false;
        var isLast = false;
        var type = $('#type').val();
        var text = $('#text').val();

        if(curBlock == 0)
            isFirst = true;

        if(result.pageResult.totalPages <= end + 1) {
            isLast = true;
            end = result.pageResult.totalPages - 1;
        }

        var pageStr = "";
        if(isFirst === false) {
            pageStr += "<li class=\"pagination_button previous\"><a href=\"javascript:moveToPageResultSearch("+ (start - 1) +","+ type + ",'" + text+"')\"><i class=\"fa fa-chevron-left\"></i></a></li>";
            pageStr +=  "<li class=\"pagination_button previous\"><a href=\"javascript:moveToPageResultSearch("+ (0) +","+ type + ",'" + text+"')\">1</a></li>";
            pageStr += "<li><span class='paginationEllipsis'>...</span></li>";
        }

        for(var i = start; i <= end; i++) {
            if(result.pageResult.number === i) {
                pageStr += "<li class=\"pagination_button active\">";
            } else {
                pageStr += "<li class=\"pagination_button\">";
            }

            pageStr += "<a href=\"javascript:moveToPageResultSearch(" + i +","+ type + ",'" + text + "')\">" + (i + 1) + "</a> </li>";
        }

        if(isLast === false) {
            pageStr +=  "<li><span class='paginationEllipsis'>...</span></li>";
            pageStr +=  "<li class=\"pagination_button next\"> <a href=\"javascript:moveToPageResultSearch(" + lastBlock +","+ type + ",'" + text + "')\">"+ (lastBlock + 1) +"</a></li>";
            pageStr +=  "<li class=\"pagination_button next\"> <a href=\"javascript:moveToPageResultSearch(" + (end + 1) +","+ type + ",'" + text + "')\"><i class=\"fa fa-chevron-right\"></i></a> </li>";
        }

        $("#ajaxPaging").empty().append(pageStr);
    }

}
