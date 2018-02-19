// select2 적용
$('#selectArtistType').select2({
    'placeholder': '유형 조회',
    'minimumResultsForSearch': Infinity
});

// 메인이미지 파일변경시
cms.ImgAcceptPreview('#mainImg', '#mainImgView', function () {
    $('#cropImageTarget').val('#mainImgView');
    window.open('/meta/artist-image-crop', '', 'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes');
});

// 프로필이미지 파일변경시
cms.ImgAcceptPreview('#profileImg', '#profileImgView', function () {
    $('#cropImageTarget').val('#profileImgView');
    window.open('/meta/artist-image-crop', '', 'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes');
});

// 메인 이미지 기존파일 Crop시
$('#editMainImg').on('click', function () {
    if ($('#mainImgView').attr('src') != null) {
        $('#cropImageTarget').val('#' + $(this).attr('name') + 'View');
        window.open('/meta/artist-image-crop', '', 'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes');
    } else {
        alert('수정할 이미지가 없습니다.');
        return false;
    }
});

// 프로필 이미지 기존파일 Crop시
$('#editProfileImg').on('click', function () {
    if ($('#profileImgView').attr('src') != null) {
        $('#cropImageTarget').val('#' + $(this).attr('name') + 'View');
        window.open('/meta/artist-image-crop', '', 'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes');
    } else {
        alert('수정할 이미지가 없습니다.');
        return false;
    }
});

// 데뷔곡
$('#debutSongSearch').keyup(function (e) {
    $(this).getWordCheck(e, function (word) {
        var options = {
            'url': '/meta/search-song-meta/' + word,
            'callback': function resultCallback(result) {
                $('#debutSongSearch').makeSearchComponent('song', result, e);
            }
        };

        cms.ajaxSearch(options);
    });

});

// 소속사 검색
$('#agencySearch').keyup(function (e) {
    $(this).getWordCheck(e, function (word) {
        var options = {
            'url': '/meta/agency/' + word,
            'callback': function resultCallback(result) {
                $('#agencySearch').makeSearchComponent('agency', result, e);
            }
        };

        cms.ajaxSearch(options);
    });
});

