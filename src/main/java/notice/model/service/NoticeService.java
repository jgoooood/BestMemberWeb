package notice.model.service;

import java.sql.Connection;
import java.util.List;

import common.JDBCTemplate;
import notice.model.dao.NoticeDAO;
import notice.model.vo.Notice;
import notice.model.vo.PageData;

public class NoticeService {
	private NoticeDAO nDao;
	private JDBCTemplate jdbcTemplate;
	
	public NoticeService() {
		nDao = new NoticeDAO();
//		JDBCTemplate = new JDBCTemplate(); 싱글톤패턴적용+JDBCTemplate는 private 이기 때문에 객체생성 바로 불가
		jdbcTemplate = JDBCTemplate.getInstance(); //메소드를 통해서 객체존재유무를 따지고 난 후 생성가능함
	}

	public int insertNotice(Notice notice) {
		Connection conn = jdbcTemplate.createConnection();
		int result = nDao.insertNotice(conn, notice);
		if(result > 0) {
			jdbcTemplate.commit(conn);
		} else {
			jdbcTemplate.rollback(conn);
		}
		jdbcTemplate.close(conn);
		return result;
	}
	
	/* 공지사항 전체 목록 조회 - 페이징 기능 없음
	public List<Notice> selectNoticeList() {
		Connection conn = jdbcTemplate.createConnection();
		List<Notice> nList = nDao.selectNoticeList(conn);
		jdbcTemplate.close(conn);
		return nList;
	} */
	
	/* 공지사항 전체 목록 조회 - 페이징 기능 적용, 페이지네비게이터 없음
	service역시 dao로 currentPage를 넘겨주기 위해 매개변수를 받아야 함->ListController에 currentPage를 전달하는 코드 추가
	public List<Notice> selectNoticeList(int currentPage) {
		Connection conn = jdbcTemplate.createConnection();
		//연결 생성값과 매개변수로 받은 currentPage를 함께 DAO로 전달
		List<Notice> nList = nDao.selectNoticeList(conn, currentPage);
		jdbcTemplate.close(conn);
		return nList;
	} */
	
	// 공지사항 전체 목록 조회
	// 1. 목록조회 : DAO.selectNoticeList(conn, currentPage) -> DAO에 연결생성과 currentPage 전달
	// 2. 네이게이터 생성 : DAO.generatePageNavi(currentPage) -> DAO에 currentPage 전달
	// service역시 dao로 currentPage를 넘겨주기 위해 매개변수를 받아야 함->ListController에 currentPage를 전달하는 코드 추가
	public PageData selectNoticeList(int currentPage) {
		// 1. 연결 생성값과 매개변수로 받은 currentPage를 함께 DAO로 전달
		Connection conn = jdbcTemplate.createConnection();
		List<Notice> nList = nDao.selectNoticeList(conn, currentPage); 
		// 2. nDao에 currentPage전달하고 생성한 네비게이터를 String타입의 pageNavi변수로 받음->리턴할 값
		String pageNavi = nDao.generatePageNavi(currentPage);
		// 3. 생성한 네비게이터 값을 controller로 반환-> (1)Map이용 (2)PageData vo클래스새로생성(더 쉬움)
		// -> 객체를 새로 생성해서 목록과 네비게이터를 함께 반환함
		PageData pd = new PageData(nList, pageNavi); // 생성자이용
		jdbcTemplate.close(conn);
		//리턴하는 변수의 타입이 PageData이므로 메소드 반환타입도 List<Notice>에서 PageData로 변경해줌
		//controller역시 반환될 리턴값을 받기위해 PageData 변수로 받아야 함
		return pd; 
	}

	public Notice selectOneByNo(int noticeNo) {
		Connection conn = jdbcTemplate.createConnection();
		Notice notice = nDao.selectOneByNo(conn, noticeNo);
		jdbcTemplate.close(conn);
		return notice;
	}


	public int modifyNotice(Notice notice) {
		Connection conn = jdbcTemplate.createConnection();
		int result = nDao.modifyNotice(conn, notice);
		if(result > 0) {
			jdbcTemplate.commit(conn);
		} else {
			jdbcTemplate.rollback(conn);
		}
		jdbcTemplate.close(conn);
		return result;
	}

	public int deleteNoticeByNo(int noticeNo) {
		Connection conn = jdbcTemplate.createConnection();
		int result = nDao.deleteNoticeByNo(conn, noticeNo);
		if(result > 0) {
			jdbcTemplate.commit(conn);
		} else {
			jdbcTemplate.rollback(conn);
		}
		jdbcTemplate.close(conn);
		return result;
	}
	


}
