package networking;
import java.io.*;
import java.net.*;
import java.util.Random;
public class Server{
   public static int counter = 0;
	public static void main(String[] args) throws ClientDisconnectedException{
		//if (args.length != 1) {
            //System.out.printf("\t|Usage: java -cp networking.MultithreadedServer <<port>>");
            //System.exit(1);
        //}
		//int port = Integer.parseInt(args[0]);
        int port = 39;
        try {
			ServerSocket ss = new ServerSocket(port);
			System.out.printf("\t|Server with IP address <<%s>> started ...\n\t|listening on port <<%d>> for client requests\n",
				ss.getInetAddress(),ss.getLocalPort());
            
            Socket connection = ss.accept();
            counter++;
            System.out.printf("\t|Server got new connection request from <<%s>>\n" ,
				connection.getInetAddress());
            System.out.println("\t|Connection #"+counter+" established\n");
            DataInputStream inFromClient = new DataInputStream(connection.getInputStream());
			DataOutputStream outToClient = new DataOutputStream(connection.getOutputStream());
			String er;
				while ((er = inFromClient.readUTF())  != null && !er.equals("-1")) {
					int ii = Integer.parseInt(er);
					String bleep = gradeChecker(ii);
            		System.out.printf("\t|Message received from client <<%d>>\n", ii);
					outToClient.writeUTF(bleep);
					outToClient.flush();		
            	}
                if(er.equals("-1")) {
                    throw new ClientDisconnectedException("\t|Client entered exit command...");
                }
		}  catch (IOException ioe) {
            System.out.printf("\t|ConnectionHandler.handleClientRequest: " + ioe.getMessage()+"\n");
			System.exit(1);
        } catch (NumberFormatException exer) {
			System.out.printf("\t|ConnectionHandler.handleClientRequest: " + exer.getMessage()+"\n");
			System.exit(1);
		} 
	}

	private static String gradeChecker(int clientInput) {
        if (clientInput >= 90) {
            return "A+";
        }
        else if (clientInput >= 85) {
            return "A";
        }
        else if (clientInput >= 80) {
            return "A-";
        }
        else if (clientInput >= 77) {
            return "B+";
        }
        else if (clientInput >= 73) {
            return "B";
        }
        else if (clientInput >= 70) {
            return "B-";
        }
        else if (clientInput >= 67) {
            return "C+";
        }
        else if (clientInput >= 63) {
            return "C";
        }
        else if (clientInput >= 60) {
            return "C-";
        }
        else if (clientInput >= 50) {
            return "D";
        }
        else {
            return "F";
        }
    }
}
