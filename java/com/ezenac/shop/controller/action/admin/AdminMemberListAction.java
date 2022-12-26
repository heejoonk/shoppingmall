package com.ezenac.shop.controller.action.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.AdminDao;
import com.ezenac.shop.dto.MemberVO;
import com.ezenac.shop.util.Paging;

public class AdminMemberListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "admin/member/memberList.jsp";
		
		HttpSession session = request.getSession();
		String avo = (String)session.getAttribute("loginAdmin");
		if( avo == null) { 
			url = "shop.do?command=admin"; 
		} else {
			
			int page=1;
			
			if( request.getParameter("page")!= null ) {   // ������ �������� �Ķ���ͷ� ���޵ɶ�
				page = Integer.parseInt( request.getParameter("page") );
				session.setAttribute("page", page);
			} else if( session.getAttribute("page")!=null ) {   // ���ư� ���������� �Ҿ��������
				page = (int) session.getAttribute("page") ; 
			} else {   // ó�� ����Ʈ�� �����ؼ� 1���̸� �����Ҷ�
				session.removeAttribute("page");
			}
			String key="";
			if( request.getParameter("key")!=null) {
				key = request.getParameter("key");
				session.setAttribute("key", key);
			} else if( session.getAttribute("key")!=null ) {
				key = (String)session.getAttribute("key");
			} else {
				session.removeAttribute("key");
			}
			Paging paging = new Paging();
			paging.setPage(page);
			AdminDao adao = AdminDao.getInstance();
			int count = adao.getAllCount("member", "name", key);
			paging.setTotalCount(count);
			ArrayList<MemberVO> list = adao.selectMember( paging, key );
			request.setAttribute("memberList", list);
			request.setAttribute("paging", paging);
			session.setAttribute("key3", key);			
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

}
