package br.com.global5.loginG5.bean.geral;

import br.com.global5.loginG5.model.geral.Usuario;
import br.com.global5.loginG5.service.geral.UsuarioService;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Properties;

/**
 * Created by j r zielinski on 04/02/17.
 */
@Named
@SessionScoped
public class LogonMB implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8356159542001076294L;

	private String login;

    private String password;

    private String newpasswd;

    private String chkpasswd;

    private boolean remember;

    private boolean loggedIn;

    private Usuario usuarioLogado;

    private Properties prop = new Properties();
    private InputStream input = null;

    private String ambiente;

    @Inject
    UsuarioService uService;

    @PostConstruct
    public void init() {    	
    	
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       
        
        

//        // load a properties file
//        try {
//            ClassLoader classLoader = getClass().getClassLoader();
//            input = new FileInputStream(classLoader.getResource("admin-config.properties").getFile());
//            prop.load(input);
//            // get the property value and print it out
//            ambiente = prop.getProperty("geral.ambiente").toUpperCase();
//            System.out.println(ambiente);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }    

    private void clear() {
    	this.invalidateSession();
		
	}

	public void trocarSenha() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("cpasswd.xhtml");
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "A troca de senhas n??o pode ser carregada. " +
                            "Informe ao suporte t??cnico.", null));
        }

    }

    public String cliente() {

        try {
            if (usuarioLogado.getPessoa().getFuncao().getArea().getRoot() == null) {
                return usuarioLogado.getNome() + "(" + usuarioLogado.getPessoa().getFuncao().getArea().getNome() + ")";
            } else {
                return usuarioLogado.getNome() + "(" + usuarioLogado.getPessoa().getFuncao().getArea().getRoot().getNome() + ")";
            }

        } catch (Exception e) {}

        return "";
    }

    public void invalidateSession() {

        // Request object to fetch the cookies
    	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //HttpServletRequest request = this.getThreadLocalRequest();

        // Response object to delete the cookies
    	HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //HttpServletResponse response = this.getThreadLocalResponse();
        response.setContentType("text/xhtml");

        Cookie[] cookies = request.getCookies();

        // Delete all the cookies
        if (cookies != null) {

            for (int i = 0; i < cookies.length; i++) {

                Cookie cookie = cookies[i];
                cookies[i].setValue(null);
                cookies[i].setMaxAge(-1);                
                response.addCookie(cookie);
            }
        }
    }
    
    public String doLogon() {
    	
        //Verifica se o e-mail e senha existem e se o usuario pode logar
        Usuario usuarioFound = uService.isUsuarioReadyToLogin(login, password);

        //Caso n??o tenha retornado nenhum usuario, ent??o mostramos um erro
        //e redirecionamos ele para a p??gina login.xhtml
        //para ele realiza-lo novamente
        if (usuarioFound == null){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "As informa????es de login, n??o s??o v??lidas, tente novamente!"));
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "login.xhtml?faces-redirect=true";
        } else {
            //caso tenha retornado um usuario, setamos a vari??vel loggedIn
            //como true e guardamos o usuario encontrado na vari??vel
            //usuarioLogado. Depois de tudo, mandamos o usu??rio
            //para a p??gina index.xhtml
            loggedIn = true;
            usuarioLogado = usuarioFound;
           //
            
//            Cookie userNameCookieRemove = new Cookie("userName","");
//            userNameCookieRemove.setMaxAge(0);
            
            FacesContext facesContext = FacesContext.getCurrentInstance();

            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("logonMB", this);
            session.setAttribute("ambiente", ambiente);
            return "index.xhtml?faces-redirect=true";
        }
    }

    public String doChangePasswd() {

        if( ! newpasswd.equals(chkpasswd) ) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "A nova senha e sua valida????o s??o diferentes!!!!"));
            return "cpasswd.xhtml?faces-redirect=true";

        }
        //Verifica se o e-mail e senha existem e se o usuario pode logar
        Usuario usuarioFound = uService.isUsuarioReadyToLogin(login, password, newpasswd);

        //Caso n??o tenha retornado nenhum usuario, ent??o mostramos um erro
        //e redirecionamos ele para a p??gina login.xhtml
        //para ele realiza-lo novamente
        if (usuarioFound == null){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "N??o foi poss??vel, realizar a troca de senha!"));
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "login.xhtml?faces-redirect=true";
        }

        return "index.xhtml?faces-redirect=true";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public UsuarioService getuService() {
        return uService;
    }

    public void setuService(UsuarioService uService) {
        this.uService = uService;
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public InputStream getInput() {
        return input;
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getNewpasswd() {
        return newpasswd;
    }

    public void setNewpasswd(String newpasswd) {
        this.newpasswd = newpasswd;
    }

    public String getChkpasswd() {
        return chkpasswd;
    }

    public void setChkpasswd(String chkpasswd) {
        this.chkpasswd = chkpasswd;
    }
}