// search callback
$('input[name=searchInputName]').on('onAutoLayoutSelected', function (e) {
    // 소속사 연관 아티스트 출력
    if ($(this).attr('id') === 'agencySearch' && e.id != null) {
        var options = {
            'url': '/meta/artist/agency/' + e.id,
            'callback': function (result) {
                var str = '';
                for (var i = 0; i < result.data.length; i++) {
                    str += result.data[i].artistName + ', ';
                }
                $('#sameAgencyArtist').val(str);
            }
        };
        cms.ajaxSearch(options);

    }
    // 그룹, 소속, 유사, 관련 아티스트 버튼 생성
    else if ($(this).attr('id') === 'groupArtistSearch' ||
        $(this).attr('id') === 'memberArtistSearch' ||
        $(this).attr('id') === 'relativeArtistSearch' ||
        $(this).attr('id') === 'similarArtistSearch') {

        var resultType = $(this).parent().attr('name');
        if (e.id !== null) {
            var arrayCheck = [];
            var duplicateCheck = true;

            $('td[name=' + resultType + '] :input[name=btnArtist]').each(function (index) {
                arrayCheck[index] = $(this).val();
            });

            for (var i = 0; i < arrayCheck.length; i++) {
                if (arrayCheck[i] === e.id) {
                    alert('이미 중복된 아티스트가 존재합니다.');
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

// 소속 그룹 검색
$('#groupArtistSearch').keyup(function (e) {
    $(this).getWordCheck(e, function (word) {
        var options = {
            'url': '/meta/search-artist-meta/' + word,
            'callback': function resultCallback(result) {
                $('#groupArtistSearch').makeSearchComponent('artist', result, e);
            }
        };

        cms.ajaxSearch(options);
    });
});

// 그룹멤버 검색
$('#memberArtistSearch').keyup(function (e) {
    $(this).getWordCheck(e, function (word) {
        var options = {
            'url': '/meta/search-artist-meta/' + word,
            'callback': function resultCallback(result) {
                $('#memberArtistSearch').makeSearchComponent('artist', result, e);
            }
        };

        cms.ajaxSearch(options);
    });
});

// 관련 아티스트 검색
$('#relativeArtistSearch').keyup(function (e) {
    $(this).getWordCheck(e, function (word) {
        var options = {
            'url': '/meta/search-artist-meta/' + word,
            'callback': function resultCallback(result) {
                $('#relativeArtistSearch').makeSearchComponent('artist', result, e);
            }
        };

        cms.ajaxSearch(options);
    });
});

// 유사 아티스트 검색
$('#similarArtistSearch').keyup(function (e) {
    $(this).getWordCheck(e, function (word) {
        var options = {
            'url': '/meta/search-artist-meta/' + word,
            'callback': function resultCallback(result) {
                $('#similarArtistSearch').makeSearchComponent('artist', result, e);
            }
        };

        cms.ajaxSearch(options);
    });
});

// MID 중복 체크
$('#createOriginId, #updateOriginId').checkOriginId('/meta/artist/check/');

// 데뷔, 결성일 Datepicker
$('#debutDtPicker, #organizeDtPicker').datepicker({
    startView: '2',
    format: 'yyyy-mm-dd',
    language: 'ko',
    orientation: 'bottom auto',
    autoclose: true
});

// 생년월일 Datepicker
$('#birthDtPicker').datepicker({
    startView: '2',
    defaultViewDate: {year: 1990, month: 0, day: 1},
    format: 'yyyy-mm-dd',
    language: 'ko',
    orientation: 'bottom auto',
    autoclose: true
});

// 활동년도 Datepicker
$('#activeAgesPicker').datepicker({
    format: 'yyyy',
    language: 'ko',
    orientation: 'bottom auto',
    multidate: true,
    multidateSeparator: ',',
    clearBtn: true,
    allowInputToggle: true,
    viewMode: 'century',
    minViewMode: 'century',
    endDate: new Date()
}).datepickerValidate('#activeAges');

// 목록 클릭시
$('#detailBtnList, #createBtnClose').locationBack(window.sessionStorage.getItem('url'));

// 닫기 글릭시
$('#updateBtnClose').locationReload();

// 등록 버튼 클릭시
$('#btnCreate').on('click', function () {
    window.sessionStorage.setItem('url', window.location.href);
    window.location.href = '/meta/artist/create';
});

// 상세보기 이동
$('tr[name=list] td:not(:nth-child(1)):not(:nth-child(2))').locationListView('[name=artistId]', '/meta/artist/detail/');

//수정화면
$('#detailBtnUpdate').on('click', function () {
    $('#artistDetail').css('display', 'none'); //상세부분 숨기기
    $('#artistUpdate').css('display', 'block'); //수정부분 표시하기

    $('#activeAges').val($('span[name=detailActiveAges]').text());
    $('#sameAgencyArtist').val($('span[name=detailSameAgencyArtist]').text());

    $('#selectArtistType').val($('#artistTypeId').text().split(':')[1].trim()).prop('selected', true);
    $('.select2-selection__rendered').empty().append($('#artistTypeName').text());

    redrawInput($('#memberArtistSearch, #similarArtistSearch, #groupArtistSearch, #relativeArtistSearch'));
    $('.a-delete').btnDelete();
});

// 삭제시
$('#detailBtnDelete').on('click', function () {
    var options = {
        'url': '/meta/artist/' + $('#detailArtistId').text(),
        'type': 'DELETE',
        'callback': function acallback() {
            window.location.href = '/meta/artist';
        }
    };

    if (confirm('삭제하시겠습니까?'))
        cms.ajaxCall(options);
    return false;
});

// 저장 클릭시
$('#createBtnSave').unbind().bind().on('click', function () {
    var param = {};
    param.artistGroup = [];
    param.memberArtist = [];
    param.relativeArtist = [];
    param.similarArtist = [];
    param.activeAges = [];
    param.debutDt = ($('#debutDt').val().length > 0 ? $('#debutDt').val() : null);
    param.organizeDt = ($('#organizeDt').val().length > 0 ? $('#organizeDt').val() : null);
    param.artistName = $('#createArtistName').val().trim();
    param.artistNameEn = $('#createArtistNameEn').val().trim();
    param.birthDt = ($('#birthDt').val().length > 0 ? $('#birthDt').val() : null);
    param.artistTypeId = $('#selectArtistType option:selected').val();
    param.originId = $('#createOriginId').val();
    param.debutSongId = $('td[name=debutSong] :input[name=searchInputId]').val();
    param.agencyId = $('td[name=agency] :input[name=searchInputId]').val();

    $('td[name=group] :input[name=btnArtist]').each(function () {
        var group = {};
        group.artistGroupId = $(this).val();
        param.artistGroup.push(group);
    });

    $('td[name=member] :input[name=btnArtist]').each(function () {
        var group = {};
        group.memberArtistId = $(this).val();
        param.memberArtist.push(group);
    });

    $('td[name=relative] :input[name=btnArtist]').each(function () {
        var group = {};
        group.relativeArtistId = $(this).val();
        param.relativeArtist.push(group);
    });

    $('td[name=similar] :input[name=btnArtist]').each(function () {
        var group = {};
        group.similarArtistId = $(this).val();
        param.similarArtist.push(group);
    });

    var str = $('#activeAges').val();
    var strSplit = str.split(',');
    if (str != '') {
        for (var i = 0; i < strSplit.length; i++) {
            var group = {};
            group.activeAges = strSplit[i].trim();
            param.activeAges.push(group);
        }
    }

    var formData = new FormData();

    if ($('#mainImgView').attr('src') != undefined)
        formData.append('mainImg', new File([makeImageFile($('#mainImgView').attr('src'))], ''));
    if ($('#profileImgView').attr('src') != undefined)
        formData.append('profileImg', new File([makeImageFile($('#profileImgView').attr('src'))], ''));

    formData.append('body', JSON.stringify(param));

    if ($('#formArtist').parsley().validate()) {
        var options = {
            'type': 'POST',
            'url': '/meta/artist',
            'data': formData,
            'processData': false,
            'contentType': false,
            'callback': function () {
                window.location.href = '/meta/artist';
            }
        };
        var isConfirm = confirm('등록하시겠습니까?');
        if (isConfirm) {
            cms.ajaxCall(options);
        }
    }
    return false;
});

// 수정 -> 저장 클릭시
$('#updateBtnCreate').unbind().bind().on('click', function () {
    var param = {};
    param.artistGroup = [];
    param.memberArtist = [];
    param.relativeArtist = [];
    param.similarArtist = [];
    param.activeAges = [];

    param.debutDt = ($('#debutDt').val().length > 0 ? $('#debutDt').val() : null);
    param.organizeDt = ($('#organizeDt').val().length > 0 ? $('#organizeDt').val() : null);
    param.birthDt = ($('#birthDt').val().length > 0 ? $('#birthDt').val() : null);

    param.artistName = $('#updateArtistName').val().trim();
    param.artistNameEn = $('#updateArtistNameEn').val().trim();
    param.artistTypeId = $('#selectArtistType option:selected').val();
    param.originId = $('#updateOriginId').val();
    param.artistId = $('#updateArtistId').val();

    param.debutSongId = $('td[name=debutSong] :input[name=searchInputId]').val();
    param.agencyId = $('td[name=agency] :input[name=searchInputId]').val();

    $('td[name=group] :input[name=btnArtist]').each(function () {
        var group = {};
        group.artistGroupId = $(this).val();
        group.artistId = $('#updateArtistId').val();
        param.artistGroup.push(group);
    });

    $('td[name=member] :input[name=btnArtist]').each(function () {
        var group = {};
        group.memberArtistId = $(this).val();
        group.artistId = $('#updateArtistId').val();
        param.memberArtist.push(group);
    });

    $('td[name=relative] :input[name=btnArtist]').each(function () {
        var group = {};
        group.relativeArtistId = $(this).val();
        group.artistId = $('#updateArtistId').val();
        param.relativeArtist.push(group);
    });

    $('td[name=similar] :input[name=btnArtist]').each(function () {
        var group = {};
        group.similarArtistId = $(this).val();
        group.artistId = $('#updateArtistId').val();
        param.similarArtist.push(group);
    });

    var str = $('#activeAges').val();
    var strSplit = str.split(',');
    if (str != '') {
        for (var i = 0; i < strSplit.length; i++) {
            var group = {};
            group.activeAges = strSplit[i].trim();
            group.artistId = $('#updateArtistId').val();
            param.activeAges.push(group);
        }
    }
    console.log(JSON.stringify(param));
    var formData = new FormData();

    if ($('#mainImgView').attr('src') == undefined || $('#mainImgView').attr('src').indexOf('base64') < 0)
        param.mainImgUrl = $('#mainImgView').attr('src');
    else
        formData.append('mainImg', new File([makeImageFile($('#mainImgView').attr('src'))], ''));
    if ($('#profileImgView').attr('src') == undefined || $('#profileImgView').attr('src').indexOf('base64') < 0)
        param.profileImgUrl = $('#profileImgView').attr('src');
    else
        formData.append('profileImg', new File([makeImageFile($('#profileImgView').attr('src'))], ''));

    formData.append('body', JSON.stringify(param));

    if ($('#formArtist').parsley().validate()) {
        var options = {
            'type': 'PUT',
            'url': '/meta/artist/' + $('#updateArtistId').val(),
            'data': formData,
            'processData': false,
            'contentType': false,
            'useTimer' : true,
            'callback': function () {
                var timer = timerAfterPageReload();
            }
        };
        var isConfirm = confirm('등록하시겠습니까?');
        if (isConfirm) {
            cms.ajaxCall(options);
        }
    }
    return false;
});

// 추가된 table 수정시
$('[name=test1]').on('click', function () {
    var target = $(this).prev().attr('id');
    $('#cropImageTarget').val('#' + target + 'View');
    window.open('/sample', '', 'fullscreen,status=no, toolbar=no, location=no, scrollbars=no, resizable=no');
});

