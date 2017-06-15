package org.moon.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.moon.controller.MemberController;
import org.moon.domain.MemberCriteria;
import org.moon.domain.MemberVO;
import org.moon.domain.SearchCriteria;
import org.moon.dto.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	static final String namespace = "org.moon.mappers.memberMapper";
	
	@Inject
	SqlSession sess;

	@Override
	public void create(MemberVO vo) throws Exception {
		
		sess.selectOne(namespace + ".create", vo);

	}

	@Override
	public MemberVO read(Integer mno) throws Exception {
		
		return sess.selectOne(namespace + ".read", mno);
	}

	@Override
	public void update(MemberVO vo) throws Exception {
		
		sess.selectOne(namespace + ".update", vo);

	}

	@Override
	public void delete(Integer mno) throws Exception {
		
		sess.selectOne(namespace + ".delete", mno);

	}

	@Override //리스트 전부 뽑아내는 부분
	public List<MemberVO> listAll() throws Exception {
		
		return sess.selectList(namespace + ".listAll");
	}

	@Override //page만 사용해서 리스트 뽑아내는 부분
	public List<MemberVO> listPage(int page) throws Exception {
		
		if(page <= 0){
			page = 1;
		}
		
		return sess.selectList(namespace + ".listPage", page);
	}

	@Override //Criteria를 사용해서 리스트 뽑아내는 부분
	public List<MemberVO> listCriteria(MemberCriteria cri) throws Exception {
		
		return sess.selectList(namespace + ".listCriteria", cri);
	}

	@Override //Criteria를 사용해서 전체 게시물 숫자인 totalCount를 계산
	public int countPaging(MemberCriteria cri) throws Exception {
		
		return sess.selectOne(namespace + ".countPaging", cri);
	}

	@Override
	public List<MemberVO> listSearch(SearchCriteria cri) throws Exception {
		
		return sess.selectList(namespace + ".listSearch", cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		
		return sess.selectOne(namespace + ".listSearchCount", cri);
	}

	@Override //첨부파일 등록
	public void addAttach(String fullName) throws Exception {
		
		sess.insert(namespace + ".addAttach", fullName);
		
	}

	@Override //첨부파일 조회
	public List<String> getAttach(Integer mno) throws Exception {
		
		return sess.selectList(namespace + ".getAttach", mno);
	}

	@Override //첨부파일 삭제
	public void deleteAttach(Integer mno) throws Exception {
		
		sess.selectOne(namespace + ".deleteAttach", mno);
		
	}

	@Override //첨부파일 수정
	public void replaceAttach(String fullName, Integer mno) throws Exception {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("fullName", fullName);
		paraMap.put("mno",  mno);
		
		sess.selectOne(namespace + ".replaceAttach", paraMap);
		
	}

	@Override //로그인
	public MemberVO login(LoginDTO dto) throws Exception {
		
		return sess.selectOne(namespace + ".login", dto);
	}

	@Override
	public void keepLogin(String mid, String sessionId, Date next) {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("mid", mid);
		paraMap.put("sessionId", sessionId);
		paraMap.put("next", next);
		
		sess.update(namespace + ".keepLogin", paraMap);
	}

	@Override
	public MemberVO checkMemberWithSessionKey(String value) {
		
		return sess.selectOne(namespace + ".checkMemberWithSessionKey", value);
	}

}
