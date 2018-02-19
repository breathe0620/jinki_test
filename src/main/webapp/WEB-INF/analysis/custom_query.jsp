<%--
  Created by IntelliJ IDEA.
  User: moonkyu
  Date: 2017. 9. 13.
  Time: PM 5:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
    <jsp:attribute name="javascriptSrc">
      <link href="/css/custom.css" rel="stylesheet">
        <script>
            $('#executeQuery').on('click',function () {
                var param = {};
                param['index'] = $('#index').val();
                param['meta'] = $('#meta').val();
                param['payload'] = $('#payload').val();

               $.ajax({
                   type: 'post',
                   url : '/analysis/customQuery',
                   contentType : 'application/json; charset=utf-8',
                   dataType    : 'json',
                   data : JSON.stringify(param),
                   success: function (result) {
                       console.log(result);
                       $('#result').empty();
                       $('#result').val(JSON.stringify(result, null, 2));
                   }
               })
            });

        </script>
     </jsp:attribute>
    <jsp:body>
        <div id="page-wrapper">
            <div class="row">
                <button id="executeQuery">test</button>
                <input id="index" type="text">
                <input id="meta" type="text"><br>
                <textarea id="payload" rows="15", cols="100"></textarea><br>
                <textarea id="result" rows="30", cols="100"></textarea>
            </div>
        </div>
    </jsp:body>
</t:template>
