package DropMusic.model;

import DropMusicRMI_M.RMIClient;
import DropMusicRMI_M.RMIServer;
import DropMusicRMI_M.Server;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

public class tornarEditorBean {
    private String username,target;
    private Server h;
    private Map<String, Object> session;


    /**
     * Cria um hashmap e um cliente, para o servidor, depois de criados, coloca as informações que recebe da action para o pacote e envia para
     * o servidor.
     * Depois disto remove o cliente do servidor(para que não receba mensagens repetidas)
     * Usa-mos um thread sleep, porque devido a usar multithreading no RMI server, o cliente não iria receber a resposta deste rapidamente
     * suficientemente rapido resultando num erro
     * Após isto verificamos se a resposta é a de sucesso e retornamos a resposta para a action
     * @return Failed ou Success
     */
    public String partilha(){
        try {
            h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar

            HashMap<String, Object> data = new HashMap<>();
            RMIClient c = new RMIClient();
            c.setName(this.username);
            h.subscribe(this.username, c);
            data.put("feature", "6");
            data.put("username", this.username);
            data.put("newEditor", this.target);

            h.receive(data);

            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Ultimo packote " + c.getLast());

            if(c.getLast().get("answer").equals("Made new editor")){
                h.remove(this.username, c);
                return "SUCCESS";
            }

            return "FAILED";
        } catch (NotBoundException e) {
            return "FAILED";
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
}
