package DropMusicRMI_M;

import java.io.*;
import java.net.*;
import java.util.*;

@SuppressWarnings("Duplicates")

/**
 * @author Lara Trindade e João Miranda
 */

public class MulticastServer extends Thread implements Serializable{
    static private String MULTICAST_ADDRESS = "224.0.224.0";
    static int PORT = 4321;
    static String bd_users;
    static String bd_artistas;
    static String bd_musicas;
    static int port_tcp;
    static String diretoria;
    static String ip;
    static ArrayList<User> users  = new ArrayList<>();
    static ArrayList<User> login = new ArrayList<>();
    static ArrayList<Artista> artistas = new ArrayList<>();
    static ArrayList<Musica_up_down> musica_u_d = new ArrayList<>();

    public synchronized static void main(String[] args) {
        MulticastServer MulticastServer = new MulticastServer();
        //args[0] - BD_usersX.obj       args[1] - BD_artistaX.obj       args[2] - porto_udp
        bd_users = args[0];
        bd_artistas = args[1];
        bd_musicas = args[2];
        port_tcp = Integer.parseInt(args[3]);
        ip = args[4];
        //ip = "localhost";
        diretoria = args[5];

        users = le_fich_obj_users(users,bd_users);
        artistas = le_fich_obj_artistas(artistas,bd_artistas);
        musica_u_d = le_fich_obj_musica_u_d(musica_u_d, bd_musicas);
        MulticastServer.start();

        try {//TCP
            ServerSocket serverSocket = new ServerSocket(port_tcp);
            while (true) {
                Socket clientSocket = serverSocket.accept(); // BLOQUEANTE
                //System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);
                System.out.println("estou no inicio");
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                String aux = in.readUTF();
                System.out.println(aux);
                String[] textoseparado = aux.split("-");

                if (textoseparado[0].compareTo("UPLOAD") == 0) {
                    int flag = 0;
                    for (int k = 0; k < musica_u_d.size(); k++) {
                        if ((musica_u_d.get(k).getTitulo().compareTo(textoseparado[4].toUpperCase()) == 0) && (musica_u_d.get(k).getU().compareTo(textoseparado[1]) == 0)) {// já foi feito o upload da musica
                            flag = 4;
                        }
                    }

                    //encontrar a musica e associar
                    if(flag == 0) {
                        String texto;
                        for (Artista a : artistas) {
                            if (a.getNome().compareTo(textoseparado[2].toUpperCase()) == 0) {//verifica se o artista existe
                                flag = 1;//nao existe albuns
                                System.out.println(flag);
                                for (int k = 0; k < a.getL_albuns().size(); k++) {
                                    if (a.getL_albuns() != null) {//lista de albuns nao é vazia
                                        if (a.getL_albuns().get(k).getTitulo().compareTo(textoseparado[3].toUpperCase()) == 0) {//verifica se o album existe
                                            flag = 2;//nao existe lista de musicas
                                            System.out.println(flag);
                                            if (a.getL_albuns().get(k).getL_Musicas() != null) {//lista de musicas não é vazia
                                                for (int j = 0; j < a.getL_albuns().get(k).getL_Musicas().size(); j++) {
                                                    flag = 3;//a musica nao existe
                                                    System.out.println(flag + "  tamanho da lista de musicas:" + a.getL_albuns().get(k).getL_Musicas().size());
                                                    if (a.getL_albuns().get(k).getL_Musicas().get(j).getTitulo().compareTo(textoseparado[4].toUpperCase()) == 0) {//verifica se a musica existe
                                                        flag = 5;//muisca existe e ele fez upload
                                                        System.out.println("musica existe");

                                                        InputStream inp = clientSocket.getInputStream();
                                                        byte[] b = new byte[1024];
                                                        FileOutputStream fos = new FileOutputStream(new File(diretoria + "/" + textoseparado[4] + ".mp3"), true);
                                                        while (inp.read(b) != -1) {
                                                            fos.write(b);
                                                        }
                                                        fos.close();
                                                        inp.close();
                                                        System.out.println(textoseparado[1]);
                                                        musica_u_d.add(new Musica_up_down(textoseparado[4].toUpperCase(),textoseparado[1] ,diretoria + "/"));
                                                        escreve_fich_obj_musica_u_d(musica_u_d, bd_musicas);

                                                        //clientSocket.close();
                                                        /*serverSocket = new ServerSocket(port_tcp);
                                                        clientSocket = serverSocket.accept(); // BLOQUEANTE
                                                        texto = "type|status; msg|Upload da musica com sucesso";
                                                        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                                                        output.writeUTF(texto);//envia
                                                        output.close();*/
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    else if (flag == 1 || flag == 0 || flag == 2) {//nao existe o artista ou o album ou a musica
                        System.out.println("entrei nesta flag");
                        InputStream inp = clientSocket.getInputStream();
                        byte[] b = new byte[1024];
                        while (inp.read(b) != -1) {
                        }
                        inp.close();
                        //clientSocket.close();

                        /*serverSocket = new ServerSocket(port_tcp);
                        clientSocket = serverSocket.accept(); // BLOQUEANTE
                        texto = "type|status; msg|O artista ou o album ou a musica não existe";
                        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                        output.writeUTF(texto);//envia
                        output.close();*/
                    }

                    else if (flag == 4) {
                        System.out.println("já foi feito o upload de: "+textoseparado[4]+" por: "+textoseparado[1]);
                        InputStream inp = clientSocket.getInputStream();
                        byte[] b = new byte[1024];
                        while (inp.read(b) != -1) {
                        }
                        inp.close();
                        //clientSocket.close();

                        //serverSocket = new ServerSocket(port_tcp);
                        //clientSocket = serverSocket.accept(); // BLOQUEANTE
                        //texto = "type|status; msg|Upload da musica já realizado";
                        //DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                        //System.out.println("estou na flag != 3");
                        //output.writeUTF(texto);//envia
                        //output.close();
                    }
                    in.close();
                    clientSocket.close();
                    //serverSocket.close();
                }
                if (textoseparado[0].compareTo("DOWNLOAD") == 0) {
                    int flag = 0;
                    for (int k = 0; k < musica_u_d.size(); k++) {
                        if (musica_u_d.get(k).getTitulo().compareTo(textoseparado[2].toUpperCase()) == 0) {// encontro a musica
                            if(musica_u_d.get(k).getU().compareTo(textoseparado[1]) == 0) {//verificar se é quem inseriu a musica
                                flag = 1;
                                OutputStream out = clientSocket.getOutputStream();
                                FileInputStream fis = new FileInputStream(musica_u_d.get(k).getDiretoria() + textoseparado[2] + ".mp3");
                                byte[] b = new byte[1024];
                                while (fis.read(b) != -1) {
                                    out.write(b);
                                }
                                out.flush();
                                out.close();
                                clientSocket.close();
                                break;
                            }
                            else if(flag != 1){//verificar se partilharam com ele
                                if(musica_u_d.get(k).getPartilhas() != null){
                                    for(int i =0; i < musica_u_d.get(k).getPartilhas().size(); i++){
                                        if(musica_u_d.get(k).getPartilhas().get(i).compareTo(textoseparado[1]) == 0){
                                            flag = 2;
                                            OutputStream out = clientSocket.getOutputStream();
                                            FileInputStream fis = new FileInputStream(musica_u_d.get(k).getDiretoria() + textoseparado[2] + ".mp3");
                                            byte[] b = new byte[1024];
                                            while (fis.read(b) != -1) {
                                                out.write(b);
                                            }
                                            out.flush();
                                            out.close();
                                            clientSocket.close();
                                            break;
                                        }
                                    }
                                }
                            }
                            /*else if(flag != 1 && flag != 2){//nao faz o download

                            }*/
                        }
                    }
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run() {//UDP
        MulticastSocket socket = null;
        MulticastSocket socket2 = null;
        try {
            ArrayList<Musica> music = new ArrayList<>();

            socket = new MulticastSocket(PORT);  // create socket and bind it RECEBER
            socket2 = new MulticastSocket();  // create socket para ENVIAR apenas
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(group);
            socket2.joinGroup(group);

            while (true) {
                int flag = 0;

                //RECEBE
                byte[] buffer = new byte[500];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                System.out.println(packet.getPort()+" *socket "+socket.getLocalPort()+"*");
                System.out.println(packet.getPort()+" *socket2 "+socket2.getLocalPort()+"*");
                while(packet.getPort() == socket2.getLocalPort()) {
                    buffer = new byte[256];
                    packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                }
                System.out.println("Received packet from " + packet.getAddress().getHostAddress() + " : " + packet.getPort() + " with message:");
                String message = new String(packet.getData(), 0, packet.getLength());
                String[] textoSeparado = message.split("-");

                switch (textoSeparado[0]) {
                    case "UDP|UPLOAD":
                        String auxx = ip+"-"+port_tcp;
                        System.out.println(auxx);
                        buffer = auxx.getBytes();
                        try {
                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                            socket2.send(packet);
                            System.out.println("enviou resposta");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "PARTILHA":
                        int xua=0,xua1 = 0;

                        if((textoSeparado[3].compareTo("publico") == 0) || (textoSeparado[3].compareTo("PUBLICO") == 0) || textoSeparado[3].compareTo("Publico") == 0 )
                            xua = 1;

                        for(int i =0; i < musica_u_d.size(); i++) {
                            System.out.println(musica_u_d.get(i).getTitulo() + "  --------   "+textoSeparado[2].toUpperCase());
                            if (musica_u_d.get(i).getTitulo().compareTo(textoSeparado[2].toUpperCase()) == 0) {
                                if (xua == 1) {
                                    xua1 = 1;
                                    musica_u_d.get(i).setPublico(true);
                                    buffer = "type|status; msg|Partilha feita com sucesso.".getBytes();
                                    try {
                                        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                        socket2.send(packet);
                                        System.out.println("enviou resposta");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (musica_u_d.get(i).getPartilhas() != null) {
                                    if (xua == 0) {
                                        for (User u : users) {
                                            if (u.getNome().compareTo(textoSeparado[3]) == 0) {//o username que meteu existe
                                                xua1=1;
                                                musica_u_d.get(i).getPartilhas().add(textoSeparado[3]);
                                                buffer = "type|status; msg|Partilha feita com sucesso.".getBytes();
                                                try {
                                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                    socket2.send(packet);
                                                    System.out.println("enviou resposta");
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }

                                }
                                if(musica_u_d.get(i).getPartilhas() == null){
                                    ArrayList<String> part = new ArrayList<>();
                                    part.add(textoSeparado[3]);
                                    musica_u_d.get(i).setPartilhas(part);
                                    buffer = "type|status; msg|Partilha feita com sucesso.".getBytes();
                                    try {
                                        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                        socket2.send(packet);
                                        System.out.println("enviou resposta");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        if(xua1 == 0){
                            buffer = "type|status; msg|O título da musica ou o username que inseriu não existe".getBytes();
                            try {
                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                socket2.send(packet);
                                System.out.println("enviou resposta");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "UDP|DOWNLOAD":
                        System.out.println("UDP|DOWNLOAD --> TAMANHO DO ARRAY DE UP DE MUSICAS: " + musica_u_d.size());
                        for(int i =0; i < musica_u_d.size(); i++){
                            if(musica_u_d.get(i).getTitulo().compareTo(textoSeparado[2].toUpperCase()) == 0){
                                if(musica_u_d.get(i).isPublico() == true){
                                    flag = 1;
                                    String auxx2 = ip+"-"+port_tcp;
                                    System.out.println(auxx2);
                                    buffer = auxx2.getBytes();
                                    try {
                                        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                        socket2.send(packet);
                                        System.out.println("enviou resposta");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (flag == 0) {
                                    if (musica_u_d.get(i).getU().compareTo(textoSeparado[1]) == 0){
                                        flag = 1;
                                        String auxx2 = ip+"-"+port_tcp;
                                        System.out.println(auxx2);
                                        buffer = auxx2.getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                if(flag == 0){
                                    if(musica_u_d.get(i).getPartilhas() != null){
                                        for(int k = 0; k < musica_u_d.get(i).getPartilhas().size(); k++){
                                            //System.out.println(musica_u_d.get(i).getPartilhas().get(k) +"    -------    "+ textoSeparado[1]);
                                            if(musica_u_d.get(i).getPartilhas().get(k).compareTo(textoSeparado[1]) == 0){//a musica foi partilhada com ele
                                                flag = 1;
                                                String auxx2 = ip+"-"+port_tcp;
                                                System.out.println(auxx2);
                                                buffer = auxx2.getBytes();
                                                try {
                                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                    socket2.send(packet);
                                                    System.out.println("enviou resposta");
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(flag == 0){
                            buffer = "type|status; msg|Não foi feito o upload dessa musica ou a musica não foi partilhada consigo.".getBytes();
                            try {
                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                socket2.send(packet);
                                System.out.println("enviou resposta");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "USER":
                        User u = new User(textoSeparado[1], textoSeparado[2]);//cria o user
                        System.out.println("criou user");
                        if (users.size() == 0) {//primeiro user a ser registado -- >EDITOR
                            u.setEditor(1);
                            users.add(u);
                            escreve_fich_obj_users(users,bd_users);
                            buffer = "type|status; logged|off; msg|Welcome to DropMusic | EDITOR".getBytes();
                            try {
                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                socket2.send(packet);
                                System.out.println("enviou resposta");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            for (User us : users) {
                                if (us.getNome().compareTo(textoSeparado[1]) == 0) {
                                    buffer = "type|status; logged|failed; msg|Username já utilizado".getBytes();
                                    flag=1;
                                    try {
                                        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                        socket2.send(packet);
                                        System.out.println("enviou resposta");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if(flag==0){
                                users.add(u);buffer = "type|status; logged|off; msg|Welcome to DropMusic".getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;

                    case "LOGIN":
                        for (User us : users) {
                            if(us.getNome().compareTo(textoSeparado[1]) == 0 && us.getPass().compareTo(textoSeparado[2])==0){
                                flag=1;
                                for(User log : login){
                                    if(log.getNome().compareTo(textoSeparado[1]) == 0){
                                        flag = 2;
                                        buffer = "type|status; msg|User já fez login.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                if(flag == 1) {
                                    login.add(us);
                                    if(us.getNotificacoes() == null || us.getNotificacoes().size() == 0 ) {
                                        buffer = "type|status; msg|Login com sucesso.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else{
                                        String s =  us.getNotificacoes().size() + "«type|status; msg|Login com sucesso.";
                                        buffer = s.getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        for(int i = 0; i< us.getNotificacoes().size(); i++){
                                            buffer = us.getNotificacoes().get(i).getBytes();
                                            try {
                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                socket2.send(packet);
                                                System.out.println("enviou resposta");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        ArrayList<String> ss = new ArrayList<>();
                                        us.setNotificacoes(ss);//coloca as notificacoes de inicio
                                    }
                                }
                            }
                        }
                        if(flag==0){
                            buffer = "type|status; msg|Utilizador necessita de criar utilizador.".getBytes();
                            try {
                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                socket2.send(packet);
                                System.out.println("enviou resposta");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "INSERIR":
                        flag = 4;
                        for(User log : login){
                            if((log.getNome().compareTo(textoSeparado[2])== 0 && log.getEditor() ==  1)){//o user é editor
                                flag = 0;
                            }
                        }
                        if(flag == 4){
                            buffer = "type|status; msg|Utilizador necessita de ser editor.".getBytes();
                            try {
                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                socket2.send(packet);
                                System.out.println("enviou resposta");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if(flag == 0) {//o user é editor
                            switch (textoSeparado[1]) {
                                case "ARTISTA":
                                    for(Artista a: artistas){
                                        if(a.getNome().compareTo(textoSeparado[3].toUpperCase()) == 0){//verifica se o artista já existe
                                            flag = 1;
                                            buffer = "type|status; msg|O artista já existe.".getBytes();
                                            try {
                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                socket2.send(packet);
                                                System.out.println("enviou resposta");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    if(flag == 0){
                                        for(User us : users){
                                            if(us.getNome().compareTo(textoSeparado[2]) == 0){//encontra o user no array
                                                Artista art = new Artista(textoSeparado[3].toUpperCase(),us);//cria o artista e associa  user que o adicionou
                                                artistas.add(art);
                                                escreve_fich_obj_artistas(artistas, bd_artistas);
                                                buffer = "type|status; msg|Artista inserido com sucesso.".getBytes();
                                                try {
                                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                    socket2.send(packet);
                                                    System.out.println("enviou resposta");
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                    break;
                                case "ALBUM":
                                    for(Artista a: artistas){
                                        if(a.getNome().compareTo(textoSeparado[3].toUpperCase()) == 0){//verifica se o artista existe
                                            flag = 1;
                                            if(a.getL_albuns() != null)
                                            for(int i = 0; i < a.getL_albuns().size(); i++){
                                                if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[4].toUpperCase()) == 0) {//o album já existe
                                                    flag = 2;
                                                    buffer = "type|status; msg|O album já existe.".getBytes();
                                                    try {
                                                        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                        socket2.send(packet);
                                                        System.out.println("enviou resposta");
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if(flag == 1){
                                        for(User us : users){
                                            if(us.getNome().compareTo(textoSeparado[2]) == 0){//encontra o user no array
                                                for(Artista a : artistas){
                                                    if(a.getNome().compareTo(textoSeparado[3].toUpperCase()) == 0){//vai buscar o artista
                                                        Album alb = new Album(textoSeparado[4].toUpperCase(),textoSeparado[5],us);//cria o album e associa o user
                                                        if(a.getL_albuns() == null){//o arraylist de albuns esta vazio
                                                            ArrayList<Album> aa = new ArrayList<>();
                                                            aa.add(alb);
                                                            a.setL_albuns(aa);
                                                        }
                                                        else {
                                                            a.getL_albuns().add(alb);
                                                        }
                                                        escreve_fich_obj_artistas(artistas, bd_artistas);
                                                        buffer = "type|status; msg|Album inserido com sucesso.".getBytes();
                                                        try {
                                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                            socket2.send(packet);
                                                            System.out.println("enviou resposta");
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if(flag == 0){
                                        buffer = "type|status; msg|O artista não existe.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case "MUSICA":
                                    for(Artista a: artistas){
                                        if(a.getNome().compareTo(textoSeparado[3].toUpperCase()) == 0){//verifica se o artista existe
                                            flag = 1;
                                            for(int i = 0; i < a.getL_albuns().size(); i++){
                                                if(a.getL_albuns() != null) {//lista de albuns nao é vazia
                                                    if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[4].toUpperCase()) == 0) {//verifica se o album existe
                                                        flag = 2;
                                                        for (int j = 0; j < a.getL_albuns().get(i).getL_Musicas().size(); j++) {
                                                            if(a.getL_albuns().get(i).getL_Musicas() != null) {//lista de musicas não é vazia
                                                                flag = 3;
                                                                if (a.getL_albuns().get(i).getL_Musicas().get(j).getTitulo().compareTo(textoSeparado[5].toUpperCase()) == 0) {//verifica se a musica existe
                                                                    flag = 4;
                                                                    buffer = "type|status; msg|A musica já existe.".getBytes();
                                                                    try {
                                                                        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                                        socket2.send(packet);
                                                                        System.out.println("enviou resposta");
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(flag == 0 || flag == 1){
                                        buffer = "type|status; msg|O artista ou o album não existe.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    System.out.println(flag);
                                    if(flag == 2){
                                        for(Artista a: artistas){
                                            if(a.getNome().compareTo(textoSeparado[3].toUpperCase()) == 0){//vai buscar o artista
                                                for(int i = 0; i < a.getL_albuns().size(); i++){
                                                    if(a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[4].toUpperCase()) == 0){//o album existe
                                                        ArrayList<String> compositores = new ArrayList<>();
                                                        String[] comp = textoSeparado[6].split(";");
                                                        for(int k=0; k<comp.length; k++){
                                                            compositores.add(comp[k].toUpperCase());
                                                        }
                                                        Musica m = new Musica(textoSeparado[5].toUpperCase(),compositores,textoSeparado[7]/*,us*/);//cria a musica
                                                        ArrayList<Musica> musi = new ArrayList<>();
                                                        musi.add(m);
                                                        a.getL_albuns().get(i).setL_Musicas(musi);
                                                        escreve_fich_obj_artistas(artistas, bd_artistas);
                                                        buffer = "type|status; msg|A musica foi inserida com sucesso.".getBytes();
                                                        try {
                                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                            socket2.send(packet);
                                                            System.out.println("enviou resposta");
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                    if(flag == 3){
                                        for(Artista a: artistas){
                                            if(a.getNome().compareTo(textoSeparado[3].toUpperCase()) == 0){//vai buscar o artista
                                                for(int i = 0; i < a.getL_albuns().size(); i++){
                                                    if(a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[4].toUpperCase()) == 0){//o album existe
                                                        ArrayList<String> compositores = new ArrayList<>();
                                                        String[] comp = textoSeparado[6].split(";");
                                                        for(int k=0; k<comp.length; k++){
                                                            compositores.add(comp[k].toUpperCase());
                                                        }
                                                        Musica m = new Musica(textoSeparado[5].toUpperCase(),compositores,textoSeparado[7]/*,us*/);//cria a musica
                                                        a.getL_albuns().get(i).getL_Musicas().add(m);
                                                        escreve_fich_obj_artistas(artistas, bd_artistas);
                                                        buffer = "type|status; msg|A musica foi inserida com sucesso.".getBytes();
                                                        try {
                                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                            socket2.send(packet);
                                                            System.out.println("enviou resposta");
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    break;
                            }
                        }
                        break;
                    case "ALTERAR":
                        flag = 4;
                        for(User log : login){
                            System.out.println(log.getNome());
                            if((log.getNome().compareTo(textoSeparado[3])== 0 && log.getEditor() ==  1)){//o user é editor
                                flag = 0;
                            }
                        }
                        if(flag == 4){
                            buffer = "type|status; msg|Utilizador necessita de ser editor.".getBytes();
                            try {
                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                socket2.send(packet);
                                System.out.println("enviou resposta");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if(flag == 0) {//o user é editor
                            switch (textoSeparado[1]) {
                                case "ARTISTA":
                                    if(textoSeparado[2].compareTo("NOME") == 0){
                                        for (Artista a : artistas) {
                                            if (a.getNome().compareTo(textoSeparado[4].toUpperCase()) == 0) {//verifica se o artista existe
                                                flag = 1;
                                                a.setNome(textoSeparado[5].toUpperCase());//altera o nome do artista
                                                escreve_fich_obj_artistas(artistas, bd_artistas);
                                                buffer = "type|status; msg|Artista alterado com sucesso.".getBytes();
                                                try {
                                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                    socket2.send(packet);
                                                    System.out.println("enviou resposta");
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        if(flag == 0){
                                            buffer = "type|status; msg|Artista não existe.".getBytes();
                                            try {
                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                socket2.send(packet);
                                                System.out.println("enviou resposta");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    break;
                                case "ALBUM":
                                    if(textoSeparado[2].compareTo("TITULOA") == 0) {
                                        for (Artista a : artistas) {
                                            if (a.getNome().compareTo(textoSeparado[4].toUpperCase()) == 0) {//verifica se o artista existe
                                                flag = 1;
                                                if(a.getL_albuns() != null) {
                                                    for (int i = 0; i < a.getL_albuns().size(); i++) {
                                                        if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[5].toUpperCase()) == 0) {//verifica se o album existe
                                                            flag = 2;
                                                            a.getL_albuns().get(i).setTitulo(textoSeparado[6].toUpperCase());//altera o titulo
                                                            escreve_fich_obj_artistas(artistas, bd_artistas);
                                                            System.out.println(a.getL_albuns().get(i).getTitulo());
                                                            buffer = "type|status; msg|Nome do album alterado com sucesso.".getBytes();
                                                            try {
                                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                                socket2.send(packet);
                                                                System.out.println("enviou resposta");
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (flag == 0 || flag == 1) {
                                            buffer = "type|status; msg|Artista ou título da musica não existe.".getBytes();
                                            try {
                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                socket2.send(packet);
                                                System.out.println("enviou resposta");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    if(textoSeparado[2].compareTo("DESCRICAO") == 0) {
                                        String fim = "";
                                        for(User us : login) {
                                            if (us.getNome().compareTo(textoSeparado[3]) == 0) {//encontra o user no array que esta a fazer a operaçao
                                                for (Artista a : artistas) {
                                                    if (a.getNome().compareTo(textoSeparado[4].toUpperCase()) == 0) {//verifica se o artista existe
                                                        flag = 1;
                                                        if (a.getL_albuns() != null) {//lista de albuns nao esta vazia
                                                            for (int i = 0; i < a.getL_albuns().size(); i++) {
                                                                if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[5].toUpperCase()) == 0) {//o album existe
                                                                    flag = 2;
                                                                    a.getL_albuns().get(i).setDescricao(textoSeparado[6]);//altera a descrição

                                                                    ArrayList<User> temp_on = new ArrayList<>();
                                                                    ArrayList<User> temp_off = new ArrayList<>();

                                                                    for(int j = 0; j < a.getL_albuns().get(i).getEditores().size(); j++) {//quantos editores o album tem
                                                                        for (User log : login) {
                                                                            System.out.println(a.getL_albuns().get(i).getEditores().get(j).getNome() + "  " + log.getNome());
                                                                            if (a.getL_albuns().get(i).getEditores().get(j).getNome().compareTo(log.getNome()) == 0) {//os users q estao on
                                                                                if(a.getL_albuns().get(i).getEditores().get(j).getNome().compareTo(us.getNome()) != 0){
                                                                                    temp_on.add(a.getL_albuns().get(i).getEditores().get(j));
                                                                                    fim = fim.concat(a.getL_albuns().get(i).getEditores().get(j).getNome()+";");
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    fim = fim.concat("»"+a.getL_albuns().get(i).getTitulo()+"«");
                                                                    flag = 0;

                                                                    for(int j = 0; j < a.getL_albuns().get(i).getEditores().size(); j++) {//lista de editores
                                                                        System.out.println(a.getL_albuns().get(i).getEditores().get(j).getNome());
                                                                        for(int k =0 ; k < temp_on.size(); k++){//quais dos editores estao online
                                                                            if(a.getL_albuns().get(i).getEditores().get(j).getNome().compareTo(temp_on.get(k).getNome()) == 0){
                                                                                flag = 1;
                                                                            }
                                                                        }
                                                                        if(flag == 0){
                                                                            temp_off.add(a.getL_albuns().get(i).getEditores().get(j));
                                                                        }
                                                                    }

                                                                    for(User uu : users){//caso o user esteja offline
                                                                        for(int ii = 0; ii < temp_off.size(); ii++){
                                                                            if(temp_off.get(ii).getNome().compareTo(uu.getNome()) == 0) {
                                                                                if (uu.getNotificacoes() == null) {
                                                                                    ArrayList<String> not = new ArrayList<>();
                                                                                    not.add("A descição do album " + a.getL_albuns().get(i).getTitulo() + " foi alterada por " + us.getNome() + ".");
                                                                                    uu.setNotificacoes(not);
                                                                                } else
                                                                                    uu.getNotificacoes().add("A descição do album " + a.getL_albuns().get(i).getTitulo() + " foi alterada por " + us.getNome() + ".");
                                                                            }
                                                                        }
                                                                    }

                                                                    for(int o =0; o < a.getL_albuns().get(i).getEditores().size(); o++){//nmr de editores do album
                                                                        if(a.getL_albuns().get(i).getEditores().get(o).getNome().compareTo(us.getNome()) == 0){//o editor ja esta no array de edicoes
                                                                            flag = 20;//se o editor já estiver no array de ediçoes
                                                                        }
                                                                    }

                                                                    if(flag != 20){
                                                                        a.getL_albuns().get(i).getEditores().add(us);//adiciona o editor à lista de editores
                                                                    }

                                                                    escreve_fich_obj_artistas(artistas, bd_artistas);
                                                                    fim = fim.concat("type|status; msg|Descrição atualizada.");
                                                                    System.out.println(fim);
                                                                    buffer = fim.getBytes();
                                                                    try {
                                                                        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                                        socket2.send(packet);
                                                                        System.out.println("enviou resposta");
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (flag == 0 || flag == 1) {
                                            buffer = "type|status; msg|Artista ou título da musica não existe.".getBytes();
                                            try {
                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                socket2.send(packet);
                                                System.out.println("enviou resposta");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    break;
                                case "MUSICA":
                                    if(textoSeparado[2].compareTo("TITULOM") == 0) {
                                        for (Artista a : artistas) {
                                            if (a.getNome().compareTo(textoSeparado[4].toUpperCase()) == 0) {//verifica se o artista existe
                                                flag = 1;
                                                for (int i = 0; i < a.getL_albuns().size(); i++) {
                                                    if (a.getL_albuns() != null) {//lista de albuns nao é vazia
                                                        if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[5].toUpperCase()) == 0) {//o album existe
                                                            flag = 2;
                                                            for (int j = 0; j < a.getL_albuns().get(i).getL_Musicas().size(); j++) {
                                                                if (a.getL_albuns().get(i).getL_Musicas() != null) {//lista de musicas nao é vazia
                                                                    flag = 3;
                                                                    if (a.getL_albuns().get(i).getL_Musicas().get(j).getTitulo().compareTo(textoSeparado[6].toUpperCase()) == 0) {//se o titulo da musica existe
                                                                        flag = 5;
                                                                        if (a.getL_albuns().get(i).getL_Musicas().get(j).getTitulo().compareTo(textoSeparado[7].toUpperCase()) == 0) {//se o novo titulo da musica existe
                                                                            flag = 6;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        System.out.println(flag);
                                        if (flag == 0 || flag == 1 || flag == 2 || flag == 3) {
                                            buffer = "type|status; msg|O artista não existe ou album nao existe ou atual titulo da musica não existe.".getBytes();
                                            try {
                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                socket2.send(packet);
                                                System.out.println("enviou resposta");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        if (flag == 5) {//alterar titulo musica
                                            for (Artista a : artistas) {
                                                if (a.getNome().compareTo(textoSeparado[4].toUpperCase()) == 0) {//verifica se o artista existe
                                                    for (int i = 0; i < a.getL_albuns().size(); i++) {
                                                        if (a.getL_albuns() != null) {//lista de albuns nao é vazia
                                                            if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[5].toUpperCase()) == 0) {//o album existe
                                                                for (int j = 0; j < a.getL_albuns().get(i).getL_Musicas().size(); j++) {
                                                                    if (a.getL_albuns().get(i).getL_Musicas() != null) {//lista de musicas nao é vazia
                                                                        if (a.getL_albuns().get(i).getL_Musicas().get(j).getTitulo().compareTo(textoSeparado[6].toUpperCase()) == 0) {//se o titulo da musica existe
                                                                            a.getL_albuns().get(i).getL_Musicas().get(j).setTitulo(textoSeparado[7].toUpperCase());
                                                                            System.out.println(a.getL_albuns().get(i).getL_Musicas().get(j).getTitulo());
                                                                            buffer = "type|status; msg|Título da musica alterado com sucesso.".getBytes();
                                                                            try {
                                                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                                                socket2.send(packet);
                                                                                System.out.println("enviou resposta");
                                                                            } catch (IOException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (flag == 6) {
                                            buffer = "type|status; msg|O título para o qual quer alterar já existe.".getBytes();
                                            try {
                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                socket2.send(packet);
                                                System.out.println("enviou resposta");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    if(textoSeparado[2].compareTo("COMPOSITORES") == 0) {
                                        for (Artista a : artistas) {
                                            if (a.getNome().compareTo(textoSeparado[4].toUpperCase()) == 0) {//verifica se o artista existe
                                                flag = 1;//nao existe o album
                                                for (int i = 0; i < a.getL_albuns().size(); i++) {
                                                    if (a.getL_albuns() != null) {//lista de albuns nao é vazia
                                                        if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[5].toUpperCase()) == 0) {//o album existe
                                                            flag = 2;//nao existe a musica
                                                            for (int j = 0; j < a.getL_albuns().get(i).getL_Musicas().size(); j++) {
                                                                if (a.getL_albuns().get(i).getL_Musicas() != null) {//lista de musicas não é vazia
                                                                    if (a.getL_albuns().get(i).getL_Musicas().get(j).getTitulo().compareTo(textoSeparado[6].toUpperCase()) == 0) {// a musica existe
                                                                        flag = 3;//compositor que quer alterarb nao existe
                                                                        System.out.println(a.getL_albuns().get(i).getL_Musicas().get(j).getCompositores().size());
                                                                        for(int k = 0; k < a.getL_albuns().get(i).getL_Musicas().get(j).getCompositores().size(); k++ ){//vai buscar a lista de compositores
                                                                            System.out.println(a.getL_albuns().get(i).getL_Musicas().get(j).getCompositores().get(k));
                                                                            if(a.getL_albuns().get(i).getL_Musicas().get(j).getCompositores().get(k).compareTo(textoSeparado[7].toUpperCase()) == 0){//verifica se a lista tem o atual compositor
                                                                                flag = 5;//o novo compositor que quer alterar nao existe --> tudo bem
                                                                                if(a.getL_albuns().get(i).getL_Musicas().get(j).getCompositores().get(k).compareTo(textoSeparado[8].toUpperCase()) == 0){//verifica se o novo compositor esta na lista
                                                                                    flag =6;//o novo compositor que quer inserir já existe
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        System.out.println(flag);
                                        if (flag == 0 || flag == 1 || flag == 2 || flag == 3){
                                            buffer = "type|status; msg|O artista não existe ou album nao existe ou atual titulo da musica não existe ou o compositor que quer alterar nao existe.".getBytes();
                                            try {
                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                socket2.send(packet);
                                                System.out.println("enviou resposta");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        if(flag == 6){
                                            buffer = "type|status; msg|O compositor já existe.".getBytes();
                                            try {
                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                socket2.send(packet);
                                                System.out.println("enviou resposta");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        if (flag == 5) {//alterar compositor
                                            for (Artista a : artistas) {
                                                if (a.getNome().compareTo(textoSeparado[4].toUpperCase()) == 0) {//verifica se o artista existe
                                                    for (int i = 0; i < a.getL_albuns().size(); i++) {
                                                        if (a.getL_albuns() != null) {//lista de albuns nao é vazia
                                                            if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[5].toUpperCase()) == 0) {//o album existe
                                                                for (int j = 0; j < a.getL_albuns().get(i).getL_Musicas().size(); j++) {
                                                                    if (a.getL_albuns().get(i).getL_Musicas() != null) {//lista de musicas não é vazia
                                                                        if (a.getL_albuns().get(i).getL_Musicas().get(j).getTitulo().compareTo(textoSeparado[6].toUpperCase()) == 0) {// a musica existe
                                                                            for(int k = 0; k < a.getL_albuns().get(i).getL_Musicas().get(j).getCompositores().size(); k++ ){//vai buscar a lista de compositores
                                                                                if(a.getL_albuns().get(i).getL_Musicas().get(j).getCompositores().get(k).compareTo(textoSeparado[7].toUpperCase()) == 0){//verifica se a lista tem o atual compositor
                                                                                    a.getL_albuns().get(i).getL_Musicas().get(j).getCompositores().add(textoSeparado[8].toUpperCase());
                                                                                    escreve_fich_obj_artistas(artistas, bd_artistas);
                                                                                    buffer = "type|status; msg|Compositor alterado com sucesso.".getBytes();
                                                                                    try {
                                                                                        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                                                        socket2.send(packet);
                                                                                        System.out.println("enviou resposta");
                                                                                    } catch (IOException e) {
                                                                                        e.printStackTrace();
                                                                                    }

                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(textoSeparado[2].compareTo("DURACAO") == 0) {
                                        for (Artista a : artistas) {
                                            if (a.getNome().compareTo(textoSeparado[4].toUpperCase()) == 0) {//verifica se o artista existe
                                                flag = 1;
                                                for (int i = 0; i < a.getL_albuns().size(); i++) {
                                                    if (a.getL_albuns() != null) {//lista de albuns nao é vazia
                                                        if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[5].toUpperCase()) == 0) {//o album existe
                                                            flag = 2;
                                                            for (int j = 0; j < a.getL_albuns().get(i).getL_Musicas().size(); j++) {
                                                                if (a.getL_albuns().get(i).getL_Musicas() != null) {//lista de musicas é vazia
                                                                    flag = 3;
                                                                    if (a.getL_albuns().get(i).getL_Musicas().get(j).getTitulo().compareTo(textoSeparado[6].toUpperCase()) == 0) {//a musica existe
                                                                        flag = 5;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if (flag == 0 || flag == 1 || flag == 2 || flag == 3) {
                                            buffer = "type|status; msg|O artista não existe ou album nao existe ou atual titulo da musica não existe.".getBytes();
                                            try {
                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                socket2.send(packet);
                                                System.out.println("enviou resposta");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        if (flag == 5) {//alterar duracao
                                            for (Artista a : artistas) {
                                                if (a.getNome().compareTo(textoSeparado[4].toUpperCase()) == 0) {//verifica se o artista existe
                                                    for (int i = 0; i < a.getL_albuns().size(); i++) {
                                                        if (a.getL_albuns() != null) {//lista de albuns nao é vazia
                                                            if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[5].toUpperCase()) == 0) {//o album existe
                                                                for (int j = 0; j < a.getL_albuns().get(i).getL_Musicas().size(); j++) {
                                                                    if (a.getL_albuns().get(i).getL_Musicas() != null) {//lista de musicas é vazia
                                                                        if (a.getL_albuns().get(i).getL_Musicas().get(j).getTitulo().compareTo(textoSeparado[6].toUpperCase()) == 0) {
                                                                            a.getL_albuns().get(i).getL_Musicas().get(j).setDuracao(textoSeparado[7].toUpperCase());
                                                                            escreve_fich_obj_artistas(artistas, bd_artistas);
                                                                            buffer = "type|status; msg|A duração foi alterada com sucesso.".getBytes();
                                                                            try {
                                                                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                                                socket2.send(packet);
                                                                                System.out.println("enviou resposta");
                                                                            } catch (IOException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }
                                    break;
                            }
                        }
                        break;
                    case "REMOVER":
                        flag = 4;
                        for(User log : login){
                            if((log.getNome().compareTo(textoSeparado[2])== 0 && log.getEditor() ==  1)){//o user é editor
                                flag = 0;
                            }
                        }
                        if(flag == 4){
                            buffer = "type|status; msg|Utilizador necessita de ser editor.".getBytes();
                            try {
                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                socket2.send(packet);
                                System.out.println("enviou resposta");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if(flag == 0) {//o user é editor
                            //System.out.println("else if");
                            switch (textoSeparado[1]) {
                                case "ARTISTA":
                                    Artista art = null;
                                    for(Artista a: artistas){
                                        if(a.getNome().compareTo(textoSeparado[3].toUpperCase()) == 0){//verifica se o artista já existe
                                            art = a;
                                            flag = 1;
                                        }
                                    }
                                    if(flag == 0){
                                        buffer = "type|status; msg|Artista não existe.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (flag == 1){
                                        artistas.remove(art);
                                        escreve_fich_obj_artistas(artistas, bd_artistas);
                                        buffer = "type|status; msg|Artista removido com sucesso.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case "ALBUM":
                                    Album alb = null;
                                    Artista artis = null;
                                    for(Artista a: artistas){
                                        if(a.getNome().compareTo(textoSeparado[3].toUpperCase()) == 0){//verifica se o artista existe
                                            flag = 1;
                                            if(a.getL_albuns() != null){//verifica se existe uma lista de musicas
                                                for(int i = 0; i < a.getL_albuns().size(); i++){
                                                    if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[4].toUpperCase()) == 0) {//o album existe
                                                        flag = 2;
                                                        artis = a;
                                                        alb = a.getL_albuns().get(i);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if(flag == 1 || flag == 0){
                                        buffer = "type|status; msg|Artista ou album não existe.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                           e.printStackTrace();
                                        }
                                    }

                                    if(flag == 2){
                                        artis.getL_albuns().remove(alb);
                                        escreve_fich_obj_artistas(artistas, bd_artistas);
                                        buffer = "type|status; msg|Album removido com sucesso.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case "MUSICA":
                                    Artista artist = null;
                                    int albu = 0;
                                    Musica m = null;
                                    for(Artista a: artistas){
                                        if(a.getNome().compareTo(textoSeparado[3].toUpperCase()) == 0){//verifica se o artista existe
                                            flag = 1;
                                            for(int i = 0; i < a.getL_albuns().size(); i++){
                                                if(a.getL_albuns() != null) {//lista de albuns nao é vazia
                                                    if (a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[4].toUpperCase()) == 0) {//o album existe
                                                        flag = 2;
                                                        for (int j = 0; j < a.getL_albuns().get(i).getL_Musicas().size(); j++) {
                                                            if(a.getL_albuns().get(i).getL_Musicas() != null) {//lista de musicas é vazia
                                                                if (a.getL_albuns().get(i).getL_Musicas().get(j).getTitulo().compareTo(textoSeparado[5].toUpperCase()) == 0) {
                                                                    flag = 3;
                                                                    artist = a;
                                                                    albu = i;
                                                                    m = a.getL_albuns().get(i).getL_Musicas().get(j);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(flag == 0 || flag == 1){
                                        buffer = "type|status; msg|O artista ou o album não existe.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if(flag == 3){
                                        artist.getL_albuns().get(albu).getL_Musicas().remove(m);
                                        escreve_fich_obj_artistas(artistas, bd_artistas);
                                        buffer = "type|status; msg|Musica removida com sucesso.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                            }
                        }
                        break;
                    case "CRITICA":
                        for(Artista a : artistas){
                            if(a.getNome().compareTo(textoSeparado[2].toUpperCase()) == 0){//o artista existe
                                if(a.getL_albuns() != null) {
                                    for (int i = 0; i < a.getL_albuns().size(); i++) {
                                        if(a.getL_albuns().get(i).getTitulo().compareTo(textoSeparado[3].toUpperCase()) == 0){//o album existe
                                            for(User us : users) {
                                                if(us.getNome().compareTo(textoSeparado[1]) == 0) {//user
                                                    if(a.getL_albuns().get(i).getCriticas() == null){
                                                        String xx = textoSeparado[4];
                                                        int x = Character.getNumericValue(xx.charAt(0));
                                                        Critica c = new Critica(us,x,textoSeparado[5]);
                                                        ArrayList<Critica> cri = new ArrayList<>();
                                                        cri.add(c);
                                                        a.getL_albuns().get(i).setCriticas(cri);
                                                        buffer = "type|status; msg|Critica adicionada com sucesso".getBytes();
                                                        try {
                                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                            socket2.send(packet);
                                                            System.out.println("enviou resposta");
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    else{
                                                        String xx = textoSeparado[4];
                                                        int x = Character.getNumericValue(xx.charAt(0));
                                                        Critica c = new Critica(us,x,textoSeparado[5]);
                                                        a.getL_albuns().get(i).getCriticas().add(c);
                                                        buffer = "type|status; msg|Critica adicionada com sucesso".getBytes();
                                                        try {
                                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                            socket2.send(packet);
                                                            System.out.println("enviou resposta");
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                        if(flag == 0){
                            buffer = "type|status; msg|Artista ou album não existe".getBytes();
                            try {
                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                socket2.send(packet);
                                System.out.println("enviou resposta");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "PESQUISAR":
                        flag = 0;
                        String messg = "type|status; msg|Musicas encontradas:\n";
                        if(textoSeparado[1].compareTo("ALBUM") == 0){
                            for(Artista a: artistas){
                                if(a.getL_albuns() != null){//existe lista de albuns
                                    for(int i = 0; i < a.getL_albuns().size(); i++){
                                        String[] s = a.getL_albuns().get(i).getTitulo().split(" ");//separa as palavras do titulo do album
                                        String[] ss = textoSeparado[3].toUpperCase().split(" ");
                                        for(int j =0; j < s.length; j++) {
                                            for (int kk = 0; kk < ss.length; kk++){
                                                if(s[j].compareTo(ss[kk]) == 0){//encontra uma das palavras do album
                                                    if (a.getL_albuns().get(i).getL_Musicas() != null) {//existe lista de musicas
                                                        //System.out.println("++++++++++++++++++"+a.getL_albuns().get(i).getL_Musicas().size());
                                                        for (int k = 0; k < a.getL_albuns().get(i).getL_Musicas().size(); k++) {
                                                            flag = 6;
                                                            music.add(a.getL_albuns().get(i).getL_Musicas().get(k));
                                                            messg = messg.concat("----> "+a.getL_albuns().get(i).getL_Musicas().get(k).getTitulo()+"\n");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if(flag == 6){
                                buffer = messg.getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(flag == 0){
                                buffer = "type|status; msg|Não existem musicas associadas".getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        else if(textoSeparado[1].compareTo("ARTISTA") == 0){
                            for(Artista a: artistas){
                                String[] s = a.getNome().split(" ");
                                String[] ss = textoSeparado[3].toUpperCase().split(" ");
                                for(int j =0; j < s.length; j++) {
                                    for (int kk = 0; kk < ss.length; kk++) {
                                        if(s[j].compareTo(ss[kk]) == 0) {//encontra uma das palavras do artista
                                            if (a.getL_albuns() != null) {
                                                for (int i = 0; i < a.getL_albuns().size(); i++) {
                                                    if (a.getL_albuns().get(i).getL_Musicas() != null) {
                                                        for (int k = 0; k < a.getL_albuns().get(i).getL_Musicas().size(); k++) {
                                                            flag = 6;
                                                            music.add(a.getL_albuns().get(i).getL_Musicas().get(k));
                                                            messg = messg.concat("----> " + a.getL_albuns().get(i).getL_Musicas().get(k).getTitulo() + "\n");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if(flag == 6){
                                buffer = messg.getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(flag == 0){
                                buffer = "type|status; msg|Não existem musicas associadas".getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    case "INFORMACAO":
                        if(textoSeparado[1].compareTo("COMPOSITOR") == 0){
                            String aux = "type|status; msg|Informação do(s) compositor(es):\n";
                            for(int i = 0; i < music.size(); i++){
                                if(music.get(i).getTitulo().compareTo(textoSeparado[3].toUpperCase()) == 0){
                                    for(int j=0; j < music.get(i).getCompositores().size(); j++){
                                        aux = aux.concat("--> "+music.get(i).getCompositores().get(j)+"\n");
                                        flag = 1;
                                    }
                                }
                            }
                            if(flag == 1){
                                buffer = aux.getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else if(textoSeparado[1].compareTo("DURACAO") == 0){
                            String aux = "type|status; msg|Informação da duração da musica:  ";
                            for(int i = 0; i < music.size(); i++){
                                if(music.get(i).getTitulo().compareTo(textoSeparado[3].toUpperCase()) == 0){
                                    aux = aux.concat(music.get(i).getDuracao());
                                    buffer = aux.getBytes();
                                    try {
                                        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                        socket2.send(packet);
                                        System.out.println("enviou resposta");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        else if(textoSeparado[1].compareTo("ARTISTA") == 0){
                            String aux = "type|status; msg|Informação do artista:\nTítulo dos albuns:\n";
                            for(Artista a : artistas){
                                if(a.getNome().compareTo(textoSeparado[3].toUpperCase()) == 0){
                                    flag = 2;
                                    if(a.getL_albuns() != null){
                                        for(int i=0; i< a.getL_albuns().size(); i++){
                                            aux = aux.concat(a.getL_albuns().get(i).getTitulo()+"\n");
                                            flag = 1;
                                        }
                                    }
                                }
                            }
                            if(flag == 1){
                                buffer = aux.getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(flag == 1){
                                buffer = "type|status; msg|Não existem albuns associados a este artista".getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(flag == 0){
                                buffer = "type|status; msg|Não existe essse artista".getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else if(textoSeparado[1].compareTo("ALBUM") == 0){
                            int avg_p=0;
                            String aux = "type|status; msg|Informação do album:  \n";
                            for(Artista a : artistas){
                                if(a.getL_albuns() != null){
                                    for(int j = 0; j < a.getL_albuns().size(); j++) {
                                        if (a.getL_albuns().get(j).getTitulo().compareTo(textoSeparado[3].toUpperCase()) == 0) {
                                            flag = 1 ;
                                            aux = aux.concat("Descrição: "+a.getL_albuns().get(j).getDescricao()+"\n");//descrição
                                            if(a.getL_albuns().get(j).getCriticas() != null){
                                                aux = aux.concat("Editores: ");
                                                for(int k =0; k < a.getL_albuns().get(j).getEditores().size();k++){
                                                    aux = aux.concat(a.getL_albuns().get(j).getEditores().get(k).getNome()+" ");//editores
                                                }
                                                if(a.getL_albuns().get(j).getCriticas() != null){
                                                    aux = aux.concat("\nCriticas:\n");//criticas
                                                    for(int k =0; k < a.getL_albuns().get(j).getCriticas().size(); k++){
                                                        avg_p = avg_p +  a.getL_albuns().get(j).getCriticas().get(k).getPont();
                                                        aux = aux.concat("pontuação: "+a.getL_albuns().get(j).getCriticas().get(k).getPont()+"  Descrição:  "+a.getL_albuns().get(j).getCriticas().get(k).getDesc()+"\n");//criticas
                                                    }
                                                    if(a.getL_albuns().get(j).getCriticas().size() != 0)
                                                        aux = aux.concat("Pontução média: "+avg_p/a.getL_albuns().get(j).getCriticas().size()+"\n");
                                                }
                                                if(a.getL_albuns().get(j).getL_Musicas() != null){
                                                    aux = aux.concat("Lista de musicas:\n");
                                                    //System.out.println("++++++++"+a.getL_albuns().get(j).getL_Musicas().size());
                                                    for(int k = 0; k < a.getL_albuns().get(j).getL_Musicas().size(); k++){
                                                        aux = aux.concat(a.getL_albuns().get(j).getL_Musicas().get(k).getTitulo()+"\n");//titulo da musica
                                                        aux = aux.concat("Compositores: ");//compositores
                                                        for(int q = 0;q < a.getL_albuns().get(j).getL_Musicas().get(k).getCompositores().size(); q++){
                                                            aux = aux.concat(a.getL_albuns().get(j).getL_Musicas().get(k).getCompositores().get(q)+", ");
                                                        }
                                                        aux = aux.concat("\nDuração: "+a.getL_albuns().get(j).getL_Musicas().get(k).getDuracao()+"\n");
                                                        flag = 2;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                            if(flag == 0){
                                buffer = "type|status; msg|Não existe essse album".getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(flag == 2){
                                buffer = aux.getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        break;
                    case "PRIVILEGIO":
                        for (User log : login) {
                            for (User us : users) {
                                System.out.println(us.getNome()+"   "+"     "+textoSeparado[2]+"     "+log.getNome()+ "  "+textoSeparado[1]);
                                if (us.getNome().compareTo(textoSeparado[2]) == 0 && (log.getNome().compareTo(textoSeparado[1]) == 0 && log.getEditor() == 1)) {//se esta com o login feito e se o username existe
                                    flag = 1;
                                    if (us.getEditor() == 1) {
                                        buffer = "type|status; msg|O user já é editor.".getBytes();
                                        try {
                                            packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                            socket2.send(packet);
                                            System.out.println("enviou resposta");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        us.setEditor(1);//utilizador passa a ser editor
                                        escreve_fich_obj_users(users, bd_users);
                                        for(User l : login){
                                            if(us.getNome().compareTo(l.getNome()) == 0){//o user esta online
                                                flag = 2;
                                                String s = us.getNome()+"»type|status; msg|O user agora é editor.";
                                                buffer = s.getBytes();
                                                try {
                                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                    socket2.send(packet);
                                                    System.out.println("enviou resposta");
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        if(flag != 2){//o user esta off
                                            if(us.getNotificacoes() != null) {
                                                us.getNotificacoes().add(log.getNome() + " colocou-o como editor.");
                                                buffer = "type|status; msg|O user agora é editor.".getBytes();
                                                try {
                                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                    socket2.send(packet);
                                                    System.out.println("enviou resposta");
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            else{
                                                ArrayList<String> aux = new ArrayList<>();
                                                aux.add(log.getNome() + " colocou-o como editor.");
                                                us.setNotificacoes(aux);
                                                buffer = "type|status; msg|O user agora é editor.".getBytes();
                                                try {
                                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                                    socket2.send(packet);
                                                    System.out.println("enviou resposta");
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (flag == 0) {
                            buffer = "type|status; msg|Nao foi possivel.".getBytes();
                            try {
                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                socket2.send(packet);
                                System.out.println("enviou resposta");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "LOGOUT":
                        if(textoSeparado[1].compareTo(" ") == 0){
                            buffer = "type|status; msg|Saiu do programa".getBytes();
                            try {
                                packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                socket2.send(packet);
                                System.out.println("enviou resposta");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            if(login.size() == 1){
                                if(login.get(0).getNome().compareTo(textoSeparado[1]) == 0){
                                    flag = 2;
                                }
                            }
                            if(flag == 2){
                                login.remove(login.get(0));
                                buffer = "type|status; msg|Logout com sucesso".getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(login != null && login.size() >  1) {
                                User temp = null;
                                for (User log : login) {
                                    if (log.getNome().compareTo(textoSeparado[1]) == 0) {
                                        temp = log;
                                        flag = 20;
                                    }
                                }
                                if(flag == 20){
                                    login.remove(temp);
                                    buffer = "type|status; msg|Logout com sucesso".getBytes();
                                    try {
                                        packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                        socket2.send(packet);
                                        System.out.println("enviou resposta");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            else{
                                buffer = "type|status; msg|Não tinha sessão iniciada. Saiu do programa".getBytes();
                                try {
                                    packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                                    socket2.send(packet);
                                    System.out.println("enviou resposta");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
            System.out.println("socket fechou");
        }
    }

    private static ArrayList<User> le_fich_obj_users(ArrayList<User> users, String nome_f) {
        try {//tenta ler do ficheiro OBJ
            FileInputStream fis = new FileInputStream(nome_f);
            ObjectInputStream obj = new ObjectInputStream(fis);
            User pd;
            while ((pd = (User) obj.readObject()) != null) {
                users.add(pd);
            }
            obj.close();
        } catch (FileNotFoundException e) {//nao consegue
            /*System.out.println("Ocorreu a exceção-> "+e+" <-na leitura do ficheiro de objetos Users. A escrever o ficheiro de objetos\n");
            System.out.println("------------------------------------------------------------------------------------------>");*/
            escreve_fich_obj_users(users,nome_f);//escreve no ficheiro de objetos
        } catch (ClassNotFoundException | IOException e) {
            //System.out.println("Ocorreu a exceção-> " + e + " <-na leitura do ficheiro de objetos.\n");
        }
        return users;
    }

    private static void escreve_fich_obj_users(ArrayList<User> users,String nome_f) {
        try {
            File f = new File(nome_f);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream obj = new ObjectOutputStream(fos);
            //System.out.println(pessoas.toString());
            for (User u : users) {
                obj.writeObject(u);
            }
            obj.close();
        } catch (FileNotFoundException e) {
            //System.out.println("Ocorreu a exceção-> " + e + "<- na escrita do ficheiro de objetos.\n");

        } catch (IOException e) {
            //System.out.println("Ocorreu a exceção-> " + e + " <-na escrita do ficheiro de objetos.");
        }
    }

    private static ArrayList<Artista> le_fich_obj_artistas(ArrayList<Artista> artistas, String nome_f) {
        try {//tenta ler do ficheiro OBJ
            FileInputStream fis = new FileInputStream(nome_f);
            ObjectInputStream obj = new ObjectInputStream(fis);
            Artista a;
            while ((a = (Artista) obj.readObject()) != null) {
                artistas.add(a);
            }
            obj.close();
        } catch (FileNotFoundException e) {//nao consegue
            /*System.out.println("Ocorreu a exceção-> "+e+" <-na leitura do ficheiro de objetos Users. A escrever o ficheiro de objetos\n");
            System.out.println("------------------------------------------------------------------------------------------>");*/
            escreve_fich_obj_artistas(artistas,nome_f);//escreve no ficheiro de objetos
        } catch (ClassNotFoundException | IOException e) {
            //System.out.println("Ocorreu a exceção-> " + e + " <-na leitura do ficheiro de objetos.\n");
        }
        return artistas;
    }

    private static void escreve_fich_obj_artistas(ArrayList<Artista> artistas,String nome_f) {
        try {
            File f = new File(nome_f);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream obj = new ObjectOutputStream(fos);
            //System.out.println(pessoas.toString());
            for (Artista a : artistas) {
                obj.writeObject(a);
            }
            obj.close();
        } catch (FileNotFoundException e) {
            //System.out.println("Ocorreu a exceção-> " + e + "<- na escrita do ficheiro de objetos.\n");

        } catch (IOException e) {
            //System.out.println("Ocorreu a exceção-> " + e + " <-na escrita do ficheiro de objetos.");
        }
    }

    private static void escreve_fich_obj_musica_u_d(ArrayList<Musica_up_down> musicas,String nome_f) {
        try {
            File f = new File(nome_f);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream obj = new ObjectOutputStream(fos);
            //System.out.println(pessoas.toString());
            for (Musica_up_down a : musicas) {
                obj.writeObject(a);
            }
            obj.close();
        } catch (FileNotFoundException e) {
            //System.out.println("Ocorreu a exceção-> " + e + "<- na escrita do ficheiro de objetos de upload de musicas.\n");

        } catch (IOException e) {
            //System.out.println("Ocorreu a exceção-> " + e + " <-na escrita do ficheiro de objetos de upload de musicas.");
        }
    }

    private static ArrayList<Musica_up_down> le_fich_obj_musica_u_d(ArrayList<Musica_up_down> musicas, String nome_f) {
        try {//tenta ler do ficheiro OBJ
            FileInputStream fis = new FileInputStream(nome_f);
            ObjectInputStream obj = new ObjectInputStream(fis);
            Musica_up_down a;
            while ((a = (Musica_up_down) obj.readObject()) != null) {
                musicas.add(a);
            }
            obj.close();
        } catch (FileNotFoundException e) {//nao consegue
            /*System.out.println("Ocorreu a exceção-> "+e+" <-na leitura do ficheiro de objetos de upload de musicas. A escrever o ficheiro de objetos\n");
            System.out.println("------------------------------------------------------------------------------------------>");*/
            escreve_fich_obj_musica_u_d(musicas,nome_f);//escreve no ficheiro de objetos
        } catch (ClassNotFoundException | IOException e) {
            //System.out.println("Ocorreu a exceção-> " + e + " <-na leitura do ficheiro de objetos de upload de musicas.\n");
        }
        return musicas;
    }
}