package DropMusic.action;
import DropMusic.model.logoutBean;
import DropMusic.model.partilhaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class logoutAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    public String checkUsername(){
        if(!session.containsKey("username"))
           return "FAILED";
        return "SUCCESS";
    }

    public logoutBean getlogoutBean() {
        if(!session.containsKey("logout"))
            this.session.put("logout", new logoutBean());
        return (logoutBean) session.get("logout");
    }

    public String logout(){
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getlogoutBean().setPassword((String) this.session.get("password"));
        this.getlogoutBean().setUsername((String) this.session.get("username"));
        String resposta = this.getlogoutBean().logout();
        return resposta;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}