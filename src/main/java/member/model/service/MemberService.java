package member.model.service;

import java.sql.Connection;

import common.JDBCTemplate;
import member.model.dao.MemberDAO;
import member.model.vo.Member;

//service의 역할
// 1. 연결생성
// 2. dao호출
// 3. 커밋, 롤백
public class MemberService {
	//필드선언
	JDBCTemplate jdbcTemplate;
	MemberDAO mDao;
	
	//생성자
	public MemberService() {
		
		// 탬플릿 객체 초기화 ->생성자 호출
//		jdbcTemplate = new JDBCTemplate(); private 때문에 생성못함
		jdbcTemplate = JDBCTemplate.getInstance(); //메소드를 호출 
		// 2. dao 호출 객체초기화 -> 생성자 호출
		mDao = new MemberDAO();
	}
	
	public Member selectCheckLogin(Member member) {
		//select는 커밋 롤백 필요없음
		// 1. 연결생성 : createConnection메소드 사용
		Connection conn = jdbcTemplate.createConnection();
		
		// 2. DAO호출(연결도 넘겨줘야 함)
		Member mOne = mDao.selectCheckLogin(conn, member);
		jdbcTemplate.close(conn);
		return mOne;
	}

	public Member selectOneById(String memberId) {
		// 연결생성
		Connection conn = jdbcTemplate.createConnection();
		// DAO호출
		Member member = mDao.selectOneById(conn, memberId);
		jdbcTemplate.close(conn);
		return member;
	}

	//메소드
	//insertMemer메소드 -> DAO메소드 호출
	public int insertMember(Member member) {
		// 1. 연결생성->common 패키지->jdbc탬플릿 생성 
		Connection conn = jdbcTemplate.createConnection();
		int result = mDao.insertMember(conn, member);
		// 3. 커밋, 롤백
		if(result > 0) {
			//성공 - 커밋
			jdbcTemplate.commit(conn);
		}else {
			//실패 - 롤백
			jdbcTemplate.rollback(conn);
		}
		jdbcTemplate.close(conn); 
		//if(conn == null || conn.isClosed()) 닫혔을 때도 다시 연결해주는 코드 생성함
		return result;
	}

	public int deleteMember(String memberId) {
		//연결생성
		Connection conn = jdbcTemplate.createConnection();
		//DAO호출
		int result = mDao.deleteMember(conn, memberId);
		if(result > 0) {
			//성공 - 커밋
			jdbcTemplate.commit(conn);
		}else {
			//실패 - 롤백
			jdbcTemplate.rollback(conn);
		}
		jdbcTemplate.close(conn); 
		return result;
	}

	public int updateMember(Member member) {
		Connection conn = jdbcTemplate.createConnection();
		int result = mDao.updateMember(conn, member);
		if(result > 0) {
			//성공 - 커밋
			jdbcTemplate.commit(conn);
		}else {
			//실패 - 롤백
			jdbcTemplate.rollback(conn);
		}
		jdbcTemplate.close(conn); 
		return result;
	}

	public int selectOne(String memberId) {
		Connection conn = jdbcTemplate.createConnection();
		int result = mDao.selectOne(conn, memberId);
		if(result > 0) {
			//성공 - 커밋
			jdbcTemplate.commit(conn);
		}else {
			//실패 - 롤백
			jdbcTemplate.rollback(conn);
		}
		jdbcTemplate.close(conn); 
		return result;
	}
	
}
