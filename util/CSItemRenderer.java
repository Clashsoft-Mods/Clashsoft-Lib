package clashsoft.clashsoftapi.util;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

public class CSItemRenderer extends RenderItem implements IItemRenderer
{
	private static RenderItem basicItemRenderer = new RenderItem();
	private static Random random = new Random();
	
	public CSItemRenderer()
	{
		super();
		this.renderManager = RenderManager.instance;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY; // || type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		if (item.getItem() instanceof ICSItemRenderable)
		{
			Icon icon = ((ICSItemRenderable)item.getItem()).getIcon(item);
			if (type == ItemRenderType.INVENTORY)
			{
				if (icon != null)
					this.renderItemAndEffectIntoGUI(CSFontRenderer.getFontRenderer(), Minecraft.getMinecraft().renderEngine, item, 0, 0);
			}
			else if (type == ItemRenderType.ENTITY)
			{
				EntityItem entity = (EntityItem) data[1];
				this.renderDroppedItem(entity, icon, item.getItem().getRenderPasses(item.getItemDamage()), 1, 1F, 1F, 1F);
			}
			else if (type == ItemRenderType.EQUIPPED)
			{
				EntityLiving entity = (EntityLiving) data[1];
				
			}
		}
	}
	
	/**
     * Renders a dropped item
     */
    public void renderDroppedItem(EntityItem par1EntityItem, Icon par2Icon, int par3, float par4, float par5, float par6, float par7)
    {
        Tessellator tessellator = Tessellator.instance;

        if (par2Icon == null)
        {
            par2Icon = this.renderManager.renderEngine.getMissingIcon(par1EntityItem.getEntityItem().getItemSpriteNumber());
        }

        float f4 = par2Icon.getMinU();
        float f5 = par2Icon.getMaxU();
        float f6 = par2Icon.getMinV();
        float f7 = par2Icon.getMaxV();
        float f8 = 1.0F;
        float f9 = 0.5F;
        float f10 = 0.25F;
        float f11;

        if (this.renderManager.options.fancyGraphics)
        {
            GL11.glPushMatrix();

            if (renderInFrame)
            {
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                GL11.glRotatef((((float)par1EntityItem.age + par4) / 20.0F + par1EntityItem.hoverStart) * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            float f12 = 0.0625F;
            f11 = 0.021875F;
            ItemStack itemstack = par1EntityItem.getEntityItem();
            int j = itemstack.stackSize;
            byte b0 = getMiniItemCount(itemstack);

            GL11.glTranslatef(-f9, -f10, -((f12 + f11) * (float)b0 / 2.0F));

            for (int k = 0; k < b0; ++k)
            {
                // Makes items offset when in 3D, like when in 2D, looks much better. Considered a vanilla bug...
                if (k > 0 && shouldSpreadItems())
                {
                    float x = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    float y = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    float z = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    GL11.glTranslatef(x, y, f12 + f11);
                }
                else
                {
                    GL11.glTranslatef(0f, 0f, f12 + f11);
                }

                if (itemstack.getItemSpriteNumber() == 0)
                {
                    this.loadTexture("/terrain.png");
                }
                else
                {
                    this.loadTexture("/gui/items.png");
                }

                GL11.glColor4f(par5, par6, par7, 1.0F);
                ItemRenderer.renderItemIn2D(tessellator, f5, f6, f4, f7, par2Icon.getSheetWidth(), par2Icon.getSheetHeight(), f12);

                if (itemstack != null && itemstack.hasEffect())
                {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    this.renderManager.renderEngine.bindTexture("%blur%/misc/glint.png");
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                    float f13 = 0.76F;
                    GL11.glColor4f(0.5F * f13, 0.25F * f13, 0.8F * f13, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    float f14 = 0.125F;
                    GL11.glScalef(f14, f14, f14);
                    float f15 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                    GL11.glTranslatef(f15, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(f14, f14, f14);
                    f15 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                    GL11.glTranslatef(-f15, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                }
            }

            GL11.glPopMatrix();
        }
        else
        {
            for (int l = 0; l < par3; ++l)
            {
                GL11.glPushMatrix();

                if (l > 0)
                {
                    f11 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f16 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f17 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    GL11.glTranslatef(f11, f16, f17);
                }

                if (!renderInFrame)
                {
                    GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                }

                GL11.glColor4f(par5, par6, par7, 1.0F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                tessellator.addVertexWithUV((double)(0.0F - f9), (double)(0.0F - f10), 0.0D, (double)f4, (double)f7);
                tessellator.addVertexWithUV((double)(f8 - f9), (double)(0.0F - f10), 0.0D, (double)f5, (double)f7);
                tessellator.addVertexWithUV((double)(f8 - f9), (double)(1.0F - f10), 0.0D, (double)f5, (double)f6);
                tessellator.addVertexWithUV((double)(0.0F - f9), (double)(1.0F - f10), 0.0D, (double)f4, (double)f6);
                tessellator.draw();
                GL11.glPopMatrix();
            }
        }
    }
    
    /**
     * Renders the item's icon or block into the UI at the specified position.
     */
    public void renderItemIntoGUI(FontRenderer par1FontRenderer, RenderEngine par2RenderEngine, ItemStack par3ItemStack, int par4, int par5)
    {
        int k = par3ItemStack.itemID;
        int l = par3ItemStack.getItemDamage();
        Icon icon = par3ItemStack.getItem() instanceof ICSItemRenderable ? ((ICSItemRenderable)par3ItemStack.getItem()).getIcon(par3ItemStack) : par3ItemStack.getIconIndex();
        float f;
        float f1;
        float f2;

        Block block = (k < Block.blocksList.length ? Block.blocksList[k] : null);
        if (par3ItemStack.getItemSpriteNumber() == 0 && block != null && RenderBlocks.renderItemIn3d(Block.blocksList[k].getRenderType()))
        {
            par2RenderEngine.bindTexture("/terrain.png");
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(par4 - 2), (float)(par5 + 3), -3.0F + this.zLevel);
            GL11.glScalef(10.0F, 10.0F, 10.0F);
            GL11.glTranslatef(1.0F, 0.5F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, -1.0F);
            GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            int i1 = Item.itemsList[k].getColorFromItemStack(par3ItemStack, 0);
            f2 = (float)(i1 >> 16 & 255) / 255.0F;
            f = (float)(i1 >> 8 & 255) / 255.0F;
            f1 = (float)(i1 & 255) / 255.0F;

            if (this.renderWithColor)
            {
                GL11.glColor4f(f2, f, f1, 1.0F);
            }

            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            this.renderBlocks.useInventoryTint = this.renderWithColor;
            this.renderBlocks.renderBlockAsItem(block, l, 1.0F);
            this.renderBlocks.useInventoryTint = true;
            GL11.glPopMatrix();
        }
        else
        {
            int j1;

            if (Item.itemsList[k].requiresMultipleRenderPasses())
            {
                GL11.glDisable(GL11.GL_LIGHTING);
                par2RenderEngine.bindTexture(par3ItemStack.getItemSpriteNumber() == 0 ? "/terrain.png" : "/gui/items.png");

                for (j1 = 0; j1 < Item.itemsList[k].getRenderPasses(l); ++j1)
                {
                    Icon icon1 = Item.itemsList[k].getIcon(par3ItemStack, j1);
                    int k1 = Item.itemsList[k].getColorFromItemStack(par3ItemStack, j1);
                    f = (float)(k1 >> 16 & 255) / 255.0F;
                    f1 = (float)(k1 >> 8 & 255) / 255.0F;
                    float f3 = (float)(k1 & 255) / 255.0F;

                    if (this.renderWithColor)
                    {
                        GL11.glColor4f(f, f1, f3, 1.0F);
                    }

                    this.renderIcon(par4, par5, icon1, 16, 16);
                }

                GL11.glEnable(GL11.GL_LIGHTING);
            }
            else
            {
                GL11.glDisable(GL11.GL_LIGHTING);

                if (par3ItemStack.getItemSpriteNumber() == 0)
                {
                    par2RenderEngine.bindTexture("/terrain.png");
                }
                else
                {
                    par2RenderEngine.bindTexture("/gui/items.png");
                }

                if (icon == null)
                {
                    icon = par2RenderEngine.getMissingIcon(par3ItemStack.getItemSpriteNumber());
                }

                j1 = Item.itemsList[k].getColorFromItemStack(par3ItemStack, 0);
                float f4 = (float)(j1 >> 16 & 255) / 255.0F;
                f2 = (float)(j1 >> 8 & 255) / 255.0F;
                f = (float)(j1 & 255) / 255.0F;

                if (this.renderWithColor)
                {
                    GL11.glColor4f(f4, f2, f, 1.0F);
                }

                this.renderIcon(par4, par5, icon, 16, 16);
                GL11.glEnable(GL11.GL_LIGHTING);
            }
        }

        GL11.glEnable(GL11.GL_CULL_FACE);
    }
    
    public void renderItemAndEffectIntoGUI(FontRenderer par1FontRenderer, RenderEngine par2RenderEngine, ItemStack par3ItemStack, int par4, int par5)
    {
        if (par3ItemStack != null)
        {
            this.renderItemIntoGUI(par1FontRenderer, par2RenderEngine, par3ItemStack, par4, par5);
            if (par3ItemStack.hasEffect())
            {
                GL11.glDepthFunc(GL11.GL_GREATER);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDepthMask(false);
                par2RenderEngine.bindTexture("%blur%/misc/glint.png");
                this.zLevel -= 50.0F;
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
                //DEFAULT GLINT COLOR
                if (par3ItemStack.getItem() instanceof ICSItemRenderable && ((ICSItemRenderable)par3ItemStack.getItem()).getGlintColor(par3ItemStack) >= 0)
                {
                	int glintColor = ((ICSItemRenderable)par3ItemStack.getItem()).getGlintColor(par3ItemStack);
                	float r = (float)(glintColor >> 16) / 255F;
					float g = (float)(glintColor >> 8 & 255) / 255F;
					float b = (float)(glintColor & 255) / 255F;
					GL11.glColor4f(r, g, b, 1F);
                }
                else
                	GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
                this.renderGlint(par4 * 431278612 + par5 * 32178161, par4 - 2, par5 - 2, 20, 20);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthMask(true);
                this.zLevel += 50.0F;
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
            }
        }
    }
	
	public void renderGlint(int par1, int par2, int par3, int par4, int par5)
    {
        for (int j1 = 0; j1 < 2; ++j1)
        {
            if (j1 == 0)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            if (j1 == 1)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            float f = 0.00390625F;
            float f1 = 0.00390625F;
            float f2 = (float)(Minecraft.getSystemTime() % (long)(3000 + j1 * 1873)) / (3000.0F + (float)(j1 * 1873)) * 256.0F;
            float f3 = 0.0F;
            Tessellator tessellator = Tessellator.instance;
            float f4 = 4.0F;

            if (j1 == 1)
            {
                f4 = -1.0F;
            }

            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + par5), (double)this.zLevel, (double)((f2 + (float)par5 * f4) * f), (double)((f3 + (float)par5) * f1));
            tessellator.addVertexWithUV((double)(par2 + par4), (double)(par3 + par5), (double)this.zLevel, (double)((f2 + (float)par4 + (float)par5 * f4) * f), (double)((f3 + (float)par5) * f1));
            tessellator.addVertexWithUV((double)(par2 + par4), (double)(par3 + 0), (double)this.zLevel, (double)((f2 + (float)par4) * f), (double)((f3 + 0.0F) * f1));
            tessellator.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)this.zLevel, (double)((f2 + 0.0F) * f), (double)((f3 + 0.0F) * f1));
            tessellator.draw();
        }
    }
}
