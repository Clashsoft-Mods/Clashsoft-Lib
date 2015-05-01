package clashsoft.cslib.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCustomLeaves extends BlockLeaves implements ICustomBlock
{
	public String[]			names;
	
	public ItemStack[]		appleStacks		= new ItemStack[4];
	public ItemStack[]		saplingStacks	= new ItemStack[4];
	public boolean[]		isColored		= new boolean[4];
	
	public int[]			adjacentTreeBlocks;
	
	public static boolean	graphicsLevel;
	
	public BlockCustomLeaves(String[] names)
	{
		super();
		this.setTickRandomly(true);
		this.setStepSound(Block.soundTypeGrass);
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		
		this.names = names;
	}
	
	public BlockCustomLeaves setAppleStacks(ItemStack... appleStacks)
	{
		this.appleStacks = appleStacks;
		return this;
	}
	
	public BlockCustomLeaves setSaplingStacks(ItemStack... saplingStacks)
	{
		this.saplingStacks = saplingStacks;
		return this;
	}
	
	@Override
	public EnumType func_176233_b(int metadata)
	{
		return null;
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, CustomBlock.typeProperty, field_176236_b, field_176237_a);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		byte b0 = 0;
		int i = b0 | (int) state.getValue(CustomBlock.typeProperty);
		
		if (!((Boolean) state.getValue(field_176237_a)).booleanValue())
		{
			i |= 4;
		}
		
		if (((Boolean) state.getValue(field_176236_b)).booleanValue())
		{
			i |= 8;
		}
		
		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(CustomBlock.typeProperty, meta).withProperty(field_176237_a, (meta & 4) == 0).withProperty(field_176236_b, (meta & 8) > 0);
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
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass)
	{
		if (this.isColored[(int) world.getBlockState(pos).getValue(CustomBlock.typeProperty)])
			return super.colorMultiplier(world, pos, renderPass);
		return 0xFFFFFF;
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(20) == 0 ? 1 : 0;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
		ItemStack stack = this.saplingStacks[(int) state.getValue(CustomBlock.typeProperty)];
		return stack != null ? stack.getItem() : null;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float dropChance, int fortune)
	{
		if (world.isRemote)
			return;
		
		int j1 = 20;
		if (fortune > 0)
		{
			j1 -= 2 << fortune;
			
			if (j1 < 10)
			{
				j1 = 10;
			}
		}
		if (world.rand.nextInt(j1) == 0)
		{
			ItemStack stack = this.saplingStacks[(int) state.getValue(CustomBlock.typeProperty)];
			if (stack != null)
			{
				spawnAsEntity(world, pos, stack);
			}
		}
		
		j1 = 200;
		if (fortune > 0)
		{
			j1 -= 10 << fortune;
			
			if (j1 < 40)
			{
				j1 = 40;
			}
		}
		if (world.rand.nextInt(j1) == 0)
		{
			ItemStack stack = this.appleStacks[(int) state.getValue(CustomBlock.typeProperty)];
			if (stack != null)
			{
				spawnAsEntity(world, pos, stack);
			}
		}
	}
	
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		IBlockState state = world.getBlockState(pos);
		return Arrays.asList(new ItemStack(this, 1, (int) state.getValue(CustomBlock.typeProperty)));
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return !graphicsLevel;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return graphicsLevel || world.getBlockState(pos).getBlock() != this;
	}
	
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
