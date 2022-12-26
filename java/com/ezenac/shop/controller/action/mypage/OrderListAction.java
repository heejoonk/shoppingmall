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

public class OrderListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "mypage/orderList.jsp";
		int oseq = Integer.parseInt(request.getParameter("oseq"));
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
		if (mvo == null) {
		    url = "shop.do?command=loginForm";
		}else {
			
			OrderDao odao = OrderDao.getInstance();
			// order_view에서 주문번호로 주문을 검색
			ArrayList<OrderVO> list = odao.selectOrdersByOseq( oseq );
			
			// 조회된 주문들의 총결제금액을 계산
			int totalPrice = 0;
			for( OrderVO ovo : list) totalPrice+=ovo.getPrice2() * ovo.getQuantity();	
			
			// 리스트와 결제금액을 request 에 태워서 보냅니다.
			request.setAttribute("orderList", list);
			request.setAttribute("totalPrice", totalPrice);
			
		}
		request.getRequestDispatcher(url).forward(request, response);

	}

}
