package com.ezenac.shop.controller.action.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.CartDao;

public class CartDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String [] cseqArr = request.getParameterValues("cseq");
		
		CartDao cdao = CartDao.getInstance();
		for( String cseq : cseqArr ) {
			cdao.deleteCart( Integer.parseInt(cseq) );
		}
		
		response.sendRedirect("shop.do?command=cartList");
		// request.getRequestDispatcher("shop.do?command=cartList").forward(request, response);

	}

}
