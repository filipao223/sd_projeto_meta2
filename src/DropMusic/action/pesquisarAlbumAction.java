package DropMusic.action;

import DropMusic.model.consultaBean;
import DropMusic.model.pesquisarAlbumBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@SuppressWarnings("Duplicates")

public class pesquisarAlbumAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome,nomeArtista,tipo,request;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }

    /**
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para pesquisar um album
     */
    public pesquisarAlbumBean getpesquisarAlbumBean() {
        if(!session.containsKey("pesquisarAlbum"))
            this.session.put("pesquisarAlbum", new pesquisarAlbumBean());
        return (pesquisarAlbumBean) session.get("pesquisarAlbum");
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
     * Função Responsável por retornar o resultado, esta função verifica se todas as caixas de texto estão vazias, se sim retorna automaticamente
     * Failed, se não continua, existe um if para cada caixa de texto, porque o request irá mudar tendo em conta o que se quer, quando entra num
     * if envia o necessário e usa o bean para retornar o resultado
     * @return Failed se a operação não tiver sucesso, ou success caso contrário
     */
    public String pesquisarAlbum() {

        String resultado = "FAILED";
        if(nome.isEmpty() && nomeArtista.isEmpty() && tipo.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        if(!nomeArtista.isEmpty()){
            String request = "26_40_".concat(nomeArtista);
            this.getpesquisarAlbumBean().setRequest(request);
            this.getpesquisarAlbumBean().setUsername((String) this.session.get("username"));
            resultado = this.getpesquisarAlbumBean().pesquisaAlbum();
            return resultado;
        }
        if(!nome.isEmpty()){
            String request = "26_39_".concat(nome);
            this.getpesquisarAlbumBean().setRequest(request);
            this.getpesquisarAlbumBean().setUsername((String) this.session.get("username"));
            resultado = this.getpesquisarAlbumBean().pesquisaAlbum();
            return resultado;
        }
        if(!tipo.isEmpty()){
            String request = "26_41_".concat(tipo);
            this.getpesquisarAlbumBean().setRequest(request);
            this.getpesquisarAlbumBean().setUsername((String) this.session.get("username"));
            resultado = this.getpesquisarAlbumBean().pesquisaAlbum();
            return resultado;
        }
        return resultado;
    }


    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}
