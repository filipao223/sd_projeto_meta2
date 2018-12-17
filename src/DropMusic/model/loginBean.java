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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

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

public class loginBean implements ServletResponseAware, ServletRequestAware {
    private String username, password,usernameRegisto,passwordRegisto, link;
    private Server h;
    private Map<String, Object> session;

    // Access codes #1: per application used to get access codes #2
    private final String API_APP_KEY = "er1zepfx26a1vl7";
    private final String API_APP_SECRET = "1ubrmvq9y3b3oq8";

    // Access codes #2: per user per application
    private String apiUserToken;
    private String url;
    private String code;

    /**
     * Cria um hashmap e um cliente, para o servidor, depois de criados, coloca as informações que recebe da action para o pacote e envia para
     * o servidor.
     * Depois disto remove o cliente do servidor(para que não receba mensagens repetidas)
     * Usa-mos um thread sleep, porque devido a usar multithreading no RMI server, o cliente não iria receber a resposta deste rapidamente
     * suficientemente rapido resultando num erro
     * Após isto verificamos se a resposta é a de sucesso e retornamos a resposta para a action
     * @return Failed ou Success
     */
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


            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Ultimo packote " + c.getLast());

            if(c.getLast().get("answer").equals("User logged in") || c.getLast().get("answer").equals("User already logged in")){
                return "SUCCESS";
            }

            return "FAILED";
        } catch (NotBoundException e) {
            return "FAILED";
        }
    }

    /**
     * Cria um hashmap e um cliente, para o servidor, depois de criados, coloca as informações que recebe da action para o pacote e envia para
     * o servidor.
     * Depois disto remove o cliente do servidor(para que não receba mensagens repetidas)
     * Usa-mos um thread sleep, porque devido a usar multithreading no RMI server, o cliente não iria receber a resposta deste rapidamente
     * suficientemente rapido resultando num erro
     * Após isto verificamos se a resposta é a de sucesso e retornamos a resposta para a action
     * @return Failed ou Success
     */
    public String registo() throws RemoteException{
        try {
            Server h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar
            //h.subscribe(this.session.get("username"), this.session.get("client"));
            HashMap<String, Object> data = new HashMap<>();
            RMIClient c = new RMIClient();
            c.setName(this.usernameRegisto);
            h.subscribe(this.usernameRegisto, c);

            data.put("feature", "29");
            data.put("username", this.usernameRegisto);
            data.put("password", this.passwordRegisto);
            h.receive(data);

            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Ultimo packote " + c.getLast());

            if(c.getLast().get("answer").equals("User registered")){
                return "SUCCESS";
            }

        } catch (NotBoundException e) {
            return "FAILED";
        }
        return "FAILED";
    }

    public String connect(){
        Scanner in = new Scanner(System.in);

        //Check if there is a cookie
        this.setServletRequest((HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST));
        for(Cookie c : servletRequest.getCookies()) {
            if (c.getName().equals("token"))
                //Expire the cookie
                c.setMaxAge(0);
        }

        OAuthService service = new ServiceBuilder()
                .provider(DropBoxApi2.class)
                .apiKey(API_APP_KEY)
                .apiSecret(API_APP_SECRET)
                .callback("http://localhost:8081/saveToken")
                .build();

        if (service == null) return "FAILED";

        link = service.getAuthorizationUrl(null);
        try{
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(URI.create(link));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    public String connectLogin(){
        Scanner in = new Scanner(System.in);

        //Check if there is a cookie
        this.setServletRequest((HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST));
        for(Cookie c : servletRequest.getCookies()) {
            if (c.getName().equals("token"))
                //Expire the cookie
                c.setMaxAge(0);
        }

        OAuthService service = new ServiceBuilder()
                .provider(DropBoxApi2.class)
                .apiKey(API_APP_KEY)
                .apiSecret(API_APP_SECRET)
                .callback("http://localhost:8081/loginDropbox")
                .build();

        if (service == null) return "FAILED";

        link = service.getAuthorizationUrl(null);
        try{
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(URI.create(link));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    public String checkToken(){
        OAuthService service = new ServiceBuilder()
                .provider(DropBoxApi2.class)
                .apiKey(API_APP_KEY)
                .apiSecret(API_APP_SECRET)
                .callback("http://localhost:8081/saveToken")
                .build();
        System.out.println("Token in bean is: " + this.code);
        Verifier verifier = new Verifier(this.code);
        Token accessToken = service.getAccessToken(null, verifier);
        if (accessToken != null){
            System.out.println("Define API_USER_TOKEN: " + accessToken.getToken());
            System.out.println("Define API_USER_SECRET: " + accessToken.getSecret());

            //Save in cookies
            this.setServletResponse((HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE));
            Cookie token = new Cookie("token", accessToken.getToken());
            token.setMaxAge(60*60*24); // Make the cookie last a day
            servletResponse.addCookie(token);

            try {
                Server h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar
                HashMap<String, Object> data = new HashMap<>();
                data.put("feature", "71");
                data.put("username", this.username);
                data.put("token", new String(String.valueOf(token)));

                h.receive(data);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }

            return "redirect";
        }
        return "FAILED";
    }

    public String checkTokenLogin(){
        OAuthService service = new ServiceBuilder()
                .provider(DropBoxApi2.class)
                .apiKey(API_APP_KEY)
                .apiSecret(API_APP_SECRET)
                .callback("http://localhost:8081/loginDropbox")
                .build();
        System.out.println("Token in bean is: " + this.code);
        Verifier verifier = new Verifier(this.code);
        Token accessToken = service.getAccessToken(null, verifier);
        if (accessToken != null){
            System.out.println("Define API_USER_TOKEN: " + accessToken.getToken());
            System.out.println("Define API_USER_SECRET: " + accessToken.getSecret());

            //Save in cookies
            this.setServletResponse((HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE));
            Cookie token = new Cookie("token", accessToken.getToken());
            token.setMaxAge(60*60*24); // Make the cookie last a day
            servletResponse.addCookie(token);

            try {
                Server h = (Server) LocateRegistry.getRegistry(1099).lookup("MainServer"); //procura server para conectar
                HashMap<String, Object> data = new HashMap<>();
                data.put("feature", "71");
                data.put("username", this.username);
                data.put("token", new String(String.valueOf(token)));

                h.receive(data);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }

            return "SUCCESS";
        }
        return "FAILED";
    }

    private HttpServletResponse servletResponse;
    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    protected HttpServletRequest servletRequest;
    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
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

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(String url){
        return this.url;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

}