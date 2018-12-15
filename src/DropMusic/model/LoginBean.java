package DropMusic.model;
import DropMusic.oauth.DropBoxApi2;
import DropMusicRMI_M.Client;
import DropMusicRMI_M.RMIClient;
import DropMusicRMI_M.RMIServer;
import DropMusicRMI_M.Server;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuthService;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("Duplicates")

public class LoginBean {
    private String username, password,usernameRegisto,passwordRegisto, link;
    private Server h;
    private Map<String, Object> session;

    // Access codes #1: per application used to get access codes #2
    private final String API_APP_KEY = "er1zepfx26a1vl7";
    private final String API_APP_SECRET = "1ubrmvq9y3b3oq8";

    // Access codes #2: per user per application
    private String apiUserToken;

    public LoginBean() {
    }

    public String procuraLogin() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 30000) { //tem de se tentar conectar durante 30 segundos
            try {
                h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar

                HashMap<String, Object> data = new HashMap<>();
                data.put("feature", "1");
                data.put("username", this.username);
                data.put("password", this.password);
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

    public String procuraRegisto() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 30000) { //tem de se tentar conectar durante 30 segundos
            try {
                h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar

                HashMap<String, Object> data = new HashMap<>();
                data.put("feature", "29");
                data.put("username", this.username);
                data.put("password", this.password);
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

    public String login() throws RemoteException {
        try {
            Server h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar
            //h.subscribe(this.session.get("username"), this.session.get("client"));
            HashMap<String, Object> data = new HashMap<>();
            data.put("feature", "1");
            data.put("username", this.username);
            data.put("password", this.password);
            h.receive(data);

            return "SUCCESS";
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
            data.put("feature", "29");
            data.put("username", this.usernameRegisto);
            data.put("password", this.passwordRegisto);
            h.receive(data);

            return "SUCCESS";
        } catch (NotBoundException e) {
            procuraRegisto();
        }
        return "FAILED";
    }

    public String connect(){
        Scanner in = new Scanner(System.in);

        OAuthService service = new ServiceBuilder()
                .provider(DropBoxApi2.class)
                .apiKey(API_APP_KEY)
                .apiSecret(API_APP_SECRET)
                .callback("http://localhost:8081/oauth.jsp")
                .build();

        if (service == null) return "FAILED";

        //try {

        //if ( apiUserToken.equals("") ) {
            link = service.getAuthorizationUrl(null);
            try{
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(URI.create(link));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "SUCCESS";
                /*System.out.println("Authorize scribe here:");
                System.out.println(service.getAuthorizationUrl(null));
                System.out.println("Press enter when done.");
                System.out.print(">>");
                Verifier verifier = new Verifier(in.nextLine());
                Token accessToken = service.getAccessToken(null, verifier);
                System.out.println("Define API_USER_TOKEN: " + accessToken.getToken());
                //System.out.println("Define API_USER_SECRET: " + accessToken.getSecret());
                return "FAILED";*/
       // }

            /*Token accessToken = new Token( API_USER_TOKEN, "");
            return "SUCSESS";

            /*listFiles(service, accessToken);
            addFile("teste.txt", service, accessToken);
            listFiles(service, accessToken);
            deleteFile("teste.txt", service, accessToken);
            listFiles(service, accessToken);


        } catch(OAuthException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }*/
       // return "FAILED";
    }

    public String checkToken(){
        OAuthService service = new ServiceBuilder()
                .provider(DropBoxApi2.class)
                .apiKey(API_APP_KEY)
                .apiSecret(API_APP_SECRET)
                .callback("http://localhost:8081/oauth.jsp")
                .build();
        System.out.println("Token in bean is: " + this.apiUserToken);
        Verifier verifier = new Verifier(this.apiUserToken);
        Token accessToken = service.getAccessToken(null, verifier);
        System.out.println("Define API_USER_TOKEN: " + accessToken.getToken());
        System.out.println("Define API_USER_SECRET: " + accessToken.getSecret());
        return "SUCCESS";
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

    public void setApiUserToken(String apiUserToken){
        this.apiUserToken = apiUserToken;
    }

    public String getApiUserToken(){
        return this.apiUserToken;
    }

}
