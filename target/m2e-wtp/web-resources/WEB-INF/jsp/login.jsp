<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录</title>
</head>
<script type="text/javascript"></script>
<%-- <script type="text/javascript" src="${path}/js/common/jquery-2.1.1.min.js"></script> --%>
<script src="${path}/js/common/jquery-1.12.3.js"></script>
<script type="text/javascript" src="${path}/js/common/jquery.md5.js"></script>
<script type="text/javascript" src="${path}/js/common/bootstrap/bootstrap.min.js"></script>
<script src="${path}/js/common/jquery.validate.js" type="text/javascript"></script>
<script src="${path}/js/common/messages_zh.js" type="text/javascript"></script>
<script src="${path}/js/common/jquery.metadata.js" type="text/javascript"></script>
<script src="${path}/js/common/login.js" type="text/javascript"></script>

<link rel="stylesheet" href="${path}/css/common/bootstrap/bootstrap-theme.min.css" type="text/css">
<link rel="stylesheet" href="${path}/css/common/bootstrap/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="${path}/css/common/bootstrap/bootstrap-admin-theme.css" type="text/css">
<style type="text/css">
    .alert{
        margin: 0 auto 20px;
    }
    .container{
    margin-top: 200px
    }
</style>
<body class="bootstrap-admin-without-padding" style="background-position: center;background-repeat: no-repeat;background-size:cover;background-image: url('${path}/imgs/login.png');">
        <div class="container" >
            <div class="row">
                <div class="col-lg-12" style="text-align: center;">
                 <div style="position: relative;margin: 0 auto;margin-bottom: 30px">
                        <label><font size="50px" style="color:#09f;font-style: !important;" >&nbsp;赛翼智慧社区云平台</font></label>
                 
                 </div>
                    
                    <form method="post" id="loginForm" action="${path}/mc/user/loginForm.do" class="bootstrap-admin-login-form alert-info">
                        <input type="hidden" class="carrierNum" name="carrier.carrierNum" value="${carrierNum}"/>
                        <table style="width: 100%;padding-top: 10px;text-align: left;">
                           <tr>
                             <td><label>用户名：</label></td>
                             <td>
	                              <div style="margin-top: 20px"><input class="form-control required loginName" type="text" name="loginName" placeholder="用户名 ">
	                              </div>
                              </td>
                           </tr>
                            <tr>
                             <td><div style="margin-top: 20px"><label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label></div></td>
                             <td>
                                 <div style="margin-top: 20px"><input class="form-control required password" type="password" name="password" placeholder="密码">
<%--                                  <label>${password}</label> --%> 
                                 </div>
                             </td>
                           </tr>
                           <tr>
                              <td><label>验证码：</label></td>
                              <td>
                                <div style="margin-top: 20px">
	                                 <div class="input-group">
									      <input type="text" class="form-control required verificationCode" maxlength="6" value="" name="verificationCode" placeholder="验证码">
									      <span class="input-group-btn">
									        <button class="btn btn-info getVerificationCode" type="button" rel="${path}/mc/user/getVerificationCode.do" style="height: 39px;padding-top: 6px">获取验证码</button>
									      </span>
									  </div>
									      <label class='msg'>${errorMessg}</label>
								 </div>
                              </td>  
                           </tr>
                           <tr>
                             <td></td>
                             <td>
                                <div style="text-align: left ;margin-top: 20px">
                                  <button class="btn btn-xs btn-primary" style="width: 230px" type="submit">登录</button>
                                </div>
                             </td>
                           </tr>
                        </table>
                    </form>
                    <div style="margin-top: 20px;">版本号：${version}</div>
                    <div style="margin-top: 50px;">Copyright © 2016 广东赛翼智能科技股份有限公司</div> 
                </div>
            </div>
        </div>
      
        <script type="text/javascript">
            $(function(){
//                 // Setting focus
//                 $('input[name="email"]').focus();

//                 // Setting width of the alert box
//                 var alert = $('.alert');
//                 var formWidth = $('.bootstrap-admin-login-form').innerWidth();
//                 var alertPadding = parseInt($('.alert').css('padding'));

//                 if (isNaN(alertPadding)) {
//                     alertPadding = parseInt($(alert).css('padding-left'));
//                 }

//                 $('.alert').width(formWidth - 2 * alertPadding);
            });
        </script>
    </body>
</html>