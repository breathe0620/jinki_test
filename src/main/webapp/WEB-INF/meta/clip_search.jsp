<%--
  Created by IntelliJ IDEA.
  User: moonkyu
  Date: 2017. 7. 28.
  Time: PM 2:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div style="width: 20%; height: 100%; float: left;">
        <input type="text" id="keyword" value="아이" onkeyup="search()">
        <button onclick="search()">찾기</button>
    </div>
    <div style="width: 80%; height: 100%; float: left;">
        <div id="result">
        </div>
    </div>
</body>
<script src="/vendor/jquery/jquery.min.js"></script>
<script>
    function search() {
        $.ajax({
            url: "/meta/clip/search/"+$('#keyword').val(),
            type: "GET",
            success: function (result) {
                var data = JSON.parse(result);
                var total = data.hits.total;
                var resource = data.hits.hits;

                $('#keyword_total').html("<p>"+total+"</p>");

                var htmlData = "<ul>";

                for(var i = 0; i < resource.length; i++) {
                    var source = resource[i]._source;
                    var type = resource[i]._type;

                    htmlData += '<li>';
                    if (type == "artist") {
                        htmlData += '<p style="background:#E4F7BA">';
                    } else if (type == "song") {
                        htmlData += '<p style="background:#FFD8D8">';
                    } else {
                        htmlData += '<p style="background:#E8D9FF">';
                    }
                    htmlData += '[Type] : '+type+'<br>';
                    if (type == "artist" || type == "song") {
                        htmlData += '[Name] : '+source.name+'<br>';
                        htmlData += '[Keyword] : '+source.keyword+'<br>';
                    } else {
                        htmlData += '[Title] : '+source.title+'<br>';
                        htmlData += '[Keyword] : '+source.keyword+'<br>';
                    }
                    htmlData += '</p>';
                    htmlData += '</li>';
                }

                htmlData += "</ul>";

                $('#result').html(htmlData);
            }
        });
    }
</script>
</html>
