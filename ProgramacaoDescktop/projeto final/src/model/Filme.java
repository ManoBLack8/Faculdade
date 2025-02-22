package model;

import java.util.Date;

public class Filme {
    
    private int id;
    private String titulo;
    private Date dataLancamento;
    private String diretor;
    private double nota;
    private String genero;
    private String capa; 

    // Construtor
    public Filme(String titulo, Date dataLancamento, String diretor, double nota, String genero) {
        this.titulo = titulo;
        this.dataLancamento = dataLancamento;
        this.diretor = diretor;
        this.nota = nota;
        this.genero = genero;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCapa(){
        return capa;
    }

    public void setCapa(String capa){
        this.capa = capa;
    }    

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", dataLancamento=" + dataLancamento +
                ", diretor='" + diretor + '\'' +
                ", nota=" + nota +
                ", genero='" + genero + '\'' +
                ", capa='" + capa + '\'' +
                '}';
    }
}