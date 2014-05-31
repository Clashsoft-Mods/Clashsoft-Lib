package clashsoft.cslib.random;

import java.util.Random;

/**
 * A {@link Random} implementation that always returns the maximum value.
 * 
 * @author Clashsoft
 */
public class MaxRandom extends Random
{
	public static MaxRandom	instance	= new MaxRandom();
	
	private MaxRandom()
	{
	}
	
	@Override
	protected int next(int bits)
	{
		return bits;
	}
	
	@Override
	public void nextBytes(byte[] bytes)
	{
		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = Byte.MAX_VALUE;
		}
	}
	
	@Override
	public int nextInt(int n)
	{
		return n - 1;
	}
	
	@Override
	public boolean nextBoolean()
	{
		return true;
	}
	
	@Override
	public float nextFloat()
	{
		return 1F;
	}
	
	@Override
	public double nextDouble()
	{
		return 1D;
	}
	
	@Override
	public synchronized double nextGaussian()
	{
		return 1D;
	}
	
	@Override
	public long nextLong()
	{
		return 1L;
	}
}
