import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class App {
    public static void main(String[] args) {
        // Componente JFrame
        JFrame miJFrame = new JFrame("Calculadora");
        miJFrame.setLocationRelativeTo(null);
        miJFrame.setSize(700, 300);
        miJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Componente JPanel
        JPanel miJPanel = new JPanel();
        miJPanel.setLayout(new GridBagLayout());

        // Configuração do layout para posicionar os componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets.set(5, 5, 5, 5); // Margens entre os componentes

        // Componente JLabel para o Número 1
        JLabel miJLabel = new JLabel("Número 1:");
        miJPanel.add(miJLabel, gbc);

        // Componente JTextArea para o Número 1
        gbc.gridy++;
        JTextArea miJTextArea = new JTextArea(1, 20);
        miJPanel.add(miJTextArea, gbc);

        // Componente JLabel para a Operação
        gbc.gridy++;
        JLabel miJLabel2 = new JLabel("Operação:");
        miJPanel.add(miJLabel2, gbc);

        // Componente JComboBox para a Operação
        gbc.gridy++;
        String[] operacoes = { "+", "-", "*", "/" };
        JComboBox<String> c1 = new JComboBox<>(operacoes);
        miJPanel.add(c1, gbc);

        // Componente JLabel para o Número 2
        gbc.gridy++;
        JLabel miJLabel3 = new JLabel("Número 2:");
        miJPanel.add(miJLabel3, gbc);

        // Componente JTextArea para o Número 2
        gbc.gridy++;
        JTextArea miJTextArea3 = new JTextArea(1, 20);
        miJPanel.add(miJTextArea3, gbc);

        // Configurações para adicionar os botões lado a lado
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 1; // Largura de 1 célula para cada botão
        JButton btnResetar = new JButton("Resetar");
        miJPanel.add(btnResetar, gbc);

        gbc.gridx = 1; // Coloca o segundo botão na próxima coluna
        JButton btnCalcular = new JButton("Calcular");
        miJPanel.add(btnCalcular, gbc);

        // Adicionar ações para o botão Calcular
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double num1 = Double.parseDouble(miJTextArea.getText());
                    double num2 = Double.parseDouble(miJTextArea3.getText());
                    String operacao = (String) c1.getSelectedItem();
                    double resultado = realizarOperacao(num1, num2, operacao);

                    JOptionPane.showMessageDialog(miJFrame, "Resultado: " + resultado);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(miJFrame, "Por favor, insira números válidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                } catch (ArithmeticException ex) {
                    JOptionPane.showMessageDialog(miJFrame, "Erro: " + ex.getMessage(), "Erro de Cálculo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ação para o botão Resetar
        btnResetar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miJTextArea.setText("");
                miJTextArea3.setText("");
                c1.setSelectedIndex(0);
            }
        });

        // Conectar JPanel ao JFrame
        miJFrame.add(miJPanel);
        miJFrame.setVisible(true);
    }

    // Método para realizar operações matemáticas
    public static double realizarOperacao(double num1, double num2, String operacao) throws ArithmeticException {
        switch (operacao) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Divisão por zero não permitida.");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Operação inválida.");
        }
    }
}
