package DropMusic.action;

import DropMusic.model.addArtistaBean;
import DropMusic.model.pesquisarArtistaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@SuppressWarnings("Duplicates")

public class pesquisarArtistaAction extends ActionSupport implements SessionAware{

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    /**
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para pesquisar um album
     */
    public pesquisarArtistaBean getpesquisarArtistaBean() {
        if(!session.containsKey("pesquisarArtista"))
            this.session.put("pesquisarArtista", new pesquisarArtistaBean());
        return (pesquisarArtistaBean) session.get("pesquisarArtista");
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
     * Função Responsável por retornar o resultado, esta função verifica se o artista está vazio, se sim retorna automaticamente
     * Failed, se não continua, existe um if para cada caixa de texto, porque o request irá mudar tendo em conta o que se quer, quando entra num
     * if envia o necessário e usa o bean para retornar o resultado
     * @return Failed se a operação não tiver sucesso, ou success caso contrário
     */
    public String pesquisarArtista(){
        if(nome.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        this.getpesquisarArtistaBean().setNome(nome);
        this.getpesquisarArtistaBean().setUsername((String) this.session.get("username"));
        String resultado = this.getpesquisarArtistaBean().pesquisaArtista();
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
