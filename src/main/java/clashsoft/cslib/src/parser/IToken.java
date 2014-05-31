package clashsoft.cslib.src.parser;

import clashsoft.cslib.src.SyntaxException;

public interface IToken
{
	public String value() throws SyntaxException;
	
	public boolean equals(String value) throws SyntaxException;
	
	public int index() throws SyntaxException;
	
	public int start() throws SyntaxException;
	
	public int end() throws SyntaxException;
	
	public IToken prev() throws SyntaxException;
	
	public IToken next() throws SyntaxException;
	
	public boolean hasPrev();
	
	public boolean hasNext();
	
	public void setPrev(IToken prev);
	
	public void setNext(IToken next);
}
