package clashsoft.cslib.math;

import java.util.Arrays;

public class CSBitMath
{
	public static boolean[] add(boolean[] b1, boolean[] b2)
	{
		// pad them to same length
		int l = b1.length > b2.length ? b1.length : b2.length;
		b1 = Arrays.copyOf(b1, l);
		b2 = Arrays.copyOf(b2, l);
		
		// prevent overflow, make the results bigger
		boolean[] b = new boolean[l + 1];
		boolean carry = false;
		
		for (int i = 0; i < l; i++)
		{
			if (b1[i] && b2[i])
			{
				b[i] = carry;
				carry = true;
			}
			else if (b1[i] || b2[i])
			{
				b[i] = !carry;
			}
			else
			{
				b[i] = carry;
				carry = false;
			}
		}
		
		// Check Overflow
		if (carry)
		{
			b[l] = true;
		}
		
		return b;
	}
	
	public static boolean[] negative(boolean[] bits)
	{
		boolean[] b = new boolean[bits.length];
		for (int i = 0; i < bits.length; i++)
			b[i] = !bits[i];
		return b;
	}
	
	public static boolean[] complement(boolean[] bits)
	{
		boolean[] b = new boolean[bits.length];
		for (int i = 0; i < bits.length; i++)
			b[i] = !bits[i];
		return b;
	}
}
