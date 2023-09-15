<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/ssi/ssi_notice.jsp"%>
<jsp:useBean id="dao" class="com.notice.NoticeDAO" />
<%
int noticeno = Integer.parseInt(request.getParameter("noticeno"));
String nowPage = request.getParameter("nowPage");
String col = request.getParameter("col");
String word = request.getParameter("word");

dao.upViewcnt(noticeno);//조회수 증가
NoticeDTO dto = dao.read(noticeno); //한건 데이터 조회

String content = dto.getContent().replaceAll("\r\n", "<br>");

List<Notice_replyDTO> list = dao.readReply(noticeno);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 읽기</title>
<script>
function list(){
	let url = "list.jsp";
	url += "?nowPage=<%=nowPage%>";
	url += "&col=<%=col%>";
	url += "&word=<%=word%>";
	
	location.href= url;
}

function reply(){	
	
	alert("호출");
	var f = document.getElementById("frm");	
	var flag = false;
	
	flag = dao.createReply(f.wname.value, f.content.value, f.passwd.value, f.noticeno.value);
	if(!flag) {
		console.log("댓글 등록 실패");
	} else {
		console.log("댓글 등록 성공!");
	}
}

function delete_reply(num) {
	//alert(num);
	
	var flag = false;
	var passwd = document.getElementById("check_passwd_"+num).value;
	//flag = dao.deleteReply(num, passwd);
	
	//if(!flag) {
	//	console.log("댓글 삭제 실패");
	//} else {
	//	console.log("댓글 삭제 성공!");
	//}	
	
	location.href = "replyDeleteProc.jsp?num="+num+"&passwd="+passwd;
	
}

function check_visible(num) {
	const div = document.getElementById("check_"+num);	
	
	if(div.style.display !== 'none') {
		div.style.display = 'none';
  	} else {
  		div.style.display = 'block';
	}
}

function update(){
	let url = "updateForm.jsp";
	url += "?nowPage=<%=nowPage%>";
	url += "&col=<%=col%>";
	url += "&word=<%=word%>";
	url += "&noticeno=<%=noticeno%>";
	
	location.href= url;
}

function del(){
	let url = "deleteForm.jsp";
	url += "?nowPage=<%=nowPage%>";
	url += "&col=<%=col%>";
	url += "&word=<%=word%>";
	url += "&noticeno=<%=noticeno%>";
	location.href = url;
	}
</script>

</head>
<body>
	<jsp:include page="/menu/top.jsp" />

	<div class="container mt-3 mb-3">
		<h3>조회</h3>
		<ul class="list-group">
			<li class="list-group-item">제목 : <%=dto.getTitle()%></li>
			<li class="list-group-item">작성자 : <%=dto.getWname()%></li>
			<li class="list-group-item" style="height: 250px; overflow-y: scroll"><%=content%></li>
			<li class="list-group-item">조회수 : <%=dto.getCnt()%></li>
			<li class="list-group-item">등록일 : <%=dto.getRdate().substring(0, 10)%></li>
		</ul>
		<br>
		<button class="btn btn-outline-dark"
			onclick="location='createForm.jsp'">등록</button>
		<button class="btn btn-outline-dark" onclick="del()">삭제</button>
		<button class="btn btn-outline-dark" onclick="update()">수정</button>
		<button class="btn btn-outline-dark" onclick="list()">목록</button>
	</div>

	<div class="container mt-5 mb-3 p-5"
		style="border-radius: 30px; border: 1px solid #dee2e6">

		<details>
			<summary style="font-size:larger">댓글</summary>
			<br>
			<%
			if (list.size() == 0) {
			%>
			<label style="font-weight: bolder">등록된 댓글이 없습니다.</label>
			<%
			} else {
			for (int i = 0; i < list.size(); i++) { 
				Notice_replyDTO rdto = list.get(i);
				int num = rdto.getReplyno();
			%>
			<div class="d-flex flex-row">
				<label style="font-weight: bolder"><%=rdto.getWname()%></label>
				<label style="color:red" onclick="check_visible(<%=num %>)">x</label>
				<div id="check_<%=num %>" style="display:none; margin-left:20px;">
					<input id="check_passwd_<%=num %>" type="text" class="col" placeholder="Enter password">
					<button type="button" class="col" onclick="delete_reply(<%=num %>)">확인</button>
				</div>
			</div>
			<br>

			<label><%=rdto.getContent()%> <%
 if (Utility.compareDay24(rdto.getRdate())) {
 	out.print("<img src='../images/new.gif'>");
 }
 %>
			</label>
			<br>
			<label style="color: #868e96; font-size-adjust: 14px"><%=rdto.getRdate()%></label>
			<hr>
			<%
			} // for end
			} // else end
			%>
			
		</details>



		<jsp:include page="/notice/replyForm.jsp" />
	</div>

</body>
</html>