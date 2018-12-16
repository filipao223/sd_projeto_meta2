package DropMusicRMI_M;

import java.rmi.*;
import java.util.HashMap;
import java.util.Map;

public interface Client extends Remote{

	public void print_on_client(Map<String, Object> h) throws RemoteException;

	public String getName() throws RemoteException;

	public void setLast(Map<String, Object> last) throws RemoteException;

	public Map<String, Object> getLast() throws RemoteException;

	public void setName(String name) throws RemoteException;

}