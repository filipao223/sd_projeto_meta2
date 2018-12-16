package DropMusic.action;

import DropMusic.model.consultaBean;
import DropMusic.model.pesquisarAlbumBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@SuppressWarnings("Duplicates")

public class pesquisarAlbumAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome,nomeArtista,tipo,request;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }

    public pesquisarAlbumBean getpesquisarAlbumBean() {
        if(!session.containsKey("pesquisarAlbum"))
            this.session.put("pesquisarAlbum", new pesquisarAlbumBean());
        return (pesquisarAlbumBean) session.get("pesquisarAlbum");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String pesquisarAlbum() {

        String resultado = "FAILED";
        if(nome.isEmpty() && nomeArtista.isEmpty() && tipo.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        if(!nomeArtista.isEmpty()){
            String request = "26_40_".concat(nomeArtista);
            this.getpesquisarAlbumBean().setRequest(request);
            this.getpesquisarAlbumBean().setUsername((String) this.session.get("username"));
            resultado = this.getpesquisarAlbumBean().pesquisaAlbum();
            return resultado;
        }
        if(!nome.isEmpty()){
            String request = "26_39_".concat(nome);
            this.getpesquisarAlbumBean().setRequest(request);
            this.getpesquisarAlbumBean().setUsername((String) this.session.get("username"));
            resultado = this.getpesquisarAlbumBean().pesquisaAlbum();
            return resultado;
        }
        if(!tipo.isEmpty()){
            String request = "26_41_".concat(tipo);
            this.getpesquisarAlbumBean().setRequest(request);
            this.getpesquisarAlbumBean().setUsername((String) this.session.get("username"));
            resultado = this.getpesquisarAlbumBean().pesquisaAlbum();
            return resultado;
        }
        return resultado;
    }


    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}
