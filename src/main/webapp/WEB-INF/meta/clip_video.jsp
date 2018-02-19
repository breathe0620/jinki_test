<%--
  Created by IntelliJ IDEA.
  User: moonkyu
  Date: 2017. 8. 29.
  Time: PM 6:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="/css/custom.css" rel="stylesheet">
</head>
<body class="videobackground">
<script src="/js/hls.js"></script>
<video id="video" controls autoplay width="100%" height="100%"></video>
<script>
    if(Hls.isSupported()) {
        var video = document.getElementById('video');
        var config = {
            debug: true,
            xhrSetup: function (xhr,url) {
                xhr.withCredentials = true; // do send cookie
                xhr.setRequestHeader("Access-Control-Allow-Origin","https://cms.mubeat.tv");
                xhr.setRequestHeader("Access-Control-Allow-Credentials","true");
            }
        };
        var hls = new Hls(config);
        var id = ${id};
        var bitrate = "${bitrate}";
        var movie = "https://vod2.cms.mubeat.tv/clip/"+id+"/"+bitrate+".m3u8";
        hls.loadSource(movie);
        hls.attachMedia(video);
        hls.on(Hls.Events.MANIFEST_PARSED,function() {
            video.play();
        });
    }
</script>
</body>
</html>
