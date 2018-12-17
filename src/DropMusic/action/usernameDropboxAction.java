package DropMusic.action;

import DropMusic.model.addAlbumBean;
import DropMusic.model.usernameDropboxBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@SuppressWarnings("Duplicates")

/**
 * Classe responsável por adicionar um album à database
 */
public class usernameDropboxAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    public void createUsername(){
        if(!session.containsKey("username"))
            this.session.put("username",nome);
        else if(session.containsKey("username")){
            this.session.replace("username",nome);
        }
    }

    /**
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para adicinar realizar login com a dropbox
     */
    public usernameDropboxBean getusernameDropboxBean() {
        if(!session.containsKey("usernameDropbox"))
            this.session.put("usernameDropbox", new usernameDropboxBean());
        return (usernameDropboxBean) session.get("usernameDropbox");
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
    public String usernameDropbox() {
        if(nome.isEmpty()){
            return "FAILED";
        }
        this.getusernameDropboxBean().setNome(nome);
        String resultado = this.getusernameDropboxBean().usernameDropbox();
        createUsername();
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
