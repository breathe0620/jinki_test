<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-3">
<jsp:attribute name="javascriptSrc">
    <script src="/js/meta/clip.js"></script>
    <script src="/js/pagination.js"></script>
    <script src="/js/search-filter.js"></script>
</jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>클립 메타 관리
                            <small>메타 관리 / <b><a href="/meta/clip">클립 메타 관리</a></b></small>
                        </h3>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="box">
                    <div class="box-title">
                        <h5><i class="fa fa-search"></i>클립 메타 조회</h5>
                        <div class="box-tools">
                            <a class="reload-link" id="pageRefresh">
                                <i class="fa fa-undo"></i>
                            </a>
                        </div>
                    </div>
                    <div class="box-content">
                        <div class="col-sm-12 col-md-12">
                            <label class="col-sm-12 col-md-1">프로그램명</label>
                            <div class="col-sm-4 col-md-2">
                                <select name="selectCorporator" id="corporatorCategory">
                                    <option value="">전체방송사</option>
                                    <c:forEach var="item" items="${corporator}">
                                        <option value="${item}">${item}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-8 col-md-3">
                                <select name="selectProgram" id="programCategory">
                                    <option value="">프로그램명</option>
                                </select>
                            </div>
                            <div class="col-sm-4 col-md-2">
                                <select name="selectCategory" id="clipCategory">
                                    <option value="">방송장르</option>
                                    <option value="02">예능</option>
                                    <option value="03">음악</option>
                                </select>
                            </div>
                            <div class="col-sm-4 col-md-2">
                                <select name="selectCategory" id="clipClassifyCategory">
                                    <option value="">클립분류</option>
                                    <option value="0">All클립</option>
                                    <c:forEach var="item" items="${clipDetailCategory}">
                                        <option value="${item.clipClassify}">${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-4 col-md-2">
                                <select name="selectCategory" id="enableCategory">
                                    <option value="">노출상태</option>
                                    <option value="Y">노출</option>
                                    <option value="N">미노출</option>
                                    <option value="T">폐기</option>
                                    <option value="R">보류</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-12">
                            <label class="col-sm-12 col-md-1">조건검색</label>
                            <div class="col-sm-4 col-md-2">
                                <select name="selectType">
                                    <option value="">선택</option>
                                    <option value="14" selected>클립명</option>
                                    <option value="2">클립고유번호</option>
                                </select>
                            </div>
                            <div class="col-sm-8 col-md-9">
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
        </div>

        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="table-responsive">
                    <span class="table-total">Total : ${result.pageResult.totalElements}</span>
                    <c:if test="${cookie.TYPE.value == 'S'}">
                        <button type="button" class="btn btn-sm btn-mint table-button" id="btnExcel" style="float:right;">엑셀 다운</button>
                        <div class="formExcel" style="display: none"></div>
                    </c:if>
                    <table class="table table-striped table-list table-custom">
                        <thead class="th-text-center">
                        <tr>
                            <th width="5%">고유번호</th>
                            <th width="5%">제공사</th>
                            <th width="10%">프로그램</th>
                            <th width="5%">방송장르</th>
                            <th width="30%" class="table-list-th-fixed">클립명</th>
                            <th width="5%">클립분류</th>
                            <th width="5%">아티스트</th>
                            <th width="5%">곡</th>
                            <th width="10%">수정일</th>
                            <th width="5%">노출여부</th>
                        </tr>
                        </thead>
                        <tbody class="text-center">
                        <c:forEach var="item" items="${result.pageResult.content}" varStatus="status">
                            <tr name="list">
                                <td name="clipMetaId">${item.clipMetaId}</td>
                                <td>${item.corporator}</td>
                                <td class="table-list-td-fixed">${item.programTitle}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.clipCategory == '02'}">
                                            <span class="label label-primary">예능</span>
                                        </c:when>
                                        <c:when test="${item.clipCategory == '03'}">
                                            <span class="label label-info">음악</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-danger">미입력</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-left table-list-td-fixed">${fn:escapeXml(item.clipTitle)}</td>
                                <td>
                                    <c:forEach var="clipDetailCategory" items="${clipDetailCategory}">
                                        <c:if test="${item.clipClassify == clipDetailCategory.clipClassify}">
                                            <c:choose>
                                                <c:when test="${item.clipClassify == '01'}">
                                                    <span class="label label-category-01">${clipDetailCategory.name}</span>
                                                </c:when>
                                                <c:when test="${item.clipClassify == '02'}">
                                                    <span class="label label-category-02">${clipDetailCategory.name}</span>
                                                </c:when>
                                                <c:when test="${item.clipClassify == '03'}">
                                                    <span class="label label-category-03">${clipDetailCategory.name}</span>
                                                </c:when>
                                                <c:when test="${item.clipClassify == '04'}">
                                                    <span class="label label-category-04">${clipDetailCategory.name}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-category-05">${clipDetailCategory.name}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.artistRegist != '0'}">
                                            <span class="label label-success">연결</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-danger">미연결</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.songRegist != '0'}">
                                            <span class="label label-success">연결</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-danger">미연결</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><fmt:formatDate pattern = "yyyy-MM-dd HH:mm"  value = "${item.modifyDt}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.enableYn == 'Y'}">
                                            <span class="label label-success">노출</span>
                                        </c:when>
                                        <c:when test="${item.enableYn == 'N'}">
                                            <span class="label label-danger">미노출</span>
                                        </c:when>
                                        <c:when test="${item.enableYn == 'R'}">
                                            <span class="label label-warning">보류</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-default">폐기</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <form action="/meta/clip" method="get" id="paginationForm">
            <input type="hidden" id="searchText" name="searchText" value="${result.searchText}"/>
            <input type="hidden" id="type" name="type" value="${result.type}"/>
            <input type="hidden" id="corporator" name="corporator" value="${result.corporator}"/>
            <input type="hidden" id="program" name="program" value="${result.program}"/>
            <input type="hidden" id="clipType" name="clipType" value="${result.clipType}"/>
            <input type="hidden" id="enableState" name="enableState" value="${result.enableState}"/>
            <input type="hidden" id="clipClassify" name="clipClassify" value="${result.clipClassify}"/>
            <c:if test="${result.pageResult.totalPages > 0}">
                <t:pagination></t:pagination>
            </c:if>
        </form>
    </jsp:body>
</t:template>
