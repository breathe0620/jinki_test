// 수정버튼 클릭시
$('[name=btnUpdate]').on('click',function () {

    var target = $(this).parent().parent();
    var targetSpan =  target.find('[name=viewVersion]');
    var targetInput =  target.find('[name=editorVersion]');

    targetSpan.css('display','none');
    targetInput.attr('type','text');
    target.find('[name=btnUpdate]').css('display','none');
    target.find('[name=btnSave],[name=btnClose]').css('display','inherit');

});

// 저장 클릭시.
$('[name=btnSave]').on('click',function () {

    var target = $(this).parent().parent();
    var param = {};
    param.osCode = target.find('[name=spanOsCode]').text();
    param.osName = target.find('[name=spanOsName]').text();
    param.version = target.find('[name=editorVersion]').val().trim();

    var options = {
        "type"     : "PUT",
        "url"      : "/manager/version",
        "data"   : JSON.stringify(param),
        "contentType" : "application/json; charset=utf-8",
        "dataType"    : "json",
        "callback" : function(){
            window.location.reload();
        }
    };

    if(confirm('수정하시겠습니까?'))
        cms.ajaxCall(options);
});

$('[name=btnClose]').on('click',function () {

    var target = $(this).parent().parent();
    var targetSpan =  target.find('[name=viewVersion]');
    var targetInput =  target.find('[name=editorVersion]');

    targetSpan.css('display','initial');
    targetInput.attr('type','hidden');

    target.find('[name=btnUpdate]').css('display','inherit');
    target.find('[name=btnSave],[name=btnClose]').css('display','none');
});

