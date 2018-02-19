<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-2">
  <jsp:attribute name="javascriptSrc">
    <script src="/js/pagination.js"></script>
    <script src="/js/search-filter.js"></script>
    <script src="/js/meta/artist.js"></script>
    <script src="/js/meta/agency.js"></script>
  </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>아티스트 메타 관리
                            <small>메타 관리 / <b><a href="/meta/artist">아티스트 메타 관리</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" class="btn btn-sm btn-info" id="btnCreate">아티스트 등록</button>
                        <button type='button' class='btn btn-sm btn-warning' id='btnAagency'>
                            소속사 등록
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="box">
                    <div class="box-title">
                        <h5><i class="fa fa-search"></i>아티스트 리스트 조회</h5>
                        <div class="box-tools">
                            <a class="reload-link" id="pageRefresh">
                                <i class="fa fa-undo"></i>
                            </a>
                        </div>
                    </div>
                    <div class="box-content">
                        <div class="col-sm-12 col-md-5">
                            <label class="col-sm-12 col-md-2">기간</label>
                            <div class="input-group input-daterange">
                                <input type="text" class="form-control" id="searchDtStart">
                                <div class="input-group-addon"><strong> ~ </strong></div>
                                <input type="text" class="form-control" id="searchDtEnd">
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-7">
                            <label class="control-label col-sm-12 col-md-2">조건검색</label>
                            <div class="col-sm-4 col-md-3">
                                <select name="selectType">
                                    <option value="">선택</option>
                                    <option value="11" selected>아티스트명</option>
                                    <option value="1">아티스트MID</option>
                                    <option value="5">소속사ID</option>
                                </select>
                            </div>
                            <div class="col-sm-8 col-md-7">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="searchInput" placeholder="검색명">
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-mint" id="searchList">검색</button>
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
                            <th width="9%">아티스트ID</th>
                            <th width="10%">아티스트MID</th>
                            <th width="33%">아티스트명</th>
                            <th width="33%">아티스트명 <span class="red">(영문)</span></th>
                            <th width="15%">등록일</th>
                        </tr>
                        </thead>
                        <tbody class="td-text-center">
                        <c:forEach var="item" items="${result.pageResult.content}">
                            <tr name="list">
                                <td name="artistId">${item.artistId}</td>
                                <td>${item.originId}</td>
                                <td>${fn:escapeXml(item.artistName)}</td>
                                <td>${fn:escapeXml(item.artistNameEn)}</td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.createDt}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <form action="/meta/artist" method="get" id="paginationForm">
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

        <div id="divModal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-agency">
                <div class="modal-content border-radius-zero">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">소속사 등록</h4>
                    </div>
                    <div class="modal-body">
                        <div class="box">
                            <form id="agencyForm" data-parsley-validate>
                                <div class="box-title" style="padding: 6px;">
                                    <h5 style="margin-bottom: 4px;"><i class="fa fa-search"></i>소속사</h5>
                                </div>
                                <div class="box-content">
                                    <label class="col-sm-2 col-md-2" for="modalAgencySearch">검색</label>
                                    <div class="col-sm-10 col-md-10">
                                        <input type="text" class="form-control" id="modalAgencySearch"
                                               placeholder="소속사명 검색">
                                    </div>
                                    <label class="col-sm-2 col-md-2" for="editorAgencyName">등록/수정</label>
                                    <div class="col-sm-5 col-md-5">
                                        <input type="hidden" name="agencyInput" id="editorAgencyId" value="">
                                        <input type="text" name="agencyInput" class="form-control"
                                               id="editorAgencyName" data-parsley-maxlength="32"
                                               placeholder="소속사명" maxlength="32" data-parsley-required="true"
                                               data-parsley-trigger="change">
                                    </div>
                                    <div class="col-sm-5 col-md-5 input-group">
                                        <input type="text" name="agencyInput" class="form-control"
                                               id="editorAgencyNameEn" data-parsley-maxlength="64"
                                               maxlength="64" placeholder="소속사명(EN)">
                                        <span class="input-group-btn">
                                            <button type="button" name="btnAgency" id="btnAgencyCreate"
                                                    class="btn btn-info">추가</button>
                                            <button type="button" name="btnAgency" id="btnAgencyUpdate"
                                                    class="btn btn-warning" style="display: none;">수정</button>
                                        </span>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="row" id="agencyTable">
                            <div class="col-sm-12 col-md-12">
                                <div class="table-responsive" style="height:350px; overflow-x:auto; overflow-y:auto;">
                                    <table class="table table-list table-custom table-agency">
                                        <thead class="th-text-center">
                                        <tr>
                                            <th width="10%" class="text-center">ID</th>
                                            <th width="45%" class="text-center">소속사명 <span
                                                    class="langHighlight">(한글)</span></th>
                                            <th width="45%" class="text-center">소속사명 <span
                                                    class="langHighlight">(영문)</span></th>
                                        </tr>
                                        </thead>
                                        <tbody id="tableResult"></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-agency-pagination">
                        <t:agencyPagination></t:agencyPagination>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
