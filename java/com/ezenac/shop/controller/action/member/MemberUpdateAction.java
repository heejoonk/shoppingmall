package com.ezenac.shop.controller.action.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.MemberDao;
import com.ezenac.shop.dto.MemberVO;

public class MemberUpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//전달된 파라미터들로 VO 객체를 채우고  updateMember  메서드로 회원정보를 수정한후
		// 세션값을 수정하세요.
		// 최종 목적지는  main.jsp 입니다.
		MemberVO mvo = new MemberVO();
		
		mvo.setId(request.getParameter("id"));
		mvo.setPwd(request.getParameter("pwd"));
		mvo.setName(request.getParameter("name"));
		mvo.setEmail(request.getParameter("email"));
		mvo.setZip_num(request.getParameter("zip_num"));
		mvo.setAddress1(request.getParameter("address1"));
		mvo.setAddress2(request.getParameter("address2"));
		mvo.setPhone(request.getParameter("phone"));
		
		MemberDao mdao = MemberDao.getInstance();
		mdao.updateMember(mvo);
		
		HttpSession session=request.getSession();  // 정보가 수정되었으면 수정된 내용을 세션의 로그인 값을 변경합니다
		session.setAttribute("loginUser", mvo);
		
		RequestDispatcher dp=request.getRequestDispatcher("shop.do?command=index");
		dp.forward(request, response);
		

	}

}
