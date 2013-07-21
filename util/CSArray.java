package clashsoft.clashsoftapi.util;

import java.lang.reflect.Array;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class CSArray
{
	public static String[][] split(String[] par1Strings, int maxLength)
	{
		int arrays = MathHelper.ceiling_float_int(((float)par1Strings.length) / ((float)maxLength));
		String[][] ret = new String[arrays][];
		
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = new String[maxLength];
			for (int j = 0; j < maxLength && (j + (i * maxLength)) < par1Strings.length; j++)
			{
				ret[i][j] = par1Strings[j + (i * maxLength)];
			}
		}
		return ret;
	}
	
	public static String[] addToAll(String[] par1Strings, String par2, String par3)
	{
		String[] ret = new String[par1Strings.length];
		for (int i = 0; i < ret.length; i++)
		{
			if (par1Strings[i] != null)
			{
				ret[i] = par2 + par1Strings[i] + par3;
			}
			else
			{
				ret[i] = "";
			}
		}
		return ret;
	}

	public static String[] combine(String[] par1Strings, String[] par2Strings)
	{
		String[] ret = new String[par1Strings.length + par2Strings.length];
		for (int i = 0; i < par1Strings.length; i++)
		{
			ret[i] = par1Strings[i] != null ? par1Strings[i] : "";
		}
		for (int i = 0; i < par2Strings.length; i++)
		{
			ret[i + par1Strings.length] = par2Strings[i] != null ? par2Strings[i] : "";
		}
		return ret;
	}
	
	public static String[] caseAll(String[] par1Strings, int mode)
	{
		String[] ret = new String[par1Strings.length];
		for (int i = 0; i < par1Strings.length; i++)
		{
			if (par1Strings[i] != null)
			{
				ret[i] = mode == 0 ? par1Strings[i].toLowerCase() : par1Strings[i].toUpperCase();
			}
			else
			{
				ret[i] = "";
			}
		}
		return ret;
	}
	
	public static int valueOf(Object[] par1Objects, Object par2Object)
	{
		for (int i = 0; i < par1Objects.length; i++)
		{
			if (par1Objects[i].equals(par2Object))
			{
				return i;
			}
		}
		return -1;
	}
	
	public static int valueOf(int[] par1, int par2Object)
	{
		for (int i = 0; i < par1.length; i++)
		{
			if (par1[i]== (par2Object))
			{
				return i;
			}
		}
		return -1;
	}

	public static boolean contains(Object[] par1, Object par2)
	{
		return valueOf(par1, par2) != -1;
	}

	public static boolean contains(int[] par1, int par2)
	{
		return valueOf(par1, par2) != -1;
	}

	public static String printArray(Object[] par1Objects)
	{
		String s = "Array<" + par1Objects.getClass().getSimpleName() + ">[" + par1Objects.length + "]{";
		for (Object o : par1Objects)
		{
			s += "[" + o.toString() + "], ";
		}
		s += "}";
		return s;
	}
	
	public static String printTypes(Object[] par1Objects)
	{
		String s = "Array<" + par1Objects.getClass().getSimpleName() + ">[" + par1Objects.length + "]{";
		for (Object o : par1Objects)
		{
			s += "[" + o.getClass().getSimpleName() + "], ";
		}
		s += "}";
		return s;
	}
}
