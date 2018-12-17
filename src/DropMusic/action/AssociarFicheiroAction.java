package DropMusic.action;

import DropMusic.model.AssociarFicheiroBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class AssociarFicheiroAction extends ActionSupport implements SessionAware {

    private Map<String, Object> session;
    private String username;
    private String music;
    private String file;

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

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public void createMusic(){
        if (!session.containsKey("music")){
            this.session.put("music", music);
        }
        else if (session.containsKey("music")){
            this.session.replace("music", music);
        }
    }

    public void createFile(){
        if (!session.containsKey("file")){
            this.session.put("file", file);
        }
        else if (session.containsKey("file")){
            this.session.replace("file", file);
        }
    }

    public AssociarFicheiroBean getAssociarFicheiroBean() {
        if(!session.containsKey("associarFicheiroBean"))
            this.session.put("associarFicheiroBean", new AssociarFicheiroBean());
        return (AssociarFicheiroBean) session.get("associarFicheiroBean");
    }

    public String action(){
        this.username = (String)this.session.get("username");

        String resposta = this.getAssociarFicheiroBean().associar();

        return resposta;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
