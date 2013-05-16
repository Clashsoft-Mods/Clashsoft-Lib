package clashsoft.clashsoftapi;

import net.minecraft.stats.Achievement;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CSLang extends LanguageRegistry
{
	public static void addLocalization(String key, String lang, String value)
	{
		LanguageRegistry.instance().addStringLocalization(key, lang, value);
	}
	
	public static void addLocalizationUS(String key, String value)
	{
		addLocalization(key, "en_US", value);
	}
	
	public static void addLocalizationDE(String key, String value)
	{
		addLocalization(key, "de_DE", value);
	}
	
	public static void addAchievement(Achievement achievement, String lang, String name, String description)
	{
		String achName = achievement.getName();
        addLocalization(achName, lang, name);
        addLocalization(achName + ".desc", lang, description);
	}
	
	public static void addAchievementUS(Achievement achievement, String name, String description)
	{
		addAchievement(achievement, "en_US", name, description);
	}
	
	public static void addAchievementDE(Achievement achievement, String name, String description)
	{
		addAchievement(achievement, "de_DE", name, description);
	}
}
