package DropMusic.action;
import DropMusic.model.tornarEditorBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class tornarEditorAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    public String target;

    public String tornarEditorAction(){
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

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

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