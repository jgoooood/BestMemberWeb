package notice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import notice.model.vo.Notice;

public class NoticeDAO {

	public Notice selectOneByNo(Connection conn, int noticeNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM NOTICE_TBL WHERE NOTICE_NO = ?";
		Notice notice = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				notice = rsetToNotice(rset);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return notice;
	}

	
	public List<Notice> selectNoticeList(Connection conn) {
		PreparedStatement pstmt = null;
		//ORDER BY를 꼭 적용해주어야 글이 뒤죽박죽 나오지 않음(DESC는 최신부터)
		//인서트 실패시 시퀀스 번호가 중간중간 빠질 수 있음->추후 수정가능
		String query = "SELECT * FROM NOTICE_TBL ORDER BY NOTICE_DATE DESC";
		ResultSet rset = null;
		List<Notice> nList = new ArrayList<Notice>();
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Notice notice = rsetToNotice(rset);
				notice.setNoticeNo(rset.getInt("NOTICE_NO"));
				notice.setNoticeSubject(rset.getString("NOTICE_SUBJECT"));
				notice.setNoticeContent(rset.getString("NOTICE_CONTENT"));
				notice.setNoticeWriter(rset.getString("NOTICE_WRITER"));
				notice.setNoticeDate(rset.getTimestamp("NOTICE_DATE"));
				notice.setUpdateDate(rset.getTimestamp("UPDATE_DATE"));
				notice.setViewCount(rset.getInt("VIEW_COUNT"));
				
				nList.add(notice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return nList;
	}

	public int insertNotice(Connection conn, Notice notice) {
		PreparedStatement pstmt = null;
		String query = "INSERT INTO NOTICE_TBL VALUES(SEQ_NOTICENO.NEXTVAL, ?, ?, 'admin', DEFAULT, DEFAULT, DEFAULT)";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, notice.getNoticeSubject());
			pstmt.setString(2, notice.getNoticeContent());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private Notice rsetToNotice(ResultSet rset) throws SQLException {
		Notice notice = new Notice();
		notice.setNoticeNo(rset.getInt("NOTICE_NO"));
		notice.setNoticeSubject(rset.getString("NOTICE_SUBJECT"));
		notice.setNoticeContent(rset.getString("NOTICE_CONTENT"));
		notice.setNoticeWriter(rset.getString("NOTICE_WRITER"));
		notice.setNoticeDate(rset.getTimestamp("NOTICE_DATE"));
		notice.setUpdateDate(rset.getTimestamp("UPDATE_DATE"));
		notice.setViewCount(rset.getInt("VIEW_COUNT"));
		return notice;
	}

}
