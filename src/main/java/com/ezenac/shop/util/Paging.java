package com.ezenac.shop.util;

public class Paging {

	private int page =1;  // 현재 보고자 하는 페이지번호
	private int totalCount;  // 게시물의 총갯수
	private int beginPage;   // 현재 화면에 표시되는 페이지중에 제일 처음 페이지   1 2 3 4 5 ▶  중  1에 해당하는 숫자
	private int endPage;   // 현재 화면에 표시되는 페이지중 제일 마지막에 나오는 페이지   1 2 3 4 5 ▶  중  5에 해당하는 숫자
	private int displayRow=5;   // 한 화면에 표시할 게시물 갯수
	private int displayPage=5;   // 한 화면에 표시할 페이지갯수     1 2 3 4 5 ▶  경우 페이지 갯수 5(다섯)개
	private boolean prev;    // 이전버튼 표시여부
	private boolean next;    // 다음 버튼 표시여부
	private int startNum;     // 현재 화면에 표시될 게시물들의 시작 번호(게시물 번호, num 이나 qseq는 아니고 검색된 게시물의 순서)
	private int endNum;      // 현재 화면에 표시될 게시물의 끝번호
	
	private void paging() {   // totalCount 를 제외한 모든 멤버변수에 해당하는 값들을 계산
		endPage = ( (int)Math.ceil( page/(double)displayPage ) ) * displayPage;
    	beginPage = endPage - (displayPage - 1);
    	int totalPage = (int)Math.ceil( totalCount/(double)displayRow );
    	if(totalPage<endPage){
			endPage = totalPage;
			next = false;
		}else{
			next = true;
		}
		prev = (beginPage==1)?false:true;
		startNum = (page-1)*displayRow+1;
		endNum = page*displayRow;
		System.out.println(beginPage + " " + endPage + " " + startNum + " " + endNum + " " + totalCount);
	} // setTotalCount 멤서드의 매개변수에 총게시물 갯수가 입력되면 paging() 메서드도 호출되어서 각 변수값이 계산됩니다.
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		paging(); // 멤버 메서드 호출
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getDisplayRow() {
		return displayRow;
	}

	public void setDisplayRow(int displayRow) {
		this.displayRow = displayRow;
	}

	public int getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getEndNum() {
		return endNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

	
}
