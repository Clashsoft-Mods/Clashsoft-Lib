package clashsoft.cslib.src.parser;

public class Token
{
	private Token		next;
	private Token		prev;
	
	public final int	index;
	public final String	value;
	public final int	start;
	public final int	end;
	
	public Token(int index, String value, String code)
	{
		this.index = index;
		this.value = value;
		this.start = code.indexOf(value);
		this.end = this.start + value.length();
	}
	
	public Token(int index, String value, int start, int end)
	{
		this.index = index;
		this.value = value;
		this.start = start;
		this.end = end;
	}
	
	public void setPrev(Token prev)
	{
		this.prev = prev;
	}
	
	public void setNext(Token next)
	{
		this.next = next;
	}
	
	public String get()
	{
		return this.value;
	}
	
	public Token next()
	{
		return this.next;
	}
	
	public Token prev()
	{
		return this.prev;
	}
	
	@Override
	public String toString()
	{
		return "\"" + this.value + "\" (pos " + this.start + ")";
	}
}
