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
    <script src="/js/service/notice.js"></script>
</jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>공지사항 관리
                            <small>서비스 관리 / <b><a href="/service/notices">공지사항 관리</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" class="btn btn-sm btn-info table-button" id="btnCreate">등록</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="box">
                    <div class="box-title">
                        <h5><i class="fa fa-search"></i>공지사항 리스트 조회</h5>
                        <div class="box-tools">
                            <a class="reload-link" id="pageRefresh">
                                <i class="fa fa-undo"></i>
                            </a>
                        </div>
                    </div>
                    <div class="box-content">
                        <label class="col-sm-12 col-md-2">조건검색</label>
                        <div class="col-sm-4 col-md-3">
                            <select name="selectType">
                                <option value="">선택</option>
                                <option value="17" selected>공지사항 제목</option>
                                <option value="18">공지사항 내용</option>
                            </select>
                        </div>
                        <div class="col-sm-8 col-md-7">
                            <div class="input-group">
                                <input type="text" class="form-control" id="searchInput" placeholder="검색명">
                                <span class="input-group-btn">
                                    <button type='button' class='btn btn-mint' id='searchList'>검색</button>
                                </span>
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
                            <th width="8%">고유번호</th>
                            <th width="8%">중요도</th>
                            <th width="8%">카테고리</th>
                            <th width="30%">제목 <span class="red">(영문)</span></th>
                            <th width="30%">제목</th>
                            <th width="10%">등록일</th>
                        </tr>
                        </thead>
                        <tbody class="td-text-center">
                        <c:forEach var="item" items="${result.pageResult.content}" varStatus="status">
                            <tr name="list">
                                <td name="noticeId">${item.noticeNo}</td>
                                <td>
                                    <c:if test="${item.isImportant == 'Y'}">
                                        <i class="fa fa-star"></i>
                                    </c:if>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.category == '00'}">
                                            <label class="label label-danger">공지사항</label>
                                        </c:when>
                                        <c:when test="${item.category == '01'}">
                                            <label class="label label-primary">업데이트</label>
                                        </c:when>
                                        <c:otherwise>
                                            <label>N/A</label>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td name="noticeSubjectEn">${fn:escapeXml(item.subjectEn)}</td>
                                <td name="noticeSubject">${fn:escapeXml(item.subject)}</td>
                                <td><fmt:formatDate pattern = "yyyy-MM-dd HH:mm:ss"  value = "${item.regDt}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <form action="/service/notices" method="get" id="paginationForm">
            <input type="hidden" id="searchText" name="searchText" value="${result.searchText}"/>
            <input type="hidden" id="type" name="type" value="${result.type}"/>
            <c:if test="${result.pageResult.totalPages > 0}">
                <t:pagination></t:pagination>
            </c:if>
        </form>
    </jsp:body>
</t:template>
