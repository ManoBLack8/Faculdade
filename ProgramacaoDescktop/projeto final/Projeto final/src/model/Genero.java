package model;

public class Genero {
    private String nome;

    public Genero(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome; // Exibe o nome do gênero no JComboBox
    }
}