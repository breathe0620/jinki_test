<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-3">
    <jsp:attribute name="javascriptSrc">
    <script src="/js/meta/album.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>곡 메타 등록
                            <small>메타 관리 / <a href="/meta/album">앨범 메타 관리</a> / <b><a href="/meta/album/create">앨범 메타 등록</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" class="btn btn-sm btn-danger" id="createBtnClose">닫기</button>
                        <button type="button" class="btn btn-sm btn-mint" id="createBtnSave">저장</button>
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
                                    <input type="text" class="form-control" id="createAlbumId" placeholder="자동생성"
                                           readonly>
                                </td>
                                <th>앨범 MID</th>
                                <td>
                                    <input type="number" class="form-control" id="createOriginId" placeholder="앨범 MID" autocomplete="off">
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 앨범명 <span
                                        class="red">(한글)</span></th>
                                <td class="input-parsley">
                                    <input type="text" class="form-control" id="createAlbumTitle" placeholder="앨범명"
                                           data-parsley-required="true" data-parsley-minlength="1"
                                           autocomplete="off" data-parsley-trigger="change" maxlength="100">
                                </td>
                                <th>앨범명 <span class="red">(영문)</span></th>
                                <td class="input-parsley">
                                    <input type="text" class="form-control" id="createAlbumTitleEN" maxlength="100"
                                           placeholder="앨범명(EN)" data-parsley-minlength="1" autocomplete="off">
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 앨범타입</th>
                                <td class="input-parsley">
                                    <select class="js-example-basic-single" id="selectAlbumType" name="createTypeSelect" data-parsley-required="true" data-parsley-trigger="change">
                                        <option value="" selected>선택</option>
                                        <c:forEach var="item" items="${albumTypeList}">
                                            <option value="${item.albumTypeId}">${item.albumTypeName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <th><i class="fa fa-exclamation red"></i> 발매일</th>
                                <td class="input-parsley">
                                    <div class='input-group date' id='publishDtPicker'>
                                        <input type='text' id="publishDt" placeholder="발매일" class="form-control"
                                               data-parsley-required="true" data-parsley-minlength="1"/>
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><i class="fa fa-exclamation red"></i> 아티스트ID</th>
                                <td colspan="3" class="form-inline" name="artist">
                                    <input type="text" id="artistSearch" name="searchInputName"
                                           class="form-control" value="" placeholder="아티스트검색"
                                           autocomplete="off">
                                    <input type="hidden" name="searchInputId" value=""/>
                                    <ul class="list-group"></ul>
                                </td>
                            </tr>
                            <tr>
                                <th rowspan="2">이미지업로드</th>
                                <td class="text-center">
                                    <label title="Upload image file" for="albumImg"
                                           class="btn btn-sm btn-primary label-fileInput">
                                        <input type="file" class="fileInput hide" name="albumImg" accept=".png, .jpg" id="albumImg"/>파일열기
                                    </label>
                                </td>
                                <td colspan="2"></td>
                            </tr>
                            <tr class="table-img-box">
                                <td>
                                    <img class="img-responsive img-preview" src="" target="_blank" id="albumImgView"/>
                                </td>
                                <td colspan="2"></td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </jsp:body>
</t:template>
