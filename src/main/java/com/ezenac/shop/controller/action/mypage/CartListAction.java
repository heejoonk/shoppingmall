package com.ezenac.shop.controller.action.mypage;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.CartDao;
import com.ezenac.shop.dto.CartVO;
import com.ezenac.shop.dto.MemberVO;

public class CartListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "mypage/cartList.jsp";  // 최종 목적지
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		if(mvo == null) {
			url = "shop.do?command=loginForm";
		} else {
			// 로그인 유저의 아이디로 카트 리스트를 검색해서 리턴 받고 리퀘스트에 저장하여 이동합니다
			CartDao cdao = CartDao.getInstance();
			ArrayList<CartVO> list = cdao.selectCart( mvo.getId() );
			request.setAttribute("cartList", list);
			
			// 장바구니 물품을 모두 구매했을때 결제할 금액을 계산합니다.
			// list 에서 상품을 하나하나 꺼내서 price2*Quantity 를 합산하는 방법으로 계산합니다
			int totalPrice = 0;
			for( CartVO cvo : list) 
				totalPrice += ( cvo.getPrice2() * cvo.getQuantity() );
			
			request.setAttribute("totalPrice", totalPrice);
		}
		request.getRequestDispatcher(url).forward(request, response);

	}

}
