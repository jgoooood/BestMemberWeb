package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/member/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		String memberId = request.getParameter("member-id");
		String memberPw = request.getParameter("member-pw");
		Member member = new Member(memberId, memberPw);
		
		//로그인 쿼리문
		//1. SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PW = ? -> 하나의 결과값 리턴 Member타입 사용
		MemberService service = new MemberService();
//		Member mOne = service.selectCheckLogin(memberId, memberPw); //경우에 따라서 변수 두개를 넘길 수 있음
		Member mOne = service.selectCheckLogin(member);
		if(mOne != null) {
			//static변수처럼 모든 페이지에서 접근해서 쓸 수 있음->로그인 성공시 아이디, 이름 정보를 저장함
			HttpSession session = request.getSession();
			session.setAttribute("memberId", mOne.getMemberId());
			session.setAttribute("memberName", mOne.getMemberName());
			
			request.setAttribute("msg", "로그인 성공");
			request.setAttribute("url", "/index.jsp");
			RequestDispatcher view = request.getRequestDispatcher("/member/serviceSuccess.jsp");
			view.forward(request, response);
		} else {
			//로그인 실패
			request.setAttribute("msg", "로그인 실패");
			RequestDispatcher view = request.getRequestDispatcher("/member/serviceFailed.jsp");
			view.forward(request, response);
		}
		*/
		
		//2. SELECT COUNT(*) FROM MEMBER_TBL WHERE MEMBER_ID = ?  -> 1 반환됨-> int result 사용
		MemberService service = new MemberService();
		String memberId = request.getParameter("member-id");
		int result = service.selectOne(memberId);
		if(result > 0) {
			//로그인 성공
			HttpSession session = request.getSession();
			session.setAttribute("memberId", memberId);
			
			request.setAttribute("msg", "로그인 성공");
			request.setAttribute("url", "/index.jsp");
			RequestDispatcher view = request.getRequestDispatcher("/member/serviceSuccess.jsp");
			view.forward(request, response);
		} else {
			request.setAttribute("msg", "로그인 실패");
			RequestDispatcher view = request.getRequestDispatcher("/member/serviceFailed.jsp");
			view.forward(request, response);
		}
	}

}
