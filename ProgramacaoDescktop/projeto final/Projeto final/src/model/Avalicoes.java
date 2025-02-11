package model;

public class Avalicoes {
    private int Id;
    private String titulo;
    private double nota;
    private String comentario;
    private int UsuarioId;
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
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
    public int getUsuarioId() {
        return UsuarioId;
    }
    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Avalicao{" +
                "id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", comentario=" + comentario +
                ", nota='" + nota + '\'' +
                ", nota=" + nota +
                ", FilmeId='" + FilmeId + '\'' +
                ", UsuarioId='" + UsuarioId + '\'' +
                '}';
    }

}
