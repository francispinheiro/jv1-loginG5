package br.com.global5.loginG5.bean.geral;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.SimpleFormatter;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.global5.loginG5.administrativo.model.Empresa;
import br.com.global5.loginG5.administrativo.service.EmpresaService;
import br.com.global5.loginG5.infra.util.checkUsuario;
import br.com.global5.loginG5.model.geral.Usuario;
import br.com.global5.loginG5.model.geral.UsuarioEmpresa;
import br.com.global5.loginG5.service.geral.UsuarioEmpresaService;
import br.com.global5.loginG5.service.geral.UsuarioService;
import br.com.global5.template.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * Class used for find list the access by user for enterprises and generated token
 * @author Francis Bueno
 *
 */

@Named
@ViewAccessScoped
public class UsuarioEmpresaMB implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private UsuarioEmpresa usuEmpresa;
	private Integer id;
	
	private Usuario usuario;
	
	private List<UsuarioEmpresa> listUsuarioEmpresa;
	private List<Empresa> lstEmpresaAdm;
	
	private String alfToken = "";
	private String alfTokenC= "";
	private Date   dtGeracaoToken = null;
	private String ulr = "";
	
	
	@Inject
	UsuarioEmpresaService usuServEmpresa;
	
	@Inject
	private EmpresaService empService;
	
	@Inject
	private UsuarioService usuService;
	
	
	@PostConstruct
	public void init(){
		clear();
	}
		
	private void clear() {
		usuEmpresa = new UsuarioEmpresa();
		usuEmpresa.setEmpresa(new Empresa());
		usuEmpresa.setUsuario(new Usuario());
		usuEmpresa.setUsuCriacao(new Usuario());
		usuEmpresa.setUsuExclusao(new Usuario());
		
		id = null;
		
		usuario = checkUsuario.valid();
		
		//filtrarEmpresasDeAcesso(usuario);
		carregarEmpresas();
		
	}
	
	public void carregarEmpresas(){
		Criteria critEmp = empService.crud().getSession().createCriteria(Empresa.class);
		this.setLstEmpresaAdm(critEmp.list());
					
	}
	
	/**
	 * List the enterprises were user can do login. 
	 * created at: 2018/09/11
	 * 
	 * @author Francis Bueno
	 * @param usuLogado	
	 * 			object usuario that this connected in system
	 * 
	 * 
	 */
	public void filtrarEmpresasDeAcesso(Usuario usuLogado ){
		
		if(usuLogado != null){
			
			Criteria criteria = usuServEmpresa.crud().getSession().createCriteria(UsuarioEmpresa.class);
			criteria.add(Restrictions.eq("usuario" , usuLogado));
			int result = criteria.list().size();
			
			if(result > 0){
				
				Criteria critEmp = empService.crud().getSession().createCriteria(Empresa.class);
				this.setLstEmpresaAdm(critEmp.list());
								
				this.setListUsuarioEmpresa(criteria.list());
				
				for(int i=0; i < result ; i++){
					
					//Chama o método geradorToken
					String aToken = this.gerarTokenDeAcesso(usuLogado , listUsuarioEmpresa.get(i).getEmpresa().getId());
					
					UsuarioEmpresa usuEmp = usuServEmpresa.findById(listUsuarioEmpresa.get(i).getId());
					
					usuEmp.setToken(aToken);
					usuEmp.setDataToken(this.getDtGeracaoToken());					
					usuServEmpresa.update(usuEmp);					
					
				}
				
			} else {
				this.setListUsuarioEmpresa(null);
			}
		}
		
	}
	
	
	public void filtrarEmpresasDeAcessoCodEmpresa(Usuario usuLogado, Integer empresa  ){
		
		if(usuLogado != null){
						
			Criteria critEmp = empService.crud().getSession().createCriteria(Empresa.class);
			
			int result = critEmp.list().size();			
			
			if(result > 0){

				this.setLstEmpresaAdm(critEmp.list());
				
				String aToken = this.gerarTokenDeAcesso(usuLogado, empresa);
				
				if (!aToken.equals("")){
					Usuario usuario = usuService.findById(usuLogado.getId());
						
						usuario.setToken(aToken);
						usuario.setDtToken(this.getDtGeracaoToken());
					
					usuService.update(usuario);
					
					urlAdmin(empresa, aToken);
					
				}
										
			} else {
				this.setLstEmpresaAdm(null);
				
			}
		}
		
	}
	
	/**
	 * Generator of Token for access at other Data Source Administrative by kind enterprise. 
	 * created at: 2018/09/11
	 * 
	 * @author Francis Bueno
	 * @param usuLogado
	 * 			object usuario that this connected in system
	 * @return  token for access between projects loginG5 and Ds Admin Enterprise 1 or 2
	 * 
	 */
	
	public String gerarTokenDeAcesso(Usuario usuLogado, Integer codEmpresa){
		if(usuLogado != null){		
		//Defini qual vai ser o algoritmo da assinatura no caso vai ser o HMAC SHA512
		SignatureAlgorithm algoritimoAssinatura = SignatureAlgorithm.HS256;
		
		//Data atual que data que o token foi gerado
		Date agora = new Date();

		//Hora atual que o token foi gerado
		GregorianCalendar gcH = new GregorianCalendar();
		gcH.setTime(new Date());
		this.setDtGeracaoToken(gcH.getTime());
		
		//Define o tempo de validade do token , minutos
		gcH.add(Calendar.MINUTE, 5);
				
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		System.out.println("Hora válida até: " + sdf.format(gcH.getTime()));
						
		//Encoda a frase segredo pra base64 pra ser usada na geração do token 
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(usuLogado.getEmail()+codEmpresa);
		SecretKeySpec key = new SecretKeySpec(apiKeySecretBytes, usuLogado.getLogin());
		
		//JWT para geração do Token
//		JwtBuilder construtor = Jwts.builder()				
//				.setIssuedAt(agora) 					//Data que o token foi gerado
//				.setIssuer(usuLogado.getLogin()) 		//login do usuario, mas poderia ser outra informação
//				.setSubject(codEmpresa.toString())
//				.signWith(algoritimoAssinatura,	key) 	//coloca o algoritimo de assinatura e frase segredo já encodada
//				.setExpiration(gcH.getTime());			//coloca a hora que o token é válido

		JwtBuilder construtor = Jwts.builder()				
				.setIssuedAt(agora) 					//Data que o token foi gerado
				.setIssuer(usuLogado.getLogin()) 		//login do usuario, mas poderia ser outra informação
				.setSubject(codEmpresa.toString())
				.setAudience(usuLogado.getSenha())
				.signWith(algoritimoAssinatura,	key) 	//coloca o algoritimo de assinatura e frase segredo já encodada
				.setExpiration(gcH.getTime());			//coloca a hora que o token é válido		
			
			this.setAlfTokenC(construtor.compact());
		
			return construtor.compact();
		
		} else {
			return null;
		}
		
	}
	
	/**
	 * Metod for validation token access. 
	 * created at: 2018/09/18
	 * 
	 * @author Francis Bueno
	 * @param token
	 * @return json access with information the user conected
	 * @throws Exception 
	 * 
	 */
	
	public Claims validaToken(Integer codEmpresa, String token){
		try{
			//JJWT vai validar o token, caso o token não seja valido ele vai executar uma exception
			//o JJWT usa o email do usuario para descodificar o token e ficando assim possivel
			//recuperar as informações no payload
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(usuario.getEmail()+codEmpresa))
					.parseClaimsJws(token).getBody();
			
			//Hora atual que o token foi gerado
			GregorianCalendar tmRec = new GregorianCalendar();
			tmRec.setTime(new Date());
			
			System.out.println(claims.getIssuer());			
			
			return claims;
		} catch (Exception ex) {
				throw ex;		
		}
	
	}
	
	/**
	 * 
	 * 
	 * @author Francis Bueno
	 * @param idEmpresa
	 * @param alfToken
	 */
	
	public void urlAdmin(Integer idEmpresa,String alfToken){
		//Integer idUsuario, Integer idEmpresa, String alfToken
		this.invalidateSession();
		
		this.validaToken(idEmpresa, alfToken);
		
		if(idEmpresa == 1){
		
			try {
		        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
		        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();		    
		        
				ExternalContext externalContext1 = FacesContext.getCurrentInstance().getExternalContext();
//				externalContext1.redirect("http://10.1.100.123:8081/empresa01/logadmin.xhtml?Tkuser=" + alfToken);
				externalContext1.redirect("http://localhost:8081/empresa01/logadmin.xhtml?Tkuser=" + alfToken);
//				externalContext1.redirect("http://192.168.64.103:8081/empresa01/logadmin.xhtml?Tkuser=" + alfToken);				
//				externalContext1.redirect("http://gcadastro.global5.com.br:8081/empresa01/logadmin.xhtml?Tkuser=" + alfToken);
//				externalContext1.redirect("http://devlinux:8081/empresa01/logadmin.xhtml?Tkuser=" + alfToken);
			} catch (IOException e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} else {
			
			try {
		        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
		        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();	
		        
				ExternalContext externalContext2 = FacesContext.getCurrentInstance().getExternalContext();
//				externalContext2.redirect("http://10.1.100.123:8082/empresa02/logadmin.xhtml?Tkuser=" + alfToken);				
				externalContext2.redirect("http://localhost:8082/empresa02/logadmin.xhtml?Tkuser=" + alfToken);
//				externalContext2.redirect("http://192.168.64.103:8082/empresa02/logadmin.xhtml?Tkuser=" + alfToken);
//				externalContext2.redirect("http://gcadastro.global5.com.br:8082/empresa02/logadmin.xhtml?Tkuser=" + alfToken);
//				externalContext2.redirect("http://devlinux:8082/empresa02/logadmin.xhtml?Tkuser=" + alfToken);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
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


	public UsuarioEmpresa getUsuEmpresa() {
		return usuEmpresa;
	}

	public void setUsuEmpresa(UsuarioEmpresa usuEmpresa) {
		this.usuEmpresa = usuEmpresa;
	}


	public List<UsuarioEmpresa> getListUsuarioEmpresa() {
		return listUsuarioEmpresa;
	}


	public void setListUsuarioEmpresa(List<UsuarioEmpresa> listUsuarioEmpresa) {
		this.listUsuarioEmpresa = listUsuarioEmpresa;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public UsuarioEmpresaService getUsuServEmpresa() {
		return usuServEmpresa;
	}


	public void setUsuServEmpresa(UsuarioEmpresaService usuServEmpresa) {
		this.usuServEmpresa = usuServEmpresa;
	}


	public String getAlfToken() {
		return alfToken;
	}


	public void setAlfToken(String alfToken) {
		this.alfToken = alfToken;
	}

	public Date getDtGeracaoToken() {
		return dtGeracaoToken;
	}

	public void setDtGeracaoToken(Date dtGeracaoToken) {
		this.dtGeracaoToken = dtGeracaoToken;
	}

	public String getAlfTokenC() {
		return alfTokenC;
	}

	public void setAlfTokenC(String alfTokenC) {
		this.alfTokenC = alfTokenC;
	}

	public String getUlr() {
		return ulr;
	}

	public void setUlr(String ulr) {
		this.ulr = ulr;
	}

	public List<Empresa> getLstEmpresaAdm() {
		return lstEmpresaAdm;
	}

	public void setLstEmpresaAdm(List<Empresa> lstEmpresaAdm) {
		this.lstEmpresaAdm = lstEmpresaAdm;
	}
	
	
	
	

}
