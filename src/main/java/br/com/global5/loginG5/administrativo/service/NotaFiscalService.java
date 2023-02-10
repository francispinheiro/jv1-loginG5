package br.com.global5.loginG5.administrativo.service;

import br.com.global5.loginG5.administrativo.model.NotaFiscal;
import br.com.global5.loginG5.infra.CrudService;
import br.com.global5.loginG5.infra.model.Filter;

import org.hibernate.Criteria;

public class NotaFiscalService extends CrudService<NotaFiscal>{

    private static final long serialVersionUID = 201806021305L;

    @Override
    public Criteria configPagination(Filter<NotaFiscal> filter) {

        if (filter.hasParam("id")) {
            crud().eq("id",
                    Integer.parseInt(filter.getParam("id").toString()));
        }

        return crud().getCriteria();
    }
}
