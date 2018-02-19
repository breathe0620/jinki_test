// 비밀번호 변경 클릭시
$('[name=passwordChange]').on('click',function () {
    $(this).css('display','none');
    $('[name=adminPwd],[name=profileAdminPwd]').css('display','block').parent().addClass('inputParsley');
});

// 리스트 상세보기
$("tr[name=list] td:not([name=adminNo])").locationListView("[name=adminNo]", "/manager/admin/");

// 등록 -> 닫기, 상세 -> 목록 클릭시
$('#createBtnClose, #detailBtnList').locationBack(window.sessionStorage.getItem('url'));

// 프로필 -> 닫기 클릭시
$('#profileBtnClose').on('click',function () {
    window.location.href = '/';
});

// 등록버튼 클릭시
$('#btnCreate').on('click', function () {
    window.sessionStorage.setItem('url', window.location.href);
    window.location.href  = '/manager/admin/create';
});

// 상세 -> 수정  클릭시
$("#detailBtnUpdate").on('click',function(){
    $("#adminDetail").css("display","none"); //상세부분 숨기기
    $("#adminUpdate").css("display","block"); //수정부분 표시하기

    // 메타관리 체크박스
    if($('[name=detailMeta]').val() == 'Y')
        $('input:checkbox[name=updateMeta]').prop("checked",true);

    // 회원관리 체크박스
    if($('[name=detailMember]').val() == 'Y')
        $('input:checkbox[name=updateMember]').prop("checked",true);
});

// 수정 -> 닫기 클릭시
$('#updateBtnClose').locationReload();

// 저장버튼 클릭시
$("#updateBtnSave").on('click',function(){
    var param = {};
    param['adminId']   = $('#adminId').val();
    param['adminName'] = $('#adminName').val().trim();
    param['phoneNum']  = $('#phoneNum').val();
    param['adminPwd']  = $('#adminPwd').val();
    param['adminNo']   = $('#adminNo').val();
    param['adminPermission'] = {};

    if($('#updateMeta').is(":checked")){
        param['adminPermission']['meta'] = 'Y';
    }else{
        param['adminPermission']['meta'] = 'N';
    }
    if($('#updateMember').is(":checked")){
        param['adminPermission']['member'] = 'Y';
    }else{
        param['adminPermission']['member'] = 'N';
    }
    param['adminPermission']['adminNo'] = $('#adminNo').val();


    if($("#signupForm").parsley().validate()){
        var options = {
            "type"     : "PUT",
            "url"      : "/manager/admin/" + $('#adminNo').val(),
            "data"   : JSON.stringify(param),
            "contentType" : "application/json; charset=utf-8",
            "dataType"    : "json",
            "callback" : function(){
                location.reload();
            }
        };

        if(confirm('수정하시겠습니까?'))
            cms.ajaxCall(options);
    }
    return false;
});

// 저장버튼 클릭시
$("#profileBtnSave").on('click',function(){
    var param = {};
    param['adminId']   = $('#profileAdminId').val();
    param['adminName'] = $('#profileAdminName').val().trim();
    param['phoneNum']  = $('#profilePhoneNum').val();
    param['adminPwd']  = $('#profileAdminPwd').val();
    param['adminNo']   = $('#profileAdminNo').val();

    param['adminPermission'] = {};

    if($('#detailMeta').is(":checked")){
        param['adminPermission']['meta'] = 'Y';
    }else{
        param['adminPermission']['meta'] = 'N';
    }
    if($('#detailMember').is(":checked")){
        param['adminPermission']['member'] = 'Y';
    }else{
        param['adminPermission']['member'] = 'N';
    }
    param['adminPermission']['adminNo'] = $('#profileAdminNo').val();

    if($("#signupForm").parsley().validate()){
        var options = {
            "type"     : "PUT",
            "url"      : "/manager/admin/profile",
            "data"   : JSON.stringify(param),
            "contentType" : "application/json; charset=utf-8",
            "dataType"    : "json",
            "callback" : function(){
                window.location.href = '/';
            }
        };

        if(confirm('수정하시겠습니까?'))
            cms.ajaxCall(options);
    }
    return false;
});

// 등록 -> 저장 클릭시
$("#createBtnSave").on('click',function(){
    var param = {};
    param['adminId'] = $('#adminId').val();
    param['adminName'] = $('#adminName').val();
    param['phoneNum'] = $('#phoneNum').val();
    param['adminPwd'] = $('#adminPwd').val();
    param['secretKey'] = $('#adminSecretKey').val();
    param['adminPermission'] = {};

    if($('#meta').is(":checked"))
        param['adminPermission']['meta'] = 'Y';
    else
        param['adminPermission']['meta'] = 'N';

    if($('#member').is(":checked"))
        param['adminPermission']['member'] = 'Y';
    else
        param['adminPermission']['member'] = 'N';

    if($('#signupForm').parsley().validate()){
        var options = {
            'type'        : 'POST',
            'url'         : '/manager/admin/signup',
            'data'        : JSON.stringify(param),
            'contentType' : 'application/json; charset=utf-8',
            'dataType'    : 'json',
            'callback' : function(){
                window.location.href = '/manager/admin';
            }
        };
        var result = confirm('등록하시겠습니까?');
        if(result){
            cms.ajaxCall(options);
        }
    }
    return false;
});