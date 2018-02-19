<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-3">
<jsp:attribute name="javascriptSrc">
  <script src="/js/pagination.js"></script>
  <script src="/js/search-filter.js"></script>
  <script src="/js/manager/user.js"></script>
</jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>회원 관리
                            <small>회원 관리 / <b><a href="/manager/user">회원 관리</a></b></small>
                        </h3>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="box">
                    <div class="box-title">
                        <h5><i class="fa fa-search"></i>회원 리스트 조회</h5>
                        <div class="box-tools">
                            <a class="reload-link" id="pageRefresh">
                                <i class="fa fa-undo"></i>
                            </a>
                        </div>
                    </div>
                    <div class="box-content">
                        <div class="col-sm-12 col-md-5">
                            <label class="col-sm-12 col-md-2">기간</label>
                            <div class="input-group input-daterange" id="searchDtpicker">
                                <input type="text" class="form-control" id="searchDtStart">
                                <div class="input-group-addon"><strong> ~ </strong></div>
                                <input type="text" class="form-control" id="searchDtEnd">
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-7">
                            <label class="col-sm-12 col-md-2">조건검색</label>
                            <div class="col-sm-4 col-md-3">
                                <select name="selectType">
                                    <option value="">선택</option>
                                    <option value="3" selected>회원 아이디</option>
                                    <option value="15">회원 닉네임</option>
                                    <option value="4">고유번호</option>
                                </select>
                            </div>
                            <div class="col-sm-8 col-md-7">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="searchInput" placeholder="검색명" autocomplete="off">
                                    <span class="input-group-btn">
                                        <button type='button' class='btn btn-mint' id='searchList'>검색</button>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="table-responsive">
                    <span class="table-total">Total : ${result.pageResult.totalElements}</span>
                    <table class="table table-striped table-list table-custom">
                        <thead class="th-text-center">
                        <tr>
                            <th width="10%">고유번호</th>
                            <th width="20%">회원 아이디</th>
                            <th width="20%">메일</th>
                            <th width="15%">회원 닉네임</th>
                            <th width="10%">회원 타입</th>
                            <th width="15%">회원 가입 일자</th>
                        </tr>
                        </thead>
                        <tbody class="td-text-center">
                        <c:forEach var="item" items="${result.pageResult.content}" varStatus="status">
                            <tr name="list">
                                <td name="userId">${item.userNo}</td>
                                <td>${item.userId}</td>
                                <td>${item.userEmail}</td>
                                <td>${item.userName}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.userType == 0}">
                                            <label class="label label-plain">이메일</label>
                                        </c:when>
                                        <c:when test="${item.userType == 1}">
                                            <label class="label label-primary">페이스북</label>
                                        </c:when>
                                        <c:otherwise>
                                            <label class="label label-danger">구글</label>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><fmt:formatDate pattern = "yyyy-MM-dd HH:mm:ss"  value = "${item.createDt}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <form action="/manager/user" method="get" id="paginationForm">
            <input type="hidden" id="searchText" name="searchText" value="${result.searchText}"/>
            <input type="hidden" id="type" name="type" value="${result.type}"/>
            <input type="hidden" id="start" name="start" value="<fmt:formatDate pattern = "yyyy-MM-dd"  value = "${result.start}"/>"/>
            <input type="hidden" id="end" name="end" value="<fmt:formatDate pattern = "yyyy-MM-dd"  value = "${result.end}"/>"/>
            <c:if test="${result.pageResult.totalPages > 0}">
                <t:pagination></t:pagination>
            </c:if>
        </form>
    </jsp:body>
</t:template>
