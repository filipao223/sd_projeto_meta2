package DropMusic.action;
import DropMusic.model.LoginBean;
import DropMusicRMI_M.RMIInter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

public class alterarArtistaAction {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    public String alterarNome,alterarNascimento,alterarDescricao;

    public void setAlterarNome(String alterarNome){ this.alterarNome = alterarNome;}

    public void setAlterarNascimento(String alterarNascimento){ this.alterarNascimento = alterarNascimento;}

    public void setAlterarDescricao(String alterarDescricao){this.alterarDescricao = alterarDescricao;}

    public String alteraArtista(){
        /*System.out.println(alterarNome);
        System.out.println(alterarNascimento);
        System.out.println(alterarDescricao);*/
        return "SUCCESS";
    }
}