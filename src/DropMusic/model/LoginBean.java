package DropMusic.model;
import DropMusicRMI_M.Client;
import DropMusicRMI_M.RMIClient;
import DropMusicRMI_M.RMIServer;
import DropMusicRMI_M.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")

public class LoginBean {
    private RMIServer serv;
    private String username, password;
    private Server h;
    private Map<String, Object> session;

    public LoginBean() {
    }

    public String procura() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 30000) { //tem de se tentar conectar durante 30 segundos
            try {
                h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar

                HashMap<String, Object> data = new HashMap<>();
                data.put("username", this.username);
                data.put("password", this.password);
                h.receive(data);

                return "SUCESS";

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            if(System.currentTimeMillis() >= time + 30000){ //no fim dos 30 segundos a coneção não é possível
                System.out.println("Não existe coneção");
                break;
            }
        }
        return "FAIL";
    }

    public String login() throws RemoteException {
        try {
            Server h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar
            //h.subscribe(this.session.get("username"), this.session.get("client"));
            HashMap<String, Object> data = new HashMap<>();
            data.put("username", this.username);
            data.put("password", this.password);
            h.receive(data);

            return "SUCCESS";
        } catch (NotBoundException e) {
            procura();
        }
        return "FAIL";
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

}
