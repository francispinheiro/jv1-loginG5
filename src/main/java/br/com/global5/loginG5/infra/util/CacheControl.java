package br.com.global5.loginG5.infra.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CacheControl implements PhaseListener {
	
	private static final long serialVersionUID = 201809171041L;
	
	public PhaseId getPhaseId(){
		return PhaseId.RENDER_RESPONSE;
	}
	
	public void afterPhase(PhaseEvent event){}
	
	public void beforePhase(PhaseEvent event){
		FacesContext facescontext = event.getFacesContext();
		HttpServletResponse	response = (HttpServletResponse) facescontext.getExternalContext().getResponse();
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "must-revalidate");
		response.addHeader("Expires", "Mon, 8 Aug 2018 10:00:00 GMT");
		
        Cookie userNameCookieRemove = new Cookie("userName","");
        userNameCookieRemove.setMaxAge(0);
        response.addCookie(userNameCookieRemove);
	}
	
	
}
