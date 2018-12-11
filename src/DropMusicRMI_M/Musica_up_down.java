package DropMusicRMI_M;
import java.io.Serializable;
import java.util.ArrayList;

public class Musica_up_down implements Serializable {
    private static final long serialVersionUID = 1L;
    String titulo;
    String u;
    ArrayList<String> partilhas = new ArrayList<>();
    boolean publico;
    String diretoria;

    public Musica_up_down(String titulo,String u,String diretorio/*,ArrayList<User> users*/) {
        this.titulo = titulo;
        this.u = u;
        this.diretoria = diretorio;
        //f = new File(diretorio + "/" + titulo);
        this.publico = false;
        //this.partilhas = users;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getU() {
        return u;
    }

    public String getDiretoria() {
        return diretoria;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public void setPartilhas(ArrayList<String> partilhas) {
        this.partilhas = partilhas;
    }

    public ArrayList<String> getPartilhas() {
        return partilhas;
    }
}
