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

public class OrderAllAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "mypage/mypage.jsp";
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
	    if(mvo==null) {
	    	url = "shop.do?command=loginForm";
	    }else {
	    	
	    	ArrayList<OrderVO> finalList = new ArrayList<OrderVO>();
	    	OrderDao odao = OrderDao.getInstance();
	    	ArrayList<Integer> oseqList = odao.selectOseqAll( mvo.getId() );
	    	
	    	for( Integer oseq : oseqList) {
	    		ArrayList<OrderVO> orderListByOseq = odao.selectOrdersByOseq( oseq );
	    		OrderVO  ovo = orderListByOseq.get(0);
	    		ovo.setPname( ovo.getPname() + " 포함 " + orderListByOseq.size() + " 건");
	    		ovo.setResult("4");
	    		int totalPrice = 0;
	            for (OrderVO ovo1 : orderListByOseq) { 
	            	totalPrice += ovo1.getPrice2() * ovo1.getQuantity();
	            	if( !ovo1.getResult().equals("4") )
	            		ovo.setResult( ovo1.getResult() );
	            }
	            ovo.setPrice2( totalPrice );
	            finalList.add( ovo );
	    	}
	    	request.setAttribute("orderList", finalList);
	    	request.setAttribute("title", "총 주문내역");
	    }
	    request.getRequestDispatcher(url).forward(request, response);

	}

}
