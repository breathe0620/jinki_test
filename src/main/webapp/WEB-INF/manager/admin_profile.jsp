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
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>회원 정보 수정
                            <small><b><a href="/manager/admin/profile">회원 정보 수정</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" class="btn btn-sm btn-danger" id="profileBtnClose">닫기</button>
                        <button type="button" class="btn btn-sm btn-mint"   id="profileBtnSave">저장</button>
                    </div>
                </div>
            </div>
            <div class="col-sm-12 col-md-12">
                <h3 class="form-section">공지사항 정보</h3>
            </div>
            <div class="col-sm-12 col-md-12">
                <form id="signupForm" data-parsley-validate>
                    <div class="table-responsive">
                        <table class="table table-bordered table-create th-text-center">
                            <tr class="table-two-group-width">
                                <th>관리자 번호</th>
                                <td>
                                    <input type="text" class="form-control" id="profileAdminNo" name="profileAdminNo" value="${profile.adminNo}" readonly>
                                </td>
                                <th>아이디</th>
                                <td>
                                    <input type="text" class="form-control" id="profileAdminId" name="profileAdminId" value="${profile.adminId}" readonly>
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 이름</th>
                                <td class="input-parsley">
                                    <input type="text" class="form-control" id="profileAdminName" name="profileAdminName" value="${profile.adminName}" placeholder="이름을 입력하세요" data-parsley-required="true" data-parsley-trigger="change" data-parsley-minlength="2" autocomplete="off">
                                </td>
                                <th><i class="fa fa-exclamation red"></i> 전화번호</th>
                                <td class="input-parsley">
                                    <input type="number" class="form-control" id="profilePhoneNum" name="profilePhoneNum" value="${profile.phoneNum}" placeholder="전화번호를 입력하세요" data-parsley-required="true" data-parsley-trigger="change" data-parsley-type="digits" data-parsley-length="[10, 11]" autocomplete="off">
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 비밀번호</th>
                                <td>
                                    <button type="button" name="passwordChange" class="btn btn-default btn-block btn-chagne-pwd">비밀번호변경</button>
                                    <input type="password" class="form-control" id="profileAdminPwd" name="profileAdminPwd" style="display: none;" placeholder="비밀번호를 입력하세요 (영문+숫자 6자리 이상)" data-parsley-minlength="6" data-parsley-pattern="/^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/" autocomplete="off">
                                </td>
                                <th><i class="fa fa-exclamation red"></i> 비밀번호 확인</th>
                                <td class="readonly">
                                    <input type="password" class="form-control" id="adminPwdConfirm" name="profileAdminPwd" style="display: none;" placeholder="비밀번호를 다시 한번 입력하세요" data-parsley-minlength="6" data-parsley-equalto="#profileAdminPwd" data-parsley-pattern="/^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/" autocomplete="off">
                                </td>
                            </tr>
                            <tr>
                                <th class="text-center">관리자 유형</th>
                                <td class="update-label-fixed">
                                    <c:choose>
                                        <c:when test="${profile.adminType == 'S'}">
                                            <span class="label label-success ">슈퍼관리자</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-warning">일반관리자</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <th>관리자 권한</th>
                                <td class="text-center update-label-fixed">
                                    <div class="col-sm-6 col-md-6">
                                        <div class="custom-checkbox">
                                            <c:choose>
                                                <c:when test="${profile.adminPermission.meta == 'Y'}">
                                                    <input type="checkbox" id="detailMeta" name="detailMeta" value="Y" checked disabled/>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="checkbox" id="detailMeta" name="detailMeta" value="N" disabled/>
                                                </c:otherwise>
                                            </c:choose>
                                            <label for="detailMeta">메타관리</label>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-md-6">
                                        <div class="custom-checkbox">
                                            <c:choose>
                                                <c:when test="${profile.adminPermission.member == 'Y'}">
                                                    <input type="checkbox" id="detailMember" name="detailMember" value="Y" checked disabled/>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="checkbox" id="detailMember" name="detailMember" value="N" disabled/>
                                                </c:otherwise>
                                            </c:choose>
                                            <label for="detailMember">회원관리</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>등록일</th>
                                <td colspan="3" class="update-padding-fixed readonly">
                                    <span><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${profile.createDt}"/></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </jsp:body>
</t:template>
