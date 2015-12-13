package ie.gmit.sw.queue;

import java.util.*;
import java.util.concurrent.*;

public class VigenereRequestManager
{
	private static final int maxCapacity = 10;
	private BlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(maxCapacity);
	private Map<Long, String> out = new ConcurrentHashMap<Long, String>();
	private VigenereHandler vigHandler;
	private String cypherText;
	
	public VigenereRequestManager()
	{
	}
	public void add(final Request r)
	{	
		try
		{
			//queue.put(r)//blocks if queue full
			//instantiate a new thread and implement the runnable interface
			Thread t1 = new Thread(new Runnable()
			{
				//start the thread
				public void run()
				{
					try
					{
						//add an element on to the queue
						queue.put(r);
						//place encrypted word on the map, with jobnumber
						out.put(r.getJobNumber(),r.getCypherText());				
						//pass to vigHandler the queue and map
						vigHandler = new VigenereHandler(queue, out);
						
						out.put(r.getJobNumber(), vigHandler.returnResult());
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
				}
			});
			t1.start();	//start thread
			t1.join();	//stop thread after time
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public String getResult(long jobNumber) throws Exception
	{
		Thread t2 = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					String result =	out.get(jobNumber);
					cypherText = result;
					System.out.println(cypherText);
					
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		});
		t2.start(); //start thread
		t2.join(); //stop thread
		out.remove(jobNumber);	//remove from map current jobNumber
		vigHandler.removeRequest(jobNumber);
		return cypherText;
	}
	
	/*public static void main(String[] args) throws Exception 
	{
		//main used for testing
		//new request with cypherText, jobNumber and maxKey parameters
		Request req = new Request("CQNBDYANVNJACXOFJA", 4, 1);
		VigenereRequestManager vrm = new VigenereRequestManager();
		vrm.add(req);
		//return request of jobnumber (1)
		System.out.println(vrm.getResult(1));
	}*/
}