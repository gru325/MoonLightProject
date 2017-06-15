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

<style>
.ProfilePhoto {
    width: 200px;
    height: 200px;
    margin:auto;

</style>

<!-- 사용자 회원 가입 양식 -->
<div class="container">
  <div class="row"> 
    <!-- Contact form -->
    <section id="contact-form" class="mt50">
    <!-- 사용자 프로필 사진 등록 -->
    <form class="clearfix mt50" role="form" method="post" id="profileform">
        <div class="col-md-8">
        <h2 class="lined-heading"><span>Profile Photo</span></h2>
        <div class="ProfilePhoto"></div>
                
        <div class="form-group">
        	<label for="name">File DROP Here</label>
        	<div class="fileDrop" style="width:80%; height:100px; border:1px dotted gray; background-color:lightslategray; margin:auto;"></div>
        </div>
        <input type="file" name="fullName" id="img" style="float:right;">

        </div>
        
      <div class="col-md-8">
        <h2 class="lined-heading"><span>Member Information</span></h2>
        <p>Welcome to our Service, Please fill these forms.</p>
        <div id="message"></div>
        <!-- Error message display -->
        
          <div class="row">
          <!-- 세로 1째줄 -->
            <div class="col-md-6">
              <div class="form-group">
                <label for="name"><span class="required">*</span> Your ID</label>
                <input name="mid" type="text" id="mid" class="form-control" value=""/>
              </div>
              <div class="form-group">
                <label for="name"><span class="required">*</span> Name</label>
                <input name="mname" type="text" id="mname" class="form-control" value=""/>
              </div>              
              <div class="form-group">
                <label for="email"><span class="required">*</span> E-mail</label>
                <input name="mmail" type="text" id="mmail" class="form-control" value=""/>
              </div>
              <div class="form-group">
                <label for="name"><span class="required">*</span> Roof Tel.</label>
                <input name="htel" type="text" id="htel" class="form-control" value=""/>
              </div>
            </div>
            <!-- 세로 2째줄 -->
            <div class="col-md-6">
              <div class="form-group">
                <label for="name"><span class="required">*</span> Your Password</label>
                <input name="mpw" type="password" id="mpw" class="form-control" value=""/>
              </div>
              <div class="form-group">
                <label for="name"><span class="required">*</span> NickName</label>
                <input name="mnick" type="text" id="mnick" class="form-control" value=""/>
              </div> 
              <div class="form-group">
                <label for="name"><span class="required">*</span> Phone Number</label>
                <input name="mhp" type="text" id="mhp" class="form-control" value=""/>
              </div>
              <div class="form-group">
                <label for="name"><span class="required">*</span> Roof Address</label>
                <input name="haddr" type="text" id="haddr" class="form-control" value=""/>
              </div>
            </div>
          <button type="submit" id="mregisterBtn" class="btn  btn-lg btn-primary" style="float:right">Registraion</button>
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


<script type="text/javascript" src="/resources/js/upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<script id="template" type="text/x-handlebars-template">

	<span class="mailbox-attachment-icon has-img"><img class="img-circle" src="{{imgsrc}}" alt="Attachment"  style="width:100%; max-height:200px;"></span>
	<div class="mailbox-attachment-info">
	<a href="{{fullName}}" class="mailbox-attachment-name delbtn"></a>
</div>
</script>


<script>

$(document).ready(function(e){
	
	var formObj = $("form[role='form']");

	$("#profileform").submit(function(e){
		
		e.preventDefault();
		
		var that = $(this);
		
		var str = "";
		
		$(".ProfilePhoto .delbtn").each(function(index){
			str += "<input type='hidden' name='imgname["+index+"]' value='"+$(this).attr("href") + "'>";
		});
		
		that.append(str);
		
		that.get(0).submit();
	});
	
	var template = Handlebars.compile($("#template").html());
	
	$(".fileDrop").on("dragenter dragover", function(e){
		e.preventDefault();
	});
	
	$(".fileDrop").on("drop", function(e){
		e.preventDefault();
		
		var files = e.originalEvent.dataTransfer.files;
		
		var file = files[0];
		
		var formData = new FormData();
		
		formData.append("file", file);
		
		$.ajax({
			url: "/uploadAjax",
			data: formData,
			dataType: "text",
			processData: false,
			contentType: false,
			type: "POST",
			success: function(data){
				
				var fileInfo = getFileInfo(data);
				
				var html = template(fileInfo);

				$(".ProfilePhoto").html(html);

			}
		})
	});
	
		$("#img").on("change", function(event){
		
		event.preventDefault();
		
		console.log("change111");
		
		var clicked = event.target;
		var file = clicked.files[0];

		var formData = new FormData();
		
		console.log(file);
		
		formData.append("file", file);	
		
		
		$.ajax({
			  url: '/uploadAjax',
			  data: formData,
			  dataType:'text',
			  processData: false,
			  contentType: false,
			  type: 'POST',
			  success: function(data){
				  
				  var fileInfo = getFileInfo(data);
				  
				  var html = template(fileInfo);
				  
				  $(".ProfilePhoto").html(html);

			  }
		});	
	});
	
});



</script>

</html>