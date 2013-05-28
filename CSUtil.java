package clashsoft.clashsoftapi;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author Clashsoft
 *
 */
public class CSUtil
{	
	private static final String[] ROMANCODE = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
	private static final int[]    BINEQUAL  = {1000, 900, 500, 400,  100, 90,  	50,	 40,   10,  9,    5,   4,    1};
	
	public static final String CURRENT_VERION = "1.5.2";
	
	/**
	 * First algorythm to sort a List by using HashSets
	 * @param list List to sort
	 * @return Sorted List
	 */
	public static List removeDuplicates(List list)
	{
		if (list != null && list.size() > 0)
		{
			Set set = new HashSet(list);
			list.clear();
			list = new LinkedList<String>(set);
		    return list;
		}
		return list;
	}
	
	/**
	 * Second algorythm to sort a list by searching for duplicates
	 * @param list List to sort
	 * @return Sorted List
	 */
	public static List removeDuplicates2(List list)
	{
		if (list != null && list.size() > 0)
		{
			List result = new ArrayList();
			for (Object item : list)
			{
				boolean duplicate = false;
				for (Object ob : result)
				{
					if (ob.equals(item))
					{
						duplicate = true;
						break;
					}
				}
				if (!duplicate)
				{
					result.add(item);
				}
			}
			return result;
		}
		return list;
	}
	
	
	/**
	 * Get the combined color from two colors
	 * @param color1
	 * @param color2
	 * @return Color stored in an Integer
	 */
	public static int combineColors(int color1, int color2)
	{
		Color c1 = new Color(color1);
		Color c2 = new Color(color2);
		
		int r = (c1.getRed() + c2.getRed()) / 2;
		int g = (c1.getGreen() + c2.getGreen()) / 2;
		int b = (c1.getBlue() + c2.getBlue()) / 2;
		
		return (b + (g * 256) + (r * 65536));
	}
	
	/**
	 * Get the combined color from an array of colors
	 * @param color1
	 * @param color2
	 * @return Color stored in an Integer
	 */
	public static int combineColors(int[] par1)
	{
		int r = 0;
		int g = 0;
		int b = 0;
		for (int i : par1)
		{
			Color c = new Color(i);
			r += c.getRed();
			g += c.getGreen();
			b += c.getBlue();
		}
		r /= par1.length;
		g /= par1.length;
		b /= par1.length;
		
		return (b + (g * 256) + (r * 65536));
	}
	
	/**
	 * Checks if bit 'pos' in 'n' is 1
	 * @param n
	 * @param pos
	 * @return
	 */
	public static boolean checkBit(int n, int pos)
	{
        return (n & 1 << pos) != 0;
    }
	
	/**
	 * Sets bit 'pos' in Integer 'n' to 'value'
	 * @param n
	 * @param pos
	 * @param value
	 */
	public static int setBit(int n, int pos, boolean value)
	{
		int bitToSet = 1 << pos;
		return  value ? (n | bitToSet) : ((n | bitToSet) ^ bitToSet);
	}

	/**
	 * Creates the colorcode for a color
	 * @param light
	 * @param r
	 * @param g
	 * @param b
	 * @return Colorcode
	 */
	public static String fontColor(int light, int r, int g, int b)
	{
		return "\u00a7" + Integer.toHexString(fontColorInt(light, r, g, b));
	}
	
	public static int fontColorInt(int light, int r, int g, int b)
	{
		int i = b > 0 ? 1 : 0;
		if (g > 0)
		{
			i += 2;
		}
		if (r > 0)
		{
			i += 4;
		}
		if (light > 0)
		{
			i += 8;
		}
		return i;
	}
	
