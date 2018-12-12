package DropMusic.action;
import java.util.Map;

public class alterarAlbumAction {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String alterarNome;
    public String alterarAno;
    public String alterarTipo;
    public String alterarArtista;
    public String alterarDescricao;

    public String alteraAlbum(){
        /*System.out.println(alterarNome);
        System.out.println(alterarNascimento);
        System.out.println(alterarDescricao);*/
        return "SUCCESS";
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
}