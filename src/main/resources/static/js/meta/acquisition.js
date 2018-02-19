// 이미지 보기
cms.lightBox();

$(':checkbox[name=transcode]').parent().click(function(event) {
    event.stopPropagation();
});

// CDN 정보보기
$('[name=btnCdn]').on('click',function () {
    var cdn = $(this).val(),
        clipMetaId = $('#detailClipMetaId').val();

    window.open('/meta/clip/video/' + clipMetaId + '/' + cdn,'_blank','width=720, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=no');

});

// transcode 요청
$('#btnTranscode').on('click',function () {
    var check_values = [];

    $.each($("input[name='transcode']:checked"), function(i){
        check_values.push($(this).parent().parent().find('[name=clipMetaId]').text());
    });

    if(check_values.length > 0) {
        transcodeSumbit(check_values);
    } else {
        alert('요청할 목록을 체크하지 않았습니다.');
    }
});

//checkbox 모두 체크 로직
$('#allCheckBox').change(function () {
    var checked = $(this).is(':checked');
    $('input[name=transcode]:checkbox').each(function() {
        if (checked) {
            $(this).not(':disabled').prop('checked', true);
        } else {
            $(this).not(':disabled').prop('checked', false);
        }
    });
});

//원본영상보기
$('#showVideo').on('click',function () {
    window.open('/meta/clip/origin/video?url=' + $(this).val(),'_blank','width=720, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=no');
});

// 목록 클릭시
$('#detailBtnList').locationBack(window.sessionStorage.getItem('url'));

// 닫기 클릭시
$('#updateBtnClose').locationReload();

// 상세보기 이동
$('tr[name=list] td:not(:nth-child(1)):not(:nth-child(2))').locationListView('[name=clipMetaId]', '/meta/acquisition/detail/');

// 수정클릭시
$('#detailBtnUpdate').on('click',function () {
    $('[name=detail]').css('display','none');
    $('[name=update]').css('display','block');
    $('td').css('background-color','rgba(244, 245, 247, 0.6)');
    $('.table-td-update').css('padding','0px');
});

// 저장 클릭시
$('#updateBtnSave').on('click',function () {

    var param = {};
    param.clipTitle   = $('#updateClipTitle').val().trim();
    // param.clipTitleEn = $('#updateClipTitleEn').val().trim();

    if($('#formAcquisition').parsley().validate()){
        var options = {
            'type'        : 'PUT',
            'url'         : '/meta/acquisition/'+$('#detailClipMetaId').val(),
            'data'        : $('#updateClipTitle').val().trim(),
            'contentType' : 'application/json; charset=utf-8',
            'dataType'    : 'json',
            'callback'    : function(){
                location.reload();
            }
        };

        var isConfirm = confirm('등록하시겠습니까?');
        if(isConfirm){
            cms.ajaxCall(options);
        }
    }
    return false;
});

