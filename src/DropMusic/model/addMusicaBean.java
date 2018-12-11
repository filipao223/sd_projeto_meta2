package DropMusic.model;

import DropMusicRMI_M.RMIInter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

@SuppressWarnings("Duplicates")

public class addMusicaBean {
    private RMIInter serv;
    private String username,nome;

    public addMusicaBean() {
    }

    public RMIInter verificaLookUp() {
        boolean chamada = true;
        while(chamada) {
            try {
                serv =(RMIInter) Naming.lookup("rmi://localhost:7000/benfica");
                chamada = false;
            } catch (MalformedURLException | RemoteException | NotBoundException e) {
                chamada = true;
                e.printStackTrace();
            }
        }
        return serv;
    }

    public int addArtista() {
        int res = 0;
        boolean chamada = true;
        String resp = "";
        //if(serv != null){
        while(chamada) {
            try{
                serv = verificaLookUp();
                resp = serv.rmi_mc("INSERIR-ARTISTA-"+username+"-"+nome);
                if(resp.compareTo("type|status; msg|O artista já existe.") == 0)
                    res = 0;
                else if(resp.compareTo("type|status; msg|Artista inserido com sucesso.") == 0)
                    res = 1;
                chamada = false;
            } catch(RemoteException e) {
                serv = verificaLookUp();
                chamada = true;
            }
        }
        return res;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
