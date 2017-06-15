package org.moon.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.moon.domain.MemberPageMaker;
import org.moon.domain.MemberVO;
import org.moon.domain.SearchCriteria;
import org.moon.dto.LoginDTO;
import org.moon.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService service;
	
	//등록
	@GetMapping("/mregister")
	public void mregisterGet(MemberVO vo, Model model){
		
		model.addAttribute(vo);
		
	}
	
	@PostMapping("/mregister")
	public String mregisterPost(MemberVO vo, RedirectAttributes rttr, Model model) throws Exception{

		service.register(vo); //vo객체를 등록처리 한다.
		
		rttr.addFlashAttribute("msg", "success"); //alert창에서 등록성공 여부를 띄워주기 위해, 1번만 넘겨주는 flashAttribute를 사용한다.
		
		logger.info("" + vo);
		
		return "redirect:/member/mlistPage"; //등록 후에는 redirect를 통해 list로 돌아온다.

	}
	
	//상세보기
	@GetMapping("/mview")
	public void mview(@RequestParam("mno") Integer mno, Model model) throws Exception{
		
		MemberVO vo = new MemberVO(); //vo객체 생성
		
		vo = service.findBymno(mno); //생성된 vo객체는 mno에 따라 그 내용이 결정된다.
		
		model.addAttribute("mviewVO",vo); //model을 통해, 위의 vo를 mviewVO라는 이름으로 jsp에 넘겨준다.
	}
	
	@GetMapping("/mviewPage")
	public void mviewPage(@RequestParam("mno") Integer mno, Model model, @ModelAttribute("cri") SearchCriteria cri) throws Exception{
		
		model.addAttribute("mviewVO", service.findBymno(mno)); //page와 perPageNum은 Criteria 타입 객체로 처리하고, mno 파라미터만 별도로 처리 해준다.
		
		
		
	}
	
	//수정
	@GetMapping("/mmodify")
	public void mmodifyGet(MemberVO vo, Model model, @RequestParam("mno") Integer mno) throws Exception{
		
		vo = service.findBymno(mno);
		
		model.addAttribute("mmodifyVO", vo);
		
	}
	
	@GetMapping("/mmodifyPage")
	public void mmodifyPageGet(@RequestParam("mno") Integer mno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		
		model.addAttribute("mmodifyVO", service.findBymno(mno));
	}
	
	@PostMapping("/mmodify")
	public String mmodifyPost(MemberVO vo, RedirectAttributes rttr) throws Exception{
		
		service.modify(vo);
		
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/member/mlistPage";
	}
	
	@PostMapping("/mmodifyPage")
	public String mmodifyPage(MemberVO vo, SearchCriteria cri, RedirectAttributes rttr) throws Exception{
		
		service.modify(vo);
		
		rttr.addAttribute("page", cri.getpage());
		rttr.addAttribute("perPageNum", cri.getperPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("msg", "success");
		
		logger.info(""+vo);
		
		return "redirect:/member/mlistPage";
		
	}
	
	//삭제
	@PostMapping("/mremove")
	public String mremove(Integer mno, RedirectAttributes rttr) throws Exception{
		
		service.remove(mno);
		
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/member/mlistPage";
		
	}
	
	@PostMapping("/mremovePage")
	public String mremovePage(@RequestParam("mno") Integer mno, RedirectAttributes rttr, SearchCriteria cri) throws Exception{
		
		service.remove(mno);
		
		rttr.addAttribute("page", cri.getpage());
		rttr.addAttribute("perPageNum", cri.getperPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("msg", "success");
		return "redirect:/member/mlistPage";
	}
	
	//리스트
	@GetMapping("/mlist")
	public void mlist(SearchCriteria cri, Model model) throws Exception{
		
		//model.addAttribute("list", service.listCriteria(cri));
		model.addAttribute("list", service.listSearchCriteria(cri));
		
	}
	
	@GetMapping("/mlistPage")
	public void mlistPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		
		//model.addAttribute("list", service.listCriteria(cri));
		model.addAttribute("list", service.listSearchCriteria(cri));
		
		MemberPageMaker pageMaker = new MemberPageMaker();
		pageMaker.setCri(cri);
		//pageMaker.setTotalCount(256); //Criteria를 사용해서 전체 게시물 숫자인 totalCount를 계산하는 다음부터는 아래와 같이 변경된다.
		//pageMaker.setTotalCount(service.listCountCriteria(cri));
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@ResponseBody
	@RequestMapping("/getAttach/{mno}")
	public List<String> getAttach(@PathVariable("mno") Integer mno) throws Exception{
		
		return service.getAttach(mno);
	}
	
	@GetMapping("/login")
	public void loginGet(@ModelAttribute("dto") LoginDTO dto){
		
	}
	
	@PostMapping("/loginPost")
	public void loginPost(LoginDTO dto, HttpSession session, Model model) throws Exception{
		
		MemberVO vo = service.login(dto);
		
		if(vo == null){
			
			return;
			
		}else{
			
			model.addAttribute("memberVO", vo);
		}
		
		if(dto.isUseCookie()){
			
			int amount = 60*60*24*7;
			
			Date sessionLimit = new Date(System.currentTimeMillis() + (1000 * amount));
			
			service.keepLogin(vo.getMid(), session.getId(), sessionLimit);
		}		
	} 
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		
		Object obj = session.getAttribute("login");
		
		if(obj != null){
			
			MemberVO vo = (MemberVO) obj;
			
			session.removeAttribute("login");
			session.invalidate();
			
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			
			if(loginCookie != null){
				
				loginCookie.setPath("/");
				loginCookie.setMaxAge(0);
				response.addCookie(loginCookie);
				
				service.keepLogin(vo.getMid(),session.getId(), new Date());
			}
		}		
		return "member/logout";		
	}
	
	@GetMapping("/test")
	public void test(){
		
	}
	
	@GetMapping("/test2")
	public void test2(){
		
	}
}
