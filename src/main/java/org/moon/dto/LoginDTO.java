package org.moon.dto;

public class LoginDTO {
	
	private String mid;
	private String mpw;
	private boolean useCookie;
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMpw() {
		return mpw;
	}
	public void setMpw(String mpw) {
		this.mpw = mpw;
	}
	public boolean isUseCookie() {
		return useCookie;
	}
	public void setUseCookie(boolean useCookie) {
		this.useCookie = useCookie;
	}
	
	@Override
	public String toString() {
		return "LoginDTO [mid=" + mid + ", mpw=" + mpw + ", useCookie=" + useCookie + "]";
	}
}
