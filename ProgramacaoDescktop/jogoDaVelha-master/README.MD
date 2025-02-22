# Jogo da Velha - GameViewer

O **GameViewer** é uma aplicação Java que implementa o clássico jogo da velha (Tic-Tac-Toe) com uma interface gráfica simples e funcionalidades adicionais, como histórico de partidas e placar.

## Funcionalidades

- **Interface Gráfica**: Jogue o jogo da velha em uma interface amigável e intuitiva.
- **Placar**: Acompanhe o número de vitórias de cada jogador.
- **Histórico de Partidas**: Visualize o histórico de todas as partidas jogadas, incluindo data, hora e resultado.
- **Novo Jogo**: Reinicie o jogo a qualquer momento, alternando o jogador inicial.
- **Persistência de Dados**: O histórico de partidas é salvo em um arquivo JSON (`historico.json`), permitindo que os dados sejam mantidos entre execuções do programa.

## Como Executar

1. **Pré-requisitos**:
   - Java Development Kit (JDK) instalado.
   - Biblioteca Gson para manipulação de JSON (disponível no Maven ou como `.jar`).

2. **Compilação**:
   - Compile o projeto usando o comando:
     ```bash
     javac -cp .:gson-2.8.6.jar view/GameViewer.java
     ```

3. **Execução**:
   - Execute o programa com o comando:
     ```bash
     java -cp .:gson-2.8.6.jar view.GameViewer
     ```

## Estrutura do Projeto

- **`GameViewer.java`**: Classe principal que contém a interface gráfica e a lógica do jogo.
- **`model/Game.java`**: Classe que gerencia a lógica do jogo, como o estado do tabuleiro e a verificação de vencedores.
- **`model/Pos.java`**: Classe auxiliar para representar posições no tabuleiro.
- **`util/Util.java`**: Classe utilitária com métodos auxiliares, como conversão de índices.
- **`historico.json`**: Arquivo JSON que armazena o histórico de partidas.

## Como Jogar

1. **Iniciar o Jogo**:
   - Ao executar o programa, a interface gráfica será exibida com o tabuleiro vazio.
   - O jogador atual é indicado na parte superior da tela.

2. **Fazer uma Jogada**:
   - Clique em qualquer célula vazia do tabuleiro para fazer sua jogada.
   - O símbolo do jogador atual será exibido na célula clicada.

3. **Verificar Resultado**:
   - O jogo verifica automaticamente se há um vencedor ou se o tabuleiro está cheio (empate).
   - O placar é atualizado conforme o resultado da partida.

4. **Novo Jogo**:
   - Clique no botão "Novo Jogo" para reiniciar o tabuleiro e alternar o jogador inicial.

5. **Histórico de Partidas**:
   - Clique no botão "Ver Histórico" para visualizar todas as partidas jogadas, incluindo data, hora e resultado.

## Exemplo de Uso

- **Jogador 1 (X)** e **Jogador 2 (O)** alternam suas jogadas até que haja um vencedor ou empate.
- O histórico de partidas é salvo automaticamente e pode ser consultado a qualquer momento.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests para melhorias, correções de bugs ou novas funcionalidades.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---

**Divirta-se jogando!** 🎮