package clashsoft.clashsoftapi.util;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import clashsoft.clashsoftapi.util.update.ModUpdate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author Clashsoft
 */
public class CSUtil
{
	public static ScriptEngineManager	mgr						= new ScriptEngineManager();
	public static ScriptEngine			engine					= mgr.getEngineByName("JavaScript");
	
	public static final String			CURRENT_VERION			= "1.6.4";
	public static final String			CLASHSOFT_ADFLY			= "2175784";
	
	public static final String[]		CLASHSOFT_UPDATE_NOTES	= readWebsite("https://dl.dropboxusercontent.com/s/pxm1ki6wbtxlvuv/update.txt").split("\n");
	
	public static String version(int rev)
	{
		return CURRENT_VERION + "-" + rev;
	}
	
	public static void log(Object o)
	{
		System.out.println(o);
	}
	
	/**
	 * First algorythm to sort a List by using HashSets
	 * 
	 * @param list
	 *            List to sort
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
	 * 
	 * @param list
	 *            List to sort
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
	 * Get the combined color from an array of colors
	 * 
	 * @param color1
	 * @param color2
	 * @return Color stored in an Integer
	 */
	public static int combineColors(int... par1)
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
	 * 
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
	 * 
	 * @param n
	 * @param pos
	 * @param value
	 */
	public static int setBit(int n, int pos, boolean value)
	{
		int bitToSet = 1 << pos;
		return value ? (n | bitToSet) : ((n | bitToSet) ^ bitToSet);
	}
	
	/**
	 * Creates the colorcode for a color
	 * 
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
	 * 
	 * @param name
	 * @return
	 */
	public static String fontColor(EnumFontColor fontColor)
	{
		return fontColor(fontColor.getLight(), fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue());
	}
	
	public static int fontColorInt(EnumFontColor fontColor)
	{
		return fontColorInt(fontColor.getLight(), fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue());
	}
	
	public static String convertToRoman(int number)
	{
		return CSString.convertToRoman(number);
	}
	
	public static int convertVersion(String versionString)
	{
		return CSString.convertVersion(versionString);
	}
	
	public static String cutString(String string, int maxLineLength)
	{
		return CSString.cutString(string, maxLineLength);
	}
	
	public static String[] makeLineList(String string)
	{
		return CSString.makeLineList(string);
	}
	
	public static ModUpdate checkForUpdate(String modName, String adflyName /* Unneccessary */, String version)
	{
		String newVersion = version;
		String updateNotes = "";
		for (int i = 0; i < CLASHSOFT_UPDATE_NOTES.length; i++)
		{
			String s = CLASHSOFT_UPDATE_NOTES[i];
			
			if (s.startsWith(modName))
			{
				int i0 = s.indexOf(':');
				int i1 = s.indexOf('=');
				if (i0 == -1 || i1 == -1)
					break;
				
				newVersion = s.substring(i0 + 1, i1);
				updateNotes = s.substring(i1 + 1);
				break;
			}
		}		
		return new ModUpdate(version, newVersion, updateNotes);
	}
	
	public static boolean checkWebsiteAvailable(String url)
	{
		try
		{
			URL url1 = new URL(url);
			
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) url1.openConnection();
			con.setRequestMethod("HEAD");
			int response = con.getResponseCode();
			return response == HttpURLConnection.HTTP_OK;
		}
		catch (Exception ex)
		{
			return false;
		}
	}
	
	public static String readWebsite(String url)
	{
		try
		{
			URL url1 = new URL(url);
			HttpURLConnection.setFollowRedirects(true);
			HttpURLConnection con = (HttpURLConnection) url1.openConnection();
			con.setDoOutput(false);
			con.setReadTimeout(20000);
			con.setRequestProperty("Connection", "keep-alive");
			
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/20.0");
			((HttpURLConnection) con).setRequestMethod("GET");
			con.setConnectTimeout(5000);
			BufferedInputStream in = new BufferedInputStream(con.getInputStream());
			int responseCode = con.getResponseCode();
			StringBuffer buffer = new StringBuffer();
			int chars_read;
			while ((chars_read = in.read()) != -1)
			{
				char g = (char) chars_read;
				buffer.append(g);
			}
			return buffer.toString();
		}
		catch (Exception ex)
		{
			return null;
		}
	}
	
	public static String increaseRevision(String version)
	{
		int i = version.indexOf('-');
		if (i < 0)
			return version;
		
		String s1 = version.substring(0, i + 1);
		String s2 = version.substring(i + 1, version.length());
		
		try
		{
			int i1 = Integer.parseInt(s2);
			
			return s1 + (i1 + 1);
		}
		catch (Exception ex)
		{
			return version;
		}
	}
	
	public static void notifyUpdate(EntityPlayer player, String modName, ModUpdate update)
	{
		if (update.isValid())
		{
			player.addChatMessage("A new " + modName + " version is available: " + EnumChatFormatting.GREEN + update.newVersion + EnumChatFormatting.RESET + ". You are using " + EnumChatFormatting.RED + update.version);
			if (!update.updateNotes.isEmpty())
				player.addChatMessage(EnumChatFormatting.RESET + "Update Notes: " + EnumChatFormatting.ITALIC + update.updateNotes);
		}
	}
	
	public static double calculateFromString(String string)
	{
		try
		{
			return (Double) engine.eval(string);
		}
		catch (ScriptException e)
		{
			return Double.NaN;
		}
	}
	
	public static boolean createBoolean(String string)
	{
		try
		{
			return (Boolean) engine.eval(string);
		}
		catch (ScriptException e)
		{
			return false;
		}
	}
	
	public static <T> T createInstance(Class c, Object... parameters)
	{
		Class[] parameterTypes = new Class[parameters.length];
		for (int i = 0; i < parameters.length; i++)
		{
			if (parameters[i] != null)
				parameterTypes[i] = parameters[i].getClass();
		}
		
		try
		{
			Constructor<T> constructor = c.getConstructor(parameterTypes);
			return constructor.newInstance(parameters);
		}
		catch (Exception ex)
		{
			return null;
		}
	}
}
