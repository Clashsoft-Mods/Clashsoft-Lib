package clashsoft.clashsoftapi;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class CSLang
{
	public static void addTranslation(String key, String lang, String value)
	{
		LanguageRegistry.instance().addStringLocalization(key, lang, value);
	}
	
	public static void addTranslation(String key, String value)
	{
		addTranslation(key, "en_US", value);
	}
	
	public static void addGermanTranslation(String key, String value)
	{
		addTranslation(key, "de_DE", value);
	}
}
