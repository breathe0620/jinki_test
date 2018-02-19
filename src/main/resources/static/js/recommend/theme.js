// 이미지 보기
cms.lightBox();

// 편집 버튼 클릭 시
$('#btnEdit').on('click', function () {
    $('.edit').show();
    $('.list').hide();
});

// 편집 모드 -> 닫기 클릭 시
$('#btnEditClose').on('click', function () {
    $('.edit').hide();
    $('.list').show();
});

// 편집 완료 클릭 시
$('#btnEditSave').on('click', function () {

    var array = [];

    $('#sortable tbody tr').each(function (idx) {
        var object = {};
        object['recommendThemeId'] = $(this).find('td').eq(0).text();
        object['position'] = $(this).find('td').eq(1).text();
        array.push(object);
    });

    var options = {
        "type": "PUT",
        "url": "/recommend/theme/position",
        "data": JSON.stringify(array),
        "contentType": "application/json; charset=utf-8",
        "dataType": "json",
        "callback": function () {
            location.reload();
        }
    };
    var isConfirm = confirm('편집 하시겠습니까?');

    if (isConfirm) {
        cms.ajaxCall(options);
    }
    return false;
});

// select2 적용
$('#selectSearchType').select2({
    "minimumResultsForSearch": Infinity
}).on('change', function () {
    if ($('#selectSearchType').val() == 2)
        $('#searchInput').attr('type', "number");
    else
        $('#searchInput').attr('type', "text");
});

// 뮤빗 DJ 클립 등록 검색버튼 클릭시
$('#searchList').on('click', function () {
    if ($('#selectSearchType').val() === '') {
        alert('검색 조건을 선택해 주세요.');
        $('#selectSearchType').focus();
    } else if ($('#selectSearchType').val() !== '' && $('#searchInput').val() === '') {
        alert('검색할 클립명을 입력해주세요.');
        $('#searchInput').focus();
    } else if ($('#selectSearchType').val() !== '' && $('#searchInput').val() !== '') {
        var type = $('#selectSearchType').val();
        var text = $('#searchInput').val();
        $('#type').val(type);
        $('#text').val(text);
        moveToPageResultSearch(0, type, text, 'searchItem');
    }
});

// 뮤빗 DJ 클립 등록 검색 엔터 누를 경우
$('#searchInput').on('keypress', function (e) {
    if (e.which == 13) {
        if ($('#selectSearchType').val() === '') {
            alert('검색 조건을 선택해 주세요.');
            $('#selectSearchType').focus();
            return false;
        } else if ($('#selectSearchType').val() !== '' && $('#searchInput').val() === '') {
            alert('검색할 클립명을 입력해주세요.');
            $('#searchInput').focus();
            return false;
        } else if ($('#selectSearchType').val() !== '' && $('#searchInput').val() !== '') {
            var type = $('#selectSearchType').val();
            var text = $('#searchInput').val();
            $('#type').val(type);
            $('#text').val(text);
            moveToPageResultSearch(0, type, text, 'searchItem');
        } else {
            return false;
        }
    }
});

// 테마 등록 버튼 클릭시 -> 값 비워주기
$('#createTheme').on('show.bs.modal', function (e) {
    $('input').each(function () {
        $(this).val('');
    });
    $('img').attr('src', '');
});

// 상세보기 이동
$("tr[name=list] td:not([name=themeId])").locationListView("[name=themeId]", "/recommend/theme/detail/");

//목록 클릭 시
$('#updateBtnList').on('click', function () {
    window.location.href = '/recommend/theme';
});

// 홈 뮤빗 Dj 클립 정보 삭제 버튼 클릭 시
$('a[name=btnDelete]').on('click', function () {
    $(this).parent().parent().remove();
});

// 테마 이미지 파일 변경 시
cms.ImgAcceptPreview("#themeImgUpload", "#themeImg", function () {
    $('#cropImageTarget').val('#themeImg');
    window.open('/meta/artist-image-crop', '', 'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes');
});

