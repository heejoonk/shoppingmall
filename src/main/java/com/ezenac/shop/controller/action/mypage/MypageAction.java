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

public class MypageAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "mypage/mypage.jsp";
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("loginUser");
	    if(mvo==null) {
	    	url = "shop.do?command=loginForm";
	    }else {
	    	
	    	// mypage.jsp �� ���� ���޵Ǽ� ȭ�鿡 ������ ����Ʈ ����
	    	ArrayList<OrderVO> finalList = new ArrayList<OrderVO>();
	    	// (  finalList.get(0).getPname() -> "XXXX �� 2��" )
	    	
	    	// �������� �ֹ� ����
	    	// ���� �α��� �� ������� ���� ��۾ȵ� �ֹ������� �������ϴ�
	    	// ���� ��� �ѹ��� 2,3���� ��ǰ��  3ȸ�� ���ļ� �ֹ��� ���¶��.... �׸��� �� �ֹ����� �ϳ��� ����� �ȵ� ����(�����)���
	    	// �������� �ֹ� ������ 3���� ǥ�õ˴ϴ�(orders���̺� ���� �ֹ� �Ǻ� ǥ��)
	    	// ǥ�ó����� �ֹ� �Ǻ� ��ǥ��ǰ�� �̸� �̿��Ͽ�    ������ ���� 3,   �ܿ����� �� 2 ...  ��� �� �������� �� 3���� ǥ�õ˴ϴ�
	    	// �׸��� �� �࿡��   �󼼺��� ��ư�� �־ Ŭ���ϸ� �� �ֹ��� ���� ������ ��ǰ�� �� �� �ս��ϴ�   
	    	
	    	// �̸� ���ؼ� ���� ���� �ʿ��� ������  �ֹ� ��ȣ�� �Դϴ�
	    	// order_view ���� �ֹ��� ���̵�� �˻��ϰ�, result �� 1�� �ֹ����� �˻��ؼ�,  �ֹ� ��ȣ���� ��ȸ�մϴ�
	    	// ���� ���� �� ���¶��  �ֹ���ȣ���� ������ �����ϴ�    
	    	// 22 22 22  24 24  26 26 26  27 27  <- ��ȸ�� �ֹ� ��ȣ���Դϴ�
	    	
	    	// �׷��� ���� �츮���� �ʿ��Ѱ�   22  24  26  27  �̹Ƿ�, ��ȸ�Ҷ� distinct Ű���带 �Ἥ ��ȸ�� �ɴϴ�
	    	// select distinct oseq from order_view where id=? and result='1'
	    	// �ֹ���ȣ(oseq)�� ��ȸ�Ҳ���� orders ���̺����� distinct ���� ��ȸ�ϸ� �ɵ��� ������
	    	// orders ���̺����� result �ʵ尡 ���  ����� �ֹ��� ���е� ��ȣ�� ��ȸ�� ���� �ʽ��ϴ�
	    	
	    	// �ߺ��� ���� �����(ó����) �ֹ���ȣ���� ��ȸ�մϴ�
	    	OrderDao odao = OrderDao.getInstance();
	    	ArrayList<Integer> oseqList = odao.selectOseqOrderIng( mvo.getId() );
	    	
	    	// ��ȸ�� �ֹ���ȣ��� �ݺ������մϴ�
	    	for( Integer oseq : oseqList) {
	    		
	    		// ���� �ֹ� ��ȣ��  �ֹ������� ��ȸ�մϴ�
	    		ArrayList<OrderVO> orderListByOseq = odao.selectOrdersByOseq( oseq );
	    		
	    		// ��ȸ�� �ֹ�����Ʈ�� ������ ��ǰ�� �ִٸ�....
	    		// ù��°��ǰ�� �����ϴ�.
	    		OrderVO  ovo = orderListByOseq.get(0);
	    		
	    		// ���� ��ǰ�� �̸��� "�����ǰ�̸� ���� X��" ���� �����մϴ�
	    		ovo.setPname( ovo.getPname() + " ���� " + orderListByOseq.size() + " ��");
	    		ovo.setResult("4");  // ��ǥ��ǰ�� ����� "����Ȯ��"���� ����
	    		// ������ �ѱݾ��� ����� ��
	    		int totalPrice = 0;
	            for (OrderVO ovo1 : orderListByOseq) { 
	            	totalPrice += ovo1.getPrice2() * ovo1.getQuantity();
	            	if( !ovo1.getResult().equals("4") ) 
	            		ovo.setResult( ovo1.getResult() );
	            	// �ֹ��� ���Ե� ��ǰ�� ����� �ϳ��� ��ȸ�ؼ� 4�� �ƴϸ� ��ǥ��ǰ�� ����� ���� ��ǰ�� ��������� 
	            }
	            
	            // ovo ��ü�� price2 �� �տ��� ����� �ѱݾ����� �����մϴ�
	            ovo.setPrice2( totalPrice );
	            
	            // ��� ������ ��ģ ovo�� finalList �� ����ϴ�
	            finalList.add( ovo );
	    	}
	    	request.setAttribute("orderList", finalList);
	    	request.setAttribute("title", "���� ���� �ֹ� ����");
	    }
	    request.getRequestDispatcher(url).forward(request, response);
	}

}






