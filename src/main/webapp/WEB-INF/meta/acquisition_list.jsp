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
    <script src="/js/meta/acquisition.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>방송 정보 입수
                            <small>메타 관리 / <b>방송 정보 입수</b></small>
                        </h3>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="box">
                    <div class="box-title">
                        <h5><i class="fa fa-search"></i>방송정보 입수 리스트 조회</h5>
                        <div class="box-tools">
                            <a class="reload-link" id="pageRefresh">
                                <i class="fa fa-undo"></i>
                            </a>
                        </div>
                    </div>
                    <div class="box-content">
                        <label class="col-sm-12 col-md-2">프로그램명</label>
                        <div class="col-sm-4 col-md-3">
                            <select name="selectCorporator" id="corporatorCategory">
                                <option value="" selected>전체방송사</option>
                                console.log("${corporators} : "+${corporators});
                                <c:forEach var="item" items="${corporators}">
                                    <option value="${item}">${item}</option>
                                    console.log("${item} : "+${item});
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-5 col-md-3">
                            <select name="selectProgram" id="programCategory">
                                <option value="" selected>프로그램명</option>
                                <c:forEach var="item" items="${programs}">
                                    <option value="${item.programId}">${item.programTitle}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-3 col-md-3">
                            <select name="selectCategory" id="clipCategory">
                                <option value="">방송장르</option>
                                <option value="02">예능</option>
                                <option value="03">음악</option>
                            </select>
                        </div>


                        <label class="col-sm-12 col-md-2">조건검색</label>
                        <div class="col-sm-4 col-md-3">
                            <select name="selectType">
                                <option value="">선택</option>
                                <option value="14" selected>클립명</option>
                                <option value="2">클립고유번호</option>
                            </select>
                        </div>
                        <div class="col-sm-8 col-md-6">
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
                    <button type='button' class='btn btn-sm btn-mint table-button' id='btnTranscode'>입수 요청</button>
                    <table class="table table-striped table-list table-custom">
                        <thead class="th-text-center">
                        <tr>
                            <th width="3%"><input type="checkbox" id="allCheckBox"></th>
                            <th width="5%">고유번호</th>
                            <th width="7%">제공사</th>
                            <th width="10%">프로그램</th>
                            <th width="5%">방송장르</th>
                            <th width="36%">클립명</th>
                            <th width="6%">입수상태</th>
                            <th width="6%">변환상태</th>
                            <th width="10%">방송날짜</th>
                        </tr>
                        </thead>
                        <tbody class="text-center">
                        <c:forEach var="item" items="${result.pageResult.content}" varStatus="status">
                            <tr name="list">
                                <td>
                                    <input type="checkbox" name="transcode">
                                </td>
                                <td name="clipMetaId">${item.clipMetaId}</td>
                                <td>${item.corporator}</td>
                                <td>${fn:escapeXml(item.programTitle)}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.clipCategory == '02'}">
                                            <span class="label label-primary">예능</span>
                                        </c:when>
                                        <c:when test="${item.clipCategory == '03'}">
                                            <span class="label label-info">음악</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label">N/A</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-left">${item.clipTitle}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.uploadYn == 'N'}">
                                            <span class="label label-warning">미입수</span>
                                        </c:when>
                                        <c:when test="${item.uploadYn == 'W'}">
                                            <span class="label label-default">대기</span>
                                        </c:when>
                                        <c:when test="${item.uploadYn == 'P'}">
                                            <span class="label label-info">시작</span>
                                        </c:when>
                                        <c:when test="${item.uploadYn == 'Y'}">
                                            <span class="label label-success">성공</span>
                                        </c:when>
                                        <c:when test="${item.uploadYn == 'F'}">
                                            <span class="label label-danger">실패</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label">N/A</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.transcodeYn == 'N'}">
                                            <span class="label label-warning">미변환</span>
                                        </c:when>
                                        <c:when test="${item.transcodeYn == 'P'}">
                                            <span class="label label-info">시작</span>
                                        </c:when>
                                        <c:when test="${item.transcodeYn == 'Y'}">
                                            <span class="label label-success">성공</span>
                                        </c:when>
                                        <c:when test="${item.transcodeYn == 'F'}">
                                            <span class="label label-danger">실패</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label">N/A</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <fmt:parseDate var="broadDate" value="${item.broadDate}" pattern="yyyyMMdd" />
                                    <fmt:formatDate value="${broadDate}" pattern="yyyy-MM-dd" />
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <form action="/meta/acquisition" method="get" id="paginationForm">
            <input type="hidden" id="searchText" name="searchText" value="${result.searchText}"/>
            <input type="hidden" id="type" name="type" value="${result.type}"/>
            <input type="hidden" id="corporator" name="corporator" value="${result.corporator}"/>
            <input type="hidden" id="program" name="program" value="${result.program}"/>
            <input type="hidden" id="clipType" name="clipType" value="${result.clipType}"/>
            <c:if test="${result.pageResult.totalPages > 0}">
                <t:pagination></t:pagination>
            </c:if>
        </form>
    </jsp:body>
</t:template>
