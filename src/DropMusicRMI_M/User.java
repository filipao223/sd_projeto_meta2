package DropMusicRMI_M;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    String nome;
    String pass;
    int editor;//0 - nao é; 1 - é
    ArrayList<String> notificacoes = new ArrayList<>();

    public User(String nome, String pass) {
        this.nome = nome;
        this.pass = pass;
        this.editor = 0;
        this.notificacoes = null;
    }

    public ArrayList<String> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(ArrayList<String> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public String getNome() {
        return nome;
    }

    public void setEditor(int editor) {
        this.editor = editor;
    }

    public int getEditor() {
        return editor;
    }

    public String getPass() {
        return pass;
    }
}
