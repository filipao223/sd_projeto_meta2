package DropMusic.action;

import DropMusic.model.ConnectDropboxBean;
import DropMusic.oauth.DropBoxApi2;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuthService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;
import java.util.Scanner;

public class ConnectDropboxAction extends ActionSupport implements SessionAware {

    private String link = null;
    private Map<String, Object> session;

    public void createLink(){
        if (!session.containsKey("link")){
            this.session.put("link", link);
        }
    }

    public ConnectDropboxBean getConnectDropboxBean(){
        if (!session.containsKey("connectDropboxBean")){
            this.session.put("connectDropboxBean", new ConnectDropboxBean());
        }
        return (ConnectDropboxBean) session.get("connectDropboxBean");
    }

    public String connect(){
        if (link==null){
            createLink();
            String resposta = this.getConnectDropboxBean().connect();
            return resposta;
        }
        return "FAILED";
    }

    public String getLink(){
        return link;
    }

    public void setLink(String link){
        this.link = link;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
