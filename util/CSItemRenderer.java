package clashsoft.clashsoftapi.util;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

public class CSItemRenderer implements IItemRenderer
{
	private static RenderItem basicItemRenderer = new RenderItem();
	
	public CSItemRenderer()
	{
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type == ItemRenderType.INVENTORY;
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
			basicItemRenderer.renderIcon(0, 0, icon, 16, 16);
		}
	}

}
