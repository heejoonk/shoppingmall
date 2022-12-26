package com.ezenac.shop.controller.action.qna;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.QnaDao;
import com.ezenac.shop.dto.MemberVO;
import com.ezenac.shop.dto.QnaVO;
import com.ezenac.shop.util.Paging;

public class QnaListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "qna/qnaList.jsp";		

		HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
	    if (mvo == null) {
	    	url = "shop.do?command=loginForm";
	    } else {
	    	// 로그인한 아이디로 qna 목록을 조회하고 리턴받습니다(메서드이름 selectQna)
	    	QnaDao qdao = QnaDao.getInstance();
	    	// ArrayList<QnaVO> list = qdao.selectQna( mvo.getId() );
			int page=1;	// 페이지 설정 : request 나 session 에 page 값이 있다면 그 값으로, 없다면 1로 현재페이지를 설정합니다
			if( request.getParameter("page") != null ) {    // 리퀘스트에 파라미터로 page가 전달된다면 page변수값을 그값으로 대체
				page = Integer.parseInt( request.getParameter("page") );
				session.setAttribute("page", page);
				// 2이상의 페이지에서 게시물을 보다가 다시 게시판으로 돌아갈때, 보던 페이지번호를 잃어 버리는 경우가 많아서
				// 현재 보고 있는 페이지번호를 세션에 저장했다가
			} else if( session.getAttribute("page")!=null) {  
				//  request에 파라미터가 없다면 한번 더 세션을 검사하여 페이지번호를 대체할지를 결정합니다
				page = (Integer) session.getAttribute("page");
			} else {
				session.removeAttribute("page"); 
			}
			
			Paging paging = new Paging();
			paging.setPage(page);  // 위에서 결정된 페이지번호 객체에 저장
			
			int count = qdao.getAllCount( mvo.getId() );  // 로그인 아이디로 검색한 QnA 게시물의 총 갯수를 구합니다
			paging.setTotalCount(count);  // 게시물 총갯수를 totaCount 변수에 저장합니다.  이때 paging() 메서드도 호출
			// 모든 객체의 멤버변수 준비 완료
			
			// 현재 페이지에 표시할 게시물(5개, 로그인한 유저가 작성한 startNum부터 endNum 까지)을 조회해서 list로 리턴 받습니다
			ArrayList<QnaVO> list = qdao.selectQna( mvo.getId(), paging );  
			
	    	request.setAttribute("qnaList", list);
	    	request.setAttribute("paging", paging);  // paging 객체도  request 에 담습니다
	    }
	    request.getRequestDispatcher(url).forward(request, response);
		
	}

}
