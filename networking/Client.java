package networking;
import java.io.*;
import java.util.Scanner;
import java.net.Socket;
import java.util.Random;
public class Client {
    private Socket socket;
    private String host;
    private int port;
    private DataInputStream inFromServer;
	private Scanner inFromKeyboard;
    private DataOutputStream outToServer;
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        requestService();
    }
    private void requestService() {
        try {
			System.out.println("\t|Inside requestService() method");
            this.socket = new Socket(host, port);
            System.out.printf("\t|Client  connected to <<%s>> on port <<%d>>\n",host,port);
            System.out.printf("\t|To exit enter a single line containing: <<%s>>\n\t|","-1");
            inFromServer = new DataInputStream(socket.getInputStream());
			inFromKeyboard = new Scanner(System.in);;
            outToServer = new DataOutputStream(socket.getOutputStream());
			sendRequest();
            } 
		
		catch (ClientDisconnectedException e) {
            System.out.printf("\t|Closing connection to server");
			System.out.println("\t|ConnectionHandler: ...\n\t|cleaning up and exiting ...\n ");
			System.exit(1);
            cleanup(); 
        }
		catch (Exception e) {
            System.exit(1);
            cleanup(); 
        }
    }
	
    private void  sendRequest() throws IOException, ClientDisconnectedException {
		String clientInput;
		
		while((clientInput = inFromKeyboard.nextLine()) != null && !clientInput.equals("-1") && Integer.parseInt(clientInput) <= 100 && Integer.parseInt(clientInput) > -2) {
            outToServer.writeUTF(clientInput); 
            outToServer.flush(); 
            System.out.println("\t|Grade <<"+clientInput+">> sent to server");
	        System.out.printf("\t|Response received from server <<%s>>\n\t|",inFromServer.readUTF());
        }
        if(clientInput.equals("-1"))
            throw new ClientDisconnectedException(" ... user has entered exit command ... ");
	}    

    private void cleanup() {
        System.out.printf("\t|Client: ... \n\t|cleaning up and exiting ...\n ");
        try {
            outToServer.close();
            inFromServer.close();
			inFromKeyboard.close();
            socket.close();
        } catch (IOException ioe) {
            System.out.printf("\t|Ooops went wrong\n ");
        }
    }
	public static void main(String[] args) {
        //if (args.length != 2) {
            //System.out.printf("\t|Usage: java -cp networking.Client <<hostname>> <<port>>");
            //System.exit(1);
        //}
		//int port = Integer.parseInt(args[1]);
        int port = 39;
        Client c = new Client("127.0.0.1", port);
    }
} 