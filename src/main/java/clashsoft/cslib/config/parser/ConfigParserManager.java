package clashsoft.cslib.config.parser;

import clashsoft.cslib.src.SyntaxException;
import clashsoft.cslib.src.parser.IToken;
import clashsoft.cslib.src.parser.Parser;
import clashsoft.cslib.src.parser.ParserManager;

public class ConfigParserManager extends ParserManager
{
	private boolean	comment;
	
	public ConfigParserManager(Parser parser)
	{
		super(parser);
	}
	
	@Override
	public void parseToken(String value, IToken token) throws SyntaxException
	{
		if ("#".equals(value))
		{
			this.comment = !this.comment;
			return;
		}
		else if (value.startsWith("\n"))
		{
			this.comment = false;
			return;
		}
		else if (value.startsWith("#") && value.endsWith("#"))
		{
			return;
		}
		else if (this.comment)
		{
			return;
		}
		
		super.parse(value, token);
	}
}
