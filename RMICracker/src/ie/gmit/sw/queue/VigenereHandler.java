package ie.gmit.sw.queue;

import java.rmi.Naming;
import java.util.*;
import java.util.concurrent.*;

import ie.gmit.sw.VigenereBreaker;

public class VigenereHandler implements Runnable
{
	private BlockingQueue<Request> queue;
	private Map<Long, String> out = new ConcurrentHashMap<Long, String>();	
	private String result;
	private Request req = null;
	
	public VigenereHandler(BlockingQueue<Request> q, Map<Long, String> out)
	{
		this.out = out;
		this.queue = q;
		run();	//call the run method
	}
	
	public void run()
	{
		try 
		{
			//retrieve an object from the queue
			req = queue.take();
		} 
		
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			VigenereBreaker vb = (VigenereBreaker) Naming.lookup("Cypher-Service");
			result = vb.decrypt(req.getCypherText(),  req.getMaxKeySize());
			//put the jobnumber and decypted text on the map
			out.put(req.getJobNumber(), result);

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}	
	public String returnResult()
	{
		//return the value that the jobnumber is associated with
		return out.get(req.getJobNumber());
	}
	public void removeRequest(long jobNumber)
	{
		//remove the jobnumber from the map
		out.remove(jobNumber);
	}
}