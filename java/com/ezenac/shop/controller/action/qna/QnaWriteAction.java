package com.ezenac.shop.controller.action.qna;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.QnaDao;
import com.ezenac.shop.dto.MemberVO;
import com.ezenac.shop.dto.QnaVO;

public class QnaWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "shop.do?command=qnaList"; // 목적지는  qnaList
		
		HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO) session.getAttribute("loginUser");    
	    
	    if (mvo == null) {
	    	url = "shop.do?command=loginForm";
	    }else{
	    	QnaVO qvo = new QnaVO();
	    	qvo.setSubject(request.getParameter("subject"));
	    	qvo.setContent(request.getParameter("content"));
	    	qvo.setId( mvo.getId() );
	    	
	    	QnaDao qdao = QnaDao.getInstance();
	    	qdao.insertQna(qvo);
	    }
	    response.sendRedirect(url);

	}

}
