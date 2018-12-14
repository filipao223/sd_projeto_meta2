package DropMusic.action;

import DropMusic.model.removeArtistaBean;
import DropMusic.model.removeMusicaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class removeMusicaAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    public removeMusicaBean getremoveMusicaBean() {
        if(!session.containsKey("removeMusica"))
            this.session.put("removeMusica", new removeMusicaBean());
        return (removeMusicaBean) session.get("removeMusica");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String removeMusica(){
        if(nome == null){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getremoveMusicaBean().setNome(nome);
        this.getremoveMusicaBean().setUsername((String) this.session.get("username"));
        String resultado = this.getremoveMusicaBean().removeMusica();
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
