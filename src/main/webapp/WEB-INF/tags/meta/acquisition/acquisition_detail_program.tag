<%@tag description="acquisition_detail_program" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <h4 class="form-section">입수 프로그램정보</h4>
        <div class="table-responsive">
            <table class="table table-bordered table-condensed table-detail th-text-center">
                <tr class="table-two-group-width">
                    <th>제공사</th>
                    <td>${detailResult.clipMetaDTO.corporator}</td>
                    <th>프로그램명</th>
                    <td>
                        ${fn:escapeXml(detailResult.clipMetaDTO.programTitle)}
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>