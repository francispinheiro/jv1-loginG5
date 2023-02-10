package br.com.global5.loginG5.administrativo.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;

import br.com.global5.loginG5.administrativo.model.Empresa;
import br.com.global5.loginG5.infra.CrudService;
import br.com.global5.loginG5.infra.model.Filter;


public class EmpresaService extends CrudService<Empresa>  {
	
    private static final long serialVersionUID = 202006081330L;

    @Override
    public Criteria configPagination(Filter<Empresa> filter) {
        if (filter.hasParam("id")) {
            crud().eq("id",
                    Integer.parseInt(filter.getParam("id").toString()));
        }

        // see index.xhtml 'model' column facet name filter
        if (filter.getEntity() != null) {
            if(filter.getEntity().getRazaoSocial() != null){
                crud().ilike("descricao", filter.getEntity().getRazaoSocial(), MatchMode.ANYWHERE);
            }
        }

        return crud().getCriteria();
    }

    @SuppressWarnings("unchecked")
    public List<String> getDescricao(String query) {
        return crud().criteria().ilike("razaoSocial", query, MatchMode.ANYWHERE)
                .projection(Projections.property("razaoSocial")).list();
    }
}
