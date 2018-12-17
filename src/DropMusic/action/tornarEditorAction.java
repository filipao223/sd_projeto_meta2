package DropMusic.action;
import DropMusic.model.tornarEditorBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class tornarEditorAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    public String target;

    /**
     * Função Responsável por retornar o resultado, coloca o username target no bean, depois usa a função do bean para retornar
     * o resultado
     * @return Failed se a operação não tiver sucesso, ou success caso contrário
     */
    public String tornarEditorAction(){
        if(target.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.gettornarEditorBean().setTarget(target);
        this.gettornarEditorBean().setUsername((String) this.session.get("username"));
        String resposta = this.gettornarEditorBean().partilha();
        return resposta;
    }


    public String getTarget() {
        return target;
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
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para tornar um user editor
     */
    public tornarEditorBean gettornarEditorBean() {
        if(!session.containsKey("tornarEditor"))
            this.session.put("tornarEditor", new tornarEditorBean());
        return (tornarEditorBean) session.get("tornarEditor");
    }

    public void setNome(String target) {
        this.target = target;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}