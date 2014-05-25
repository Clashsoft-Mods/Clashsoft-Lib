package clashsoft.cslib.minecraft.addon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import clashsoft.cslib.minecraft.init.CSLib;

/**
 * The class Addon.
 * <p>
 * An annotation that marks mod parts / mod extensions.
 * 
 * @author Clashsoft
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Addon
{
	public String modName() default CSLib.NAME;
	
	public String addonName();
	
	public boolean enable() default true;
}
