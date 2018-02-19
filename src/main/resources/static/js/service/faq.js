// 등록 -> Naver SmartEditor 2.0 적용 //
if($('#createContent').length > 0){
    var oEditors = []; // 개발되어 있는 소스에 맞추느라, 전역변수로 사용하였지만, 지역변수로 사용해도 전혀 무관 함.

    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors, // 전역변수 명과 동일해야 함.
        elPlaceHolder: 'createContent', // 에디터가 그려질 textarea ID 값과 동일 해야 함.
        sSkinURI: '/vendor/smarteditor2-master/workspace/SmartEditor2Skin.html', // Editor HTML
        fCreator: 'createSEditor2', // SE2BasicCreator.js 메소드명이니 변경 금지 X
        htParams: { // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseToolbar: true, // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseVerticalResizer: true, // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
            fOnBeforeUnload : function(){},
            bUseModeChanger: true
        }
    });
}

// 수정 -> Naver SmartEditor 2.0 적용 //
if($('#updateContent').length > 0){
    var oEditors = [];

    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: 'updateContent',
        sSkinURI: '/vendor/smarteditor2-master/workspace/SmartEditor2Skin.html',
        fCreator: 'createSEditor2',
        htParams: {
            bUseToolbar: true,
            bUseVerticalResizer: true,
            fOnBeforeUnload : function(){},
            bUseModeChanger: true
        }
    });
}

// 카테고리 select2 적용
$('#createCategory, #updateCategory').select2({
    'placeholder': '카테고리 선택',
    'minimumResultsForSearch': Infinity
});

// 등록 -> 언어별 선택시
$('li[name=createLangType]').on('click',function () {
    confirm('해당 언어로 이동하게 될 경우 현재 작업하신 내용이 저장되지 않습니다.\n이동하시겠습니까?');
    $('#createSubject').val('');
    oEditors.getById['createContent'].exec('SET_IR', ['']);
});

// 상세 -> 언어별 선택시
$('li[name=detailLangType]').on('click',function () {

    if($(this).text() == '한글')
        var langType = 'KO';
    else
        var langType = 'EN';

    var options = {
        'type': 'GET',
        'url': '/service/faq/' + $('#detailFaqId').text() + '/' + langType,
        'callback': function acallback(result) {
            if(result.data != null){
                $('#detailSubject').empty().append(result.data.subject);
                $('#detailContent').empty().append(result.data.content);
            }else{
                $('#detailSubject').empty();
                $('#detailContent').empty();
            }
        }
    };
    cms.ajaxCall(options);
});

// 수정 -> 언어별 선택시
$('li[name=updateLangType]').on('click',function () {
    confirm('해당 언어로 이동하게 될 경우 현재 작업하신 내용이 저장되지 않습니다.\n이동하시겠습니까?')

    if($(this).text() == '한글')
        var langType = 'KO';
    else
        var langType = 'EN';

    var options = {
        'type': 'GET',
        'url': '/service/faq/' + $('#detailFaqId').text() + '/' + langType,
        'callback': function acallback(result) {
            if(result.data != null){
                $('#updateSubject').val(result.data.subject);
                oEditors.getById['updateContent'].exec('SET_IR', [result.data.content]);
            }else{
                $('#updateSubject').val('');
                oEditors.getById['updateContent'].exec('SET_IR', ['']);
            }

        }
    };
    cms.ajaxCall(options);
});

// 목록, 닫기 클릭시
$('#detailBtnList, #createBtnClose').locationBack(window.sessionStorage.getItem('url'));

// 수정 -> 닫기 누를시
$('#updateBtnClose').locationReload();

// 상세보기 이동
$('tr[name=list] td:not([name=faqId])').locationListView('[name=faqId]', '/service/faq/');

// 등록 버튼 클릭시
$('#btnCreate').on('click', function () {
    window.sessionStorage.setItem('url', window.location.href);
    window.location.href = '/service/faq';
});

