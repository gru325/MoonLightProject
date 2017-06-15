package org.moon.persistence;

import java.util.Date;
import java.util.List;

import org.moon.domain.MemberCriteria;
import org.moon.domain.MemberVO;
import org.moon.domain.SearchCriteria;
import org.moon.dto.LoginDTO;

public interface MemberDAO {
	
	public void create(MemberVO vo) throws Exception;
	
	public MemberVO read(Integer mno) throws Exception;
	
	public void update(MemberVO vo) throws Exception;
	
	public void delete(Integer mno) throws Exception;
	
	public List<MemberVO> listAll() throws Exception;
	
	public List<MemberVO> listPage(int page) throws Exception;
	
	public List<MemberVO> listCriteria(MemberCriteria cri) throws Exception;
	
	public int countPaging(MemberCriteria cri) throws Exception; // totalCount 반환 처리
	
	public List<MemberVO> listSearch(SearchCriteria cri) throws Exception; //검색 결과를 리스트 처리하기 위함
	
	public int listSearchCount(SearchCriteria cri) throws Exception; //검색 결과를 페이징 처리하기 위함
	
	public void addAttach(String fullName) throws Exception;
	
	public List<String> getAttach(Integer mno) throws Exception;
	
	public void deleteAttach(Integer mno) throws Exception;
	
	public void replaceAttach(String fullName, Integer mno) throws Exception;
	
	public MemberVO login(LoginDTO dto) throws Exception;
	
	public void keepLogin(String mid,String sessionId, Date next);
	
	public MemberVO checkMemberWithSessionKey(String value);

}
