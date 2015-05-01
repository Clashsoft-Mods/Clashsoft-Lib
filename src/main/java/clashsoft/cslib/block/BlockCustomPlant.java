package clashsoft.cslib.block;

import static net.minecraftforge.common.EnumPlantType.Plains;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCustomPlant extends CustomBlock implements IPlantable
{
	public static final PropertyInteger	age	= BlockCrops.AGE;
	
	public BlockCustomPlant(String[] names)
	{
		super(Material.plants, names, null);
		this.setBlockBounds(0.3F, 0F, 0.3F, 0.7F, 0.6F, 0.7F);
		this.setStepSound(Block.soundTypeGrass);
		this.lightOpacity = 0;
		this.opaque = false;
	}
	
	// RENDER SECTION
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	// WORLD SECTION
	
	@Override
	public boolean canReplace(World world, BlockPos pos, EnumFacing side, ItemStack stack)
	{
		return this.canPlaceBlockOnSide(world, pos, side) && this.canBlockStay(world, pos, this.getStateFromMeta(stack.getItemDamage()));
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);
		this.checkFlowerChange(world, pos, state);
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		super.onNeighborBlockChange(world, pos, state, neighborBlock);
		this.checkFlowerChange(world, pos, state);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		this.checkFlowerChange(world, pos, state);
	}
	
	protected void checkFlowerChange(World world, BlockPos pos, IBlockState state)
	{
		if (!this.canBlockStay(world, pos, state))
		{
			world.destroyBlock(pos, true);
		}
	}
	
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{
		boolean validLight = world.getLight(pos) >= 8 || world.canSeeSky(pos);
		return validLight && this.isValidSoil(world, pos, state);
	}
	
	public boolean isValidSoil(World world, BlockPos pos, IBlockState state)
	{
		return this.isValidSoil(state, world.getBlockState(pos.offsetDown()));
	}
	
	public boolean isValidSoil(IBlockState plant, IBlockState soil)
	{
		Block block = soil.getBlock();
		return block == Blocks.dirt || block == Blocks.grass;
	}
	
	// COLLISION BOX
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
	{
		return null;
	}
	
	// OTHER PROPERTIES
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return Plains;
	}
	
	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockState(pos);
	}
	
	// SUB BLOCKS
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List itemList)
	{
		for (int i = 0; i < this.names.length; i++)
		{
			itemList.add(new ItemStack(this, 1, i));
		}
	}
}
