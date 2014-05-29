package clashsoft.cslib.src.parser;

import clashsoft.cslib.src.CSSource;
import clashsoft.cslib.src.SyntaxException;

public class ParserManager
{
	protected Parser	currentParser;
	
	public final void parse(String code)
	{
		// Create a list of raw tokens
		Token token = CSSource.tokenize(code);
		while (token.next() != null)
		{
			try
			{
				this.currentParser.parse(this, token.value, token);
			}
			catch (SyntaxException ex)
			{
				ex.print(System.err, code, token);
			}
			token = token.next();
		}
	}
	
	public void parse(Parser parser, String code) throws SyntaxException
	{
		this.currentParser = parser;
		parser.begin(this);
		this.parse(code);
	}
	
	public void pushParser(Parser parser) throws SyntaxException
	{
		if (this.currentParser != null)
		{
			parser.setParent(this.currentParser);
		}
		this.currentParser = parser;
		parser.begin(this);
	}
	
	public void pushParser(Parser parser, Token token) throws SyntaxException
	{
		this.pushParser(parser);
		parser.parse(this, token.value, token);
	}
	
	public Parser popParser() throws SyntaxException
	{
		if (this.currentParser != null)
		{
			Parser parser = this.currentParser;
			parser.end(this);
			this.currentParser = parser.getParent();
			return parser;
		}
		return null;
	}
}
