package br.com.global5.loginG5.model.geral;

import java.util.Date;

import javax.persistence.*;

import br.com.global5.loginG5.administrativo.model.Empresa;
import br.com.global5.loginG5.infra.model.BaseEntity;

@Entity
@Table(name = "usuario_empresa")
public class UsuarioEmpresa implements BaseEntity {
	
	@Id
	@SequenceGenerator(name="usuario_empresa_usueoid_seq", sequenceName="usuario_empresa_usueoid_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="usuario_empresa_usueoid_seq")
	@Column(name = "usueoid")
	private Integer id;
	
	@ManyToOne(
			fetch = FetchType.EAGER,
			targetEntity = Usuario.class,
			cascade={CascadeType.DETACH, CascadeType.MERGE}
	)
	@JoinColumn(name = "usue_usuoid")
	private Usuario usuario;
	
	@ManyToOne(
			fetch = FetchType.EAGER,
			targetEntity = Empresa.class,
			cascade={CascadeType.DETACH, CascadeType.MERGE}
	)
	@JoinColumn(name = "usue_empoid")
	private Empresa empresa;
	
	@Column( name = "usue_token")
	private String token;
	
	@Column( name = "usue_dt_token")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataToken;
	
	@Column( name = "usue_dt_exclusao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataExclusao;
	
	@ManyToOne(
			fetch = FetchType.EAGER,
			targetEntity = Usuario.class,
			cascade={CascadeType.DETACH, CascadeType.MERGE}
	)
	@JoinColumn(name = "usue_usuoid_criacao")
	private Usuario usuCriacao;
	
	@ManyToOne(
			fetch = FetchType.EAGER,
			targetEntity = Usuario.class,
			cascade={CascadeType.DETACH, CascadeType.MERGE}
	)
	@JoinColumn(name = "usue_usuoid_exclusao")
	private Usuario usuExclusao;
	
	public UsuarioEmpresa () {}
	public UsuarioEmpresa (Integer id) {
		this.id = id;
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
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public Date getDataToken() {
		return dataToken;
	}
	public void setDataToken(Date dataToken) {
		this.dataToken = dataToken;
	}
	public Date getDataExclusao() {
		return dataExclusao;
	}
	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
	public Usuario getUsuCriacao() {
		return usuCriacao;
	}
	public void setUsuCriacao(Usuario usuCriacao) {
		this.usuCriacao = usuCriacao;
	}
	public Usuario getUsuExclusao() {
		return usuExclusao;
	}
	public void setUsuExclusao(Usuario usuExclusao) {
		this.usuExclusao = usuExclusao;
	}
	
}
