package DropMusic.action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;
import DropMusic.model.*;

@SuppressWarnings("Duplicates")

public class LoginAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String username = null, password = null;

    public String registar() {
        int op;
        System.out.println("-->"+username);
        System.out.println("-->"+password);
        if(username != null && password != null && password != "" && username != "") {
            this.getLoginBean().setUsername(this.username);
            this.getLoginBean().setPassword(this.password);
            op = this.getLoginBean().registar();
            if(op == 1) {
                session.put("username", username);
                session.put("registar", true);
                session.put("editor",false);
                this.getLoginBean().setEditor(false);
                System.out.println("*"+username);
                System.out.println("*"+password);
                return "SUCCESS";
            }
            if(op == 2) {
                session.put("username", username);
                session.put("registar", true);
                session.put("editor",true);
                this.getLoginBean().setEditor(false);
                System.out.println("*"+username);
                System.out.println("*"+password);
                return "SUCCESS";
            }
            else if(op == 0 ){//O USERNAME JÁ ESTA A SER UTILIZADO
                session.put("username", username);
                session.put("registar", false);
                System.out.println(username);
                System.out.println(password);
                return "REGISTAR";
            }
        }
        return "REGISTAR";
    }

    public String login() {
        System.out.println("-->"+username);
        System.out.println("-->"+password);
        int op;
        if((username != null && password != null) || (password != "" && username != "")) {
            this.getLoginBean().setUsername(this.username);
            this.getLoginBean().setPassword(this.password);
            op = this.getLoginBean().login();
            if(op == 1) {
                session.put("username", username);
                session.put("loggedin", 1);
                System.out.println("*"+username);
                System.out.println("*"+password);
                return "SUCCESS";
            }
            else if(op== 0){//o user nao esta registado
                session.put("username", username);
                session.put("loggedin", 0);
                System.out.println(username);
                System.out.println(password);
                return "LOGIN";
            }
            else if(op == 2){//o user já esta loggin
                session.put("username", username);
                session.put("loggedin", 2);
                System.out.println(username);
                System.out.println(password);
                return "LOGIN";
            }
        }
        return "LOGIN";
    }

    public String logout() {
        System.out.println("-->"+username);
        System.out.println("-->"+password);
        if((username != null && password != null) || (password != "" && username != "")) {
            this.getLoginBean().setUsername(this.username);
            this.getLoginBean().setPassword(this.password);
            if(this.getLoginBean().login() == 1) {
                session.put("username", username);
                session.put("loggedin", false);
                System.out.println("*"+username);
                System.out.println("*"+password);
                return "SUCCESS";
            }
            else if(this.getLoginBean().login() == 0){//o user nao esta registado
                session.put("username", username);
                session.put("loggedin", true);
                System.out.println(username);
                System.out.println(password);
                return "LOGIN";
            }
            else if(this.getLoginBean().login() == 2){//o user já esta loggin
                session.put("username", username);
                session.put("loggedin", "n");
                System.out.println(username);
                System.out.println(password);
                return "LOGIN";
            }
        }
        return "LOGIN";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginBean getLoginBean() {
        if(!session.containsKey("loginBean"))
            this.setLoginBean(new LoginBean());
        return (LoginBean) session.get("loginBean");
    }

    public void setLoginBean(LoginBean loginbean) {
        this.session.put("loginBean", loginbean);
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