	/**
	 * Converts a color name to a color code
	 * @param name
	 * @return
	 */
	public static String fontColor(String name)
	{
		if (name == "black")
		{
			return fontColor(0, 0, 0, 0);
		}
		else if (name == "blue")
		{
			return fontColor(0, 0, 0, 1);
		}
		else if (name == "green")
		{
			return fontColor(0, 0, 1, 0);
		}
		else if (name == "cyan")
		{
			return fontColor(0, 0, 1, 1);
		}
		else if (name == "red")
		{
			return fontColor(0, 1, 0, 0);
		}
		else if (name == "purple")
		{
			return fontColor(0, 1, 0, 1);
		}
		else if (name == "orange")
		{
			return fontColor(0, 1, 1, 0);
		}
		else if (name == "lightgray")
		{
			return fontColor(0, 1, 1, 1);
		}
		else if (name == "darkgray")
		{
			return fontColor(1, 0, 0, 0);
		}
		else if (name == "purpleblue")
		{
			return fontColor(1, 0, 0, 1);
		}
		else if (name == "lightgreen")
		{
			return fontColor(1, 0, 1, 0);
		}
		else if (name == "lightblue")
		{
			return fontColor(1, 0, 1, 1);
		}
		else if (name == "lightred")
		{
			return fontColor(1, 1, 0, 0);
		}
		else if (name == "pink")
		{
			return fontColor(1, 1, 0, 1);
		}
		else if (name == "yellow")
		{
			return fontColor(1, 1, 1, 0);
		}
		else
		{
			return fontColor(1, 1, 1, 1);
		}
	}
	
	public static int fontColorInt(String name)
	{
		if (name == "black")
		{
			return fontColorInt(0, 0, 0, 0);
		}
		else if (name == "blue")
		{
			return fontColorInt(0, 0, 0, 1);
		}
		else if (name == "green")
		{
			return fontColorInt(0, 0, 1, 0);
		}
		else if (name == "cyan")
		{
			return fontColorInt(0, 0, 1, 1);
		}
		else if (name == "red")
		{
			return fontColorInt(0, 1, 0, 0);
		}
		else if (name == "purple")
		{
			return fontColorInt(0, 1, 0, 1);
		}
		else if (name == "orange")
		{
			return fontColorInt(0, 1, 1, 0);
		}
		else if (name == "lightgray")
		{
			return fontColorInt(0, 1, 1, 1);
		}
		else if (name == "darkgray")
		{
			return fontColorInt(1, 0, 0, 0);
		}
		else if (name == "purpleblue")
		{
			return fontColorInt(1, 0, 0, 1);
		}
		else if (name == "lightgreen")
		{
			return fontColorInt(1, 0, 1, 0);
		}
		else if (name == "lightblue")
		{
			return fontColorInt(1, 0, 1, 1);
		}
		else if (name == "lightred")
		{
			return fontColorInt(1, 1, 0, 0);
		}
		else if (name == "pink")
		{
			return fontColorInt(1, 1, 0, 1);
		}
		else if (name == "yellow")
		{
			return fontColorInt(1, 1, 1, 0);
		}
		else
		{
			return fontColorInt(1, 1, 1, 1);
		}
	}
	
	public static String convertToRoman(int number)
	{
		if (number <= 0 || number >= 4000)
		{
            System.out.println("Exception while converting to Roman: Value outside roman numeral range.");
            return "\u007akROMAN";
        }
        String roman = "";
        
        for (int i = 0; i < ROMANCODE.length; i++)
        {
            while (number >= BINEQUAL[i])
            {
                number -= BINEQUAL[i];
                roman  += ROMANCODE[i];
            }
        }
        return roman;
	}
	
	public static int convertVersion(String versionString)
	{
		String version = versionString.replace('.', '|');
		System.out.println(version);
		String[] s = version.split("|");
		int[] ints = new int[s.length];
		for (int i = 0; i < s.length; i++)
		{
			try
			{
				ints[i] = Integer.parseInt(s[i]);
			}
			catch (Exception ex)
			{
				//System.out.println("Exception while converting Version String: non-numerals contained.");
			}
		}
		int ret = 0;
		for (int i = 0; i < ints.length; i++)
		{
			ret += (ints[i] << (i * 4));
		}
		return ret;
	}
	
	public static String cutString(String string, int maxLineLength)
	{
		String[] words = string.split(" ");
		String ret = "";
		int i = 0;
		while (i < words.length)
		{
			String s = "";
			while (i < words.length && (s += words[i]).length() <= maxLineLength)
			{
				s += " ";
				i++;
			}
			ret += s + "\n";
			i++;
		}
		return ret;
	}
	
	public static String[] makeLineList(String string)
	{
		return string.split("\n");
	}
}
