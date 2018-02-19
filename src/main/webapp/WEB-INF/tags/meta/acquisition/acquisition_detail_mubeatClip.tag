<%@tag description="acquisition_detail_mubearClip" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <h4 class="form-section">뮤빗 클립정보</h4>
        <div class="table-responsive">
            <table class="table table-bordered table-condensed table-detail th-text-center">
                <tr class="table-two-group-width">
                    <th>입수상태</th>
                    <td class="detail-label-fixed">
                        <c:choose>
                            <c:when test="${detailResult.clipAcquisitionDTO.uploadYn == 'N'}">
                                <span class="label label-warning">미입수</span>
                            </c:when>
                            <c:when test="${detailResult.clipAcquisitionDTO.uploadYn == 'W'}">
                                <span class="label label-default">대기</span>
                            </c:when>
                            <c:when test="${detailResult.clipAcquisitionDTO.uploadYn == 'P'}">
                                <span class="label label-info">시작</span>
                            </c:when>
                            <c:when test="${detailResult.clipAcquisitionDTO.uploadYn == 'Y'}">
                                <span class="label label-success">성공</span>
                            </c:when>
                            <c:otherwise>
                                <span class="label label-danger">실패</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <th>변환상태</th>
                    <td class="detail-label-fixed">
                        <c:choose>
                            <c:when test="${detailResult.clipAcquisitionDTO.transcodeYn == 'N'}">
                                <span class="label label-warning">미변환</span>
                            </c:when>
                            <c:when test="${detailResult.clipAcquisitionDTO.transcodeYn == 'P'}">
                                <span class="label label-info">시작</span>
                            </c:when>
                            <c:when test="${detailResult.clipAcquisitionDTO.transcodeYn == 'Y'}">
                                <span class="label label-success">성공</span>
                            </c:when>
                            <c:otherwise>
                                <span class="label label-danger">실패</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>플레이시간</th>
                    <td><span>${detailResult.clipMetaDTO.playTime}</span></td>
                    <th>카테고리</th>
                    <td class="detail-label-fixed">
                        <c:choose>
                            <c:when test="${detailResult.clipMetaDTO.clipCategory == '02'}">
                                <span class="label label-primary">예능</span>
                            </c:when>
                            <c:when test="${detailResult.clipMetaDTO.clipCategory == '03'}">
                                <span class="label label-info">음악</span>
                            </c:when>
                            <c:otherwise>
                                <span class="label">N/A</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>방송일자</th>
                    <td colspan="3">${detailResult.clipMetaDTO.broadDate}</td>
                </tr>
                <tr>
                    <th>클립명</th>
                    <td colspan="3" class="padding-zero input-parsley">
                        <input type="text" name="detail" value="${fn:escapeXml(detailResult.clipMetaDTO.clipTitle)}" readonly>
                        <input type="text" name="update" class="form-control" id="updateClipTitle" value="${fn:escapeXml(detailResult.clipMetaDTO.clipTitle)}" style="display: none;">
                    </td>
                </tr>
                <c:if test="${cookie.TYPE.value == 'S'}">
                    <tr>
                        <th width="20%" class="text-center">원본 영상</th>
                        <td colspan="3" class="text-center">
                            <button type="button" id="showVideo" class="btn btn-xs btn-info" value="${detailResult.originalVideo}">원본영상보기</button>
                        </td>
                    </tr>
                </c:if>
            </table>
        </div>
    </div>
</div>
