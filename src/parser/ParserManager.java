package clashsoft.cslib.src.parser;

import clashsoft.cslib.src.CSSource;
import clashsoft.cslib.src.SyntaxException;

public class ParserManager
{
	protected Parser	currentParser;
	
	public ParserManager(Parser parser)
	{
		this.currentParser = parser;
		this.currentParser.begin(this);
	}
	
	public final void parse(String code)
	{
		// Create a list of raw tokens
		IToken token = CSSource.tokenize(code);
		try
		{
			while (token.next() != null)
			{
				try
				{
					this.currentParser.parse(this, token.value(), token);
				}
				catch (SyntaxException ex)
				{
					ex.print(System.err, code, token);
				}
				token = token.next();
			}
		}
		catch (SyntaxException ex)
		{
			ex.print(System.err, code, token);
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
	
	public void pushParser(Parser parser, IToken token) throws SyntaxException
	{
		this.pushParser(parser);
		this.currentParser.parse(this, token.value(), token);
	}
	
	public void popParser() throws SyntaxException
	{
		if (this.currentParser != null)
		{
			this.currentParser.end(this);
			this.currentParser = this.currentParser.getParent();
		}
	}
	
	public void popParser(Token token) throws SyntaxException
	{
		this.popParser();
		if (this.currentParser != null)
		{
			this.currentParser.parse(this, token.value(), token);
		}
	}
}
