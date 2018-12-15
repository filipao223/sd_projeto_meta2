package DropMusic.action;

import DropMusic.model.alterarAlbumBean;
import DropMusic.model.alterarArtistaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class alterarArtistaAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;
    public String alterarNome;
    public String alterarDescricao;
    public String alterarNascimento;
    public String request;

    public alterarArtistaBean getalterarArtistaBean() {
        if(!session.containsKey("alterarArtista"))
            this.session.put("alterarArtista", new alterarArtistaBean());
        return (alterarArtistaBean) session.get("alterarArtista");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String alteraArtista(){

        String resposta = "FAILED";

        System.out.println(alterarNascimento);
        System.out.println(alterarDescricao);
        System.out.println(alterarNome);

        if(nome.isEmpty() && alterarNascimento.isEmpty() && alterarDescricao.isEmpty() && alterarNome.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        if(!alterarNascimento.isEmpty()){
            request = "17_24_".concat(nome.concat("_").concat(alterarNascimento));
            this.getalterarArtistaBean().setUsername((String) this.session.get("username"));
            this.getalterarArtistaBean().setRequest(request);
            this.getalterarArtistaBean().setNome(nome);
            resposta = this.getalterarArtistaBean().alteraArtista();
        }
        else if(!alterarNome.isEmpty()){
            request = "17_18_".concat(nome.concat("_").concat(alterarNome));
            this.getalterarArtistaBean().setUsername((String) this.session.get("username"));
            this.getalterarArtistaBean().setRequest(request);
            this.getalterarArtistaBean().setNome(nome);
            resposta = this.getalterarArtistaBean().alteraArtista();
        }
        else if(!alterarDescricao.isEmpty()){
            request = "17_21_".concat(nome.concat("_").concat(alterarDescricao));
            this.getalterarArtistaBean().setUsername((String) this.session.get("username"));
            this.getalterarArtistaBean().setRequest(request);
            this.getalterarArtistaBean().setNome(nome);
            resposta = this.getalterarArtistaBean().alteraArtista();
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

    public String getAlterarDescricao() {
        return alterarDescricao;
    }

    public void setAlterarDescricao(String alterarDescricao) {
        this.alterarDescricao = alterarDescricao;
    }

    public String getAlterarNascimento() {
        return alterarNascimento;
    }

    public void setAlterarNascimento(String alterarNascimento) {
        this.alterarNascimento = alterarNascimento;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}