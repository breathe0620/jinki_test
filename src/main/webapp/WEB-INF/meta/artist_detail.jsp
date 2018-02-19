<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-2">
    <jsp:attribute name="javascriptSrc">
      <script src="/js/pagination.js"></script>
      <script src="/js/meta/artist.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="artistDetail">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>아티스트 메타 상세
                                <small>메타 관리 / <a href="/meta/artist">아티스트 메타 관리</a> / <b><a href="/meta/artist/detail/${detailResult.artistId}">아티스트 메타 상세</a></b></small>
                            </h3>
                        </div>
                        <div class="title-right">
                            <button type="button" class="btn btn-sm btn-success" id="detailBtnList">목록</button>
                            <button type="button" class="btn btn-sm btn-danger" id="detailBtnDelete">삭제</button>
                            <button type="button" class="btn btn-sm btn-warning" id="detailBtnUpdate">수정</button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <h3 class="form-section">아티스트 정보</h3>
                </div>
                <div class="col-sm-12 col-md-12">
                    <div class="table-responsive">
                        <table class="table table-bordered table-condensed table-detail th-text-center">
                            <tr class="table-three-group-width">
                                <th>아티스트ID</th>
                                <td><span id="detailArtistId">${detailResult.artistId}</span></td>
                                <th>아티스트 MID</th>
                                <td>${detailResult.originId}</td>
                                <th>아티스트유형</th>
                                <td>
                                    <c:forEach var="item" items="${artistTypeList}">
                                        <c:if test="${item.artistTypeId == detailResult.artistTypeId}">
                                            <span id="artistTypeName" class="left-text">${item.typeName}</span>
                                        </c:if>
                                    </c:forEach>
                                    <span id="artistTypeId" class="right-text">ID: ${detailResult.artistTypeId}</span>
                                </td>
                            </tr>
                            <tr>
                                <th>아티스트명 <span class="red">(한글)</span></th>
                                <td>
                                        ${fn:escapeXml(detailResult.artistName)}
                                </td>
                                <th>아티스트명 <span class="red">(영문)</span></th>
                                <td>
                                        ${fn:escapeXml(detailResult.artistNameEn)}
                                </td>
                                <th>생년월일</th>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${detailResult.birthDt}"/></td>
                            </tr>
                            <tr>
                                <th>데뷔</th>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${detailResult.debutDt}"/></td>
                                <th>결성일</th>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${detailResult.organizeDt}"/></td>
                                <th>활동년도</th>
                                <td>
                                    <c:forEach var="item" items="${detailResult.activeAges}" varStatus="index">
                                        <c:choose>
                                            <c:when test="${index.last}">
                                                <span name="detailActiveAges">${item.activeAges}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span name="detailActiveAges">${item.activeAges},</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <th>데뷔곡</th>
                                <td>${detailResult.debutSongName}</td>
                                <th>소속사명</th>
                                <td>${detailResult.agencyName}</td>
                                <td colspan="2"></td>
                            </tr>
                            <tr>
                                <th>소속그룹</th>
                                <td colspan="5">
                                    <c:forEach var="item" items="${detailResult.artistGroup}" varStatus="index">
                                        <c:choose>
                                            <c:when test="${index.last}">
                                                <span>${item.groupName}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>${item.groupName}, </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <th>그룹멤버</th>
                                <td colspan="5">
                                    <c:forEach var="item" items="${detailResult.memberArtist}" varStatus="index">
                                        <c:choose>
                                            <c:when test="${index.last}">
                                                <span>${item.memberArtistName}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>${item.memberArtistName}, </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <th>연관아티스트</th>
                                <td colspan="5">
                                    <c:forEach var="item" items="${detailResult.relativeArtist}" varStatus="index">
                                        <c:choose>
                                            <c:when test="${index.last}">
                                                <span>${item.memberArtistName}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>${item.memberArtistName}, </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <th>유사아티스트</th>
                                <td colspan="5">
                                    <c:forEach var="item" items="${detailResult.similarArtist}" varStatus="index">
                                        <c:choose>
                                            <c:when test="${index.last}">
                                                <span>${item.memberArtistName}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>${item.memberArtistName}, </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <th>같은 소속사 아티스트</th>
                                <td colspan="5">
                                    <c:forEach var="item" items="${detailResult.sameAgencyArtist}"
                                               varStatus="index">
                                        <c:choose>
                                            <c:when test="${index.last}">
                                                <span name="detailSameAgencyArtist">${item.artistName}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span name="detailSameAgencyArtist">${item.artistName}, </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr class="td-text-center">
                                <th colspan="3">아티스트 대문 이미지</th>
                                <th colspan="3">아티스트 프로필 이미지</th>
                            </tr>
                            <tr class="td-text-center">
                                <td colspan="3">
                                    <a target="_blank" href="${detailResult.mainImgUrl}">${detailResult.mainImgUrl}</a>
                                </td>
                                <td colspan="3">
                                    <a target="_blank" href="${detailResult.profileImgUrl}">${detailResult.profileImgUrl}</a>
                                </td>
                            </tr>
                            <tr class="table-img-box">
                                <td colspan="3">
                                    <c:if test="${detailResult.mainImgUrl != ''}">
                                        <img src="${detailResult.mainImgUrl}" class="img-responsive img-preview img-artist"/>
                                    </c:if>
                                </td>
                                <td colspan="3">
                                    <c:if test="${detailResult.profileImgUrl != ''}">
                                        <img src="${detailResult.profileImgUrl}" class="img-responsive img-preview img-artist"/>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <th>등록일</th>
                                <td colspan="5">
                                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detailResult.createDt}"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div id="artistUpdate" style="display:none;">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>아티스트 메타 등록
                                <small>메타 관리 / <a href="/meta/artist">아티스트 메타 관리</a> / <b><a href="/meta/artist/detail">아티스트 메타 수정</a></b></small>
                            </h3>
                        </div>
                        <div class="title-right">
                            <button type="button" class="btn btn-sm btn-danger" id="updateBtnClose">닫기</button>
                            <button type="button" class="btn btn-sm btn-mint" id="updateBtnCreate">저장</button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <h3 class="form-section">아티스트 정보</h3>
                </div>
                <div class="col-sm-12 col-md-12">
                    <form id="formArtist" data-parsley-validate>
                        <input type="hidden" id="cropImageType" value="artist">
                        <input type="hidden" id="cropImageTarget" value="">
                        <div class="table-responsive">
                            <table class="table table-bordered table-create th-text-center">
                                <tr class="table-three-group-width">
                                    <th><i class="fa fa-exclamation red"></i> 아티스트ID</th>
                                    <td>
                                        <input type="text" class="form-control" id="updateArtistId" value="${detailResult.artistId}" placeholder="자동생성" autocomplete="off" disabled="true">
                                    </td>
                                    <th>아티스트 MID</th>
                                    <td>
                                        <input type="number" class="form-control" id="updateOriginId" value="${detailResult.originId}" placeholder="아티스트 MID" data-parsley-minlength="1" autocomplete="off">
                                    </td>
                                    <th><i class="fa fa-exclamation red"></i> 아티스트유형</th>
                                    <td class="input-parsley">
                                        <select class="js-example-basic-single" id="selectArtistType" name="createTypeSelect" data-parsley-required="true">
                                            <c:forEach var="item" items="${artistTypeList}">
                                                <option value="${item.artistTypeId}">${item.typeName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation red"></i> 아티스트명 <span class="red">(한글)</span></th>
                                    <td class="input-parsley">
                                        <input type="text" class="form-control" id="updateArtistName"
                                               value="${fn:escapeXml(detailResult.artistName)}" placeholder="아티스트명"
                                               data-parsley-required="true" data-parsley-minlength="1"
                                               autocomplete="off" data-parsley-trigger="change" maxlength="64">
                                    </td>
                                    <th>아티스트명 <span class="red">(영문)</span></th>
                                    <td class="input-parsley">
                                        <input type="text" class="form-control" id="updateArtistNameEn"
                                               value="${fn:escapeXml(detailResult.artistNameEn)}" maxlength="64"
                                               placeholder="아티스트명" data-parsley-minlength="1" autocomplete="off">
                                    </td>
                                    <th>생년월일</th>
                                    <td>
                                        <div class='input-group date' id='birthDtPicker'>
                                            <input type='text' class="form-control" id="birthDt" value="<fmt:formatDate pattern = "yyyy-MM-dd"  value = "${detailResult.birthDt}"/>" placeholder="생년월일" autocomplete="off"/>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>데뷔</th>
                                    <td>
                                        <div class='input-group date' id='debutDtPicker'>
                                            <input type='text' class="form-control" id="debutDt" value="<fmt:formatDate pattern = "yyyy-MM-dd"  value = "${detailResult.debutDt}"/>" placeholder="데뷔" autocomplete="off"/>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                        </div>
                                    </td>
                                    <th>결성일</th>
                                    <td>
                                        <div class='input-group date' id='organizeDtPicker'>
                                            <input type='text' class="form-control" id="organizeDt" value="<fmt:formatDate pattern = "yyyy-MM-dd"  value = "${detailResult.organizeDt}"/>" placeholder="결성일" autocomplete="off"/>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                        </div>
                                    </td>
                                    <th><i class="fa fa-exclamation red"></i> 활동년도</th>
                                    <td class="input-parsley">
                                        <div class='input-group date' id='activeAgesPicker'>
                                            <input type='text' class="form-control" id="activeAges" value="" placeholder="활동년도" data-parsley-required="true" autocomplete="off"/>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>  </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>데뷔곡</th>
                                    <td name="debutSong">
                                        <input type="text" id="debutSongSearch" name="searchInputName" value="${detailResult.debutSongName}" class="form-control" value="" placeholder="데뷔곡 검색" autocomplete="off">
                                        <input type="hidden" name="searchInputId" value="${detailResult.debutSongId}"/>
                                        <ul class="list-group"></ul>
                                    </td>
                                    <th>소속사명</th>
                                    <td name="agency">
                                        <input type="text" id="agencySearch" name="searchInputName"
                                               value="${detailResult.agencyName}" class="form-control" value=""
                                               placeholder="소속사명 검색" autocomplete="off">
                                        <input type="hidden" name="searchInputId" value="${detailResult.agencyId}"/>
                                        <ul class="list-group"></ul>
                                    </td>
                                    <td colspan="2"></td>
                                </tr>
                                <tr>
                                    <th>소속그룹</th>
                                    <td colspan="5" class="form-inline" name="group">
                                        <c:forEach var="item" items="${detailResult.artistGroup}">
                                            <input type="hidden" name="btnArtist" value="${item.artistGroupId}"/>
                                            <button type='button' name="btnBox" class='btn btn-sm btn-info btn-tag'>
                                                <span><strong>${item.groupName}</strong></span>
                                                <a class='a-delete' href='#'><i class='ace-icon fa fa-times'></i></a>
                                            </button>
                                        </c:forEach>
                                        <input type="text" class="form-control" name="searchInputName" id="groupArtistSearch" placeholder="소속 그룹 검색" autocomplete="off">
                                        <input type="hidden" name="searchInputId" value=""/>
                                        <ul class="list-group"></ul>
                                    </td>
                                </tr>
                                <tr>
                                    <th>그룹멤버</th>
                                    <td colspan="5" class="form-inline" name="member">
                                        <c:forEach var="item" items="${detailResult.memberArtist}">
                                            <input type="hidden" name="btnArtist" value="${item.memberArtistId}"/>
                                            <button type='button' name="btnBox" class='btn btn-sm btn-info btn-tag'>
                                                <span><strong>${item.memberArtistName}</strong></span>
                                                <a class='a-delete' href='#'><i class='ace-icon fa fa-times'></i></a>
                                            </button>
                                        </c:forEach>
                                        <input type="text" class="form-control" name="searchInputName" id="memberArtistSearch" placeholder="그룹 멤버 검색" autocomplete="off">
                                        <input type="hidden" name="searchInputId" value=""/>
                                        <ul class="list-group"></ul>
                                    </td>
                                </tr>
                                <tr>
                                    <th>연관아티스트</th>
                                    <td colspan="5" class="form-inline" name="relative">
                                        <c:forEach var="item" items="${detailResult.relativeArtist}">
                                            <input type="hidden" name="btnArtist" value="${item.relativeArtistId}"/>
                                            <button type='button' name="btnBox" class='btn btn-sm btn-info btn-tag'>
                                                <span><strong>${item.memberArtistName}</strong></span>
                                                <a class='a-delete' href='#'><i class='ace-icon fa fa-times'></i></a></button>
                                        </c:forEach>
                                        <input type="text" class="form-control" name="searchInputName" id="relativeArtistSearch" placeholder="관련 아티스트 검색" autocomplete="off">
                                        <input type="hidden" name="searchInputId" value=""/>
                                        <ul class="list-group"></ul>
                                    </td>
                                </tr>
                                <tr>
                                    <th>유사아티스트</th>
                                    <td colspan="5" class="form-inline" name="similar">
                                        <c:forEach var="item" items="${detailResult.similarArtist}">
                                            <input type="hidden" name="btnArtist" value="${item.similarArtistId}"/>
                                            <button type='button' name="btnBox" class='btn btn-sm btn-info btn-tag'>
                                                <span><strong>${item.memberArtistName}</strong></span>
                                                <a class='a-delete' href='#'><i class='ace-icon fa fa-times'></i></a></button>
                                        </c:forEach>
                                        <input type="text" class="form-control" name="searchInputName" id="similarArtistSearch" placeholder="유사 아티스트 검색" autocomplete="off">
                                        <input type="hidden" name="searchInputId" value=""/>
                                        <ul class="list-group"></ul>
                                    </td>
                                </tr>
                                <tr>
                                    <th>같은 소속사 아티스트</th>
                                    <td colspan="5">
                                        <input type="text" class="form-control" id="sameAgencyArtist" value="" placeholder="같은 소속사 아티스트" autocomplete="off" readonly>
                                    </td>
                                </tr>
                                <tr>
                                    <th colspan="3">아티스트 대문 이미지</th>
                                    <th colspan="3">아티스트 프로필 이미지</th>
                                </tr>
                                <tr>
                                    <td colspan="3" class="text-center">
                                        <label title="Upload image file" for="mainImg" class="btn btn-sm btn-primary label-fileInput">
                                            <input type="file" class="fileInput hide" name="mainImg" accept=".png, .jpg" id="mainImg"/>파일열기
                                        </label>
                                        <button type="button" class="btn btn-sm btn-warning label-fileInput" name="mainImg" id="editMainImg">파일수정</button>
                                    </td>
                                    <td colspan="3" class="text-center">
                                        <label title="Upload image file" for="profileImg" class="btn btn-sm btn-primary label-fileInput">
                                            <input type="file" class="fileInput hide" name="profileImg" accept=".png, .jpg" id="profileImg"/>파일열기
                                        </label>
                                        <button type="button" class="btn btn-sm btn-warning label-fileInput" name="profileImg" id="editProfileImg">파일수정</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3">
                                        <c:choose>
                                            <c:when test="${detailResult.mainImgUrl != null}">
                                                <img class="img-responsive img-preview img-artist" id="mainImgView" src="${detailResult.mainImgUrl}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img class="img-responsive img-preview img-artist" id="mainImgView"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td colspan="3">
                                        <c:choose>
                                            <c:when test="${detailResult.profileImgUrl != null}">
                                                <img class="img-responsive img-preview img-artist" id="profileImgView" src="${detailResult.profileImgUrl}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img class="img-responsive img-preview img-artist" id="profileImgView"/>
                                            </c:otherwise>
                                        </c:choose>
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
