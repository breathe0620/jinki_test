var options = {
    "type": "GET",
    "url": "/meta/clip/transcodedStates",
    "callback": function resultCallback(result) {
        transcodeListView(result);
    }
};

cms.ajaxCall(options);

function transcodeListView(result) {
    $('#transcodeOrEnableDone').append(result.transcodeOrEnableDone);
    $('#enableMapped').append(result.enableMapped);
    $('#allDoneTranscode').append(result.allDone);
    $('#readyToTranscode').append(result.readyToTranscode);
    $('#failsUpload').append(result.uploadFails);
    $('#failsTranscode').append(result.transcodeFails);
    $('#transcodedMusic').append(result.transcodedMusic);
    $('#transcodedEntertainment').append(result.transcodedEntertainment);
}