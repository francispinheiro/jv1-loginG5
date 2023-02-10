package br.com.global5.loginG5.administrativo.model;

import javax.persistence.*;

import br.com.global5.loginG5.infra.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal implements BaseEntity {

    @Id
    @SequenceGenerator(name = "nota_fiscal_nfoid_seq", sequenceName = "nota_fiscal_nfoid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "nota_fiscal_nfoid_seq")
    @Column(name ="nfoid")
    private Integer id;
    @Column(name ="nf_emissao_previsao")
    private boolean origemPrevisao;

    @Column(name ="nf_nr_nf")
    private Integer numNf;
    @Column(name ="nf_nr_rps")
    private Integer numRps;

    @Column(name ="nf_cfop_servico")
    private String cfopServ;    
    @Column(name ="nf_dt_emissao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtEmissao;
    @Column(name ="nf_dt_autorizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtAutorizacao;

    @Column(name ="nf_dados_fatura")
    private String dadosFatura;

    @Column(name ="nf_observacao")
    private String observacao;
    
    @Column( name = "nf_dt_vencimento")
    private Date dtVencimento;

    @Column(name ="nf_complemento")
    private String complementoNf;
    @Column(name ="nf_vlr")
    private BigDecimal vlrServico;
    @Column(name ="nf_vlr_base_iss")
    private BigDecimal vlrBaseIss;
    @Column(name ="nf_vlr_iss")
    private BigDecimal vlrIss;
    @Column(name ="nf_vlr_liquido")
    private BigDecimal vlrLiquido;
    @Column(name ="nf_dt_cancelamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCancel;
    
    @Column( name = "nf_chave_nfse")
    private String chaveNfse;
    
    @Column( name = "nf_link_nfse")
    private String linkNfse;
    
    public NotaFiscal(){}

    public NotaFiscal(Integer id){this.id = id;}

    public NotaFiscal numNf(Integer numNf) {
        this.numNf = numNf;
        return this;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isOrigemPrevisao() {
		return origemPrevisao;
	}

	public void setOrigemPrevisao(boolean origemPrevisao) {
		this.origemPrevisao = origemPrevisao;
	}

	public Integer getNumNf() {
		return numNf;
	}

	public void setNumNf(Integer numNf) {
		this.numNf = numNf;
	}

	public Integer getNumRps() {
		return numRps;
	}

	public void setNumRps(Integer numRps) {
		this.numRps = numRps;
	}

	public String getCfopServ() {
		return cfopServ;
	}

	public void setCfopServ(String cfopServ) {
		this.cfopServ = cfopServ;
	}

	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public Date getDtAutorizacao() {
		return dtAutorizacao;
	}

	public void setDtAutorizacao(Date dtAutorizacao) {
		this.dtAutorizacao = dtAutorizacao;
	}

	public String getDadosFatura() {
		return dadosFatura;
	}

	public void setDadosFatura(String dadosFatura) {
		this.dadosFatura = dadosFatura;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public String getComplementoNf() {
		return complementoNf;
	}

	public void setComplementoNf(String complementoNf) {
		this.complementoNf = complementoNf;
	}

	public BigDecimal getVlrServico() {
		return vlrServico;
	}

	public void setVlrServico(BigDecimal vlrServico) {
		this.vlrServico = vlrServico;
	}

	public BigDecimal getVlrBaseIss() {
		return vlrBaseIss;
	}

	public void setVlrBaseIss(BigDecimal vlrBaseIss) {
		this.vlrBaseIss = vlrBaseIss;
	}

	public BigDecimal getVlrIss() {
		return vlrIss;
	}

	public void setVlrIss(BigDecimal vlrIss) {
		this.vlrIss = vlrIss;
	}

	public BigDecimal getVlrLiquido() {
		return vlrLiquido;
	}

	public void setVlrLiquido(BigDecimal vlrLiquido) {
		this.vlrLiquido = vlrLiquido;
	}

	public Date getDtCancel() {
		return dtCancel;
	}

	public void setDtCancel(Date dtCancel) {
		this.dtCancel = dtCancel;
	}

	public String getChaveNfse() {
		return chaveNfse;
	}

	public void setChaveNfse(String chaveNfse) {
		this.chaveNfse = chaveNfse;
	}

	public String getLinkNfse() {
		return linkNfse;
	}

	public void setLinkNfse(String linkNfse) {
		this.linkNfse = linkNfse;
	}
    
}
