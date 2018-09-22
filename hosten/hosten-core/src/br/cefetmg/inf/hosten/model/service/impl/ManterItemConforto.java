package br.cefetmg.inf.hosten.model.service.impl;

import br.cefetmg.inf.hosten.model.dao.IItemConfortoDAO;
import br.cefetmg.inf.hosten.model.dao.impl.ItemConfortoDAO;
import br.cefetmg.inf.hosten.model.dao.rel.impl.CategoriaItemConfortoDAO;
import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.hosten.model.domain.rel.CategoriaItemConforto;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.List;
import br.cefetmg.inf.hosten.model.dao.rel.ICategoriaItemConfortoDAO;
import br.cefetmg.inf.hosten.model.service.IManterItemConforto;

public class ManterItemConforto implements IManterItemConforto {

    IItemConfortoDAO objetoDAO;

    public ManterItemConforto() {
        objetoDAO = ItemConfortoDAO.getInstance();
    }

    @Override
    public boolean inserir(ItemConforto itemConforto)
            throws NegocioException, SQLException {
        // testa tamanho dos campos
        if (itemConforto.getCodItem().length() != 3) {
            throw new NegocioException("O código do item deve ter 3 caracteres.");
        }
        if (itemConforto.getDesItem().length() > 40) {
            throw new NegocioException("A descrição do item ultrapassou os 40 caracteres máximos permitidos.");
        }

        // pesquisa para saber se há algum item já 
        // inserido que possui o mesmo código
        List<ItemConforto> itensPesquisados
                = listar(itemConforto.getCodItem(), "codItem");

        if (itensPesquisados.isEmpty()) {
            // não tem item com o mesmo código

            // busca se tem item com a mesma descrição
            List<ItemConforto> itensPesquisados1
                    = listar(itemConforto.getDesItem(), "desItem");
            if (itensPesquisados1.isEmpty()) {
                // não tem item com a mesma descrição
                // pode inserir
                boolean testeRegistro = objetoDAO
                        .adicionaItemConforto(itemConforto);
                return testeRegistro;
            } else {
                // tem item com a mesma descrição
                throw new NegocioException("Descrição do item repetida!");
            }
        } else {
            // tem item com o mesmo código
            throw new NegocioException("Código do item repetido!");
        }
    }

    @Override
    public boolean alterar(String codRegistro, ItemConforto itemConforto)
            throws SQLException, NegocioException {
        // testa tamanho dos campos
        if (itemConforto.getCodItem().length() != 3) {
            throw new NegocioException("O código do item deve ter 3 caracteres.");
        }
        if (itemConforto.getDesItem().length() > 40) {
            throw new NegocioException("A descrição do item ultrapassou os 40 caracteres máximos permitidos.");
        }

        List<ItemConforto> buscaRegistroAntigo = listar(codRegistro, "codItem");
        ItemConforto registroAntigo = buscaRegistroAntigo.get(0);
        
        // pesquisa para saber se há algum item já inserido que possui o mesmo código
        List<ItemConforto> itensPesquisados
                = listar(itemConforto.getCodItem(), "codItem");

        if (itensPesquisados.isEmpty() || (codRegistro.equals(itemConforto.getCodItem())) ) {
            // não tem item com o mesmo código

            // busca se tem item com a mesma descrição
            List<ItemConforto> itensPesquisados1
                    = listar(itemConforto.getDesItem(), "desItem");
            if (itensPesquisados1.isEmpty()
                    || (registroAntigo.getDesItem().equals(itemConforto.getDesItem())) ) {
                // não tem item com a mesma descrição
                // pode alterar
                boolean testeRegistro
                        = objetoDAO
                                .atualizaItemConforto(codRegistro, itemConforto);
                return testeRegistro;
            } else {
                // tem item com a mesma descrição
                throw new NegocioException("Descrição do item repetida!");
            }
        } else {
            // tem item com o mesmo código
            throw new NegocioException("Código do item repetido!");
        }
    }

    @Override
    public boolean excluir(String codRegistro)
            throws NegocioException, SQLException {
        //
        // pesquisa se o código do item é utilizado em Categoria de Quarto
        //
        ICategoriaItemConfortoDAO relDAO = CategoriaItemConfortoDAO.getInstance();
        List<CategoriaItemConforto> rel = relDAO.busca(codRegistro, "codItem");
        if (rel.isEmpty()) {
            return objetoDAO.deletaItemConforto(codRegistro);
        } else {
            throw new NegocioException(
                    "Não é possível excluir esse item: "
                    + "ele é utilizado em uma categoria de quartos!");
        }
    }

    @Override
    public List<ItemConforto> listar(Object dadoBusca, String coluna)
            throws NegocioException, SQLException {
        //
        // confere se foi digitado um dado busca e se a coluna é válida
        //
        if (dadoBusca != null) {
            if (coluna.equals("codItem") || coluna.equals("desItem")) {
                return objetoDAO.buscaItemConforto(dadoBusca, coluna);
            } else {
                throw new NegocioException(
                        "Não existe essa informação em item de conforto! "
                        + "Busque pelo código ou pela descrição");
            }
        } else {
            throw new NegocioException("Nenhum item buscado!");
        }
    }

    @Override
    public List<ItemConforto> listarTodos()
            throws NegocioException, SQLException {
        return objetoDAO.buscaTodosItemConfortos();
    }
}
