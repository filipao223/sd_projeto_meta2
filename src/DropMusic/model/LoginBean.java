package DropMusic.model;
import DropMusicRMI_M.RMIInter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

@SuppressWarnings("Duplicates")

public class LoginBean {
    private RMIInter serv;
    private String username, password;
    boolean editor;

    public LoginBean() {
    }

    public RMIInter verificaLookUp() {
        boolean chamada = true;
        while(chamada) {
            try {
                serv =(RMIInter) Naming.lookup("rmi://localhost:7000/benfica");
                chamada = false;
            } catch (MalformedURLException | RemoteException | NotBoundException e) {
                chamada = true;
                e.printStackTrace();
            }
        }
        return serv;
    }

    public int registar() {
        int res = 0;
        boolean chamada = true;
        String resp = "";
        //if(serv != null){
        while(chamada) {
            try{
                serv = verificaLookUp();
                resp = serv.rmi_mc("USER-"+username+"-"+password);
                if(resp.contains("EDITOR") == true)
                    res = 1;
                else if(resp.compareTo("type|status; logged|off; msg|Welcome to DropMusic") == 0)
                    res = 2;
                else if(resp.compareTo("type|status; logged|failed; msg|Username já utilizado") == 0)
                    res = 0;
                chamada = false;
            } catch(RemoteException e) {
                serv = verificaLookUp();
                chamada = true;
            }
        }
        return res;
    }

    public int login() {
        int res = 0;
        boolean chamada = true;
        String resp = "";
        while(chamada) {
            try{
                serv = verificaLookUp();
                resp = serv.rmi_mc("LOGIN-"+username+"-"+password);
                if(resp.contains("type|status; msg|Login com sucesso.") == true)
                    res = 1;
                else if(resp.compareTo("type|status; msg|Utilizador necessita de criar utilizador.") == 0)
                    res = 0;
                else if(resp.compareTo("type|status; msg|User já fez login.") == 0)
                    res = 2;
                chamada = false;
            } catch(RemoteException e) {
                serv = verificaLookUp();
                chamada = true;
            }
        }
        return res;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEditor(boolean aux) {
        this.editor = aux;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean getEditor() {
        return editor;
    }
}
