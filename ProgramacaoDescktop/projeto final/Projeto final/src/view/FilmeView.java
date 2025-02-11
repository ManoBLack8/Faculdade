package view;

import controller.FilmeController;
import controller.UsuarioController; // Controlador para usuários
import model.Filme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilmeView extends JFrame {
    private FilmeController controller;
    private UsuarioController usuarioController; // Controlador de usuários
    private JPanel gridPanel;

    public FilmeView() {
        this.controller = new FilmeController();
        this.usuarioController = new UsuarioController(); // Inicializa o controlador de usuários
        initComponents();
    }

    private void initComponents() {
        setTitle("CRUD de Filmes e Usuários");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu superior
        JMenuBar menuBar = new JMenuBar();

        // Menu Arquivo
        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenuItem salvarItem = new JMenuItem("Salvar no JSON");
        salvarItem.addActionListener(e -> salvarNoJson());
        JMenuItem carregarItem = new JMenuItem("Carregar do JSON");
        carregarItem.addActionListener(e -> carregarDoJson());
        arquivoMenu.add(salvarItem);
        arquivoMenu.add(carregarItem);
        menuBar.add(arquivoMenu);

        // Menu Filme
        JMenu filmeMenu = new JMenu("Filme");
        JMenuItem adicionarFilmeItem = new JMenuItem("Adicionar Filme");
        adicionarFilmeItem.addActionListener(e -> adicionarFilme());
        JMenuItem editarFilmeItem = new JMenuItem("Editar Filme");
        editarFilmeItem.addActionListener(e -> editarFilme());
        filmeMenu.add(adicionarFilmeItem);
        filmeMenu.add(editarFilmeItem);
        menuBar.add(filmeMenu);

        // Menu Usuário
        JMenu usuarioMenu = new JMenu("Usuário");
        JMenuItem adicionarUsuarioItem = new JMenuItem("Adicionar Usuário");
        adicionarUsuarioItem.addActionListener(e -> adicionarUsuario());
        JMenuItem editarUsuarioItem = new JMenuItem("Editar Usuário");
        editarUsuarioItem.addActionListener(e -> editarUsuario());
        usuarioMenu.add(adicionarUsuarioItem);
        usuarioMenu.add(editarUsuarioItem);
        menuBar.add(usuarioMenu);

        setJMenuBar(menuBar);

        // Painel principal com grid de filmes
        gridPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 colunas, espaçamento de 10px
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(gridPanel), BorderLayout.CENTER);

        // Carrega os filmes ao iniciar
        carregarDoJson();
    }

    private void editarFilme() {
        String tituloBusca = JOptionPane.showInputDialog(this, 
            "Digite o título do filme para buscar:", 
            "Buscar Filme", JOptionPane.QUESTION_MESSAGE);
    
        if (tituloBusca == null || tituloBusca.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Título inválido! A busca foi cancelada.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    private void adicionarUsuario() {
        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField senhaField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("E-mail:"));
        panel.add(emailField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Adicionar Usuário", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String email = emailField.getText();
            String senha = senhaField.getText();

            if (!nome.isEmpty() && !email.isEmpty()) {
                usuarioController.adicionarUsuario(nome, email, senha); // Adiciona ao controlador de usuários
                JOptionPane.showMessageDialog(this, "Usuário adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarUsuario() {
        JTextField usuarioIdField = new JTextField();
        JTextField novoNomeField = new JTextField();
        JTextField novoEmailField = new JTextField();
        JTextField novoSenhaField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("ID do Usuário:"));
        panel.add(usuarioIdField);
        panel.add(new JLabel("Novo Nome:"));
        panel.add(novoNomeField);
        panel.add(new JLabel("Novo E-mail:"));
        panel.add(novoEmailField);
        panel.add(new JLabel("Nova Senha:"));
        panel.add(novoSenhaField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Editar Usuário", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int usuarioId = Integer.parseInt(usuarioIdField.getText());
                String novoNome = novoNomeField.getText();
                String novoEmail = novoEmailField.getText();
                String novoSenha = novoSenhaField.getText();

                if (usuarioController.editarUsuario(usuarioId, novoNome, novoEmail, novoSenha)) {
                    JOptionPane.showMessageDialog(this, "Usuário editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void salvarNoJson() {
        controller.adicionarFilme(getName(), null, getTitle(), DO_NOTHING_ON_CLOSE, getWarningString(), getName());;
        JOptionPane.showMessageDialog(this, "Dados salvos no arquivo JSON.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void carregarDoJson() {
        controller.listarFilmes();
        atualizarGrid();
    }

    private void atualizarGrid() {
        gridPanel.removeAll(); // Limpa o painel
        for (Filme filme : controller.listarFilmes()) {
            JPanel card = criarCardFilme(filme);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    adicionarAvaliacao(filme); // Abre o diálogo para adicionar avaliação
                }
            });
            gridPanel.add(card);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void adicionarAvaliacao(Filme filme) {
        JTextField notaField = new JTextField();
        JTextField comentarioField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nota (0 a 10):"));
        panel.add(notaField);
        panel.add(new JLabel("Comentário:"));
        panel.add(comentarioField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Adicionar Avaliação", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double nota = Double.parseDouble(notaField.getText());
                String comentario = comentarioField.getText();

                if (nota < 0 || nota > 10) {
                    JOptionPane.showMessageDialog(this, "A nota deve estar entre 0 e 10!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Aqui você pode chamar o controller de avaliações para adicionar a avaliação
                JOptionPane.showMessageDialog(this, "Avaliação adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nota inválida! Digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel criarCardFilme(Filme filme) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(Color.WHITE);

        // Carrega a imagem da capa
        ImageIcon icon = new ImageIcon(filme.getCapa());
        Image image = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH); // Redimensiona a imagem
        JLabel capaLabel = new JLabel(new ImageIcon(image));
        capaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(capaLabel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.add(new JLabel("Título: " + filme.getTitulo()));
        infoPanel.add(new JLabel("Diretor: " + filme.getDiretor()));
        infoPanel.add(new JLabel("Data: " + new SimpleDateFormat("dd/MM/yyyy").format(filme.getDataLancamento())));
        infoPanel.add(new JLabel("Nota: " + filme.getNota()));
        infoPanel.add(new JLabel("Gênero: " + filme.getGenero()));
        card.add(infoPanel, BorderLayout.SOUTH);

        return card;
    }

    private void adicionarFilme() {
        JTextField tituloField = new JTextField();
        JTextField dataField = new JTextField();
        JTextField diretorField = new JTextField();
        JTextField notaField = new JTextField();
        JTextField generoField = new JTextField();
        JTextField capaField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Título:"));
        panel.add(tituloField);
        panel.add(new JLabel("Data de Lançamento (dd/MM/yyyy):"));
        panel.add(dataField);
        panel.add(new JLabel("Diretor:"));
        panel.add(diretorField);
        panel.add(new JLabel("Nota:"));
        panel.add(notaField);
        panel.add(new JLabel("Gênero:"));
        panel.add(generoField);
        panel.add(new JLabel("Capa (caminho da imagem):"));
        panel.add(capaField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Adicionar Filme", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String titulo = tituloField.getText();
                Date dataLancamento = new SimpleDateFormat("dd/MM/yyyy").parse(dataField.getText());
                String diretor = diretorField.getText();
                double nota = Double.parseDouble(notaField.getText());
                String genero = generoField.getText();
                String capa = capaField.getText();

                controller.adicionarFilme(titulo, dataLancamento, diretor, nota, genero, capa);
                atualizarGrid();
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nota inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FilmeView view = new FilmeView();
            view.setVisible(true);
        });
    }
}