<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/meta/clip" prefix="clipTag" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-2">
    <jsp:attribute name="javascriptSrc">
      <script src="/js/meta/clip.js"></script>
      <script>
          arraySongGenre = [];
          <c:forEach var="item" items="${songGenre}">
          var mapSongGenre = {};
          mapSongGenre.genreId = '${item.genreId}';
          mapSongGenre.genreName = '${item.genreName}';
          arraySongGenre.push(mapSongGenre);
          </c:forEach>

          function matchingGenre(targetId) {
              for (var i = 0; i < arraySongGenre.length; i++) {
                  if (arraySongGenre[i].genreId == targetId)
                      return arraySongGenre[i].genreName;
              }
          }
      </script>
    </jsp:attribute>
    <jsp:body>
        <div id="clipDetail">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <div class="page-title">
                        <div class="title-left">
                            <h3>클립 메타 상세
                                <small>메타 관리 / <a href="/meta/clip">클립 메타 관리</a> / <b><a href="/meta/clip/${detailResult.clipMetaId}">클립 메타 상세</a></b></small>
                            </h3>
                        </div>
                        <div class="title-right">
                            <button type="button" class="btn btn-sm btn-success" id="detailBtnList">목록</button>
                            <button type="button" class="btn btn-sm btn-warning" id="detailBtnUpdate">수정</button>
                            <input type="hidden" id="detailClipMetaId" value="${detailResult.clipMetaId}"/>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${detailResult != null}">
                <clipTag:clip_detail_clip/>
            </c:if>
            <c:if test="${fn:length(detailResult.clipMediaList) > 0}">
                <clipTag:clip_detail_cdn/>
            </c:if>
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <h4 class="form-section">아티스트 정보</h4>
                    <div class="table-responsive">
                        <c:forEach var="item" items="${detailResult.artistList}">
                            <table class="table table-bordered table-condensed table-detail th-text-center">
                                <tr class="table-two-group-width">
                                    <th>아티스트ID</th>
                                    <td>${item.artistId}</td>
                                    <th>아티스트MID</th>
                                    <td>${item.originId}</td>
                                </tr>
                                <tr>
                                    <th>아티스트명 <span class="red">(한글)</span></th>
                                    <td>${fn:escapeXml(item.artistName)}</td>
                                    <th>아티스트명 <span class="red">(영문)</span></th>
                                    <td>${fn:escapeXml(item.artistNameEn)}</td>
                                </tr>
                                <tr>
                                    <th>아티스트 대문 이미지</th>
                                    <td colspan="3" class="img-href">
                                        <a data-toggle="modal" data-target="#lightbox">
                                            <img alt="" src="${item.artistMainImgUrl}" class="lightBox-Url"/>
                                                ${item.artistMainImgUrl}
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <th>아티스트 프로필 이미지</th>
                                    <td colspan="3" class="img-href">
                                        <a data-toggle="modal" data-target="#lightbox">
                                            <img src="${item.artistProfileImgUrl}" class="lightBox-Url"/>
                                                ${item.artistProfileImgUrl}
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <h4 class="form-section">곡 정보</h4>
                    <div class="table-responsive">
                        <c:forEach var="item" items="${detailResult.songList}">
                            <table class="table table-bordered table-condensed table-detail th-text-center">
                                <tr class="table-two-group-width">
                                    <th>곡ID</th>
                                    <td>${item.songId}</td>
                                    <th>곡MID</th>
                                    <td>${item.originId}</td>
                                </tr>
                                <tr>
                                    <th>곡명 <span class="red">(한글)</span></th>
                                    <td>${fn:escapeXml(item.songName)}</td>
                                    <th>곡명 <span class="red">(영문)</span></th>
                                    <td>${fn:escapeXml(item.songNameEn)}</td>
                                </tr>
                                <tr>
                                    <th>발매일</th>
                                    <td><fmt:formatDate pattern = "yyyy-MM-dd"  value = "${item.publishDt}"/></td>
                                    <th>활동장르</th>
                                    <td>
                                        <c:forEach var="songGenre" items="${songGenre}">
                                            <c:if test="${songGenre.genreId == item.genreId}"><span>${songGenre.genreName}</span></c:if>
                                        </c:forEach>
                                    </td>
                                </tr>
                                <tr>
                                    <th>앨범 이미지</th>
                                    <td colspan="3" class="img-href">
                                        <a data-toggle="modal" data-target="#lightbox">
                                            <img src="${item.albumImgUrl}" class="lightBox-Url"/>
                                                ${item.albumImgUrl}
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div id="clipUpdate" style="display: none;">
            <form id="formClip" data-parsley-validate>
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div class="page-title">
                            <div class="title-left">
                                <h3>클립 메타 수정
                                    <small>메타 관리 / <a href="/meta/clip">클립 메타 관리</a> / <b><a href="/meta/clip/${detailResult.clipMetaId}">클립 메타 수정</a></b></small>
                                </h3>
                            </div>
                            <div class="title-right">
                                <button type="button" class="btn btn-sm btn-danger" id="updateBtnClose">닫기</button>
                                <button type="button" class="btn btn-sm btn-mint" id="updateBtnSave">저장</button>
                                <input type="hidden" id="updateClipMetaId" value="${detailResult.clipMetaId}"/>
                                <input type="hidden" id="updateProgramId" value="${detailResult.programId}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <c:if test="${detailResult != null}">
                    <clipTag:clip_update_clip/>
                </c:if>
                <c:if test="${fn:length(detailResult.clipMediaList) > 0}">
                    <clipTag:clip_detail_cdn/>
                </c:if>
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <h4 class="form-section">아티스트 정보</h4>
                        <div class="table-responsive">
                            <table class="table table-bordered table-condensed table-create th-text-center">
                                <tr class="table-two-after-one-group-width">
                                    <th>아티스트검색</th>
                                    <td name="artistSearch" class="cutomInputGroup">
                                        <div class="td-ul-overflow input-group">
                                            <input type="text" class="form-control" id="artistSearch" name="searchInputName" value="" placeholder="아티스트 검색" autocomplete="off">
                                            <input type="hidden" name="searchInputId" value=""/>
                                            <ul class="list-group absolute-ul"></ul>
                                            <span class="input-group-btn">
                                            <button type="button" class="btn btn-xs btn-info" name="btnArtistAdd"><strong>추가</strong></button>
                                        </span>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="table-responsive" id="artistInfo">
                            <c:forEach var="item" items="${detailResult.artistList}">
                                <table class="table table-bordered  table-condensed table-detail th-text-center">
                                    <tr class="table-two-group-width">
                                        <th>아티스트ID</th>
                                        <td name="checkArtistId">${item.artistId}</td>
                                        <th>아티스트MID</th>
                                        <td>${item.originId}
                                            <button type="button" class="btn btn-xxs btn-danger btn-clip-info-delete"><i class="fa fa-times"></i></button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>아티스트명 <span class="red">(한글)</span></th>
                                        <td>${fn:escapeXml(item.artistName)}</td>
                                        <th>아티스트명 <span class="red">(영문)</span></th>
                                        <td name="clipArtistNameEn">${fn:escapeXml(item.artistNameEn)}</td>
                                    </tr>
                                    <tr>
                                        <th>아티스트 대문 이미지</th>
                                        <td colspan="3" class="img-href">
                                            <a data-toggle="modal" data-target="#lightbox">
                                                <img src="${item.artistMainImgUrl}" class="lightBox-Url"/>
                                                    ${item.artistMainImgUrl}
                                            </a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>아티스트 프로필 이미지</th>
                                        <td colspan="3" class="img-href">
                                            <a data-toggle="modal" data-target="#lightbox">
                                                <img src="${item.artistProfileImgUrl}" class="lightBox-Url"/>
                                                    ${item.artistProfileImgUrl}
                                            </a>
                                        </td>
                                    </tr>
                                </table>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 expand-clip-min-height">
                        <h4 class="form-section">곡 정보</h4>
                        <div class="table-responsive">
                            <table class="table table-bordered table-condensed table-create th-text-center">
                                <tr class="table-two-after-one-group-width">
                                    <th>곡검색</th>
                                    <td name="songSearch" class="cutomInputGroup">
                                        <div class="td-ul-overflow input-group">
                                            <input type="text" class="form-control" id="songSearch" name="searchInputName" value="" placeholder="곡 검색" autocomplete="off">
                                            <input type="hidden" name="searchInputId" value=""/>
                                            <ul class="list-group absolute-ul"></ul>
                                            <span class="input-group-btn">
                                                <button type="button" class="btn btn-xs btn-info" name="btnSongAdd"><strong>추가</strong></button>
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="table-responsive" id="songInfo">
                            <c:forEach var="item" items="${detailResult.songList}">
                                <table class="table table-bordered  table-condensed table-detail th-text-center">
                                    <tr class="table-two-group-width">
                                        <th>곡ID</th>
                                        <td name="checkSongId">${item.songId}</td>
                                        <th>곡MID</th>
                                        <td>${item.originId}
                                            <button type="button" class="btn btn-xxs btn-danger btn-clip-info-delete">
                                                <i class="fa fa-times"></i>
                                            </button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>곡명 <span class="red">(한글)</span></th>
                                        <td>${fn:escapeXml(item.songName)}</td>
                                        <th>곡명 <span class="red">(영문)</span></th>
                                        <td name="clipSongNameEn">${fn:escapeXml(item.songNameEn)}</td>
                                    </tr>
                                    <tr>
                                        <th>발매일</th>
                                        <td><fmt:formatDate pattern = "yyyy-MM-dd"  value = "${item.publishDt}"/></td>
                                        <th>활동장르</th>
                                        <td>
                                            <c:forEach var="songGenre" items="${songGenre}">
                                                <c:if test="${songGenre.genreId == item.genreId}"><span>${songGenre.genreName}</span></c:if>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>앨범 이미지</th>
                                        <td colspan="3" class="img-href">
                                            <a data-toggle="modal" data-target="#lightbox">
                                                <img src="${item.albumImgUrl}"
                                                     class="lightBox-Url"/>${item.albumImgUrl}</a>
                                        </td>
                                    </tr>
                                </table>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div id="lightbox" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <button type="button" class="close hidden" data-dismiss="modal" aria-hidden="true"><i
                        class="fa fa-times"></i></button>
                <div class="modal-content">
                    <div class="modal-body">
                        <img src=""/>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
