<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-6">
    <jsp:attribute name="javascriptSrc">
        <script src="/js/manager/versions.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <h3 class="page-header header-title">버전 관리 리스트</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="table-responsive">
                    <table class="table table-hover table-list">
                        <thead class="table-tr-th-text-center">
                        <tr>
                            <th width="25%">OS 코드</th>
                            <th width="25%">OS Name</th>
                            <th width="25%">Version</th>
                            <th width="10%"></th>
                        </tr>
                        </thead>
                        <tbody class="table-tr-td-text-center">
                            <c:forEach var="item" items="${versions}" varStatus="status">
                                <tr id="info">
                                    <td><span name="spanOsCode">${item.osCode}</span></td>
                                    <td><span name="spanOsName">${item.osName}</span></td>
                                    <td>
                                        <span name="viewVersion">${item.version}</span>
                                        <input type="hidden" class="form-control" name="editorVersion" value="${item.version}"/>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-sm btn-warning" name="btnUpdate">수정</button>
                                        <button type="button" class="btn btn-sm btn-info"    name="btnSave" style="display: none;">저장</button>
                                        <button type="button" class="btn btn-sm btn-danger"  name="btnClose" style="display: none;">닫기</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
