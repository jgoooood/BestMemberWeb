package notice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import notice.model.vo.Notice;

public class NoticeDAO {

	public int insertNotice(Connection conn, Notice notice) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO NOTICE_TBL VALUES(SEQ_NOTICENO.NEXTVAL,?,?,'admin',DEFAULT,DEFAULT,DEFAULT)";
		
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/* 전체목록 출력하기 : 페이징과 DB쿼리문의 ROW_NUMBER()함수 적용 전
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
	} */
	
	//전체목록 출력하기 : 페이징과 DB쿼리문의 ROW_NUMBER() 함수 적용->currentPage(현재페이지)를 매개변수로 받아야 함
	//*현재페이지를 매개변수로 추가*해주었기 때문에 service도 마찬가지로 currentPage를 넘겨주는 코드를 추가해줘야 함
	public List<Notice> selectNoticeList(Connection conn, int currentPage) {
		PreparedStatement pstmt = null;
		//ROW_NUMBER가 부여된 테이블을 인라인뷰로 조회함->조건절을 ROW_NUMBER가 ?부터 ?사이인 데이터를 가지고 옴
		String query = "SELECT * FROM(SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_NO DESC) ROW_NUM, NOTICE_TBL. * FROM NOTICE_TBL) WHERE ROW_NUM BETWEEN ? AND ?";
		ResultSet rset = null;
		List<Notice> nList = new ArrayList<Notice>();
		//recordCountPerPage : 한 페이지당 보여질 게시물의 개수
		//currentPage : 현재 페이지를 뜻하며 매개변수로 받아야 하는 값
		//recordCountPerPage : 한 페이지당 보여질 게시물의 개수
		// ex) 한 페이지에 10개씩 게시물이 보이고, 시작할 페이징을 동적으로 변경해줘야 함.
		//currentPage		start
		//	   1		 	  1
		//	   2		 	 11
		//	   3		 	 21
		//	   4			 31
		int recordCountPerPage = 10; //페이지당 보여줄 개수
		//페이징 시작값->시작값고정하면 누적되서 나옴
		// 시작값과 끝의 값을 동적으로 처리하기 위한 계산식
		// 1. 시작값 : 현재 페이지 * 한 페이지당 보여질 게시물의 개수 - (게시물 개수 - 1) -> 현재 페이지 2 * 게시물 수 10 - (게시물 수 10 - 1) = 11(2페이지의 시작값)
		int start = currentPage * recordCountPerPage - (recordCountPerPage - 1); //->시작값 1 
		// 2. 끝의 값 : 현재 페이지 * 게시물 개수 -> 현재페이지 2 * 게시물 수 10 = 20(2페이지의 끝의 값)
		int end = currentPage * recordCountPerPage; //페이징 끝 값
		try {
			pstmt = conn.prepareStatement(query);
			//이전과는 다르게 DB쿼리문의 BETWEEN ? AND ?의 값을 세팅해주어야함.
			pstmt.setInt(1, start); //현재페이지의 시작값
			pstmt.setInt(2, end); //현재페이지의 끝의 값
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Notice notice = rsetToNotice(rset);
				nList.add(notice);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nList;
	}
	
	// 페이지 네비게이터 생성 : currentPage를 매개변수로 받아야 함
	public String generatePageNavi(int currentPage) {
		// 1. 생성할 네비게이터 개수를 먼저 계산 
			// 전체 게시물의 갯수 : 37 (totalCount)
			// 1페이지 보여줄 게시물 수 : 10 (recordCountPerPage)
			// 범위의 갯수(네비게이터의 수) : 4 (naviTotalCount)
	
			// 전체 게시물의 갯수는 : 55 (totalCount)
			// 1페이지 보여줄 게시물 수 : 10 (recordCountPerPage)
			// 범위의 갯수(네비게이터의 수) : 6 (naviTotalCount)
	
			// 전체 게시물의 갯수는 : 76 (totalCount)
			// 1페이지 보여줄 게시물 수 : 10 (recordCountPerPage)
			// 범위의 갯수 : 8 (naviTotalCount)
		// SELECT COUNT(*) FROM NOTICE_TBL; 전체 게시물의 갯수를 동적으로 가지고 와야함
		// DB의 저장된 행의 갯수->!추후 전체게시물을 동적으로 가지고 오도록 수정!
		int totalCount = 204; 
		int recordCountPerPage = 10; //한 페이지당 보여줄 게시물 수
		int naviTotalCount = 0; //네비게이터 수 (총 범위의 개수)
		
		// 네비게이터 수 구하는 식 : 전체게시물 / 페이지당 게시물 수 + 1
		//if문으로 식 세우기 : 전체페이지를 노출게시물 수로 나눠서 
		if(totalCount % recordCountPerPage > 0) { //전체 개수를 페이지당 개수로 나눠서 나머지가 있으면
			naviTotalCount = totalCount / recordCountPerPage +1; // naviTotalCount값 = 나눈 몫(페이지)에 +1
		} else { // 나머지가 없으면
			naviTotalCount = totalCount / recordCountPerPage; //naviTotalCount값 = 나눈 몫(페이지)
		}
		
		// 2. 페이지가 넘어갈 때 시작될 네비게이터의 시작값과 끝 값을 정해줌
		// naviCountPerPage : 한 페이지 범위에 보여질 페이지 개수
		// int naviCountPerPage = 5; -> 1, 2, 3, 4, 5
		// int naviCountPerPage = 7; -> 1, 2, 3, 4, 5, 6, 7
		// 이후 페이지 범위는 다음버튼을 눌러야만 보여줌
		int naviCountPerPage = 10; //한 범위 당 보여줄 네비게이터 개수 ->1~5페이지까지 보임
		// currentPage값에 따라 statrNavi는 바뀌어야함
		//currentPage		 startNavi		endNavi
		//1,2,3,4,5				1		->		5
		//6,7,8,9,10	  		6		->		10
		//11,12,13,14,15 		11		->		15
		//16,17,18,19,20  		16		->		20
		int startNavi = ((currentPage - 1)/naviCountPerPage) *  naviCountPerPage + 1;
		int endNavi = startNavi + naviCountPerPage - 1;
		//endNavi값이 총 범위의 갯수보다 커지는 것을 막아주는 코드
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		
		// 3. 다음, 이전 버튼 생성
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi == 1) { //startNavi가 1이면 이전버튼 필요없음
			needPrev = false;
		}
		if(endNavi == naviTotalCount) { //endNavi가 네비게이터의 최종 끝의 값이면 다음버튼 필요없음
			needNext = false;
		}
		StringBuilder result = new StringBuilder(); //누적을 하게해주는 api
		//이전 버튼이 필요하면(true)
		if(needPrev) {  //이전버튼 추가
			result.append("<a href='/notice/list.do?currentPage="+(startNavi-1)+"'>[이전]</a> ");
		}
		//for문으로 페이지 네비게이터 번호 생성
		for(int i = startNavi; i<= endNavi; i++) {
//			result += "<a href=\"#\">1</a>";
			//.append를 통해 result에 누적해서 저장됨
			result.append("<a href='/notice/list.do?currentPage="+i+"'>"+i+"</a>&nbsp;&nbsp;");
		}
		//다음 버튼이 필요하면(true)
		if(needNext) { //다음버튼 추가
			result.append("<a href='/notice/list.do?currentPage="+(endNavi+1)+"'>[다음]</a>");
		}
		return result.toString(); //String타입으로 반환->메소드 반환타입도 String으로 변경
	}

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

	public int modifyNotice(Connection conn, Notice notice) {
		// UPDATE NOTICE_TBL SET NOTICE_SUBJECT = ? , NOTICE_CONTENT = ? WHERE NOTICE_NO = ?
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "UPDATE NOTICE_TBL SET NOTICE_SUBJECT = ? , NOTICE_CONTENT = ? WHERE NOTICE_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, notice.getNoticeSubject());
			pstmt.setString(2, notice.getNoticeContent());
			pstmt.setInt(3, notice.getNoticeNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int deleteNoticeByNo(Connection conn, int noticeNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "DELETE FROM NOTICE_TBL WHERE NOTICE_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
