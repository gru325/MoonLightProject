package org.moon.controller;

import java.sql.Connection;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.moon.domain.MemberCriteria;
import org.moon.domain.MemberVO;
import org.moon.domain.SearchCriteria;
import org.moon.persistence.MemberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
@WebAppConfiguration
public class MoonTest {
	
	private static final Logger logger = LoggerFactory.getLogger(MoonTest.class);
	
	@Inject
	DataSource ds;
	
	@Test
	public void conTest(){
		try {
			Connection con = ds.getConnection();
			System.out.println(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Inject
	MemberDAO dao;
	
	@Test
	public void createTest() throws Exception{
		
		MemberVO vo = new MemberVO();
		
		vo.setMid("member99");
		vo.setMname("테스트멤버");
		vo.setMpw("4321");
		vo.setMmail("test@moon.com");
		vo.setMhp("01087654321");
		
		dao.create(vo);
	}
	
	@Test
	public void readTest() throws Exception{
		
		MemberVO vo = new MemberVO();
		
		vo.setMno(2);
		
		dao.read(2);
	}
	
	@Test
	public void updateTest() throws Exception{
		
		MemberVO vo = new MemberVO();
		
		vo.setMno(2);
		vo.setMid("updatemember99");
		vo.setMname("수정테스트멤버");
		vo.setMpw("update4321");
		vo.setMmail("updatetest@moon.com");
		vo.setMhp("01187654321");		
		
		dao.update(vo);
	}
	
	@Test
	public void deleteTest() throws Exception{
		
		dao.delete(2);
	}
	
	@Test
	public void listAllTest() throws Exception{
				
		dao.listAll();
	}
	
	@Test
	public void listPageTest() throws Exception{
		
		int page = 1;
		
		List<MemberVO> list = dao.listPage(page);
		
		for(MemberVO vo : list){
			logger.info(vo.getMno() + ":" + vo.getMid());
		}
	}
	
	@Test
	public void listCriteriaTest() throws Exception{
		
		MemberCriteria cri = new MemberCriteria();
		cri.setpage(1);
		cri.setperPageNum(20);
		
		List<MemberVO> list = dao.listCriteria(cri);
		
		for(MemberVO vo : list){
			logger.info(vo.getMno() + ":" + vo.getMid());			
		}
	}
	
	@Test
	public void testURI() throws Exception{ //UriComponents 클래스는 path나 query에 해당하는 문자열들을 추가해서 원하는 URI를 생성할 때 사용한다.
		
		//Builder패턴
		UriComponents uricomponents = 
				UriComponentsBuilder.newInstance()
				.path("/member/mview")
				.queryParam("mno", 12)
				.queryParam("perPageNum", 20)
				.build();
		
		//아래 2개는 테스트 결과가 동일하게 찍혀야 한다.
		logger.info("/member/mview?mno=12&perPageNum=20");
		logger.info(uricomponents.toString());
	}
	
	@Test
	public void testURI2() throws Exception{ //UricomponentsBuilder는 특정 URI를 먼저 지정하고 작업하는것도 가능하다.
		
		UriComponents uriComponents = 
				UriComponentsBuilder.newInstance()
				.path("/{module}/{page}") //여기서 필요한 경로(path)를 미리 지정해놓고,
				.queryParam("mno", 12)
				.queryParam("perPageNum", 20)
				.build()
				.expand("member","mview") //여기서 필요한 경로대로 변경시켜 사용(expand)할 수 있다.
				.encode();
		
		//아래 2개도 역시 테스트 결과가 동일하게 찍혀야 한다.
		logger.info("/member/mview?mno=12&perPageNum=20");
		logger.info(uriComponents.toString());
	}
	
	@Test
	public void BeforeDynamicSQLTest() throws Exception{
		
		SearchCriteria cri = new SearchCriteria();
		cri.setpage(1);
		cri.setKeyword("");
		cri.setSearchType("");
		
		logger.info("=======================");
		
		List<MemberVO> list = dao.listSearch(cri);
		
		for(MemberVO vo : list){
			logger.info(vo.getMno() + ":" + vo.getMid());
		}
		
		logger.info("=======================");
		
		logger.info("COUNT: " + dao.listSearchCount(cri));
	}

}
