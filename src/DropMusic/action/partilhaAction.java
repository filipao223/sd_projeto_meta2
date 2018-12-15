package DropMusic.action;
import DropMusic.model.partilhaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class partilhaAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String target = null;

    public String checkUsername(){
        if(!session.containsKey("username"))
           return "FAILED";
        return "SUCCESS";
    }

    public partilhaBean getpartilhaBean() {
        if(!session.containsKey("partilha"))
            this.session.put("partilha", new partilhaBean());
        return (partilhaBean) session.get("partilha");
    }

    public String partilhaAction(){
        if(target.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getpartilhaBean().setTarget(target);
        this.getpartilhaBean().setUsername((String) this.session.get("username"));
        String resposta = this.getpartilhaBean().partilha();
        return resposta;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}