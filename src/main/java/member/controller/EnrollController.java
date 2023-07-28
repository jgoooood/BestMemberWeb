package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class EnrollController
 */
@WebServlet("/member/register.do")
public class EnrollController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrollController() {
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
		//한글 세팅
		request.setCharacterEncoding("UTF-8");
		
		
		String memberId = request.getParameter("member-id");
		String memberPw = request.getParameter("member-pw");
		String memberName = request.getParameter("member-name");
		int memberAge = Integer.parseInt(request.getParameter("member-age"));
		String memberGender = request.getParameter("member-gender");
		String memberEmail = request.getParameter("member-email");
		String memberPhone = request.getParameter("member-phone");
		String memberAddress = request.getParameter("member-address");
		String memberHobby = request.getParameter("member-hobby");
		
		//생성자를 통해 member객체에 한번에 저장
		Member member = new Member(memberId, memberPw, memberName, memberAge, memberGender, memberEmail, memberPhone, memberAddress, memberHobby);
		
		//서비스호출->(서비스는 dao호출함)
		MemberService service = new MemberService();
		//INSERT INTO MEMBER_TBL VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT, DEFAULT, DEFAULT)
		//서비스 클래스로 메소드 호출해서 값 전달
		int result = service.insertMember(member);
		if(result > 0) {
			//성공하면 성공페이지로 이동 -> RequestDispatcher
			//성공페이지에서 원하는 문구를 표시하거나 alert창 띄울 수 있음
			request.setAttribute("msg", "회원가입 성공");
			request.setAttribute("url", "/index.jsp");
			request.getRequestDispatcher("/member/serviceSuccess.jsp")
			.forward(request, response);
		} else {
			//실패
			request.getRequestDispatcher("/member/serviceFail.jsp")
			.forward(request, response);
		}
	}

}
