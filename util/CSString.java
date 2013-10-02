package clashsoft.clashsoftapi.util;

import java.util.Random;

import net.minecraft.client.Minecraft;

/**
 * The Class CSString.
 * 
 * This class adds several string tools.
 */
public class CSString
{
	/** The Constant ROMANCODE. */
	private static final String[]	ROMANCODE				= { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
	
	/** The Constant BINEQUAL. */
	private static final int[]		BINEQUAL				= { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
	
	/** The Constant ALPHABET. */
	public static final String		ALPHABET				= "abcdefghijklmnopqrstuvwxyz";
	
	/** The Constant VOWELS. */
	public static final String		VOWELS					= "aeiou";
	
	/** The Constant CONSONANTS. */
	public static final String		CONSONANTS				= "bcdfghjklmnpqrstvwxyz";
	
	/** The Constant CONSONANTCOMBINATIONS. */
	public static final String[]	CONSONANTCOMBINATIONS	= { "bl", "br", "cl", "cr", "dr", "fl", "fr", "gl", "gr", "pl", "pr", "sh", "sk", "sl", "sm", "sn", "sp", "st", "sw", "tr", "tw", "th" };
	
	/**
	 * Converts a number to a roman number.
	 * 
	 * @param number
	 *            the number
	 * @return the roman number
	 */
	public static String convertToRoman(int number)
	{
		if (number <= 0 || number >= 4000)
		{
			System.out.println("Exception while converting to Roman: Value outside roman numeral range.");
			return String.valueOf(number);
		}
		StringBuilder roman = new StringBuilder();
		
		for (int i = 0; i < ROMANCODE.length; i++)
		{
			while (number >= BINEQUAL[i])
			{
				number -= BINEQUAL[i];
				roman.append(ROMANCODE[i]);
			}
		}
		return roman.toString();
	}
	
	/**
	 * Trims a string to the given render width.
	 * <p>
	 * If the renderer string length is longer than the maximum render width
	 * given, the last chars will be replaced with "...".
	 * 
	 * @param string
	 *            the string
	 * @param maxRenderWidth
	 *            the max render width
	 * @return the string
	 */
	public static String trimStringToRenderWidth(String string, int maxRenderWidth)
	{
		Minecraft mc = Minecraft.getMinecraft();
		int i = mc.fontRenderer.getStringWidth("...");
		boolean flag = false;
		while (string.length() > 0 && mc.fontRenderer.getStringWidth(string) + i > maxRenderWidth - 6)
		{
			string = string.substring(0, string.length() - 1);
			flag = true;
		}
		if (flag)
			string += "...";
		
		return string;
	}
	
	/**
	 * Wraps a string to make multiple lines with a maximum length. It doesn't
	 * cut words
	 * 
	 * @param string
	 *            the string
	 * @param maxLineLength
	 *            the max line length
	 * @return the string
	 */
	public static String cutString(String string, int maxLineLength)
	{
		String[] words = string.split(" ");
		String ret = "";
		int i = 0;
		while (i < words.length)
		{
			String s = "";
			while (i < words.length && (s += words[i]).length() <= maxLineLength)
			{
				s += " ";
				i++;
			}
			ret += s.trim() + "\n";
			i++;
		}
		return ret.trim();
	}
	
	/**
	 * Makes a line array for rendering.
	 * 
	 * @param string
	 *            the string
	 * @return the string[]
	 */
	public static String[] makeLineList(String string)
	{
		return string.split("\n");
	}
	
	/**
	 * Gets the initials of a string.
	 * <p>
	 * Example:
	 * <p>
	 * {@code getInitials("Hello World")} returns "HW";
	 * {@code getInitials("Half-Life 3")} returns "HL3"
	 * 
	 * @param string
	 *            the string
	 * @return the initials
	 */
	public static String getInitials(String string)
	{
		StringBuilder builder = new StringBuilder(string.length());
		for (String s : string.split("\\p{Punct}"))
			builder.append(Character.toUpperCase(s.charAt(0)));
		return builder.toString();
	}
	
	/**
	 * Case a string
	 * <p>
	 * Modes:
	 * <p>
	 * 0: lowercase 1: UPPERCASE 2: lower camelCase 3: Upper CamelCase 4:
	 * iNVERTED CASE 5: INVERTED LOWER CAMELcASE 6: iNVERTED uPPER cAMELcASE
	 * 
	 * @param string
	 *            the string
	 * @param mode
	 *            the mode
	 * @return the string
	 */
	public static String caseString(String string, int mode)
	{
		switch (mode)
		{
		case 0: // lowercase
			return string.toLowerCase();
		case 1: // UPPERCASE
			return string.toUpperCase();
		case 2: // lower camelCase
			return firstCharToCase(string, 0);
		case 3: // Upper CamelCase
			return firstCharToCase(string, 1);
		case 4: // iNVERTED CASE
		{
			StringBuilder ret = new StringBuilder(string.length());
			for (char c : string.toCharArray())
			{
				if (Character.isUpperCase(c))
					ret.append(Character.toLowerCase(c));
				else
					ret.append(Character.toUpperCase(c));
			}
			return ret.toString();
		}
		case 5: // INVERTED LOWER CAMELcASE
			return caseString(caseString(string, 2), 4);
		case 6: // iNVERTED uPPER cAMELcASE
			return caseString(caseString(string, 3), 4);
		default:
			return string;
		}
	}
	
	/**
	 * Returns the string with the first char being an uppercase char.
	 * 
	 * @param string
	 *            the string
	 * @return the string
	 */
	@Deprecated
	public static String firstCharToUpperCase(String string)
	{
		return firstCharToCase(string, 1);
	}
	
	/**
	 * Returns the string with the first string being a) a lowercase char or b)
	 * an uppercase char
	 * <p>
	 * Modes:
	 * <p>
	 * 0: lowercase 1: Uppercase
	 * 
	 * @param string
	 *            the string
	 * @param mode
	 *            the mode
	 * @return the string
	 */
	public static String firstCharToCase(String string, int mode)
	{
		return (mode == 0 ? Character.toLowerCase(string.charAt(0)) : Character.toUpperCase(string.charAt(0))) + string.substring(1);
	}
	
	/**
	 * Checks the char is a vowel.
	 * 
	 * @param c
	 *            the char
	 * @return true, if the char is a vowel
	 */
	public static boolean isVowel(char c)
	{
		return VOWELS.indexOf(c) != -1;
	}
	
	/**
	 * Checks if the char is a consonant.
	 * 
	 * @param c
	 *            the char
	 * @return true, if the char is a consonant
	 */
	public static boolean isConsonant(char c)
	{
		return CONSONANTS.indexOf(c) != -1;
	}
	
	/**
	 * Returns a new random letter (a-z).
	 * 
	 * @param random
	 *            the random
	 * @return the random letter
	 */
	public static char nextLetter(Random random)
	{
		return ALPHABET.charAt(random.nextInt(ALPHABET.length()));
	}
	
	/**
	 * Returns a new random vowel (a, e, i, o, u)
	 * 
	 * @param random
	 *            the random
	 * @return the random vowel
	 */
	public static char nextVowel(Random random)
	{
		return VOWELS.charAt(random.nextInt(VOWELS.length()));
	}
	
	/**
	 * Returns a new random consonant (b, c, d, f, g, h, j, k, l, m, n, p, q, r,
	 * s, t, v, w, x, y, z)
	 * 
	 * @param random
	 *            the random
	 * @return the random consonant
	 */
	public static char nextConsonant(Random random)
	{
		return CONSONANTS.charAt(random.nextInt(CONSONANTS.length()));
	}
	
	/**
	 * Checks if char c2 can directly follow c1 in normal english.
	 * 
	 * @param c1
	 *            the first char
	 * @param c2
	 *            the second char
	 * @return true, if c1 can follow c2
	 */
	public static boolean canCharFollowChar(char c1, char c2)
	{
		if (isVowel(c1) || isVowel(c2))
			return true;
		
		String s = (c1 + "" + c2).toLowerCase();
		return CSArrays.contains(CONSONANTCOMBINATIONS, s);
	}
}
