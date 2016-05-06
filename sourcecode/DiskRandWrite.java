
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class DiskRandR implements Runnable {

	int bufferSize;

	int noOfThreads;

	DiskRandR() {
	}

	DiskRandR(int noOfThreads, int bufferSize)

	{
		this.bufferSize = bufferSize;

		this.noOfThreads = noOfThreads;
	}

	public void run() {

		try {
			long noOfLoops = 1000;

			byte[] bytes = new byte[bufferSize];
			// Generates random bytes and place them in bytes array
			ThreadLocalRandom.current().nextBytes(bytes);
			// Creating varying size byte blocks
			ByteBuffer buffer = ByteBuffer.wrap(bytes);
			RandomAccessFile readFile = new RandomAccessFile("DiskWriteRand.txt", "rw");
			FileChannel inChannel = readFile.getChannel();

			long range = bufferSize * noOfLoops;
			Random r = new Random();

			for (long i = 1; i <= noOfLoops / noOfThreads; i++) {
				long number = (long) (r.nextDouble() * range);
				// Writes a sequence of bytes to this channel from given buffer
				inChannel.write(buffer);
				// Sets the position of buffer back to zero so we can re-read
				// the data
				buffer.rewind();
				// Sets the inChannel to Random positions
				inChannel.position(number);
			}
			inChannel.close();
			readFile.close();

		}

		catch (Exception E) {

		}

	}

	public void DiskRandCompute(int i, int j) {
		// TODO Auto-generated method stub

	}
}

public class DiskRandWrite {

	public static void main(String args[])

	{

		DiskRandWrite CalRandWrite = new DiskRandWrite();
		int[] noOfThreads = { 1, 2 };
		int[] buffSize = { 1, 1024, 1024 * 1024 };

		for (int i : noOfThreads) {

			for (int j : buffSize) {
				CalRandWrite.DiskRandCompute(i, j);

			}

		}
	}

	public void DiskRandCompute(int noOfThreads, int buffersize) {
		// Function to perform Disk Random Write Operations with varying block
		// sizes and varying concurrent Threads

		try {

			DiskRandR writeRand = new DiskRandR(noOfThreads, buffersize);
			long startTime = System.currentTimeMillis();
			Thread[] threads = new Thread[noOfThreads];
			for (int i = 0; i < threads.length; i++) {

				threads[i] = new Thread(writeRand);
				threads[i].start();

			}
			for (Thread thread : threads) {
				thread.join();
			}

			long latency = 0;
			long endTime = System.currentTimeMillis();
			long totalTime = (endTime - startTime);
			float timetaken = (float) (totalTime / 1000.0);
			float throughput = 1000 * buffersize / (timetaken * 1048576);
			float totaldata = (float) (1000 * buffersize / 1048576.0);
			float datainbytes = (float) (1000 * buffersize);
			float Latency = (float) ((buffersize * timetaken) / (datainbytes));
			System.out.println("Time " + timetaken + "Seconds");
			System.out.println("No of Threads " + noOfThreads);
			System.out.println("Buffer Size " + buffersize);
			System.out.println("Total Data Write is " + totaldata + " MB");
			System.out.println("Throughput = " + throughput + " MB/S");
			System.out.println("Latency = " + Latency + " seconds\n");
		} catch (Exception e)

		{

		}

	}
}
