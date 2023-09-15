<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/ssi/ssi_bbs.jsp" %> 
<jsp:useBean  id="dao" class="com.bbs.BbsDAO"/>  
<jsp:useBean id="dto" class="com.bbs.BbsDTO" /> 
<jsp:setProperty name="dto" property="*"/>
<%
   boolean flag =  dao.create(dto);
    
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/menu/top.jsp" />
<div class="container">
<div class="container p-5 my-5 border">
	<%
		if(flag){
			out.println("글 등록 성공 입니다.");
		}else{
			out.println("글 등록 실패 입니다.");
		}
	%>
</div>
	<button class="btn btn-light" onclick="location.href='createForm.jsp'">다시등록</button>
	<button class="btn btn-light" onclick="location.href='list.jsp'">목록</button>
</div>
</body>
</html>