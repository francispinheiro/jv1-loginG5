package br.com.global5.loginG5.administrativo.model;

import javax.persistence.*;

import br.com.global5.loginG5.infra.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "titulo_receber")
public class ContasReceber implements BaseEntity {



    @Id
    @SequenceGenerator(name = "titulo_receber_titroid_seq", sequenceName = "titulo_receber_titroid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "titulo_receber_titroid_seq")
    @Column(name ="titroid")
    private Integer id;
    @Column(name ="titr_num_titulo")
    private String numTitulo;
    @Column(name ="titr_dt_emissao")
    private Date dt_emissaoTit;
    @Column(name ="titr_dt_fatura")
    private Date dt_fatura;
    @Column(name ="titr_dt_liquidacao")
    private Date dt_liquidacao;
    @Column(name ="titr_dt_nf")
    private Date dt_emissaoNf;
    @Column(name ="titr_dt_registro_sistema")
    private Date dt_registroTitSistema;
    @Column(name ="titr_dt_vencimento")
    private Date dt_vencimento;

    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = NotaFiscal.class,
            cascade = {CascadeType.DETACH, CascadeType.MERGE}
    )
    @JoinColumn(name ="titr_nfoid")
    private NotaFiscal notaFiscal;

    @Column(name ="titr_nr_titulo_bancario")
    private String nossoNumero;
    
    @Column(name ="titr_linha_digitavel_bol")
    private String linhaDigitavelBol;
    
    @Column(name ="titr_codigo_barra_bol")
    private String codigoBarraBol;

    @Column(name ="titr_nr_arquivo_remessa")
    private Integer numArqRemessa;

    @Column(name ="titr_cod_ocorrencia_remessa")
    private String codOcorrenciaRem;

    @Column(name ="titr_vlr_despesa_cartorio")
    private BigDecimal vlrDespCartorio;

    @Column(name ="titr_vlr_despesa_cobranca")
    private BigDecimal vlrDespCobranca;

    @Column(name ="titr_observacao")
    private String observacao;

    @Column(name ="titr_dt_ultimo_recebimento")
    private Date dt_ultRecebimento;

    @Column(name ="titr_vlr_aberto")
    private BigDecimal vlrAberto;

    @Column(name ="titr_vlr_desconto")
    private BigDecimal vlrDesconto;

    @Column(name ="titr_vlr_juros")
    private BigDecimal vlrJuros;
    
    @Column(name ="titr_vlr_juros_dia")
    private BigDecimal vlrJurosDia;
    
    @Column(name = "titr_perc_juros_mes")
    private BigDecimal percentualJuros;

    @Column(name ="titr_vlr_liquido")
    private BigDecimal vlrLiquido;

    @Column(name ="titr_vlr_multa")
    private BigDecimal vlrMulta;
    
    @Column(name = "titr_perc_multa_mes")
    private BigDecimal percentualMulta;
 
    @Column(name ="titr_vlr_original")
    private BigDecimal vlrOriginal;

    @Column(name ="titr_comissao_gerada")
    private boolean geradaComissao;

    @Column(name ="titr_dt_base_comissao")
    private Date dt_BaseComissao;
    
