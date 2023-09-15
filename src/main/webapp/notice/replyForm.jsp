<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/ssi/ssi_notice.jsp" %>
<jsp:useBean id="dao" class="com.notice.NoticeDAO" />
<%
	int noticeno = Integer.parseInt(request.getParameter("noticeno"));	
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>
</script>
</head>

<body>
<div class="container mt-3">
  <form id="frm" action="replyProc.jsp" method="post">
    <input type="hidden" name='noticeno' value="<%=noticeno %>">
    <input type="hidden" name='nowPage' value="<%=request.getParameter("nowPage") %>">
    <input type="hidden" name='col' value="<%=request.getParameter("col") %>">
    <input type="hidden" name='word' value="<%=request.getParameter("word") %>">
    
    <div class="mb-3 mt-3">
      <label for="content">내용:</label>
      <textarea class="form-control" rows="3" id="content" name="content" style="resize: none;"></textarea>
    </div>
    <div class="row mb-3">
	    <div class="col">
	      <label for="wname">작성자:</label>
	      <input type="text" class="form-control" id="wname" placeholder="Enter name" name="wname">
	    </div>     
	    <div class="col">
	      <label for="pwd">비밀번호:</label>
	      <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="passwd">
	    </div>
    </div>
    
    <button type="submit" class="btn btn-primary" >Submit</button>
  </form>
  
  
</div>
</body>
</html>