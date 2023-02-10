package br.com.global5.loginG5.administrativo.bean;


import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.StreamedContent;

import br.com.global5.loginG5.administrativo.model.ContasReceber;
import br.com.global5.loginG5.administrativo.model.NotaFiscal;
import br.com.global5.loginG5.administrativo.service.ContasReceberService;
import br.com.global5.loginG5.administrativo.service.NotaFiscalService;
import br.com.global5.loginG5.infra.util.RelatorioUtil;
import br.com.global5.loginG5.infra.util.UtilException;

@Named
@ViewScoped
@ManagedBean(name = "emissorBoletoNf")
public class BoletoFinMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ContasReceber cre;
	
	
	
	private Integer id;
	
	private String 	cnpjCliente = "";
	private Integer numNotaFiscal = null;
	private String	codVerificador = "";
	private String  urlAtual = "";
	
	// Relatórios
	private StreamedContent rltContasInternas;
	
	@Inject
	ContasReceberService creService;
	
	@Inject
	NotaFiscalService nfseService;	
	
	@PostConstruct
	public void init(){
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

	public StreamedContent getRltContasInternas() {
		
		FacesContext context = FacesContext.getCurrentInstance();
 
		String nomeRelatorioJasper = "Boleto";
		String nomeRelatorioSaida  = "Boleto" + "_Doc_6289";
				
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		
		HashMap parametrosRelatorio = new HashMap<>();
		parametrosRelatorio.put("doc", 6289);
		
		try{
			this.rltContasInternas = relatorioUtil.geraRelatorio(parametrosRelatorio, nomeRelatorioJasper, nomeRelatorioSaida, 1);
		}catch (UtilException e){
			context.addMessage(null,new FacesMessage(e.getMessage()));
		}
					
		return this.rltContasInternas;

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



	


	
}
