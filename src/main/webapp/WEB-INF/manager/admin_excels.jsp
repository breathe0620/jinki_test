<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-6">
    <jsp:attribute name="javascriptSrc">
        <script src="/js/manager/admin_excel.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <h3 class="page-header header-title">Excel 한글 관리 리스트</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <div class="table-responsive">
                    <table class="table table-list table-bordered table-condensed table-striped table-custom">
                        <thead class="th-text-center">
                            <tr>
                                <th width="16.6%">아티스트</th>
                                <th width="16.6%">앨범</th>
                                <th width="16.6%">곡</th>
                                <th width="16.6%">소속사</th>
                                <th width="16.6%">프로그램</th>
                                <th width="16.6%">클립</th>
                            </tr>
                        </thead>
                        <tbody class="td-text-center">
                            <tr>
                                <td><a class="btn btn-sm btn-info btn-excel" href="/meta/artist/excel">다운로드</a></td>
                                <td><a class="btn btn-sm btn-info btn-excel" href="/meta/album/excel">다운로드</a></td>
                                <td><a class="btn btn-sm btn-info btn-excel" href="/meta/song/excel">다운로드</a></td>
                                <td><a class="btn btn-sm btn-info btn-excel" href="/meta/agency/excel">다운로드</a></td>
                                <td><a class="btn btn-sm btn-info btn-excel" href="/meta/program/excel">다운로드</a></td>
                                <td><a class="btn btn-sm btn-info btn-excel" href="/meta/clip/excel">다운로드</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12 col-md-12">
                <h3 class="page-header header-title">Excel 영문 관리 리스트</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12 col-md-12 alert alert-success">
                <div class="col-sm-12 col-md-6">
                    <li>Excel 영문 파일을 업로드 하기전 Excel 한글 관리의 파일을 다운로드 후 해당 Excel 파일의 양식과 일치 시켜 올려야 합니다.</li>
                    <li>Excel 파일 내 고유번호, 영문 (그룹 영문 제외) 열의 내용만 반영이 됩니다. 또한 값이 비어있지 않을 경우에만 수정됩니다.</li>
                </div>
                <div class="col-sm-12 col-md-6">
                    <li>컬럼 설명에 (숫자) 라고 되어 있는 부분은 반드시 숫자이어야 합니다.</li>
                    <li><span class="langHighlight">데이터를 밀려 쓰게되면 복구가 안될 위험이 있으니 주의하시기 바랍니다.</span></li>
                </div>
            </div>
            <div class="col-sm-12 col-md-12">
                <div class="table-responsive">
                    <table class="table table-list table-bordered table-condensed table-excel">
                        <tbody class="th-text-center">
                        <tr>
                            <th width="15%">아티스트</th>
                            <td width="35%">
                                <input type="file" class="input-excel" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                <input type="hidden" value="/meta/artist/excel">
                                <a class="btn btn-sm btn-mint btn-excel" name="uploadExcelEn">등록</a>
                            </td>
                            <th width="15%">앨범</th>
                            <td width="35%">
                                <input type="file" class="input-excel" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                <input type="hidden" value="/meta/album/excel">
                                <a class="btn btn-sm btn-mint btn-excel" name="uploadExcelEn">등록</a>
                            </td>
                        </tr>
                        <tr>
                            <th>곡</th>
                            <td>
                                <input type="file" class="input-excel" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                <input type="hidden" value="/meta/song/excel">
                                <a class="btn btn-sm btn-mint btn-excel" name="uploadExcelEn">등록</a>
                            </td>
                            <th>소속사</th>
                            <td>
                                <input type="file" class="input-excel" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                <input type="hidden" value="/meta/agency/excel">
                                <a class="btn btn-sm btn-mint btn-excel" name="uploadExcelEn">등록</a>
                            </td>
                        </tr>
                        <tr>
                            <th>프로그램</th>
                            <td>
                                <input type="file" class="input-excel" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                <input type="hidden" value="/meta/program/excel">
                                <a class="btn btn-sm btn-mint btn-excel" name="uploadExcelEn">등록</a>
                            </td>
                            <th>클립</th>
                            <td>
                                <input type="file" class="input-excel" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                <input type="hidden" value="/meta/clip/excel">
                                <a class="btn btn-sm btn-mint btn-excel" name="uploadClipExcelEn">등록</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
