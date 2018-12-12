package DropMusic.action;
import java.util.Map;

public class tornarEditorAction {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    public String nome;

    public String tornarEditor(){
        System.out.println(nome);
        return "SUCCESS";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}