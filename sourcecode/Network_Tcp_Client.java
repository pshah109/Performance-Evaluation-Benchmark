import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

class Tcp_Client_N implements Runnable{
	
	String localhost = null;
	int buff = 0;
	int port=0;
	
	public Tcp_Client_N(){
	}
	

	public Tcp_Client_N(int buff, int port,String host) {
		this.buff = buff;
		this.port=port;
		this.localhost=host;
	}
	
	
	public void run() {

		DataOutputStream Dataoutput ;
		DataInputStream Datainput ;
		Socket socket ;
		OutputStream output ;
		InputStream input ;
		

		try {
			socket = new Socket(localhost, port);
			//Returns Output Stream for this socket
			output = socket.getOutputStream();
			
			byte[] sendPackets = new byte[buff];
			//Generates random bytes and place them in sendPackets array
			 ThreadLocalRandom.current().nextBytes(sendPackets);
	     
			Dataoutput = new DataOutputStream(output);
			Dataoutput.writeInt(sendPackets.length);
			//Writes Data into Output Stream provided 
			Dataoutput.write(sendPackets); 
			//Returns Input Stream for this socket
			input = socket.getInputStream();
			Datainput = new DataInputStream(input);
			//Creating byte array for receiving packets back from server
			byte[] RecievePackets = new byte[buff];
			//Reads the byte from contained input stream
			Datainput.readFully(RecievePackets);
			System.out.println("Communicating With Thread : "+Thread.currentThread().getName());
			System.out.println("Recieved Packet Size: "+ RecievePackets.length);
			socket.close();
			output.close();
		} catch (Exception e) {
		
			
			System.out.println("Please Enter appropriate Server Address");
			System.out.println("Error Message "+ e.getMessage());
			System.exit(0);
			
		}

	}

	
}

public class Network_Tcp_Client{
	
 public static void main(String args[])
	 
	 {
	 String s=null;
	 String nthread=null;
	 int threadcount=0;
	 
     Scanner in = new Scanner(System.in);
     System.out.println("Please Enter IP address of Server you want to Communicate with");
     s = in.nextLine();
     
     
     Scanner in1 = new Scanner(System.in);
     System.out.println("Please Enter no of Threads 1 or 2");
     nthread = in1.nextLine();
     	 
     threadcount=Integer.parseInt(nthread);
     
	 Network_Tcp_Client tcp= new Network_Tcp_Client();
	 int[] noOfThreads = {threadcount};
	 int[] buffSize = {1,1024,64*1024};
	
	 
	 for (int i : noOfThreads) {
		  
		for (int j : buffSize)
		{
			tcp.TcpNetworkCalculation(i,j,s);
			
		}
		
	 }
}

public void TcpNetworkCalculation(int noOfThreads,int buffersize,String host)

{
	
	// Function to perform Network Operations using TCP Protocol
	
	 long startTime = System.currentTimeMillis();


	 try{

	 Thread[] threads = new Thread[noOfThreads];
	 int[] port = {11379,11279};
	 for (int i = 0; i < threads.length; i++)
	 {
		 Tcp_Client_N th = new Tcp_Client_N(buffersize,port[i],host);
		 threads[i] = new Thread (th);
		 threads[i].start();
		 
	 }
	 for (Thread thread : threads) {
			thread.join();
		  }

	 }
	 catch (Exception e)
	 
	 {
		 
	 }   
		long endTime = System.currentTimeMillis();
 		long totalTime = (endTime-startTime);
		float timetaken = (float) (totalTime/1000.0 );					
		float throughput = noOfThreads*buffersize*2*8/(timetaken*1000000);
		System.out.println("Time " + timetaken );
		System.out.println("No of Threads " + noOfThreads );
		System.out.println("Buffer Size " + buffersize);
		System.out.println("Throughput = " + throughput + " Mb/S" );
		
}

	
}