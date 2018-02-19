<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
    <jsp:attribute name="javascriptSrc">
      <script src="/js/meta/song.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>곡 메타 등록
                            <small>메타 관리 / <a href="/meta/song">곡 메타 관리</a> / <b><a href="/meta/song/create">곡 메타 등록</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" class="btn btn-sm btn-danger" id="createBtnClose">닫기</button>
                        <button type="button" class="btn btn-sm btn-mint" id="createBtnSave">저장</button>
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
                                    <input type="text" class="form-control" placeholder="자동생성" readonly>
                                </td>
                                <th>곡 MID</th>
                                <td>
                                    <input type="number" id="createOriginId" class="form-control" placeholder="곡 MID" autocomplete="off">
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 곡명 <span class="red">(한글)</span></th>
                                <td class="input-parsley">
                                    <input type="text" class="form-control" id="createSongName" placeholder="곡명"
                                           data-parsley-required="true" data-parsley-minlength="1"
                                           autocomplete="off" data-parsley-trigger="change" maxlength="128">
                                </td>
                                <th>곡명 <span class="red">(영문)</span></th>
                                <td class="input-parsley">
                                    <input type="text" class="form-control" id="createSongNameEn"
                                           placeholder="곡명(EN)" data-parsley-minlength="1"
                                           maxlength="128" autocomplete="off">
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 장르타입</th>
                                <td class="input-parsley">
                                    <select class="js-example-basic-single" id="selectGenre" name="createTypeSelect" data-parsley-required="true">
                                        <option value="" selected>선택</option>
                                        <c:forEach var="genreItem" items="${genreList}">
                                            <option value="${genreItem.genreId}">${genreItem.genreName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td colspan="2"></td>
                            </tr>
                            <tr>
                                <th>앨범</th>
                                <td>
                                    <input type="text" id="albumSearch" name="searchInputName" class="form-control" value="" placeholder="앨범명" autocomplete="off">
                                    <input type="hidden" name="searchInputId" value=""/>
                                    <ul class="list-group"></ul>
                                </td>
                                <th>발매일</th>
                                <td>
                                    <input type='text' class="form-control" id="publishDt" placeholder="자동생성" autocomplete="off" readonly/>
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 아티스트ID</th>
                                <td colspan="3" class="form-inline" name="artist">
                                    <input type="text" id="artistSearch" name="searchInputName" class="form-control" value="" placeholder="아티스트검색" autocomplete="off">
                                    <input type="hidden" name="searchInputId" value=""/>
                                    <ul class="list-group"></ul>
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 가사</th>
                                <td colspan="3" class="input-parsley">
                                    <textarea class="form-control" id="createLyric" placeholder="가사" data-parsley-required="true" autocomplete="off"></textarea>
                                </td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </jsp:body>
</t:template>
