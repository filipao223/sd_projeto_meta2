package DropMusicRMI_M;

import java.io.Serializable;
import java.lang.*;
import java.rmi.RemoteException;

public class Online implements Serializable {
    String nome;
    CBInter inter;

    public Online(String nome, CBInter inter){
        this.nome = nome;
        this.inter = inter;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CBInter getInter() {
        return inter;
    }

    public void setInter(CBInter inter) {
        this.inter = inter;
    }

    public void notifica(){
        try {
            inter.notificaEditor("Agora é editor");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void notifica_d(String nome_album){
        try {
            inter.notificaDescricao("Descrição do album '"+nome_album+"' foi alterada.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}