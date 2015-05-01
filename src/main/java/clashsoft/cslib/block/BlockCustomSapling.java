package clashsoft.cslib.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockCustomSapling extends BlockSapling implements ICustomBlock
{
	public String[]	names;
	
	public BlockCustomSapling(String[] names)
	{
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
		this.setHardness(0F);
		this.setStepSound(Block.soundTypeGrass);
		
		this.names = names;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(CustomBlock.typeProperty, meta).withProperty(STAGE_PROP, (meta & 8) >> 3);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = (int) state.getValue(CustomBlock.typeProperty);
		i |= (int) state.getValue(STAGE_PROP) << 3;
		return i;
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { CustomBlock.typeProperty, STAGE_PROP });
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return CustomBlock.getUnlocalizedName(this, stack, this.names);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list)
	{
		CustomBlock.addInformation(this, stack, list);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (!world.isRemote)
		{
			super.updateTick(world, pos, state, random);
			
			if (world.getLight(pos.offsetUp()) >= 9 && random.nextInt(7) == 0)
			{
				this.grow(world, random, pos, state);
			}
		}
	}
	
	@Override
	public void grow(World world, Random random, BlockPos pos, IBlockState state)
	{
		if ((int) state.getValue(STAGE_PROP) == 0)
		{
			world.setBlockState(pos, state.withProperty(STAGE_PROP, 1), 4);
		}
		else
		{
			this.func_176476_e(world, pos, state, random);
		}
	}
	
	/*
	 * growTree
	 */
	@Override
	public void func_176476_e(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (world.isRemote)
			return;
		
		if (!TerrainGen.saplingGrowTree(world, random, pos))
			return;
		
		WorldGenerator worldgen = this.getWorldGen(world, pos, state, random);
		
		world.setBlockState(pos, Blocks.air.getDefaultState(), 0);
		if (worldgen != null && !worldgen.generate(world, random, pos))
		{
			world.setBlockState(pos, state, 4);
		}
	}
	
	public abstract WorldGenerator getWorldGen(World world, BlockPos pos, IBlockState state, Random random);
	
	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{
		return (world.getLight(pos) >= 8 || world.canSeeSky(pos)) && this.isValidSoil(world, pos, state);
	}
	
	public boolean isValidSoil(World world, BlockPos pos, IBlockState state)
	{
		return this.isValidSoil(state, world.getBlockState(pos.offsetDown()));
	}
	
	public abstract boolean isValidSoil(IBlockState plant, IBlockState soil);
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list)
	{
		for (int i = 0; i < this.names.length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
