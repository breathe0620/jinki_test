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
    <script src="/js/meta/program.js"></script>
</jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>프로그램 메타 관리
                            <small>메타 관리 / <b><a href="/meta/program">프로그램 메타 관리</a></b></small>
                        </h3>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="box">
                    <div class="box-title">
                        <h5><i class="fa fa-search"></i>프로그램 메타 조회</h5>
                        <div class="box-tools">
                            <a class="reload-link" id="pageRefresh">
                                <i class="fa fa-undo"></i>
                            </a>
                        </div>
                    </div>
                    <div class="box-content">
                        <div class="col-sm-12 col-md-12">
                            <label class="col-sm-12 col-md-1">조건검색</label>
                            <div class="col-sm-6 col-md-2">
                                <select name="selectProgramSearchType" id="selectProgramSearchType">
                                    <option value="0">방송사 & 프로그램 선택</option>
                                    <option value="1">프로그램명 입력</option>
                                </select>
                            </div>
                            <div class="col-sm-6 col-md-2">
                                <select name="selectCorporator" id="corporatorCategory">
                                    <option value="">전체방송사</option>
                                    <c:forEach var="item" items="${corporator}">
                                        <option value="${item}">${item}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-6 col-md-3">
                                <select name="selectProgram" id="programCategory">
                                    <option value="">프로그램명</option>
                                </select>
                            </div>
                            <div class="col-sm-6 col-md-3">
                                <input type="hidden" name="selectType" value="16"/>
                                <div class="input-group">
                                    <input type="text" class="form-control" id="searchInput" placeholder="검색명" autocomplete="off" disabled>
                                    <span class="input-group-btn"><button type='button' class='btn btn-primary' id='searchList'>검색</button></span>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-12 col-md-12">
                            <form id="programForm" class="editorForm" data-parsley-validate>
                                <label class="col-sm-12 col-md-1">수정</label>
                                <div class="col-sm-6 col-md-2">
                                    <select name="selectCorporator" id="editorCorporator" disabled>
                                        <option value="">전체방송사</option>
                                        <c:forEach var="item" items="${corporator}">
                                            <option value="${item}">${item}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-6 col-md-2 tableParsley">
                                    <input type="hidden" name="programInput" id="editorProgramId" value="">
                                    <input type="text" name="programInput" class="form-control"
                                           id="editorProgramName" placeholder="프로그램명" autocomplete="off"
                                           readonly>
                                </div>
                                <div class="col-sm-12 col-md-3" style="padding-top: 0 !important;">
                                    <input type="text" name="programInput" class="form-control"
                                           id="editorProgramNameEn" placeholder="프로그램명(EN)" autocomplete="off">
                                </div>
                                <div class="col-sm-10 col-md-3 nation_select">
                                    <select id="editorNation" multiple data-placeholder="국가검색">
                                    </select>
                                </div>
                                <div class="col-sm-2 col-md-1">
                                    <button type="button" name="btnAgency" id="btnProgramUpdate" class="btn btn-sm btn-warning" disabled>수정</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="table-responsive" style="background-color:#fff;">
                    <span class="table-total"><strong>Total : ${result.pageResult.totalElements}</strong></span>
                    <table class="table table-striped table-list table-custom">
                        <thead class="th-text-center">
                        <tr>
                            <th width="15%">고유번호</th>
                            <th width="15%">제공사</th>
                            <th width="28%">프로그램명</th>
                            <th width="28%">프로그램명 <span class="red">(영문)</span></th>
                            <th width="14%">국가</th>
                            <%--<td width="5%"></td>--%>
                        </tr>
                        </thead>
                        <tbody class="text-center">
                        <c:forEach var="item" items="${result.pageResult.content}" varStatus="status">
                            <tr name="list">
                                <td name="programId">${item.programId}</td>
                                <td name="programCorporator">${item.corporator}</td>
                                <td name="programTitle">${item.programTitle}</td>
                                <td name="programTitleEn">${item.programTitleEn}</td>
                                <td name="limitNation">${item.limitnation}</td>
                                <%--<td>--%>
                                    <%--<button type="button" class="btn btn-xs btn-danger btn-delete" name="btnDelete"><i class="fa fa-times"></i></button>--%>
                                <%--</td>--%>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <form action="/meta/program" method="get" id="paginationForm">
            <input type="hidden" id="searchText" name="searchText" value="${result.searchText}"/>
            <input type="hidden" id="type" name="type" value="${result.type}"/>
            <input type="hidden" id="corporator" name="corporator" value="${result.corporator}"/>
            <input type="hidden" id="program" name="program" value="${result.program}"/>
            <c:if test="${result.pageResult.totalPages > 0}">
                <t:pagination></t:pagination>
            </c:if>
        </form>
    </jsp:body>
</t:template>