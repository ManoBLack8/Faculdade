package controller;

import dao.GeneroDAO;
import model.Genero;

import java.util.List;

public class GeneroController {
    private GeneroDAO generoDAO;

    public GeneroController() {
        this.generoDAO = new GeneroDAO();
    }

    // Adiciona um novo gênero
    public void adicionarGenero(String nome) {
        Genero genero = new Genero(nome);
        generoDAO.adicionarGenero(genero);
    }

    // Lista todos os gêneros
    public List<Genero> listarGeneros() {
        return generoDAO.listarGeneros();
    }

    // Remove um gênero pelo nome
    public void removerGenero(String nome) {
        generoDAO.removerGenero(nome);
    }
}
