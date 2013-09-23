package clashsoft.clashsoftapi.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.minecraft.client.Minecraft;

public class CSString
{
	private static final String[]	ROMANCODE	= { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
	private static final int[]		BINEQUAL	= { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
	
	public static String convertToRoman(int number)
	{
		if (number <= 0 || number >= 4000)
		{
			System.out.println("Exception while converting to Roman: Value outside roman numeral range.");
			return String.valueOf(number);
		}
		StringBuilder roman = new StringBuilder();
		
		for (int i = 0; i < ROMANCODE.length; i++)
		{
			while (number >= BINEQUAL[i])
			{
				number -= BINEQUAL[i];
				roman.append(ROMANCODE[i]);
			}
		}
		return roman.toString();
	}
	
	public static int convertVersion(String versionString)
	{
		String[] s = versionString.split("\\.");
		int[] ints = new int[s.length];
		for (int i = 0; i < s.length; i++)
		{
			try
			{
				ints[i] = Integer.parseInt(s[i]);
			}
			catch (Exception ex)
			{
				System.out.println("Exception while converting Version String: non-digits contained.");
			}
		}
		int ret = 0;
		for (int i = 0; i < ints.length; i++)
		{
			ret |= (ints[i] << (i * 4));
		}
		return ret;
	}
	
	public static String trimStringToRenderWidth(String string, int maxRenderWidth)
	{
		Minecraft mc = Minecraft.getMinecraft();
		int i = mc.fontRenderer.getStringWidth("...");
		boolean flag = false;
		while(string.length() > 0 && mc.fontRenderer.getStringWidth(string) + i > maxRenderWidth - 6)
		{
			string = string.substring(0, string.length() - 1);
			flag = true;
		}
		if (flag)
			string += "...";
		
		return string;
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
			ret += s.trim() + "\n";
			i++;
		}
		return ret.trim();
	}
	
	public static String[] makeLineList(String string)
	{
		return string.split("\n");
	}
	
	public static double calculateFromString(String string)
	{
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		
		try
		{
			return (Double) engine.eval(string);
		}
		catch (ScriptException e)
		{
			return Double.NaN;
		}
	}
}
