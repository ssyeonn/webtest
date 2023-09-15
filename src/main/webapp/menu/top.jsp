<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String root = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jsp study</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
   <!--상단메뉴-->
    <div class="container-fluid">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a class="nav-link active" href="<%=root%>/index.jsp">Home</a>
        </li>
 
        <li class="nav-item dropdown">
          <a
            class="nav-link dropdown-toggle"
            data-bs-toggle="dropdown"
            href="#"
          >
            게시판</a
          >
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<%=root%>/bbs/list.jsp">게시판 목록</a></li>
            <li><a class="dropdown-item" href="<%=root%>/bbs/createForm.jsp">게시판 생성</a></li>
          </ul>
        </li>
        <li class="nav-item dropdown">
          <a
            class="nav-link dropdown-toggle"
            data-bs-toggle="dropdown"
            href="#"
          >
            공지사항</a
          >
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<%=root%>/notice/list.jsp">공지사항 목록</a></li>
            <li><a class="dropdown-item" href="<%=root%>/notice/createForm.jsp">공지사항 생성</a></li>
          </ul>
        </li>
        
        <li class="nav-item"><a class="nav-link" href="<%=root%>/poll/poll.jsp#section1">투표</a></li>
        
        <li class="nav-item"><a class="nav-link" href="<%=root%>/webgame/webgame.jsp">웹게임</a></li>
      </ul>
      
    </div>
</body>
</html>