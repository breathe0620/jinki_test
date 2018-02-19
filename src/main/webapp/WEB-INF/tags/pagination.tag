<%@tag description="Pagenation template" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="blockSize" value="10"/>
<fmt:parseNumber var="firstBlock" integerOnly="true" type="number" value="${(result.pageResult.number / blockSize) / blockSize}" />
<fmt:parseNumber var="lastBlock" integerOnly="true" type="number" value="${result.pageResult.totalPages - 1}" />
<fmt:parseNumber var="curBlock" integerOnly="true" type="number" value="${result.pageResult.number / blockSize}" />
<fmt:parseNumber var="start" integerOnly="true" type="number" value="${curBlock * blockSize}"/>
<fmt:parseNumber var="end" integerOnly="true" type="number" value="${start + blockSize - 1}"/>
<c:set var="isFirst" value="false"/>
<c:set var="isLast" value="false"/>
<c:if test="${curBlock == 0}">
    <c:set var="isFirst" value="true"/>
</c:if>
<c:if test="${result.pageResult.totalPages <= end + 1}">
    <c:set var="isLast" value="true"/>
    <fmt:parseNumber var="end" integerOnly="true" type="number" value="${result.pageResult.totalPages - 1}"/>
</c:if>
<input type="hidden" name="page"/>
<div class="row">
    <div class="col-lg-12 text-center">
        <ul class="pagination">
          <c:if test="${isFirst == false}">
              <c:if test="${start > 90}">
                  <li class="pagination_button previous">
                      <a class="pagination-arrow" href="javascript:goToPage(${start - 100})"><i class="fa fa-angle-double-left"></i></a>
                  </li>
              </c:if>
              <li class="pagination_button previous">
                  <a class="pagination-arrow" href="javascript:goToPage(${start - 10})"><i class="fa fa-angle-left"></i></a>
              </li>
              <li class="pagination_button previous">
                  <a href="javascript:goToPage(0)">1</a>
              </li>
              <li><span class='paginationEllipsis'>...</span></li>
          </c:if>
            <c:forEach begin="${start}" end="${end}" varStatus="loop">
                <c:choose>
                    <c:when test="${result.pageResult.number == loop.current}">
                        <li class="pagination_button active">
                            <a href="javascript:goToPage(${loop.current})">${loop.current + 1}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="pagination_button">
                            <a href="javascript:goToPage(${loop.current})">${loop.current + 1}</a>
                        </li>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
            <c:if test="${isLast == false}">
                <li><span class='paginationEllipsis'>...</span></li>
                <li class="pagination_button next">
                    <a href="javascript:goToPage(${lastBlock})">${lastBlock + 1}</a>
                </li>
                <li class="pagination_button next">
                    <a class="pagination-arrow" href="javascript:goToPage(${end + 1})"><i class="fa fa-angle-right"></i></a>
                </li>
                <li class="pagination_button next">
                    <a class="pagination-arrow" href="javascript:goToPage(${end + 91})"><i class="fa fa-angle-double-right"></i></a>
                </li>
            </c:if>
        </ul>
    </div>
</div>
