// 상세보기 이동
$("tr[name=list] td:not([name=userId])").locationListView("[name=userId]", "/manager/user/");

// 목록 클릭시
$("#detailBtnList").locationBack(window.sessionStorage.getItem('url'));

// 닫기 글릭시
$("#updateBtnClose").locationReload();

// 수정 클릭시
$('#detailBtnUpdate').on('click',function () {
    $('[name=detail]').hide();
    $('[name=update]').show();

    $('#selectStatus').val($('[name=userStatus]').val()).select2({"minimumResultsForSearch": Infinity});
    $('div[name=update]').parent('td').css('padding',0);
});

// 저장 클릭시
$('#updateBtnSave').on('click',function () {
   var stateCode = $("#selectStatus option:selected").val();

    if(confirm('수정하시겠습니까?')){
        $.ajax({
            type: "PUT",
            url : "/manager/user/"+ $('[name=userNo]').text(),
            data : stateCode,
            contentType : 'application/json; charset=utf-8',
            dataType    : 'json',
            success : function (result) {
                location.reload();
            }
        });
    }
});