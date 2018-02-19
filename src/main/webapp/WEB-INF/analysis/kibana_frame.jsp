<%--
  Created by IntelliJ IDEA.
  User: moonkyu
  Date: 2017. 9. 12.
  Time: AM 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
    <jsp:attribute name="javascriptSrc">
      <link href="/css/custom.css" rel="stylesheet">
     </jsp:attribute>
    <jsp:body>
        <div id="page-wrapper">
            <div class="row">
                <iframe src="${kibanaPage}" width="100%" height="600px"></iframe>
            </div>
        </div>
    </jsp:body>
</t:template>