// 테마 이미지 파일 수정 시
$('#editThemeImgUpload').on('click', function () {
    if ($('#themeImg').attr('src') != null) {
        $('#cropImageTarget').val('#themeImg');
        window.open('/meta/artist-image-crop', '', 'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes');
    } else {
        alert('수정할 이미지가 없습니다.');
        return false;
    }
});

// 삭제 버튼 클릭 시
$('#updateBtnDelete').on('click', function () {
    var options = {
        "url": "/recommend/theme/" + $('#updateRecommendThemeId').val(),
        "type": "DELETE",
        "callback": function acallback() {
            window.location.href = "/recommend/theme";
        }
    };

    if (confirm('삭제하시겠습니까?'))
        cms.ajaxCall(options);
    return false;
});

// 클립 등록 리스트에서 삭제버튼 클릭 시
$(document).on('click', 'a[name=btnDelete]', function () {
    var options = {
        "type": "DELETE",
        "url": "/recommend/theme/item/" + $('#updateRecommendThemeId').val() + '/' + $(this).parent().parent().find('td').eq(0).text(),
        "callback": function (result) {
            moveToPageResultSearch(0,6, $('#updateRecommendThemeId').val(), null);
        }
    };
    if (confirm('삭제하시겠습니까?'))
        cms.ajaxCall(options);
    else
        return false;
});

// 클립 검색 후 등록버튼 클릭 시
$(document).on('click', 'a[name=btnInsert]', function () {

    if($(this).parent().parent().find('td').eq(2).text() !== '미노출'){
        var options = {
            "type": "POST",
            "url": "/recommend/theme/item/" + $('#updateRecommendThemeId').val() + '/' + $(this).parent().parent().find('td').eq(0).text(),
            "callback": function () {
                moveToPageResultSearch(0,6, $('#updateRecommendThemeId').val(), null);
            }
        };
        if (confirm('등록하시겠습니까?'))
            cms.ajaxCall(options);
        else
            return false;
    }else{
        alert('미노출 상태는 등록할 수 없습니다.');
    }
});

// Dom 준비가 완료 된 후
$(document).ready(function () {

    // 테마 리스트에서 노출상태 검색 시
    $('select[name=selectType]').select2({
        "minimumResultsForSearch": Infinity
    }).on('change', function () {
        $('#table-theme-list tbody tr').show().filter(function () {
            if ($('select[name=selectType]').val() !== '') {
                var check = ($('select[name=selectType]').val() === 'N') ? '미노출' : '노출';
                if ($(this).children().eq(4).text().trim() !== check)
                    $(this).hide();
            } else {
                $(this).show();
            }
        });
    });

    // 테마 리스트에서 제목 검색 버튼 클릭 시
    $('#searchBtnThemeTitle').on('click', function () {
        $('#table-theme-list tbody tr').show().filter(function () {
            if ($(this).children().eq(2).text().indexOf($('#searchThemeTitle').val()) == -1 && $(this).children().eq(3).text().indexOf($('#searchThemeTitle').val()) == -1)
                $(this).hide()

        });
    });

    // 테마 리스트에서 제목 검색 엔터 누를 시
    $('#searchThemeTitle').on('keypress', function (e) {
        if (e.which == 13) {
            $('#table-theme-list tbody tr').show().filter(function () {
                if ($(this).children().eq(2).text().indexOf($('#searchThemeTitle').val()) == -1 && $(this).children().eq(3).text().indexOf($('#searchThemeTitle').val()) == -1)
                    $(this).hide()
            });
        }
    });

    if ($('#updateThemeEnableYn').length > 0) {
        $('input[name=createEnableYn]').each(function () {
            ($(this).val() === $('#updateThemeEnableYn').val()) ? $(this).attr('checked', true) : $(this).attr('checked', false)
        });
    }

    if ($('#updateRecommendThemeId').length > 0) {
        moveToPageResultSearch(0,6, $('#updateRecommendThemeId').val(), null);
    }

    if ($("#sortable tbody").length > 0) {
        // 테이블 편집 sortable 적용
        $("#sortable tbody").sortable({
            chosenClass: 'sortable-custom-choose',
            ghostClass: 'sortable-custom-ghost',
            dragClass: 'sortable-custom-drag',
            animation: 150,
            onEnd: function (evt) {
                $('#sortable tbody tr').each(function (i) {
                    $(this).find('td').eq(1).text($('#sortable tbody tr').length - i);
                });
            }
        });
    }
});

