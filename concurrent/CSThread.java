package clashsoft.cslib.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CSThread extends Thread
{
	public static boolean DEBUG = true;
	
	public final String name;
	
	private List<Task>			tasks		= new ArrayList();
	private ListIterator<Task>	iterator	= tasks.listIterator();
	
	public CSThread(String name)
	{
		this.name = name;
		this.setName(name);
	}
	
	@Override
	public void run()
	{
		if (DEBUG)
		{
			System.out.println("Starting Thread " + this.name);
		}
		
		while (iterator.hasNext())
		{
			Task task = iterator.next();
			System.out.println("  Starting Task " + task.name);
			task.run();
			System.out.println("  Finished Task " + task.name);
			iterator.remove();
		}
		
		if (DEBUG)
		{
			System.out.println("Finished Thread " + this.name);
		}
	}
	
	public void addTask(Task task)
	{
		iterator.add(task);
	}
	
	public int getTaskCount()
	{
		return tasks.size();
	}
}
