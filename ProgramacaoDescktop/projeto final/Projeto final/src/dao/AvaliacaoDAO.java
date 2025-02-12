package dao;

import model.Avalicoes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AvaliacaoDAO {

    private static final String FILE_PATH = "avaliacoes.json";
    private Gson gson = new Gson();

    // Método para salvar uma lista de avaliações no arquivo JSON
    public void salvarAvaliacoes(List<Avalicoes> avaliacoes) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(avaliacoes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar a lista de avaliações do arquivo JSON
    public List<Avalicoes> carregarAvaliacoes() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Avalicoes>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Método para adicionar uma nova avaliação
    public void adicionarAvaliacao(Avalicoes avaliacao) {
        List<Avalicoes> avaliacoes = carregarAvaliacoes();
        avaliacoes.add(avaliacao);
        salvarAvaliacoes(avaliacoes);
    }
    
    public List<Avalicoes> buscarAvaliacoesPorFilme(int filmeId) {
        List<Avalicoes> todasAvaliacoes = carregarAvaliacoes();
        return todasAvaliacoes.stream()
                .filter(av -> av.getFilmeId() == filmeId)
                .collect(Collectors.toList());
    }

    public void editarAvaliacao(Avalicoes avaliacao) {
        List<Avalicoes> avaliacoes = carregarAvaliacoes();
        for (int i = 0; i < avaliacoes.size(); i++) {
            if (avaliacoes.get(i).getId() == avaliacao.getId()) {
                avaliacoes.set(i, avaliacao);
                break;
            }
        }
        salvarAvaliacoes(avaliacoes);
    }

    public void deletarAvaliacao(int avaliacaoId) {
        List<Avalicoes> avaliacoes = carregarAvaliacoes();
        avaliacoes.removeIf(av -> av.getId() == avaliacaoId);
        salvarAvaliacoes(avaliacoes);
    }
}