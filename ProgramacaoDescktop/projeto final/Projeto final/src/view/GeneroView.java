package view;

import controller.GeneroController;
import model.Genero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneroView extends JFrame {
    private GeneroController generoController;
    private JList<String> generoList;
    private DefaultListModel<String> generoListModel;

    public GeneroView() {
        this.generoController = new GeneroController();
        initComponents();
    }

    private void initComponents() {
        setTitle("Gerenciar Gêneros");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        generoListModel = new DefaultListModel<>();
        atualizarListaGeneros();
        generoList = new JList<>(generoListModel);

        JScrollPane scrollPane = new JScrollPane(generoList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton adicionarButton = new JButton("Adicionar");
        JButton removerButton = new JButton("Remover");

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarGenero();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerGenero();
            }
        });

        botoesPanel.add(adicionarButton);
        botoesPanel.add(removerButton);
        add(botoesPanel, BorderLayout.SOUTH);
    }

    private void atualizarListaGeneros() {
        generoListModel.clear();
        for (Genero genero : generoController.listarGeneros()) {
            generoListModel.addElement(genero.getNome());
        }
    }

    private void adicionarGenero() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do gênero:", "Adicionar Gênero", JOptionPane.QUESTION_MESSAGE);
        if (nome != null && !nome.trim().isEmpty()) {
            generoController.adicionarGenero(nome);
            atualizarListaGeneros();
        } else {
            JOptionPane.showMessageDialog(this, "O nome do gênero não pode ser vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerGenero() {
        int selecionado = generoList.getSelectedIndex();
        if (selecionado != -1) {
            String nome = generoListModel.getElementAt(selecionado);
            generoController.removerGenero(nome);
            atualizarListaGeneros();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um gênero para remover!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
