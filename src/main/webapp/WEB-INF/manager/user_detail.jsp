<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
    <jsp:attribute name="javascriptSrc">
        <script src="/js/manager/user.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>유저 상세
                            <small>서비스 관리 / <a href="/manager/user">유저 관리</a> / <b><a
                                    href="/manager/user/${detailResult.userNo}">유저 상세</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" name="detail" class="btn btn-sm btn-success" id="detailBtnList">목록</button>
                        <button type="button" name="detail" class="btn btn-sm btn-warning" id="detailBtnUpdate">수정</button>
                        <button type="button" name="update" class="btn btn-sm btn-danger" id="updateBtnClose" style="display: none;">닫기</button>
                        <button type="button" name="update" class="btn btn-sm btn-mint" id="updateBtnSave" style="display: none;">저장</button>
                    </div>
                </div>
            </div>
            <div class="col-sm-12 col-md-12">
                <h3 class="form-section">유저 정보</h3>
            </div>
            <div class="col-sm-12 col-md-12">
                <div class="table-responsive">
                    <table class="table table-bordered table-condensed table-detail th-text-center">
                        <tr class="table-two-group-width">
                            <th>고유번호</th>
                            <td><span name="userNo">${detailResult.userNo}</span></td>
                            <th>회원ID</th>
                            <td><span>${detailResult.userId}</span></td>
                        </tr>
                        <tr>
                            <th>회원 가입 타입</th>
                            <td>
                                <c:choose>
                                    <c:when test="${detailResult.userType == 0}">
                                        <label class="label label-plain label-userType">이메일</label>
                                    </c:when>
                                    <c:when test="${detailResult.userType == 1}">
                                        <label class="label label-primary label-userType">페이스북</label>
                                    </c:when>
                                    <c:when test="${detailResult.userType == 2}">
                                        <label class="label label-danger label-userType">구글</label>
                                    </c:when>
                                </c:choose>
                            </td>
                            <th>회원 메일 정보</th>
                            <td><span>${detailResult.userEmail}</span></td>
                        </tr>
                        <tr>
                            <th>회원 닉네임</th>
                            <td><span>${detailResult.userName}</span></td>
                            <th>회원 상태</th>
                            <td>
                                <input type="hidden" name="userStatus" value="${detailResult.userStatus}"/>
                                <div name="detail">
                                    <c:choose>
                                        <c:when test="${detailResult.userStatus == 'Y'}">
                                            <label class="label label-primary label-userType">가입</label>
                                        </c:when>
                                        <c:when test="${detailResult.userStatus == 'D'}">
                                            <label class="label label-danger label-userType">탈퇴</label>
                                        </c:when>
                                        <c:when test="${detailResult.userStatus == 'B'}">
                                            <label class="label label-default label-userType">블락</label>
                                        </c:when>
                                    </c:choose>
                                </div>
                                <div name="update" style="display: none;">
                                    <select id="selectStatus">
                                        <option value="">카테고리 선택</option>
                                        <option value="Y">가입</option>
                                        <option value="D">탈퇴</option>
                                        <option value="B">블락</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>회원 가입 일자</th>
                            <td>
                                <span><fmt:formatDate pattern="yyyy-MM-dd" value="${detailResult.createDt}"/></span>
                            </td>
                            <th>회원 접속 시간</th>
                            <td>
                                <span><fmt:formatDate pattern="yyyy-MM-dd" value="${detailResult.modifyDt}"/></span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
