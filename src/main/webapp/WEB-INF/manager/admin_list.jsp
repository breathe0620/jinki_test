<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-6">
    <jsp:attribute name="javascriptSrc">
        <script src="/js/manager/admin.js"></script>
        <script src="/js/pagination.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>관리자 관리
                            <small>회원 관리 / <b><a href="/manager/admin">관리자 관리</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" class="btn btn-sm btn-info" id="btnCreate">등록</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" style="margin-top: 20px;">
            <div class="col-sm-12 col-md-12">
                <div class="table-responsive">
                    <table class="table table-striped table-list table-custom">
                        <thead class="th-text-center">
                        <tr>
                            <th width="10%">고유번호</th>
                            <th width="30%">아이디</th>
                            <th width="10%">이름</th>
                            <th width="15%">휴대폰 번호</th>
                            <th width="10%">관리자 유형</th>
                            <th width="15%">생성일</th>
                        </tr>
                        </thead>
                        <tbody class="td-text-center">
                        <c:forEach var="item" items="${pageResult.content}" varStatus="status">
                            <tr name="list">
                                <td name="adminNo">${item.adminNo}</td>
                                <td>${item.adminId}</td>
                                <td>${item.adminName}</td>
                                <td>${item.phoneNum}</td>
                                <c:choose>
                                    <c:when test="${item.adminType == 'S'}">
                                        <td>
                                            <span class="label label-success">슈퍼관리자</span>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <span class="label label-warning">일반관리자</span>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td><fmt:formatDate pattern = "yyyy-MM-dd HH:mm:ss"  value = "${item.createDt}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%--<form action="/manager/admin" method="get" id="paginationForm">--%>
            <%--<t:pagination></t:pagination>--%>
        <%--</form>--%>
    </jsp:body>
</t:template>
