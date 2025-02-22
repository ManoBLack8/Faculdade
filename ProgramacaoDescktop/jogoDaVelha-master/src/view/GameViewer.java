package view;

import model.Game;
import model.Pos;
import util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GameViewer extends JFrame {


    public static final String JOGADOR_1_SIMBOLO = "X";
    public static final String JOGADOR_2_SIMBOLO = "O";
    public static final String VAZIO_SIMBOLO = " ";
    private static final String HISTORICO_ARQUIVO = "historico.json";

    private JFrame janela;
    private ArrayList<JButton> botoesTabuleiro = new ArrayList<>();
    private JLabel labelJogadorAtual;
    private JLabel labelPlacar;

    private int vitoriasJogador1 = 0;
    private int vitoriasJogador2 = 0;
    private int jogadorInicial = Game.PLAYER_1;

    private Clip victorySound; // Adicionado para reproduzir o som de vitória

    private Game jogo;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public GameViewer() {
        this.jogo = new Game();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        janela = new JFrame();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setTitle("Jogo da Velha");
        janela.setSize(500, 500);
        janela.setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        JPanel painelCentral = new JPanel();
        JPanel painelInferior = new JPanel();

        janela.add(painelSuperior, BorderLayout.NORTH);
        janela.add(painelCentral, BorderLayout.CENTER);
        janela.add(painelInferior, BorderLayout.SOUTH);

        painelSuperior.setBorder(BorderFactory.createTitledBorder("Status"));
        painelCentral.setBorder(BorderFactory.createTitledBorder("Tabuleiro"));
        painelInferior.setBorder(BorderFactory.createTitledBorder("Placar"));

        renderizarStatus(painelSuperior);
        renderizarTabuleiro(painelCentral);
        renderizarPlacar(painelInferior);
        renderizarBotaoHistorico(painelInferior);

        atualizarTabuleiro();
    }

    private void carregarSom() {
        try {
            // Carrega o arquivo de som
            File soundFile = new File("../victorias.mp3");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            victorySound = AudioSystem.getClip();
            victorySound.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(janela, "Erro ao carregar o som de vitória.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void renderizarBotaoHistorico(JPanel painel) {
        JButton botaoHistorico = new JButton("Ver Histórico");
        botaoHistorico.addActionListener(this::botaoHistoricoClique);
        painel.add(botaoHistorico);
    }

    private void renderizarStatus(JPanel painelStatus) {
        labelJogadorAtual = new JLabel("Vez do Jogador: " + decodificarSimboloJogador(jogo.getCurrentPlayer()));
        painelStatus.add(labelJogadorAtual);

        JButton botaoNovoJogo = new JButton("Novo Jogo");
        botaoNovoJogo.addActionListener(this::botaoNovoJogoClique);
        painelStatus.add(botaoNovoJogo);
    }

    private void renderizarTabuleiro(JPanel painelTabuleiro) {
        painelTabuleiro.setLayout(new GridLayout(3, 3));

        Font fonte = new Font("TimesRoman", Font.BOLD, 40);

        for (int i = 0; i < 9; i++) {
            JButton botao = new JButton(VAZIO_SIMBOLO);
            botao.setFont(fonte);
            botao.addActionListener(this::botaoTabuleiroClique);
            painelTabuleiro.add(botao);
            botoesTabuleiro.add(botao);
        }
    }

    private void renderizarPlacar(JPanel painelPlacar) {
        labelPlacar = new JLabel("Placar - Jogador 1: 0 | Jogador 2: 0");
        painelPlacar.add(labelPlacar);
    }

    private void atualizarPlacar() {
        labelPlacar.setText("Placar - Jogador 1: " + vitoriasJogador1 + " | Jogador 2: " + vitoriasJogador2);
    }


    private void verificarVencedor() {
        int vencedor = jogo.getWinner();
        String resultado;

        if (vencedor == Game.PLAYER_1) {
            vitoriasJogador1++;
            resultado = "Vencedor: " + JOGADOR_1_SIMBOLO;
        } else if (vencedor == Game.PLAYER_2) {
            vitoriasJogador2++;
            resultado = "Vencedor: " + JOGADOR_2_SIMBOLO;
        } else if (isBoardFull()) {
            resultado = "Deu velha!";
        } else {
            return;
        }

        labelJogadorAtual.setText(resultado);
        atualizarPlacar();
        desativarBotoes();
        salvarHistorico(resultado);

        // Reproduz o som de vitória
        if (victorySound != null) {
            victorySound.setFramePosition(0); // Reinicia o som
            victorySound.start(); // Reproduz o som
        }

        if (vitoriasJogador1 == 5 || vitoriasJogador2 == 5) {
            JOptionPane.showMessageDialog(janela, "Vitória do Jogo! Reiniciando o placar.");
            carregarSom();
            vitoriasJogador1 = 0;
            vitoriasJogador2 = 0;
            atualizarPlacar();
        }
    }

    private void atualizarTabuleiro() {
        for (int i = 0; i < 9; i++) {
            Pos pos = Util.singleToPos(i);
            botoesTabuleiro.get(i).setText(decodificarSimboloJogador(jogo.getBoard()[pos.x][pos.y]));
        }

        labelJogadorAtual.setText("Vez do Jogador: " + decodificarSimboloJogador(jogo.getCurrentPlayer()));
    }

    private String decodificarSimboloJogador(int jogador) {
        switch (jogador) {
            case Game.PLAYER_1:
                return JOGADOR_1_SIMBOLO;
            case Game.NO_PLAYER:
                return VAZIO_SIMBOLO;
            case Game.PLAYER_2:
                return JOGADOR_2_SIMBOLO;
            default:
                return "Erro";
        }
    }

    private void botaoTabuleiroClique(ActionEvent evento) {
        JButton botaoClicado = (JButton) evento.getSource();
        int indice = botoesTabuleiro.indexOf(botaoClicado);
        Pos pos = Util.singleToPos(indice);

        if (jogo.play(pos.x, pos.y)) {
            atualizarTabuleiro();
            verificarVencedor();
        }
        atualizarTabuleiro();
    } 


    private boolean isBoardFull() {
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                if (jogo.getBoard()[i][j] == Game.NO_PLAYER) {
                    return false;
                }
            }
        }
        return true;
    }

    private void desativarBotoes() {
        for (JButton botao : botoesTabuleiro) {
            botao.setEnabled(false);
        }
    }

    private void botaoHistoricoClique(ActionEvent evento) {
        try {
            if (!Files.exists(Paths.get(HISTORICO_ARQUIVO))) {
                JOptionPane.showMessageDialog(janela, "Nenhum histórico encontrado.");
                return;
            }

            JsonElement elemento = gson.fromJson(new FileReader(HISTORICO_ARQUIVO), JsonElement.class);
            if (elemento == null || !elemento.isJsonArray()) {
                JOptionPane.showMessageDialog(janela, "Formato inválido do histórico.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JsonArray historico = elemento.getAsJsonArray();
            StringBuilder historicoTexto = new StringBuilder("Histórico de Partidas:\n\n");

            for (JsonElement partidaElemento : historico) {
                if (partidaElemento.isJsonObject()) {
                    JsonObject partida = partidaElemento.getAsJsonObject();
                    historicoTexto.append(partida.get("data_hora").getAsString())
                            .append(" - ")
                            .append(partida.get("resultado").getAsString())
                            .append("\n");
                }
            }

            JOptionPane.showMessageDialog(janela, historicoTexto.toString(), "Histórico de Partidas", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(janela, "Erro ao carregar o histórico.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void salvarHistorico(String resultado) {
        JsonArray historico = new JsonArray();

        // Verifica se o arquivo já existe e tenta carregar os dados anteriores
        try (FileReader reader = new FileReader(HISTORICO_ARQUIVO)) {
            JsonElement elemento = gson.fromJson(reader, JsonElement.class);

            if (elemento != null && elemento.isJsonArray()) {
                historico = elemento.getAsJsonArray(); // Usa o array existente
            }
        } catch (IOException e) {
            System.out.println("Arquivo histórico não encontrado. Criando um novo.");
        }

        // Criar um novo objeto de partida
        JsonObject partida = new JsonObject();
        partida.addProperty("data_hora", LocalDateTime.now().toString());
        partida.addProperty("jogador_1", JOGADOR_1_SIMBOLO);
        partida.addProperty("jogador_2", JOGADOR_2_SIMBOLO);
        partida.addProperty("resultado", resultado);

        // Adiciona a nova partida ao histórico
        historico.add(partida);

        // Salva o histórico atualizado no arquivo
        try (FileWriter writer = new FileWriter(HISTORICO_ARQUIVO)) {
            gson.toJson(historico, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void botaoNovoJogoClique(ActionEvent evento) {
        jogadorInicial = (jogadorInicial == Game.PLAYER_1) ? Game.PLAYER_2 : Game.PLAYER_1;
        jogo.reset();
        jogo.setCurrentPlayer(jogadorInicial);
        atualizarTabuleiro();
        for (JButton botao : botoesTabuleiro) {
            botao.setEnabled(true);
        }
    }

    public void executar() {
        janela.setVisible(true);
    }
}
