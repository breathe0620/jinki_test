<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>블렌딩 운영 서비스</title>

    <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
    <link href="/dist/css/sb-admin-2.css" rel="stylesheet">
    <link href="/css/common.css" rel="stylesheet">
    <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    -->
</head>
<body>

<div class="container">
    <form class="form-horizontal" id="signupForm" action="/signup-super" data-parsley-validate>
        <div class="panel panel-primary margin-default-signup-super">
            <div class="panel-heading">
                슈퍼 관리자 회원 가입
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="inputName">이름</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="inputName" name="adminName" placeholder="이름을 입력하세요" data-parsley-required="true" data-parsley-trigger="change" data-parsley-minlength="2">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="inputId">아이디</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="inputId" name="adminId" placeholder="아이디를 입력하세요" data-parsley-required="true" data-parsley-trigger="change"
                               data-parsley-minlength="5" data-parsley-type="email">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="inputPassword">비밀번호</label>
                    <div class="col-sm-4">
                        <input type="password" class="form-control" id="inputPassword" name="adminPwd" placeholder="비밀번호를 입력하세요 (영문+숫자 6자리 이상)" data-parsley-required="true"
                               data-parsley-trigger="change" data-parsley-minlength="6" data-parsley-pattern="/^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="inputConfirm">비밀번호 확인</label>
                    <div class="col-sm-4">
                        <input type="password" class="form-control" id="inputConfirm" placeholder="비밀번호를 다시 한번 입력하세요" data-parsley-required="true" data-parsley-trigger="change"
                               data-parsley-minlength="6" data-parsley-equalto="#inputPassword" data-parsley-pattern="/^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="inputPhoneNum">전화번호</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="inputPhoneNum" name="phoneNum" placeholder="전화번호를 입력하세요" data-parsley-required="true" data-parsley-trigger="change" data-parsley-type="digits" data-parsley-length="[10, 11]">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="inputSecretKey">확인키</label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" id="inputSecretKey" name="secretKey" placeholder="확인키를 입력하세요" data-parsley-required="true" data-parsley-trigger="change">
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <input type="submit" class="btn btn-primary pull-right" id="btnSuperSignup" value="가입"/>
                <div class="clearfix"></div>
            </div>
        </div>
    </form>

</div>


<script src="/vendor/jquery/jquery.min.js"></script>
<script src="/js/parsley.min.js"></script>
<script src="/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="/vendor/metisMenu/metisMenu.min.js"></script>
<script src="/dist/js/sb-admin-2.js"></script>
<script src="/js/common.js"></script>

</body>
</html>