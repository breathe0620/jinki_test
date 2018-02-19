// 월 ~ 일 요일 리스트에 넣어주기
function generateWeekName() {
    var array = ['월', '화', '수', '목', '금', '토', '일'];
    $('#tableRecommendProgramList tbody tr').each(function (index) {
        $(this).children().eq(1).text(array[index] + '요일');
    });
}

// 이미지 보기
cms.lightBox();

// 방송사 선택 select2 적용
$('#selectCorporator').select2({
    "minimumResultsForSearch": Infinity
}).on('change', function () {
    if($(this).val() !== null && $(this).val() !== '') {
        var options = {
            'type' : 'GET',
            'url' : '/recommend/weekprogram/program/'+ $(this).val(),
            'callback': function (result) {
                $('#selectProgram').empty().generateProgram(result);
            }
        };
        cms.ajaxCall(options);
    }
});

// 프로그램 선택 select2 적용
$('#selectProgram').select2();

// 방송일자 선택 select2 적용
$('#selectProgramBroadDate').select2({
    "minimumResultsForSearch": Infinity
}).on('change',function () {
    $(this).generateProgramBroadDateList();
});

$.fn.generateProgramBroadDateList = function () {
    if($(this).val() !== null && $(this).val() !== '') {
        var options = {
            'type' : 'GET',
            'url' : '/recommend/weekprogram/broaddate/'+ $('#updateRecommendProgramId').val() +'/'+ $('#updateProgramId').val() +'/'+ $(this).val(),
            'callback': function (result) {
                $(this).generateBroadProgram(result);
            }
        };
        cms.ajaxCall(options);
    }
};

// 등록된 클립 삭제 시
$(document).on('click', '#btnDelete', function () {
    if($('#resultWeekProgramItems tr input[type=checkbox]:checked').length > 0){
        var array = [];
        $('#resultWeekProgramItems tr').each(function () {
            if($(this).children().eq(0).find('input[type=checkbox]').prop('checked') === true){
                var param = {};
                param['recommendProgramId'] = $('#updateRecommendProgramId').val();
                param['clipMetaId'] = $(this).children().eq(1).text();
                array.push(param);
            }
        });

        var options = {
            'type' : 'DELETE',
            'url' : '/recommend/weekprogram/item',
            'data': JSON.stringify(array),
            "contentType": "application/json; charset=utf-8",
            "dataType": "json",
            'callback': function (result) {
                generateProgramItems($('#updateRecommendProgramId').val());
                $('#selectProgramBroadDate').generateProgramBroadDateList();
            }
        };
        if(confirm('삭제하시겠습니까?'))
            cms.ajaxCall(options);
        else
            return false;
    }else{
        alert('삭제할 클립이 없습니다.');
        return false;
    }
});

// 방송일자 매핑된 클립 등록 시
$('#btnItemInsert').on('click', function () {
    if($('#tableSearchResult tr input[type=checkbox]:checked').length > 0){
        var array = [];
        var check = true;
        $('#tableSearchResult tr').each(function () {

            if($(this).children().eq(0).find('input[type=checkbox]').prop('checked') === true){
                if ($(this).children().eq(4).text().trim() === '미노출') {
                    alert('미노출 상태는 등록할 수 없습니다.');
                    check = false;
                    return false;
                }
                var param = {};
                param['recommendProgramId'] = $('#updateRecommendProgramId').val();
                param['clipMetaId'] = $(this).children().eq(1).text();
                array.push(param);
            }
        });

        if(check){
            var options = {
                'type' : 'POST',
                'url' : '/recommend/weekprogram/item',
                'data': JSON.stringify(array),
                "contentType": "application/json; charset=utf-8",
                "dataType": "json",
                'callback': function (result) {
                    generateProgramItems($('#updateRecommendProgramId').val());
                    $('#selectProgramBroadDate').generateProgramBroadDateList();
                }
            };
            if(confirm('등록하시겠습니까?'))
                cms.ajaxCall(options);
            else
                return false;
        }
    }else{
        alert('등록할 클립이 없습니다.');
        return false;
    }
});

// 수정 버튼 클릭 시
$('#detailBtnUpdate').on('click', function () {
   $('.detail').hide();
   $('.update').show();

   $('#weekProgramImgUpload, #labelWeekProgramImgUpload, #editWeekProgramImgUpload').attr('disabled', false);
});

