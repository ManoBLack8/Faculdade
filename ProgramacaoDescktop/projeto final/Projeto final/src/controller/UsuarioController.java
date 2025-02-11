package controller;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Método para adicionar um novo usuário
    public boolean adicionarUsuario(String nome, String email, String senha) {
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            return false; // Não permite dados vazios
        }
        Usuario novoUsuario = new Usuario(0, nome, email, senha);
        usuarioDAO.adicionarUsuario(novoUsuario);
        return true;
    }

    // Método para editar um usuário existente
    public boolean editarUsuario(int id, String novoNome, String novoEmail, String novaSenha) {
        if (novoNome.isEmpty() || novoEmail.isEmpty() || novaSenha.isEmpty()) {
            return false; // Não permite campos vazios
        }
        return usuarioDAO.editarUsuario(id, novoNome, novoEmail, novaSenha);
    }

    // Método para remover um usuário pelo ID
    public boolean removerUsuario(int id) {
        return usuarioDAO.removerUsuario(id);
    }

    // Método para listar todos os usuários
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    // Método para buscar um usuário por email e senha (login)
    public Usuario autenticarUsuario(String email, String senha) {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario; // Retorna o usuário autenticado
            }
        }
        return null; // Retorna null se a autenticação falhar
    }
}
