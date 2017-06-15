package org.moon.domain;

public class MemberCriteria {
	
	private int page;
	private int perPageNum;
	
	public MemberCriteria(){
		this.page = 1;
		this.perPageNum = 10;
	}

	public int getpage() {
		return page;
	}

	public void setpage(int page) {
		
		//악의적인 page 수정 방지
		if(page <= 0){
			this.page= 1;
			return;
		}
		
		this.page= page;
	}

	public int getperPageNum() {
		return perPageNum;
	}

	public void setperPageNum(int perPageNum) {
		
		//악의적인 perPageNum 수정 방지
		if(perPageNum <=0 || perPageNum > 50){
			this.perPageNum = 10;
			return;
		}
		
		this.perPageNum = perPageNum;
	}	
	
	@Override
	public String toString() {
		return "MemberCriteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}

	//MyBatis SQL Mapper에 사용할 메소드
	public int getPageStart(){
		
		return (this.page - 1) * perPageNum;  
	}
	
	

}
