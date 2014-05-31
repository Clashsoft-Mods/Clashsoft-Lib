package clashsoft.cslib.config.parser;

import clashsoft.cslib.config.ConfigCategory;
import clashsoft.cslib.src.SyntaxException;
import clashsoft.cslib.src.parser.IToken;
import clashsoft.cslib.src.parser.Parser;
import clashsoft.cslib.src.parser.ParserManager;

public class OptionParser extends Parser<ConfigCategory>
{
	public final ConfigCategory category;
	
	public String type;
	public String name;
	public String comment;
	public String value;
	
	public OptionParser(ConfigCategory category)
	{
		this.category = category;
	}
	
	@Override
	public void parse(ParserManager pm, String value, IToken token) throws SyntaxException
	{
		if (":".equals(value))
		{
			this.type = token.prev().value();
		}
		else if ("=".equals(value))
		{
			this.name = token.prev().value();
			this.value = token.next().value();
		}
		else if (this.value != null)
		{
			Object obj = ConfigCategory.parseObject(this.type, this.value);
			this.category.addOption(this.name, this.comment, obj);
			
			pm.popParser();
		}
	}
}
