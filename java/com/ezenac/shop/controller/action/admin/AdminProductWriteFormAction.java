package com.ezenac.shop.controller.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;

public class AdminProductWriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "admin/product/productWrite.jsp";
		HttpSession session = request.getSession();
		String loginAdmin = (String)session.getAttribute("loginAdmin");
		if( loginAdmin == null) { 
			url = "shop.do?command=admin";
		}else {
			String [] kindList = { "Heels", "Boots", "Sandals", "Shcakers",	"Slipers", "On Sale" };
			// kind 값 입력시 화면에 표시될 카테고리 이름들을 문자 배열에 넣고 request  에 담아서 이동합니다.
			request.setAttribute("kindList", kindList);
		}
		request.getRequestDispatcher(url).forward(request, response);

	}

}
