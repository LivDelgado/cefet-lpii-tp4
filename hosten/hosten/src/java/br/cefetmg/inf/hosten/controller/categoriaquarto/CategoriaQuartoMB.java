package br.cefetmg.inf.hosten.controller.categoriaquarto;

import br.cefetmg.inf.hosten.model.domain.CategoriaQuarto;
import br.cefetmg.inf.hosten.model.service.IManterCategoriaQuarto;
import br.cefetmg.inf.hosten.proxy.ManterCategoriaQuartoProxy;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "categoriaMB")
@ViewScoped
public class CategoriaQuartoMB {

    private List<CategoriaQuarto> listaCtgs;
    private CategoriaQuarto ctg;

    public CategoriaQuartoMB() {
            ctg = null;
            IManterCategoriaQuarto manterCtgs = new ManterCategoriaQuartoProxy();
            try {
                listaCtgs = manterCtgs.listarTodos();
            } catch (Exception ex) {
                //
            }
        }

    public CategoriaQuarto getCtg() {
        return ctg;
    }

    public void setCtg(CategoriaQuarto ctg) {
        this.ctg = ctg;
    }

    public List<CategoriaQuarto> getListaCtgs() {
        return listaCtgs;
    }

    public void editar() {
        System.out.println("editar ctg: " + ctg.getNomCategoria());
    }

    public void deletar() {
        System.out.println("editar ctg: " + ctg.getNomCategoria());
    }
}