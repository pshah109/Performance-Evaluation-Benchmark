
  class FloatOpsSamples implements Runnable {
	
	  int noOfThreads;
	  
	  
	  FloatOpsSamples (int noOfThreads)
	  
	  {
		  this.noOfThreads=noOfThreads;
		  
	  }
	  
	public  void run()
			{
	
		 		float a=1.9f,b=3.7f,c=4.4f,d=1.2f,e=2.1f,f=2.1f,g=1.2f,h=2.9f;
		 		float j=1.9f,k=3.7f,l=2.1f,m=1.2f,n=2.9f,o=33.f,p=1.2f,q=2.2f,r=3.1f;
		 		float s=1.2f,t=1.2f,u=1.6f,v=1.3f,w=1.4f;
				long i;
				// The loop will be executed for (1 Billion times/ NoofThreads) by each Thread
				for (i=0;i<1000000000/noOfThreads;i++)
				{
					
					a=w+b;
					c=k+b;
					b=m+j;
					d=k-o;
					e=c-a;
					f=p+b;
					g=l+m;
					h=c+m;
					j=m+k;
					l=a+k;
					m=n-u;	
					n=a+b;
					o=c-v;
					p=l+c;
					q=m+t;
					r=j+s;
					s=p+a;
					t=k+g;
					u=h+g;
					v=g+e;
					w=a+b;
					q=f+w;
				}	
			}
	  }


  class IntOpsSamples implements Runnable {
	
	  int noOfThreads;
	  
	  
	  IntOpsSamples (int noOfThreads)
	  
	  {
		  this.noOfThreads=noOfThreads;
		  
	  }
	  
	public  void run()
			{
	
		int a=1,b=4,c=4,d=8,e=2,f=2,g=5,h=5;
 		int j=3,k=3,l=2,m=4,n=2,o=33,p=1,q=2,r=3;
 		int s=5,t=7,u=4,v=3,w=7;
				long i;
				// The loop will be executed for (1 Billion times/ NoofThreads) by each Thread
				for (i=0;i<1000000000/noOfThreads;i++)
				{
					a=w+b;
					c=k+b;
					b=m+j;
					d=k-o;
					e=c-a;
					f=p+b;
					g=l+m;
					h=c+m;
					j=m+k;
					l=a+k;
					m=n-u;	
					n=a+b;
					o=c-v;
					p=l+c;
					q=m+t;
					r=j+s;
					s=p+a;
					t=k+g;
					u=h+g;
					v=g+e;
					w=a+b;
					q=f+w;
				}	
			}
	  }
  

  public class CpuOperationSamples {
	 
	 public static void main(String args[])
	 
	 {
		 
		 CpuOperationSamples fops = new CpuOperationSamples();

		System.out.println("Following are 600 Samples for Floating Point Operations per Second With 4 Threads");	 
		 //This loop will provide 600 Samples for Giga FLOPS with 4 threads
		for(int i=0;i<600;i++)
		{
		 fops.computeFops(4);
			
		 }
		 System.out.println("Following are 600 Samples for Integer Operations Per Seconds With 4 Threads");	
		 
		 //This loop will provide 600 Samples for Giga IOPS with 4 threads
		 for(int i=0;i<600;i++)
			{
			 fops.computeIops(4);
				
			 }
			  
			
			
		 
			
	 }
	 
	 public void computeFops(int noOfThreads)
		{	
		// Function for calculating Giga Flops
			try
			{
			 long startTime = System.currentTimeMillis();
			 FloatOpsSamples tf = new FloatOpsSamples(noOfThreads);
			Thread[] threads = new Thread[noOfThreads];
			 for (int i = 0; i < threads.length; i++)
			 {
				 threads[i] = new Thread (tf);
				 threads[i].start();
				 
			 }
			 
			 for (Thread thread : threads) {
					thread.join();
				  }
			 	long endTime = 0;
				endTime = System.currentTimeMillis();
				long timeneeded = endTime - startTime;
				
				float timetaken = (float) (timeneeded/1000.0);
				
				double flops =   (1000000000.0/timetaken);
				
				// Divide the flops by 1 billion to get Giga Flops
				// Multiply FLOPS with no of operations
				double Gigaflops =44*flops/1000000000;
				System.out.println(Gigaflops);
	 
		}
			
			catch(Exception e)
			{	
				
			}
		}
	 
	 public void computeIops(int noOfThreads)
		{	
		// Function for calculating Giga Iops
			try
			{
			 long startTime = System.currentTimeMillis();
			 IntOpsSamples ti = new IntOpsSamples(noOfThreads);
			Thread[] threads = new Thread[noOfThreads];
			 for (int i = 0; i < threads.length; i++)
			 {
				 threads[i] = new Thread (ti);
				 threads[i].start();
				 
			 }
			 
			 for (Thread thread : threads) {
					thread.join();
				  }
			 	long endTime = 0;
				endTime = System.currentTimeMillis();
				long timeneeded = endTime - startTime;
				
				float timetaken = (float) (timeneeded/1000.0);				
				// Divide the Iops by 1 billion to get Giga Ilops
				// Multiply IOPS with no of operations
				double Iops =   (1000000000.0/timetaken);
				double GigaIops = 44*Iops/1000000000;
				System.out.println(GigaIops);
	 
		}
			
			catch(Exception e)
			{	
				
			}
		}

	 
	 
	 
 }