package com.ezenac.shop.controller.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.OrderDao;
import com.ezenac.shop.dto.MemberVO;

public class OrderEndAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "shop.do?command=orderDetail&oseq=" + request.getParameter("oseq");
		HttpSession session = request.getSession();
		MemberVO avo = (MemberVO)session.getAttribute("loginUser");
		if( avo == null) { 
			url = "shop.do?command=loginForm"; 
		} else {
			
			int odseq = Integer.parseInt( request.getParameter("odseq") );
			
			OrderDao odao = OrderDao.getInstance();
			odao.updateOrderEnd(  odseq  );
		}
		request.getRequestDispatcher(url).forward(request, response);

	}

}
