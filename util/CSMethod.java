package clashsoft.clashsoftapi.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public abstract class CSMethod<R> implements Callable
{
	private Map<String, Object> args = new HashMap<String, Object>();
	
	public CSMethod(Object[] argsValues, String[] argsNames)
	{
		if (argsValues.length != argsNames.length)
			throw new IllegalArgumentException("Name and value array must be of same size!");
		
		for (int i = 0; i < argsNames.length; i++)
			args.put(argsNames[i], argsValues[i]);
	}
	
	public CSMethod(Object... args)
	{
		String name = "";
		for (int i = 0; i < args.length; i++)
		{
			if ((i & 1) == 0 && args[i] instanceof String)
				name = (String) args[i];
			else
			{
				this.args.put(name, args[i]);
			}
		}
	}
	
	public <T> T setArg(String name, T value)
	{
		return (T) this.args.put(name, value);
	}
	
	public void removeArg(String name)
	{
		this.args.remove(name);
	}
	
	public <T> T getArg(String name)
	{
		return (T) this.args.get(name);
	}
	
	@Override
	public final R call()
	{
		return this.run();
	}
	
	public abstract R run();
}
