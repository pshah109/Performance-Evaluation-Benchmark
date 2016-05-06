import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

class DiskReadS implements Runnable {

	int bufferSize;
	String path;
	int noOfThreads;

	DiskReadS() {

	}

	DiskReadS(int noOfThreads, int bufferSize, String path)

	{
		this.bufferSize = bufferSize;
		this.path = path;
		this.noOfThreads = noOfThreads;
	}

	public void run() {

		try {
			System.gc();
			RandomAccessFile readFile = new RandomAccessFile(path, "rw");
			FileChannel inChannel = readFile.getChannel();
			// Creating varying size Buffer Blocks
			ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
			// Read bytes from this channel to given buffer
			int bytesRead = inChannel.read(buffer);

			while (bytesRead != -1) {
				// Flips buffer to make it ready for read
				buffer.flip();

				while (buffer.hasRemaining()) {
					// Reads the byte at buffers current position
					buffer.get();
				}
				// Clear buffer to make it ready for writing
				buffer.clear();
				// Read bytes from this channel to given buffer
				bytesRead = inChannel.read(buffer);

			}
			readFile.close();
			System.gc();

		}

		catch (Exception E) {
		}

	}

}

public class DiskSeqRead {

	public static void main(String args[])

	{

		String s;

		s = "myfile.txt";

		DiskSeqRead CalSeqRead = new DiskSeqRead();
		int[] noOfThreads = { 1, 2 };
		int[] buffSize = { 1, 1024, 1024 * 1024 };

		for (int i : noOfThreads) {

			for (int j : buffSize) {
				CalSeqRead.DiskReadSeqCompute(i, j, s);

			}

		}
	}

	public void DiskReadSeqCompute(int noOfThreads, int buffersize, String path)

	{
		// Function to perform Disk Sequential Read Operations with varying
		// block sizes and varying concurrent Threads
		try {

			RandomAccessFile readFile = new RandomAccessFile(path, "rw");
			DiskReadS readSeq = new DiskReadS(noOfThreads, buffersize, path);

			long startTime = System.currentTimeMillis();
			Thread[] threads = new Thread[noOfThreads];
			for (int i = 0; i < threads.length; i++) {

				threads[i] = new Thread(readSeq);
				threads[i].start();

			}
			for (Thread thread : threads) {
				thread.join();
			}

			long endTime = System.currentTimeMillis();
			long totalTime = (endTime - startTime);
			float timetaken = (float) (totalTime / 1000.0);
			float data = (float) (noOfThreads * readFile.length() / 1048576.0);
			float throughput = data / timetaken;
			float Latency = (float) ((buffersize * timetaken) / (noOfThreads * readFile.length()));
			System.out.println("Time " + timetaken);
			System.out.println("No of Threads " + noOfThreads);
			System.out.println("Buffer Size " + buffersize);
			System.out.println("Total Data Read is " + data + " MB");
			System.out.println("Throughput = " + throughput + " MB/S");
			System.out.println("Latency = " + Latency + " seconds\n");
		}

		catch (Exception e) {

		}
	}

}
