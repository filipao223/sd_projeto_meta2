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

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
