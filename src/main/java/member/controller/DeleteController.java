package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;

/**
 * Servlet implementation class DeleteController
 */
@WebServlet("/member/delete.do")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberService service = new MemberService();
		//요청에 대한 반응->쿼리문 생각
		//회원삭제쿼리문 : DELETE FROM MEMBER_TBL WHERE MEMBER_ID = ?
		//a링크이기 때문에 쿼리스트링 직접 작성해줌
		//<a href="/member/delete.do?memberId=${sessionScope.memberId }">탈퇴하기</a>
		String memberId = request.getParameter("memberId");
		int result = service.deleteMember(memberId);
		if(result > 0) {
			//페이지 이동 2가지
			//1.with Data : request.setAttribute("변수명", 내용)
			//성공
			request.setAttribute("msg", "회원탈퇴 완료"); //메세지담당
			request.setAttribute("url", "/member/logout.do"); //location변환 담당
			RequestDispatcher view = request.getRequestDispatcher("/member/serviceSuccess.jsp");
			view.forward(request, response);
			//2.without Date : response.sendRedirect("");
			//회원탈퇴 후 로그아웃 화면으로 단순페이지 이동 
//			response.sendRedirect("/member/logout.do"); 
		} else {
			//실패
			request.setAttribute("msg", "회원탈퇴 실패");
			RequestDispatcher view = request.getRequestDispatcher("/member/serviceFailed.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
