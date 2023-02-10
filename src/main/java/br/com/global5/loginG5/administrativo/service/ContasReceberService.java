package br.com.global5.loginG5.administrativo.service;

import br.com.global5.loginG5.administrativo.model.ContasReceber;
import br.com.global5.loginG5.infra.CrudService;
import br.com.global5.loginG5.infra.model.Filter;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;




public class ContasReceberService extends CrudService<ContasReceber>{

    private static final long serialVersionUID = 201805021733L;

    @Override
    public Criteria configPagination(Filter<ContasReceber> filter) {

        if (filter.hasParam("id")) {
            crud().eq("id",
                    Integer.parseInt(filter.getParam("id").toString()));
        }

        if (filter.getEntity() != null) {
            if(filter.getEntity().getNumTitulo() != null){
                crud().ilike("descricao", filter.getEntity().getNumTitulo(), MatchMode.ANYWHERE);
            }
        }

        return crud().getCriteria();
    }

}
