package DropMusic.action;
import DropMusic.model.logoutBean;
import DropMusic.model.partilhaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class logoutAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

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
     * @return O bean usado para logout
     */
    public logoutBean getlogoutBean() {
        if(!session.containsKey("logout"))
            this.session.put("logout", new logoutBean());
        return (logoutBean) session.get("logout");
    }

    /**
     * Função Responsável por retornar o resultado, coloca o username e a password no bean, depois usa a função do bean para retornar
     * o resultado
     * @return Failed se a operação não tiver sucesso, ou success caso contrário
     */
    public String logout(){
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getlogoutBean().setUsername((String) this.session.get("username"));
        String resposta = this.getlogoutBean().logout();
        return resposta;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}