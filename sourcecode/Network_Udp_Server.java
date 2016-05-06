import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


class Network_Udp_Ser implements Runnable {

int portNum;

Network_Udp_Ser (int portNum){
        this.portNum = portNum;
}

public void run() {

        try{

                //Create byte array of maximum size to receive data from client
        		// Packet size for UDP cannot exceed 64 KB
                byte receivingdata[]=new byte[1024*62];
                //Create Server Socket
                DatagramSocket socket=new DatagramSocket(portNum);

                System.out.println("Waiting for Data Packets");
                //Create DatagramPacket to receive packet from client
                DatagramPacket recievepacket=new DatagramPacket(receivingdata,receivingdata.length);
                 //Receives  data gram socket from this specified socket
                socket.receive(recievepacket);

              //Creating byte array for sending packets back to client
                byte[] sendData = recievepacket.getData();
                //Create DatagramPacket to send to client with specified IP address and port number
                DatagramPacket sendpacket = new DatagramPacket(sendData,sendData.length,recievepacket.getAddress(),recievepacket.getPort());
               //Sends the packet from this specified socket
                socket.send(sendpacket);

                System.out.println("Data Received and replied back to client "+recievepacket.getAddress().getHostAddress() );
                System.out.println(Thread.currentThread().getName() + " is completed");

        }catch (Exception e) {
                e.printStackTrace();
        }
        
}


}

public class Network_Udp_Server {

public static void main(String args[]) throws IOException {
	
	
	Thread[] threads = new Thread[2];
	 int[] port = {10129,12979};
	
	 for (int i = 0; i < threads.length; i++)
	 {
		 Network_Udp_Ser  udp= new Network_Udp_Ser(port[i]);
		
		 threads[i] = new Thread (udp);
		 threads[i].start();
		 
	 }
               

}

}
