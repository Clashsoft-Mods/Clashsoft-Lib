package clashsoft.cslib.src;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import clashsoft.cslib.src.parser.IToken;
import clashsoft.cslib.util.CSString;

public class CSSource extends CSString
{
	public static boolean isClass(String token)
	{
		return "class".equals(token) || "interface".equals(token) || "enum".equals(token) || "@interface".equals(token);
	}
	
	public static boolean isPrimitiveType(String token)
	{
		return "void".equals(token) || "boolean".equals(token) || "byte".equals(token) || "short".equals(token) || "char".equals(token) || //
				"int".equals(token) || "long".equals(token) || "float".equals(token) || "double".equals(token);
	}
	
	public static int parseModifier(String s)
	{
		switch (s)//Fixme: incompatible types
		{
		case "public":
			return Modifier.PUBLIC;
		case "protected":
			return Modifier.PROTECTED;
		case "private":
			return Modifier.PRIVATE;
		case "abstract":
			return Modifier.ABSTRACT;
		case "static":
			return Modifier.STATIC;
		case "final":
			return Modifier.FINAL;
		case "transient":
			return Modifier.TRANSIENT;
		case "volatile":
			return Modifier.VOLATILE;
		case "synchronized":
			return Modifier.SYNCHRONIZED;
		case "native":
			return Modifier.NATIVE;
		case "strictfp":
			return Modifier.STRICT;
		}
		return 0;
	}
	
	public static IToken tokenize(String code)
	{
		Lexer tokenizer = new Lexer(code);
		tokenizer.tokenize();
		return tokenizer.first;
	}
	
	public static List<String> codeSplit(String text, char split)
	{
		int len = text.length();
		
		List<String> result = new ArrayList<String>();
		
		int depth1 = 0; // Depth of ( )
		int depth2 = 0; // Depth of [ ]
		int depth3 = 0; // Depth of { }
		int depth4 = 0; // Depth of < >
		
		boolean quote = false;
		int index = 0;
		char current = 0;
		char last = 0;
		
		for (int i = 0; i < len;)
		{
			int i0 = i + 1;
			current = text.charAt(i);
			
			if (last != '\\')
			{
				if (current == '"')
				{
					quote = !quote;
				}
				else if (!quote)
				{
					if (current == '(')
					{
						depth1++;
					}
					else if (current == ')')
					{
						depth1--;
					}
					else if (current == '[')
					{
						depth2++;
					}
					else if (current == ']')
					{
						depth2--;
					}
					else if (current == '{')
					{
						depth3++;
					}
					else if (current == '}')
					{
						depth3--;
					}
					else if (current == '<')
					{
						depth4++;
					}
					else if (current == '>')
					{
						depth4--;
					}
					
				}
				
				if (!quote && depth1 == 0 && depth2 == 0 && depth3 == 0 && depth4 == 0)
				{
					if (current == split)
					{
						result.add(text.substring(index, i));
						index = i0;
					}
					else if (i0 == len)
					{
						result.add(text.substring(index, i0));
					}
				}
			}
			last = current;
			i = i0;
		}
		return result;
	}
	
	/**
	 * Strips comments from Java source code.
	 * 
	 * @param string
	 *            the source code
	 * @return the string without any comments
	 */
	public static String stripComments(String string)
	{
		int len = string.length();
		StringBuilder result = new StringBuilder(len);
		
		boolean quote = false;
		boolean charQuote = false;
		boolean literal = false;
		
		for (int i = 0; i < len; i++)
		{
			char c = string.charAt(i);
			
			// Do not check if the current char is a literal
			if (!literal)
			{
				// String quote switch
				if (c == '"')
				{
					quote = !quote;
				}
				else if (c == '\'')
				{
					charQuote = !charQuote;
				}
				else if (c == '\\')
				{
					literal = true;
				}
				else if (!quote && !charQuote)
				{
					// Comment indicators always start with a '/'
					if (c == '/' && i + 1 < string.length())
					{
						char c1 = string.charAt(i + 1);
						// Line comment
						if (c1 == '/')
						{
							i = string.indexOf("\n", i) - 1;
							continue;
						}
						// Multi-line comment
						else if (c1 == '*')
						{
							i = string.indexOf("*/", i + 1) + 1;
							continue;
						}
					}
				}
			}
			else
			{
				literal = false;
			}
			
			result.append(c);
		}
		
		return result.toString();
	}
	
	public static String replaceLiterals(String string)
	{
		int len = string.length();
		StringBuilder result = new StringBuilder(len);
		
		boolean literal = false;
		for (int i = 0; i < len; i++)
		{
			char c = string.charAt(i);
			if (!literal)
			{
				if (c == '\\')
				{
					literal = true;
					continue;
				}
			}
			else
			{
				literal = false;
				if (c == 'n')
				{
					c = '\n';
				}
				else if (c == 't')
				{
					c = '\t';
				}
				else if (c == 'r')
				{
					c = '\r';
				}
				else if (c == 'b')
				{
					c = '\b';
				}
				else if (c == 'u' && i + 5 < len)
				{
					String u = string.substring(i + 1, i + 5);
					c = (char) Integer.parseInt(u, 16);
					i += 4;
				}
			}
			result.append(c);
		}
		return result.toString();
	}
	
	public static List<Class<?>> getClassesForPackage(Package pack)
	{
		String packageName = pack.getName();
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		
		File directory = null;
		String fullPath;
		String relPath = packageName.replace('.', '/');
		URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
		
		if (resource == null)
		{
			throw new RuntimeException("No resource for " + relPath);
		}
		fullPath = resource.getFile();
		
		try
		{
			directory = new File(resource.toURI());
		}
		catch (URISyntaxException e)
		{
			throw new RuntimeException(packageName + " (" + resource + ") does not appear to be a valid URL / URI.  Strange, since we got it from the system...", e);
		}
		catch (IllegalArgumentException e)
		{
			directory = null;
		}
		
		if (directory != null && directory.exists())
		{
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (String file : files)
			{
				// we are only interested in .class files
				if (file.endsWith(".class"))
				{
					// removes the .class extension
					String className = packageName + '.' + file.substring(0, file.length() - 6);
					
					try
					{
						classes.add(Class.forName(className));
					}
					catch (ClassNotFoundException e)
					{
						throw new RuntimeException("ClassNotFoundException loading " + className);
					}
				}
			}
		}
		else
		{
			try
			{
				String jarPath = fullPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
				JarFile jarFile = new JarFile(jarPath);
				Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements())
				{
					JarEntry entry = entries.nextElement();
					String entryName = entry.getName();
					if (entryName.startsWith(relPath) && entryName.length() > relPath.length() + "/".length())
					{
						String className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
						
						try
						{
							classes.add(Class.forName(className));
						}
						catch (ClassNotFoundException e)
						{
							jarFile.close();
							throw new RuntimeException("ClassNotFoundException loading " + className);
						}
					}
				}
				jarFile.close();
			}
			catch (IOException e)
			{
				throw new RuntimeException(packageName + " (" + directory + ") does not appear to be a valid package", e);
			}
		}
		return classes;
	}
}
