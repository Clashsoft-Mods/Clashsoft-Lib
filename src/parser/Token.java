package clashsoft.cslib.src.parser;


public class Token implements IToken
{
	private IToken			prev;
	private IToken			next;
	
	private final int		index;
	private final String	value;
	private final int		start;
	private final int		end;
	
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
	
	@Override
	public void setPrev(IToken prev)
	{
		this.prev = prev;
	}
	
	@Override
	public void setNext(IToken next)
	{
		this.next = next;
	}
	
	@Override
	public String value()
	{
		return this.value;
	}
	
	@Override
	public int index()
	{
		return this.index;
	}
	
	@Override
	public int start()
	{
		return this.start;
	}
	
	@Override
	public int end()
	{
		return this.end;
	}
	
	@Override
	public IToken next()
	{
		if (this.next == null)
		{
			this.next = new FakeToken();
			this.next.setPrev(this);
		}
		return this.next;
	}
	
	@Override
	public IToken prev()
	{
		if (this.prev == null)
		{
			this.prev = new FakeToken();
			this.prev.setNext(this);
		}
		return this.prev;
	}
	
	public boolean equals(String value)
	{
		return value.equals(this.value);
	}
	
	@Override
	public String toString()
	{
		return "Token #" + this.index + "\"" + this.value + "\" (" + this.start + "-" + this.end + ")";
	}
}
