package controller;

import dao.FilmeDAO;
import model.Filme;
import java.util.Date;
import java.util.List;

public class FilmeController {
    private FilmeDAO filmeDAO;

    public FilmeController() {
        this.filmeDAO = new FilmeDAO();
    }

    public void adicionarFilme(String titulo, Date dataLancamento, String diretor, double nota, String genero, String capa) {
        Filme filme = new Filme(titulo, dataLancamento, diretor, nota, genero, capa);
        filmeDAO.adicionarFilme(filme);
    }

    public boolean editarFilme(int id, String titulo, Date dataLancamento, String diretor, double nota, String genero, String capa) {
        return filmeDAO.editarFilme(id, titulo, dataLancamento, diretor, nota, genero, capa);
    }

    public List<Filme> listarFilmes() {
        return filmeDAO.listarFilmes();
    }

    public Filme buscarFilmePorId(int id) {
        return filmeDAO.buscarFilmePorId(id);
    }

    public void atualizarFilme(int id, String titulo, Date dataLancamento, String diretor, double nota, String genero, String capa) {
        Filme filme = new Filme(titulo, dataLancamento, diretor, nota, genero, capa);
        filme.setId(id);
        filmeDAO.atualizarFilme(filme);
    }

    public void removerFilme(int id) {
        filmeDAO.removerFilme(id);
    }

    public Filme buscarFilmePorTitulo(String titulo) {
    for (Filme filme : listarFilmes()) {
        if (filme.getTitulo().equalsIgnoreCase(titulo)) {
            return filme;
        }
    }
    return null; // Retorna null se não encontrar
}

}