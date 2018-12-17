package DropMusic.model;

import DropMusicRMI_M.RMIClient;
import DropMusicRMI_M.Server;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

public class escreveCriticaBean {
    private String username,target,critica,rating;
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
            RMIClient c = new RMIClient();
            c.setName(this.username);
            h.subscribe(this.username, c);
            data.put("feature", "5");
            data.put("username", this.username);
            data.put("album", this.target);
            data.put("text",this.critica);
            data.put("rating",this.rating);
            h.receive(data);

            h.remove(this.username, c);

            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(c.getLast().get("answer").equals("Published critique")){
                return "SUCCESS";
            }

            return "FAILED";
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
