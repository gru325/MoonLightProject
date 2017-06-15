package org.moon.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.moon.domain.MemberVO;
import org.moon.domain.SearchCriteria;
import org.moon.dto.LoginDTO;
import org.moon.persistence.MemberDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	MemberDAO dao;

	@Transactional
	@Override
	public void register(MemberVO vo) throws Exception {
		
		dao.create(vo);
		
		//프로필 사진 업로드시 트랜잭션 처리
		String[] imgname = vo.getImgname();
		
		if(imgname == null){
			return;
			
		}else{
			for (String fullName : imgname) {
				dao.addAttach(fullName);
			}
		}
	}

	@Override
	public MemberVO findBymno(Integer mno) throws Exception {
		
		return dao.read(mno);
	}

	@Transactional
	@Override
	public void modify(MemberVO vo) throws Exception {
		
		dao.update(vo);
		
		Integer mno = vo.getMno();
		
		dao.deleteAttach(mno);
		
		String[] imgname = vo.getImgname();
		
		if(imgname == null){
			return;
			
		}else{
			String fullName = imgname[0];
				dao.replaceAttach(fullName, mno);
			}
	}

	@Override
	public void remove(Integer mno) throws Exception {
		
		dao.deleteAttach(mno);
		dao.delete(mno);
	}

	@Override
	public List<MemberVO> listAll() throws Exception {
		
		return dao.listAll();
	}

	@Override
	public List<MemberVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		
		return dao.listSearchCount(cri);
	}

	@Override
	public List<String> getAttach(Integer mno) throws Exception {
		
		return dao.getAttach(mno);
	}

	@Override
	public MemberVO login(LoginDTO dto) throws Exception {
		
		return dao.login(dto);
	}

	@Override
	public void keepLogin(String mid, String sessionId, Date next) throws Exception {
		
		dao.keepLogin(mid, sessionId, next);
		
	}

	@Override
	public MemberVO checkLoginBefore(String value) {
		
		return dao.checkMemberWithSessionKey(value);
	}
}
