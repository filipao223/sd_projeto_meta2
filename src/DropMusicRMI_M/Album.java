package DropMusicRMI_M;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private static final long serialVersionUID = 1L;
    String titulo;
    ArrayList<Musica> l_musicas = new ArrayList<>();
    String descricao;
    ArrayList<Critica> criticas = new ArrayList<>();
    ArrayList<User> editores = new ArrayList<>();//da descricao
    /*String info_g_musical;
    String data_lanc;*/

    public Album(String titulo, String d,User edit) {
        this.titulo = titulo;
        this.descricao = d;
        this.editores.add(edit);
        /*this.info_g_musical = info_g_musical;
        this.data_lanc = data_lanc;*/
    }

    public ArrayList<User> getEditores() {
        return editores;
    }

    public void setEditores(ArrayList<User> editores) {
        this.editores = editores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<Musica> getL_Musicas() {
        return l_musicas;
    }

    public void setL_Musicas(ArrayList<Musica> musicas) {
        this.l_musicas = musicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Critica> getCriticas() {
        return criticas;
    }

    public void setCriticas(ArrayList<Critica> criticas) {
        this.criticas = criticas;
    }

    /*public String getInfo_g_musical() {
        return info_g_musical;
    }

    public void setInfo_g_musical(String info_g_musical) {
        this.info_g_musical = info_g_musical;
    }

    public String getData_lanc() {
        return data_lanc;
    }

    public void setData_lanc(String data_lanc) {
        this.data_lanc = data_lanc;
    }*/
}
