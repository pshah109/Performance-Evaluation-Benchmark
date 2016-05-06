import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

class UDP_Client_N implements Runnable{
	
	
	int buff = 0;
	int port=0;
	String localhost=null;
	
	public UDP_Client_N(int buff, int port,String localhost) {
		this.buff = buff;
		this.port=port;
		this.localhost=localhost;
	}

	
	public void run() {
	    DatagramSocket socket = null;

        try  {
        		//Determines the IP address of Server to which connection need to be created
                InetAddress host = InetAddress.getByName(localhost);
                socket = new DatagramSocket();
            
                byte[] SendPackets = new byte[buff];
              //Generates random bytes and place them in sendPackets array
                ThreadLocalRandom.current().nextBytes(SendPackets);
                //Create DatagramPacket to send to server with specified IP address and port number
                DatagramPacket Sndpackets = new DatagramPacket(SendPackets, SendPackets.length, host, port);
                //Sends the pack from specified socket
                socket.send(Sndpackets);
              //Creating byte array for receiving packets back from server
                byte[] RecievedPackets = new byte[buff];
                // constructing DatagramPacket for receiving packets
                DatagramPacket Recvpackets = new DatagramPacket(RecievedPackets, RecievedPackets.length);
                //Receives  data gram socket from this specified socket
                socket.receive(Recvpackets);
    			System.out.println("Recieved Packet Size: "+ RecievedPackets.length);

        }catch(Exception e)
        {
        	System.out.println("Please Enter appropriate Server Address");
			System.out.println("Error Message "+ e.getMessage());
			System.exit(0);
        	
        }
	}
	
	
}

public class Network_Udp_Client {
	
	public static void main (String args[])
	
	{
		int buff=0;
		int nthread=0;
		String host=null;
		
		System.out.println("Please Enter IP address of Server you want to Communicate with ");
		Scanner in1 = new Scanner(System.in);
		host=in1.nextLine();
		
		System.out.println("Please Enter no of Threads 1 or 2");
		Scanner in2 = new Scanner(System.in);
		nthread=Integer.parseInt(in2.nextLine());
		
		System.out.println("Please Enter the size of Buffer Packets in Bytes");
		Scanner in3 = new Scanner(System.in);
		buff=Integer.parseInt(in3.nextLine());
		

		Network_Udp_Client udp= new Network_Udp_Client();
		 int[] noOfThreads = {nthread};
		
		 
		 for (int i : noOfThreads) {
			  
			
				udp.UDPNetworkCalculation(i,buff,host);
				
			
			
		 }
			
	}
	
	public void UDPNetworkCalculation(int noOfThreads,int buffersize,String path)

	{
		
		// Function to perform Network Operations using UDP Protocol
		
		 long startTime = System.currentTimeMillis();


		 try{

		 Thread[] threads = new Thread[noOfThreads];
		 int[] port = {10129,12979};
		 for (int i = 0; i < threads.length; i++)
		 {
			 UDP_Client_N th = new UDP_Client_N(buffersize,port[i],path);
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
	 		System.out.println("Time " + totalTime + " ms" );
	 		long timetaken = (long) (totalTime );					
			float throughput = (float)(noOfThreads*buffersize*2*8/(timetaken));
			System.out.println("No of Threads " + noOfThreads );
			System.out.println("Buffer Size " + buffersize);
			System.out.println("Throughput = " + throughput/1000 + " Mb/S\n" );
		
		
	}
	
	
	
}
