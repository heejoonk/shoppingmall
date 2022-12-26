package com.ezenac.shop.controller.action.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.MemberDao;
import com.ezenac.shop.dto.MemberVO;

public class JoinAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 전달된 파라미터들을 VO 객체에 넣고 insertMember 메서드를 호출하세요
		MemberDao mdao = MemberDao.getInstance();
		MemberVO mvo = new MemberVO();
		
		mvo.setId(request.getParameter("id"));
		mvo.setPwd(request.getParameter("pwd"));
		mvo.setName(request.getParameter("name"));
		mvo.setEmail(request.getParameter("email"));
		mvo.setZip_num(request.getParameter("zip_num"));
		mvo.setAddress1(request.getParameter("address1"));
		mvo.setAddress2(request.getParameter("address2"));
		mvo.setPhone(request.getParameter("phone"));

		int result = mdao.insertMember(mvo);
		
		HttpSession session = request.getSession();
		if(result==1)  session.setAttribute("message", "회원가입 완료~! 로그인하세요!");
		else session.setAttribute("message", "회원가입 실패~! 다음에 다시 시도하세요! 계속실패하면 관리자에게 문의하세요");

		response.sendRedirect("shop.do?command=loginForm");
	}

}
