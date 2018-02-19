<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
    <jsp:attribute name="javascriptSrc">
        <link href="/vendor/DataTables/media/css/dataTables.bootstrap.css" rel="stylesheet">
        <script src="/vendor/DataTables/media/js/jquery.dataTables.js"></script>
        <script src="/vendor/DataTables/media/js/dataTables.bootstrap.min.js"></script>
        <script src="/vendor/smarteditor2-master/workspace/js/service/HuskyEZCreator.js"></script>
        <script src="/js/meta/archive.js"></script>
    </jsp:attribute>
    <jsp:body>

        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>아카이브 영상 입수
                            <small>메타 관리 / <b>아카이브 영상 입수</b></small>
                        </h3>
                    </div>
                </div>
                <div class="col-sm-12 col-md-6">
                    <div class="box float-e-margins">
                        <div class="box-title" style="padding: 4px 10px 2px;">
                            <h5>XML 영상 정보</h5>
                        </div>
                        <div class="box-content" style="padding: 0 !important;">
                            <textarea class="resultXML" style="width:100%; height: 350px; border: 0 !important;" readonly></textarea>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 col-md-6 form-archive">
                    <form id="formArchive" data-parsley-validate>
                        <div class="col-md-6 col-sm-6">
                            <label class="control-label" for="editorProviderType">제공사 유형</label>
                            <select class="form-control" name="editorSelect" id="editorProviderType" data-parsley-required="true" data-parsley-trigger="change">
                                <option value="">선택</option>
                                <option value="01">Archive</option>
                                <option value="02">Agency</option>
                            </select>
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <label class="control-label" for="editorClipCategory">클립 카테고리</label>
                            <select class="form-control" name="editorSelect" id="editorClipCategory" data-parsley-required="true" data-parsley-trigger="change">
                                <option value="">선택</option>
                                <option value="02">예능</option>
                                <option value="03">음악</option>
                            </select>
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <label class="control-label" for="editorPlaytime">플레이시간(초)</label>
                            <input type="number" min="10" class="form-control" value="" id="editorPlaytime" placeholder="초(Second)로 계산해서 입력해주세요.">
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <label class="control-label" for="editorBroadDate">방송일</label>
                            <div class='input-group date' id='broadDtPicker' style="width: 100% !important;">
                                <input type='text' class="form-control" id="editorBroadDate" value="" placeholder="방송일" autocomplete="off"/>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <label class="control-label" for="editorClipTitle">클립명 <span class="langHighlight">(한글)</span></label>
                            <input type="text" class="form-control" value="" id="editorClipTitle" placeholder="클립명 (한글)" data-parsley-required="true" data-parsley-trigger="change">
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <label class="control-label" for="editorClipTitleEn">클립명 <span class="langHighlight">(영문)</span></label>
                            <input type="text" class="form-control" value="" id="editorClipTitleEn" placeholder="클립명 (영문)">
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <label class="control-label" for="editorImgUpload">이미지 업로드</label>
                            <input type="file" class="form-control" name="editorSelect" id="editorImgUpload" accept="image/*"/>
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <label class="control-label" for="editorVideoUpload">영상 업로드</label>
                            <input type="file" class="form-control" name="editorSelect" id="editorVideoUpload" accept="video/*"/>
                        </div>
                        <div class="col-md-6 col-sm-6 divSelectCorporator" style="display: none;">
                            <label class="control-label" for="editorCorporator">방송사</label>
                            <select class="form-control" name="editorSelect" id="editorCorporator" data-parsley-required="true" data-parsley-trigger="change">
                                <option value="">방송사 선택</option>
                                <c:forEach var="item" items="${corporators}">
                                    <option value="${item}">${item}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6 col-sm-6 divSelectCorporator" style="display: none;">
                            <label class="control-label" for="editorProgram">프로그램</label>
                            <select class="form-control" name="editorSelect" id="editorProgram" data-parsley-required="true" data-parsley-trigger="change">
                                <option value="">프로그램 선택</option>
                            </select>
                        </div>
                        <div class="col-md-6 col-sm-6 divSelectAgency" style="display: none;">
                            <label class="control-label" for="editorAgency">소속사</label>
                            <select class="form-control" name="editorSelect" id="editorAgency" data-parsley-required="true" data-parsley-trigger="change">
                                <option value="">소속사 선택</option>
                                <c:forEach var="item" items="${agency}">
                                    <option value="${item.agencyId}">${item.agencyName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-12 col-md-12">
                            <div class="pull-right">
                                <button type="button" class="btn btn-info" id="btnEditor">등록</button>
                            </div>
                        </div>
                    </form>
                </div>
                <hr>
                <div class="col-md-12 col-sm-12">
                    <div class="table-responsive">
                        <table class="table datatable-list table-custom" id="resultTable">
                            <thead class="th-text-center">
                            <tr>
                                <th width="15%">CODE</th>
                                <th width="20%">제공사</th>
                                <th width="40%">프로그램명</th>
                                <th width="15%">방송일</th>
                                <th width="10%">영상</th>
                            </tr>
                            </thead>
                            <tbody class="td-text-center">
                            <c:forEach var="item" items="${list}">
                                <tr name="list">
                                    <td>
                                        <input type="hidden" name="originXML" value="${item.xml}">
                                        <input type="hidden" name="originVideo" value="${item.video}">
                                            ${item.code}
                                    </td>
                                    <td>${item.corporator}</td>
                                    <td>${item.name}</td>
                                    <td>
                                        <fmt:parseDate var="broadDate" value="${item.date}" pattern="yyyyMMdd" />
                                        <fmt:formatDate value="${broadDate}" pattern="yyyy-MM-dd" />
                                    </td>
                                    <td>
                                        <a name="videoView" class="btn btn-xxs btn-info" onclick="video('${item.video}')"><strong>영상 보기</strong></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
