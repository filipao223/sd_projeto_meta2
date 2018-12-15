package DropMusic.action;

import DropMusic.model.removeAlbumBean;
import DropMusic.model.removeArtistaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class removeArtistaAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    public removeArtistaBean getremoveArtistaBean() {
        if(!session.containsKey("removeArtista"))
            this.session.put("removeArtista", new removeArtistaBean());
        return (removeArtistaBean) session.get("removeArtista");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String removeArtista(){
        if(nome.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getremoveArtistaBean().setNome(nome);
        this.getremoveArtistaBean().setUsername((String) this.session.get("username"));
        String resultado = this.getremoveArtistaBean().removeArtista();
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
