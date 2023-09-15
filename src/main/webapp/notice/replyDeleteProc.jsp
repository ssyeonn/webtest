<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/ssi/ssi_notice.jsp" %> 
<jsp:useBean id="dao" class="com.notice.NoticeDAO"/>  
<jsp:useBean id="dto" class="com.notice.NoticeDTO" /> 
<jsp:setProperty name="dto" property="*"/>
<%
	int num = Integer.parseInt(request.getParameter("num"));
	String passwd = request.getParameter("passwd");
	
	
  	boolean flag = false;
  	flag = dao.deleteReply(num, passwd);  	
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>

	
	// history.back();
	location.href = document.referrer;
	
</script>
</head>
<body>
if(!flag) {
  		console.log("댓글 삭제 실패");
  	} else {
  		console.log("댓글 삭제 성공!");
  	}
</body>
</html>