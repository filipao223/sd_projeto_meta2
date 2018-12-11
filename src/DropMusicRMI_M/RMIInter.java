package DropMusicRMI_M;

import java.rmi.*;
import java.util.concurrent.CopyOnWriteArrayList;

import java.net.MulticastSocket;
import java.util.*;

public interface RMIInter extends Remote{
    public String teste() throws java.rmi.RemoteException;
    public boolean test_servers() throws java.rmi.RemoteException;
    public CopyOnWriteArrayList<Online> lista_online() throws java.rmi.RemoteException;;
    public void verificaClient() throws RemoteException;
    public void subscribe(CBInter c,String nome) throws RemoteException;
    public void unsubscribe(String nome) throws RemoteException;
    public void editorCallback(String s) throws java.rmi.RemoteException;

    /*      MULTICAST       */
    public String rmi_mc(String s) throws RemoteException;
    public String rmi_mc_p(String s) throws RemoteException;
    public String rmi_mc_d(String s) throws RemoteException;
    public String rmi_udp(String s) throws RemoteException;
    }