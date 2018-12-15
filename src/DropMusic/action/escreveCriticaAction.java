package DropMusic.action;
import DropMusic.model.escreveCriticaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class escreveCriticaAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String target = null,critica = null;

    public escreveCriticaBean getescreveCritica() {
        if(!session.containsKey("escreveCritica"))
            this.session.put("escreveCritica", new escreveCriticaBean());
        return (escreveCriticaBean) session.get("escreveCritica");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String escreveCriticaAction(){

        System.out.println(target);
        System.out.println(critica);
        if(target.isEmpty() || critica.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }

        this.getescreveCritica().setTarget(target);
        this.getescreveCritica().setUsername((String) this.session.get("username"));
        this.getescreveCritica().setCritica(critica);

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