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

/**
 * @author Clashsoft
 *
 */
public class ClashsoftMisc
{
	public static void test()
	{
		System.out.println(combineColors(0x000001, 0x000003));
	}
	
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
	
	public static int combineColors(int color1, int color2)
	{
		Color c1 = new Color(color1);
		Color c2 = new Color(color2);
		
		int r = (c1.getRed() + c2.getRed()) / 2;
		int g = (c1.getGreen() + c2.getGreen()) / 2;
		int b = (c1.getBlue() + c2.getBlue()) / 2;
		
		return (b + (g * 256) + (r * 65536));
	}
	
	public static boolean checkBit(int n, int pos)
	{
        return (n & 1 << pos) != 0;
    }
	
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
	
	public static Icon registerIcon(IconRegister par1IconRegister, String par2)
	{
		return par1IconRegister.registerIcon(par2);
	}

	public static String fontColor(int light, int r, int g, int b)
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
		return "\u00a7" + Integer.toHexString(i);
	}
	
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
	
	public static void bindTexture(RenderEngine par1RenderEngine, String par2)
	{
		par1RenderEngine.bindTexture(par2);
	}
}
