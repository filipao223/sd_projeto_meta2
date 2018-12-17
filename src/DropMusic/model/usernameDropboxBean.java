package DropMusic.model;

import DropMusicRMI_M.RMIClient;
import DropMusicRMI_M.Server;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")

public class usernameDropboxBean {
    private Server h;
    private String username,nome;
    private Map<String, Object> session;

    public String procura() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 30000) { //tem de se tentar conectar durante 30 segundos
            try {
                h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar

                HashMap<String, Object> data = new HashMap<>();
                data.put("feature", "2");
                data.put("username", this.username);
                data.put("action", "33_".concat(this.nome));
                h.receive(data);

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

    /**
     * Cria um hashmap e um cliente, para o servidor, depois de criados, coloca as informações que recebe da action para o pacote e envia para
     * o servidor.
     * Depois disto remove o cliente do servidor(para que não receba mensagens repetidas)
     * Usa-mos um thread sleep, porque devido a usar multithreading no RMI server, o cliente não iria receber a resposta deste rapidamente
     * suficiente
     * @return
     */
    public String usernameDropbox() {
        try {
            Server h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar
            //h.subscribe(this.session.get("username"), this.session.get("client"));
            HashMap<String, Object> data = new HashMap<>();
            RMIClient c = new RMIClient();
            c.setName(this.nome);
            h.subscribe(this.nome, c);
            data.put("feature", "53");
            data.put("username", this.nome);
            h.receive(data);

            h.remove(this.nome, c);

            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Ultimo packote " + c.getLast());

            if(c.getLast().get("answer").equals("User found")){
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

    public void setNome(String nome) {
        this.nome = nome;
    }
}
