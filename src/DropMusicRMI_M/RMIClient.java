package DropMusicRMI_M;

import java.net.*;
import java.rmi.*;
import java.lang.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.io.IOException;
import java.io.*;
import java.io.File;

@SuppressWarnings("Duplicates")

/**
 * @author Lara Trindade e João Miranda
 */

public class RMIClient extends UnicastRemoteObject implements  Serializable, CBInter{
    private static final long serialVersionUID = 1L;
    static String username = " ";

    protected RMIClient() throws RemoteException{
    }

    public static RMIInter verificaLookup() {//função que verifica lookup, ou seja, o cliente fica à procura de um server para comunicar
        RMIInter verifica = null;
        try{
            while(true) {
                //System.out.println("kek3");
                verifica = (RMIInter) Naming.lookup("rmi://localhost:7000/benfica");//"rmi://localhost25.33.238.221:7000/benfica"
                //System.out.println("kek4");
                try {
                    if (verifica.test_servers()) {
                        //System.out.println(verifica.test_servers());
                        break;
                    }
                }catch(RemoteException re){
                    //System.out.println("kek remote");
                    //re.printStackTrace();
                    continue;
                }
            }

        }catch(Exception e){
            //System.out.println("Exception in main: " + e);
            //e.printStackTrace(); /*imprime tudo o que é da exceção, depois tirar*/
        }
        return verifica;
    }

