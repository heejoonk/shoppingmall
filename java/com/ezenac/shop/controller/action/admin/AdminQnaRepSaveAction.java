package com.ezenac.shop.controller.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.AdminDao;
import com.ezenac.shop.dto.QnaVO;

public class AdminQnaRepSaveAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
String url = "shop.do?command=adminQnaDetail";
		
		HttpSession session = request.getSession();
		String avo = (String)session.getAttribute("loginAdmin");
		if( avo == null) url="shop.do?command=admin";
		else {
			AdminDao adao = AdminDao.getInstance();
			//QnaVO qvo = new QnaVO();
			//qvo.setQseq( Integer.parseInt( request.getParameter("qseq") ) );
			//qvo.setReply( request.getParameter("reply") );
			//adao.updateQna( qvo );
			int qseq = Integer.parseInt( request.getParameter("qseq") );
			String reply = request.getParameter("reply");
			adao.updateQna( qseq, reply);
			//url = url + "&qseq=" + qvo.getQseq();
			url = url + "&qseq=" + qseq;
		}
		request.getRequestDispatcher(url).forward(request, response);

	}

}
