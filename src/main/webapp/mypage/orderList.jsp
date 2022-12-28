<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%@ include file="sub_image_menu.jsp" %>

<!-- 방금 주문한 주문 목록만 보여주고, 같은 화면이 다시 나타나지 않습니다. 
	이후에는 총 주문내역 또는 진행중인 주문내역 에서 다른 주문과 함께 볼 수 있고, 세부내용은 상세내역에서 볼 수 있습니다 -->

<article>
	<form>
		<h2> Order List </h2> 
		<table id="cartList">  <!-- 동일한 css 적용을 위한 id사용 -->
			<tr><th>상품명</th><th>수 량</th><th>가 격</th><th>주문일</th><th>진행상태</th></tr>
			<c:forEach items="${orderList}" var="orderVO">
				<tr><td>
					<a href="shop.do?command=productDetail&pseq=${orderVO.pseq}">
					<h3>${orderVO.pname}</h3></td>
					<td> ${orderVO.quantity}</td>
		       		<td><fmt:formatNumber value="${orderVO.price2*orderVO.quantity}" type="currency"/></td>      
		       		<td><fmt:formatDate value="${orderVO.indate}" type="date"/></td>
		      		<td> 처리 진행 중 </td></tr>
			</c:forEach>
			<tr><th colspan="2"> 총 액 </th>
		       	<th colspan="2"><fmt:formatNumber value="${totalPrice}" type="currency"/></th>
		       	<th>주문 처리가 완료되었습니다. </th></tr> 	
		</table>
	</form>	
</article>


<%@ include file="../footer.jsp" %>