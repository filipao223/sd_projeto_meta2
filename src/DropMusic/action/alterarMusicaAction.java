package DropMusic.action;
import DropMusic.model.alterarArtistaBean;
import DropMusic.model.alterarMusicaBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class alterarMusicaAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome,alterarNome,alterarAno,alterarAlbum,alterarArtista,request;

    /**
     * Adiciona o bean à sessão já existente se este não existir
     * @return O bean usado para alterar musica
     */
    public alterarMusicaBean getalterarMusicaBean() {
        if(!session.containsKey("alterarMusica"))
            this.session.put("alterarMusica", new alterarMusicaBean());
        return (alterarMusicaBean) session.get("alterarMusica");
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
    public String alteraMusica(){

        String resposta = "FAILED";

        if(nome.isEmpty() && alterarAno.isEmpty() && alterarAlbum.isEmpty() && alterarNome.isEmpty() && alterarArtista.isEmpty()){
            return "FAILED";
        }
        if(checkUsername().matches("FAILED")) {
            return "FAILED";
        }
        if(!alterarAno.isEmpty()){
            request = "16_19_".concat(nome.concat("_").concat(alterarAno));
            this.getalterarMusicaBean().setUsername((String) this.session.get("username"));
            this.getalterarMusicaBean().setRequest(request);
            this.getalterarMusicaBean().setNome(nome);
            resposta = this.getalterarMusicaBean().alteraMusica();
        }
        else if(!alterarNome.isEmpty()){
            request = "16_18_".concat(nome.concat("_").concat(alterarNome));
            this.getalterarMusicaBean().setUsername((String) this.session.get("username"));
            this.getalterarMusicaBean().setRequest(request);
            this.getalterarMusicaBean().setNome(nome);
            resposta = this.getalterarMusicaBean().alteraMusica();
        }
        else if(!alterarAlbum.isEmpty()){
            request = "16_25_".concat(nome.concat("_").concat(alterarAlbum));
            this.getalterarMusicaBean().setUsername((String) this.session.get("username"));
            this.getalterarMusicaBean().setRequest(request);
            this.getalterarMusicaBean().setNome(nome);
            resposta = this.getalterarMusicaBean().alteraMusica();
        }
        else if(!alterarArtista.isEmpty()){
            request = "16_20_".concat(nome.concat("_").concat(alterarAlbum));
            this.getalterarMusicaBean().setUsername((String) this.session.get("username"));
            this.getalterarMusicaBean().setRequest(request);
            this.getalterarMusicaBean().setNome(nome);
            resposta = this.getalterarMusicaBean().alteraMusica();
        }
        return resposta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getAlterarAlbum() {
        return alterarAlbum;
    }

    public void setAlterarAlbum(String alterarAlbum) {
        this.alterarAlbum = alterarAlbum;
    }

    public String getAlterarArtista() {
        return alterarArtista;
    }

    public void setAlterarArtista(String alterarArtista) {
        this.alterarArtista = alterarArtista;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}