package DropMusic.action;
import DropMusic.model.alterarArtistaBean;
import DropMusic.model.alterarMusicaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class alterarMusicaAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome,alterarNome,alterarAno,alterarAlbum,alterarArtista,request;

    public alterarMusicaBean getalterarMusicaBean() {
        if(!session.containsKey("alterarMusica"))
            this.session.put("alterarMusica", new alterarMusicaBean());
        return (alterarMusicaBean) session.get("alterarMusica");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String alteraMusica(){

        String resposta = "FAILED";

        if(nome.isEmpty() && alterarAno.isEmpty() && alterarAlbum.isEmpty() && alterarNome.isEmpty() && alterarArtista.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        if(!alterarAno.isEmpty()){
            request = "16_19_".concat(nome.concat("_").concat(alterarAno));
            this.getalterarMusicaBean().setUsername((String) this.session.get("username"));
            this.getalterarMusicaBean().setRequest(request);
            this.getalterarMusicaBean().setNome(nome);
            resposta = this.getalterarMusicaBean().alteraMusica();
        }
        else if(!alterarNome.isEmpty()){
            request = "16_18_".concat(nome.concat("_").concat(alterarNome));
            this.getalterarMusicaBean().setUsername((String) this.session.get("username"));
            this.getalterarMusicaBean().setRequest(request);
            this.getalterarMusicaBean().setNome(nome);
            resposta = this.getalterarMusicaBean().alteraMusica();
        }
        else if(!alterarAlbum.isEmpty()){
            request = "16_25_".concat(nome.concat("_").concat(alterarAlbum));
            this.getalterarMusicaBean().setUsername((String) this.session.get("username"));
            this.getalterarMusicaBean().setRequest(request);
            this.getalterarMusicaBean().setNome(nome);
            resposta = this.getalterarMusicaBean().alteraMusica();
        }
        else if(!alterarArtista.isEmpty()){
            request = "16_20_".concat(nome.concat("_").concat(alterarAlbum));
            this.getalterarMusicaBean().setUsername((String) this.session.get("username"));
            this.getalterarMusicaBean().setRequest(request);
            this.getalterarMusicaBean().setNome(nome);
            resposta = this.getalterarMusicaBean().alteraMusica();
        }
        return resposta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAlterarNome() {
        return alterarNome;
    }

    public void setAlterarNome(String alterarNome) {
        this.alterarNome = alterarNome;
    }

    public String getAlterarAno() {
        return alterarAno;
    }

    public void setAlterarAno(String alterarAno) {
        this.alterarAno = alterarAno;
    }

    public String getAlterarAlbum() {
        return alterarAlbum;
    }

    public void setAlterarAlbum(String alterarAlbum) {
        this.alterarAlbum = alterarAlbum;
    }

    public String getAlterarArtista() {
        return alterarArtista;
    }

    public void setAlterarArtista(String alterarArtista) {
        this.alterarArtista = alterarArtista;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}