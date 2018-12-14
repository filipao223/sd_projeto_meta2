package DropMusic.action;

import DropMusic.model.addMusicaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;


public class addMusicAction extends ActionSupport implements SessionAware{

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    public addMusicaBean getaddMusicaBean() {
        if(!session.containsKey("addMusica"))
            this.session.put("addMusica", new addMusicaBean());
        return (addMusicaBean) session.get("addMusica");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String addMusic() {
        if(nome == null){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getaddMusicaBean().setNome(nome);
        this.getaddMusicaBean().setUsername((String) this.session.get("username"));
        String resultado = this.getaddMusicaBean().addMusica();
        return resultado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
