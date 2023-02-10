package br.com.global5.loginG5.administrativo.model;

import br.com.global5.loginG5.infra.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@Table(name = "empresa")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Empresa implements BaseEntity {

    @Id
    @SequenceGenerator(name = "empresa_empoid_seq", sequenceName = "empresa_empoid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "empresa_empoid_seq")
    @Column(name = "empoid")
    private Integer id;
    @Column(name = "emp_razao_social")
    private String  razaoSocial;
    @Column(name = "emp_nome_fantasia")
    private String  nomeFantasia;
    @Column(name = "emp_cnpj")
    private String  numCNPJ;
    @Column(name = "emp_inscr_estadual")
    private String  numInscEstadual;
    @Column(name = "emp_inscr_municipal")
    private String  numInscMunicipal;
    @Column(name = "emp_perc_juros")
    private Double percJurosRec;
    @Column(name = "emp_perc_multa")
    private Double percMultaRec;
    @Column(name = "emp_schema")
    private String schemaEmpresa;
    @Column(name = "emp_dt_criacao")
    private Date dtCriacao;

    public Empresa(){}

    public Empresa (Integer id){this.id = id;}

    public Empresa razaoSocial (String nomeEmpresa){
        this.razaoSocial = nomeEmpresa;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getNumCNPJ() {
        return numCNPJ;
    }

    public void setNumCNPJ(String numCNPJ) {
        this.numCNPJ = numCNPJ;
    }

    public String getNumInscEstadual() {
        return numInscEstadual;
    }

    public void setNumInscEstadual(String numInscEstadual) {
        this.numInscEstadual = numInscEstadual;
    }

    public String getNumInscMunicipal() {
        return numInscMunicipal;
    }

    public void setNumInscMunicipal(String numInscMunicipal) {
        this.numInscMunicipal = numInscMunicipal;
    }

    public Double getPercJurosRec() {
        return percJurosRec;
    }

    public void setPercJurosRec(Double percJurosRec) {
        this.percJurosRec = percJurosRec;
    }

    public Double getPercMultaRec() {
        return percMultaRec;
    }

    public void setPercMultaRec(Double percMultaRec) {
        this.percMultaRec = percMultaRec;
    }

    public String getSchemaEmpresa() {
        return schemaEmpresa;
    }

    public void setSchemaEmpresa(String schemaEmpresa) {
        this.schemaEmpresa = schemaEmpresa;
    }

    public Date getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(Date dtCriacao) {
        this.dtCriacao = dtCriacao;
    }
}