// select2 적용
$('#selectAlbumType').select2({
    'placeholder' : '유형 조회',
    'minimumResultsForSearch': Infinity
});

// search callback
$('input[name=searchInputName]').on('onAutoLayoutSelected', function(e) {
    // 그룹, 소속, 유사, 관련 아티스트 버튼 생성
    if ($(this).attr('id') === 'artistSearch'){
        var resultType = $(this).parent().attr('name');
        if(e.id !== null){
            var arrayCheck = [];
            var duplicateCheck = true;

            $('td[name='+resultType+'] :input[name=btnArtist]').each(function(index){
                arrayCheck[index] = $(this).val();
            });

            for(var i = 0; i < arrayCheck.length; i++){
                if(arrayCheck[i] === e.id){
                    alert('이미 중복된 아티스트가 존재합니다.');
                    duplicateCheck = false;
                }
            }

            if(duplicateCheck === true){
                var buttonStr = "";
                buttonStr += "<input type='hidden' name='btnArtist' value='" + e.id ;
                buttonStr += "'/> <button type='button' name='btnBox' class='btn btn-sm btn-info btn-tag'><span><strong>" + e.name ;
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

// 아티스트 검색
$('#artistSearch').keyup(function (e) {
    if($('#artistSearch').attr('data-parsley-required')){
        $('#artistSearch').removeAttr('data-parsley-required').parent('div').removeClass('input-parsley');
        $('#artistSearch').parent().find('.parsley-errors-list').remove();
        $('#artistSearch').parent().addClass('parsley-success');
    }
    $(this).getWordCheck(e, function (word) {
        var options = {
            'url': '/meta/search-artist-meta/' + word,
            'callback': function resultCallback(result) {
                $('#artistSearch').makeSearchComponent('artist', result, e);
            }
        };

        cms.ajaxSearch(options);
    });
});

// 발매일 Datepicker
$('#publishDtPicker').datepicker({
    startView: '2',
    format: 'yyyy-mm-dd',
    language: 'ko',
    orientation: 'bottom auto',
    autoclose: true
}).datepickerValidate('#publishDt');

// 앨범 img 업로드시 png파일 제한
cms.ImgAcceptPreview('#albumImg', '#albumImgView');

// 상세보기 이동시
$('tr[name=list] td:not(:nth-child(1)):not(:nth-child(2))').locationListView('[name=albumId]', '/meta/album/detail/');

// 목록 클릭시
$('#detailBtnList, #createBtnClose').locationBack(window.sessionStorage.getItem('url'));

// 수정 -> 닫기 클릭시
$('#updateBtnClose').locationReload();

// 등록 버튼 클릭시
$('#btnCreate').on('click', function () {
    window.sessionStorage.setItem('url', window.location.href);
    window.location.href = '/meta/album/create';
});

// MID 중복 체크
$('#updateOriginId, #createOriginId').checkOriginId('/meta/album/check/');

// 수정 클릭시
$('#detailBtnUpdate').on('click', function () {

    $('#albumDetail').css('display', 'none');
    $('#albumUpdate').css('display', 'block');

    if ($('#detailPublishDt').text() != '') {
        $('#publishDt').val(moment($('#detailPublishDt').text()).format('YYYY-MM-DD'));
    } else {
        $('#publishDt').val('');
    }

    $('#selectAlbumType').val($('#albumTypeId').text().split(':')[1].trim()).prop('selected', true);
    $('.select2-selection__rendered').empty().append($('#albumTypeName').text());

    redrawInput($('input[name=searchInputName]'));
    $('.a-delete').btnDelete();
});

// 삭제시
$('#detailBtnDelete').on('click', function () {
    var options = {
        'url': '/meta/album/' + $('#detailAlbumId').text(),
        'type': 'DELETE',
        'callback': function acallback(result) {
            window.location.href = '/meta/album'
        }
    };

    if (confirm('삭제하시겠습니까?') === true)
        cms.ajaxCall(options);
});

// 저장 클릭시
$('#createBtnSave').unbind().bind().on('click', function () {
    var param = {}, str = '';
    var formData = new FormData();

    param.albumTitle  = $('#createAlbumTitle').val().trim();
    param.albumTitleEN  = $('#createAlbumTitleEN').val().trim();
    param.originId    = $('#createOriginId').val();
    param.publishDt   = ($('#publishDt').val().length > 0 ? $('#publishDt').val() : null);
    param.albumTypeId = $('#selectAlbumType option:selected').val();

    if($('td[name=artist] :input[name=btnArtist]').length > 0){
        $('td[name=artist] :input[name=btnArtist]').each(function(e){
            str += $(this).val()+',';
        });
        $('#artistSearch').removeAttr('data-parsley-required','true').parent('div').removeClass('input-parsley');
    }else{
        $('#artistSearch').attr('data-parsley-required','true').parent('div').addClass('input-parsley');
    }
    param.artistId = str.slice(0,-1);

    formData.append('body', JSON.stringify(param));
    formData.append('albumImg', $('input[name=albumImg]')[0].files[0]);

    if ($('#formAlbum').parsley().validate()) {
        var options = {
            'type': 'POST',
            'url': '/meta/album',
            'data': formData,
            'processData': false,
            'contentType': false,
            'callback': function () {
                window.location.href = '/meta/album';
            }
        };
        var isConfirm = confirm('등록하시겠습니까?');
        if (isConfirm) {
            cms.ajaxCall(options);
        }
    } else {
        $('#formAlbum').parsley();
    }
    return false;
});

// 저장 클릭시
$('#updateBtnSave').unbind().bind().on('click', function () {
    var param = {}, str = '';
    var formData = new FormData();

    param.albumTitle = $('#updateAlbumTitle').val().trim();
    param.albumTitleEN = $('#updateAlbumTitleEN').val().trim();
    param.originId = $('#updateOriginId').val();
    param.publishDt = ($('#publishDt').val().length > 0 ? $('#publishDt').val() : null);
    param.albumTypeId = $('#selectAlbumType option:selected').val();
    param.albumId = $('#detailAlbumId').val();

    if($('td[name=artist] :input[name=btnArtist]').length > 0){
        $('td[name=artist] :input[name=btnArtist]').each(function(e){
            str += $(this).val()+',';
        });
        $('#artistSearch').removeAttr('data-parsley-required','true').parent('div').removeClass('input-parsley');
    }else{
        $('#artistSearch').attr('data-parsley-required','true').parent('div').addClass('input-parsley');
    }
    param.artistId = str.slice(0,-1);

    if ($('input[name=albumImg]')[0].files[0] == undefined)
        param.albumImgUrl = $('#datailAlbumImgUrl').text();
    else
        formData.append('albumImg', $('input[name=albumImg]')[0].files[0]);

    formData.append('body', JSON.stringify(param));

    if ($('#formAlbum').parsley().validate()) {
        var options = {
            'type': 'PUT',
            'url': '/meta/album/' + $('#detailAlbumId').text(),
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