// 등록 -> 저장 클릭 시
$('#createBtnTheme').on('click', function () {
    var param = {};
    param.enableYn = 'N';
    param.title = $('#createThemeTitle').val().trim();
    param.titleEn = $('#createThemeTitleEn').val().trim();
    param.position = $('#table-theme-list tbody tr').length + 1;

    var formData = new FormData();

    if ($('#themeImg').attr('src') != undefined && $('#themeImg').attr('src') !== '')
        formData.append("img", new File([makeImageFile($('#themeImg').attr('src'))], ""));

    formData.append("body", JSON.stringify(param));

    if ($("#formCreateTheme").parsley().validate()) {
        var options = {
            "type": "POST",
            "url": "/recommend/theme",
            "data": formData,
            "processData": false,
            "contentType": false,
            'useTimer' : true,
            "callback": function () {
                var timer = timerAfterPageReload();
            }
        };
        var isConfirm = confirm('등록하시겠습니까?');
        if (isConfirm) {
            cms.ajaxCall(options);
        }
    }
});

// 수정 -> 저장 클릭 시
$('#updateBtnSave').on('click', function () {
    var param = {};
    param.recommendThemeId = $('#updateRecommendThemeId').val();
    param.enableYn = $('input[name=createEnableYn]:checked').val();
    param.title = $('#updateTitle').val().trim();
    param.titleEn = $('#updateTitleEn').val().trim();
    param.position = $('#updateThemePosition').val();

    var formData = new FormData();

    if ($('#themeImg').attr('src') == undefined || $('#themeImg').attr('src').indexOf('base64') < 0)
        param.themeImgUrl = $('#themeImg').attr("src");
    else
        formData.append("img", new File([makeImageFile($('#themeImg').attr('src'))], ""));

    formData.append("body", JSON.stringify(param));

    if ($("#formUpdateTheme").parsley().validate()) {
        var options = {
            "type": "PUT",
            "url": "/recommend/theme/" + param.recommendThemeId,
            "data": formData,
            "processData": false,
            "contentType": false,
            'useTimer' : true,
            "callback": function () {
                var timer = timerAfterPageReload();
            }
        };
        var isConfirm = confirm('등록하시겠습니까?');
        if (isConfirm) {
            cms.ajaxCall(options);
        }
    }
});

// pageNo = 0, searchType = 클립명, searchText, type = searchItem
function moveToPageResultSearch(pageNo, searchType, searchText, type) {

    var options = {
        "url": (type === 'searchItem') ? "/recommend/theme/item/search?size=10&page=" + pageNo + '&type=' + searchType + '&searchText=' + searchText : "/recommend/theme/item?size=10&page=" + pageNo + '&type=' + searchType + '&searchText=' + searchText,
        "type": "GET",
        "callback": function (result) {
            searchCallback(result, type);
        }
    };
    cms.ajaxCall(options);
}

