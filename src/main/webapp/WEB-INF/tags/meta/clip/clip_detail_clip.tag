<%@tag description="acquisition_detail_clip" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <h4 class="detail-section"> 클립 정보 <small><b>고유번호 : ${detailResult.clipMetaId}</b></small></h4>
        <div class="table-responsive">
            <table class="table table-bordered table-condensed table-detail th-text-center">
                <tr>
                    <th width="20%">카테고리</th>
                    <td width="30%" class="detail-label-fixed">
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
                    <td width="30%" class="detail-label-fixed">
                        <input type="hidden" name="detailClipClassify" value="${detailResult.clipClassify}"/>
                        <c:forEach var="item" items="${clipDetailCategory}">
                            <c:if test="${item.clipClassify == detailResult.clipClassify}">
                                <input type="hidden" name="detailClipClassifyName" value="${item.name}"/>
                                <c:choose>
                                    <c:when test="${item.clipClassify == '01'}">
                                        <span class="label label-category-01">${item.name}</span>
                                    </c:when>
                                    <c:when test="${item.clipClassify == '02'}">
                                        <span class="label label-category-02">${item.name}</span>
                                    </c:when>
                                    <c:when test="${item.clipClassify == '03'}">
                                        <span class="label label-category-03">${item.name}</span>
                                    </c:when>
                                    <c:when test="${item.clipClassify == '04'}">
                                        <span class="label label-category-04">${item.name}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="label label-category-05">${item.name}</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <th>독점 여부</th>
                    <td class="text-center">
                        <div class="custom-checkbox">
                            <c:choose>
                                <c:when test="${detailResult.isoriginal == 'Y'}">
                                    <input type="checkbox" id="detailIsOriginal" name="detailIsOriginal" value="Y" checked disabled/>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" id="detailIsOriginal" name="detailIsOriginal" value="N" disabled/>
                                </c:otherwise>
                            </c:choose>
                            <label for="detailIsOriginal"></label>
                        </div>
                    </td>
                    <th>노출여부</th>
                    <td class="detail-label-fixed">
                        <c:choose>
                            <c:when test="${detailResult.enableYn == 'Y'}">
                                <span class="label label-success"><input type="hidden" name="clipDetailEnable" value="Y"> 노출</span>
                            </c:when>
                            <c:when test="${detailResult.enableYn == 'N'}">
                                <span class="label label-danger"><input type="hidden" name="clipDetailEnable" value="N">미노출</span>
                            </c:when>
                            <c:when test="${detailResult.enableYn == 'R'}">
                                <span class="label label-warning"><input type="hidden" name="clipDetailEnable" value="R">보류</span>
                            </c:when>
                            <c:when test="${detailResult.enableYn == 'T'}">
                                <span class="label label-default"><input type="hidden" name="clipDetailEnable" value="T">폐기</span>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>클립ID</th>
                    <td>${detailResult.clipId}</td>
                    <th>플레이시간</th>
                    <td>${detailResult.playTime}</td>
                </tr>
                <tr>
                    <th>방송일자</th>
                    <td>
                        <fmt:parseDate var="broadDt" value="${detailResult.broadDt}" pattern="yyyyMMdd" />
                        <fmt:formatDate value="${broadDt}" pattern="yyyy-MM-dd" />
                    </td>
                    <th>제공사</th>
                    <td>${detailResult.corporator}</td>
                </tr>
                <tr>
                    <th width="20%">클립명 <span class="red">(한글)</span></th>
                    <td colspan="3">
                        ${fn:escapeXml(detailResult.clipTitle)}
                    </td>
                </tr>
                <tr>
                    <th>클립명 <span class="red">(영문)</span></th>
                    <td colspan="3">
                        ${fn:escapeXml(detailResult.clipTitleEn)}
                    </td>
                </tr>
                <tr>
                    <th>프로그램명 <span class="red">(한글)</span></th>
                    <td colspan="3">
                        ${fn:escapeXml(detailResult.programTitle)}
                    </td>
                </tr>
                <tr>
                    <th>프로그램명 <span class="red">(영문)</span></th>
                    <td colspan="3">
                        ${fn:escapeXml(detailResult.programTitleEn)}
                    </td>
                </tr>
                <tr>
                    <th>썸네일이미지</th>
                    <td colspan="3">
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
                            <button type="button" name="showVideo" class="btn btn-xs btn-info" value="${detailResult.clipUrl}">원본영상보기</button>
                        </td>
                    </tr>
                </c:if>
            </table>
        </div>
    </div>
</div>
