package DropMusic.action;
import DropMusicRMI_M.RMIServer;
import DropMusicRMI_M.Server;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.AccessException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import DropMusic.model.*;

@SuppressWarnings("Duplicates")

public class LoginAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String username = null, password = null;
    private Server h;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Server procura() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 30000) { //tem de se tentar conectar durante 30 segundos
            try {
                h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar
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
        return h;
    }

    public String login(){
        if(username != null && password != null){
            session.put("username",username);
            session.put("password",password);
        }
        else{
            return "FAIL";
        }
        return "FAIL";
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
