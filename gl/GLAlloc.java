package clashsoft.cslib.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.lwjgl.opengl.GL11;

public class GLAlloc
{
    private static final Map displayLists = new HashMap();
    private static final List textures = new ArrayList();

    /**
     * Generates the specified number of display lists and returns the first index.
     */
    public static synchronized int generateDisplayLists(int n)
    {
        int j = GL11.glGenLists(n);
        displayLists.put(Integer.valueOf(j), Integer.valueOf(n));
        return j;
    }

    public static synchronized void deleteDisplayLists(int n)
    {
        GL11.glDeleteLists(n, ((Integer)displayLists.remove(Integer.valueOf(n))).intValue());
    }

    public static synchronized void deleteTextures()
    {
        for (int i = 0; i < textures.size(); ++i)
        {
            GL11.glDeleteTextures(((Integer)textures.get(i)).intValue());
        }

        textures.clear();
    }

    /**
     * Deletes all textures and display lists. Called on shutdown to free up resources.
     */
    public static synchronized void deleteTexturesAndDisplayLists()
    {
        Iterator iterator = displayLists.entrySet().iterator();

        while (iterator.hasNext())
        {
            Entry entry = (Entry)iterator.next();
            GL11.glDeleteLists(((Integer)entry.getKey()).intValue(), ((Integer)entry.getValue()).intValue());
        }

        displayLists.clear();
        deleteTextures();
    }

    /**
     * Creates and returns a direct byte buffer with the specified capacity. Applies native ordering to speed up access.
     */
    public static synchronized ByteBuffer createDirectByteBuffer(int n)
    {
        return ByteBuffer.allocateDirect(n).order(ByteOrder.nativeOrder());
    }

    /**
     * Creates and returns a direct int buffer with the specified capacity. Applies native ordering to speed up access.
     */
    public static IntBuffer createDirectIntBuffer(int n)
    {
        return createDirectByteBuffer(n << 2).asIntBuffer();
    }

    /**
     * Creates and returns a direct float buffer with the specified capacity. Applies native ordering to speed up
     * access.
     */
    public static FloatBuffer createDirectFloatBuffer(int n)
    {
        return createDirectByteBuffer(n << 2).asFloatBuffer();
    }
}
