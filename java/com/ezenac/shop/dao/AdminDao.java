package com.ezenac.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ezenac.shop.dto.MemberVO;
import com.ezenac.shop.dto.OrderVO;
import com.ezenac.shop.dto.ProductVO;
import com.ezenac.shop.dto.QnaVO;
import com.ezenac.shop.util.Dbman;
import com.ezenac.shop.util.Paging;

public class AdminDao {

	private AdminDao() {}
	private static AdminDao instance = new AdminDao();
	public static AdminDao getInstance() { return instance;}
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public String workerCheck(String workId) {
		String pwd = null;
		
		String sql = "select * from worker where id = ?";
		con = Dbman.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, workId);
			rs = pstmt.executeQuery();
			if( rs.next() )
				pwd = rs.getString("pwd");
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}
		return pwd;
	}

	public ArrayList<ProductVO> selectProduct( Paging paging, String key ) {
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		con = Dbman.getConnection();
		String sql = " select * from ("
				+ " select * from ("
				+ " select rownum as rn, p.* from "
				+ " ((select * from product    where name like '%'||?||'%'    order by pseq desc) p)"
				+ " ) where rn>=?"
				+ " ) where rn<=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  key);
			pstmt.setInt(2, paging.getStartNum());
			pstmt.setInt(3,  paging.getEndNum());
			rs = pstmt.executeQuery();
			while(rs.next()) {
		    	ProductVO pvo = new ProductVO();
    	    	pvo.setPseq(rs.getInt("pseq"));
    	    	pvo.setIndate(rs.getTimestamp("indate"));
    	    	pvo.setName(rs.getString("name"));
    	    	pvo.setPrice1(rs.getInt("price1"));
    	    	pvo.setPrice2(rs.getInt("price2"));
    	    	pvo.setPrice3(rs.getInt("price3"));
    	    	pvo.setImage(rs.getString("image"));
    	    	pvo.setUseyn(rs.getString("useyn"));
    	        pvo.setBestyn(rs.getString("bestyn"));
    	    	list.add(pvo);
			} 
		}catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);  }
		return list;
	}

	public int getAllCount( String tableName, String fieldName, String key ) {
		int count = 0;
		con = Dbman.getConnection();
		String sql = "select count(*) as cnt from " + tableName + " where " + fieldName + " like '%'||?||'%'";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  key);
			rs = pstmt.executeQuery();
			if(rs.next())
				count = rs.getInt("cnt");
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);
		}
		return count;
	}

	public void insertProduct(ProductVO pvo) {
		
		String sql = "insert into product( pseq, kind, name, price1, price2, price3, "
				+ " content, image) values( product_seq.nextVal, ? , ? , ? , ? , ? , ? , ?)";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pvo.getKind());      
		    pstmt.setString(2, pvo.getName());
		    pstmt.setInt(3, pvo.getPrice1());
		    pstmt.setInt(4, pvo.getPrice2());
		    pstmt.setInt(5, pvo.getPrice3());
		    pstmt.setString(6, pvo.getContent());
		    pstmt.setString(7, pvo.getImage());  
		    pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);  }
		
	}

	public void updateProduct(ProductVO pvo) {
		String sql = "update product set kind=?, useyn=?, name=?, price1=?, "
				+ " price2=?, price3=?, content=?, image=?, bestyn=?  where pseq=?";
		
		con = Dbman.getConnection();
		try {			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pvo.getKind());
		    pstmt.setString(2, pvo.getUseyn());
		    pstmt.setString(3, pvo.getName());
		    pstmt.setInt(4, pvo.getPrice1());
		    pstmt.setInt(5, pvo.getPrice2());
		    pstmt.setInt(6, pvo.getPrice3());
		    pstmt.setString(7, pvo.getContent());
		    pstmt.setString(8, pvo.getImage());
		    pstmt.setString(9, pvo.getBestyn());
		    pstmt.setInt(10, pvo.getPseq());
		    pstmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);  
		}
		
	}

	public ArrayList<OrderVO> selectOrder(Paging paging, String key) {
		ArrayList<OrderVO> list = new ArrayList<OrderVO>();
		String sql = "select * from ( "
				+ " select * from ( "
				+ " select rownum as rn, o.* from "
				+ " ((select * from order_view where mname like '%'||?||'%' order by result, odseq desc) o) "
				+ " ) where rn >= ? "
				+ " ) where rn <= ? ";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  key);
			pstmt.setInt(2,  paging.getStartNum() );
			pstmt.setInt(3,  paging.getEndNum() );
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderVO ovo = new OrderVO();
				ovo.setOdseq(rs.getInt("odseq"));					ovo.setOseq(rs.getInt("oseq"));
				ovo.setId(rs.getString("id"));							ovo.setIndate(rs.getTimestamp("indate"));
				ovo.setMname(rs.getString("mname"));			ovo.setZip_num(rs.getString("zip_num"));
				ovo.setAddress1(rs.getString("address1"));		ovo.setPhone(rs.getString("phone"));
				ovo.setPseq(rs.getInt("pseq"));						ovo.setQuantity(rs.getInt("quantity"));
				ovo.setPname(rs.getString("pname"));			ovo.setPrice2(rs.getInt("price2"));
				ovo.setResult(rs.getString("result"));				ovo.setAddress2(rs.getString("address2"));
				list.add(ovo);
			}
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs); }
		return list;
	}

	public void updateOrderResult(int odseq) {
		
		String sql = "update order_detail set result=result+1 where odseq=?";
		// 데이터 수정시 view 를 수정하지 않고 , view  의 원본이 되는 테이블(order_detail)을 수정하는것이 좋습니다.
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  odseq);
			pstmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);  }
		
	}

	public ArrayList<MemberVO> selectMember(Paging paging, String key) {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		String sql = " select * from ("
				+ " select * from ("
				+ " select rownum as rn, m.* from "
				+ "(( select * from meber where name like '%'||?||'%' order by indate desc ) m)"
				+ " ) where rn>=?"
				+ " ) where rn<=?";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  key);
			pstmt.setInt(2,  paging.getStartNum() );
			pstmt.setInt(3,  paging.getEndNum() );
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString("id"));
				mvo.setPwd(rs.getString("pwd"));
				mvo.setName(rs.getString("name"));
				mvo.setEmail(rs.getString("email"));
				mvo.setZip_num(rs.getString("zip_num"));
				mvo.setAddress1(rs.getString("address1"));
				mvo.setPhone(rs.getString("phone"));
				mvo.setUseyn(rs.getString("useyn"));
				mvo.setIndate(rs.getTimestamp("indate"));
				list.add(mvo);
			}
		} catch (SQLException e) {	e.printStackTrace();
		} finally {  Dbman.close(con, pstmt, rs);  }
		return list;
	}

	public int getAllCountForQna(String key) {
		int count=0;
		String sql = "select count(*) as cnt from qna "
				+ " where subject like '%'||?||'%' or content like '%'||?||'%' ";
		con=Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  key);
			pstmt.setString(2,  key);
			rs = pstmt.executeQuery();
			if( rs.next() )
				count = rs.getInt("cnt");
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbman.close(con, pstmt, rs);  }
		
		return count;
	}

	public ArrayList<QnaVO> selectQna(Paging paging, String key) {
		ArrayList<QnaVO> list = new ArrayList<QnaVO>();
		String sql = " select * from ( "
				+ " select * from ( "
				+ " select rownum as rn, q.* from "
				+ " ((select * from qna where subject like '%'||?||'%' or content like '%'||?||'%' order by qseq desc) q) "
				+ " ) where rn>=? "
				+ " ) where rn<=? ";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  key);
			pstmt.setString(2,  key);
			pstmt.setInt(3,  paging.getStartNum()) ;
			pstmt.setInt(4,  paging.getEndNum() );
			rs = pstmt.executeQuery();
			while(rs.next()) {
				QnaVO qvo = new QnaVO();
				qvo.setQseq(rs.getInt("qseq"));
				qvo.setSubject(rs.getString("subject"));
				qvo.setContent(rs.getString("content"));
				qvo.setId(rs.getString("id"));
				qvo.setIndate(rs.getTimestamp("indate"));
				qvo.setReply(rs.getString("reply"));
				qvo.setRep(rs.getString("rep"));
				list.add(qvo);
			}
		} catch (SQLException e) { e.printStackTrace();
		} finally {  Dbman.close(con, pstmt, rs);   }
		return list;
	}

	public void updateQna(int qseq, String reply) {
		String sql = "update qna set reply=?, rep='2' where qseq=?";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reply );
			pstmt.setInt(2,  qseq );
			pstmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace(); 
		} finally { Dbman.close(con, pstmt, rs);  }
		
	}

	

	
	
	
}
