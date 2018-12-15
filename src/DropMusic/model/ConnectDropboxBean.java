package DropMusic.model;

import DropMusic.oauth.DropBoxApi2;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuthService;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class ConnectDropboxBean {

    private String link;
    // Access codes #1: per application used to get access codes #2
    private static final String API_APP_KEY = "er1zepfx26a1vl7";
    private static final String API_APP_SECRET = "1ubrmvq9y3b3oq8";

    // Access codes #2: per user per application
    private static final String API_USER_TOKEN = "";

    public void setLink(String link){
        this.link = link;
    }

    public String getLink(){
        return this.link;
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

        if ( API_USER_TOKEN.equals("") ) {
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
        }

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
        return "FAILED";
    }
}
