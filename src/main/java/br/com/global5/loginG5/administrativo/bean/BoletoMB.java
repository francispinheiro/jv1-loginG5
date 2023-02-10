package br.com.global5.loginG5.administrativo.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.apache.poi.ss.formula.eval.RelationalOperationEval;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

import br.com.global5.loginG5.administrativo.model.ContasReceber;
import br.com.global5.loginG5.administrativo.model.NotaFiscal;
import br.com.global5.loginG5.administrativo.service.ContasReceberService;
import br.com.global5.loginG5.administrativo.service.NotaFiscalService;
import br.com.global5.loginG5.infra.util.RelatorioUtil;
import br.com.global5.loginG5.infra.util.UtilException;
import br.com.global5.loginG5.infra.util.checkUsuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Named
@ViewScoped
public class BoletoMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ContasReceber cre;	
	
	private Integer id;
	
	private String 	cnpjCliente = "";
	private Integer numNotaFiscal = null;
	private String	codVerificador = "";
	private String  urlAtual = "";
	private String	nomeCliente = "";
	
	// Relatórios
	private StreamedContent rltContasInternas;
	
	@Inject
	ContasReceberService creService;
	
	@Inject
	NotaFiscalService nfseService;	
	
	@PostConstruct
	public void init(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();    
		String id = request.getParameter("Doc");  
		String idCnpj = request.getParameter("CNPJ"); 
		String idCodV = request.getParameter("Cod");
		String idNomeCliente = request.getParameter("Cli");
			
		this.setCnpjCliente(idCnpj);
		this.setNumNotaFiscal(Integer.parseInt(id));
		this.setCodVerificador(idCodV);
		this.setNomeCliente(idNomeCliente);
		
		clear();
	}
	
	private void clear() {
		
		cre = new ContasReceber();
		id = null;
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnpjCliente() {
		return cnpjCliente;
	}

	public void setCnpjCliente(String cnpjCliente) {
		this.cnpjCliente = cnpjCliente;
	}

	public Integer getNumNotaFiscal() {
		return numNotaFiscal;
	}

	public void setNumNotaFiscal(Integer numNotaFiscal) {
		this.numNotaFiscal = numNotaFiscal;
	}

	public String getCodVerificador() {
		return codVerificador;
	}

	public void setCodVerificador(String codVerificador) {
		this.codVerificador = codVerificador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ContasReceber getCre() {
		return cre;
	}

	public void setCre(ContasReceber cre) {
		this.cre = cre;
	}

	public ContasReceberService getCreService() {
		return creService;
	}

	public void setCreService(ContasReceberService creService) {
		this.creService = creService;
	}

	public NotaFiscalService getNfseService() {
		return nfseService;
	}

	public void setNfseService(NotaFiscalService nfseService) {
		this.nfseService = nfseService;
	}
	
	public void urlPmcNfse(){
		
		NotaFiscal nfseId = new NotaFiscal();
		
		Criteria criteria = nfseService.crud().getSession().createCriteria(NotaFiscal.class);
		criteria.setProjection(Projections.sum("vlrServico")).uniqueResult();
		criteria.add(Restrictions.eq("numNf", this.getNumNotaFiscal()));
		
		BigDecimal result = (BigDecimal) criteria.uniqueResult();
		
	Integer qryNfse = null;
		
		if( result != null){
			
			qryNfse = nfseService.crud().eq("numNf", this.getNumNotaFiscal()).find().getId();
			nfseId = nfseService.findById(qryNfse);			
			
			if(nfseId.getNumNf().intValue()==this.getNumNotaFiscal().intValue() && nfseId.getChaveNfse().equals(this.getCodVerificador())){
					
					try {
						ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
						String ulrNfse = nfseId.getLinkNfse();
						externalContext.redirect("https://isscuritiba.curitiba.pr.gov.br/portalnfse/Default.aspx?doc="+this.getCnpjCliente()+"&num="+String.valueOf(this.getNumNotaFiscal())+"&cod="+this.getCodVerificador());
						 
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
				} 							
		}else {
			
			ExternalContext externalContextErro = FacesContext.getCurrentInstance().getExternalContext();
			
			try {
				externalContextErro.redirect("https://isscuritiba.curitiba.pr.gov.br/portalnfse/Default.aspx?doc="+this.getCnpjCliente()+"&num="+String.valueOf(this.getNumNotaFiscal())+"&cod="+this.getCodVerificador());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
		}
		 
	}
	
	
	public StreamedContent getRltContasInternas() {
		
		FacesContext context = FacesContext.getCurrentInstance();

		String nomeRelatorioJasper = "Boleto";
		String docBoleto = String.valueOf(this.getNumNotaFiscal());
		String nomeRelatorioSaida  = "Boleto_Global_NF_" + docBoleto ;		
		
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		
		HashMap parametrosRelatorio = new HashMap<>();
		parametrosRelatorio.put("doc", String.valueOf(this.getNumNotaFiscal()));
		
		NotaFiscal nfseId = new NotaFiscal();
		
		Criteria criteria = nfseService.crud().getSession().createCriteria(NotaFiscal.class);
		criteria.setProjection(Projections.sum("vlrServico")).uniqueResult();
		criteria.add(Restrictions.eq("numNf", this.getNumNotaFiscal()));
		BigDecimal result = (BigDecimal) criteria.uniqueResult();
		
		Integer qryNfse = null;
		
		if( result != null){
			qryNfse = nfseService.crud().eq("numNf", this.getNumNotaFiscal()).find().getId();
			nfseId = nfseService.findById(qryNfse);
			
			try{
				if(nfseId.getNumNf().intValue()==this.getNumNotaFiscal().intValue() && nfseId.getChaveNfse().equals(this.getCodVerificador())){
					this.rltContasInternas = relatorioUtil.geraRelatorio(parametrosRelatorio, nomeRelatorioJasper, nomeRelatorioSaida, 1);					
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Atenção","Estão divergentes o número da nota fiscal ou código de verificação. Por gentileza verifique."));				
				}
				
			}catch (UtilException e){
				context.addMessage(null,new FacesMessage(e.getMessage()));
			}
						
			return this.rltContasInternas;
			
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Atenção","O número da nota fiscal não existe em nossa base de dados. Por gentileza verifique."));
			try {
				//index.xhtml?CNPJ=01937440000132&Doc=6289&Cod=1UKDQ105
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml?CNPJ="+this.getCnpjCliente()+"&Doc="+String.valueOf(this.getNumNotaFiscal())+"&Cod="+this.getCodVerificador());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		
			
		

	}

	public void setRltContasInternas(StreamedContent rltContasInternas) {
		this.rltContasInternas = rltContasInternas;
	}

	public String getUrlAtual(String url) {
		String urlWindow = url;
		String teste = urlWindow;
		return urlAtual;
	}

	public void setUrlAtual(String urlAtual) {
		this.urlAtual = urlAtual;
	}

	public String getUrlAtual() {
		return urlAtual;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}



	


	
}
