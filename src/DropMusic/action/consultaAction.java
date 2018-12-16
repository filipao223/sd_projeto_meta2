package DropMusic.action;

import DropMusic.model.consultaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@SuppressWarnings("Duplicates")

public class consultaAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nomeAlbum;
    public String nomeArtista;
    public String info;

    public String getNomeAlbum() {
        return nomeAlbum;
    }

    public void setNomeAlbum(String nomeAlbum) {
        this.nomeAlbum = nomeAlbum;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }

    public consultaBean getconsultaBean() {
        if(!session.containsKey("consulta"))
            this.session.put("consulta", new consultaBean());
        return (consultaBean) session.get("consulta");
    }

    public String checkUsername(){
        if(!session.containsKey("username"))
            return "FAILED";
        return "SUCCESS";
    }

    public String consulta() {

        String resultado = "FAILED";
        if(nomeAlbum.isEmpty() && nomeArtista.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        if(!nomeArtista.isEmpty()){
            this.getconsultaBean().setNomeArtista(nomeArtista);
            this.getconsultaBean().setUsername((String) this.session.get("username"));
            resultado = this.getconsultaBean().consultaArtista();

            info = getconsultaBean().getInfo();

            System.out.println("Info " + info);

            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return resultado;
        }
        if(!nomeAlbum.isEmpty()){
            this.getconsultaBean().setNomeAlbum(nomeAlbum);
            this.getconsultaBean().setUsername((String) this.session.get("username"));
            resultado = this.getconsultaBean().consultaAlbum();

            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            info = this.getconsultaBean().getInfo();

            System.out.println("Info " + info);

            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return resultado;
        }
        return resultado;
    }


    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}
