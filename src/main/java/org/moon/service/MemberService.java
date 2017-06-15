package org.moon.service;

import java.util.Date;
import java.util.List;

import org.moon.domain.MemberVO;
import org.moon.domain.SearchCriteria;
import org.moon.dto.LoginDTO;

public interface MemberService {
	
	public void register(MemberVO vo) throws Exception;
	
	public MemberVO findBymno(Integer mno) throws Exception;
	
	public void modify(MemberVO vo) throws Exception;
	
	public void remove(Integer mno) throws Exception;
	
	public List<MemberVO> listAll() throws Exception;
	
	//public List<MemberVO> listCriteria(MemberCriteria cri) throws Exception; //동적 SQL 동작 확인을 위해 아래와 같이 Criteria 변경
	public List<MemberVO> listSearchCriteria(SearchCriteria cri) throws Exception;
	
	//public int listCountCriteria(MemberCriteria cri) throws Exception; //동적 SQL 동작 확인을 위해 아래와 같이 Criteria 변경
	public int listSearchCount(SearchCriteria cri) throws Exception;
	
	public List<String> getAttach(Integer mno) throws Exception;
	
	public MemberVO login(LoginDTO dto) throws Exception;
	
	public void keepLogin(String mid, String sessionId, Date next)throws Exception;
	
	public MemberVO checkLoginBefore(String value);
}