function searchCallback(result, type) {
    if (result.pageResult !== null && result.pageResult !== undefined) {

        (type === 'searchItem') ? $("#tableSearchResult").empty() : $("#resultThemeItem").empty();
        (type === 'searchItem') ? null : $('#totalCount').empty().append('Total : '+ result.pageResult.totalElements);
        var str = '';
        for (var i = 0; i < result.pageResult.content.length; i++) {
            str += '<tr class="text-center">';
            str += '<td name="clipMetaId"><a onclick="window.open(\'/meta/clip/detail/'+ result.pageResult.content[i].clipMetaId + '\',\'_blank\',\'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes\')">' + result.pageResult.content[i].clipMetaId + '</a></td>';
            str += '<td class="text-left table-list-td-fixed" title="'+ result.pageResult.content[i].titleEn +'">' + result.pageResult.content[i].title + '</td>';
            str += '<td>' + ((result.pageResult.content[i].enableYn == 'Y') ? '<span class="label label-success">노출</span>' : '<span class="label label-default">확인필요</span>') + '</td>';
            str += '<td class="table-home-td-delete">';
            (type === 'searchItem') ? str += '<a name="btnInsert" class="btn btn-xs btn-info">등록</a>' : str += '<a name="btnDelete" class="btn btn-xs btn-danger">삭제</a>';
            str += '</td></tr>';
        }

        (type === 'searchItem') ? $("#tableSearchResult").append(str) : $("#resultThemeItem").append(str);
    }

    generatePaginationForm(result, type);
}

function generatePaginationForm(result, type) {

    var blockSize = 10;
    var firstBlock = parseInt((result.pageResult.number / blockSize) / blockSize);
    var lastBlock = result.pageResult.totalPages - 1;
    var curBlock = parseInt(result.pageResult.number / blockSize);
    var start = parseInt(curBlock * blockSize);
    var end = start + blockSize - 1;
    var isFirst = false;
    var isLast = false;
    var pagingType = (type === 'searchItem')? $('#type').val() : 6;
    var pagingText = (type === 'searchItem')? $('#text').val() : $('#updateRecommendThemeId').val();

    if (curBlock == 0)
        isFirst = true;

    if (result.pageResult.totalPages <= end + 1) {
        isLast = true;
        end = result.pageResult.totalPages - 1;
    }

    var pageStr = "";
    if (isFirst === false) {
        pageStr += "<li class=\"pagination_button previous\">";
        pageStr += "<a href=\"javascript:moveToPageResultSearch(" + (start - 1) + "," + pagingType + ",'" + pagingText + "','" + type + "')\">";
        pageStr += "<i class=\"fa fa-chevron-left\"></i></a></li>";
        pageStr += "<li class=\"pagination_button previous\"><a href=\"javascript:moveToPageResultSearch(" + (0) + "," + pagingType + ",'" + pagingText + "','" + type + "')\">1</a></li>";
        pageStr += "<li><span class='paginationEllipsis'>...</span></li>";
    }

    for (var i = start; i <= end; i++) {
        if (result.pageResult.number === i) {
            pageStr += "<li class=\"pagination_button active\">";
        } else {
            pageStr += "<li class=\"pagination_button\">";
        }

        pageStr += "<a href=\"javascript:moveToPageResultSearch(" + i + "," + pagingType + ",'" + pagingText + "','" + type + "')\">" + (i + 1) + "</a> </li>";
    }

    if (isLast === false) {
        pageStr += "<li><span class='paginationEllipsis'>...</span></li>";
        pageStr += "<li class=\"pagination_button next\">";
        pageStr += "<a href=\"javascript:moveToPageResultSearch(" + lastBlock + "," + pagingType + ",'" + pagingText + "','" + type + "')\">" + (lastBlock + 1) + "</a></li>";
        pageStr += "<li class=\"pagination_button next\">";
        pageStr += "<a href=\"javascript:moveToPageResultSearch(" + (end + 1) + "," + pagingType + ",'" + pagingText + "','" + type + "')\"><i class=\"fa fa-chevron-right\"></i></a> </li>";
    }

    (type === 'searchItem')? $('#ajaxSearchPaging').empty().append(pageStr) : $('#ajaxItemPaging').empty().append(pageStr);
}