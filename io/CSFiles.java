package clashsoft.cslib.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class CSFiles
{
	public static boolean write(File file, String text)
	{
		try
		{
			if (!file.exists())
			{
				file.createNewFile();
			}
			
			byte[] bytes = text.getBytes();
			Files.write(file.toPath(), bytes);
			return true;
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public static boolean writeLines(File file, List<String> lines)
	{
		try
		{
			if (!file.exists())
			{
				file.createNewFile();
			}
			
			Files.write(file.toPath(), lines, Charset.defaultCharset());
			return true;
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public static String read(File file)
	{
		try
		{
			if (!file.exists())
			{
				return null;
			}
			
			byte[] bytes = Files.readAllBytes(file.toPath());
			return new String(bytes);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public static List<String> readLines(File file)
	{
		try
		{
			if (!file.exists())
			{
				return null;
			}
			
			return Files.readAllLines(file.toPath(), Charset.defaultCharset());
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
