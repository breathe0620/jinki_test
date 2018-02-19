var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#status").show();
    } else {
        $("#status").hide();
    }
    $("#statusMessage").html("")
}

function connect() {
    var socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {
       setConnected(true);
       stompClient.subscribe("/spark/status", function(status) {
           presentStatus(JSON.parse(status.body).content);
       });
       stompClient.subscribe('/spark/data', function(data) {
           presentData(JSON.parse(data.body).content);
       });
    });
}

$('#analysisType, #chartType').select2({
    'placeholder' : '조회',
    'minimumResultsForSearch': Infinity
});

$("#analysisDtpicker").datepicker({
    startView: "0",
    format: "yyyy/mm/dd",
    language:"ko",
    orientation: "bottom auto",
    endDate : "-7D",
    autoclose: true
});

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
}

function presentStatus(status) {
    $("#statusMessage").append("<span>"+ status + "</span><br>");
}

function presentData(data) {
    var subData = data.split('\n');
    var date = [], param = {}, totalCol, totalRow, day = [];
    param.days = {};

    totalCol = subData[0].split(',').length - 2;
    totalRow = data.split('\n').length - 2;

    for (var i = 0; i <= totalRow; i++){
        date = subData[i].split(',');
        day[i] = date.shift();
        for (var e = 0; e < i; e ++)
            date.shift();
        param.days[''+day[i]+''] = date;
    }

    $('#result').empty().retention({
        data : param,
        dateDisplayFormat : "YYYY/MM/DD",
        title : "Retention Analysis",
        retentionDays : totalCol,
        enableTooltip : false,
        enableDateRange : false,
        showHeaderValues : false,
        enableInactive: false,
        showAbsolute : false,
        toggleValues : false
    });
}

$(function () {
    connect();
    $( "#btnAnalysis" ).click(function() {
        if($('#analysisDtStart').val() != "")
            resultRequest();
        else
            alert("Date를 선택하시길 바랍니다.");
    });
});

function resultRequest() {
    $('#result, #statusMessage').empty();

    var param = {};
    param.type = $('[name=type]').val();
    param.log = $('[name=log]').val();
    param.environment = $('[name=environment]').val();
    param.startDate = $('[name=date]').val();
    param.date;
    var dateFormat = "{";

    if(param.type == "retention"){
        for(var i = 0; i < 7; i++){
            dateFormat += moment(new Date($('[name=date]').val())).add(i,'day').format('YYYY/MM/DD');
            if (i == 6) {
                dateFormat += "}";
            } else {
                dateFormat += ",";
            }
        }
        param.date = dateFormat;
    }

    $.ajax({
        type : "GET",
        url : "/analysis/jobRequest",
        data : param,
        success : function (result) {
            if(result.data != null || result.data != "")
                presentData(result.data);
        }
    });
}

$(window).on("beforeunload", function(){
    disconnect();
});