//checkbox 모두 체크 로직
$('#searchCheckBox').change(function () {
    var checked = $(this).is(":checked");
    $("input[name=programClipSearchItem]:checkbox").each(function() {
        if (checked) {
            $(this).not(":disabled").prop("checked", true);
        } else {
            $(this).not(":disabled").prop("checked", false);
        }
    });
});

//checkbox 모두 체크 로직
$('#resultCheckBox').change(function () {
    var checked = $(this).is(":checked");
    $("input[name=programClipResultItem]:checkbox").each(function() {
        if (checked) {
            $(this).not(":disabled").prop("checked", true);
        } else {
            $(this).not(":disabled").prop("checked", false);
        }
    });
});

// 프로그램 이미지 파일 변경 시
cms.ImgAcceptPreview("#weekProgramImgUpload", "#weekProgramImg", function () {
    $('#cropImageTarget').val('#weekProgramImg');
    window.open('/meta/artist-image-crop', '', 'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes');
});

// 프로그램 이미지 파일 수정 시
$('#editWeekProgramImgUpload').on('click', function () {
    if ($('#weekProgramImg').attr('src') != null) {
        $('#cropImageTarget').val('#weekProgramImg');
        window.open('/meta/artist-image-crop', '', 'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes');
    } else {
        alert('수정할 이미지가 없습니다.');
        return false;
    }
});

// 상세보기 이동
$('a[name=btnWeekProgramDetail]').on('click', function () {
    window.location.href = '/recommend/weekprogram/detail/' + $(this).parent().parent().find('td[name=recommendProgramId]').text();
});

// 닫기 버튼 클릭 시
$('#updateBtnClose').locationReload();

// 목록 버튼 클릭 시
$('#detailBtnList').on('click', function () {
   window.location.href = '/recommend/weekprogram';
});

// 수정 -> 저장 클릭 시
$('#updateBtnSave').on('click', function () {
    var param = {};
    param['recommendProgramId'] = $('#updateRecommendProgramId').val();
    param['programId'] = ($('#selectProgram').val() !== null && $('#selectProgram').val() !== '')? $('#selectProgram').val() : $('#updateProgramId').val();

    var formData = new FormData();

    if ($('#weekProgramImg').attr('src') == undefined || $('#weekProgramImg').attr('src').indexOf('base64') < 0)
        param['bannerImgUrl'] = $('#weekProgramImg').attr("src");
    else
        formData.append("img", new File([makeImageFile($('#weekProgramImg').attr('src'))], ""));

    formData.append("body", JSON.stringify(param));

    if ($("#formWeekProgram").parsley().validate()) {
        var options = {
            "type": "PUT",
            "url": "/recommend/weekprogram/" + param['recommendProgramId'],
            "data": formData,
            "processData": false,
            "contentType": false,
            'useTimer' : true,
            "callback": function () {
                var timer = timerAfterPageReload();
            }
        };
        if (confirm('등록하시겠습니까?')) {
            cms.ajaxCall(options);
        }
    }
});

function generateProgramItems(recommendProgramId) {
    $(':input[type=checkbox]').prop('checked',false);
    var options = {
        'type' : 'GET',
        'url' : '/recommend/weekprogram/item/'+ recommendProgramId,
        'callback': function (result) {
            generateProgramItemsHtml(result);
        }
    };
    cms.ajaxCall(options);
}

function generateProgramItemsHtml(result) {
    $('#resultWeekProgramItems').empty();
    if(result['data'] !== null && result['data'] !== undefined) {
        $('#totalCount').empty().append('Total : '+ result['data'].length);
        for (var i = 0; i < result['data'].length; i++) {
            var str = '';
            str += '<tr>';
            str += '<td class="table-home-td-delete line-height-zero vertical-middle"><input type="checkbox" class="checkBox-program-search" name="programClipResultItem"></td>';
            str += '<td><a onclick="window.open(\'/meta/clip/detail/'+ result['data'][i]['clipMetaId'] +'\',\'_blank\',\'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes\')">'+ result['data'][i]['clipMetaId'] +'</a></td>';
            str += '<td class="text-left table-list-td-fixed" title="'+ result['data'][i]['titleEn'] +'">'+ result['data'][i]['title'] +'</td>';
            str += '<td>'+ moment(result['data'][i]['broadDt']).format('YYYY-MM-DD') +'</td>';
            str += '<td>';
//            str += (result['data'][i]['enableYn'] !== 'N')? '<span class="label label-success">노출</span>':'<span class="label label-danger">미노출</span>';
            str += (result['data'][i]['enableYn'] == 'Y')? '<span class="label label-success">노출</span>':(result['data'][i]['enableYn'] == 'N')?'<span class="label label-danger">미노출</span>':(result['data'][i]['enableYn'] == 'R')?'<span class="label label-warning">보류</span>':'<span class="label label-default">폐기</span>';
            str += '</td>';
            str += '</tr>';
            $('#resultWeekProgramItems').append(str);
        }
    }
}

