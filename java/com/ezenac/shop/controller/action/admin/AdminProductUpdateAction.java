package com.ezenac.shop.controller.action.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.shop.controller.action.Action;
import com.ezenac.shop.dao.AdminDao;
import com.ezenac.shop.dto.ProductVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class AdminProductUpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "shop.do?command=adminProductDetail";
		HttpSession session = request.getSession();
		String avo = (String)session.getAttribute("loginAdmin");
		if( avo == null) url = "shop.do?command=admin"; 
		else {
			// mulpartRequest 를 이용해서  전달된 내용들을 ProductVO 에 구성해서 해당 레코드를 수정하세요
			// 수정후 productDetail.jsp 로 이동하여 수정한 내용을 바로 확인합니다
			ProductVO pvo = new ProductVO();
			ServletContext context = session.getServletContext();
		    String path = context.getRealPath("product_images");
		    MultipartRequest multi = new MultipartRequest( 
		    		request,	path,	 5*1024*1024, "UTF-8",	 new DefaultFileRenamePolicy()  );
		    pvo.setPseq(Integer.parseInt( multi.getParameter("pseq") ));
		    pvo.setKind( multi.getParameter("kind"));
		    pvo.setName( multi.getParameter("name"));
		    pvo.setPrice1( Integer.parseInt( multi.getParameter("price1")));
		    pvo.setPrice2( Integer.parseInt( multi.getParameter("price2")));
		    pvo.setPrice3( Integer.parseInt( multi.getParameter("price3")));
		    pvo.setContent(multi.getParameter("content"));
		    pvo.setUseyn(multi.getParameter("useyn"));
		    pvo.setBestyn(multi.getParameter("bestyn"));
		    if(multi.getFilesystemName("image") == null ) pvo.setImage(multi.getParameter("oldImage") );
		    else 	pvo.setImage( multi.getFilesystemName("image") );
		    AdminDao adao = AdminDao.getInstance();
		    adao.updateProduct(pvo);
		    url = url + "&pseq=" + pvo.getPseq();
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

}
