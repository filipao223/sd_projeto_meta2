package DropMusic.action;

import DropMusic.model.addArtistaBean;
import DropMusic.model.pesquisarArtistaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@SuppressWarnings("Duplicates")

public class pesquisarArtistaAction extends ActionSupport implements SessionAware{

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    public pesquisarArtistaBean getpesquisarArtistaBean() {
        if(!session.containsKey("pesquisarArtista"))
            this.session.put("pesquisarArtista", new pesquisarArtistaBean());
        return (pesquisarArtistaBean) session.get("pesquisarArtista");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String pesquisarArtista(){
        if(nome.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getpesquisarArtistaBean().setNome(nome);
        this.getpesquisarArtistaBean().setUsername((String) this.session.get("username"));
        String resultado = this.getpesquisarArtistaBean().pesquisaArtista();
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
