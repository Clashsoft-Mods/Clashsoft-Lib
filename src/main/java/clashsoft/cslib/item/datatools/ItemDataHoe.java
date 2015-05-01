package clashsoft.cslib.item.datatools;

import java.util.Collections;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemDataHoe extends ItemDataTool
{
	public ItemDataHoe(ToolMaterial toolMaterial)
	{
		super(0F, toolMaterial, Collections.EMPTY_SET, "Hoe");
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state)
	{
		return 1F;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return this.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
	}
	
	public static boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side)
	{
		if (!player.func_175151_a(pos, side, stack))
			return false;
		
		UseHoeEvent event = new UseHoeEvent(player, stack, world, pos);
		if (MinecraftForge.EVENT_BUS.post(event))
			return false;
		
		if (event.getResult() == Event.Result.ALLOW)
		{
			stack.damageItem(1, player);
			return true;
		}
		
		if (side == EnumFacing.DOWN)
			return false;
		
		Block block = world.getBlockState(pos).getBlock();
		if (block != Blocks.grass && block != Blocks.dirt)
			return false;
		
		BlockPos up = pos.offsetUp();
		if (world.getBlockState(up).getBlock().isAir(world, up))
		{
			Block block1 = Blocks.farmland;
			world.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, block1.stepSound.getStepSound(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.frequency * 0.8F);
			
			if (world.isRemote)
				return true;
			
			world.setBlockState(pos, block1.getDefaultState());
			stack.damageItem(1, player);
			return true;
		}
		
		return false;
	}
	
	@Override
	public Multimap getItemAttributeModifiers()
	{
		return HashMultimap.create();
	}
}
