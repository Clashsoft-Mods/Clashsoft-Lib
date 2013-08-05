package clashsoft.clashsoftapi.util;

import java.util.Random;

public class CSRandom extends Random
{
	private static final long	serialVersionUID	= 2427471479709660180L;

	public static int nextInt(Random par1Random, int par2, int par3)
	{
		if (par3 < par2)
			par3 = par2 + 1;
		if (par3 == par2)
			return par2;
		return par2 + ((par2 != par3) ? par1Random.nextInt(par3 - par2) : 0);
	}
	
	public static String getNextRandomName()
	{
		return "TODO";
	}
}
