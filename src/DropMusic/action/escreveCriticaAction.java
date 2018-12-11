package DropMusic.action;
import java.util.Map;

public class escreveCriticaAction {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String alterarNome,alterarNascimento,alterarDescricao;

    public void setAlterarNome(String alterarNome){ this.alterarNome = alterarNome;}

    public void setAlterarNascimento(String alterarNascimento){ this.alterarNascimento = alterarNascimento;}

    public void setAlterarDescricao(String alterarDescricao){this.alterarDescricao = alterarDescricao;}

    public String escreveCritica(){
        /*System.out.println(alterarNome);
        System.out.println(alterarNascimento);
        System.out.println(alterarDescricao);*/
        return "SUCCESS";
    }
}