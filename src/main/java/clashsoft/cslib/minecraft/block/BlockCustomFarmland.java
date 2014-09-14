package clashsoft.cslib.minecraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCustomFarmland extends BlockFarmland
{
	public Block	dirtBlock;
	public int		dirtMetadata;
	
	public BlockCustomFarmland(Block dirtBlock)
	{
		this.dirtBlock = dirtBlock;
	}
	
	public BlockCustomFarmland(Block dirtBlock, int dirtMetadata)
	{
		this.dirtBlock = dirtBlock;
		this.dirtMetadata = dirtMetadata;
	}
	
	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float f)
	{
		if (!world.isRemote && world.rand.nextFloat() < f - 0.5F)
		{
			if (!(entity instanceof EntityPlayer) && !world.getGameRules().getGameRuleBooleanValue("mobGriefing"))
			{
				return;
			}
			
			world.setBlock(x, y, z, this.dirtBlock, this.dirtMetadata, 3);
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		super.onNeighborBlockChange(world, x, y, z, block);
		Material material = world.getBlock(x, y + 1, z).getMaterial();
		
		if (material.isSolid())
		{
			world.setBlock(x, y, z, this.dirtBlock, this.dirtMetadata, 3);
		}
	}
	
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return side == 1 ? super.getIcon(1, metadata) : this.dirtBlock.getIcon(side, this.dirtMetadata);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return this.dirtBlock.getItemDropped(this.dirtMetadata, random, fortune);
	}
	
	@Override
	public int damageDropped(int metadata)
	{
		return this.dirtBlock.damageDropped(this.dirtMetadata);
	}
	
	@Override
	public Item getItem(World world, int x, int y, int z)
	{
		return Item.getItemFromBlock(this.dirtBlock);
	}
}
