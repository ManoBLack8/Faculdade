package dao;

import model.Genero;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GeneroDAO {
    private static final String ARQUIVO_JSON = "generos.json";
    private List<Genero> generos;

    public GeneroDAO() {
        this.generos = carregarGeneros();
    }

    // Adiciona um novo gênero
    public void adicionarGenero(Genero genero) {
        generos.add(genero);
        salvarGeneros();
    }

    // Lista todos os gêneros
    public List<Genero> listarGeneros() {
        return generos;
    }

    // Remove um gênero pelo nome
    public boolean removerGenero(String nome) {
        boolean removido = generos.removeIf(genero -> genero.getNome().equalsIgnoreCase(nome));
        if (removido) {
            salvarGeneros();
        }
        return removido;
    }

    // Salva os gêneros no arquivo JSON
    private void salvarGeneros() {
        try (Writer writer = new FileWriter(ARQUIVO_JSON)) {
            Gson gson = new Gson();
            gson.toJson(generos, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os gêneros: " + e.getMessage());
        }
    }

    // Carrega os gêneros do arquivo JSON
    private List<Genero> carregarGeneros() {
        try (Reader reader = new FileReader(ARQUIVO_JSON)) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Genero>>() {}.getType();
            return gson.fromJson(reader, tipoLista);
        } catch (FileNotFoundException e) {
            // Retorna uma lista vazia caso o arquivo não exista
            return new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar os gêneros: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
