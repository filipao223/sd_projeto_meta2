/*package RMIInter;*/
package DropMusicRMI_M;

import java.rmi.*;
import java.rmi.server.*;
import java.net.*;
import java.io.*;
import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("Duplicates")

/**
 * @author Lara Trindade e João Miranda
 */

public class RMIServer extends UnicastRemoteObject implements RMIInter {
    private static final long serialVersionUID = 1L;
    private String MULTICAST_ADDRESS = "224.0.224.0";
    private int PORT = 4321;
    static CBInter client;  //referência para a interface do cliente
    private static CopyOnWriteArrayList<Online> logged = new CopyOnWriteArrayList();    //ArrayList dos clientes que estão online
    MulticastSocket socket = null;

    public RMIServer() throws RemoteException {
        super();
    }

    public String teste() throws RemoteException {  //função para verificar se o cliente está ligado
        return "Olá cliente!" ;
    }

    public boolean test_servers() throws RemoteException{   //função para verificar o lookup
        return true;
    }

    public void verificaClient() throws RemoteException{
        System.out.println("Cliente Conectado");
    }

    public CopyOnWriteArrayList<Online> lista_online() throws RemoteException{
        //System.out.println("aqui");
        return logged;
    }

    private static void serverStart(RMIServer serv){    //função para ligar o server
        try {
            serv = new RMIServer();
            Naming.bind("rmi://localhost:7000/benfica", serv);
        } catch (RemoteException re) {
            System.out.println("Remote Exception: " + re);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL Exception: " + e);
        } catch (AlreadyBoundException abe) {
            System.out.println("Um servidor já se encontra binded. Substituir o servidor pelo backup se necessário");
            //abe.printStackTrace();
            testaRMIserver(serv);
        }
    }