    public static void menu(RMIInter inter, CBInter cInter){    //Menu de registo/login
        MulticastSocket msocket = null;
        try {
            msocket = new MulticastSocket();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        Scanner sc = new Scanner (System.in);
        int opcao = -1;
        System.out.println("-------- MENU -------");
        System.out.println("1. Registar");
        System.out.println("2. Login");
        System.out.println("0. Logout");
        while (opcao<0 || opcao >2) {
            if (sc.hasNextInt()) {
                opcao = sc.nextInt();
                if (opcao == 1)
                    Registar(msocket, inter, cInter);
                else if (opcao == 2)
                    login(msocket, inter, cInter);
                else {
                    String aux = "LOGOUT-".concat(username);
                    try {
                        inter = verificaLookup();
                        System.out.println(inter.rmi_mc(aux));
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                    System.exit(0);
                }
            }
            else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
    }

    public static void Registar(MulticastSocket socket,RMIInter inter, CBInter cInter) {    //Registar cliente
        String s = "";
        Scanner keyboardScanner = new Scanner(System.in);
        String user = "";
        String readKeyboard;

        user = user.concat("USER-");
        System.out.println("username: ");
        readKeyboard = keyboardScanner.nextLine();
        user = user.concat(readKeyboard+"-");
        System.out.println("password: ");
        readKeyboard = keyboardScanner.nextLine();
        user = user.concat(readKeyboard);

        try {
            inter = verificaLookup();
            s = inter.rmi_mc(user);
            System.out.println(s);
            menu(inter, cInter);
        } catch (RemoteException e) {
            inter = verificaLookup();
            //e.printStackTrace();
        }
    }

    public static void login(MulticastSocket socket,RMIInter inter, CBInter cInter){    //Login de cliente
        String s = "";
        Scanner keyboardScanner = new Scanner(System.in);
        String user = "";
        String readKeyboard;
        String auxx = "";

        user = user.concat("LOGIN-");
        System.out.println("username: ");
        readKeyboard = keyboardScanner.nextLine();
        auxx = readKeyboard;
        user = user.concat(readKeyboard+"-");
        System.out.println("password: ");
        readKeyboard = keyboardScanner.nextLine();
        user = user.concat(readKeyboard);

        try {
            s = inter.rmi_mc(user);
            System.out.println(s);
            if(s.compareTo("type|status; msg|Utilizador necessita de criar utilizador.") == 0 || s.compareTo("type|status; msg|User já fez login.") == 0)
                menu(inter, cInter);
            else {
                String [] aux = s.split("\n");
                if(aux[0].compareTo("type|status; msg|Login com sucesso.") == 0) {
                    username = auxx;
                    inter.subscribe(cInter, username);
                    menuOp(socket, inter);
                }
            }
        } catch (RemoteException e) {
            //e.printStackTrace();
            verificaLookup();
            login(socket,inter,cInter);
        }
    }

    public static void menuOp(MulticastSocket socket,RMIInter inter){   //menu do cliente
        int opcao = -1;
        Scanner sc = new Scanner (System.in);

        System.out.println("-------- MENU PRINCIPAL -------");
        System.out.println("1. Gerir artistas, albuns e musicas");
        System.out.println("2. Pesquisar musicas");
        System.out.println("3. Consultar detalhes album ou artista");
        System.out.println("4. Escrever critica a um album");
        System.out.println("5. Dar privilegios de editor a user");
        System.out.println("6. Upload de musica");
        System.out.println("7. Partilhar musica");
        System.out.println("8. Download de musica");
        System.out.println("0. Logout");

        while (opcao<0 || opcao >8) {
            if (sc.hasNextInt()) {
                opcao = sc.nextInt();
                if (opcao == 1){
                    menuGerir(socket,inter);
                }
                else if (opcao == 2){
                    pesquisarMusicas(socket,inter);
                }
                else if (opcao == 3){
                    consultarAlbumArtista(socket,inter);
                }
                else if (opcao == 4){
                    criticas(socket,inter);
                }
                else if (opcao == 5){
                    privilegios(socket,inter);
                }
                else if (opcao == 6){
                    upload(socket,inter);
                }
                else if (opcao == 7){
                    partilha(socket,inter);
                }

                else if (opcao == 8) {
                    download(socket,inter);
                }
                else {
                    String aux = "LOGOUT-".concat(username);
                    try {
                        inter=verificaLookup();
                        System.out.println(inter.rmi_mc(aux));
                        inter.unsubscribe(username);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                    System.exit(0);
                }
            }
            else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
    }

    private static void upload(MulticastSocket socket, RMIInter inter) {
        String directory_music = "";
        Scanner keyboardScanner = new Scanner(System.in);
        Socket so = null;
        String s="";
        String texto = "";
        try {
            //enviar por udp para receber o port e o ip do tcp para que deve enviar
            int porto;
            String str = "";
            String msg = "UDP|UPLOAD";
            try {
                inter = verificaLookup();
                str = inter.rmi_udp(msg);
                System.out.println(s);
            } catch (RemoteException e) {
                //e.printStackTrace();
                inter = verificaLookup();
            }

            String [] ip_porto = str.split("-");
            //System.out.println(ip_porto[0] + " "+ip_porto[1]);
            /*String [] ipp = porttt[1].split("/");
            System.out.println(ipp[1]+"    "+Integer.parseInt(porttt[0]) );*/

            //TCP
            texto = texto.concat("UPLOAD-"+username+"-");

            System.out.println("Nome do artista: ");
            s = keyboardScanner.nextLine();
            texto = texto.concat(s+"-");

            System.out.println("Nome do album: ");
            s = keyboardScanner.nextLine();
            texto = texto.concat(s+"-");

            System.out.println("Nome da musica: ");
            s = keyboardScanner.nextLine();
            texto = texto.concat(s/*+"-"*/);

            so =  new Socket(ip_porto[0],Integer.parseInt(ip_porto[1]));
            DataOutputStream output = new DataOutputStream(so.getOutputStream());
            output.writeUTF(texto);//envia TCP
            //System.out.println(texto);

            System.out.println("Diretório do ficheiro de musica: ");
            directory_music = keyboardScanner.nextLine();

            OutputStream out = so.getOutputStream();
            FileInputStream fis = new FileInputStream(directory_music);

            byte [] b = new  byte[1024];
            while(fis.read(b) != -1)
            {
                out.write(b);
            }
            out.flush();
            out.close();
            so.close();

            /*so =  new Socket(ip_porto[0],Integer.parseInt(ip_porto[1]) );
            DataInputStream input = new DataInputStream(so.getInputStream());
            //RECEBER MSG
            //String aux = input.readUTF();
            System.out.println(aux);
            input.close();
            so.close();*/

        } catch (IOException e) {
            inter = verificaLookup();
            //e.printStackTrace();
        }
        menuOp(socket,inter);
    }

    private static void partilha(MulticastSocket socket, RMIInter inter) {//tcp
        Scanner keyboardScanner = new Scanner(System.in);
        String str;
        String s;
        String texto = "";
        int flag = 0;

        String msg = "PARTILHA-"+username+"-";
        System.out.println("Título da musica: ");
        s = keyboardScanner.nextLine();
        msg = msg.concat(s+"-");
        System.out.println("Username do utilizadore com quem quer partilhar a musica, se quiser partilhar com toda a gente escreva 'publico': ");
        s = keyboardScanner.nextLine();
        msg = msg.concat(s);
        try {
            inter = verificaLookup();
            str = inter.rmi_udp(msg);
            System.out.println(str);
        } catch (RemoteException e) {
            //e.printStackTrace();
            inter = verificaLookup();
        }
        menuOp(socket,inter);
    }

    private static void download(MulticastSocket socket, RMIInter inter) {
        String directory_music = "";
        Scanner keyboardScanner = new Scanner(System.in);
        Socket so = null;
        String nome_musica="";
        String texto = "";
        int flag = 0;

        //enviar por udp para receber o port e o ip do tcp para que deve enviar
        int porto;
        String str = "";
        String msg = "UDP|DOWNLOAD-"+username+"-";
        System.out.println("Nome da musica que pretende fazer o download: ");
        nome_musica = keyboardScanner.nextLine();
        msg = msg.concat(nome_musica);
        try {
            inter = verificaLookup();
            str = inter.rmi_udp(msg);
            System.out.println(str);
        } catch (RemoteException e) {
            //e.printStackTrace();
            inter = verificaLookup();
        }
        if(str.compareTo("type|status; msg|Não foi feito o upload dessa musica ou a musica não foi partilhada consigo.") == 0){
            System.out.println(str);
            flag = 1;
        }
        if(flag == 0){
            try {
                String[] ip_porto = str.split("-");
                //System.out.println(ip_porto[0] + " " + ip_porto[1]);

                //TCP
                texto = texto.concat("DOWNLOAD-" + username + "-" + nome_musica);
                so = new Socket(ip_porto[0], Integer.parseInt(ip_porto[1]));
                DataOutputStream output = new DataOutputStream(so.getOutputStream());
                output.writeUTF(texto);//envia TCP
                //System.out.println(texto);

                System.out.println("Diretório de onde quer guardar a musica: ");
                directory_music = keyboardScanner.nextLine();

                InputStream inp = so.getInputStream();
                byte[] b = new byte[1024];
                FileOutputStream fos = new FileOutputStream(new File(directory_music + "/" + nome_musica + ".mp3"), true);
                while (inp.read(b) != -1) {
                    fos.write(b);
                }
                fos.close();
                inp.close();
                so.close();

            } catch (IOException e) {
                inter = verificaLookup();
                //e.printStackTrace();
            }
        }
        menuOp(socket,inter);
    }

    public static void menuGerir(MulticastSocket socket,RMIInter inter) {   //Menu
        int op = -1;
        String ger = "";
        Scanner sc = new Scanner (System.in);
        System.out.println("-------- MENU GERIR -------");
        System.out.println("1. Gerir artista");
        System.out.println("2. Gerir album");
        System.out.println("3. Gerir musica");

        while (op<1 || op>3 ) {
            if (sc.hasNextInt()) {
                op = sc.nextInt();
                if(op == 1) {
                    ger = ger.concat("ARTISTA-");
                    menuGerirArtista(socket, inter);
                }
                else if(op == 2){
                    menuGerirAlbum(socket,inter);
                }
                else {
                    menuGerirMusica(socket,inter);
                }
            } else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
    }

    public static void menuGerirArtista(MulticastSocket socket,RMIInter inter) { //Menu
        int op = -1;
        String g_artista = "";
        String s = "";
        Scanner sc = new Scanner (System.in);

        System.out.println("-------- MENU GERIR ARTISTA -------");
        System.out.println("1. Inserir artista");
        System.out.println("2. Alterar artista");
        System.out.println("3. Remover artista");

        while (op<1 || op>3 ) {
            if (sc.hasNextInt()) {
                op = sc.nextInt();
                s = sc.nextLine();//guarda o enter
                if(op == 1) {//inserir artista
                    g_artista = g_artista.concat("INSERIR-ARTISTA-"+username+"-");
                    System.out.println("Nome de artista: ");
                    s = sc.nextLine();
                    g_artista = g_artista.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_artista);
                        System.out.println(s);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
                else if(op == 2){//alterar artista
                    g_artista = g_artista.concat("ALTERAR-ARTISTA-NOME-"+username+"-");
                    System.out.println("Nome do artista que quer alterar: ");
                    s = sc.nextLine();
                    g_artista = g_artista.concat(s+"-");
                    System.out.println("Novo nome do artista: ");
                    s = sc.nextLine();
                    g_artista = g_artista.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_artista);
                        System.out.println(s);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
                else{//remover
                    g_artista = g_artista.concat("REMOVER-ARTISTA-"+username+"-");
                    System.out.println("Nome de artista: ");
                    s = sc.nextLine();
                    g_artista = g_artista.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_artista);
                        System.out.println(s);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
        menuOp(socket,inter);
    }

    private static void menuGerirAlbum(MulticastSocket socket, RMIInter inter) {    //Menu
        int op = -1;
        String g_album = "";
        String s = "";
        Scanner sc = new Scanner (System.in);

        System.out.println("-------- MENU GERIR ALBUM -------");
        System.out.println("1. Inserir album");
        System.out.println("2. Alterar album");
        System.out.println("3. Remover album");

        while (op<1 || op>3 ) {
            if (sc.hasNextInt()) {
                op = sc.nextInt();
                s = sc.nextLine();//guarda o enter
                if(op == 1) {
                    g_album = g_album.concat("INSERIR-ALBUM-"+username+"-");
                    System.out.println("Nome do artista: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s+"-");
                    System.out.println("Nome do album: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s+"-");
                    System.out.println("Descrição do album: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_album);
                        System.out.println(s);
                        menuOp(socket,inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
                else if(op == 2){
                    g_album = g_album.concat("ALTERAR-ALBUM-");
                    AlterarAlbum(g_album, socket, inter);
                }
                else{
                    g_album = g_album.concat("REMOVER-ALBUM-"+username+"-");
                    System.out.println("Nome do artista: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s+"-");
                    System.out.println("Nome do album: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_album);
                        System.out.println(s);
                        menuOp(socket,inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
    }

    private static void AlterarAlbum(String g_album, MulticastSocket socket, RMIInter inter) {  //Menu
        Scanner sc = new Scanner(System.in);
        int op = -1;
        String s = "";

        System.out.println("-------- MENU ALTERAR ALBUM -------");
        System.out.println("1. Alterar título do album");
        System.out.println("2. Alterar descrição do album");

        while (op<1 || op>2 ) {
            if (sc.hasNextInt()) {
                op = sc.nextInt();
                s = sc.nextLine();
                if (op == 1) {
                    g_album = g_album.concat("TITULOA-"+username+"-");
                    System.out.println("Nome do artista: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s+"-");
                    System.out.println("Título do album que quer alterar: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s+"-");
                    System.out.println("Novo título do album: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_album);
                        System.out.println(s);
                        menuOp(socket,inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
                else{
                    g_album = g_album.concat("DESCRICAO-"+username+"-");
                    System.out.println("Nome do artista: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s+"-");
                    System.out.println("Título de album: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s+"-");
                    System.out.println("Nova descrição: ");
                    s = sc.nextLine();
                    g_album = g_album.concat(s);
                    try {
                        //inter.editorCallback();
                        inter = verificaLookup();
                        s = inter.rmi_mc_d(g_album);
                        System.out.println(s);
                        menuOp(socket,inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
            }
            else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
    }

    private static void menuGerirMusica(MulticastSocket socket, RMIInter inter) {   //Menu
        int op = -1;
        String g_musica = "";
        String s = "";

        Scanner sc = new Scanner (System.in);
        System.out.println("-------- MENU GERIR MUSICA -------");
        System.out.println("1. Inserir musica");
        System.out.println("2. Alterar musica");
        System.out.println("3. Remover musica");

        while (op<1 || op>3 ) {
            if (sc.hasNextInt()) {
                op = sc.nextInt();
                sc.nextLine();//guarda o enter
                if(op == 1) {//inserir artista
                    g_musica = g_musica.concat("INSERIR-MUSICA-"+username+"-");
                    System.out.println("Nome de artista: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Título do album: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Título da musica: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Compositores(separar por ;): ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Duração: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_musica);
                        System.out.println(s);
                        menuOp(socket,inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
                else if(op == 2){
                    g_musica = g_musica.concat("ALTERAR-MUSICA-");
                    AlterarMusica(g_musica, socket, inter);
                }
                else{
                    g_musica = g_musica.concat("REMOVER-MUSICA-"+username+"-");
                    System.out.println("Nome de artista: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Título do album: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Título da musica: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_musica);
                        System.out.println(s);
                        menuOp(socket, inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
    }

    private static void AlterarMusica(String g_musica, MulticastSocket socket, RMIInter inter) {    //Menu
        int op = -1;
        Scanner sc = new Scanner (System.in);
        String s = "";

        System.out.println("-------- MENU ALTERAR MUSICA -------");
        System.out.println("1. Alterar título");
        System.out.println("2. Alterar compositores");
        System.out.println("3. Alterar duração");

        while (op<1 || op>3 ) {
            if (sc.hasNextInt()) {
                op = sc.nextInt();
                s = sc.nextLine();//enter
                if(op == 1) {
                    g_musica = g_musica.concat("TITULOM-"+username+"-");
                    System.out.println("Nome de artista: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Título do album: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Atual título da musica: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Título da musica: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_musica);
                        System.out.println(s);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
                else if(op == 2){
                    g_musica = g_musica.concat("COMPOSITORES-"+username+"-");
                    System.out.println("Nome de artista: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Título do album: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Título da musica: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Nome do compositor que quer alterar: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Novo nome do compositor: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_musica);
                        System.out.println(s);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
                else{
                    g_musica = g_musica.concat("DURACAO-"+username+"-");
                    System.out.println("Nome de artista: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Título do album: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Título da musica: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s+"-");
                    System.out.println("Nova duração: ");
                    s = sc.nextLine();
                    g_musica = g_musica.concat(s);
                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(g_musica);
                        System.out.println(s);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
        menuOp(socket,inter);
    }

    private static void pesquisarMusicas(MulticastSocket socket, RMIInter inter) {  //Menu
        int opcao = -1;
        Scanner sc = new Scanner (System.in);
        String s = "";
        String pesq = "PESQUISAR-";

        System.out.println("-------- MENU PESQUISAR MUSICA -------");
        System.out.println("1. Pesquisar por album");
        System.out.println("2. Pesquisar por artista");

        while (opcao<1 || opcao >2) {
            if (sc.hasNextInt()) {
                opcao = sc.nextInt();
                s = sc.nextLine();//enter
                if (opcao == 1) {
                    pesq = pesq.concat("ALBUM-"+username+"-");
                    System.out.println("Nome do album: ");
                    s = sc.nextLine();
                    pesq = pesq.concat(s+"-");

                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(pesq);
                        System.out.println(s);
                        menuOpAlbum(socket, inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                } else {
                    pesq = pesq.concat("ARTISTA-"+username+"-");
                    System.out.println("Nome do artista: ");
                    s = sc.nextLine();
                    pesq = pesq.concat(s+"-");

                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(pesq);
                        System.out.println(s);
                        menuOpAlbum(socket, inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }

                }
            } else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
        //menuOp(socket,inter);
    }

    private static void menuOpAlbum(MulticastSocket socket, RMIInter inter) {   //Menu
        int opcao = -1;
        Scanner sc = new Scanner (System.in);
        String s = "";
        String musica = "";

        System.out.println("-------- MENU -------");
        System.out.println("1. Ver informação de uma musica");
        System.out.println("2. voltar para o menu principal");

        while (opcao<1 || opcao >2) {
            if (sc.hasNextInt()) {
                opcao = sc.nextInt();
                s = sc.nextLine();//enter
                if (opcao == 1) {
                    menuInfo(socket,inter);
                } else {
                    menuOp(socket,inter);
                }
            } else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
    }

    private static void menuInfo(MulticastSocket socket, RMIInter inter) {  //Menu
        int opcao = -1;
        Scanner sc = new Scanner (System.in);
        String s = "";
        String info ="INFORMACAO-";
        String musica = "";

        System.out.println("-------- MENU -------");
        System.out.println("1. Ver informação dos compositores");
        System.out.println("2. Ver informação sobre a duração da musica");
        //System.out.println("0. Voltar para o menu principal");

        while (opcao<1 || opcao >2) {
            if (sc.hasNextInt()) {
                opcao = sc.nextInt();
                s = sc.nextLine();//enter
                if (opcao == 1) {
                    info = info.concat("COMPOSITOR-"+username+"-");
                    System.out.println("Nome da musica: ");
                    s = sc.nextLine();
                    info = info.concat(s);

                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(info);
                        System.out.println(s);
                        menuOp(socket,inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                } else {
                    info = info.concat("DURACAO-"+username+"-");
                    System.out.println("Nome da musica: ");
                    s = sc.nextLine();
                    info = info.concat(s);

                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(info);
                        System.out.println(s);
                        menuOp(socket,inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
    }

    private static void consultarAlbumArtista(MulticastSocket socket,RMIInter inter){   //Menu
        int opcao = -1;
        Scanner sc = new Scanner (System.in);
        String s = "";
        String info ="INFORMACAO-";
        String musica = "";

        System.out.println("-------- MENU -------");
        System.out.println("1. Ver informação de um album");
        System.out.println("2. Ver informação de um artista");
        //System.out.println("0. Voltar para o menu principal");

        while (opcao<1 || opcao >2) {
            if (sc.hasNextInt()) {
                opcao = sc.nextInt();
                s = sc.nextLine();//enter
                if (opcao == 1) {
                    info = info.concat("ALBUM-"+username+"-");
                    System.out.println("Nome do album: ");
                    s = sc.nextLine();
                    info = info.concat(s);

                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(info);
                        System.out.println(s);
                        menuOp(socket,inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                } else {
                    info = info.concat("ARTISTA-"+username+"-");
                    System.out.println("Nome do artista: ");
                    s = sc.nextLine();
                    info = info.concat(s);

                    try {
                        inter = verificaLookup();
                        s = inter.rmi_mc(info);
                        System.out.println(s);
                        menuOp(socket,inter);
                    } catch (RemoteException e) {
                        inter = verificaLookup();
                        //e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Introduza um numero correto");
                sc.nextLine();
            }
        }
    }

    private static void privilegios(MulticastSocket socket, RMIInter inter) {   //Menu
        String s = "";
        Scanner keyboardScanner = new Scanner(System.in);
        String priv = "";
        String readKeyboard;

        priv = priv.concat("PRIVILEGIO-"+username+"-");
        System.out.println("Username a quem quer dar privilegio de ser editor: ");
        readKeyboard = keyboardScanner.nextLine();
        priv = priv.concat(readKeyboard);

        try {
            inter = verificaLookup();
            inter.editorCallback(readKeyboard);
            s = inter.rmi_mc_p(priv);
            System.out.println(s);
            menuOp(socket, inter);
        } catch (RemoteException e) {
            inter = verificaLookup();
            //e.printStackTrace();
        }
    }

    private static void criticas(MulticastSocket socket, RMIInter inter) {  //Menu
        String s = "";
        Scanner keyboardScanner = new Scanner(System.in);
        String crit = "";
        String readKeyboard;

        crit = crit.concat("CRITICA-"+username+"-");
        System.out.println("Nome do artista: ");
        readKeyboard = keyboardScanner.nextLine();
        crit = crit.concat(readKeyboard+"-");
        System.out.println("Título do album a que quer dar a crítica: ");
        readKeyboard = keyboardScanner.nextLine();
        crit = crit.concat(readKeyboard+"-");
        System.out.println("Pontução(0 - 10): ");
        readKeyboard = keyboardScanner.nextLine();
        crit = crit.concat(readKeyboard+"-");
        System.out.println("Descrição: ");
        readKeyboard = keyboardScanner.nextLine();
        //System.out.println(readKeyboard.length());
        while(readKeyboard.length()> 300){
            System.out.println("Descrição: ");
            readKeyboard = keyboardScanner.nextLine();
        }
        crit = crit.concat(readKeyboard);

        try {
            inter = verificaLookup();
            s = inter.rmi_mc(crit);
            System.out.println(s);
            menuOp(socket, inter);
        } catch (RemoteException e) {
            inter = verificaLookup();
            //e.printStackTrace();
        }
    }

    public void notificaEditor(String s){   //Notificação do cliente que é um novo editor
        System.out.println(s);
    }

    public void notificaDescricao(String s){    //Notificação para o cliente que a descrição que escolheu parao album foi alterada
        System.out.println(s);
    }

    public static void main(String args[]) {
        System.getProperties().put("java.security.policy", "policy.all");
        RMIInter inter;
        RMIClient c = null;
        try{
            c = new RMIClient();
        }catch(RemoteException e){
            //e.printStackTrace();
        }
        CBInter cInter;
        inter = verificaLookup();
        try {
            inter.verificaClient();
            //System.out.println(inter.teste());
        } catch (RemoteException e) {
            //e.printStackTrace();
        }
        menu(inter, (CBInter) c);
    }
}