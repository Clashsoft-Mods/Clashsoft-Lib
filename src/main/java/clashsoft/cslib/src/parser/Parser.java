package clashsoft.cslib.src.parser;

import clashsoft.cslib.src.CSSource;
import clashsoft.cslib.src.SyntaxException;

public abstract class Parser<T>
{
	public static final Parser	rootParser	= new Parser()
											{
												@Override
												public void parse(ParserManager pm, String value, IToken token) throws SyntaxException
												{
												}
											};
	
	private Parser				parent;
	protected int				modifiers;
	
	public Parser()
	{
		this.parent = rootParser;
	}
	
	public Parser(Parser parent)
	{
		this.parent = parent;
	}
	
	public Parser getParent()
	{
		return this.parent;
	}
	
	public void setParent(Parser parent)
	{
		if (parent != null)
		{
			this.parent = parent;
		}
	}
	
	public int addModifier(int mod)
	{
		this.modifiers |= mod;
		return this.modifiers;
	}
	
	public int removeModifier(int mod)
	{
		this.modifiers &= ~mod;
		return this.modifiers;
	}
	
	public boolean checkModifier(String token)
	{
		int mod = CSSource.parseModifier(token);
		if (mod != 0)
		{
			this.addModifier(mod);
			return true;
		}
		return false;
	}
	
	public void begin(ParserManager pm)
	{
	}
	
	public abstract void parse(ParserManager pm, String value, IToken token) throws SyntaxException;
	
	public void end(ParserManager pm)
	{
	}
}
