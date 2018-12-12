package DropMusicRMI_M;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Server extends Remote {

	public void receive(HashMap h) throws RemoteException;

	public void subscribe(String name, Client client) throws RemoteException;

}