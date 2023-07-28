package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MyInfoController
 */
@WebServlet("/member/myInfo.do")
public class MyInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyInfoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이지 이동 2가지
		// 1. with Data : DB에서 가져온 데이터를 같이 결과페이지로 보냄->쿼리문 생각
		// 쿼리문 : 고유한 값에 대한 정보 하나만가져옴 SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?
		// list member result 중 member타입사용
		MemberService service = new MemberService();
		String memberId = request.getParameter("member-id");
		Member member = service.selectOneById(memberId);
		request.setAttribute("member", member); //db에서 가져온 데이터를 정의해 보낼 데이터 세팅함
		RequestDispatcher view = request.getRequestDispatcher("/member/myInfo.jsp");
		view.forward(request, response);
		//첫번째파라미터는 "키값", 두번째파라미터는 변수(데이터가 저장되어있음) 
		// 2. without Data : 요청결과에 대해 데이터없이 단순 페이지 이동 ex) 로그아웃 LogoutController
//		response.sendRedirect("");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
