<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                        <h3>관리자 정보 등록
                            <small>회원 관리 / <a href="/manager/admin/">관리자 관리</a> / <b><a href="/manager/admin/create">관리자 정보 등록</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" class="btn btn-sm btn-danger" id="createBtnClose">닫기</button>
                        <button type="button" class="btn btn-sm btn-mint"   id="createBtnSave">저장</button>
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
                                <th><i class="fa fa-exclamation red"></i> 아이디</th>
                                <td class="input-parsley">
                                    <input type="text" class="form-control" id="adminId" name="adminId"
                                           placeholder="아이디를 입력하세요" data-parsley-required="true"
                                           data-parsley-trigger="change" data-parsley-minlength="5"
                                           data-parsley-type="email" autocomplete="off" maxlength="32">
                                </td>
                                <th><i class="fa fa-exclamation red"></i> 이름</th>
                                <td class="input-parsley">
                                    <input type="text" class="form-control" id="adminName" name="adminName"
                                           placeholder="이름을 입력하세요" data-parsley-required="true"
                                           data-parsley-trigger="change" data-parsley-minlength="2"
                                           autocomplete="off" maxlength="32">
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 비밀번호</th>
                                <td class="input-parsley">
                                    <input type="password" class="form-control" id="adminPwd" name="adminPwd"
                                           placeholder="비밀번호를 입력하세요 (영문+숫자 6자리 이상)" data-parsley-required="true"
                                           data-parsley-trigger="change" data-parsley-minlength="6"
                                           data-parsley-pattern="/^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/"
                                           autocomplete="off" maxlength="128">
                                </td>
                                <th><i class="fa fa-exclamation red"></i> 비밀번호 확인</th>
                                <td class="input-parsley">
                                    <input type="password" class="form-control" id="adminPwdConfirm"
                                           placeholder="비밀번호를 다시 한번 입력하세요" data-parsley-required="true"
                                           data-parsley-trigger="change" data-parsley-minlength="6"
                                           data-parsley-equalto="#adminPwd"
                                           data-parsley-pattern="/^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/"
                                           autocomplete="off" maxlength="128">
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 전화번호</th>
                                <td class="input-parsley">
                                    <input type="number" class="form-control" id="phoneNum" name="phoneNum"
                                           placeholder="전화번호를 입력하세요" data-parsley-required="true"
                                           data-parsley-trigger="change" data-parsley-type="digits"
                                           data-parsley-length="[10, 11]" autocomplete="off" maxlength="11">
                                </td>
                                <th>권한</th>
                                <td class="text-center">
                                    <div class="col-sm-6 col-md-6">
                                        <div class="custom-checkbox create-custom-checkbox">
                                            <input type="checkbox" id="meta" name="meta"/>
                                            <label for="meta">메타관리</label>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-md-6">
                                        <div class="custom-checkbox create-custom-checkbox">
                                            <input type="checkbox" id="member" name="member"/>
                                            <label for="member">회원관리</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 확인키</th>
                                <td colspan="3" class="input-parsley">
                                    <input type="password" class="form-control" id="adminSecretKey"
                                           name="adminSecretKey" data-parsley-required="true"
                                           data-parsley-trigger="change" placeholder="확인키" autocomplete="off">
                                </td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </jsp:body>
</t:template>
