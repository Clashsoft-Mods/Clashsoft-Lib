package clashsoft.cslib.util;

public class CSSource extends CSString
{
	public static boolean isQuoted(String string, int pos)
	{
		return isQuoted(analyseSource(string, pos));
	}
	
	public static boolean isQuoted(int[] data)
	{
		return (data[4] & 1) != 0;
	}
	
	public static boolean isCharQuoted(String string, int pos)
	{
		return isCharQuoted(analyseSource(string, pos));
	}
	
	public static boolean isCharQuoted(int[] data)
	{
		return (data[4] & 2) != 0;
	}
	
	public static boolean isLiteral(String string, int pos)
	{
		return isLiteral(analyseSource(string, pos));
	}
	
	public static boolean isLiteral(int[] data)
	{
		return (data[4] & 4) != 0;
	}
	
	public static int getParenthesisDepth(String string, int pos)
	{
		return getParenthesisDepth(analyseSource(string, pos));
	}
	
	public static int getParenthesisDepth(int[] data)
	{
		return data[0];
	}
	
	public static int getSquareBracketDepth(String string, int pos)
	{
		return getSquareBracketDepth(analyseSource(string, pos));
	}
	
	public static int getSquareBracketDepth(int[] data)
	{
		return data[1];
	}
	
	public static int getCurlyBracketDepth(String string, int pos)
	{
		return getCurlyBracketDepth(analyseSource(string, pos));
	}
	
	public static int getCurlyBracketDepth(int[] data)
	{
		return data[2];
	}
	
	public static int getAngleBracketDepth(String string, int pos)
	{
		return getAngleBracketDepth(analyseSource(string, pos));
	}
	
	public static int getAngleBracketDepth(int[] data)
	{
		return data[3];
	}
	
	public static int[] analyseSource(String string, int pos)
	{
		int depth1 = 0;
		int depth2 = 0;
		int depth3 = 0;
		int depth4 = 0;
		int other = 0;
		boolean quote = false;
		boolean charQuote = false;
		boolean literal = false;
		
		for (int i = 0; (i < pos) && (i < string.length()); i++)
		{
			char c = string.charAt(i);
			
			if (!literal)
			{
				if (c == '"')
					quote = !quote;
				else if (c == '\'')
					charQuote = !charQuote;
				else if (c == '\\')
					literal = true;
				else if (!quote && !charQuote)
				{
					switch (c)
					{
					case '(':
						depth1++;
					case ')':
						depth1--;
					case '[':
						depth2++;
					case ']':
						depth2--;
					case '{':
						depth3++;
					case '}':
						depth3--;
					case '<':
						depth4++;
					case '>':
						depth4--;
					default:
						continue;
					}
				}
			}
			else
				literal = false;
		}
		
		other = (literal ? 4 : 0) | (charQuote ? 2 : 0) | (quote ? 1 : 0);
		
		return new int[] { depth1, depth2, depth3, depth4, other};
	}
}
