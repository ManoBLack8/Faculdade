package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Avalicoes;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AvaliacoesDAO {
    private List<Avalicoes> avaliacoes;
    private static final String JSON_FILE = "avaliacoes.json";

    public AvaliacoesDAO() {
        avaliacoes = new ArrayList<>();
        loadFromJson(); // Carrega os dados do JSON ao iniciar
    }

    // Create
    public void adicionarAvaliacao(Avalicoes avaliacao) {
        avaliacoes.add(avaliacao);
        saveToJson();
    }

    // Read
    public List<Avalicoes> listarAvaliacoes() {
        return avaliacoes;
    }

    public Avalicoes buscarAvaliacaoPorId(int id) {
        for (Avalicoes avaliacao : avaliacoes) {
            if (avaliacao.getId() == id) {
                return avaliacao;
            }
        }
        return null;
    }

    // Update
    public void atualizarAvaliacao(Avalicoes avaliacaoAtualizada) {
        for (Avalicoes avaliacao : avaliacoes) {
            if (avaliacao.getId() == avaliacaoAtualizada.getId()) {
                avaliacao.setTitulo(avaliacaoAtualizada.getTitulo());
                avaliacao.setNota(avaliacaoAtualizada.getNota());
                avaliacao.setComentario(avaliacaoAtualizada.getComentario());
                avaliacao.setUsuarioId(avaliacaoAtualizada.getUsuarioId());
                avaliacao.setFilmeId(avaliacaoAtualizada.getFilmeId());
                break;
            }
        }
        saveToJson();
    }

    // Delete
    public void removerAvaliacao(int id) {
        avaliacoes.removeIf(avaliacao -> avaliacao.getId() == id);
        saveToJson();
    }

    // Salvar dados no arquivo JSON
    public void saveToJson() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(avaliacoes, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo JSON: " + e.getMessage());
        }
    }

    // Carregar dados do arquivo JSON
    public void loadFromJson() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(JSON_FILE)) {
            Type listType = new TypeToken<List<Avalicoes>>() {}.getType();
            avaliacoes = gson.fromJson(reader, listType);
            if (avaliacoes == null) {
                avaliacoes = new ArrayList<>(); // Evita NullPointerException
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar do arquivo JSON: " + e.getMessage());
        }
    }
}