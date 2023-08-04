package notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;
import notice.model.vo.PageData;

/**
 * Servlet implementation class ListController
 */
@WebServlet("/notice/list.do")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeService service = new NoticeService();
		//currentPage를 넘겨줘야 DAO에서 시작값과 끝의 값을 동적으로 계산하여 목록을 출력함
		//getParameter로 currentPage의 값을 가져와야 하는데 수동으로 쿼리스트링을 만들어서 가져올 수 있게 함
		//http://127.0.0.1:8888/notice/list.do?currentPage=1 -> 현재 페이지currentPage에 1이 담겨서 넘어감
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		// List<Notice> nList = service.selectNoticeList(currentPage); <-반환타입 변경 이전 코드
		PageData pd = service.selectNoticeList(currentPage);
		List<Notice> nList = pd.getnList();  //nList는 이제 pd에서 가져와 사용할 수 있음
		request.setAttribute("nList", nList);
		request.setAttribute("pageNavi", pd.getPageNavi()); //생성한 네비게이터를 jsp가 사용할 수 있음->단 가져올 scope코드 추가해야함
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/notice/list.jsp");
		view.forward(request, response);
		// 만약 http://127.0.0.1:8888/notice/list.do?currentPage=4 url접속하면 46~60목록이 나옴
		// 1페이지 : 1~15, 2페이지 16~30, 3페이지 31~45, 4페이지 46~60
		///////////////////여기까지 수동으로 url을 입력해서 공지사항 목록을 15개씩 가져옴///////////////////
		
		//**수동으로 url을 입력하지 않고 페이지번호를 눌러서 동적으로 목록조회하기 : 페이지 네비게이터 생성
		//페이지 네비게이터는 DAO에서 생성해줌. generatePageNavi()메소드
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
