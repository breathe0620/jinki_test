<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
  <jsp:attribute name="javascriptSrc">
    <script src="/js/manager/admin.js"></script>
  </jsp:attribute>
    <jsp:body>
        <div id="adminDetail">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>관리자 정보 상세
                                <small>회원 관리 / <a href="/manager/admin/">관리자 관리</a> / <b><a href="/manager/admin/${detailResult.adminNo}">관리자 정보 상세</a></b></small>
                            </h3>
                        </div>
                        <div class="title-right">
                            <button type="button" class="btn btn-sm btn-success" id="detailBtnList">목록</button>
                            <button type="button" class="btn btn-sm btn-warning" id="detailBtnUpdate">수정</button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <h3 class="form-section">관리자 정보</h3>
                </div>
                <div class="col-sm-12 col-md-12">
                    <div class="table-responsive">
                        <table class="table table-bordered table-condensed table-detail th-text-center">
                            <tr class="table-two-group-width">
                                <th>관리자 번호</th>
                                <td>${detailResult.adminNo}</td>
                                <th>관리자 ID</th>
                                <td>${detailResult.adminId}</td>
                            </tr>
                            <tr>
                                <th>관리자 이름</th>
                                <td>${detailResult.adminName}</td>
                                <th>전화번호</th>
                                <td>${detailResult.phoneNum}</td>
                            </tr>
                            <tr>
                                <th class="text-center">관리자 유형</th>
                                <td class="detail-label-fixed">
                                    <c:choose>
                                        <c:when test="${detailResult.adminType == 'S'}">
                                            <span class="label label-success ">슈퍼관리자</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-warning">일반관리자</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <th>관리자 권한</th>
                                <td>
                                    <div class="col-sm-6 col-md-6">
                                        <div class="custom-checkbox">
                                            <c:choose>
                                                <c:when test="${detailResult.adminPermission.meta == 'Y'}">
                                                    <input type="checkbox" id="detailMeta" name="detailMeta"
                                                           value="Y" checked disabled/>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="checkbox" id="detailMeta" name="detailMeta"
                                                           value="N" disabled/>
                                                </c:otherwise>
                                            </c:choose>
                                            <label for="detailMeta">메타관리</label>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-md-6">
                                        <div class="custom-checkbox">
                                            <c:choose>
                                                <c:when test="${detailResult.adminPermission.member == 'Y'}">
                                                    <input type="checkbox" id="detailMember" name="detailMember"
                                                           value="Y" checked disabled/>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="checkbox" id="detailMember" name="detailMember"
                                                           value="N" disabled/>
                                                </c:otherwise>
                                            </c:choose>
                                            <label for="detailMember">회원관리</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th class="text-center">등록일</th>
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
                                                    value="${detailResult.createDt}"/></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div id="adminUpdate" style="display:none;">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>관리자 정보 수정
                                <small>회원 관리 / <a href="/manager/admin/">관리자 관리</a> / <b><a href="/manager/admin/${detailResult.adminNo}">관리자 정보 수정</a></b></small>
                            </h3>
                        </div>
                        <div class="title-right">
                            <button type="button" class="btn btn-sm btn-danger" id="updateBtnClose">닫기</button>
                            <button type="button" class="btn btn-sm btn-mint" id="updateBtnSave">저장</button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <h3 class="form-section">관리자 정보</h3>
                </div>
                <div class="col-sm-12 col-md-12">
                    <form id="signupForm" data-parsley-validate>
                        <div class="table-responsive">
                            <table class="table table-bordered table-create th-text-center">
                                <tr class="table-two-group-width">
                                    <th>관리자 번호</th>
                                    <td>
                                        <input type="text" class="form-control" id="adminNo" name="adminNo"
                                               value="${detailResult.adminNo}" readonly>
                                    </td>
                                    <th>관리자 아이디</th>
                                    <td>
                                        <input type="text" class="form-control" id="adminId" name="adminId"
                                               value="${detailResult.adminId}" readonly>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation red"></i> 관리자 이름</th>
                                    <td class="input-parsley">
                                        <input type="text" class="form-control" id="adminName" name="adminName"
                                               value="${detailResult.adminName}" placeholder="이름을 입력하세요"
                                               data-parsley-required="true" data-parsley-trigger="change"
                                               data-parsley-minlength="2" autocomplete="off" maxlength="32">
                                    </td>
                                    <th><i class="fa fa-exclamation red"></i> 전화번호</th>
                                    <td class="input-parsley">
                                        <input type="number" class="form-control" id="phoneNum" name="phoneNum"
                                               value="${detailResult.phoneNum}" placeholder="전화번호를 입력하세요"
                                               data-parsley-required="true" data-parsley-trigger="change"
                                               data-parsley-type="digits" data-parsley-length="[10, 11]"
                                               autocomplete="off" maxlength="11">
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation red"></i> 비밀번호</th>
                                    <td>
                                        <button type="button" name="passwordChange"
                                                class="btn btn-default btn-block btn-chagne-pwd">비밀번호변경
                                        </button>
                                        <input type="password" class="form-control" id="adminPwd"
                                               name="adminPwd" style="display: none;"
                                               placeholder="비밀번호를 입력하세요 (영문+숫자 6자리 이상)"
                                               data-parsley-minlength="6"
                                               data-parsley-pattern="/^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/"
                                               autocomplete="off" maxlength="128">
                                    </td>
                                    <th><i class="fa fa-exclamation red"></i> 비밀번호 확인</th>
                                    <td>
                                        <input type="password" class="form-control" id="adminPwdConfirm"
                                               name="adminPwd" style="display: none;"
                                               placeholder="비밀번호를 다시 한번 입력하세요" data-parsley-minlength="6"
                                               data-parsley-equalto="#adminPwd"
                                               data-parsley-pattern="/^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/"
                                               autocomplete="off" maxlength="128">
                                    </td>
                                </tr>
                                <tr>
                                    <th class="text-center">관리자 유형</th>
                                    <td class="update-label-fixed">
                                        <c:choose>
                                            <c:when test="${detailResult.adminType == 'S'}">
                                                <span class="label label-success ">슈퍼관리자</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="label label-warning">일반관리자</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <th>관리자 권한</th>
                                    <td class="text-center">
                                        <div class="col-sm-6 col-md-6">
                                            <div class="custom-checkbox create-custom-checkbox">
                                                <input type="checkbox" id="updateMeta" name="updateMeta"/>
                                                <label for="updateMeta">메타관리</label>
                                            </div>
                                        </div>
                                        <div class="col-sm-6 col-md-6">
                                            <div class="custom-checkbox create-custom-checkbox">
                                                <input type="checkbox" id="updateMember" name="updateMember"/>
                                                <label for="updateMember">회원관리</label>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
