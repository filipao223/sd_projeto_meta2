package DropMusic.action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;
import DropMusic.model.*;

@SuppressWarnings("Duplicates")

public class addArtistaAction extends ActionSupport implements SessionAware{

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    public addArtistaBean getaddArtistaBean() {
        if(!session.containsKey("addArtista"))
            this.session.put("addArtista", new addArtistaBean());
        return (addArtistaBean) session.get("addArtista");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String addArtista(){
        if(nome == null){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getaddArtistaBean().setNome(nome);
        this.getaddArtistaBean().setUsername((String) this.session.get("username"));
        String resultado = this.getaddArtistaBean().addArtista();
        return resultado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}
