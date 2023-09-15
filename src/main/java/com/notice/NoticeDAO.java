package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.utility.DBClose;
import com.utility.DBOpen;

public class NoticeDAO {	
	public boolean createReply(String content, String wname, String passwd, int noticeno) {
		boolean flag = false;

		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into notice_reply(content, wname, passwd, rdate, noticeno) ");
		sql.append(" values (?, ?, ?, sysdate(), ?) ");

		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, content);
			pstmt.setString(2, wname);
			pstmt.setString(3, passwd);
			pstmt.setInt(4, noticeno);

			int cnt = pstmt.executeUpdate();
			if (cnt > 0)
				flag = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}

		return flag;
	}
	
	public boolean deleteReply(int replyno, String passwd) {
		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from notice_reply ");
		sql.append(" where replyno = ? and passwd = ? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, replyno);
			pstmt.setString(2, passwd);
			
			int cnt = pstmt.executeUpdate();
			if(cnt>0)flag =true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}
		
		return flag;
	}
	
	public void upAnsnum(Map map) {
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		int grpno = (int)map.get("grpno");
		int ansnum = (int)map.get("ansnum");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" update bbs  ");
		sql.append(" set ansnum = ansnum + 1 ");
		sql.append(" where grpno = ?  ");
		sql.append(" and ansnum > ?  ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, grpno);
			pstmt.setInt(2, ansnum);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}

	}
	
	public List<Notice_replyDTO> readReply(int noticeno) {
		List<Notice_replyDTO> list = new ArrayList<>();
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select replyno, content, wname, rdate ");
		sql.append(" from notice_reply ");
		sql.append(" where noticeno = ? ");
		sql.append(" order by noticeno asc ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, noticeno);
			
			rs = pstmt.executeQuery();			
			while(rs.next()) {
				Notice_replyDTO dto = new Notice_replyDTO();
				dto.setReplyno(rs.getInt("replyno"));
				dto.setContent(rs.getString("content"));
				dto.setWname(rs.getString("wname"));
				dto.setRdate(rs.getString("rdate"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
		
		return list;
	}
	
	public boolean delete(int noticeno) {
		boolean flag = false;
		
		if(deleteReply(noticeno)) {
			Connection con = DBOpen.open();
			PreparedStatement pstmt = null;		
			
			StringBuffer sql = new StringBuffer();
			sql.append(" delete from notice  ");
			sql.append(" where noticeno = ?  ");

			try {
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, noticeno);
				
				int cnt = pstmt.executeUpdate();
				if(cnt>0)flag =true;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBClose.close(pstmt, con);
			}
		}
		
		return flag;
	}
	
	public boolean deleteReply(int noticeno) {
		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;		
		
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from notice_reply  ");
		sql.append(" where noticeno = ?  ");

		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, noticeno);

			int cnt = pstmt.executeUpdate();
			if(cnt>=0)
				flag =true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}
		
		return flag;
	}
	
	public boolean passCheck(Map map) {
		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int noticeno = (int)map.get("noticeno");
		String passwd = (String)map.get("passwd");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(noticeno)");
		sql.append(" from notice ");
		sql.append(" where noticeno = ? and passwd = ? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());			
			pstmt.setInt(1, noticeno);
			pstmt.setString(2, passwd);

			rs = pstmt.executeQuery();			
			rs.next();			
			int cnt = rs.getInt(1);
			if(cnt>0) flag = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
		
		return flag;
	}
	
	public boolean update(NoticeDTO dto) {
		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" update notice ");
		sql.append(" set wname = ?, ");
		sql.append("  	 title = ?, ");
		sql.append("  	 content = ?  ");		
		sql.append(" where noticeno = ? ");
		
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getWname());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getNoticeno());
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt>0)flag = true;			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}
		
		return flag;
	}

	public void upViewcnt(int noticeno) {
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" update notice  ");
		sql.append(" set cnt = cnt + 1  ");
		sql.append(" where noticeno = ? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, noticeno);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}	
		
	}
	
	public NoticeDTO read(int num) {
		NoticeDTO dto = null;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select noticeno, title, content, wname, rdate, cnt ");
		sql.append(" from notice ");
		sql.append(" where noticeno = ? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();			
			if(rs.next()) {
				dto = new NoticeDTO();
				dto.setNoticeno(rs.getInt("noticeno"));				
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWname(rs.getString("wname"));				
				dto.setRdate(rs.getString("rdate"));
				dto.setCnt(rs.getInt("cnt"));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
	
		return dto;
	}

	public int total(String col, String word) {
		int total = 0;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as cnt from notice");

		if (word.trim().length() > 0 && col.equals("title_content")) {
			sql.append(" where title like concat('%' , ? , '%')  ");
			sql.append(" or content like concat('%' , ? , '%')  ");
		} else if (word.trim().length() > 0) {
			sql.append(" where " + col + " like concat('%' , ? , '%')  ");
		}

		try {
			pstmt = con.prepareStatement(sql.toString());

			if (word.trim().length() > 0 && col.equals("title_content")) {
				pstmt.setString(1, word);
				pstmt.setString(2, word);
			} else if (word.trim().length() > 0) {
				pstmt.setString(1, word);
			}

			rs = pstmt.executeQuery();			
			rs.next();			
			total = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}

		return total;
	}
	
	public List<NoticeDTO> list(Map map) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String col = (String) map.get("col");
		String word = (String) map.get("word");
		int sno = (int)map.get("sno");
		int eno = (int)map.get("eno");

		StringBuffer sql = new StringBuffer();
		sql.append(" select noticeno, title, wname, cnt, rdate ");
		sql.append(" from notice ");

		if (word.trim().length() > 0 && col.equals("title_content")) {
			sql.append(" where title like concat('%' , ? , '%')  ");
			sql.append(" or content like concat('%' , ? , '%')  ");
		} else if (word.trim().length() > 0) {
			sql.append(" where " + col + " like concat('%' , ? , '%')  ");
		}

		sql.append(" order by noticeno desc ");
		sql.append(" limit ?, ? ");

		try {
			pstmt = con.prepareStatement(sql.toString());
            int i=0;
			if (word.trim().length() > 0 && col.equals("title_content")) {
				pstmt.setString(++i, word);
				pstmt.setString(++i, word);
			} else if (word.trim().length() > 0) {
				pstmt.setString(++i, word);
			}
			
			pstmt.setInt(++i, sno);
			pstmt.setInt(++i, eno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				dto.setNoticeno(rs.getInt("noticeno"));				
				dto.setTitle(rs.getString("title"));
				dto.setWname(rs.getString("wname"));				
				dto.setRdate(rs.getString("rdate"));
				dto.setCnt(rs.getInt("cnt"));				

				list.add(dto);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}

		return list;
	}

	public boolean create(NoticeDTO dto) {
		boolean flag = false;
		
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into notice(title, content, wname, passwd, rdate) ");
		sql.append(" values(?, ?, ?, ?, sysdate()) ");

		try {
			pstmt = con.prepareStatement(sql.toString());		
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getWname());
			pstmt.setString(4, dto.getPasswd());

			int cnt = pstmt.executeUpdate();
			if (cnt > 0)
				flag = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(pstmt, con);
		}

		return flag;
	}
}
