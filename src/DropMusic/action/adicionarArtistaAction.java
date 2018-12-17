package DropMusic.action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;
import DropMusic.model.*;

@SuppressWarnings("Duplicates")

public class adicionarArtistaAction extends ActionSupport implements SessionAware{

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    /**
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para adicinar um artista
     */
    public adicionarArtistaBean getaddArtistaBean() {
        if(!session.containsKey("addArtista"))
            this.session.put("addArtista", new adicionarArtistaBean());
        return (adicionarArtistaBean) session.get("addArtista");
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
     * Função Responsável por retornar o resultado, coloca o username e o nome do artista no bean, depois usa a função do bean para retornar
     * o resultado
     * @return Failed se a operação não tiver sucesso, ou success caso contrário
     */
    public String addArtista(){
        if(nome.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getaddArtistaBean().setNome(nome);
        this.getaddArtistaBean().setUsername((String) this.session.get("username"));
        String resultado = this.getaddArtistaBean().addArtista();
        return resultado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}
