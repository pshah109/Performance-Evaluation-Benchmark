import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

class DiskReadR implements Runnable {

	int bufferSize;
	String path;
	int noOfThreads;

	DiskReadR(int noOfThreads, int bufferSize, String path)

	{
		this.bufferSize = bufferSize;
		this.path = path;
		this.noOfThreads = noOfThreads;
	}

	public void run() {
		try {
			System.gc();
			RandomAccessFile File = new RandomAccessFile(path, "rw");
			FileChannel inChannel = File.getChannel();
			long size = inChannel.size();
			long noOfLoops = (inChannel.size() / bufferSize);
			// Creating varying size Buffer Blocks
			ByteBuffer buf = ByteBuffer.allocate(bufferSize);
			int bytesRead = inChannel.read(buf);
			long value = size - 1048576;

			for (long i = 0; i <= noOfLoops; i++) {

				// Creating a Random Function to obtain random inChannel
				// position
				long random = (long) ((Math.random() * (value)) + 1);
				// Obtaining Random inChannel position
				inChannel.position(random);
				// Flips buffer to make it ready for read
				buf.flip();

				while (buf.hasRemaining()) {
					// Reads the byte at buffers current position
					buf.get();
				}
				// Clear buffer to make it ready for writing
				buf.clear();
				// Read bytes from this channel to given buffer
				bytesRead = inChannel.read(buf);

			}
			File.close();
			System.gc();

		}

		catch (Exception E)

		{

		}

	}
}

public class DiskRandRead {

	public static void main(String args[])

	{
		String s = null;
		;

		DiskRandRead CalRandRead = new DiskRandRead();
		int[] noOfThreads = { 1, 2 };
		int[] buffSize = { 1, 1024, 1024 * 1024 };

		for (int i : noOfThreads) {

			for (int j : buffSize) {

				if (j == 1)

				{
					s = "myshortfile.txt";
				}

				else {
					s = "myfile.txt";
				}
				CalRandRead.DiskReadRandCompute(i, j, s);

			}

		}

	}

	public void DiskReadRandCompute(int noOfThreads, int buffersize, String path)

	{
		// Function to perform Disk Random Read Operations with varying block
		// sizes and varying concurrent Threads
		try {
			RandomAccessFile readFile = new RandomAccessFile(path, "rw");
			DiskReadR readRand = new DiskReadR(noOfThreads, buffersize, path);
			long startTime = System.currentTimeMillis();
			Thread[] threads = new Thread[noOfThreads];
			for (int i = 0; i < threads.length; i++) {

				threads[i] = new Thread(readRand);
				threads[i].start();

			}
			for (Thread thread : threads) {
				thread.join();
			}

			long endTime = System.currentTimeMillis();
			long totalTime = (endTime - startTime);
			float timetaken = (float) (totalTime / 1000.0);
			float data = (float) (readFile.length() / 1048576.0);
			float throughput = noOfThreads * data / timetaken;
			float Latency = (float) ((buffersize * timetaken) / (noOfThreads * readFile.length()));
			System.out.println("Time " + timetaken);
			System.out.println("No of Threads " + noOfThreads);
			System.out.println("Buffer Size " + buffersize);
			System.out.println("Total Data Read is " + data + " MB");
			System.out.println("Throughput = " + throughput + " MB/S");
			System.out.println("Latency = " + Latency + " Seconds\n");

		} catch (Exception e)

		{

		}

	}

}