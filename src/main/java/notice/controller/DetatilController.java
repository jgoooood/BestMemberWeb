package notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class DetatilController
 */
@WebServlet("/notice/detail.do")
public class DetatilController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetatilController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//상세조회 쿼리문 : SELECT * FROM NOTICE_TBL WHERE NOTICE_NO = ?
		//리턴타입 : Notice
		//쿼리스트링 분석해서 왼쪽의 값을 가져옴->문자열타입이기 때문에 숫자로 변환Integer.parseInt(request.getParameter("noticeNo"));
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		NoticeService service = new NoticeService();
		Notice notice = service.selectOneByNo(noticeNo);
		if(notice != null) {
			//상세 페이지로 이동(withData)
			// servlet : request.setAttribute로 jsp에서 객체에 접근할수 있도록 값을 전달
			// jsp : 서블릿에서 보낸 값을 가져오는 requestScope.객체명을 통해 값을 가져옴
			request.setAttribute("notice", notice);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/notice/detail.jsp");
			view.forward(request, response);
		} else {
			//실패 페이지로 이동
			request.setAttribute("msg", "데이터가 존재하지 않습니다.");
			request.getRequestDispatcher("/WEB-INF/views/common/serviceFailed.jsp").forward(request, response);
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
