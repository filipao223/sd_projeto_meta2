package DropMusic.action;

import DropMusic.model.addAlbumBean;
import DropMusic.model.removeAlbumBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class removeAlbumAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    public removeAlbumBean getremoveAlbumBean() {
        if(!session.containsKey("removeAlbum"))
            this.session.put("removeAlbum", new removeAlbumBean());
        return (removeAlbumBean) session.get("removeAlbum");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String removeAlbum(){
        if(nome == null){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getremoveAlbumBean().setNome(nome);
        this.getremoveAlbumBean().setUsername((String) this.session.get("username"));
        String resultado = this.getremoveAlbumBean().removeAlbum();
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
