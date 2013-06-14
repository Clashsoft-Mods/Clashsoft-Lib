package clashsoft.clashsoftapi.util;

import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	
	@SideOnly(Side.CLIENT)
	public static void addAchievement(Achievement achievement, String lang, String name, String description)
	{
		String achName = achievement.getName();
        addLocalization(achName, lang, name);
        addLocalization(achName + ".desc", lang, description);
	}
	
	@SideOnly(Side.CLIENT)
	public static void addAchievementUS(Achievement achievement, String name, String description)
	{
		addAchievement(achievement, "en_US", name, description);
	}
	
	@SideOnly(Side.CLIENT)
	public static void addAchievementDE(Achievement achievement, String name, String description)
	{
		addAchievement(achievement, "de_DE", name, description);
	}
	
	public static void addPotion(Potion potion, String lang, String name, String effectname, String description)
	{
		String potionname = potion.getName();
        addLocalization(potionname, lang, effectname);
        addLocalization(potionname + ".postfix", lang, name);
        addLocalization(potionname + ".description", lang, name);
	}
	
	public static void addPotionUS(Potion potion, String name, String effectname, String description)
	{
		addPotion(potion, "en_US", name, effectname, description);
	}
	
	public static void addPotionDE(Potion potion, String name, String effectname, String description)
	{
		addPotion(potion, "de_DE", name, effectname, description);
	}
}
