package com.notice;

public class Notice_replyDTO {
	private int replyno;
	private String content;
	private String wname;
	private String passwd;
	private String rdate;
	private int noticeno;

	public Notice_replyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Notice_replyDTO(int replyno, String content, String wname, String passwd, String rdate, int noticeno) {
		super();
		this.replyno = replyno;
		this.content = content;
		this.wname = wname;
		this.passwd = passwd;
		this.rdate = rdate;
		this.noticeno = noticeno;
	}

	@Override
	public String toString() {
		return "Notice_replyDTO [replyno=" + replyno + ", content=" + content + ", wname=" + wname + ", passwd="
				+ passwd + ", rdate=" + rdate + ", noticeno=" + noticeno + "]";
	}

	public int getReplyno() {
		return replyno;
	}

	public void setReplyno(int replyno) {
		this.replyno = replyno;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWname() {
		return wname;
	}

	public void setWname(String wname) {
		this.wname = wname;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public int getNoticeno() {
		return noticeno;
	}

	public void setNoticeno(int noticeno) {
		this.noticeno = noticeno;
	}

}
