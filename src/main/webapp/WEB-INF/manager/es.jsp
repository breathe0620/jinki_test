<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-6">
    <jsp:attribute name="javascriptSrc">
        <link href="/css/custom.css" rel="stylesheet">
        <script src="/js/custom.js"></script>
        <script>
            $('button[name=btn-es]').on('click',function () {

                var options = {
                    "type": "GET",
                    "url": "/meta/"+ $(this).attr('id') +"-all",
                    "callback": function resultCallback(result) {
                        alert(result.data);
                    }
                };
                cms.ajaxCall(options);
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <h3 class="page-header header-title">Elastic Search migration 관리</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="table-responsive">
                    <table class="table table-list">
                        <thead class="table-tr-th-text-center">
                        <tr>
                            <th width="25%">아티스트</th>
                            <th width="25%">앨범</th>
                            <th width="25%">곡</th>
                            <th width="25%">클립</th>
                        </tr>
                        </thead>
                        <tbody class="table-tr-td-text-center">
                        <tr>
                            <td><button type="button" class="btn btn-sm btn-info btn-excel" name="btn-es" id="artist-es">ElasticSearch에 넣기</button></td>
                            <td><button type="button" class="btn btn-sm btn-info btn-excel" name="btn-es" id="album-es">ElasticSearch에 넣기</button></td>
                            <td><button type="button" class="btn btn-sm btn-info btn-excel" name="btn-es" id="song-es">ElasticSearch에 넣기</button></td>
                            <td><button type="button" class="btn btn-sm btn-info btn-excel" name="btn-es" id="clip-es">ElasticSearch에 넣기</button></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
