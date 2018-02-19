<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib tagdir="/WEB-INF/tags/meta/acquisition" prefix="acquisitionTag" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
    <jsp:attribute name="javascriptSrc">
      <script src="/js/meta/acquisition.js"></script>
     </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="page-title">
                    <div class="title-left">
                        <h3>방송정보 입수 상세
                            <small>서비스 관리 / <a href="/meta/acquisition">방송정보 입수</a> / <b><a href="/meta/acquisition/${detailResult.clipMetaDTO.clipMetaId}">방송정보 입수 상세</a></b></small>
                        </h3>
                    </div>
                    <div class="title-right">
                        <button type="button" name="detail" class="btn btn-sm btn-success" id="detailBtnList">목록</button>
                        <button type="button" name="detail" class="btn btn-sm btn-warning" id="detailBtnUpdate">수정</button>
                        <button type="button" name="update" class="btn btn-sm btn-danger" id="updateBtnClose" style="display: none;">닫기</button>
                        <button type="button" name="update" class="btn btn-sm btn-mint" id="updateBtnSave" style="display: none;">저장</button>
                    </div>
                </div>
            </div>
        </div>

        <input type="hidden" id="detailClipMetaId" value="${detailResult.clipMetaDTO.clipMetaId}"/>
        <div id="detail_body">
            <form id="formAcquisition"  data-parsley-validate>
                <c:if test="${detailResult.clipDTO != null}">
                    <acquisitionTag:acquisition_detail_clip/>
                </c:if>
                <c:if test="${detailResult.clipMetaDTO.corporator != null}">
                    <acquisitionTag:acquisition_detail_program/>
                </c:if>
                <c:if test="${detailResult.contentDTO != null}">
                    <acquisitionTag:acquisition_detail_content/>
                </c:if>
                <c:if test="${detailResult.clipMetaDTO != null}">
                    <acquisitionTag:acquisition_detail_mubeatClip/>
                </c:if>
                <c:if test="${fn:length(detailResult.clipMediaList) > 0}">
                    <acquisitionTag:acquisition_detail_cdn/>
                </c:if>
            </form>
        </div>
    </jsp:body>
</t:template>
