package view;

import controller.FilmeController;
import dao.GeneroDAO;
import model.Avalicoes;
import model.Filme;
import model.Genero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FilmeView extends JFrame {
    private FilmeController controller;
    private GeneroDAO generoDAO;
    private JPanel gridPanel;

    public FilmeView() {
        this.controller = new FilmeController();
        this.generoDAO = new GeneroDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("CRUD de Filmes e Avaliações");
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

        // Menu Gênero
        JMenu generoMenu = new JMenu("Gênero");
        JMenuItem gerenciarGenerosItem = new JMenuItem("Gerenciar Gêneros");
        gerenciarGenerosItem.addActionListener(e -> abrirGeneroView());
        generoMenu.add(gerenciarGenerosItem);
        menuBar.add(generoMenu);

        setJMenuBar(menuBar);

        // Painel principal com grid de filmes
        gridPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 colunas, espaçamento de 10px
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(gridPanel), BorderLayout.CENTER);

        // Carrega os filmes ao iniciar
        carregarDoJson();
    }

    private void editarFilme() {
        // Solicita o título do filme a ser editado
        String tituloBusca = JOptionPane.showInputDialog(this,
                "Digite o título do filme para buscar:",
                "Buscar Filme", JOptionPane.QUESTION_MESSAGE);
    
        if (tituloBusca == null || tituloBusca.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Título inválido! A busca foi cancelada.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Busca o filme pelo título
        Filme filmeParaEditar = controller.buscarFilmePorTitulo(tituloBusca);
        if (filmeParaEditar == null) {
            JOptionPane.showMessageDialog(this,
                    "Filme não encontrado!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Cria os campos de edição com os dados atuais do filme
        JTextField tituloField = new JTextField(filmeParaEditar.getTitulo());
        JTextField dataField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(filmeParaEditar.getDataLancamento()));
        JTextField diretorField = new JTextField(filmeParaEditar.getDiretor());
        JTextField notaField = new JTextField(String.valueOf(filmeParaEditar.getNota()));
    
        // Cria um JComboBox para selecionar o gênero
        JComboBox<String> generoComboBox = new JComboBox<>();
        for (Genero genero : generoDAO.listarGeneros()) {
            generoComboBox.addItem(genero.getNome()); // Adiciona o nome do gênero
        }
    
        // Painel para editar os campos
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Título:"));
        panel.add(tituloField);
        panel.add(new JLabel("Data de Lançamento (dd/MM/yyyy):"));
        panel.add(dataField);
        panel.add(new JLabel("Diretor:"));
        panel.add(diretorField);
        panel.add(new JLabel("Nota:"));
        panel.add(notaField);
        panel.add(new JLabel("Gênero:"));
        panel.add(generoComboBox);
    
        int result = JOptionPane.showConfirmDialog(
                this, panel, "Editar Filme", JOptionPane.OK_CANCEL_OPTION);
    
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Obtém os novos valores dos campos
                String novoTitulo = tituloField.getText();
                Date novaDataLancamento = new SimpleDateFormat("dd/MM/yyyy").parse(dataField.getText());
                String novoDiretor = diretorField.getText();
                double novaNota = Double.parseDouble(notaField.getText());
                String novoGenero = (String) generoComboBox.getSelectedItem();
    
                // Atualiza o filme no controller
                controller.atualizarFilme(filmeParaEditar.getId(), novoTitulo, novaDataLancamento, novoDiretor, novaNota, novoGenero);
                atualizarGrid(); // Atualiza a exibição dos filmes
                JOptionPane.showMessageDialog(this, "Filme editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nota inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirGeneroView() {
        GeneroView generoView = new GeneroView();
        generoView.setVisible(true);
    }

    private void salvarNoJson() {
        controller.adicionarFilme(getTitle(), null, getWarningString(), DO_NOTHING_ON_CLOSE, getName());
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
                    abrirAvaliacoesDoFilme(filme); // Abre a view de avaliações do filme
                }
            });
            gridPanel.add(card);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void abrirAvaliacoesDoFilme(Filme filme) {
        List<Avalicoes> avaliacoes = controller.abrirAvaliacoesDoFilme(filme.getId());
        AvaliacaoView avaliacaoView = new AvaliacaoView(avaliacoes, filme.getId());
        avaliacaoView.setVisible(true);
    }

    private JPanel criarCardFilme(Filme filme) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(Color.WHITE);

        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.add(new JLabel("Título: " + filme.getTitulo()));
        infoPanel.add(new JLabel("Diretor: " + filme.getDiretor()));
        infoPanel.add(new JLabel("Data: " + new SimpleDateFormat("dd/MM/yyyy").format(filme.getDataLancamento())));
        infoPanel.add(new JLabel("Nota: " + filme.getNota()));
        infoPanel.add(new JLabel("Gênero: " + filme.getGenero()));
        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

    private void adicionarFilme() {
        JTextField tituloField = new JTextField();
        JTextField dataField = new JTextField();
        JTextField diretorField = new JTextField();
        JTextField notaField = new JTextField();

        // Cria um JComboBox para selecionar o gênero
        JComboBox<Genero> generoComboBox = new JComboBox<>();
        for (Genero genero : generoDAO.listarGeneros()) {
            generoComboBox.addItem(genero);
        }

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Título:"));
        panel.add(tituloField);
        panel.add(new JLabel("Data de Lançamento (dd/MM/yyyy):"));
        panel.add(dataField);
        panel.add(new JLabel("Diretor:"));
        panel.add(diretorField);
        panel.add(new JLabel("Nota:"));
        panel.add(notaField);
        panel.add(new JLabel("Gênero:"));
        panel.add(generoComboBox);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Adicionar Filme", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String titulo = tituloField.getText();
                Date dataLancamento = new SimpleDateFormat("dd/MM/yyyy").parse(dataField.getText());
                String diretor = diretorField.getText();
                double nota = Double.parseDouble(notaField.getText());
                Genero genero = (Genero) generoComboBox.getSelectedItem();

                controller.adicionarFilme(titulo, dataLancamento, diretor, nota, genero.getNome());
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