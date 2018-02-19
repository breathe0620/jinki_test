<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
    <jsp:attribute name="javascriptSrc">
      <script src="/js/custom.js"></script>
      <script src="/js/meta/song.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="songDetail">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>곡 메타 상세
                                <small>메타 관리 / <a href="/meta/song">곡 메타 관리</a> / <b><a href="/meta/song/detail/${detailResult.songId}">곡 메타 상세</a></b></small>
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
                    <h3 class="form-section">곡 정보</h3>
                </div>
                <div class="col-sm-12 col-md-12">
                    <div class="table-responsive">
                        <table class="table table-bordered table-condensed table-detail th-text-center">
                            <tr class="table-two-group-width">
                                <th>곡명ID</th>
                                <td><span id="detailSongId">${detailResult.songId}</span></td>
                                <th>곡 MID</th>
                                <td><span>${detailResult.originId}</span></td>
                            </tr>
                            <tr>
                                <th>곡명 <span class="red">(한글)</span></th>
                                <td>
                                        ${fn:escapeXml(detailResult.songName)}
                                </td>
                                <th>곡명 <span class="red">(영문)</span></th>
                                <td>
                                        ${fn:escapeXml(detailResult.songNameEn)}
                                </td>
                            </tr>
                            <tr>
                                <th>장르유형</th>
                                <td>
                                    <c:forEach var="genreItem" items="${genreList}">
                                        <c:if test="${genreItem.genreId == detailResult.genreId}">
                                            <span id="genreName" class="left-text">${genreItem.genreName}</span>
                                        </c:if>
                                    </c:forEach>
                                    <span id="genreId" class="right-text">ID: ${detailResult.genreId}</span>
                                </td>
                                <td colspan="2"></td>
                            </tr>
                            <tr>
                                <th>앨범</th>
                                <td>
                                    <span class="left-text">${detailResult.albumName}</span>
                                    <span class="right-text">ID: ${detailResult.albumId} / MID: ${detailResult.albumOriginId}</span>
                                </td>
                                <th>발매일</th>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${detailResult.publishDt}"/></td>
                            </tr>
                            <tr>
                                <th>아티스트ID</th>
                                <td colspan="3">
                                    <span id="detailArtistName" class="left-text">${detailResult.artistName}</span>
                                </td>
                            </tr>
                            <tr>
                                <th>등록일</th>
                                <td colspan="3">
                                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detailResult.createDt}"/>
                                </td>
                            </tr>
                            <tr>
                                <th>가사</th>
                                <td colspan="3"><textarea readonly>${fn:escapeXml(detailResult.lyric)}</textarea></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div id="songUpdate" style="display:none;">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>곡 메타 수정
                                <small>메타 관리 / <a href="/meta/song">곡 메타 관리</a> / <b><a href="/meta/song/detail">곡 메타 수정</a></b></small>
                            </h3>
                        </div>
                        <div class="title-right">
                            <button type="button" class="btn btn-sm btn-danger" id="updateBtnClose">닫기</button>
                            <button type="button" class="btn btn-sm btn-mint" id="updateBtnCreate">저장</button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 col-md-12">
                    <h3 class="form-section">곡 정보</h3>
                </div>
                <div class="col-sm-12 col-md-12">
                    <form id="formSong" data-parsley-validate>
                        <div class="table-responsive">
                            <table class="table table-bordered table-create th-text-center">
                                <tr class="table-two-group-width">
                                    <th><i class="fa fa-exclamation red"></i> 곡명ID</th>
                                    <td>
                                        <input type="text" class="form-control" id="updateSongId" value="${detailResult.songId}" placeholder="자동생성" readonly>
                                    </td>
                                    <th>곡 MID</th>
                                    <td>
                                        <input type="number" id="updateOriginId" value="${detailResult.originId}" class="form-control" placeholder="곡 MID" autocomplete="off">
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation red"></i> 곡명 <span class="red">(한글)</span></th>
                                    <td class="input-parsley">
                                        <input type="text" class="form-control" id="updateSongName"
                                               value="${fn:escapeXml(detailResult.songName)}" placeholder="곡명"
                                               data-parsley-required="true" data-parsley-minlength="1"
                                               autocomplete="off" data-parsley-trigger="change" maxlength="128">
                                    </td>
                                    <th>곡명 <span class="red">(영문)</span></th>
                                    <td class="input-parsley">
                                        <input type="text" class="form-control" id="updateSongNameEn"
                                               value="${fn:escapeXml(detailResult.songNameEn)}"
                                               placeholder="곡명(EN)" data-parsley-minlength="1"
                                               autocomplete="off" maxlength="128">
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation red"></i> 장르유형</th>
                                    <td class="input-parsley">
                                        <select class="js-example-basic-single" id="selectGenre" name="createTypeSelect" data-parsley-required="true">
                                            <c:forEach var="item" items="${genreList}">
                                                <option value="${item.genreId}">${item.genreName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td colspan="2"></td>
                                </tr>
                                <tr>
                                    <th>앨범</th>
                                    <td>
                                        <input type="text" id="albumSearch" name="searchInputName" value="${detailResult.albumName}" class="form-control" placeholder="앨범" autocomplete="off">
                                        <input type="hidden" name="searchInputId" value="${detailResult.albumId}">
                                        <ul class="list-group absolute-ul"></ul>
                                    </td>
                                    <th>발매일</th>
                                    <td>
                                        <input type='text' id="updatePublishDt" value="<fmt:formatDate pattern = "yyyy-MM-dd"  value = "${detailResult.publishDt}"/>" placeholder="발매일" class="form-control" autocomplete="off" readonly/>
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
                                        <input type="text" id="artistSearch" name="searchInputName" value="" data-parsley-required="false" class="form-control" placeholder="아티스트검색" autocomplete="off">
                                        <input type="hidden" name="searchInputId" value=""/>
                                        <ul class="list-group"></ul>
                                    </td>
                                </tr>
                                <tr>
                                    <th><i class="fa fa-exclamation red"></i> 가사</th>
                                    <td colspan="3" class="input-parsley">
                                        <textarea class="form-control" id="updateLyric" placeholder="가사" autocomplete="off" data-parsley-required="true">${fn:escapeXml(detailResult.lyric)}</textarea>
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