// 수정 버튼 클릭시
$('#detailBtnUpdate').on('click', function () {
    var langType;
    $('#detailFaq').css('display', 'none');
    $('#updateFaq').css('display', 'block');

    if($('#detailIsImportant').is(':checked'))
        $('#updateIsImportant').prop('checked',true);

    // 카테고리 select
    if($('#detailCategory').val() != null){
        $('#updateCategory').val($('#detailCategory').val());
        $('#updateCategory').parent().find('.select2-selection__rendered').empty().append($('#updateCategory option:selected').text());
    }else{
        $('#updateCategory').val('');
    }

    // 언어별 Tab 중 해당 언어로 클릭한 정보 수정창에 보여주도록
    if($('li[name=detailLangType].active').text() == '한글'){
        $('#updateLangTypeEn').removeClass('active');
        $('#updateLangTypeKo').addClass('active');
        langType = 'KO';
    }else{
        $('#updateLangTypeEn').addClass('active');
        $('#updateLangTypeKo').removeClass('active');
        langType = 'EN';
    }

    var options = {
        'type': 'GET',
        'url': '/service/faq/' + $('#detailFaqId').text() + '/' + langType,
        'callback': function acallback(result) {
            if(result.data != null){
                $('#updateSubject').val(result.data.subject);
                oEditors.getById['updateContent'].exec('SET_IR', [result.data.content]);
            }else{
                $('#updateSubject').val('');
                oEditors.getById['updateContent'].exec('SET_IR', ['']);
            }
        }
    };
    cms.ajaxCall(options);

});

// 삭제시
$('#btnDelete').on('click',function(){
    var faqId = $('#detailFaqId').text();
    var options = {
        'url'         : '/service/faq/'+ faqId,
        'type'        : 'DELETE',
        'callback' : function acallback(result){
            window.location.href = '/service/faqs';
        }
    };
    var result = confirm('삭제하시겠습니까?');
    if(result){
        cms.ajaxCall(options);
    }
    return false;
});

// 등록 -> 저장 클릭시
$('#createBtnSave').on('click', function () {
    var param = {};
    param.category    = $('#createCategory').val();
    param.content     = oEditors.getById['createContent'].getIR();
    param.subject     = $('#createSubject').val().trim();

    if($('#createIsImportant').is(':checked'))
        param.isImportant = 'Y';
    else
        param.isImportant = 'N';

    if($('li[name=createLangType].active').text() == '한글')
        param.langType = 'KO';
    else
        param.langType = 'EN';

    if(param.content == '<p><br></p>'){
        alert('내용을 입력하세요.');
        oEditors.getById['createContent'].exec('FOCUS'); //포커싱
        return false;
    }

    if ($('#formFaq').parsley().validate()) {
        var options = {
            'type': 'POST',
            'url': '/service/faq',
            'data': JSON.stringify(param),
            'contentType': 'application/json; charset=utf-8',
            'dataType': 'json',
            'callback': function acallback(result) {
                window.location.href = '/service/faqs';
            }
        };
        var isConfirm = confirm('등록하시겠습니까?');
        if (isConfirm)
            cms.ajaxCall(options);
    }
    return false;
});

// 수정 -> 저장 클릭시
$('#updateBtnSave').on('click', function () {
    var param = {};
    param.category    = $('#updateCategory').val();
    param.content     = oEditors.getById['updateContent'].getIR();
    param.subject     = $('#updateSubject').val().trim();
    param.faqNo    = $('#detailFaqId').text();

    if($('#updateIsImportant').is(':checked'))
        param.isImportant = 'Y';
    else
        param.isImportant = 'N';

    if($('li[name=updateLangType].active').text() == '한글')
        param.langType = 'KO';
    else
        param.langType = 'EN';

    if(param.content == '<p><br></p>'){
        alert('내용을 입력하세요.');
        oEditors.getById['updateContent'].exec('FOCUS'); //포커싱
        return false;
    }

    if ($('#formFaq').parsley().validate()) {
        var options = {
            'type': 'PUT',
            'url': '/service/faq/' + param.faqNo,
            'data': JSON.stringify(param),
            'contentType': 'application/json; charset=utf-8',
            'dataType': 'json',
            'callback': function acallback(result) {
                window.location.href = decodeURIComponent(document.location.href);
            }
        };
        var isConfirm = confirm('수정하시겠습니까?');
        if (isConfirm)
            cms.ajaxCall(options);
    }
    return false;
});

