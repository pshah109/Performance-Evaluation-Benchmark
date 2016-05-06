import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ThreadLocalRandom;

class DiskWriteS implements Runnable {
	
	DiskWriteS()
	{}
	
	int bufferSize;
	String path;
	int noOfThreads;
	
	DiskWriteS (int noOfThreads,int bufferSize)
	  
	  {
		  this.bufferSize=bufferSize;
	
		  this.noOfThreads=noOfThreads;
	  }
	
	 	public  void run()
		{
	 		try{
	 			
	 			byte[] bytes = new byte[bufferSize];
	 			//Generates random bytes and place them in bytes array
	 			 ThreadLocalRandom.current().nextBytes(bytes);
	 			// Creating varying size byte blocks
		         ByteBuffer buffer = ByteBuffer.wrap(bytes);
		         File file = new File("DiskWriteSeq.txt");
		         boolean append = false;
		         FileChannel SeqChannel = new FileOutputStream(file, append).getChannel();
		         long noOfLoops=0;
		         if(bufferSize==1)
		        	 
		         {
		        	 noOfLoops= 52428800; 
		         }
		         else if (bufferSize==1024)
		        	 
		         {
		        	 noOfLoops= 5242880;
		         }
		         else
		         {
		        	 
		        	 noOfLoops= 5120;
		         }
		   
		         for(long i=1;i<=noOfLoops/noOfThreads;i++)
		         {
		        	//Writes a sequence of bytes to this channel from given buffer
		        	 SeqChannel.write(buffer);
		        	 //Sets the inChannel to Random positions 
		             buffer.rewind();
		         }
		         SeqChannel.close();
	 			
	 		System.gc();
	 		
	 	
	 		}
	 		
	 		catch (Exception E)
			
			{
	 			 
				
			}
	 		
	 		
		}
	}

public class DiskSeqWrite {

	public static void main(String args[])
	
	{

		 
		DiskSeqWrite CalSeqWrite = new DiskSeqWrite(); 
	 	 int[] noOfThreads = {1,2};
	 	 int[] buffSize = {1,1024,1024*1024};
	 	
	 	 for (int i : noOfThreads) {
	 		  
	 		for (int j : buffSize)
	 		{
	 			CalSeqWrite.DiskWriteSeqCompute(i,j);
	 			
	 		}
	 		
	 	 }
		
	}
	
	
	 public void DiskWriteSeqCompute (int noOfThreads,int buffersize)
	 {
		 
		 try{
			// Function to perform Disk Sequential Write Operations with varying block sizes and varying concurrent Threads
			 
			 DiskWriteS writeSeq = new DiskWriteS(noOfThreads,buffersize);
			 long startTime = System.currentTimeMillis();
			 Thread[] threads = new Thread[noOfThreads];
			 for (int i = 0; i < threads.length; i++)
			 {
				
				 threads[i] = new Thread (writeSeq);
				 threads[i].start();
				 
			 }
			 for (Thread thread : threads) {
					thread.join();
				  }
			 		float Latency=0;
			 		long endTime = System.currentTimeMillis();
			 		long totalTime = (endTime-startTime);
					float timetaken = (float) (totalTime/1000.0 );
					float data=0;
					if(buffersize==1)
					{
						
						 data = (float) (50);
						  Latency = ((buffersize*timetaken)/(50));
					}
					else
					{
						 data = (float) (5120);
						 Latency = ((buffersize*timetaken)/(5120));
					}
					
					float throughput = data/timetaken;
					System.out.println("Time " + timetaken +" Seconds" );
					System.out.println("No of Threads " + noOfThreads );
					System.out.println("Buffer Size " + buffersize);
					System.out.println("Total Data Write is " + data + " MB");
					System.out.println("Throughput = " + throughput + " MB/S" );
					System.out.println("Latency = " + Latency/1048576 +" Seconds\n");
			 
			 }
			 catch(Exception e)
			 
			 {
				 
			 }
			 
		 
		 
	 }
	
}
