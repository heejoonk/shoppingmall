package com.ezenac.shop.controller.action.product;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.ProductDao;
import com.ezenac.shop.dto.ProductVO;

public class CategoryAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String kind = request.getParameter("kind");
		
		ProductDao pdao = ProductDao.getInstance();
		ArrayList<ProductVO> list = pdao.selectKindProductList(kind);
		
		request.setAttribute("productKindList", list);
		
		String url = "product/productKind.jsp";
		request.getRequestDispatcher(url).forward(request, response);
		// RequestDispatcher dp = request.getRequestDispatcher(url);
		// dp.forward(request, response);

	}

}
