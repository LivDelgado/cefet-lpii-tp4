package br.cefetmg.inf.hosten.model.service.impl;

import br.cefetmg.inf.hosten.model.dao.ICategoriaQuartoDAO;
import br.cefetmg.inf.hosten.model.dao.impl.CategoriaQuartoDAO;
import br.cefetmg.inf.hosten.model.dao.impl.QuartoDAO;
import br.cefetmg.inf.hosten.model.dao.rel.impl.CategoriaItemConfortoDAO;
import br.cefetmg.inf.hosten.model.domain.CategoriaQuarto;
import br.cefetmg.inf.hosten.model.domain.ItemConforto;
import br.cefetmg.inf.hosten.model.domain.Quarto;
import br.cefetmg.inf.hosten.model.domain.rel.CategoriaItemConforto;
import br.cefetmg.inf.util.exception.NegocioException;
import java.sql.SQLException;
import java.util.List;
import br.cefetmg.inf.hosten.model.dao.rel.ICategoriaItemConfortoDAO;
import br.cefetmg.inf.hosten.model.service.IManterCategoriaQuarto;

public class ManterCategoriaQuarto implements IManterCategoriaQuarto {

    ICategoriaQuartoDAO objetoDAO;

    public ManterCategoriaQuarto() {
        objetoDAO = CategoriaQuartoDAO.getInstance();
    }

    @Override
    public boolean inserir(
            CategoriaQuarto categoriaQuarto,
            List<ItemConforto> itensCategoria)
            throws NegocioException, SQLException {
        // testa tamanho dos campos
        if (categoriaQuarto.getCodCategoria().length() != 3) {
            throw new NegocioException("O código da categoria deve ter 3 caracteres.");
        }
        if (categoriaQuarto.getNomCategoria().length() > 40) {
            throw new NegocioException("O nome da categoria ultrapassou os 40 caracteres máximos permitidos.");
        }
        if (categoriaQuarto.getVlrDiaria() > 9999999.99) {
            throw new NegocioException("O valor da diária ultrapassou valor máximo de R$ 9999999,99.");
        }

        // pesquisa para saber se há alguma categoria já 
        // inserida que possui o mesmo código
        List<CategoriaQuarto> categoriasPesquisadas
                = listar(categoriaQuarto.getCodCategoria(), "codCategoria");

        if (categoriasPesquisadas.isEmpty()) {
            // não tem categoria com o mesmo código

            // busca se tem categoria com oo mesmo nome
            List<CategoriaQuarto> categoriasPesquisadas1
                    = listar(categoriaQuarto.getNomCategoria(), "nomCategoria");
            if (categoriasPesquisadas1.isEmpty()) {
                // não tem categoria com o mesmo nome
                // pode inserir

                if (itensCategoria.isEmpty()) {
                    throw new NegocioException(
                            "Não é possível adicionar uma categoria "
                            + "que não tem nenhum item de conforto.");
                }

                // cria os relacionamentos
                ICategoriaItemConfortoDAO relDAO = CategoriaItemConfortoDAO
                        .getInstance();
                for (ItemConforto item : itensCategoria) {
                    CategoriaItemConforto rel
                            = new CategoriaItemConforto(
                                    categoriaQuarto.getCodCategoria(),
                                    item.getCodItem());
                    relDAO.adiciona(rel);
                }

                // adiciona a categoria
                boolean testeRegistro = objetoDAO
                        .adicionaCategoriaQuarto(categoriaQuarto);
                return testeRegistro;
            } else {
                // tem item com a mesma descrição
                throw new NegocioException("Nome da categoria repetido!");
            }
        } else {
            // tem categoria com o mesmo código
            throw new NegocioException("Código da categoria repetido!");
        }
    }

