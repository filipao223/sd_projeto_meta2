package DropMusic.action;

import java.util.Map;

public class alterarArtistaAction {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String nome,alterarNome,alterarNascimento,alterarDescricao;

    public void setAlterarNome(String alterarNome){ this.alterarNome = alterarNome;}

    public void setAlterarNascimento(String alterarNascimento){ this.alterarNascimento = alterarNascimento;}

    public void setAlterarDescricao(String alterarDescricao){this.alterarDescricao = alterarDescricao;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAlterarNome() {
        return alterarNome;
    }

    public String getAlterarNascimento() {
        return alterarNascimento;
    }

    public String getAlterarDescricao() {
        return alterarDescricao;
    }

    public String alteraArtista(){
        /*System.out.println(alterarNome);
        System.out.println(alterarNascimento);
        System.out.println(alterarDescricao);*/
        return "SUCCESS";
    }
}