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

<style type="text/css">

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
        <div class="col-md-8">
        <h2 class="lined-heading"><span>Profile Photo</span></h2>
        
        <div class="ProfilePhoto"></div>
        
        <button type="file" class="btn  btn-lg btn-primary" style="float:right; display:none;">File</button>
        
        </div>
          </div>
          
      <div class="col-md-8">
        <h2 class="lined-heading"><span>Member Information</span></h2>
        <p>Welcome to our Service, Please fill these forms.</p>
        <div id="message"></div>
        <!-- Error message display -->
        <form class="clearfix mt50" role="form" method="" action="" name="contactform" id="mviewform">
          <div class="row">
          <!-- 세로 1째줄 -->
            <div class="col-md-6">
              <div class="form-group">
                <label for="name"><span class="required">*</span> Your ID</label>
                <input name="mid" type="text" id="member_id" class="form-control" value="${mviewVO.mid}" readonly="readonly"/>
              </div>
              <div class="form-group">
                <label for="name"><span class="required">*</span> Name</label>
                <input name="mname" type="text" id="member_name" class="form-control" value="${mviewVO.mname}" readonly="readonly"/>
              </div>              
              <div class="form-group">
                <label for="email"><span class="required">*</span> E-mail</label>
                <input name="mmail" type="text" id="member_email" class="form-control" value="${mviewVO.mmail}"readonly="readonly"/>
              </div>
              <div class="form-group">
                <label for="name"><span class="required">*</span> Roof Tel.</label>
                <input name="htel" type="text" id="htel" class="form-control" value="${mviewVO.htel}" readonly="readonly"/>
              </div>
            </div>
            <!-- 세로 2째줄 -->
            <div class="col-md-6">
              <div class="form-group">
                <label for="name"><span class="required">*</span> Your Password</label>
                <input name="mpw" type="password" id="member_pw" class="form-control" value="${mviewVO.mpw}" readonly="readonly"/>
              </div>
              <div class="form-group">
                <label for="name"><span class="required">*</span> NickName</label>
                <input name="mnick" type="text" id="member_nick" class="form-control" value="${mviewVO.mnick}" readonly="readonly"/>
              </div> 
              <div class="form-group">
                <label for="name"><span class="required">*</span> Phone Number</label>
                <input name="mhp" type="text" id="member_phone" class="form-control" value="${mviewVO.mhp}" readonly="readonly"/>
              </div>
              <div class="form-group">
                <label for="name"><span class="required">*</span> Roof Address</label>
                <input name="htel" type="text" id="htel" class="form-control" value="${mviewVO.haddr}" readonly="readonly"/>
              </div>
            </div>
            
            <!-- MemberController의 메소드 결과를 처리하기 위한 form 태그 내의 정보 -->
            <input type="hidden" name="mno" value="${mviewVO.mno}">
			<input type="hidden" name="page" value="${cri.page}">
			<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
			<input type="hidden" name="searchType" value="${cri.searchType}">
			<input type="hidden" name="keyword" value="${cri.keyword}">
	
          <button type="submit" id="mlistBtn" class="btn  btn-lg btn-primary" style="float:left;">List</button>
          <button type="submit" id="mmodifyBtn" class="btn  btn-lg btn-primary" style="float:right;">Modify</button>
          <button type="submit" id="mremoveBtn" class="btn  btn-lg btn-primary" style="float:right;">Remove</button>
          </div>
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

<script id="templateAttach" type="text/x-handlebars-tamplate">

<span class="mailbox-attachment-icon has-img"><img class="img-circle" src="{{imgsrc}}" alt="Attachment"  style="width:100%; max-height:200px;"></span>
	<div class="mailbox-attachment-info">
	<a href="{{fullName}}" class="mailbox-attachment-name"></a>
</div>
</script>

<script>

$(document).ready(function(e){
	
	var formObj = $("form[role='form']");
	
	console.log(formObj);
	
	$("#mremoveBtn").on("click",function(e){
		
		/* var replyCnt = $("#replycntSmall").html().replace(/[^0-9]/g,"");
		
		if(replyCnt > 0){
			alert("댓글 달린 게시물은 삭제할 수 없습니다.");
			return;
		} 댓글 없는 게시판이므로 주석처리 했습니다. */
		
		var arr = [];
		$(".ProfilePhoto").each(function(index){
			arr.push($(this).attr("data-src"));			
		});
		
		if(arr.length > 0){
			$.post("/deleteAllFiles", {imgname:arr}, function(){
			});
		}
		formObj.attr("method", "post");
		formObj.attr("action", "/member/mremovePage")
		formObj.submit();
	});

	
	$("#mmodifyBtn").on("click",function(e){
		formObj.attr("method", "get");
		formObj.attr("action", "/member/mmodifyPage")
		formObj.submit();
	});
	
	$("#mlistBtn").on("click",function(e){
		formObj.attr("method", "get");
		formObj.attr("action", "/member/mlistPage")
		formObj.submit();
	});
	
	var mno = ${mviewVO.mno};
	var template = Handlebars.compile($("#templateAttach").html());
	
	$.getJSON("/member/getAttach/" + mno, function(list){ //컨트롤러에서 List를 반환하기 때문에 JSON 형태의 데이터를 전송하게 된다. 이를 getJSON()을 이용해서 처리한다.
		
		$(list).each(function(){ //데이터를 화면에 보여주는 부분은 등록하는 부분과 동일하게 template을 이용한다.
			
			var fileInfo = getFileInfo(this);

			var html = template(fileInfo);

			$(".ProfilePhoto").html(html);

		});
	});
})

</script>


</html>