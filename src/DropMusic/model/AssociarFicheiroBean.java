package DropMusic.model;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AssociarFicheiroBean implements ServletResponseAware, ServletRequestAware {

    private String username = null;
    private String music = null;
    private String file = null;

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

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setMusic(String music){
        this.music = music;
    }

    public String getMusic(){
        return this.music;
    }

    public void setFile(String file){
        this.file = file;
    }

    public String getFile(){
        return this.file;
    }

    public String associar(){
        //Check if OAuth token exists
        String token = "";
        boolean exists = false;
        this.setServletRequest((HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST));
        for(Cookie c : servletRequest.getCookies()) {
            if (c.getName().equals("token"))
                token = c.getValue();
            exists = true;
        }

        if (!exists) return "FAILED";

        //Check if file exists
        //Create Dropbox API url with file name
        String url = "https://api.dropboxapi.com/2/files/get_metadata";

        System.out.println("Music: " + this.music);
        System.out.println("File: " + this.file);

        try{
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + token);
            con.setRequestProperty("Content-type", "application/json");

            String json = "{\n" +
                    "    \"path\": \"/music1.mp3\",\n" +
                    "    \"include_media_info\": false,\n" +
                    "    \"include_deleted\": false,\n" +
                    "    \"include_has_explicit_shared_members\": false\n" +
                    "}";

            String urlParameters = "" + URLEncoder.encode(json, "UTF-8");
            //String urlParameters = "";

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            return "SUCCESS";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "FAILED";
        } catch (IOException e) {
            e.printStackTrace();
            return "FAILED";
        }
    }
}
