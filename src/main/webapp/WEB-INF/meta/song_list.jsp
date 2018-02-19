<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
  <jsp:attribute name="javascriptSrc">
    <script src="/js/pagination.js"></script>
    <script src="/js/search-filter.js"></script>
    <script src="/js/meta/song.js"></script>
  </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>곡 메타 관리
                            <small>메타 관리 / <b><a href="/meta/song">곡 메타 관리</a></b></small>
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
                        <h5><i class="fa fa-search"></i>곡 리스트 조회</h5>
                        <div class="box-tools">
                            <a class="reload-link" id="pageRefresh">
                                <i class="fa fa-undo"></i>
                            </a>
                        </div>
                    </div>
                    <div class="box-content">
                        <div class="col-sm-12 col-md-5">
                            <label class="col-sm-12 col-md-2" for="searchDtStart">기간</label>
                            <div class="input-group input-daterange">
                                <input type="text" class="form-control" id="searchDtStart">
                                <div class="input-group-addon"> ~ </div>
                                <input type="text" class="form-control" id="searchDtEnd">
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-7">
                            <label class="control-label col-sm-12 col-md-2">조건검색</label>
                            <div class="col-sm-4 col-md-3">
                                <select name="selectType">
                                    <option value="">선택</option>
                                    <option value="13" selected>곡명</option>
                                    <option value="11">아티스트명</option>
                                    <option value="1">곡 MID</option>
                                </select>
                            </div>
                            <div class="col-sm-8 col-md-7">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="searchInput" placeholder="검색명">
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-mint" id='searchList'>검색</button>
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
                            <th width="10%">곡ID</th>
                            <th width="10%">곡MID</th>
                            <th width="40%">곡명</th>
                            <%--<th width="20%">곡명 <span class="red">(영문)</span></th>--%>
                            <th width="20%">아티스트명</th>
                            <th width="15%">등록일</th>
                        </tr>
                        </thead>
                        <tbody class="td-text-center">
                        <c:forEach var="item" items="${result.pageResult.content}">
                            <tr name="list">
                                <td name="songId">${item.songId}</td>
                                <td>${item.originId}</td>
                                <td>${fn:escapeXml(item.songName)}</td>
                                <%--<td>${fn:escapeXml(item.songNameEn)}</td>--%>
                                <td>
                                    <c:set var="name" value="${fn:split(item.artistName,',')}"/>
                                    <c:choose>
                                        <c:when test="${fn:length(name) > 2}">
                                            ${name[0]}, ${name[1]} 외 ${fn:length(name) - 2}명
                                        </c:when>
                                        <c:when test="${fn:length(name) == 2}">
                                            ${name[0]}, ${name[1]}
                                        </c:when>
                                        <c:otherwise>
                                            ${name[0]}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.createDt}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <form action="/meta/song" method="get" id="paginationForm">
            <input type="hidden" id="searchText" name="searchText" value="${result.searchText}"/>
            <input type="hidden" id="type" name="type" value="${result.type}"/>
            <input type="hidden" id="start" name="start"
                   value="<fmt:formatDate pattern = "yyyy-MM-dd"  value = "${result.start}"/>"/>
            <input type="hidden" id="end" name="end"
                   value="<fmt:formatDate pattern = "yyyy-MM-dd"  value = "${result.end}"/>"/>
            <c:if test="${result.pageResult.totalPages > 0}">
                <t:pagination></t:pagination>
            </c:if>
        </form>
    </jsp:body>
</t:template>
