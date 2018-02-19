<%--
  Created by IntelliJ IDEA.
  User: moonkyu
  Date: 2017. 8. 25.
  Time: PM 2:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:template title="블랜딩 운영 서비스" openNavClass="nav-1-4">
    <jsp:attribute name="javascriptSrc">
        <link href="/vendor/retention-graph/retention-graph.css" rel="stylesheet">
        <link href="/vendor/daterangepicker/daterangepicker.css" rel="stylesheet">
        <script src="/vendor/daterangepicker/daterangepicker.js"></script>
        <script src="/webjars/sockjs-client/sockjs.min.js"></script>
        <script src="/webjars/stomp-websocket/stomp.min.js"></script>
        <script src="/vendor/retention-graph/retention-graph.js"></script>
        <script src="/js/manager/analysis.js"></script>
</jsp:attribute>
    <jsp:body>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-md-12">
                    <div class="ibox ibox-heading">
                        <div class="ibox-title">
                            <h5><i class="fa fa-search"></i>Analysis 조회</h5>
                            <div class="ibox-tools">
                                <a class="reload-link" id="pageRefresh">
                                    <i class="fa fa-undo hide"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <div class="row">
                                <div class="col-md-3">
                                    <label class="control-label col-md-4">Type</label>
                                    <div class="col-md-8">
                                        <select id="chartType" name="type">
                                            <option value="">선택</option>
                                            <option value="retention" selected>retention</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label class="control-label col-md-4">Date</label>
                                    <div class='input-group date' id='analysisDtpicker' style="padding-left: 15px; padding-right: 15px;">
                                        <input type='text' class="form-control col-md-8" name="date" id="analysisDtStart"/>
                                        <span class="input-group-addon" style="border-right:1px solid #aaa;"><span class="glyphicon glyphicon-calendar"></span></span>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label class="control-label col-md-4">Environment</label>
                                    <div class="col-md-8">
                                        <select id="analysisType" name="environment">
                                            <option value="">선택</option>
                                            <option value="dev" selected>Dev</option>
                                            <option value="live">Live</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <input type="hidden" name="day" id="analysisDay" value="7">
                                    <input type="hidden" name="log" id="analysisLog" value="false">
                                    <button type='button' class='btn btn-primary' id='btnAnalysis'>분석요청</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row" style="margin-bottom: 20px;">
                <div class="col-md-12">
                    <h4 class="form-section" style="margin-bottom: 10px !important;">Analysis Status</h4>
                    <div id="status">
                        <div id="statusMessage">

                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div id="result"></div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>