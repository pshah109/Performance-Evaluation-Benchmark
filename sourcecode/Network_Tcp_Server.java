import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Network_Tcp_Server extends Thread {

	protected Socket sendersocket;

	public static void main(String[] args) throws IOException {
	
			Scanner in = new Scanner(System.in);
	     System.out.println("Please Number of threads 1 or 2");
	     String s = in.nextLine();
		
		int a = Integer.parseInt(s);
		
		ServerSocket Socket1 = null;
		ServerSocket Socket2 = null;

		try {
			//Creates server Socket bound to specified port provided
			Socket1 = new ServerSocket(11379);
			
			Socket2 = new ServerSocket(11279);
		
			System.out.println("Socket has been Created");
			try {

				System.out.println("Waiting for Connection");
				while (true) {
			//Waits for the connection to get made with this socket and accepts it
					new Network_Tcp_Server(Socket1.accept());
				if(a==2)
				{
					new Network_Tcp_Server(Socket2.accept());
				}	
				}
			} catch (Exception e) {
				System.out.println("Connection failure.");
				System.exit(1);
			}
		} catch (Exception e) {
		
			System.exit(1);
		} finally {
			try {
				// Socket is closed
				Socket1.close();
				Socket2.close();
			} catch (Exception e) {
				
				System.exit(1);
			}
		}
	}

	private Network_Tcp_Server(Socket clientSoc) {
		sendersocket = clientSoc;
		start();
	}

	public void run() {

		try {

			InputStream input = sendersocket.getInputStream(); 
			OutputStream output = sendersocket.getOutputStream(); 
			DataInputStream Datain = new DataInputStream(input);
			int length = Datain.readInt(); 
			byte[] RecievedPacket = new byte[length];
			Datain.readFully(RecievedPacket); 

			System.out.println(Thread.currentThread().getName() + " aknowledgement recieved from client side");

			DataOutputStream Dataout = new DataOutputStream(output);
			Dataout.write(RecievedPacket); 

		} catch (Exception e) {
			System.exit(1);
		}
	}
}
