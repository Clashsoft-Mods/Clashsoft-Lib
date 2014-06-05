package clashsoft.cslib.concurrent;

public class ThreadManager
{
	public final String	name;
	public final int	cores;
	private CSThread[]	threads;
	
	/**
	 * Creates a new {@link ThreadManager} with the current amount of available
	 * processors.
	 * 
	 * @param name
	 *            the name
	 */
	public ThreadManager(String name)
	{
		this(name, Runtime.getRuntime().availableProcessors());
	}
	
	/**
	 * Creates a new {@link ThreadManager} with the given amount of available
	 * processors. For each available processor, it creates an instance of
	 * {@link CSThread}.
	 * 
	 * @param name
	 *            the name
	 * @param cores
	 *            the amount of available processors
	 */
	public ThreadManager(String name, int cores)
	{
		this.name = name;
		this.cores = cores;
		this.threads = new CSThread[cores];
		
		for (int i = 0; i < cores; i++)
		{
			this.threads[i] = new CSThread(name + "-" + i);
		}
	}
	
	/**
	 * Adds and constructs a task to the least occupied thread.
	 * 
	 * @param runnable
	 *            the task
	 */
	public void addTask(Runnable runnable)
	{
		CSThread thread = this.getLeastOccupiedThread();
		String name = thread.getName() + ":" + thread.getTaskCount();
		thread.addTask(new Task(name, runnable));
	}
	
	/**
	 * Adds a task to the least occupied thread.
	 * 
	 * @param task
	 *            the task
	 */
	public void addTask(Task task)
	{
		CSThread thread = this.getLeastOccupiedThread();
		thread.addTask(task);
	}
	
	/**
	 * Returns the thread that currently has the least amount of tasks in it's
	 * queue.
	 * 
	 * @return the least occupied thread
	 */
	public CSThread getLeastOccupiedThread()
	{
		CSThread[] threads = this.threads;
		int cores = this.cores;
		int min = Integer.MAX_VALUE;
		int thread = 0;
		
		for (int i = 0; i < cores; i++)
		{
			CSThread t = threads[i];
			int taskCount = t.getTaskCount();
			if (taskCount < min)
			{
				min = taskCount;
				thread = i;
			}
		}
		
		return threads[thread];
	}
}
