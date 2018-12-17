package DropMusic.action;
import DropMusic.model.addMusicaBean;
import DropMusic.model.alterarAlbumBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class alterarAlbumAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;
    public String alterarNome;
    public String alterarAno;
    public String alterarTipo;
    public String alterarArtista;
    public String alterarDescricao;
    public String request;

    /**
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para alterar um album
     */
    public alterarAlbumBean getalterarAlbumBean() {
        if(!session.containsKey("alterarAlbum"))
            this.session.put("alterarAlbum", new alterarAlbumBean());
        return (alterarAlbumBean) session.get("alterarAlbum");
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
    public String alteraAlbum(){

        String resposta = "FAILED";

        if(nome.isEmpty() && alterarArtista.isEmpty() && alterarAno.isEmpty() && alterarDescricao.isEmpty() && alterarNome.isEmpty() && alterarTipo.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        if(!alterarArtista.isEmpty()){
            request = "15_20_".concat(nome.concat("_").concat(alterarArtista));
            this.getalterarAlbumBean().setUsername((String) this.session.get("username"));
            this.getalterarAlbumBean().setRequest(request);
            this.getalterarAlbumBean().setNome(nome);
            resposta = this.getalterarAlbumBean().alteraAlbum();
        }
        else if(!alterarNome.isEmpty()){
            request = "15_18_".concat(nome.concat("_").concat(alterarNome));
            this.getalterarAlbumBean().setUsername((String) this.session.get("username"));
            this.getalterarAlbumBean().setRequest(request);
            this.getalterarAlbumBean().setNome(nome);
            resposta = this.getalterarAlbumBean().alteraAlbum();
        }
        else if(!alterarAno.isEmpty()){
            request = "15_19_".concat(nome.concat("_").concat(alterarAno));
            this.getalterarAlbumBean().setUsername((String) this.session.get("username"));
            this.getalterarAlbumBean().setRequest(request);
            this.getalterarAlbumBean().setNome(nome);
            resposta = this.getalterarAlbumBean().alteraAlbum();
        }
        else if(!alterarTipo.isEmpty()){
            request = "15_22_".concat(nome.concat("_").concat(alterarTipo));
            this.getalterarAlbumBean().setUsername((String) this.session.get("username"));
            this.getalterarAlbumBean().setRequest(request);
            this.getalterarAlbumBean().setNome(nome);
            resposta = this.getalterarAlbumBean().alteraAlbum();
        }
        else if(!alterarDescricao.isEmpty()){
            request = "15_21_".concat(nome.concat("_").concat(alterarDescricao));
            this.getalterarAlbumBean().setUsername((String) this.session.get("username"));
            this.getalterarAlbumBean().setRequest(request);
            this.getalterarAlbumBean().setNome(nome);
            resposta = this.getalterarAlbumBean().alteraAlbum();
        }
        return resposta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAlterarTipo() {
        return alterarTipo;
    }

    public void setAlterarTipo(String alterarTipo) {
        this.alterarTipo = alterarTipo;
    }

    public String getAlterarNome() {
        return alterarNome;
    }

    public void setAlterarNome(String alterarNome) {
        this.alterarNome = alterarNome;
    }

    public String getAlterarAno() {
        return alterarAno;
    }

    public void setAlterarAno(String alterarAno) {
        this.alterarAno = alterarAno;
    }

    public String getAlterarArtista() {
        return alterarArtista;
    }

    public void setAlterarArtista(String alterarArtista) {
        this.alterarArtista = alterarArtista;
    }

    public String getAlterarDescricao() {
        return alterarDescricao;
    }

    public void setAlterarDescricao(String alterarDescricao) {
        this.alterarDescricao = alterarDescricao;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}