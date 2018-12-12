package DropMusic.action;
import java.util.Map;

public class alterarMusicaAction {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome,alterarNome,alterarAno,alterarAlbum,alterarArtista;

    public String alteraMusica(){
        /*System.out.println(alterarNome);
        System.out.println(alterarNascimento);
        System.out.println(alterarDescricao);*/
        return "SUCCESS";
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
}