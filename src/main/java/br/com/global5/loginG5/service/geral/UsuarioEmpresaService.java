package br.com.global5.loginG5.service.geral;

import org.hibernate.Criteria;

import br.com.global5.loginG5.infra.CrudService;
import br.com.global5.loginG5.infra.model.Filter;
import br.com.global5.loginG5.model.geral.UsuarioEmpresa;

public class UsuarioEmpresaService extends CrudService<UsuarioEmpresa> {
	
	private static final long serialVersionUID = 20180912090038L;
	
	public Criteria configPagination(Filter<UsuarioEmpresa> filter){
		
		if(filter.hasParam("id")){
			crud().eq("id", Integer.parseInt(filter.getParam("id").toString()));
		}
		return crud().getCriteria();
	}

}
