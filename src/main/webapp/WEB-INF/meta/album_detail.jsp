<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-2">
    <jsp:attribute name="javascriptSrc">
      <script src="/js/custom.js"></script>
      <script src="/js/meta/album.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="albumDetail">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>앨범 메타 등록
                                <small>메타 관리 / <a href="/meta/album">앨범 메타 관리</a> / <b><a href="/meta/album/detail/${detailResult.albumId}">앨범 메타 상세</a></b></small>
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
                    <h3 class="form-section">앨범 정보</h3>
                </div>
                <div class="col-sm-12 col-md-12">
                    <div class="table-responsive">
                        <table class="table table-bordered table-condensed table-detail th-text-center">
                            <tr class="table-two-group-width">
                                <th>앨범ID</th>
                                <td><span id="detailAlbumId">${detailResult.albumId}</span></td>
                                <th>앨범 MID</th>
                                <td><span>${detailResult.originId}</span></td>
                            </tr>
                            <tr>
                                <th>앨범명 <span class="red">(한글)</span></th>
                                <td>
                                        ${fn:escapeXml(detailResult.albumTitle)}
                                </td>
                                <th>앨범명 <span class="red">(영문)</span></th>
                                <td>
                                        ${fn:escapeXml(detailResult.albumTitleEN)}
                                </td>
                            </tr>
                            <tr>
                                <th>앨범타입</th>
                                <td>
                                    <c:forEach var="albumType" items="${albumType}">
                                        <c:if test="${albumType.albumTypeId == detailResult.albumTypeId}">
                                            <span id="albumTypeName" class="left-text">${albumType.albumTypeName}</span>
                                            <span id="albumTypeId" class="right-text">ID : ${detailResult.albumTypeId}</span>
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <th>발매일</th>
                                <td>
                                    <span id="detailPublishDt"><fmt:formatDate pattern="yyyy-MM-dd" value="${detailResult.publishDt}"/></span>
                                </td>
                            </tr>
                            <tr>
                                <th>아티스트ID</th>
                                <td colspan="3">${detailResult.artistName}</td>
                            </tr>
                            <tr>
                                <th rowspan="2">이미지업로드</th>
                                <td colspan="3">
                                    <c:choose>
                                        <c:when test="${detailResult.albumImgUrl != null}">
                                            <a target="_blank" id="datailAlbumImgUrl" href="${detailResult.albumImgUrl}">${detailResult.albumImgUrl}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a>이미지 없음</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr class="table-img-box">
                                <td rowspan="2">
                                    <c:if test="${detailResult.albumImgUrl != null}">
                                        <img src="${detailResult.albumImgUrl}" class="img-responsive img-preview"/>
                                    </c:if>
                                </td>
                                <td colspan="2"></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div id="albumUpdate" style="display:none;">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>앨범 메타 등록
                                <small>메타 관리 / <a href="/meta/album">앨범 메타 관리</a> / <b><a href="/meta/album/detail">앨범 메타 수정</a></b></small>
                            </h3>
                        </div>
                        <div class="title-right">
                            <button type="button" class="btn btn-sm btn-danger" id="updateBtnClose">닫기</button>
                            <button type="button" class="btn btn-sm btn-mint" id="updateBtnSave">저장</button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <h3 class="form-section">앨범 정보</h3>
                </div>
                <div class="col-sm-12 col-md-12">
                    <form id="formAlbum" data-parsley-validate>
                        <input type="hidden" id="cropImageType" value="album">
                        <input type="hidden" id="cropImageTarget" value="">
                        <div class="table-responsive">
                            <table class="table table-bordered table-create th-text-center">
                                <tr class="table-two-group-width">
                                    <th><i class="fa fa-exclamation red"></i> 앨범ID</th>
                                    <td>
                                        <input type="text" class="form-control" id="updateAlbumId" value="${detailResult.albumId}" placeholder="자동생성" readonly>
                                    </td>
                                    <th>앨범 MID</th>
                                    <td>
                                        <input type="number" class="form-control" id="updateOriginId" value="${detailResult.originId}" placeholder="앨범 MID" autocomplete="off">
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation red"></i> 앨범명 <span
                                            class="red">(한글)</span></th>
                                    <td class="input-parsley">
                                        <input type="text" class="form-control" id="updateAlbumTitle"
                                               value="${fn:escapeXml(detailResult.albumTitle)}" placeholder="앨범명"
                                               data-parsley-required="true" data-parsley-minlength="1"
                                               autocomplete="off" data-parsley-trigger="change" maxlength="100">
                                    </td>
                                    <th>앨범명 <span class="red">(영문)</span></th>
                                    <td class="input-parsley">
                                        <input type="text" class="form-control" id="updateAlbumTitleEN"
                                               value="${fn:escapeXml(detailResult.albumTitleEN)}" maxlength="100"
                                               placeholder="앨범명(EN)" data-parsley-minlength="1" autocomplete="off">
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation red"></i> 앨범타입</th>
                                    <td class="input-parsley">
                                        <select class="js-example-basic-single" id="selectAlbumType" name="createTypeSelect" data-parsley-required="true">
                                            <c:forEach var="item" items="${albumType}">
                                                <option value="${item.albumTypeId}">${item.albumTypeName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <th><i class="fa fa-exclamation red"></i> 발매일</th>
                                    <td class="input-parsley">
                                        <div class='input-group date' id='publishDtPicker'>
                                            <input type='text' id="publishDt" placeholder="발매일" class="form-control" data-parsley-required="true" autocomplete="off"/>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation red"></i> 아티스트ID</th>
                                    <td colspan="3" class="form-inline" name="artist">
                                        <c:forTokens var="item" items="${detailResult.artistName}" delims="," varStatus="status">
                                            <c:set var="itemId" value="${fn:split(detailResult.artistId,',')}"/>
                                            <input type="hidden" name="btnArtist" value="${itemId[status.count-1]}"/>
                                            <button type='button' name="btnBox" class='btn btn-sm btn-info btn-tag'>
                                                <span><strong>${item}</strong></span>
                                                <a class='a-delete' href='#'><i class='ace-icon fa fa-times'></i></a>
                                            </button>
                                        </c:forTokens>
                                        <input type="text" id="artistSearch" name="searchInputName" class="form-control" value="" placeholder="아티스트검색" autocomplete="off">
                                        <input type="hidden" name="searchInputId" value=""/>
                                        <ul class="list-group"></ul>
                                    </td>
                                </tr>
                                <tr>
                                    <th rowspan="2">이미지업로드</th>
                                    <td class="text-center">
                                        <label title="Upload image file" for="albumImg" class="btn btn-sm btn-primary label-fileInput">
                                            <input type="file" class="fileInput hide" name="albumImg" accept=".png, .jpg" id="albumImg"/>파일열기
                                        </label>
                                    </td>
                                    <td colspan="2"></td>
                                </tr>
                                <tr class="table-img-box">
                                    <td>
                                        <c:choose>
                                            <c:when test="${detailResult.albumImgUrl != null }">
                                                <img class="img-responsive img-preview" id="albumImgView" src="${detailResult.albumImgUrl}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img class="img-responsive img-preview" id="albumImgView" src="" target="_blank"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td colspan="2"></td>
                                </tr>
                            </table>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
