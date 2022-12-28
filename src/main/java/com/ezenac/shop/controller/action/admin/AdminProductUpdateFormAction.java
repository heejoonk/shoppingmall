package com.ezenac.shop.controller.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.ProductDao;
import com.ezenac.shop.dto.ProductVO;

public class AdminProductUpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "admin/product/productUpdate.jsp";
		HttpSession session = request.getSession();
		String avo = (String)session.getAttribute("loginAdmin");
		if( avo == null) { 
			url = "shop.do?command=admin"; 
		}else {
			// 전달된 상품번호로 상품상세내역을 조회하고 리퀘스트에 저장합니다. kind 들도 배열에 넣고 레퀘스트에 저장합니다
			int pseq = Integer.parseInt( request.getParameter("pseq") );
			
			ProductDao pdao = ProductDao.getInstance();
			ProductVO pvo = pdao.getProduct(pseq);
			
			request.setAttribute("productVO", pvo);
			
			String kindList[] = { "Heels", "Boots", "Sandals", "Shcakers", "Slipers", "Sale" };    
			request.setAttribute("kindList", kindList);
		}
		request.getRequestDispatcher(url).forward(request, response);

	}

}
