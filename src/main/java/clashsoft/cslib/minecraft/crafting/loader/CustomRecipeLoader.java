package clashsoft.cslib.minecraft.crafting.loader;

import java.io.*;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import cpw.mods.fml.common.registry.GameData;

import net.minecraft.item.Item;

public abstract class CustomRecipeLoader
{
	public static File	customPluginsDir	= new File("custom-plugins");
	
	static
	{
		customPluginsDir.mkdirs();
		
		File readme = new File(customPluginsDir, "readme.txt");
		try
		{
			readme.createNewFile();
			FileUtils.writeLines(readme, Arrays.asList("Custom-Plugins Directory Readme File"));
		}
		catch (IOException ex)
		{
		}
	}
	
	public String		type;
	
	public CustomRecipeLoader(String type)
	{
		this.type = type;
	}
	
	public void load()
	{
		try
		{
			File[] files = customPluginsDir.listFiles();
			
			for (File file : files)
			{
				if (file.getName().startsWith(this.type))
				{
					FileInputStream stream = new FileInputStream(file);
					DataInputStream in = new DataInputStream(stream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					System.out.println("Loading " + this.type + " recipes from: " + file);
					
					String line;
					while ((line = br.readLine()) != null)
					{
						this.processLine(line);
					}
					br.close();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void processLine(String line)
	{
		line = line.toLowerCase().replaceAll(" ", "");
		
		Item input = null;
		int inputMeta = 0;
		Item output = null;
		int outputMeta = 0;
		int amount = 1;
		float exp = 1F;
		
		if (!line.isEmpty())
		{
			String[] split = line.split(";");
			
			for (String s : split)
			{
				if (s.startsWith("input="))
				{
					input = GameData.getItemRegistry().getObject(s.substring(6));
				}
				else if (s.startsWith("input_meta="))
				{
					inputMeta = Integer.parseInt(s.substring(11));
				}
				else if (s.startsWith("output="))
				{
					output = GameData.getItemRegistry().getObject(s.substring(7));
				}
				else if (s.startsWith("output_meta="))
				{
					outputMeta = Integer.parseInt(s.substring(12));
				}
				else if (s.startsWith("amount="))
				{
					amount = Integer.parseInt(s.substring(7));
				}
				else if (s.startsWith("exp="))
				{
					exp = Float.parseFloat(s.substring(4));
				}
			}
			this.addRecipe(input, inputMeta, output, outputMeta, amount, exp);
		}
	}
	
	public abstract void addRecipe(Item input, int inputMeta, Item output, int outputMeta, int amount, float exp);
}