    @Column( name = "titr_dt_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt_Alteracao;
   
    public ContasReceber (){}
    public ContasReceber (Integer id){this.id = id;}
    public ContasReceber numTitulo (String numTitulo){
    	this.numTitulo = numTitulo;
    	return this;
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
	public String getNumTitulo() {
		return numTitulo;
	}
	public void setNumTitulo(String numTitulo) {
		this.numTitulo = numTitulo;
	}
	public Date getDt_emissaoTit() {
		return dt_emissaoTit;
	}
	public void setDt_emissaoTit(Date dt_emissaoTit) {
		this.dt_emissaoTit = dt_emissaoTit;
	}
	public Date getDt_fatura() {
		return dt_fatura;
	}
	public void setDt_fatura(Date dt_fatura) {
		this.dt_fatura = dt_fatura;
	}
	public Date getDt_liquidacao() {
		return dt_liquidacao;
	}
	public void setDt_liquidacao(Date dt_liquidacao) {
		this.dt_liquidacao = dt_liquidacao;
	}
	public Date getDt_emissaoNf() {
		return dt_emissaoNf;
	}
	public void setDt_emissaoNf(Date dt_emissaoNf) {
		this.dt_emissaoNf = dt_emissaoNf;
	}
	public Date getDt_registroTitSistema() {
		return dt_registroTitSistema;
	}
	public void setDt_registroTitSistema(Date dt_registroTitSistema) {
		this.dt_registroTitSistema = dt_registroTitSistema;
	}
	public Date getDt_vencimento() {
		return dt_vencimento;
	}
	public void setDt_vencimento(Date dt_vencimento) {
		this.dt_vencimento = dt_vencimento;
	}
	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}
	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	public String getNossoNumero() {
		return nossoNumero;
	}
	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}
	public String getLinhaDigitavelBol() {
		return linhaDigitavelBol;
	}
	public void setLinhaDigitavelBol(String linhaDigitavelBol) {
		this.linhaDigitavelBol = linhaDigitavelBol;
	}
	public String getCodigoBarraBol() {
		return codigoBarraBol;
	}
	public void setCodigoBarraBol(String codigoBarraBol) {
		this.codigoBarraBol = codigoBarraBol;
	}
	public Integer getNumArqRemessa() {
		return numArqRemessa;
	}
	public void setNumArqRemessa(Integer numArqRemessa) {
		this.numArqRemessa = numArqRemessa;
	}
	public String getCodOcorrenciaRem() {
		return codOcorrenciaRem;
	}
	public void setCodOcorrenciaRem(String codOcorrenciaRem) {
		this.codOcorrenciaRem = codOcorrenciaRem;
	}
	public BigDecimal getVlrDespCartorio() {
		return vlrDespCartorio;
	}
	public void setVlrDespCartorio(BigDecimal vlrDespCartorio) {
		this.vlrDespCartorio = vlrDespCartorio;
	}
	public BigDecimal getVlrDespCobranca() {
		return vlrDespCobranca;
	}
	public void setVlrDespCobranca(BigDecimal vlrDespCobranca) {
		this.vlrDespCobranca = vlrDespCobranca;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Date getDt_ultRecebimento() {
		return dt_ultRecebimento;
	}
	public void setDt_ultRecebimento(Date dt_ultRecebimento) {
		this.dt_ultRecebimento = dt_ultRecebimento;
	}
	public BigDecimal getVlrAberto() {
		return vlrAberto;
	}
	public void setVlrAberto(BigDecimal vlrAberto) {
		this.vlrAberto = vlrAberto;
	}
	public BigDecimal getVlrDesconto() {
		return vlrDesconto;
	}
	public void setVlrDesconto(BigDecimal vlrDesconto) {
		this.vlrDesconto = vlrDesconto;
	}
	public BigDecimal getVlrJuros() {
		return vlrJuros;
	}
	public void setVlrJuros(BigDecimal vlrJuros) {
		this.vlrJuros = vlrJuros;
	}
	public BigDecimal getVlrJurosDia() {
		return vlrJurosDia;
	}
	public void setVlrJurosDia(BigDecimal vlrJurosDia) {
		this.vlrJurosDia = vlrJurosDia;
	}
	public BigDecimal getPercentualJuros() {
		return percentualJuros;
	}
	public void setPercentualJuros(BigDecimal percentualJuros) {
		this.percentualJuros = percentualJuros;
	}
	public BigDecimal getVlrLiquido() {
		return vlrLiquido;
	}
	public void setVlrLiquido(BigDecimal vlrLiquido) {
		this.vlrLiquido = vlrLiquido;
	}
	public BigDecimal getVlrMulta() {
		return vlrMulta;
	}
	public void setVlrMulta(BigDecimal vlrMulta) {
		this.vlrMulta = vlrMulta;
	}
	public BigDecimal getPercentualMulta() {
		return percentualMulta;
	}
	public void setPercentualMulta(BigDecimal percentualMulta) {
		this.percentualMulta = percentualMulta;
	}
	public BigDecimal getVlrOriginal() {
		return vlrOriginal;
	}
	public void setVlrOriginal(BigDecimal vlrOriginal) {
		this.vlrOriginal = vlrOriginal;
	}
	public boolean isGeradaComissao() {
		return geradaComissao;
	}
	public void setGeradaComissao(boolean geradaComissao) {
		this.geradaComissao = geradaComissao;
	}
	public Date getDt_BaseComissao() {
		return dt_BaseComissao;
	}
	public void setDt_BaseComissao(Date dt_BaseComissao) {
		this.dt_BaseComissao = dt_BaseComissao;
	}
	public Date getDt_Alteracao() {
		return dt_Alteracao;
	}
	public void setDt_Alteracao(Date dt_Alteracao) {
		this.dt_Alteracao = dt_Alteracao;
	}

}
