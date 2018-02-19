<%@tag description="acquisition_detail_clip" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <h4 class="detail-section"> 클립 정보</h4>
        <div class="table-responsive">
            <table class="table table-bordered table-condensed table-create th-text-center">
                <tr>
                    <th width="20%">카테고리</th>
                    <td width="30%" class="update-label-fixed">
                        <c:choose>
                            <c:when test="${detailResult.clipCategory == '02'}">
                                <span class="label label-primary">예능</span>
                            </c:when>
                            <c:when test="${detailResult.clipCategory == '03'}">
                                <span class="label label-info">음악</span>
                            </c:when>
                            <c:otherwise>
                                <span class="label label-danger">미입력</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <th width="20%">상세 카테고리</th>
                    <td width="30%" class="input-parsley">
                        <select id="selectCategory" data-parsley-required="true">
                            <option value="">카테고리 선택</option>
                            <c:forEach var="item" items="${clipDetailCategory}">
                                <option value="${item.clipClassify}">
                                    <span id="updateClipClassify" class="text-left">${item.clipClassify} : ${item.name}</span>
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>독점 여부</th>
                    <td class="text-center">
                        <div class="custom-checkbox create-custom-checkbox">
                            <input type="checkbox" id="updateIsOriginal" name="updateIsOriginal"/>
                            <label for="updateIsOriginal"></label>
                        </div>
                    </td>
                    <th>노출여부</th>
                    <td class="text-center update-td">
                        <div class="form-inline custom-radio">
                            <label class="radio-inline"><input type="radio" name="radioEnable" value="Y">노출</label>
                            <label class="radio-inline"><input type="radio" name="radioEnable" value="N">미노출</label>
                            <label class="radio-inline"><input type="radio" name="radioEnable" value="T">폐기</label>
                            <label class="radio-inline"><input type="radio" name="radioEnable" value="R">보류</label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>클립ID</th>
                    <td><input type="text" value="${detailResult.clipId}" readonly/></td>
                    <th>플레이시간</th>
                    <td><input type="text" value="${detailResult.playTime}" readonly/> </td>
                </tr>
                <tr>
                    <th>방송일자</th>
                    <td>
                        <fmt:parseDate var="broadDt" value="${detailResult.broadDt}" pattern="yyyyMMdd" />
                        <input type="text" value="<fmt:formatDate value="${broadDt}" pattern="yyyy-MM-dd" />" readonly/>
                    </td>
                    <th>제공사</th>
                    <td><input type="text" value="${detailResult.corporator}" readonly/></td>
                </tr>
                <tr>
                    <th width="20%">클립명 <span class="red">(한글)</span></th>
                    <td colspan="3" class="input-parsley">
                        <input type="text" class="form-control" id="updateClipTitle" value="${fn:escapeXml(detailResult.clipTitle)}" data-parsley-required="true">
                    </td>
                </tr>
                <tr>
                    <th>클립명 <span class="red">(영문)</span></th>
                    <td colspan="3" class="input-parsley">
                        <div class="input-group input-group-title">
                            <input type="text" class="form-control" id="updateClipTitleEn" value="${fn:escapeXml(detailResult.clipTitleEn)}">
                            <span class="input-group-btn"><button type="button" class="btn btn-sm btn-info" id="autoCreateClipTitleEn">자동생성</button></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>프로그램명 <span class="red">(한글)</span></th>
                    <td colspan="3"><input type="text" id="updateProgramTitle"  value="${fn:escapeXml(detailResult.programTitle)}" readonly/></td>
                </tr>
                <tr>
                    <th>프로그램명 <span class="red">(영문)</span></th>
                    <td colspan="3"><input type="text" id="updateProgramTitleEn" value="${fn:escapeXml(detailResult.programTitleEn)}" readonly/></td>
                </tr>
                <tr>
                    <th>썸네일이미지</th>
                    <td colspan="3" class="update-fixed">
                        <a data-toggle="modal" data-target="#lightbox">
                            <img src="${detailResult.clipMainImgUrl}" class="lightBox-Url"/>
                            ${detailResult.clipMainImgUrl}
                        </a>
                    </td>
                </tr>
                <c:if test="${cookie.TYPE.value == 'S'}">
                    <tr>
                        <th>원본 영상</th>
                        <td colspan="3" class="text-center">
                            <button type="button" name="showVideo" class="btn btn-xs btn-info" style="margin-top: 3px;" value="${detailResult.clipUrl}">원본영상보기</button>
                        </td>
                    </tr>
                </c:if>
            </table>
        </div>
    </div>
</div>