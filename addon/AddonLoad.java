package clashsoft.cslib.addon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class AddonLoad
 * <p>
 * Marks the {@code load()} method in an {@link clashsoft.cslib.addon.Addon} class
 * 
 * @author Clashsoft
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AddonLoad
{
	
}
