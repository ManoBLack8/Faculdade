package view;

import model.Avalicoes;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AvaliacaoView extends JFrame {
    private JPanel mainPanel;
    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnDeletar;
    private JList<Avalicoes> avaliacoesList;
    private List<Avalicoes> avaliacoes;
    private int filmeId; // Adicionado para identificar o filme associado às avaliações

    public AvaliacaoView(List<Avalicoes> avaliacoes, int filmeId) {
        this.avaliacoes = avaliacoes;
        this.filmeId = filmeId;
        initComponents();
    }

    private void initComponents() {
        setTitle("Avaliações do Filme");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new BorderLayout());
        avaliacoesList = new JList<>(avaliacoes.toArray(new Avalicoes[0]));
        avaliacoesList.setCellRenderer(new AvaliacaoCellRenderer());
        mainPanel.add(new JScrollPane(avaliacoesList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnAdicionar = new JButton("Adicionar");
        btnEditar = new JButton("Editar");
        btnDeletar = new JButton("Deletar");
        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnDeletar);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Adiciona listeners aos botões
        btnAdicionar.addActionListener(e -> adicionarAvaliacao());
        btnEditar.addActionListener(e -> editarAvaliacao());
        btnDeletar.addActionListener(e -> deletarAvaliacao());
    }

    private void adicionarAvaliacao() {
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

                // Cria uma nova avaliação
                Avalicoes novaAvaliacao = new Avalicoes();
                novaAvaliacao.setId(avaliacoes.size() + 1); // Gera um ID único (simplificado)
                novaAvaliacao.setNota(nota);
                novaAvaliacao.setComentario(comentario);
                novaAvaliacao.setFilmeId(filmeId); // Associa a avaliação ao filme

                // Adiciona a nova avaliação à lista
                avaliacoes.add(novaAvaliacao);
                avaliacoesList.setListData(avaliacoes.toArray(new Avalicoes[0])); // Atualiza a JList
                JOptionPane.showMessageDialog(this, "Avaliação adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nota inválida! Digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarAvaliacao() {
        Avalicoes selectedAvaliacao = avaliacoesList.getSelectedValue();
        if (selectedAvaliacao == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma avaliação para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField notaField = new JTextField(String.valueOf(selectedAvaliacao.getNota()));
        JTextField comentarioField = new JTextField(selectedAvaliacao.getComentario());

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nota (0 a 10):"));
        panel.add(notaField);
        panel.add(new JLabel("Comentário:"));
        panel.add(comentarioField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Editar Avaliação", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double nota = Double.parseDouble(notaField.getText());
                String comentario = comentarioField.getText();

                if (nota < 0 || nota > 10) {
                    JOptionPane.showMessageDialog(this, "A nota deve estar entre 0 e 10!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                selectedAvaliacao.setNota(nota);
                selectedAvaliacao.setComentario(comentario);
                avaliacoesList.repaint();
                JOptionPane.showMessageDialog(this, "Avaliação editada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nota inválida! Digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deletarAvaliacao() {
        Avalicoes selectedAvaliacao = avaliacoesList.getSelectedValue();
        if (selectedAvaliacao == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma avaliação para deletar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, "Tem certeza que deseja deletar esta avaliação?", "Confirmar Deleção", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            avaliacoes.remove(selectedAvaliacao); // Remove a avaliação da lista
            avaliacoesList.setListData(avaliacoes.toArray(new Avalicoes[0])); // Atualiza a JList
            JOptionPane.showMessageDialog(this, "Avaliação deletada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static class AvaliacaoCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Avalicoes avaliacao = (Avalicoes) value;
            setText("<html><b>Nota:</b> " + avaliacao.getNota() + "<br><b>Comentário:</b> " + avaliacao.getComentario() + "</html>");
            return this;
        }
    }
}