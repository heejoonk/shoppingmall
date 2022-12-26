package com.ezenac.shop.controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.shop.dao.ProductDao;
import com.ezenac.shop.dto.ProductVO;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 베스트 상품과 신상품을 데이터베이스에서 조회해서  리스트로 리턴받고, 이를 리퀘스트에 넣어서 main.jsp로 이동합니다
		ProductDao pdao = ProductDao.getInstance();
		
		ArrayList<ProductVO> blist =  pdao.getBestList();		
		ArrayList<ProductVO> nlist =  pdao.getNewList();
		
		request.setAttribute("bestList", blist);
		request.setAttribute("newList", nlist);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
		dispatcher.forward(request, response);


	}

}
