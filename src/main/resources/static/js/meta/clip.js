//이미지 보기
cms.lightBox();

// select2 적용
$('select').select2({'minimumResultsForSearch': Infinity}).on('change',function () {
    $(this).parsley().validate();
});

// 클립 영문명 자동생성
$('#autoCreateClipTitleEn').on('click',function () {
    var strClipTitleEn = '';
    var strClipArtist = '';
    var strClipSong = '';

    $('td[name=clipArtistNameEn]').each(function () {
        if($(this).text() !== '')
            strClipArtist += $(this).text() + ', ';
    });

    $('td[name=clipSongNameEn]').each(function () {
        if($(this).text() !== '')
            strClipSong += $(this).text() + ', ';
    });

    strClipTitleEn += strClipArtist.substr(0, strClipArtist.lastIndexOf(','));
    if(strClipSong.length > 0)
        strClipTitleEn += ' - ';
    strClipTitleEn += strClipSong.substr(0, strClipSong.lastIndexOf(','));

    $('#updateClipTitleEn').val(strClipTitleEn);
});

// 아티스트검색
$('#artistSearch').keyup(function (e) {
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

// 곡 검색
$('#songSearch').keyup(function (e) {
    $(this).getWordCheck(e, function (word) {

        var options = {
            'url': '/meta/search-song-meta/' + word,
            'callback': function resultCallback(result) {
                $('#songSearch').makeSearchComponent('song', result, e);
            }
        };

        cms.ajaxSearch(options);
    });

});

// 아티스트, 곡 정보 삭제
$('.btn-clip-info-delete').tableDelete();

// 아티스트 정보 추가
$('button[name=btnArtistAdd]').on('click',function () {
    var targetName = $(this).parent().parent().find('[name=searchInputName]');
    var targetId = $(this).parent().parent().find('[name=searchInputId]');

    if(targetName.val() != ''){
        var options = {
            'type': 'GET',
            'url': '/meta/clip/artist/' + targetId.val(),
            'callback': function resultCallback(result) {
                $('#artistInfo').makeTableInfo('artist', result);
            }
        };
        cms.ajaxCall(options);
        targetName.val('').focus();
        targetId.val('');

    }else{
        alert('입력값이 없습니다.');
    }
});

// 곡 정보 추가
$('button[name=btnSongAdd]').on('click',function () {
    var targetName = $(this).parent().parent().find('[name=searchInputName]');
    var targetId = $(this).parent().parent().find('[name=searchInputId]');

    if(targetName.val() != ''){
        var options = {
            'type': 'GET',
            'url': '/meta/clip/song/' + targetId.val(),
            'callback': function resultCallback(result) {
                $('#songInfo').makeTableInfo('song', result);
            }
        };
        cms.ajaxCall(options);
        targetName.val('').focus();
        targetId.val('');

    }else{
        alert('입력값이 없습니다.');
    }
});

// CDN 정보보기
$('[name=btnCdn]').on('click',function () {
    var cdn = $(this).val(),
        clipMetaId = $('#detailClipMetaId').val();

    window.open('/meta/clip/video/' + clipMetaId + '/' + cdn,'_blank','width=720, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=no');

});

//원본영상보기
$('[name=showVideo]').on('click',function () {
    console.log($(this).val());
    window.open('/meta/clip/origin/video?url=' + $(this).val(),'_blank','width=720, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=no');
});

// 상세보기 이동
$('tr[name=list] td:not([name=clipMetaId])').locationListView('[name=clipMetaId]', '/meta/clip/detail/');

// 목록 클릭시
$('#detailBtnList').locationBack(window.sessionStorage.getItem('url'));

// 닫기 글릭시
$('#updateBtnClose').locationReload();

//수정화면
$('#detailBtnUpdate').on('click',function(){
    $('#clipDetail').css('display','none'); //상세부분 숨기기
    $('#clipUpdate').css('display','block'); //수정부분 표시하기
    $('td').not('.update-td').css('background-color','rgba(244, 245, 247, 0.6)');

    // 독점여부 체크박스
    if($('[name=detailIsOriginal]').val() == 'Y')
        $('input:checkbox[name=updateIsOriginal]').prop('checked',true);

    // 카테고리 select
    if($('[name=detailClipClassifyName]').val() != null){
        $('#selectCategory').val($('[name=detailClipClassify]').val());
        $('#selectCategory').parent().find('.select2-selection__rendered').empty().append($('[name=detailClipClassifyName]').val());
    }else{
        $('#selectCategory').val('');
    }

    $('input:radio[name=radioEnable]:input[value='+$('input[name=clipDetailEnable]').val()+']').attr('checked','checked');

    $('.btn-clip-info-delete').tableDelete();

});

// 저장 클릭시
$('#updateBtnSave').unbind().bind().on('click',function() {

    var param = {};
    param.songList    = [];
    param.artistList  = [];
    param.clipTitle   = $('#updateClipTitle').val().trim();
    param.clipTitleEn = $('#updateClipTitleEn').val().trim();
    // param.programId = $('#updateProgramId').val();
    // param.clipMetaId  = $('#updateClipMetaId').val();

    // 상세 카테고리 select
    param.clipClassify      = $('#selectCategory option:selected').val();

    // 노출 여부 select
    param.enableYn      = $(':radio[name=radioEnable]:checked').val();

    // 독점여부 체크박스
    if($('input:checkbox[name=updateIsOriginal]').is(':checked') == true)
        param.isoriginal = 'Y';
    else
        param.isoriginal = 'N';

    // 아티스트 저장
    $('td[name=checkArtistId]').each(function(index){
        var artistGroup = {};
        artistGroup.artistId = $(this).text();
        param.artistList.push(artistGroup);
    });

    // 곡 저장
    $('td[name=checkSongId]').each(function(index){
        var songGroup = {};
        songGroup.songId = $(this).text();
        param.songList.push(songGroup);
    });

    var formData = new FormData();
    formData.append('body',JSON.stringify(param));

    if($('#formClip').parsley().validate()) {
        var options = {
            'type'        : 'PUT',
            'url'         : '/meta/clip/'+$('#updateClipMetaId').val(),
            'data'        : formData,
            'processData' : false,
            'contentType' : false,
            'callback'    : function(){
                window.location.href = decodeURIComponent(document.location.href);
            }
        };
        var isConfirm = confirm('등록하시겠습니까?');
        if(isConfirm){
            cms.ajaxCall(options);
        }
    }
    return false;
});
