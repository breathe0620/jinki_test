<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-2">
    <jsp:attribute name="javascriptSrc">
    <script src="/js/meta/artist.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>아티스트 메타 등록
                            <small>메타 관리 / <a href="/meta/artist">아티스트 메타 관리</a> / <b><a href="/meta/artist/create">아티스트 메타 등록</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" class="btn btn-sm btn-danger" id="createBtnClose">닫기</button>
                        <button type="button" class="btn btn-sm btn-mint" id="createBtnSave">저장</button>
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
                                    <input type="text" class="form-control" id="createArtistId" placeholder="자동생성" data-parsley-minlength="1" autocomplete="off" disabled="true">
                                </td>
                                <th>아티스트 MID</th>
                                <td>
                                    <input type="number" class="form-control" id="createOriginId" placeholder="아티스트 MID" data-parsley-minlength="1" autocomplete="off">
                                </td>
                                <th><i class="fa fa-exclamation red"></i> 아티스트유형</th>
                                <td class="input-parsley">
                                    <select class="js-example-basic-single" id="selectArtistType" name="createTypeSelect" data-parsley-required="true">
                                        <option value="" selected>선택</option>
                                        <c:forEach var="item" items="${artistTypeList}">
                                            <option value="${item.artistTypeId}">${item.typeName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 아티스트명 <span
                                        class="red">(한글)</span></th>
                                <td class="input-parsley">
                                    <input type="text" class="form-control" id="createArtistName"
                                           placeholder="아티스트명" data-parsley-required="true" maxlength="64"
                                           data-parsley-minlength="1" autocomplete="off" data-parsley-trigger="change">
                                </td>
                                <th>아티스트명 <span class="red">(영문)</span></th>
                                <td class="input-parsley">
                                    <input type="text" class="form-control" id="createArtistNameEn" maxlength="64"
                                           placeholder="아티스트명" data-parsley-minlength="1" autocomplete="off">
                                </td>
                                <th>생년월일</th>
                                <td>
                                    <div class='input-group date' id='birthDtPicker'>
                                        <input type='text' id="birthDt" placeholder="생년월일" value="" class="form-control" autocomplete="off"/>
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>데뷔</th>
                                <td>
                                    <div class='input-group date' id='debutDtPicker'>
                                        <input type='text' class="form-control" id="debutDt" value="" placeholder="데뷔" autocomplete="off"/>
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </td>
                                <th>결성일</th>
                                <td>
                                    <div class='input-group date' id='organizeDtPicker'>
                                        <input type='text' id="organizeDt" placeholder="결성일" value="" class="form-control" autocomplete="off"/>
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </td>
                                <th><i class="fa fa-exclamation red"></i> 활동년도</th>
                                <td class="input-parsley">
                                    <div class='input-group date' id='activeAgesPicker'>
                                        <input type='text' class="form-control" id="activeAges" placeholder="활동년도" data-parsley-required="true" data-parsley-trigger="change" autocomplete="off"/>
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>데뷔곡</th>
                                <td name="debutSong">
                                    <input type="text" id="debutSongSearch" name="searchInputName" class="form-control" value="" placeholder="데뷔곡 검색" autocomplete="off">
                                    <input type="hidden" name="searchInputId" value=""/>
                                    <ul class="list-group"></ul>
                                </td>
                                <th>소속사명</th>
                                <td name="agency">
                                    <input type="text" id="agencySearch" name="searchInputName" value="" class="form-control" value="" placeholder="소속사명 검색" autocomplete="off">
                                    <input type="hidden" name="searchInputId" value=""/>
                                    <ul class="list-group"></ul>
                                </td>
                                <td colspan="2"></td>
                            </tr>
                            <tr>
                                <th>소속그룹</th>
                                <td colspan="5" class="form-inline" name="group">
                                    <input type="text" class="form-control" name="searchInputName" id="groupArtistSearch" placeholder="소속 그룹 검색" autocomplete="off">
                                    <input type="hidden" name="searchInputId" value=""/>
                                    <ul class="list-group"></ul>
                                </td>
                            </tr>
                            <tr>
                                <th>그룹멤버</th>
                                <td colspan="5" class="form-inline" name="member">
                                    <input type="text" class="form-control" name="searchInputName" id="memberArtistSearch" placeholder="그룹 멤버 검색" autocomplete="off">
                                    <input type="hidden" name="searchInputId" value=""/>
                                    <ul class="list-group"></ul>
                                </td>
                            </tr>
                            <tr>
                                <th>연관아티스트</th>
                                <td colspan="5" class="form-inline" name="relative">
                                    <input type="text" class="form-control" name="searchInputName" id="relativeArtistSearch" placeholder="관련 아티스트 검색" autocomplete="off">
                                    <input type="hidden" name="searchInputId" value=""/>
                                    <ul class="list-group"></ul>
                                </td>
                            </tr>
                            <tr>
                                <th>유사아티스트</th>
                                <td colspan="5" class="form-inline" name="similar">
                                    <input type="text" class="form-control" name="searchInputName" id="similarArtistSearch" placeholder="유사 아티스트 검색" autocomplete="off">
                                    <input type="hidden" name="searchInputId" value=""/>
                                    <ul class="list-group"></ul>
                                </td>
                            </tr>
                            <tr>
                                <th>같은 소속사 아티스트</th>
                                <td colspan="5">
                                    <input type="text" class="form-control" id="sameAgencyArtist" placeholder="같은 소속사 아티스트" autocomplete="off" readonly>
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
                                </td>
                                <td colspan="3" class="text-center">
                                    <label title="Upload image file" for="profileImg" class="btn btn-sm btn-primary label-fileInput">
                                        <input type="file" class="fileInput hide" name="profileImg" accept=".png, .jpg" id="profileImg"/>파일열기
                                    </label>
                                </td>
                            </tr>
                            <tr class="table-img-box">
                                <td colspan="3">
                                    <img class="img-responsive img-preview img-artist" id="mainImgView"/>
                                </td>
                                <td colspan="3">
                                    <img class="img-responsive img-preview img-artist" id="profileImgView"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </jsp:body>
</t:template>