    @Override
    public boolean alterar(String codRegistro, CategoriaQuarto categoriaQuarto, List<ItemConforto> itensCategoria)
            throws NegocioException, SQLException {
        // testa tamanho dos campos
        if (categoriaQuarto.getCodCategoria().length() != 3) {
            throw new NegocioException("O código da categoria deve ter 3 caracteres.");
        }
        if (categoriaQuarto.getNomCategoria().length() > 40) {
            throw new NegocioException("O nome da categoria ultrapassou os 40 caracteres máximos permitidos.");
        }
        if (categoriaQuarto.getVlrDiaria() > 9999999.99) {
            throw new NegocioException("O valor da diária ultrapassou valor máximo de R$ 9999999,99.");
        }

        List<CategoriaQuarto> buscaRegistroAntigo = listar(codRegistro, "codCategoriaQuarto");
        CategoriaQuarto registroAntigo = buscaRegistroAntigo.get(0);

        // pesquisa para saber se há alguma categoria já 
        // inserida que possui o mesmo código
        List<CategoriaQuarto> categoriasPesquisadas
                = listar(categoriaQuarto.getCodCategoria(), "codCategoria");

        if (categoriasPesquisadas.isEmpty() || (codRegistro.equals(categoriaQuarto.getCodCategoria()))) {
            // não tem categoria com o mesmo código

            // busca se tem categoria com oo mesmo nome
            List<CategoriaQuarto> categoriasPesquisadas1
                    = listar(categoriaQuarto.getNomCategoria(), "nomCategoria");
            if (categoriasPesquisadas1.isEmpty()
                    || (registroAntigo.getNomCategoria().equals(categoriaQuarto.getNomCategoria()))) {
                // não tem categoria com o mesmo nome
                // pode alterar

                if (itensCategoria.isEmpty()) {
                    throw new NegocioException("Não é possível deixar uma categoria sem nenhum item de conforto.");
                }

                // atualiza a categoria
                boolean testeRegistro = objetoDAO.atualizaCategoriaQuarto(codRegistro, categoriaQuarto);
                if (testeRegistro) {
                    // atualiza os relacionamentos
                    ICategoriaItemConfortoDAO relDAO = CategoriaItemConfortoDAO.getInstance();
                    // deleta todos os relacionamentos com aquela categoria
                    List<CategoriaItemConforto> listaREL = relDAO.busca(categoriaQuarto.getCodCategoria(), "codCategoria");
                    if (!listaREL.isEmpty()) {
                        relDAO.deletaPorColuna(categoriaQuarto.getCodCategoria(), "codCategoria");
                    }
                    // cria os relacionamentos com os itens passados
                    for (ItemConforto item : itensCategoria) {
                        CategoriaItemConforto rel = new CategoriaItemConforto(categoriaQuarto.getCodCategoria(), item.getCodItem());
                        relDAO.adiciona(rel);
                    }

                    return true;
                } else {
                    return false;
                }
            } else {
                // tem item com a mesma descrição
                throw new NegocioException("Nome da categoria repetido!");
            }
        } else {
            // tem categoria com o mesmo código
            throw new NegocioException("Código da categoria repetido!");
        }
    }

    @Override
    public boolean excluir(String codRegistro)
            throws NegocioException, SQLException {
        List<CategoriaQuarto> categoriasPesquisadas
                = listar(codRegistro, "codCategoria");
        if (categoriasPesquisadas.isEmpty()) {
            throw new NegocioException("Essa categoria não existe!");
        }

        // confere se há algum quarto na categoria
        QuartoDAO quartoDAO = QuartoDAO.getInstance();
        List<Quarto> listaQuartos = quartoDAO
                .buscaQuarto(codRegistro, "codCategoria");
        if (!listaQuartos.isEmpty()) {
            throw new NegocioException(
                    "Não é possível excluir essa categoria. Há "
                    + listaQuartos.size() + " quartos nela.");
        }

        // deleta todos os relacionamentos com aquela categoria
        ICategoriaItemConfortoDAO relDAO = CategoriaItemConfortoDAO.getInstance();
        List<CategoriaItemConforto> listaREL
                = relDAO.busca(
                        categoriasPesquisadas.get(0).getCodCategoria(),
                        "codCategoria");
        if (!listaREL.isEmpty()) {
            return relDAO.deletaPorColuna(
                    categoriasPesquisadas.get(0).getCodCategoria(),
                    "codCategoria");
        }

        // deleta a categoria
        return objetoDAO.deletaCategoriaQuarto(codRegistro);
    }

    @Override
    public List<CategoriaQuarto> listar(Object dadoBusca, String coluna)
            throws NegocioException, SQLException {
        //
        // confere se foi digitado um dado busca e se a coluna é válida
        //
        if (dadoBusca != null) {
            if (coluna.equals("codCategoria")
                    || coluna.equals("nomCategoria")
                    || coluna.equals("vlrDiaria")) {
                return objetoDAO.buscaCategoriaQuarto(dadoBusca, coluna);
            } else {
                throw new NegocioException(
                        "Não existe essa informação em categoria de quarto! "
                        + "Busque pelo código, pelo nome ou pelo valor");
            }
        } else {
            throw new NegocioException("Nenhuma categoria buscada!");
        }
    }

    @Override
    public List<CategoriaQuarto> listarTodos()
            throws NegocioException, SQLException {
        return objetoDAO.buscaTodosCategoriaQuartos();
    }

    @Override
    public List<ItemConforto> listarItensRelacionados(String codCategoria) 
            throws NegocioException, SQLException {
        if(codCategoria != null) {
            ICategoriaItemConfortoDAO categoriaItemConfortoDAO 
                = CategoriaItemConfortoDAO.getInstance();
            return categoriaItemConfortoDAO.buscaItensConfortoRelacionados(codCategoria);
        }
        return null;
    }
}
