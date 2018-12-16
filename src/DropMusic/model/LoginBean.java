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
    private String username, password,usernameRegisto,passwordRegisto;
    private Server h;
    private Map<String, Object> session;

    public LoginBean() {
    }

    public String procuraLogin() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 30000) { //tem de se tentar conectar durante 30 segundos
            try {
                h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar

                HashMap<String, Object> data = new HashMap<>();
                RMIClient c = new RMIClient();
                h.subscribe(this.username, c);

                data.put("feature", "1");
                data.put("username", this.username);
                data.put("password", this.password);
                h.receive(data);

                System.out.println(c.getLast());

                return "SUCCESS";

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
        return "FAILED";
    }

    public String procuraRegisto() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 30000) { //tem de se tentar conectar durante 30 segundos
            try {
                h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar

                HashMap<String, Object> data = new HashMap<>();
                RMIClient c = new RMIClient();
                h.subscribe(this.username, c);
                data.put("feature", "29");
                data.put("username", this.username);
                data.put("password", this.password);
                h.receive(data);

                System.out.println(c.getLast());

                return "SUCCESS";

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
        return "FAILED";
    }

    public String login() throws RemoteException {
        try {
            Server h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar
            //h.subscribe(this.session.get("username"), this.session.get("client"));
            HashMap<String, Object> data = new HashMap<>();
            RMIClient c = new RMIClient();
            c.setName(this.username);
            h.subscribe(this.username, c);

            data.put("feature", "1");
            data.put("username", this.username);
            data.put("password", this.password);

            h.receive(data);

            System.out.println("Ultimo packote " + c.getLast());

            if(c.getLast().get("answer").equals("User logged in")){
                return "SUCCESS";
            }

            return "FAILED";
        } catch (NotBoundException e) {
            procuraLogin();
        }
        return "FAILED";
    }

    public String registo() throws RemoteException{
        try {
            Server h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar
            //h.subscribe(this.session.get("username"), this.session.get("client"));
            HashMap<String, Object> data = new HashMap<>();
            RMIClient c = new RMIClient();
            c.setName(this.username);
            h.subscribe(this.username, c);

            data.put("feature", "29");
            data.put("username", this.usernameRegisto);
            data.put("password", this.passwordRegisto);
            h.receive(data);

            if(c.getLast().get("answer").equals("User registered")){
                return "SUCCESS";
            }
        } catch (NotBoundException e) {
            procuraRegisto();
        }
        return "FAILED";
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsernameRegisto(String usernameRegisto) {
        this.usernameRegisto = usernameRegisto;
    }

    public void setPasswordRegisto(String passwordRegisto) {
        this.passwordRegisto = passwordRegisto;
    }

    public String getUsername() {
        return username;
    }

}
