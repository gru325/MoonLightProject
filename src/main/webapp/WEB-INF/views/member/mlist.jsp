<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/script.jsp"%>
<%@include file="../includes/banner.jsp"%>

<div class="container" style="text-align:center;">
<div class="col-sm-8 mt50" style="display:inline-block; width:100%;">
<h2 class="lined-heading"><span>Member List</span></h2>
<table class="table table-striped mt30">
        <tbody>
        <tr>
        <th>번호</th>
        <th>ID</th>
        <th>이름</th>
        <th>PW</th>
        <th>닉네임</th>
        <th>E-mail</th>
        <th>연락처</th>
        <th>등록일자</th>
        </tr>
        
        <c:forEach items="${list}" var="memberVO">
          <tr>
            <td>${memberVO.mno}</td>
            <td><a href="/member/mview?mno=${memberVO.mno}">${memberVO.mid}</a></td>
            <td>${memberVO.mname}</td>
            <td>${memberVO.mpw}</td>
            <td>${memberVO.mnick}</td>
            <td>${memberVO.mmail}</td>
            <td>${memberVO.mhp}</td>
            <td>${memberVO.regdate}</td>
            </tr>
        </c:forEach>
        
        </tbody>
      </table>
      
      <div class="text-center mt50">
      <ul class="pagination clearfix">
        <li class="disabled"><a href="#">«</a></li>
        <li class="active"><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li><a href="#">»</a></li>
      </ul>
      </div>
      </div>
      </div>
      
      
<script>
      
      history.pushState(null, null, location.href);
      window.onpopstate = function(event) {
      	history.go(1);
      };
      
      /* 등록/수정/삭제 후 Alert창 띄워주는 코드는 redirect가 모두 mlist이기 때문에 mlist에 위치한다 */
      var result = "${msg}";
    
      if(result=="success"){
    	  alert("등록!!");
    	  }

      
      </script>