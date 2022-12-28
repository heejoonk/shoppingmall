package com.ezenac.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ezenac.shop.dto.CartVO;
import com.ezenac.shop.dto.OrderVO;
import com.ezenac.shop.util.Dbman;

public class OrderDao {

	private OrderDao() {}
	private static OrderDao itc = new OrderDao();
	public static OrderDao getInstance() { return itc; }
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public int insertOrder(ArrayList<CartVO> cartList, String id) {
		int oseq = 0;
		
		con = Dbman.getConnection();
		
		try {
			// 1. 구매자 아이디와 시퀀스를 이용해서  orders 테이블에 레코드를 추가
			String sql = "insert into orders(oseq, id) values( orders_seq.nextVal , ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  id);
			pstmt.executeUpdate();			
			
			// 2. orders 테이블에 방금 추가한 레코드의  oseq 필드값을 조회. 
			//    => 1씩 늘어나는 시퀀스 값으로 oseq 가 입력되었을껏이므로 가장 큰 oseq 값이 방금 추가된 값이 됩니다
			sql = "select max(oseq) as max_oseq from orders";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if( rs.next() ) oseq = rs.getInt("max_oseq");
			
			// 3. 카트목록과  방금 조회한 가장 큰 oseq 를 이용해서 order_detail 에 레코드를 카트 상품 단위로 하나씩 추가합니다
			sql = "insert into order_detail( odseq, oseq, pseq, quantity ) "
					+ "values( order_detail_seq.nextVal, ? , ? , ? )";
			for( CartVO cvo : cartList ) {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,  oseq);
				pstmt.setInt(2, cvo.getPseq() );
				pstmt.setInt(3,  cvo.getQuantity() );
				pstmt.executeUpdate();
			}
			
			// 4. order_detail 에 추가된 카트 내용은  cart 테이블에서 처리되었으므로 삭제 또는 result  를 2로 변경 또는 삭제합니다
			sql = "delete from cart where cseq=?";
			// sql = "update cart set result='2' where cseq=?";
			for( CartVO cvo : cartList ) {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,  cvo.getCseq() );
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}
		return oseq;
	}

	public ArrayList<OrderVO> selectOrdersByOseq(int oseq) {
		ArrayList<OrderVO> list = new ArrayList<OrderVO>();
		String sql = "select * from order_view where oseq=?";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, oseq);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				    OrderVO ovo = new OrderVO();
					ovo.setOdseq(rs.getInt("odseq"));					ovo.setOseq(rs.getInt("oseq"));
					ovo.setId(rs.getString("id"));							ovo.setIndate(rs.getTimestamp("indate"));
					ovo.setMname(rs.getString("mname"));			ovo.setZip_num(rs.getString("zip_num"));
					ovo.setAddress1(rs.getString("address1"));		ovo.setAddress2(rs.getString("address2"));
					ovo.setPhone(rs.getString("phone"));				ovo.setPname(rs.getString("pname"));
					ovo.setPrice2(rs.getInt("price2"));					ovo.setPseq(rs.getInt("pseq"));
					ovo.setQuantity(rs.getInt("quantity"));			ovo.setResult(rs.getString("result"));
					list.add(ovo);
			}
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}
		return list;
	}
	

	public ArrayList<Integer> selectOseqOrderIng(String id) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		String sql = "select distinct oseq from order_view "
				+ " where id=? and result IN('1', '2', '3') order by oseq desc";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  id);
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				list.add(  rs.getInt("oseq")  ); // 주문번호만 list 에 담습니다
			}
		} catch (SQLException e) {   e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);   
		}
		
		return list;
	}

	public ArrayList<Integer> selectOseqAll(String id) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String sql = "select distinct oseq from order_view "
				+ " where id=? order by oseq desc";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  id);
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				list.add(  rs.getInt("oseq")  ); // 주문번호만 list 에 담습니다
			}
		} catch (SQLException e) {   e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);   
		}
		
		return list;
	}

	public void updateOrderEnd(int odseq) {
		
		String sql = "update order_detail set result='4' where odseq=?";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  odseq);
			pstmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);  }
		
	}
	
	
}















