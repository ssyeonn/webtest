<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/ssi/ssi_notice.jsp" %> 
<jsp:useBean id="dao" class="com.notice.NoticeDAO"/>  
<jsp:useBean id="dto" class="com.notice.NoticeDTO" /> 
<jsp:setProperty name="dto" property="*"/>
<%
	String wname = request.getParameter("wname");
	String content = request.getParameter("content");
	String passwd = request.getParameter("passwd");
	int noticeno = Integer.parseInt(request.getParameter("noticeno"));
	
	boolean null_flag = false; String msg = "";
	if(wname=="" || content=="" || passwd=="") {
		null_flag = true;
		msg += "빈칸을 채워주세요.";
	} else if(content.getBytes().length>500) {
		null_flag = true;
		msg += "댓글은 500byte 이하로 작성해주세요.";
	} else if(wname.getBytes().length>30) {
		null_flag = true;
		msg += "작성자는 30byte 이하로 작성해주세요.";
	} else if(passwd.getBytes().length>20) {
		null_flag = true;
		msg += "비밀번호는 20byte 이하로 작성해주세요.";
	}
	
  	boolean flag = false;
  	if(!null_flag) {
  		dao.createReply(content, wname, passwd, noticeno);  
  	}
  	
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>

	if(<%=null_flag %>) {
		alert('<%=msg %>');
	}
	// history.back();
	location.href = document.referrer;
	
</script>
</head>
</html>