package DropMusic.action;
import DropMusic.model.escreveCriticaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class escreveCriticaAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String target = null;
    public String critica = null;
    public String rating = null;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    /**
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para escrever uma critica
     */
    public escreveCriticaBean getescreveCritica() {
        if(!session.containsKey("escreveCritica"))
            this.session.put("escreveCritica", new escreveCriticaBean());
        return (escreveCriticaBean) session.get("escreveCritica");
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
     * Função Responsável por retornar o resultado, coloca o username,  o nome do album,rating e a critica escrita
     * no bean, depois usa a função do bean para retornar o resultado
     * @return Failed se a operação não tiver sucesso, ou success caso contrário
     */
    public String escreveCriticaAction(){

        if(target.isEmpty() || critica.isEmpty() || rating.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }

        this.getescreveCritica().setTarget(target);
        this.getescreveCritica().setUsername((String) this.session.get("username"));
        this.getescreveCritica().setCritica(critica);
        this.getescreveCritica().setRating(rating);

        String resposta = this.getescreveCritica().critica();

        return resposta;
    }


    public void setTarget(String target) {
        this.target = target;
    }

    public void setCritica(String critica) {
        this.critica = critica;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}