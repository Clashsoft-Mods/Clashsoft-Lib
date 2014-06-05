package clashsoft.cslib.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import clashsoft.cslib.logging.CSLog;

/**
 * A Thread implementation that processes a queue of tasks.
 * 
 * @author Clashsoft
 */
public class CSThread extends Thread
{
	public static boolean		DEBUG		= true;
	
	private List<Task>			tasks		= new ArrayList();
	private ListIterator<Task>	iterator	= this.tasks.listIterator();
	
	public CSThread(String name)
	{
		this.setName(name);
	}
	
	@Override
	public void run()
	{
		info("Starting Thread " + this.getName());
		
		while (this.iterator.hasNext())
		{
			Task task = this.iterator.next();
			info("  Starting Task " + task.name);
			task.run();
			info("  Finished Task " + task.name);
			this.iterator.remove();
		}
		
		info("Finished Thread " + this.getName());
	}
	
	private static void info(String string)
	{
		if (DEBUG)
		{
			CSLog.info(string);
		}
	}
	
	/**
	 * Adds a task to this thread. The task is added to the end of the queue,
	 * and thus it becomes the last task to be processed.
	 * 
	 * @param task
	 *            the task.
	 */
	public void addTask(Task task)
	{
		this.iterator.add(task);
	}
	
	/**
	 * Returns the amount of tasks this {@link CSThread} thread has to process
	 * before finishing.
	 * 
	 * @return the amount of tasks
	 */
	public int getTaskCount()
	{
		return this.tasks.size();
	}
}
