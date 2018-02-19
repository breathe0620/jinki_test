<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
    <jsp:attribute name="javascriptSrc">
        <script src="/vendor/smarteditor2-master/workspace/js/service/HuskyEZCreator.js"></script>
        <script src="/js/service/faq.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>FAQ 등록
                            <small>서비스 관리 / <a href="/service/faqs">FAQ 관리</a> / <b><a href="/service/faq">FAQ 등록</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" class="btn btn-sm btn-danger" id="createBtnClose">닫기</button>
                        <button type="button" class="btn btn-sm btn-mint" id="createBtnSave">저장</button>
                    </div>
                </div>
            </div>
            <div class="col-sm-12 col-md-12">
                <h3 class="form-section">FAQ 정보</h3>
            </div>
            <form id="formFaq" data-parsley-validate>
                <div class="col-sm-12 col-md-12">
                    <div class="table-responsive">
                        <table class="table table-bordered table-create th-text-center">
                            <tr class="table-two-group-width">
                                <th>고유번호</th>
                                <td>
                                    <input type="text" class="form-control" placeholder="자동생성" readonly>
                                </td>
                                <th>등록일</th>
                                <td>
                                    <input type="text" class="form-control" placeholder="자동생성" readonly>
                                </td>
                            </tr>
                            <tr>
                                <th>카테고리</th>
                                <td class="input-parsley">
                                    <select id="createCategory" data-parsley-required="true">
                                        <option value="" selected>선택</option>
                                        <option value="00">회원정보</option>
                                        <option value="01">결제/해지</option>
                                        <option value="02">플레이어</option>
                                        <option value="03">이용안내</option>
                                        <option value="04">기타</option>
                                    </select>
                                </td>
                                <th>중요도</th>
                                <td class="text-center">
                                    <div class="custom-checkbox create-custom-checkbox">
                                        <input type="checkbox" id="createIsImportant" name="createIsImportant"/>
                                        <label for="createIsImportant"></label>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <div class="alert alert-success">
                        <li>언어별 공지사항 제목, 내용 입력란은 별개로 저장됩니다.</li>
                        <li>언어별 Tab을 이동하게 될 경우 <span class="langHighlight">해당 내용이 저장되지 않으니 유의</span>하시길 바랍니다.</li>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12" style="margin-bottom: 15px;">
                    <ul class="nav nav-tabs">
                        <li class="active" name="createLangType"><a data-toggle="tab">영문</a></li>
                        <li class="" name="createLangType"><a data-toggle="tab">한글</a></li>
                    </ul>
                </div>
                <div class="col-sm-12 col-md-12">
                    <div class="table-responsive">
                        <table class="table table-bordered table-create th-text-center">
                            <tr>
                                <th width="20%">제목</th>
                                <td colspan="3" class="input-parsley">
                                    <input type="text" id="createSubject" class="form-control" maxlength="256"
                                           data-parsley-required="true" placeholder="FAQ 제목" autocomplete="off">
                                </td>
                            </tr>
                            <tr>
                                <th>내용</th>
                                <td colspan="3">
                                    <textarea id="createContent" style="width: 100%; height: 400px;"></textarea>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </form>
        </div>
    </jsp:body>
</t:template>
