package DropMusicRMI_M;

import java.io.Serializable;

public class Critica implements Serializable{
    private static final long serialVersionUID = 1L;
    User nome_user;
    int pont;
    String desc;

    public Critica(User u, int pont, String desc){
        this.nome_user = u;
        this.pont = pont;
        this.desc = desc;
    }

    public int getPont() {
        return pont;
    }

    public String getDesc() {
        return desc;
    }
}
