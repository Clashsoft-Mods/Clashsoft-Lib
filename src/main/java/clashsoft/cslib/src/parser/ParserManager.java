package clashsoft.cslib.src.parser;

import java.io.PrintStream;

import clashsoft.cslib.src.CSSource;
import clashsoft.cslib.src.SyntaxException;

public class ParserManager
{
	protected Parser	currentParser;
	
	public ParserManager()
	{
		this(Parser.rootParser);
	}
	
	/**
	 * Creates a new {@link ParserManager} with the given {@link Parser}
	 * {@code parser} as the current parser, and calls the parser's
	 * {@link Parser#begin(ParserManager) begin} method.
	 * 
	 * @see Parser#begin(ParserManager)
	 * @param parser
	 *            the parser
	 */
	public ParserManager(Parser parser)
	{
		this.currentParser = parser;
		this.currentParser.begin(this);
	}
	
	/**
	 * Sets the current parser to the given {@link Parser} {@code parser} and
	 * starts parsing the {@link String Code} {@code code}.
	 * 
	 * @see Parser#begin(ParserManager)
	 * @see #parse(String)
	 * @param parser
	 *            the parser
	 * @param code
	 *            the code
	 */
	public final void parse(Parser parser, String code)
	{
		this.currentParser = parser;
		parser.begin(this);
		this.parse(code);
	}
	
	/**
	 * Starts parsing the given {@link String Code} {@code code}. The code is
	 * tokenized using {@link CSSource#tokenize(String)} and then
	 * {@link #parse(IToken)} is called on each token. When a
	 * {@link SyntaxException} occurs, it gets printed to the standart error
	 * output {@link System#err} using
	 * {@link SyntaxException#print(PrintStream, String, IToken)}
	 * 
	 * @see Token
	 * @see CSSource#tokenize(String)
	 * @see #parse(IToken)
	 * @param code
	 *            the code.
	 */
	public final void parse(String code)
	{
		IToken first = CSSource.tokenize(code);
		IToken token = first;
		try
		{
			boolean removed = false;
			while (token.hasNext())
			{
				IToken next = token.next();
				if (!this.retainToken(token.value(), token))
				{
					IToken prev = token.prev();
					prev.setNext(next);
					next.setPrev(prev);
					removed = true;
				}
				token = next;
			}
			
			if (removed)
			{
				token = first;
				int index = 0;
				while (token.hasNext())
				{
					token.setIndex(index);
					token = token.next();
					index++;
				}
			}
			
			token = first;
			while (token.hasNext())
			{
				try
				{
					this.parse(token.value(), token);
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
	
	public boolean retainToken(String value, IToken token)
	{
		return true;
	}
	
	/**
	 * Parses the given {@link IToken} {@code token}. You can override this
	 * method to sort out comments.
	 * 
	 * @see Parser#parse(ParserManager, String, IToken)
	 * @param value
	 *            the value of the token
	 * @param token
	 *            the token
	 * @throws SyntaxException
	 *             syntax errors
	 */
	public void parse(String value, IToken token) throws SyntaxException
	{
		this.currentParser.parse(this, value, token);
	}
	
	/**
	 * Adds the given {@link Parser} {@code parser} to the stack.
	 * 
	 * @see Parser#parse(ParserManager, String, IToken)
	 * @param parser
	 *            the parser
	 * @throws SyntaxException
	 *             syntax errors
	 */
	public void pushParser(Parser parser) throws SyntaxException
	{
		if (this.currentParser != null)
		{
			parser.setParent(this.currentParser);
		}
		this.currentParser = parser;
		parser.begin(this);
	}
	
	/**
	 * Adds the given {@link Parser} {@code parser} to the stack and makes it
	 * parse the given {@link IToken} {@code token}.
	 * 
	 * @see #pushParser(Parser)
	 * @see Parser#parse(ParserManager, String, IToken)
	 * @param parser
	 *            the parser
	 * @throws SyntaxException
	 *             syntax errors
	 */
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
