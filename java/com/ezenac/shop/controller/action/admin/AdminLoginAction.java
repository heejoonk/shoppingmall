package com.ezenac.shop.controller.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.AdminDao;

public class AdminLoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String workId = request.getParameter("workId");
	    String workPwd = request.getParameter("workPwd");
	    
	    String url = "shop.do?command=admin";  // 관리자 로그인창
	    
	    AdminDao adao = AdminDao.getInstance();
	    String adminUserPwd = adao.workerCheck( workId );
	    
	    if( adminUserPwd == null ) 
	    	request.setAttribute("message", "아이디가 없습니다");
	    else if( !adminUserPwd.equals(workPwd) ) 
	    	request.setAttribute("message", "비밀번호가 맞지 않습니다");
	    else if( adminUserPwd.equals(workPwd) ) {
	    	HttpSession session = request.getSession();
	    	session.setAttribute("loginAdmin", workId );
	    	url = "shop.do?command=adminProductList";
	    }
	    request.getRequestDispatcher(url).forward(request, response);
	}

}
