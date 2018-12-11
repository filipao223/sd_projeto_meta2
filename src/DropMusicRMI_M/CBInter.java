package DropMusicRMI_M;

import java.rmi.*;

public interface CBInter extends Remote{
    //public void print_on_client(String s) throws java.rmi.RemoteException;
    public void notificaEditor(String s) throws java.rmi.RemoteException;
    public void notificaDescricao(String nome_album) throws java.rmi.RemoteException;
}

