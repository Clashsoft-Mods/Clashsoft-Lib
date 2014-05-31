package clashsoft.cslib.config;

import java.io.File;
import java.util.*;

import clashsoft.cslib.config.parser.ConfigParser;
import clashsoft.cslib.config.parser.ConfigParserManager;
import clashsoft.cslib.io.CSFiles;
import clashsoft.cslib.src.parser.ParserManager;


public class ConfigCategory
{
	public ConfigCategory					parent;
	public final String						name;
	public String							comment;
	
	protected boolean						hasChanged;
	
	protected Map<String, Object>			options;
	protected Map<String, String>			comments;
	protected Map<String, ConfigCategory>	subCategories;
	
	public ConfigCategory(String name)
	{
		this(null, name);
	}
	
	public ConfigCategory(ConfigCategory parent, String name)
	{
		this.parent = parent;
		this.name = name;
		this.comment = name + " settings";
	}
	
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	public <T> T getOption(String name)
	{
		if (this.options == null)
			this.options = new TreeMap();
		return (T) this.options.get(name);
	}
	
	public <T> T getOption(String name, T _default)
	{
		T o = this.getOption(name);
		if (o == null)
		{
			this.addOption(name, null, _default);
			return _default;
		}
		return o;
	}
	
	public <T> T getOption(String name, String comment, T _default)
	{
		this.addComment(name, comment);
		return this.getOption(name, _default);
	}
	
	public <T> T getOption(String name, String category, String comment, T _default)
	{
		ConfigCategory category1 = this.getSubCategory(category);
		return category1.getOption(name, comment, _default);
	}
	
	public ConfigCategory getSubCategory(String name)
	{
		if (name == null)
			return this;
		
		ConfigCategory category = null;
		if (this.subCategories != null)
			category = this.subCategories.get(name);
		if (category == null)
			category = this.addSubCategory(name, null);
		return category;
	}
	
	public String getComment(String name)
	{
		if (this.comments != null)
			return this.comments.get(name);
		return null;
	}
	
	public void addOption(String name, String comment, Object value)
	{
		if (this.options == null)
			this.options = new TreeMap();
		this.options.put(name, value);
		this.hasChanged = true;
		
		this.addComment(name, comment);
	}
	
	public ConfigCategory addSubCategory(String name, String comment)
	{
		ConfigCategory category = new ConfigCategory(this, name);
		
		if (this.subCategories == null)
			this.subCategories = new TreeMap();
		this.subCategories.put(name, category);
		this.hasChanged = true;
		
		this.addComment(name, comment);
		
		return category;
	}
	
	public void addComment(String name, String comment)
	{
		if (comment != null)
		{
			if (this.comments == null)
				this.comments = new HashMap();
			this.comments.put(name, comment);
			this.hasChanged = true;
		}
	}
	
	public void save(File file)
	{
		if (this.hasChanged)
		{
			StringBuilder buffer = new StringBuilder();
			this.write(buffer, "");
			
			CSFiles.write(file, buffer.toString());
		}
	}
	
	public void load(File file)
	{
		String text = CSFiles.read(file);
		this.read(text);
	}
	
	public void write(StringBuilder buffer, String prefix)
	{
		String prefix1 = prefix + "    ";
		
		if (this.comment != null)
			addSectionComment(buffer, prefix, this.comment);
		
		buffer.append(prefix).append(this.name).append('\n');
		buffer.append(prefix).append("{\n");
		
		if (this.options != null)
			for (Map.Entry<String, Object> entry : this.options.entrySet())
			{
				String key = entry.getKey();
				Object value = entry.getValue();
				String comment = this.getComment(key);
				char c = value.getClass().getSimpleName().charAt(0);
				
				if (comment != null)
				{
					buffer.append(prefix1).append("# ").append(comment).append(" #").append('\n');
				}
				
				buffer.append(prefix1).append(c).append(':').append(key).append('=').append(String.valueOf(value)).append('\n');
				buffer.append(prefix1).append('\n');
			}
		
		if (this.subCategories != null)
			for (Map.Entry<String, ConfigCategory> entry : this.subCategories.entrySet())
			{
				String key = entry.getKey();
				ConfigCategory value = entry.getValue();
				String comment = this.getComment(key);
				
				if (comment != null)
				{
					buffer.append(prefix1).append('\n');
					addSectionComment(buffer, prefix1, comment);
				}
				
				value.write(buffer, prefix1);
				buffer.append('\n');
			}
		
		buffer.append(prefix).append("}\n");
	}
	
	private static void addSectionComment(StringBuilder buffer, String prefix, String comment)
	{
		char[] chars = new char[comment.length() + 4];
		Arrays.fill(chars, '#');
		
		buffer.append(prefix).append(chars).append('\n');
		buffer.append(prefix).append("# ").append(comment).append(" #").append('\n');
		buffer.append(prefix).append(chars).append('\n');
		buffer.append(prefix).append('\n');
	}
	
	public void read(String text)
	{
		ConfigParser parser = new ConfigParser(this);
		ParserManager manager = new ConfigParserManager(parser);
		manager.parse(text);
	}
	
	public void read(List<String> lines, int i)
	{
		String name = null;
		String comment = null;
		String category = null;
		
		for (; i < lines.size(); i++)
		{
			String line = lines.get(i).trim();
			
			if (line.length() < 2)
				continue;
			
			if (line.charAt(0) == '#')
			{
				if (line.charAt(1) != '#')
					comment = line.substring(2);
				continue;
			}
			
			int index = line.indexOf(':');
			if (index != -1)
			{
				String type = line.substring(0, index);
				int index1 = line.indexOf('=', index);
				if (index1 != -1)
				{
					name = line.substring(index + 1, index1);
					String s = line.substring(index1 + 1);
					Object value = parseObject(type, s);
					
					this.addOption(name, comment, value);
					comment = null;
				}
			}
			else if (line.startsWith("{"))
			{
				ConfigCategory category1 = this.getSubCategory(category);
				category1.read(lines, i + 1);
			}
			else if (line.startsWith("}"))
			{
				return;
			}
			else
			{
				int index1 = line.indexOf('{');
				if (index1 != -1)
				{
					category = line.substring(0, index1).trim();
					ConfigCategory category1 = this.addSubCategory(category, comment);
					category1.read(lines, i + 1);
				}
				else
				{
					category = line;
					this.addSubCategory(category, comment);
				}
				comment = null;
			}
		}
		
		this.hasChanged = false;
	}
	
	public static Object parseObject(String type, String value)
	{
		if (type.startsWith("S"))
			return value;
		else if (type.startsWith("B"))
			return Boolean.valueOf(value);
		else if (type.startsWith("I"))
			return Integer.valueOf(value);
		else if (type.startsWith("L"))
			return Long.valueOf(value);
		else if (type.startsWith("F"))
			return Float.valueOf(value);
		else if (type.startsWith("D"))
			return Double.valueOf(value);
		return value;
	}
}
