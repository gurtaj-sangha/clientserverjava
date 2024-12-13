package networking;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import networking.ClientDisconnectedException;

public class Clientel {
    private int port;
    private Socket socket;
    private String host;
    private DataInputStream read;
    private Scanner keyboard;
    private  DataOutputStream write;
    public Clientel(int port, String host) {
        this.port = port;
        this.host = host;
        requestService();
    }
    public void requestService() {
        try {
            this.socket = new Socket(host, port);
            System.out.printf("\t|Client  connected to <<%s>> on port <<%d>>\n",host,port);
            System.out.printf("\t|To exit enter a single line containing: <<%s>>\n\t|","-1");
            read = new DataInputStream(socket.getInputStream());
            write = new DataOutputStream(socket.getOutputStream());
            keyboard = new Scanner(System.in);
            sendRequest();
        } catch(ClientDisconnectedException e) {
            System.exit(1);
            cleanup();
        } catch(Exception e) {
            System.exit(1);
            cleanup();
        }
    }
    public void sendRequest() throws IOException, ClientDisconnectedException {
        String line;
        while((line = keyboard.nextLine()) != null) {
            write.writeUTF(line);
            write.flush();
            System.out.println("Sum of numbers sent to server so far <<"+read.readInt()+">>");

        } 
    }
    public void cleanup() {
        try {
            socket.close();
            read.close();
            write.close();
            keyboard.close();
        } catch(Exception e) {
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        Clientel c = new Clientel(432, "127.0.0.1");
    }
}
