package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Genero;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GeneroDAO {
    private static final String FILE_PATH = "generos.json";
    private List<Genero> generos;

    public GeneroDAO() {
        this.generos = carregarGeneros();
    }

    // Adiciona um gênero à lista
    public void adicionarGenero(Genero genero) {
        generos.add(genero);
        salvarGeneros();
    }

    // Retorna a lista de gêneros
    public List<Genero> listarGeneros() {
        carregarGeneros();
        return generos;
    }

    // Remove um gênero pelo nome
    public void removerGenero(String nome) {
        generos.removeIf(genero -> genero.getNome().equalsIgnoreCase(nome));
        salvarGeneros();
    }

    // Carrega os gêneros do arquivo JSON
    private List<Genero> carregarGeneros() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Genero>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>(); // Retorna uma lista vazia caso o arquivo não exista
        }
    }

    // Salva os gêneros no arquivo JSON
    private void salvarGeneros() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(generos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
