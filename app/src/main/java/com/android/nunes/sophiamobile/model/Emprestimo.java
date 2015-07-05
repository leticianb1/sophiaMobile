package com.android.nunes.sophiamobile.model;

/**
 * Created by Letícia on 28/06/2015.
 */
public class Emprestimo {
    String dataDevolucao;
    String dataEmprestimo;
    String livro;
    String valorMulta;
    String imagemLivro;

    public Emprestimo(String livro, String dataDevolucao, String dataEmprestimo, String imagemLivro) {
        this.dataDevolucao = dataDevolucao;
        this.dataEmprestimo = dataEmprestimo;
        this.livro = livro;
        this.imagemLivro = imagemLivro;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(String valorMulta) {
        this.valorMulta = valorMulta;
    }

    public String getImagemLivro() {
        return imagemLivro;
    }

    public void setImagemLivro(String imagemLivro) {
        this.imagemLivro = imagemLivro;
    }
}
