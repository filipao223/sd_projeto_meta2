package DropMusic.model;

import DropMusicRMI_M.Server;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

public class escreveCriticaBean {
    private String username,target,critica;
    private Server h;
    private Map<String, Object> session;

    public String procura() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 30000) { //tem de se tentar conectar durante 30 segundos
            try {
                h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar

                HashMap<String, Object> data = new HashMap<>();
                data.put("feature", "5");
                data.put("username", this.username);
                data.put("target", this.target);
                data.put("critique",this.critica);

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

    public String critica(){
        try {
            Server h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar
            //h.subscribe(this.session.get("username"), this.session.get("client"));
            HashMap<String, Object> data = new HashMap<>();
            data.put("feature", "5");
            data.put("username", this.username);
            data.put("target", this.target);
            data.put("critique",this.critica);
            h.receive(data);

            return "SUCCESS";
        } catch (NotBoundException e) {
            procura();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "FAILED";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setCritica(String critica){this.critica = critica;}
}
