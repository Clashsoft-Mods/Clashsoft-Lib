package clashsoft.cslib.gl;

import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GLContext;

public class GLHelper
{
	/**
	 * An OpenGL constant corresponding to GL_TEXTURE0, used when setting data pertaining to auxiliary OpenGL texture units.
	 */
	public static int		defaultTexUnit;
	
	/**
	 * An OpenGL constant corresponding to GL_TEXTURE1, used when setting data pertaining to auxiliary OpenGL texture units.
	 */
	public static int		lightmapTexUnit;
	
	/**
	 * True if the renderer supports multitextures and the OpenGL version != 1.3
	 */
	private static boolean	useMultitextureARB;
	
	/** Stores the last values sent into setLightmapTextureCoords */
	public static float		lastBrightnessX	= 0.0f;
	/** Stores the last values sent into setLightmapTextureCoords */
	public static float		lastBrightnessY	= 0.0f;
	
	/**
	 * Initializes the texture constants to be used when rendering lightmap values
	 */
	public static void initializeTextures()
	{
		useMultitextureARB = GLContext.getCapabilities().GL_ARB_multitexture && !GLContext.getCapabilities().OpenGL13;
		
		if (useMultitextureARB)
		{
			defaultTexUnit = 33984;
			lightmapTexUnit = 33985;
		}
		else
		{
			defaultTexUnit = 33984;
			lightmapTexUnit = 33985;
		}
	}
	
	/**
	 * Sets the current lightmap texture to the specified OpenGL constant
	 */
	public static void setActiveTexture(int unit)
	{
		if (useMultitextureARB)
		{
			ARBMultitexture.glActiveTextureARB(unit);
		}
		else
		{
			GL13.glActiveTexture(unit);
		}
	}
	
	/**
	 * Sets the current lightmap texture to the specified OpenGL constant
	 */
	public static void setClientActiveTexture(int unit)
	{
		if (useMultitextureARB)
		{
			ARBMultitexture.glClientActiveTextureARB(unit);
		}
		else
		{
			GL13.glClientActiveTexture(unit);
		}
	}
	
	/**
	 * Sets the current coordinates of the given lightmap texture
	 */
	public static void setLightmapTextureCoords(int unit, float x, float y)
	{
		if (useMultitextureARB)
		{
			ARBMultitexture.glMultiTexCoord2fARB(unit, x, y);
		}
		else
		{
			GL13.glMultiTexCoord2f(unit, x, y);
		}
		
		if (unit == lightmapTexUnit)
		{
			lastBrightnessX = x;
			lastBrightnessY = y;
		}
	}
}
