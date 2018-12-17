package DropMusic.action;

import DropMusic.model.removeArtistaBean;
import DropMusic.model.removeMusicaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class removeMusicaAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    /**
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para remover uma musica
     */
    public removeMusicaBean getremoveMusicaBean() {
        if(!session.containsKey("removeMusica"))
            this.session.put("removeMusica", new removeMusicaBean());
        return (removeMusicaBean) session.get("removeMusica");
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
     * Função Responsável por retornar o resultado, coloca o username e o nome da musica no bean, depois usa a função do bean para retornar
     * o resultado
     * @return Failed se a operação não tiver sucesso, ou success caso contrário
     */
    public String removeMusica(){
        if(nome.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getremoveMusicaBean().setNome(nome);
        this.getremoveMusicaBean().setUsername((String) this.session.get("username"));
        String resultado = this.getremoveMusicaBean().removeMusica();
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
