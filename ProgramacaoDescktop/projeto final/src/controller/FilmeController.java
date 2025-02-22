package controller;

import dao.AvaliacaoDAO;
import dao.FilmeDAO;
import model.Avalicoes;
import model.Filme;
import view.AvaliacaoView;

import java.util.Date;
import java.util.List;


public class FilmeController {
    private FilmeDAO filmeDAO;

    public FilmeController() {
        this.filmeDAO = new FilmeDAO();
    }

    public void adicionarFilme(String titulo, Date dataLancamento, String diretor, double nota, String genero) {
        Filme filme = new Filme(titulo, dataLancamento, diretor, nota, genero);
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

    public void atualizarFilme(int id, String titulo, Date dataLancamento, String diretor, double nota, String genero) {
        Filme filme = new Filme(titulo, dataLancamento, diretor, nota, genero);
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

    // Exemplo de uso na FilmeView
    public List<Avalicoes> abrirAvaliacoesDoFilme(int filmeId) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
        List<Avalicoes> avaliacoes = avaliacaoDAO.buscarAvaliacoesPorFilme(filmeId);

        // Abre a view de avaliações
        AvaliacaoView avaliacaoView = new AvaliacaoView(avaliacoes, filmeId);
        avaliacaoView.setVisible(true);
        return avaliacoes;
    }

    // Exemplo de edição de uma avaliação
    public void editarAvaliacaoExemplo(Avalicoes avaliacao) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
        avaliacaoDAO.editarAvaliacao(avaliacao);
    }

    // Exemplo de exclusão de uma avaliação
    public void deletarAvaliacaoExemplo(int avaliacaoId) {
        AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
        avaliacaoDAO.deletarAvaliacao(avaliacaoId);
    }

}