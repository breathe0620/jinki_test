<%@tag description="acquisition_detail_content" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <h4 class="form-section">입수 회차정보</h4>
        <div class="table-responsive">
            <table class="table table-bordered table-condensed table-detail th-text-center">
                <tr class="table-two-group-width">
                    <th>방송일자</th>
                    <td>
                        <fmt:parseDate var="broadDate" value="${detailResult.contentDTO.broadDate}" pattern="yyyyMMdd" />
                        <fmt:formatDate value="${broadDate}" pattern="yyyy-MM-dd" />
                    </td>
                    <th>회차</th>
                    <td>${detailResult.contentDTO.number}</td>
                </tr>
            </table>
        </div>
    </div>
</div>