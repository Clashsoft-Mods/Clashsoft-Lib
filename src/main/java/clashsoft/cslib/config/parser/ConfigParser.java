package clashsoft.cslib.config.parser;

import clashsoft.cslib.config.ConfigCategory;
import clashsoft.cslib.src.SyntaxException;
import clashsoft.cslib.src.parser.IToken;
import clashsoft.cslib.src.parser.Parser;
import clashsoft.cslib.src.parser.ParserManager;

public class ConfigParser extends Parser<ConfigCategory>
{
	public ConfigCategory	config;
	
	private String			name;
	
	public ConfigParser(ConfigCategory config)
	{
		this.name = "";
		this.config = config;
	}
	
	@Override
	public boolean parse(ParserManager pm, String value, IToken token) throws SyntaxException
	{
		if ("{".equals(value))
		{
			this.name = this.name.trim();
			if (!this.name.equals(this.config.name))
			{
				ConfigCategory sub = this.config.addSubCategory(this.name, null);
				pm.pushParser(new ConfigParser(sub));
				this.name = "";
			}
			return true;
		}
		else if ("}".equals(value))
		{
			pm.popParser();
			return true;
		}
		else if (":".equals(token.next().value()))
		{
			pm.pushParser(new OptionParser(this.config), token);
			return true;
		}
		else
		{
			this.name += value + " ";
			return true;
		}
	}
}