    private static void testaRMIserver(RMIServer serv) {    //função para verificar se o servidor falha e se necessário liga outro
        int flag = 0;
        int contador = 0;
        System.out.println("Sou o secundário");
        while (true) {
            try {
                RMIInter inter = (RMIInter) Naming.lookup("rmi://localhost:7000/benfica");
                String message = inter.teste();
                logged = inter.lista_online();
                //System.out.println(logged.size());
            } catch (RemoteException e) {//so entra aqui quando  serv principal nao funciona
                contador++;
                flag = 1;
                if (contador == 5) {
                    break;
                }
            } catch (NotBoundException e) {
                //e.printStackTrace();
            }catch (MalformedURLException e) {
               // e.printStackTrace();
            }
            if (flag == 0) {
                contador = 0;
            }
        }
        try {
            Thread.sleep (1000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        try {
            Naming.rebind("rmi://localhost:7000/benfica", serv); //muda a referencia para o novo servidor
        } catch (RemoteException e) {
           // e.printStackTrace();
        } catch (MalformedURLException e) {
           // e.printStackTrace();
        }
    }

    public String rmi_mc(String s){ //troca de informação entre o RMIServer e o Multicast
        String message = "";
        String fim = "";
        try {
            socket = new MulticastSocket(PORT);  // create socket para ENVIAR apenas
            System.out.println(s);
            byte[] buffer = s.getBytes();
            try {
                InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
                socket.joinGroup(group);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                socket.send(packet);

                buffer = new byte[256];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                message = new String(packet.getData(), 0, packet.getLength());
                //System.out.println(packet.getPort()+"*RECEBER");

                while(packet.getPort() == PORT) {
                    buffer = new byte[256];
                    packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    message = new String(packet.getData(), 0, packet.getLength());

                    System.out.println(message);
                    String [] aux = message.split("«");
                    String [] aux2 = aux[0].split(" ");
                    System.out.println("---->"+aux[0]);

                    if(aux2[0].compareTo("type|status;") == 0){//não tem notificacoes para receber
                        fim = fim.concat(message);
                        return fim;
                    }
                    else{
                        String xx = aux[0];
                        int x = Character.getNumericValue(xx.charAt(0));
                        System.out.println("###########"+x);
                        fim = fim.concat(aux[1]+"\n");
                        for(int i = 0; i< x; i++){
                            System.out.println("é aqui");
                            buffer = new byte[256];
                            packet = new DatagramPacket(buffer, buffer.length);
                            socket.receive(packet);
                            message = new String(packet.getData(), 0, packet.getLength());
                            fim = fim.concat(message+"\n");
                        }
                        return fim;
                    }
                }
                return fim;

            } catch (IOException e) {
                //e.printStackTrace();
            }
        } catch (IOException e) {
           // e.printStackTrace();
        }
        return message;
    }

    public String rmi_mc_p(String s){   //troca de informação entre o RMIServer e o Multicast acerca dos Privilégios
        String message = "";
        String message_e = "";
        try {
            socket = new MulticastSocket(PORT);  // create socket para ENVIAR apenas
            System.out.println(s);
            byte[] buffer = s.getBytes();

            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(group);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(packet);

            buffer = new byte[256];
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            message = new String(packet.getData(), 0, packet.getLength());
            //System.out.println(packet.getPort()+"*RECEBER");

            while(packet.getPort() == PORT) {
                buffer = new byte[256];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                message = new String(packet.getData(), 0, packet.getLength());
            }
                System.out.println(message);
                String[] aux = message.split("»");
                if(aux.length == 1){//o novo editor já é editor
                    message_e = message_e.concat(aux[0]);
                }
                else
                    message_e = message_e.concat(aux[1]);
            }catch (IOException e) {
            //e.printStackTrace();
            }
        return message_e;
    }

    public String rmi_mc_d(String s){   //troca de informação entre o RMIServer e o Multicast acerca da descrição
        String message = "";
        String message_d = "";
        try {
            socket = new MulticastSocket(PORT);  // create socket para ENVIAR apenas
            System.out.println(s);
            byte[] buffer = s.getBytes();

            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(group);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(packet);

            buffer = new byte[256];
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            message = new String(packet.getData(), 0, packet.getLength());
            //System.out.println(packet.getPort()+"*RECEBER");

            while(packet.getPort() == PORT) {
                buffer = new byte[256];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                message = new String(packet.getData(), 0, packet.getLength());
            }

            System.out.println(message);
            String[] aux = message.split("»");
            //aux [0] -> nome dos users que estão online separados por ; que devem receber callback de descrição
            //aux[1] -> titulo do album E msg a enviar para o utilizador que fez a ação
            String [] aux2 = aux[1].split("«");
            descricaoCallback(aux[0],aux2[0]);
            System.out.println(message);
            System.out.println(aux[0]+"     "+aux[1]);
            System.out.println(aux2[0]);
            System.out.println(aux2[1]);
            message_d = message_d.concat(aux2[1]);
        }catch (IOException e) {
            //e.printStackTrace();
        }
        return message_d;
    }

    public String rmi_udp(String s){ //troca de informação entre o RMIServer e o Multicast
        String message = "";
        try {
            socket = new MulticastSocket(PORT);  // create socket para ENVIAR apenas
            System.out.println(s);
            byte[] buffer = s.getBytes();
            try {
                InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
                socket.joinGroup(group);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                socket.send(packet);

                while(packet.getPort() == PORT) {//o ip e porto do 1º multicast que responder
                    buffer = new byte[256];
                    packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    //System.out.println(packet.getPort()+"*RECEBER");
                    message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(message);
                }

                System.out.println(message);
            } catch (IOException e) {
               // e.printStackTrace();
            }
        } catch (IOException e) {
           // e.printStackTrace();
        }
        return message;
    }

    public void descricaoCallback(String s,String nome_a){  //procura o cliente para enviar o callback de allteração da descrição
        String [] aux = s.split(";");
        for(Online l:logged){
            for(int i=0; i < aux.length; i++){
                if(l.getNome().compareTo(aux[i]) == 0){
                    l.notifica_d(nome_a);
                }
            }
        }
    }

    public void editorCallback(String s) {  //procura o cliente para enviar o callback de allteração para editor
        String message = "";
        System.out.println(message);
        //System.out.println("---->" + aux[0]);

        for (Online l : logged) {
            if (l.getNome().compareTo(s) == 0) {
                l.notifica();
            }
        }
    }

    public void subscribe(CBInter c,String nome) throws RemoteException{ //serve para saber qual o cliente
        Online o = new Online(nome,c);
        logged.add(o);
        //System.out.println("Lista de Referencias"+logged);
    }

    public void unsubscribe(String nome) throws  RemoteException{
        Online aux = null;
        for(Online l:logged){
            if(l.getNome().compareTo(nome) == 0){
                aux = l;
            }
        }
        logged.remove(aux);
    }

    public static void main (String args[]){
        RMIServer serv = null;

        serverStart(serv);  //liga o servidor
        System.out.println("Server ready");
        System.out.println("Sou o primário");
    }
}