package model;

public class Avalicoes {
    private int Id;
    private double nota;
    private String comentario;
    private int FilmeId;

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public int getFilmeId() {
        return FilmeId;
    }
    public void setFilmeId(int filmeId) {
        FilmeId = filmeId;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    @Override
    public String toString() {
        return "Avalicao{" +
                "id=" + Id +
                ", comentario=" + comentario +
                ", nota='" + nota + '\'' +
                ", FilmeId='" + FilmeId + '\'' +
                '}';
    }

}
