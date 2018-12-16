package DropMusic.action;
import DropMusicRMI_M.RMIClient;
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


public class LoginAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String username = null, password = null, usernameRegisto = null,passwordRegisto = null;
    private String link = null;
    private String apiUserToken = null;

    public void createLink(){
        if (!session.containsKey("link")){
            this.session.put("link", link);
        }
    }

    public void createApiUserToken(){
        if (!session.containsKey("apiUserToken")){
            this.session.put("apiUserToken", apiUserToken);
        }
    }

    public void createUsername(){
        if(!session.containsKey("username"))
            this.session.put("username",username);
    }

    public void createPassword(){
        if(!session.containsKey("password"))
            this.session.put("password",password);
    }

    public LoginBean getLoginBean() {
        if(!session.containsKey("loginBean"))
            this.session.put("loginBean", new LoginBean());
        return (LoginBean) session.get("loginBean");
    }

    public void createClient(){
        if(!session.containsKey("client")) {
            try {
                RMIClient c;
                this.session.put("client",c = new RMIClient());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public String login() throws RemoteException {
        if(!username.isEmpty() && !password.isEmpty()){
            createClient();
            createUsername();
            createPassword();
            this.getLoginBean().setUsername(username);
            this.getLoginBean().setPassword(password);
            String resposta = this.getLoginBean().login();
            return resposta;
        }
        else{
            return "FAILED";
        }
    }

    public String registar() throws RemoteException{
        if(!usernameRegisto.isEmpty() && !passwordRegisto.isEmpty()){
            createClient();
            createUsername();
            createPassword();
            this.getLoginBean().setUsernameRegisto(usernameRegisto);
            this.getLoginBean().setPasswordRegisto(passwordRegisto);
            String resposta = this.getLoginBean().registo();
            return resposta;
        }
        else{
            return "FAILED";
        }
    }

    public String connectDropbox(){
        if (link==null){
            createLink();
            String resposta = this.getLoginBean().connect();
            return resposta;
        }
        return "FAILED";
    }

    public String saveToken(){
        if (apiUserToken!=null){
            System.out.println("Token is: " + apiUserToken);
            System.out.println("Entered here");
            this.getLoginBean().setApiUserToken(apiUserToken);
            String resposta = this.getLoginBean().checkToken();
            return resposta;
        }
        System.out.println("Did not enter here");
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

    public void setApiUserToken(String apiUserToken){
        this.apiUserToken = apiUserToken;
    }

    public String getApiUserToken(){
        return apiUserToken;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
