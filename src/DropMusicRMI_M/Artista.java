package DropMusicRMI_M;

import java.io.Serializable;
import java.util.ArrayList;

public class Artista implements Serializable{
    private static final long serialVersionUID = 1L;
    String nome;
    ArrayList<Album> l_albuns = new ArrayList<>();
    //ArrayList<User> editores = new ArrayList<>();
    //String descricao;
    //String duracao_do_artista;


    public Artista(String nome,User edit){
        this.nome = nome;
        //this.l_albuns = null;
        //this.editores.add(edit);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Album> getL_albuns() {
        return l_albuns;
    }

    public void setL_albuns(ArrayList<Album> l_albuns) {
        this.l_albuns = l_albuns;
    }

    /*public ArrayList<User> getEditores() {
        return editores;
    }

    public void setEditores(ArrayList<User> editores) {
        this.editores = editores;
    }*/
}
