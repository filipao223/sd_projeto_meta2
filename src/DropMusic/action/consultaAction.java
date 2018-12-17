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

    /**
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para consultar uma musica
     */
    public consultaBean getconsultaBean() {
        if(!session.containsKey("consulta"))
            this.session.put("consulta", new consultaBean());
        return (consultaBean) session.get("consulta");
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
     * Função Responsável por retornar o resultado, esta função verifica qual caixa de texto está vazia, se estiveram todas retorna
     * Failed, se não continua, existe um if para cada caixa de texto, porque o request irá mudar tendo em conta o que se quer, quando entra num
     * if envia o necessário e usa o bean para retornar o resultado
     * @return Failed se a operação não tiver sucesso, ou success caso contrário
     */
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
