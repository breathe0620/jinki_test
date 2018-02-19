<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
    <jsp:attribute name="javascriptSrc">
        <script src="/vendor/smarteditor2-master/workspace/js/service/HuskyEZCreator.js"></script>
        <script src="/js/service/notice.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="detailNotice">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>공지사항 상세
                                <small>서비스 관리 / <a href="/service/notices">공지사항 관리</a> / <b><a href="/service/notice/${detail.data.noticeNo}">공지사항 상세</a></b></small>
                            </h3>
                        </div>
                        <div class="title-right">
                            <button type="button" class="btn btn-sm btn-success" id="detailBtnList">목록</button>
                            <button type="button" class="btn btn-sm btn-danger"  id="btnDelete">삭제</button>
                            <button type="button" class="btn btn-sm btn-warning" id="detailBtnUpdate">수정</button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <h3 class="form-section">공지사항 정보</h3>
                </div>
                <div class="col-sm-12 col-md-12">
                    <div class="table-responsive">
                        <table class="table table-bordered table-condensed table-detail th-text-center">
                            <tr class="table-two-group-width">
                                <th>고유번호</th>
                                <td>
                                    <span name="detailResult" id="detailNoticeId">${detail.data.noticeNo}</span>
                                </td>
                                <th>등록일</th>
                                <td>
                                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detail.data.regDt}"/>
                                </td>
                            </tr>
                            <tr>
                                <th>카테고리</th>
                                <td class="detail-label-fixed">
                                    <input type="hidden" id="detailCategory" value="${detail.data.category}"/>
                                    <c:choose>
                                        <c:when test="${detail.data.category == '00'}">
                                            <span name="detailResult" class="label label-danger">공지사항</span>
                                        </c:when>
                                        <c:when test="${detail.data.category == '01'}">
                                            <span name="detailResult" class="label label-primary">업데이트</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span name="detailResult" class="label label-default">미입력</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <th>중요도</th>
                                <td class="text-center">
                                    <input type="hidden" id="hiddenDetailIsImportant"
                                           value="${detail.data.isImportant}"/>
                                    <div class="custom-checkbox update-custom-checkbox">
                                        <c:choose>
                                            <c:when test="${detail.data.isImportant == 'Y'}">
                                                <input type="checkbox" id="detailIsImportant" name="detailIsImportant" disabled checked/>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="checkbox" id="detailIsImportant" name="detailIsImportant" disabled/>
                                            </c:otherwise>
                                        </c:choose>
                                        <label for="detailIsImportant"></label>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <div class="alert alert-success">
                        <li>언어별 Tab을 누르시게 되면 해당 언어의 제목과 내용을 보실수 있습니다.</li>
                        <li>
                            <strong><span class="red">모바일 화면</span>으로 게시글을 보는 방법</strong>
                            <ul>
                                <li>
                                    <a href="http://troy.labs.daum.net/" target="_blank">http://troy.labs.daum.net/</a> 접속 -> https://dev.api.mubeat.tv/view/notice 입력 -> 모바일 기기 선택
                                </li>
                            </ul>
                        </li>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12" style="margin-bottom: 15px;">
                    <ul class="nav nav-tabs">
                        <c:choose>
                            <c:when test="${detail.data.langType == 'EN'}">
                                <li class="active" name="detailLangType"><a data-toggle="tab">영문</a></li>
                                <li class="" name="detailLangType"><a data-toggle="tab">한글</a></li>
                            </c:when>
                            <c:when test="${detail.data.langType == 'KO'}">
                                <li class="" name="detailLangType"><a data-toggle="tab">영문</a></li>
                                <li class="active" name="detailLangType"><a data-toggle="tab">한글</a></li>
                            </c:when>
                        </c:choose>
                    </ul>
                </div>
                <div class="col-sm-12 col-md-12">
                    <div class="table-responsive">
                        <table class="table table-bordered table-condensed table-detail th-text-center">
                            <tr>
                                <th width="20%">공지사항 제목</th>
                                <td colspan="3">
                                    <span id="detailSubject">${fn:escapeXml(detail.data.subject)}</span>
                                </td>
                            </tr>
                            <tr>
                                <th>내용</th>
                                <td colspan="3">
                                    <div class="detailTextEditor" id="detailContent">
                                            ${detail.data.content}
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div id="updateNotice" style="display: none;">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>공지사항 수정
                                <small>서비스 관리 / <a href="/service/notices">공지사항 관리</a> / <b><a href="/service/notice/${detail.data.noticeNo}">공지사항 수정</a></b></small>
                            </h3>
                        </div>
                        <div class="title-right">
                            <button type="button" class="btn btn-sm btn-danger" id="updateBtnClose">닫기</button>
                            <button type="button" class="btn btn-sm btn-mint" id="updateBtnSave">저장</button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <h3 class="form-section">공지사항 정보</h3>
                </div>
                <form id="formNotice" data-parsley-validate>
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-bordered table-create th-text-center">
                                <tr class="table-two-group-width">
                                    <th>고유번호</th>
                                    <td>
                                        <input type="text" class="form-control" value="${detail.data.noticeNo}" readonly>
                                    </td>
                                    <th>등록일</th>
                                    <td>
                                        <input type="text" class="form-control" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detail.data.regDt}"/>" readonly>
                                    </td>
                                </tr>
                                <tr>
                                    <th>카테고리</th>
                                    <td class="input-parsley">
                                        <select id="updateCategory" data-parsley-required="true">
                                            <option value="">선택</option>
                                            <option value="00">공지사항</option>
                                            <option value="01">업데이트</option>
                                        </select>
                                    </td>
                                    <th>중요도</th>
                                    <td class="text-center detail-label-fixed">
                                        <div class="custom-checkbox update-custom-checkbox">
                                            <input type="checkbox" id="updateIsImportant" name="updateIsImportant"/>
                                            <label for="updateIsImportant"></label>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="alert alert-success">
                            <li>언어별 공지사항 제목, 내용 입력란은 별개로 저장됩니다.</li>
                            <li>언어별 Tab을 이동하게 될 경우 <span class="red">해당 내용이 저장되지 않으니 유의</span>하시길 바랍니다.</li>
                        </div>
                    </div>
                    <div class="col-md-12" style="margin-bottom: 15px;">
                        <ul class="nav nav-tabs">
                            <li class="active" name="updateLangType" id="updateLangTypeEn"><a data-toggle="tab">영문</a></li>
                            <li class="" name="updateLangType" id="updateLangTypeKo"><a data-toggle="tab">한글</a></li>
                        </ul>
                    </div>
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-bordered table-create th-text-center">
                                <tr>
                                    <th width="20%">공지사항 제목</th>
                                    <td colspan="3" class="input-parsley">
                                        <input type="text" id="updateSubject" class="form-control" maxlength="256"
                                               data-parsley-required="true" placeholder="공지사항 제목"
                                               autocomplete="off" value="">
                                    </td>
                                </tr>
                                <tr>
                                    <th>내용</th>
                                    <td colspan="3">
                                        <textarea id="updateContent" style="width: 100%; height: 400px;"></textarea>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </jsp:body>
</t:template>
