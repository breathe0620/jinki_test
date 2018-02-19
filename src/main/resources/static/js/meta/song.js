// select2 적용
$('#selectGenre').select2({
    'placeholder': '유형 조회',
    'minimumResultsForSearch': Infinity
});

// 수정 보기
$("#detailBtnUpdate").on('click', function () {

    // 수정시 css로 화면 표시 처리
    $("#songDetail").css("display", "none");
    $("#songUpdate").css("display", "block");

    $("#selectGenre").val($('#genreId').text().split(":")[1].trim()).prop("selected", true);
    $(".select2-selection__rendered").empty().append($('#genreName').text());

    redrawInput($('#artistSearch'));
    $('.a-delete').btnDelete();
});

// search callback
$('input[name=searchInputName]').on("onAutoLayoutSelected", function (e) {
    // 그룹, 소속, 유사, 관련 아티스트 버튼 생성
    if ($(this).attr('id') === 'artistSearch') {
        var resultType = $(this).parent().attr("name");
        if (e.id !== null) {
            var arrayCheck = [];
            var duplicateCheck = true;

            $("td[name=" + resultType + "] :input[name=btnArtist]").each(function (index) {
                arrayCheck[index] = $(this).val();
            });

            for (var i = 0; i < arrayCheck.length; i++) {
                if (arrayCheck[i] === e.id) {
                    alert("이미 중복된 아티스트가 존재합니다.");
                    duplicateCheck = false;
                }
            }

            if (duplicateCheck === true) {
                var buttonStr = "";
                buttonStr += "<input type='hidden' name='btnArtist' value='" + e.id;
                buttonStr += "'/> <button type='button' name='btnBox' class='btn btn-sm btn-info btn-tag'><span><strong>" + e.name;
                buttonStr += "</strong></span> <a class='a-delete' href='#'><i class='ace-icon fa fa-times'></i></a></button>";

                $(this).before(buttonStr);
                redrawInput($(this));
            }
            $(this).parent().find('input[name=searchInputName]').val('');
            $(this).focus();
            $('.a-delete').btnDelete();
        }
    }
});

// 목록, 닫기 클릭시
$("#detailBtnList, #createBtnClose").locationBack(window.sessionStorage.getItem('url'));

// 닫기 누를시
$("#updateBtnClose").locationReload();

// 상세보기 이동
$("tr[name=list] td:not(:nth-child(1)):not(:nth-child(2))").locationListView("[name=songId]", "/meta/song/detail/");

// 등록버튼 클릭시
$('#btnCreate').on('click', function () {
    window.sessionStorage.setItem('url', window.location.href);
    window.location.href  = '/meta/song/create';
});

// MID 중복 체크
$('#createOriginId, #updateOriginId').checkOriginId("/meta/song/check/");

// 삭제시
$("#detailBtnDelete").on('click', function () {
    var options = {
        "url": "/meta/song/" + $('#detailSongId').text(),
        "type": "DELETE",
        "callback": function acallback(result) {
            window.location.href = window.sessionStorage.getItem('url');
        }
    };

    if (confirm('삭제하시겠습니까?') === true)
        cms.ajaxCall(options);
});

// 앨범검색
$('#albumSearch').keyup(function (e) {
    $(this).getWordCheck(e, function (word) {
        var options = {
            "url": "/meta/search-album-meta/" + word,
            "callback": function resultCallback(result) {
                $("#albumSearch").makeSearchComponent('album', result, e);
            }
        };

        cms.ajaxSearch(options);
    });
});

// 아티스트 검색
$('#artistSearch').keyup(function (e) {
    if ($('#artistSearch').attr('data-parsley-required')) {
        $('#artistSearch').removeAttr('data-parsley-required').parent('td').removeClass('input-parsley');
        $('#artistSearch').parent().find('.parsley-errors-list').remove();
        $('#artistSearch').parent().addClass('parsley-success');
    }
    $(this).getWordCheck(e, function (word) {
        var options = {
            "url": "/meta/search-artist-meta/" + word,
            "callback": function resultCallback(result) {
                console.log(result);
                $("#artistSearch").makeSearchComponent('artist', result, e);
            }
        };

        cms.ajaxSearch(options);
    });
});

// 저장 클릭시
$('#createBtnSave').unbind().bind().on('click', function () {
    var param = {};
    param.songName = $('#createSongName').val().trim();
    param.songNameEn = $('#createSongNameEn').val().trim();
    param.genreId = $('#selectGenre option:selected').val();
    param.originId = $('#createOriginId').val();
    param.lyric = $('#createLyric').val();
    param.albumId = $('#albumSearch').parent().find('input[name=searchInputId]').val();

    var str = '';
    if ($('td[name=artist] :input[name=btnArtist]').length > 0) {
        $("td[name=artist] :input[name=btnArtist]").each(function (e) {
            str += $(this).val() + ',';
        });
        $('#artistSearch').removeAttr('data-parsley-required', 'true').parent('td').removeClass('input-parsley');
    } else {
        $('#artistSearch').attr('data-parsley-required', 'true').parent('td').addClass('input-parsley');
    }

    param.artistId = str.slice(0, -1);

    if ($("#formSong").parsley().validate()) {
        var options = {
            "type": "POST",
            "url": "/meta/song",
            "data": JSON.stringify(param),
            "contentType": "application/json; charset=utf-8",
            "dataType": "json",
            "callback": function acallback(result) {
                window.location.href = "/meta/song";
            }
        };
        var isConfirm = confirm('등록하시겠습니까?');
        if (isConfirm)
            cms.ajaxCall(options);
    }
    return false;
});

// 수정 저장 클릭시
$("#updateBtnCreate").on('click', function (e) {
    e.preventDefault();

    var url = "/meta/song/" + $('#updateSongId').val();
    var param = {};
    param.songId = $("#updateSongId").val();
    param.songName = $("#updateSongName").val().trim();
    param.songNameEn = $('#updateSongNameEn').val().trim();
    param.originId = $("#updateOriginId").val();
    param.genreId = $("#selectGenre option:selected").val();
    param.albumId = $('#albumSearch').parent().find('input[name=searchInputId]').val();
    param.lyric = $('#updateLyric').val();

    var str = '';
    if ($('td[name=artist] :input[name=btnArtist]').length > 0) {
        $("td[name=artist] :input[name=btnArtist]").each(function (e) {
            str += $(this).val() + ',';
        });
        $('#artistSearch').removeAttr('data-parsley-required', 'true').parent('td').removeClass('input-parsley');
    } else {
        $('#artistSearch').attr('data-parsley-required', 'true').parent('td').addClass('input-parsley');
    }
    param.artistId = str.slice(0, -1);

    if ($("#formSong").parsley({
            errorsWrapper: "<span class='parsley-errors-list'></span>",
            errorTemplate: "<span style='color:red;'></span>"
        }).validate()) {
        var options = {
            "type": "PUT",
            "url": url,
            "data": JSON.stringify(param),
            "contentType": "application/json; charset=utf-8",
            "dataType": "json",
            "callback": function acallback(result) {
                if (result.code !== '0000')
                    alert("저장하는데 실패하였습니다 : " + result.msg);
                else
                    location.reload();
            }
        };

        var isConfirm = confirm('수정하시겠습니까?');
        if (isConfirm)
            cms.ajaxCall(options);

    }
    return false;
});

