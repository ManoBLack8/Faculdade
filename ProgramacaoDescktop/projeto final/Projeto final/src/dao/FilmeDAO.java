package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Filme;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class FilmeDAO {
    private List<Filme> filmes;
    private int proximoId;
    private static final String JSON_FILE = "filmes.json";

    public FilmeDAO() {
        filmes = new ArrayList<>();
        proximoId = 1;
        loadFromJson(); // Carrega os dados do JSON ao iniciar
    }

    // Create
    public void adicionarFilme(Filme filme) {
        filme.setId(proximoId++);
        filmes.add(filme);
        saveToJson(); // Salva no JSON após adicionar
    }

    // Read
    public List<Filme> listarFilmes() {
        return filmes;
    }

    public Filme buscarFilmePorId(int id) {
        for (Filme filme : filmes) {
            if (filme.getId() == id) {
                return filme;
            }
        }
        return null;
    }

    // Update
    public void atualizarFilme(Filme filmeAtualizado) {
        for (Filme filme : filmes) {
            if (filme.getId() == filmeAtualizado.getId()) {
                filme.setTitulo(filmeAtualizado.getTitulo());
                filme.setDataLancamento(filmeAtualizado.getDataLancamento());
                filme.setDiretor(filmeAtualizado.getDiretor());
                filme.setNota(filmeAtualizado.getNota());
                filme.setGenero(filmeAtualizado.getGenero());
                saveToJson(); // Salva no JSON após atualizar
                break;
            }
        }
    }

    // Delete
    public void removerFilme(int id) {
        filmes.removeIf(filme -> filme.getId() == id);
        saveToJson(); // Salva no JSON após remover
    }

    // Salvar dados no arquivo JSON
    public void saveToJson() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(filmes, writer);
            System.out.println("Dados salvos em " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo JSON: " + e.getMessage());
        }
    }

    // Carregar dados do arquivo JSON
    public void loadFromJson() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(JSON_FILE)) {
            Type listType = new TypeToken<List<Filme>>() {}.getType();
            filmes = gson.fromJson(reader, listType);
            if (filmes == null) {
                filmes = new ArrayList<>(); // Evita NullPointerException
            }
            // Atualiza o próximo ID com base no último filme
            if (!filmes.isEmpty()) {
                proximoId = filmes.get(filmes.size() - 1).getId() + 1;
            }
            System.out.println("Dados carregados de " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("Erro ao carregar do arquivo JSON: " + e.getMessage());
        }
    }

    public boolean editarFilme(int id, String titulo, Date dataLancamento, String diretor, double nota, String genero, String capa) {
        for (Filme filme : filmes) {
            if (filme.getId() == id) {
                filme.setTitulo(titulo);
                filme.setDataLancamento(dataLancamento);
                filme.setDiretor(diretor);
                filme.setNota(nota);
                filme.setGenero(genero);
                filme.setCapa(capa);
                saveToJson(); // Atualiza o arquivo JSON
                return true;
            }
        }
        return false;
    }
    
}