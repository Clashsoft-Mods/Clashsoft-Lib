package clashsoft.cslib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockCustomPortal extends BlockCustomPortalBase
{
	public BlockCustomPortal(int dimensionID)
	{
		super(dimensionID);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return func_176549_a((EnumFacing.Axis) state.getValue(BlockPortal.field_176550_a));
	}
	
	public static int func_176549_a(EnumFacing.Axis p_176549_0_)
	{
		return p_176549_0_ == EnumFacing.Axis.X ? 1 : p_176549_0_ == EnumFacing.Axis.Z ? 2 : 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World worldIn, BlockPos pos)
	{
		return null;
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { BlockPortal.field_176550_a });
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block block)
	{
		EnumFacing.Axis axis = (EnumFacing.Axis) state.getValue(BlockPortal.field_176550_a);
		PortalSize size;
		
		if (axis == EnumFacing.Axis.X)
		{
			size = new PortalSize(world, pos, state, this.frameBlock, EnumFacing.Axis.X);
			
			if (!size.isValid() || size.portals < size.width * size.height)
			{
				world.setBlockState(pos, Blocks.air.getDefaultState());
			}
		}
		else if (axis == EnumFacing.Axis.Z)
		{
			size = new PortalSize(world, pos, state, this.frameBlock, EnumFacing.Axis.Z);
			
			if (!size.isValid() || size.portals < size.width * size.height)
			{
				world.setBlockState(pos, Blocks.air.getDefaultState());
			}
		}
	}
	
	public boolean generatePortal(World world, BlockPos pos, IBlockState state)
	{
		PortalSize size1 = new PortalSize(world, pos, state, this.frameBlock, EnumFacing.Axis.X);
		PortalSize size2 = new PortalSize(world, pos, state, this.frameBlock, EnumFacing.Axis.Z);
		
		if (size1.isValid() && size1.portals == 0)
		{
			size1.generatePortal();
			return true;
		}
		if (size2.isValid() && size2.portals == 0)
		{
			size2.generatePortal();
			return true;
		}
		
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, BlockPos pos)
	{
		EnumFacing.Axis axis = (EnumFacing.Axis) access.getBlockState(pos).getValue(BlockPortal.field_176550_a);
		float f = 0.125F;
		float f1 = 0.125F;
		
		if (axis == EnumFacing.Axis.X)
		{
			f = 0.5F;
		}
		
		if (axis == EnumFacing.Axis.Z)
		{
			f1 = 0.5F;
		}
		
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
	}
	
	public static class PortalSize
	{
		private final World			world;
		private final IBlockState	state;
		private final IBlockState	frameBlock;
		private final EnumFacing	direction1;
		private final EnumFacing	direction2;
		private int					portals;
		private BlockPos			chunkPos;
		private int					height;
		private int					width;
		
		public PortalSize(World world, BlockPos pos, IBlockState state, IBlockState frameBlock, EnumFacing.Axis axis)
		{
			this.world = world;
			this.state = state;
			this.frameBlock = frameBlock;
			
			if (axis == EnumFacing.Axis.X)
			{
				this.direction1 = EnumFacing.WEST;
				this.direction2 = EnumFacing.EAST;
			}
			else
			{
				this.direction1 = EnumFacing.SOUTH;
				this.direction2 = EnumFacing.NORTH;
			}
			
			for (BlockPos blockpos1 = pos; pos.getY() > blockpos1.getY() - 21 && pos.getY() > 0 && this.isValidBlock(world.getBlockState(pos.offsetDown())); pos = pos.offsetDown())
			{
				;
			}
			
			int j1 = this.calculateWidth(pos, this.direction2) - 1;
			
			if (j1 >= 0)
			{
				this.chunkPos = pos.offset(this.direction2, j1);
				this.width = this.calculateWidth(this.chunkPos, this.direction1);
				
				if (this.width < 2 || this.width > 21)
				{
					this.chunkPos = null;
					this.width = 0;
				}
			}
			
			if (this.chunkPos != null)
			{
				this.height = this.calculateHeight();
			}
		}
		
		protected int calculateWidth(BlockPos pos, EnumFacing direction)
		{
			int width;
			
			for (width = 0; width < 22; ++width)
			{
				BlockPos pos1 = pos.offset(direction, width);
				
				if (!this.isValidBlock(this.world.getBlockState(pos1)))
				{
					break;
				}
				
				if (!this.isFrameBlock(this.world.getBlockState(pos1.offsetDown(1))))
				{
					break;
				}
			}
			
			return this.isFrameBlock(this.world.getBlockState(pos.offset(direction, width))) ? width : 0;
		}
		
		protected int calculateHeight()
		{
			outer:
			for (this.height = 0; this.height < 21; ++this.height)
			{
				BlockPos pos = this.chunkPos.offsetUp(this.height);
				
				for (int i = 0; i < this.width; ++i)
				{
					BlockPos pos1 = pos.offset(this.direction1, i);
					IBlockState state = this.world.getBlockState(pos1);
					
					if (state.equals(this.state))
					{
						++this.portals;
					}
					else if (!this.isValidBlock(state))
					{
						break outer;
					}
					
					if (i == 0)
					{
						if (!this.isFrameBlock(this.world.getBlockState(pos.offset(this.direction2))))
						{
							break outer;
						}
					}
					else if (i == this.width - 1)
					{
						if (!this.isFrameBlock(this.world.getBlockState(pos.offset(this.direction1))))
						{
							break outer;
						}
					}
				}
			}
			
			for (int i = 0; i < this.width; ++i)
			{
				BlockPos pos = this.chunkPos.offset(this.direction2, i);
				if (!this.isFrameBlock(this.world.getBlockState(pos)))
				{
					this.height = 0;
					break;
				}
			}
			
			if (this.height <= 21 && this.height >= 3)
				return this.height;
			else
			{
				this.chunkPos = null;
				this.width = 0;
				this.height = 0;
				return 0;
			}
		}
		
		protected boolean isValidBlock(IBlockState state)
		{
			Block block = state.getBlock();
			return block.getMaterial() == Material.air || block == Blocks.fire || state.equals(this.state);
		}
		
		protected boolean isFrameBlock(IBlockState state)
		{
			return state.equals(this.frameBlock);
		}
		
		public boolean isValid()
		{
			return this.chunkPos != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
		}
		
		public void generatePortal()
		{
			for (int i = 0; i < this.width; ++i)
			{
				BlockPos pos1 = this.chunkPos.offset(this.direction1, i);
				for (int l = 0; l < this.height; ++l)
				{
					BlockPos pos2 = pos1.offsetUp(l);
					this.world.setBlockState(pos1, this.state, 2);
				}
			}
		}
	}
}
