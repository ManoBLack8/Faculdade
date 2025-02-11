package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String FILE_PATH = "usuarios.json";
    private List<Usuario> usuarios;

    public UsuarioDAO() {
        this.usuarios = carregarUsuarios();
    }

    // Adiciona um novo usuário
    public void adicionarUsuario(Usuario usuario) {
        usuario.setId(gerarNovoId());
        usuarios.add(usuario);
        salvarUsuarios();
    }

    // Edita um usuário existente pelo ID
    public boolean editarUsuario(int id, String novoNome, String novoEmail, String novaSenha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuario.setNome(novoNome);
                usuario.setEmail(novoEmail);
                usuario.setSenha(novaSenha);
                salvarUsuarios();
                return true;
            }
        }
        return false;
    }

    // Remove um usuário pelo ID
    public boolean removerUsuario(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuarios.remove(usuario);
                salvarUsuarios();
                return true;
            }
        }
        return false;
    }

    // Lista todos os usuários
    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    // Carrega os usuários do arquivo JSON
    private List<Usuario> carregarUsuarios() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Usuario>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // Salva os usuários no arquivo JSON
    private void salvarUsuarios() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new Gson().toJson(usuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Gera um novo ID para um usuário
    private int gerarNovoId() {
        return usuarios.isEmpty() ? 1 : usuarios.get(usuarios.size() - 1).getId() + 1;
    }
}
