package com.ezenac.shop.controller.action.mypage;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.OrderDao;
import com.ezenac.shop.dto.MemberVO;
import com.ezenac.shop.dto.OrderVO;

public class OrderDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int oseq = Integer.parseInt( request.getParameter("oseq") );
		HttpSession session = request.getSession();
	    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
	    
	    String url = "";
	    if (loginUser == null) {
	    	url = "shop.do?command=loginForm";
	    }else {
	    	
	    	// 주문번호로 조회한 주문(상품) 리스트 리턴
	    	OrderDao odao = OrderDao.getInstance();
	    	ArrayList<OrderVO> orderList = odao.selectOrdersByOseq(oseq);
	    	
	    	// 리퀘스트에 주문리스트를 담습니다
	    	request.setAttribute("orderList", orderList);
	    	
	    	// 리턴받은 주문리스트의 첫번째를 이용하여 orderDetail 이란 이름으로 리퀘스트에 담습니다
	    	request.setAttribute("orderDetail", orderList.get(0) );
	    				
	    	//주문 총금액을 계산하고 리퀘스트에 담습니다
	    	int totalPrice = 0;
	    	for( OrderVO ovo : orderList)
	    	   		totalPrice += ovo.getPrice2() * ovo.getQuantity();
	    	request.setAttribute("totalPrice" , totalPrice);
	    	url = "mypage/orderDetail.jsp";   	
	    }
	    request.getRequestDispatcher(url).forward(request, response);
	}

}
