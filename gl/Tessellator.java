package clashsoft.cslib.gl;

import java.nio.*;
import java.util.Arrays;

import org.lwjgl.opengl.ARBBufferObject;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Tessellator
{
	private static int			nativeBufferSize		= 0x200000;
	private static int			trivertsInBuffer		= (nativeBufferSize / 48) * 6;
	public static boolean		renderingWorldRenderer	= false;
	public boolean				defaultTexture			= false;
	private int					rawBufferSize			= 0;
	public int					textureID				= 0;
	/**
	 * Boolean used to check whether quads should be drawn as two triangles. Initialized to false and never changed.
	 */
	private static boolean		convertQuadsToTriangles;
	
	/**
	 * Boolean used to check if we should use vertex buffers. Initialized to false and never changed.
	 */
	private static boolean		tryVBO;
	
	/** The byte buffer used for GL allocation. */
	private ByteBuffer			byteBuffer				= GLAlloc.createDirectByteBuffer(nativeBufferSize * 4);
	
	/** The same memory as byteBuffer, but referenced as an integer buffer. */
	private IntBuffer			intBuffer				= this.byteBuffer.asIntBuffer();
	
	/** The same memory as byteBuffer, but referenced as an float buffer. */
	private FloatBuffer			floatBuffer				= this.byteBuffer.asFloatBuffer();
	
	/** Short buffer */
	private ShortBuffer			shortBuffer				= this.byteBuffer.asShortBuffer();
	
	/** Raw integer array. */
	private int[]				rawBuffer;
	
	/**
	 * The number of vertices to be drawn in the next draw call. Reset to 0 between draw calls.
	 */
	private int					vertexCount;
	
	/** The first coordinate to be used for the texture. */
	private double				textureU;
	
	/** The second coordinate to be used for the texture. */
	private double				textureV;
	private int					brightness;
	
	/** The color (RGBA) value to be used for the following draw call. */
	private int					color;
	
	/**
	 * Whether the current draw object for this tessellator has color values.
	 */
	private boolean				hasColor;
	
	/**
	 * Whether the current draw object for this tessellator has texture coordinates.
	 */
	private boolean				hasTexture;
	private boolean				hasBrightness;
	
	/**
	 * Whether the current draw object for this tessellator has normal values.
	 */
	private boolean				hasNormals;
	
	/** The index into the raw buffer to be used for the next data. */
	private int					rawBufferIndex;
	
	/**
	 * The number of vertices manually added to the given draw call. This differs from vertexCount because it adds extra vertices when converting quads to triangles.
	 */
	private int					addedVertices;
	
	/** Disables all color information for the following draw call. */
	private boolean				isColorDisabled;
	
	/** The draw mode currently being used by the tessellator. */
	public int					drawMode;
	
	/**
	 * An offset to be applied along the x-axis for all vertices in this draw call.
	 */
	public double				xOffset;
	
	/**
	 * An offset to be applied along the y-axis for all vertices in this draw call.
	 */
	public double				yOffset;
	
	/**
	 * An offset to be applied along the z-axis for all vertices in this draw call.
	 */
	public double				zOffset;
	
	/** The normal to be applied to the face being drawn. */
	private int					normal;
	
	/** The static instance of the Tessellator. */
	public static Tessellator		instance				= new Tessellator(2097152);
	
	/** Whether this tessellator is currently in draw mode. */
	public boolean				isDrawing;
	
	/** Whether we are currently using VBO or not. */
	private boolean				useVBO					= false;
	
	/** An IntBuffer used to store the indices of vertex buffer objects. */
	private static IntBuffer	vertexBuffers;
	
	/**
	 * The index of the last VBO used. This is used in round-robin fashion, sequentially, through the vboCount vertex buffers.
	 */
	private int					vboIndex;
	
	/** Number of vertex buffer objects allocated for use. */
	private static int			vboCount				= 10;
	
	private Tessellator(int par1)
	{
		this();
	}
	
	public Tessellator()
	{
		this.defaultTexture = true;
		this.useVBO = tryVBO && GLContext.getCapabilities().GL_ARB_vertex_buffer_object;
		
		if (this.useVBO)
		{
			vertexBuffers = GLAlloc.createDirectIntBuffer(vboCount);
			ARBBufferObject.glGenBuffersARB(vertexBuffers);
		}
	}
	
	/**
	 * Draws the data set up in this tessellator and resets the state to prepare for new drawing.
	 */
	public int draw()
	{
		if (!this.isDrawing)
		{
			throw new IllegalStateException("Not tesselating!");
		}
		else
		{
			this.isDrawing = false;
			
			int offs = 0;
			while (offs < this.vertexCount)
			{
				int vtc = 0;
				if (this.drawMode == 7 && convertQuadsToTriangles)
				{
					vtc = Math.min(this.vertexCount - offs, trivertsInBuffer);
				}
				else
				{
					vtc = Math.min(this.vertexCount - offs, nativeBufferSize >> 5);
				}
				this.intBuffer.clear();
				this.intBuffer.put(this.rawBuffer, offs * 8, vtc * 8);
				this.byteBuffer.position(0);
				this.byteBuffer.limit(vtc * 32);
				offs += vtc;
				
				if (this.useVBO)
				{
					this.vboIndex = (this.vboIndex + 1) % Tessellator.vboCount;
					ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, Tessellator.vertexBuffers.get(this.vboIndex));
					ARBBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.byteBuffer, ARBBufferObject.GL_STREAM_DRAW_ARB);
				}
				
				if (this.hasTexture)
				{
					if (this.useVBO)
					{
						GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 32, 12L);
					}
					else
					{
						this.floatBuffer.position(3);
						GL11.glTexCoordPointer(2, 32, this.floatBuffer);
					}
					
					GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				}
				
				if (this.hasBrightness)
				{
					GLHelper.setClientActiveTexture(GLHelper.lightmapTexUnit);
					
					if (this.useVBO)
					{
						GL11.glTexCoordPointer(2, GL11.GL_SHORT, 32, 28L);
					}
					else
					{
						this.shortBuffer.position(14);
						GL11.glTexCoordPointer(2, 32, this.shortBuffer);
					}
					
					GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
					GLHelper.setClientActiveTexture(GLHelper.defaultTexUnit);
				}
				
				if (this.hasColor)
				{
					if (this.useVBO)
					{
						GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 32, 20L);
					}
					else
					{
						this.byteBuffer.position(20);
						GL11.glColorPointer(4, true, 32, this.byteBuffer);
					}
					
					GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
				}
				
				if (this.hasNormals)
				{
					if (this.useVBO)
					{
						GL11.glNormalPointer(GL11.GL_UNSIGNED_BYTE, 32, 24L);
					}
					else
					{
						this.byteBuffer.position(24);
						GL11.glNormalPointer(32, this.byteBuffer);
					}
					
					GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
				}
				
				if (this.useVBO)
				{
					GL11.glVertexPointer(3, GL11.GL_FLOAT, 32, 0L);
				}
				else
				{
					this.floatBuffer.position(0);
					GL11.glVertexPointer(3, 32, this.floatBuffer);
				}
				
				GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				
				if (this.drawMode == 7 && convertQuadsToTriangles)
				{
					GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vtc);
				}
				else
				{
					GL11.glDrawArrays(this.drawMode, 0, vtc);
				}
				
				GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
				
				if (this.hasTexture)
				{
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				}
				
				if (this.hasBrightness)
				{
					GLHelper.setClientActiveTexture(GLHelper.lightmapTexUnit);
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
					GLHelper.setClientActiveTexture(GLHelper.defaultTexUnit);
				}
				
				if (this.hasColor)
				{
					GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
				}
				
				if (this.hasNormals)
				{
					GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
				}
			}
			
			if (this.rawBufferSize > 0x20000 && this.rawBufferIndex < (this.rawBufferSize << 3))
			{
				this.rawBufferSize = 0;
				this.rawBuffer = null;
			}
			
			int i = this.rawBufferIndex * 4;
			this.reset();
			return i;
		}
	}
	
	/**
	 * Clears the tessellator state in preparation for new drawing.
	 */
	private void reset()
	{
		this.vertexCount = 0;
		this.byteBuffer.clear();
		this.rawBufferIndex = 0;
		this.addedVertices = 0;
	}
	
	/**
	 * Sets draw mode in the tessellator to draw quads.
	 */
	public void startDrawingQuads()
	{
		this.startDrawing(GL11.GL_QUADS);
	}
	
	/**
	 * Resets tessellator state and prepares for drawing (with the specified draw mode).
	 */
	public void startDrawing(int mode)
	{
		if (this.isDrawing)
		{
			throw new IllegalStateException("Already tesselating!");
		}
		else
		{
			this.isDrawing = true;
			this.reset();
			this.drawMode = mode;
			this.hasNormals = false;
			this.hasColor = false;
			this.hasTexture = false;
			this.hasBrightness = false;
			this.isColorDisabled = false;
		}
	}
	
	/**
	 * Sets the texture coordinates.
	 */
	public void setTextureUV(double u, double v)
	{
		this.hasTexture = true;
		this.textureU = u;
		this.textureV = v;
	}
	
	public void setBrightness(int brightness)
	{
		this.hasBrightness = true;
		this.brightness = brightness;
	}
	
	/**
	 * Sets the RGB values as specified, converting from floats between 0 and 1 to integers from 0-255.
	 */
	public void setColorOpaque_F(float r, float g, float b)
	{
		this.setColorOpaque((int) (r * 255.0F), (int) (g * 255.0F), (int) (b * 255.0F));
	}
	
	/**
	 * Sets the RGBA values for the color, converting from floats between 0 and 1 to integers from 0-255.
	 */
	public void setColorRGBA_F(float r, float g, float b, float a)
	{
		this.setColorRGBA((int) (r * 255.0F), (int) (g * 255.0F), (int) (b * 255.0F), (int) (a * 255.0F));
	}
	
	/**
	 * Sets the RGB values as specified, and sets alpha to opaque.
	 */
	public void setColorOpaque(int r, int g, int b)
	{
		this.setColorRGBA(r, g, b, 255);
	}
	
	/**
	 * Sets the RGBA values for the color. Also clamps them to 0-255.
	 */
	public void setColorRGBA(int r, int g, int b, int a)
	{
		if (!this.isColorDisabled)
		{
			if (r > 255)
			{
				r = 255;
			}
			
			if (g > 255)
			{
				g = 255;
			}
			
			if (b > 255)
			{
				b = 255;
			}
			
			if (a > 255)
			{
				a = 255;
			}
			
			if (r < 0)
			{
				r = 0;
			}
			
			if (g < 0)
			{
				g = 0;
			}
			
			if (b < 0)
			{
				b = 0;
			}
			
			if (a < 0)
			{
				a = 0;
			}
			
			this.hasColor = true;
			
			if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
			{
				this.color = a << 24 | b << 16 | g << 8 | r;
			}
			else
			{
				this.color = r << 24 | g << 16 | b << 8 | a;
			}
		}
	}
	
	/**
	 * Adds a vertex specifying both x,y,z and the texture u,v for it.
	 */
	public void addVertexWithUV(double x, double y, double z, double u, double v)
	{
		this.setTextureUV(u, v);
		this.addVertex(x, y, z);
	}
	
	/**
	 * Adds a vertex with the specified x,y,z to the current draw call. It will trigger a draw() if the buffer gets full.
	 */
	public void addVertex(double x, double y, double z)
	{
		if (this.rawBufferIndex >= this.rawBufferSize - 32)
		{
			if (this.rawBufferSize == 0)
			{
				this.rawBufferSize = 0x10000;
				this.rawBuffer = new int[this.rawBufferSize];
			}
			else
			{
				this.rawBufferSize *= 2;
				this.rawBuffer = Arrays.copyOf(this.rawBuffer, this.rawBufferSize);
			}
		}
		++this.addedVertices;
		
		if (this.drawMode == 7 && convertQuadsToTriangles && this.addedVertices % 4 == 0)
		{
			for (int i = 0; i < 2; ++i)
			{
				int j = 8 * (3 - i);
				
				if (this.hasTexture)
				{
					this.rawBuffer[this.rawBufferIndex + 3] = this.rawBuffer[this.rawBufferIndex - j + 3];
					this.rawBuffer[this.rawBufferIndex + 4] = this.rawBuffer[this.rawBufferIndex - j + 4];
				}
				
				if (this.hasBrightness)
				{
					this.rawBuffer[this.rawBufferIndex + 7] = this.rawBuffer[this.rawBufferIndex - j + 7];
				}
				
				if (this.hasColor)
				{
					this.rawBuffer[this.rawBufferIndex + 5] = this.rawBuffer[this.rawBufferIndex - j + 5];
				}
				
				this.rawBuffer[this.rawBufferIndex + 0] = this.rawBuffer[this.rawBufferIndex - j + 0];
				this.rawBuffer[this.rawBufferIndex + 1] = this.rawBuffer[this.rawBufferIndex - j + 1];
				this.rawBuffer[this.rawBufferIndex + 2] = this.rawBuffer[this.rawBufferIndex - j + 2];
				++this.vertexCount;
				this.rawBufferIndex += 8;
			}
		}
		
		if (this.hasTexture)
		{
			this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float) this.textureU);
			this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float) this.textureV);
		}
		
		if (this.hasBrightness)
		{
			this.rawBuffer[this.rawBufferIndex + 7] = this.brightness;
		}
		
		if (this.hasColor)
		{
			this.rawBuffer[this.rawBufferIndex + 5] = this.color;
		}
		
		if (this.hasNormals)
		{
			this.rawBuffer[this.rawBufferIndex + 6] = this.normal;
		}
		
		this.rawBuffer[this.rawBufferIndex + 0] = Float.floatToRawIntBits((float) (x + this.xOffset));
		this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float) (y + this.yOffset));
		this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits((float) (z + this.zOffset));
		this.rawBufferIndex += 8;
		++this.vertexCount;
	}
	
	/**
	 * Sets the color to the given opaque value (stored as byte values packed in an integer).
	 */
	public void setColorOpaque_I(int rgb)
	{
		int r = rgb >> 16 & 255;
		int g = rgb >> 8 & 255;
		int b = rgb & 255;
		this.setColorOpaque(r, g, b);
	}
	
	/**
	 * Sets the color to the given color (packed as bytes in integer) and alpha values.
	 */
	public void setColorRGBA_I(int rgb, int a)
	{
		int r = rgb >> 16 & 255;
		int g = rgb >> 8 & 255;
		int b = rgb & 255;
		this.setColorRGBA(r, g, b, a);
	}
	
	/**
	 * Disables colors for the current draw call.
	 */
	public void disableColor()
	{
		this.isColorDisabled = true;
	}
	
	/**
	 * Sets the normal for the current draw call.
	 */
	public void setNormal(float x, float y, float z)
	{
		this.hasNormals = true;
		byte b0 = (byte) ((int) (x * 127.0F));
		byte b1 = (byte) ((int) (y * 127.0F));
		byte b2 = (byte) ((int) (z * 127.0F));
		this.normal = b0 & 255 | (b1 & 255) << 8 | (b2 & 255) << 16;
	}
	
	/**
	 * Sets the translation for all vertices in the current draw call.
	 */
	public void setTranslation(double x, double y, double z)
	{
		this.xOffset = x;
		this.yOffset = y;
		this.zOffset = z;
	}
	
	/**
	 * Offsets the translation for all vertices in the current draw call.
	 */
	public void addTranslation(float x, float y, float z)
	{
		this.xOffset += x;
		this.yOffset += y;
		this.zOffset += z;
	}
}
