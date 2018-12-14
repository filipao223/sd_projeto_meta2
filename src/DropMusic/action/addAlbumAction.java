package DropMusic.action;

import DropMusic.model.addAlbumBean;
import DropMusic.model.addMusicaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@SuppressWarnings("Duplicates")

public class addAlbumAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    public addAlbumBean getaddAlbumBean() {
        if(!session.containsKey("addAlbum"))
            this.session.put("addAlbum", new addAlbumBean());
        return (addAlbumBean) session.get("addAlbum");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String addAlbum() {
        if(nome == null){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getaddAlbumBean().setNome(nome);
        this.getaddAlbumBean().setUsername((String) this.session.get("username"));
        String resultado = this.getaddAlbumBean().addAlbum();
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
