package clashsoft.cslib.random;

import java.util.Random;

/**
 * A {@link Random} implementation that always returns the minimum value, mostly
 * being 0.
 * 
 * @author Clashsoft
 */
public class MinRandom extends Random
{
	public static MinRandom	instance	= new MinRandom();
	
	private MinRandom()
	{
	}
	
	@Override
	protected int next(int bits)
	{
		return 0;
	}
	
	@Override
	public void nextBytes(byte[] bytes)
	{
		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = Byte.MIN_VALUE;
		}
	}
	
	@Override
	public int nextInt(int n)
	{
		return 0;
	}
	
	@Override
	public boolean nextBoolean()
	{
		return false;
	}
	
	@Override
	public float nextFloat()
	{
		return 0F;
	}
	
	@Override
	public double nextDouble()
	{
		return 0D;
	}
	
	@Override
	public synchronized double nextGaussian()
	{
		return 0D;
	}
	
	@Override
	public long nextLong()
	{
		return 0L;
	}
}
