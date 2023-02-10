package br.com.global5.loginG5.model.cadastro;

import br.com.global5.loginG5.infra.model.BaseEntity;
import br.com.global5.loginG5.model.areas.AreaFuncao;
import br.com.global5.loginG5.model.enums.DocumentoTipo;
import br.com.global5.loginG5.model.geral.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "pessoa")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pessoa implements BaseEntity {

    @Id
    @SequenceGenerator(name = "pessoa_pesoid_seq", sequenceName = "pessoa_pesoid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "pessoa_pesoid_seq")
    @Column(name = "pesoid")
    private Integer id;

    @Column(name = "pes_nome")
    private String nome;

    @Column(name = "pes_nome_fantasia")
    private String nomeFantasia;

    @Column(name = "pes_email")
    private String email;

    @Column(name = "pes_dt_criacao")
    private java.sql.Timestamp dtCriacao;

    @Column(name = "pes_dt_exclusao")
    private java.sql.Timestamp dtExclusao;

    @Column(name = "pes_dt_alteracao")
    private java.sql.Timestamp dtAlteracao;

    @ManyToOne(
            fetch=FetchType.LAZY,
            targetEntity=Usuario.class,
            cascade={CascadeType.DETACH, CascadeType.MERGE}
    )
    @JoinColumn(name="pes_usuoid_criacao")
    private Usuario usuarioCriacao;

    @ManyToOne(
            fetch=FetchType.LAZY,
            targetEntity=Usuario.class,
            cascade={CascadeType.DETACH, CascadeType.MERGE}
    )
    @JoinColumn(name="pes_usuoid_alteracao")
    private Usuario usuarioAlteracao;

    @ManyToOne(
            fetch=FetchType.LAZY,
            targetEntity=Usuario.class,
            cascade={CascadeType.DETACH, CascadeType.MERGE}
    )
    @JoinColumn(name="pes_usuoid_exclusao")
    private Usuario usuarioExclusao;

    @ManyToOne(
            fetch=FetchType.LAZY,
            targetEntity=AreaFuncao.class,
            cascade={CascadeType.DETACH, CascadeType.MERGE}
    )
    @JoinColumn(name="pes_afunoid")
    private AreaFuncao funcao;

    @Column(name = "pes_pessoa_fisica")
    private boolean pessoaFisica;

    @Column(name = "pes_documento1")
    private String documento1;

    @ManyToOne(
            fetch=FetchType.LAZY,
            targetEntity=DocumentoTipo.class,
            cascade={CascadeType.DETACH, CascadeType.MERGE}
    )
    @JoinColumn(name = "pes_documento1_tipo")
    private DocumentoTipo tipoDoc1;

    @Column(name = "pes_documento2")
    private String documento2;

    @ManyToOne(
            fetch=FetchType.LAZY,
            targetEntity=DocumentoTipo.class,
            cascade={CascadeType.DETACH, CascadeType.MERGE}
    )
    @JoinColumn(name = "pes_documento2_tipo")
    private DocumentoTipo tipoDoc2;

    @ManyToOne(
            fetch=FetchType.LAZY,
            targetEntity=DocumentoTipo.class,
            cascade={CascadeType.DETACH, CascadeType.MERGE}
    )
    @JoinColumn(name = "pes_documento3_tipo")
    private DocumentoTipo tipoDoc3;

    @Column(name = "pes_documento3")
    private String documento3;

 

  

    @ManyToOne(
            fetch=FetchType.LAZY,
            targetEntity=Localizador.class,
            cascade={CascadeType.DETACH, CascadeType.MERGE}
    )
    @JoinColumn(name="pes_locoid")
    private Localizador endereco;

    @Type(type = "json")
    @Column(name = "pes_dados_cadastrais", columnDefinition = "json")
    private String dadosCadastrais;

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInicial;

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtFinal;

    @Transient
    private boolean mostrarExcluidos;

    public Pessoa() {}

    public Pessoa(Integer id) {
        this.id = id;
    }

    public Pessoa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public boolean hasNome() {
        return nome != null && !"".equals(nome.trim());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Timestamp getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(Timestamp dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public Timestamp getDtExclusao() {
        return dtExclusao;
    }

    public void setDtExclusao(Timestamp dtExclusao) {
        this.dtExclusao = dtExclusao;
    }

    public Timestamp getDtAlteracao() {
        return dtAlteracao;
    }

    public void setDtAlteracao(Timestamp dtAlteracao) {
        this.dtAlteracao = dtAlteracao;
    }

    public Usuario getUsuarioCriacao() {
        return usuarioCriacao;
    }

    public void setUsuarioCriacao(Usuario usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    public Usuario getUsuarioAlteracao() {
        return usuarioAlteracao;
    }

    public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }

    public Usuario getUsuarioExclusao() {
        return usuarioExclusao;
    }

    public void setUsuarioExclusao(Usuario usuarioExclusao) {
        this.usuarioExclusao = usuarioExclusao;
    }

    public AreaFuncao getFuncao() {
        return funcao;
    }

    public void setFuncao(AreaFuncao funcao) {
        this.funcao = funcao;
    }

    public boolean isPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(boolean pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public String getDocumento1() {
        return documento1;
    }

    public void setDocumento1(String documento1) {
        this.documento1 = documento1;
    }

    public DocumentoTipo getTipoDoc1() {
        return tipoDoc1;
    }

    public void setTipoDoc1(DocumentoTipo tipoDoc1) {
        this.tipoDoc1 = tipoDoc1;
    }

    public String getDocumento2() {
        return documento2;
    }

    public void setDocumento2(String documento2) {
        this.documento2 = documento2;
    }

    public DocumentoTipo getTipoDoc2() {
        return tipoDoc2;
    }

    public void setTipoDoc2(DocumentoTipo tipoDoc2) {
        this.tipoDoc2 = tipoDoc2;
    }

    public Localizador getEndereco() {
        return endereco;
    }

    public void setEndereco(Localizador endereco) {
        this.endereco = endereco;
    }

    public String getDadosCadastrais() {
        return dadosCadastrais;
    }

    public void setDadosCadastrais(String dadosCadastrais) {
        this.dadosCadastrais = dadosCadastrais;
    }

    public Date getDtInicial() {
        return dtInicial;
    }

    public void setDtInicial(Date dtInicial) {
        this.dtInicial = dtInicial;
    }

    public Date getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(Date dtFinal) {
        this.dtFinal = dtFinal;
    }

    public boolean isMostrarExcluidos() {
        return mostrarExcluidos;
    }

    public void setMostrarExcluidos(boolean mostrarExcluidos) {
        this.mostrarExcluidos = mostrarExcluidos;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DocumentoTipo getTipoDoc3() {
        return tipoDoc3;
    }

    public void setTipoDoc3(DocumentoTipo tipoDoc3) {
        this.tipoDoc3 = tipoDoc3;
    }

 

    public String getDocumento3() {
        return documento3;
    }

    public void setDocumento3(String documento3) {
        this.documento3 = documento3;
    }
}
