package DropMusic.model;
import DropMusicRMI_M.RMIServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

@SuppressWarnings("Duplicates")

public class addArtistaBean {
    private RMIServer serv;
    private String username,nome;

    public addArtistaBean() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
