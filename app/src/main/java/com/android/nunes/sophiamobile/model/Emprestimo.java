package com.android.nunes.sophiamobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Letícia on 28/06/2015.
 */
public class Emprestimo implements Parcelable {
    String dataDevolucao;
    String dataEmprestimo;
    String livro;
    String valorMulta;
    String imagemLivro;
    String renovavel;

    public Emprestimo(String livro, String dataDevolucao, String dataEmprestimo, String imagemLivro, String valorMulta, String renovavel) {
        this.dataDevolucao = dataDevolucao;
        this.dataEmprestimo = dataEmprestimo;
        this.livro = livro;
        this.imagemLivro = imagemLivro;
        this.valorMulta = valorMulta;
        this.renovavel = renovavel;
    }

    public Emprestimo() {
    }


    public String getRenovavel() {
        return renovavel;
    }

    public void setRenovavel(String renovavel) {
        this.renovavel = renovavel;
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

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Emprestimo> CREATOR = new Creator<Emprestimo>() {
        public Emprestimo createFromParcel(Parcel source) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.livro = source.readString();
            emprestimo.dataDevolucao = source.readString();
            emprestimo.dataEmprestimo = source.readString();
            emprestimo.valorMulta = source.readString();
            emprestimo.imagemLivro = source.readString();
            emprestimo.renovavel = source.readString();
            return emprestimo;
        }

        public Emprestimo[] newArray(int size) {
            return new Emprestimo[size];
        }
    };


            @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(livro);
        dest.writeString(dataDevolucao);
        dest.writeString(dataEmprestimo);
        dest.writeString(valorMulta);
        dest.writeString(imagemLivro);
        dest.writeString(renovavel);
    }
}