// 방송사 생성
$.fn.generateCorporator = function(result) {
    if(result['data'] !== null && result['data'] !== undefined){

        $(this).append('<option value="">방송사 선택</option>');

        for (var i = 0; i <  result['data'].length; i++){
            $(this).append('<option value="'+ result['data'][i] +'">'+ result['data'][i] +'</option>');
        }
    }else{
        alert('해당 데이터가 없습니다.');
    }
};

// 프로그램 생성
$.fn.generateProgram = function (result) {
    if(result['data'] !== null && result['data'] !== undefined){

        $(this).append('<option value="">프로그램 선택</option>');

        for (var i = 0; i <  result['data'].length; i++){
            $(this).append('<option value="'+ result['data'][i]['programId'] +'">'+ result['data'][i]['programTitle'] +'</option>');
        }
    }else{
        alert('해당 데이터가 없습니다.');
    }
};

// 방송일자 생성
$.fn.generateBroadDate = function (result) {
    if(result['data'] !== null && result['data'] !== undefined){
        $(this).append('<option value="">방송일자 선택</option>').attr('disabled', false);

        for (var i = 0; i <  result['data'].length; i++){
            $(this).append('<option value="'+ result['data'][i] +'">'+ result['data'][i] +'</option>');
        }
    }else{
        // alert('해당 데이터가 없습니다.');
        $(this).append('<option>해당 데이터가 없습니다.</option>').attr('disabled', true);
    }
};

// 방송일자 클립 검색 리스트
$.fn.generateBroadProgram = function (result) {
    $('#tableSearchResult').empty();
    if(result['data'] !== null && result['data'] !== undefined){
        for (var i = 0; i <  result['data'].length; i++){
            var str = '';
            str += '<tr>';
            str += '    <td class="table-home-td-delete line-height-zero vertical-middle"><input type="checkbox" class="checkBox-program-search" name="programClipSearchItem"></td>';
            str += '    <td><a onclick="window.open(\'/meta/clip/detail/'+ result['data'][i]['clipMetaId'] +'\',\'_blank\',\'fullscreen=yes, status=yes, toolbar=yes, location=yes, scrollbars=yes, resizable=yes\')">'+ result['data'][i]['clipMetaId'] +'</a></td>';
            str += '    <td class="text-left table-list-td-fixed" title="'+ result['data'][i]['titleEn'] +'">'+ result['data'][i]['title'] +'</td>';
            str += '    <td>'+ moment(result['data'][i]['broadDt']).format('YYYY-MM-DD') +'</td>';
            str += '    <td>';
            str +=          (result['data'][i]['enableYn'] !== 'N')? '<span class="label label-success">노출</span>':'<span class="label label-danger">미노출</span>';
            str += '    </td>';
            str += '</tr>';
            $('#tableSearchResult').append(str);
        }
    }
};

// DOM이 모두 준비 되었을 때 함수 실행
$(document).ready(function () {
    if($('#tableRecommendProgramList tbody tr').length > 0)
        generateWeekName();

    if($('#selectCorporator').length > 0){
        var options = {
            'type' : 'GET',
            'url' : '/recommend/weekprogram/program/',
            'callback': function (result) {
                $('#selectCorporator').generateCorporator(result);
            }
        };
        cms.ajaxCall(options);
    }

    if($('#selectProgramBroadDate').length > 0){
        var options = {
            'type': 'GET',
            'url': '/recommend/weekprogram/broaddate/' + $('#updateProgramId').val(),
            'callback': function (result) {
                $('#selectProgramBroadDate').empty().generateBroadDate(result);
            }
        };
        cms.ajaxCall(options);
    }
    if($('#resultWeekProgramItems').length > 0) {
        generateProgramItems($('#updateRecommendProgramId').val());
    }
});