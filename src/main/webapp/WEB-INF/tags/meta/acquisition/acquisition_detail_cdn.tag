<%@tag description="acquisition_detail_cdn" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="row">
    <div class="col-md-12">
        <h4 class="form-section">뮤빗 CDN정보</h4>
        <div class="table-responsive">
            <table class="table table-bordered table-condensed table-detail th-text-center">
                <tr>
                    <th width="20%">CDN</th>
                    <c:forEach var="item" items="${detailResult.clipMediaList}" varStatus="status">
                        <c:if test="${not status.last}">
                            <c:forTokens var="path" items="${item.mediaPath}" delims="/" varStatus="titleStatus">
                                <c:if test="${titleStatus.last}">
                                    <c:set var="subPath" value="${fn:substring(path,0,fn:indexOf(path,'.m3u8'))}"/>
                                    <c:set var="result" value="${fn:substring(subPath,fn:indexOf(subPath,'_')+1,fn:length(subPath)) }"/>
                                </c:if>
                            </c:forTokens>
                            <td class="text-center">
                                <button type="button" class="btn btn-xs btn-info" name="btnCdn" value="${subPath}">${result} 보기</button>
                            </td>
                        </c:if>
                    </c:forEach>
                </tr>
            </table>
        </div>
    </div>
</div>