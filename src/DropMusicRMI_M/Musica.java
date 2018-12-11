package DropMusicRMI_M;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Musica implements Serializable {
    private static final long serialVersionUID = 1L;
    String titulo;
    ArrayList <String> compositores = new ArrayList<>();
    String duracao;
    //ArrayList<User> partilhas = new ArrayList<>();
    //boolean publico;
    //String diretoria;

    //File f = null;
    //ArrayList<User> editores = new ArrayList<>();
    /*String historia;
    String editoras;
    String letra;*/


    public Musica(String titulo,ArrayList<String> comp,String duracao/*,User edit*/) {
        this.titulo = titulo;
        this.compositores = comp;
        this.duracao = duracao;
        //this.editores.add(edit);
    }

    public ArrayList<String> getCompositores() {
        return compositores;
    }

    public void setCompositores(ArrayList<String> compositores) {
        this.compositores = compositores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    /*public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getEditoras() {
        return editoras;
    }

    public void setEditoras(String editoras) {
        this.editoras = editoras;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }*/
}
