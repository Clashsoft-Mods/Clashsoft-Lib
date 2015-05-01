package clashsoft.cslib.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockCustomGrass extends CustomBlock
{
	public IBlockState[]	dirtBlocks;
	
	public BlockCustomGrass(String name)
	{
		this(new String[] { name });
	}
	
	public BlockCustomGrass(String[] names)
	{
		super(Material.grass, names, null);
		this.setStepSound(Block.soundTypeGrass);
		this.setTickRandomly(true);
		this.setHardness(0.6F);
		
		this.names = names;
		
		this.dirtBlocks = new IBlockState[names.length];
	}
	
	public BlockCustomGrass setDirtBlocks(IBlockState[] dirtBlocks)
	{
		this.dirtBlocks = dirtBlocks;
		return this;
	}
	
	public BlockCustomGrass setDirtBlock(int metadata, IBlockState dirtBlock)
	{
		this.dirtBlocks[metadata] = dirtBlock;
		return this;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
		IBlockState dirt = this.dirtBlocks[(int) state.getValue(CustomBlock.typeProperty)];
		return dirt.getBlock().getItemDropped(dirt, random, fortune);
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		IBlockState dirt = this.dirtBlocks[(int) state.getValue(CustomBlock.typeProperty)];
		return dirt.getBlock().damageDropped(dirt);
	}
	
	@Override
	public int getDamageValue(World world, BlockPos pos)
	{
		return this.getMetaFromState(world.getBlockState(pos));
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (world.isRemote)
			return;
		
		IBlockState dirt = this.dirtBlocks[(int) state.getValue(CustomBlock.typeProperty)];
		BlockPos pos1 = pos.offsetUp();
		int lightValue = world.getLight(pos1);
		int lightOpacity = world.getBlockLightOpacity(pos1);
		
		if (lightValue < 4 && lightOpacity > 2 && dirt != null)
		{
			world.setBlockState(pos, dirt, 3);
			return;
		}
		
		if (lightValue < 9)
			return;
		
		for (int l = 0; l < 4; ++l)
		{
			pos1 = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
			
			IBlockState block = world.getBlockState(pos1);
			if (block.equals(dirt))
			{
				lightValue = world.getLight(pos1.offsetUp());
				lightOpacity = world.getBlockLightOpacity(pos1.offsetUp());
				
				if (lightValue >= 4 && lightOpacity <= 2)
				{
					world.setBlockState(pos1, state, 3);
				}
			}
		}
	}
}
