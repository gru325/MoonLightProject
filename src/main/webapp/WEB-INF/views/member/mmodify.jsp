<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/script.jsp"%>
<%@include file="../includes/banner.jsp"%>
<body>

<!-- 사용자 회원 가입 양식 -->
<div class="container">
  <div class="row"> 
    <!-- Contact form -->
    <section id="contact-form" class="mt50">
    <!-- 사용자 프로필 사진 등록 -->
        <div class="col-md-8">
        <h2 class="lined-heading"><span>Profile Photo</span></h2>
        <div class="ProfilePhoto" style="position: relative; width:300px; height:200px; border:1px solid; margin:auto;"></div>
        <button type="file" class="btn  btn-lg btn-primary" style="float:right;">File</button>
        </div>
          </div>
      <div class="col-md-8">
        <h2 class="lined-heading"><span>Member Information</span></h2>
        <p>Welcome to our Service, Please fill these forms.</p>
        <div id="message"></div>
        <!-- Error message display -->
        <form class="clearfix mt50" role="form" method="post" name="contactform" id="mmodifyform">
          <div class="row">
          <!-- 세로 1째줄 -->
            <div class="col-md-6">
              <div class="form-group">
                <label for="name"><span class="required">*</span> Your ID</label>
                <input name="mid" type="text" id="mid" class="form-control" value="${mmodifyVO.mid}" readonly="readonly"/>
              </div>
              <div class="form-group">
                <label for="name"><span class="required">*</span> Name</label>
                <input name="mname" type="text" id="mname" class="form-control" value="${mmodifyVO.mname}" readonly="readonly"/>
              </div>              
              <div class="form-group">
                <label for="email"><span class="required">*</span> E-mail</label>
                <input name="mmail" type="text" id="mmail" class="form-control" value="${mmodifyVO.mmail}"/>
              </div>
            </div>
            <!-- 세로 2째줄 -->
            <div class="col-md-6">
              <div class="form-group">
                <label for="name"><span class="required">*</span> Your Password</label>
                <input name="mpw" type="password" id="mpw" class="form-control" value="${mmodifyVO.mpw}"/>
              </div>
              <div class="form-group">
                <label for="name"><span class="required">*</span> NickName</label>
                <input name="mnick" type="text" id="mnick" class="form-control" value="${mmodifyVO.mnick}"/>
              </div> 
              <div class="form-group">
                <label for="name"><span class="required">*</span> Phone Number</label>
                <input name="mhp" type="text" id="mhp" class="form-control" value="${mmodifyVO.mhp}"/>
              </div>
            </div>
            <input name="mno" type="hidden" value="${mmodifyVO.mno}">
          <button type="submit" id="mmodifyBtn" class="btn  btn-lg btn-primary" style="float:right">Modify</button>
        </form>
      </div>
    </section>
  </div>
</div>

<!-- Footer -->
<footer>
  <div class="footer-bottom">
    <div class="container">
      <div class="row">
        <div class="col-xs-6"> &copy; 2014 Starhotel All Rights Reserved </div>
        <div class="col-xs-6 text-right">
          <ul>
            <li><a href="contact-01.html">Contact</a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</footer>

<!-- Go-top Button -->
<div id="go-top"><i class="fa fa-angle-up fa-2x"></i></div>

</body>





<script>

$(document).ready(function(e){
	var formObj = $("form[id='mmodifyform']");
	
	$("#mmodifyBtn").on("click", function(e){
		formObj.attr("method", "post");
		formObj.attr("action", "/member/mmodify");
		})
	})


</script>

</html>