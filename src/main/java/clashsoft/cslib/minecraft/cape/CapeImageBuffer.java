package clashsoft.cslib.minecraft.cape;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import net.minecraft.client.renderer.IImageBuffer;

public class CapeImageBuffer implements IImageBuffer
{
	private int	imageWidth;
	private int	imageHeight;
	
	@Override
	public BufferedImage parseUserSkin(BufferedImage bufferedImage)
	{
		if (bufferedImage == null)
		{
			return null;
		}
		else
		{
			this.imageWidth = bufferedImage.getWidth((ImageObserver) null);
			this.imageHeight = bufferedImage.getHeight((ImageObserver) null);
			if (this.imageWidth < 64)
			{
				this.imageWidth = 64;
			}
			if (this.imageHeight < 32)
			{
				this.imageHeight = 32;
			}
			
			BufferedImage capeImage = new BufferedImage(this.imageWidth, this.imageHeight, 2);
			
			Graphics graphics = capeImage.getGraphics();
			graphics.drawImage(bufferedImage, 0, 0, (ImageObserver) null);
			graphics.dispose();
			
			return capeImage;
		}
	}
	
	@Override
	public void func_152634_a()
	{
	}
}
