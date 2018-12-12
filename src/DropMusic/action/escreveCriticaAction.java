package DropMusic.action;
import java.util.Map;

public class escreveCriticaAction {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome,critica;

    public String escreveCritica(){
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

    public String getCritica() {
        return critica;
    }

    public void setCritica(String critica) {
        this.critica = critica;
    }
}