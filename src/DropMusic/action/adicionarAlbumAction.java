package DropMusic.action;

import DropMusic.model.adicionarAlbumBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@SuppressWarnings("Duplicates")

/**
 * Classe responsável por adicionar um album à database
 */
public class adicionarAlbumAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    /**
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para adicinar um album
     */
    public adicionarAlbumBean getaddAlbumBean() {
        if(!session.containsKey("addAlbum"))
            this.session.put("addAlbum", new adicionarAlbumBean());
        return (adicionarAlbumBean) session.get("addAlbum");
    }

    /**
     * Verifica se a sessão já tem o username para que o utilizador não possa fazer ações sem ele
     * @return Failed se a sessão não possuir username, success se possuir
     */
    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    /**
     * Função Responsável por retornar o resultado, coloca o username e o nome do album no bean, e depois usa a função do bean para retornar
     * o resultado
     * @return Failed se a operação não tiver sucesso, ou success caso contrário
     */
    public String addAlbum() {
        if(nome.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getaddAlbumBean().setNome(nome);
        this.getaddAlbumBean().setUsername((String) this.session.get("username"));
        String resultado = this.getaddAlbumBean().addAlbum();
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
