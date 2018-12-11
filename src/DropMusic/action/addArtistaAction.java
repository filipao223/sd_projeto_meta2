package DropMusic.action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;
import DropMusic.model.*;

@SuppressWarnings("Duplicates")

public class addArtistaAction extends ActionSupport implements SessionAware{

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    String username,nome;
    boolean editor;

    public String addArtista(){
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        /*LoginBean b = new LoginBean();
        username = b.getUsername();*/
        username = "ADMIN";
        System.out.printf(username);
        System.out.println(nome);
        if(nome != null && nome != ""){
            this.getaddArtistaBean().setUsername(this.username);
            this.getaddArtistaBean().setNome(this.nome);
            if(this.getaddArtistaBean().addArtista() == 1) {
                session.put("nome", true);
                return "SUCCESS";
            }
            else if(this.getaddArtistaBean().addArtista() == 0){
                session.put("nome", false);
                return "FAILED";
            }
        }
        return "FAILED";
    }

    public addArtistaBean getaddArtistaBean() {
        if(!session.containsKey("addartistaBean"))
            this.setaddArtistaBean(new addArtistaBean());
        return (addArtistaBean) session.get("addartistaBean");
    }

    public void setaddArtistaBean(addArtistaBean addartistabean) {
        this.session.put("addartistaBean",addartistabean);
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
