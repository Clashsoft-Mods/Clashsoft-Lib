package clashsoft.clashsoftapi.util;

import java.util.Random;

import net.minecraft.client.Minecraft;

/**
 * The Class CSString. This class adds several string tools.
 */
public class CSString
{
	/** The Constant ROMANCODE. */
	private static final String[]	ROMANCODE				= { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
	
	/** The Constant BINEQUAL. */
	private static final int[]		BINEQUAL				= { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
	
	/** The Alphabet. */
	public static final String		ALPHABET				= "abcdefghijklmnopqrstuvwxyz";
	
	/** Vowels. */
	public static final String		VOWELS					= "aeiou";
	
	/** Consonants. */
	public static final String		CONSONANTS				= "bcdfghjklmnpqrstvwxyz";
	
	/** Possible consonant combinations in the english language. */
	public static final String[]	CONSONANTCOMBINATIONS	= { "bl", "br", "cl", "cr", "dr", "fl", "fr", "gl", "gr", "pl", "pr", "sh", "sk", "sl", "sm", "sn", "sp", "st", "sw", "tr", "tw", "th" };
	
	/**
	 * Case constants.
	 * 
	 * @see CSString#caseString(String, int)
	 */
	public static final int			LOWERCASE				= 0, UPPERCASE = 1, LOWER_CAMELCASE = 2, UPPER_CAMELCASE = 3, INVERTED_CASE = 4, INVERTED_LOWER_CAMELCASE = 5, INVERTED_UPPER_CAMELCASE = 6;
	
	/**
	 * Converts a number to a roman number.
	 * 
	 * @param number
	 *            the number
	 * @return the roman number
	 */
	public static String convertToRoman(int number)
	{
		if (number == 0)
			return "0";
		else if (number < 0)
			return "-" + convertToRoman(-number);
		else if (number >= 4000)
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
		StringBuilder ret = new StringBuilder(string.length());
		StringBuilder temp = new StringBuilder(10);
		
		int i = 0;
		while (i < words.length)
		{
			while (i < words.length && (temp.append(words[i])).length() <= maxLineLength)
			{
				temp.append(' ');
				i++;
			}
			ret.append(temp.toString().trim()).append('\n');
			temp.delete(0, temp.length());
			i++;
		}
		return ret.toString().trim();
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
	 * @see CSString#firstCharToCase(String, int)
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
		case LOWERCASE: // lowercase
			return string.toLowerCase();
		case UPPERCASE: // UPPERCASE
			return string.toUpperCase();
		case LOWER_CAMELCASE: // lower camelCase
		{
			String[] array = string.toLowerCase().split(" ");
			for (int i = 0; i < string.length(); i++)
				array[i] = firstCharToCase(array[i], LOWERCASE);
			return concat(" ", array);
		}
		case UPPER_CAMELCASE: // Upper CamelCase
		{
			String[] array = string.toLowerCase().split(" ");
			for (int i = 0; i < string.length(); i++)
				array[i] = firstCharToCase(array[i], UPPERCASE);
			return concat(" ", array);
		}
		case INVERTED_CASE: // iNVERTED CASE
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
		case INVERTED_LOWER_CAMELCASE: // INVERTED LOWER CAMELcASE
			return caseString(caseString(string, 2), 4);
		case INVERTED_UPPER_CAMELCASE: // iNVERTED uPPER cAMELcASE
			return caseString(caseString(string, 3), 4);
		default:
			return string;
		}
	}
	
	/**
	 * Puts an array created with {@link String#split(String)} back together.
	 * 
	 * @param split
	 *            the string used in {@link String#split(String)}.
	 * @param parts
	 *            the output of {@link String#split(String)}
	 * @return
	 */
	public static String concat(String split, String... parts)
	{
		StringBuilder result = new StringBuilder(parts.length * 10);
		for (int i = 0; i < parts.length; i++)
		{
			result.append(parts[i]);
			if (i + 1 != parts.length)
				result.append(split);
		}
		return result.toString();
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
		return VOWELS.indexOf(Character.toLowerCase(c)) != -1;
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
		return CONSONANTS.indexOf(Character.toLowerCase(c)) != -1;
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
		
		String s = new String(new char[] { Character.toLowerCase(c1), Character.toLowerCase(c2) });
		return CSArrays.contains(CONSONANTCOMBINATIONS, s);
	}
}
