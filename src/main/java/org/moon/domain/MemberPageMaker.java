package org.moon.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class MemberPageMaker {
	
	//DB에서 계산되는 데이터
	private int totalCount; //SQL결과로 나온 데이터의 전체 개수
	//계산을 통해 만들어지는 데이터
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	
	private int displayPageNum = 10; // 한 줄에 보여지는 페이지 번호의 개수
	
	private MemberCriteria cri;

	
	public void setCri(MemberCriteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) { // totalCount가 설정되는 시점에 calcDate()를 실행한다!
		this.totalCount = totalCount;
		
		calcData();
	}

	private void calcData() {
		
		endPage = (int) (Math.ceil (cri.getpage() / (double) displayPageNum) * displayPageNum); // (Math.ceil(현재 페이지의 번호 / 페이지 번호의 개수) * 페이지 번호의 개수)
		
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int) (Math.ceil (totalCount / (double) cri.getperPageNum()));
		
		if(endPage > tempEndPage){
			endPage = tempEndPage;
		}
		
		prev = startPage == 1 ? false : true;
		
		next = endPage * cri.getperPageNum() >= totalCount ? false : true;	
		
	}
	
	//mlist의 페이지의 경우 모든 정보는 MemberCriteria에 있기 때문에 makeQuery()메소드를 추가시켜 준다.
	public String makeQuery(int page){
		
		UriComponents uriComponents = 
				UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getperPageNum())
				.build();
		
		return uriComponents.toString();
		
	}
	
	public String makeSearch(int page){
		
		UriComponents uriComponents = 
				UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getperPageNum())
				.queryParam("searchType", ((SearchCriteria)cri).getSearchType())
				.queryParam("keyword", encoding(((SearchCriteria)cri).getKeyword()))
				.build();
		
		return uriComponents.toString();
		
	}
	
	public String encoding(String keyword){
		
		if(keyword == null || keyword.trim().length() == 0){
			
			return "";
		}
		try {
			
			return URLEncoder.encode(keyword, "UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			
			return "";
		}
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
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

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public MemberCriteria getCri() {
		return cri;
	}

	@Override
	public String toString() {
		return "MemberPageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", prev=" + prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + "]";
	}
	
}
