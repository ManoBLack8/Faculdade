package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuViewer extends JFrame {

    private JFrame janela;
    private JPanel painelCentral;
    private JButton botaoIniciar;

    public MenuViewer() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Configuração da janela principal
        janela = new JFrame();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setTitle("MENU");
        janela.setSize(500, 500);
        janela.setVisible(true);

        // Configuração do painel central
        painelCentral = new JPanel();
        painelCentral.setBorder(BorderFactory.createTitledBorder("Modo de Jogo"));
        painelCentral.setLayout(new GridLayout(1, 1));

        // Configuração do botão "Iniciar"
        botaoIniciar = new JButton("Iniciar");
        botaoIniciar.setFont(new Font("TimesRoman", Font.BOLD, 40));
        botaoIniciar.addActionListener(this::botaoIniciarClique);

        // Adicionando o botão ao painel central
        painelCentral.add(botaoIniciar);

        // Adicionando o painel central à janela
        janela.add(painelCentral, BorderLayout.CENTER);
    }

    private void botaoIniciarClique(ActionEvent evento) {
        GameViewer jogo = new GameViewer();
        jogo.executar();  
        janela.dispose();
    }

    public void executar() {
        // Método para iniciar a exibição do menu
        janela.setVisible(true);
    }

}