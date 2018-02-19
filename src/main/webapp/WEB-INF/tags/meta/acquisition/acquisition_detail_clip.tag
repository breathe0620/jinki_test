<%@tag description="acquisition_detail_clip" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="row">
    <div class="col-sm-12 col-md-12">
        <h4 class="form-section">입수 클립정보</h4>
        <div class="table-responsive">
            <table class="table table-bordered table-condensed table-detail th-text-center">
                <tr>
                    <th width="20%">클립ID</th>
                    <td width="30%">${detailResult.clipDTO.clipId}</td>
                    <th width="20%">카테고리</th>
                    <td width="30%" class="detail-label-fixed">
                        <c:choose>
                            <c:when test="${detailResult.clipDTO.clipCategory == '02'}">
                                <span class="label label-primary">예능</span>
                            </c:when>
                            <c:when test="${detailResult.clipDTO.clipCategory == '03'}">
                                <span class="label label-info">음악</span>
                            </c:when>
                            <c:otherwise>
                                <span class="label">N/A</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>플랫폼 사용여부</th>
                    <td>${detailResult.clipDTO.platformIsUse}</td>
                    <th>사용여부</th>
                    <td class="detail-label-fixed">
                        <c:choose>
                            <c:when test="${detailResult.clipDTO.isUse == 'Y'}">
                                <span class="label label-success">사용</span>
                            </c:when>
                            <c:when test="${detailResult.clipDTO.isUse == 'N'}">
                                <span class="label label-danger">미사용</span>
                            </c:when>
                            <c:otherwise>
                                <span class="label">N/A</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>방송일자</th>
                    <td colspan="3">
                        ${detailResult.clipDTO.broadDate}
                    </td>
                </tr>
                <tr>
                    <th>클립명</th>
                    <td colspan="3">
                        ${fn:escapeXml(detailResult.clipDTO.title)}
                    </td>
                </tr>
                <tr>
                    <th width="20%">상세정보</th>
                    <td colspan="3">
                        ${fn:escapeXml(detailResult.clipDTO.synopsis)}
                    </td>
                </tr>
                <tr>
                    <th>등록일</th>
                    <td>
                        <fmt:parseDate var="regDate" value="${detailResult.clipDTO.regDate}" pattern="yyyyMMddHHmmss" />
                        <fmt:formatDate value="${regDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <th>수정일</th>
                    <td>
                        <fmt:parseDate var="modifyDate" value="${detailResult.clipDTO.modifyDate}" pattern="yyyyMMddHHmmss" />
                        <fmt:formatDate value="${modifyDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                </tr>
                <tr>
                    <th>파일수정일</th>
                    <td>
                        ${detailResult.clipDTO.fileModifyDate}
                    </td>
                    <th></th>
                    <td></td>
                </tr>
            </table>
        </div>
    </div>
</div>