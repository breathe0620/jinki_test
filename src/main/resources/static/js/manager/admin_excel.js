

$('a[name=uploadExcelEn]').on('click', function () {

    var url = $(this).parent().find('input[type=hidden]').val();
    var file = $(this).parent().find('input[type=file]')[0].files[0];

    if(file !== null && file !== undefined) {
        var formData = new FormData();
        formData.append('excel', file);

        var options = {
            "type": "PUT",
            "url": url,
            "data": formData,
            "processData": false,
            "contentType": false,
            "callback": function () {
                alert('성공적으로 저장되었습니다.');
                location.reload();
            }
        };

        var isConfirm = confirm('등록하시겠습니까?');
        if (isConfirm) {
            cms.ajaxCall(options);
        }
    }else {
        alert('파일을 선택해주시길 바랍니다.');
    }
    return false;
});

$('a[name=uploadClipExcelEn]').on('click', function () {

    var url = $(this).parent().find('input[type=hidden]').val();
    var file = $(this).parent().find('input[type=file]')[0].files[0];

    if(file !== null && file !== undefined) {
        var formData = new FormData();
        formData.append('excel', file);

        var options = {
            "type": "PUT",
            "url": url,
            "data": formData,
            "processData": false,
            "contentType": false,
            "callback": function (result) {
                console.log(result);

                if(result.data !== undefined)
                    alert('파일등록 실패 \n' + JSON.stringify(result.data));
                else
                    alert('성공적으로 저장되었습니다.');

                location.reload();
            }
        };

        var isConfirm = confirm('등록하시겠습니까?');
        if (isConfirm) {
            cms.ajaxCall(options);
        }
    }else {
        alert('파일을 선택해주시길 바랍니다.');
    }
    return false;
});