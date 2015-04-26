package clashsoft.cslib.minecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public final class TeleporterNoPortal extends Teleporter
{
	public TeleporterNoPortal(WorldServer world)
	{
		super(world);
	}
	
	@Override
	public void func_180266_a(Entity p_180266_1_, float p_180266_2_)
	{
	}
	
	@Override
	public boolean func_180620_b(Entity p_180620_1_, float p_180620_2_)
	{
		return true;
	}
}
