package clashsoft.cslib.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CustomTeleporter extends Teleporter
{
	public IBlockState			frameBlock;
	public IBlockState			portalBlock;
	
	protected final WorldServer	world;
	protected final Random		random;
	protected final LongHashMap	destinationCoordinateCache	= new LongHashMap();
	protected final List		destinationCoordinateKeys	= new ArrayList();
	
	public CustomTeleporter(WorldServer world)
	{
		super(world);
		this.world = world;
		this.random = new Random(world.getSeed());
	}
	
	@Override
	public void func_180266_a(Entity entity, float yaw)
	{
		if (!this.func_180620_b(entity, yaw))
		{
			this.makePortal(entity);
			this.func_180620_b(entity, yaw);
		}
	}
	
	@Override
	public boolean func_180620_b(Entity entity, float yaw)
	{
		
		boolean flag = true;
		double d0 = -1.0D;
		int i = MathHelper.floor_double(entity.posX);
		int j = MathHelper.floor_double(entity.posZ);
		boolean flag1 = true;
		Object object = BlockPos.ORIGIN;
		long k = ChunkCoordIntPair.chunkXZ2Int(i, j);
		
		if (this.destinationCoordinateCache.containsItem(k))
		{
			Teleporter.PortalPosition portalposition = (Teleporter.PortalPosition) this.destinationCoordinateCache.getValueByKey(k);
			d0 = 0.0D;
			object = portalposition;
			portalposition.lastUpdateTime = this.world.getTotalWorldTime();
			flag1 = false;
		}
		else
		{
			BlockPos blockpos4 = new BlockPos(entity);
			
			for (int l = -128; l <= 128; ++l)
			{
				BlockPos blockpos1;
				
				for (int i1 = -128; i1 <= 128; ++i1)
				{
					for (BlockPos blockpos = blockpos4.add(l, this.world.getActualHeight() - 1 - blockpos4.getY(), i1); blockpos.getY() >= 0; blockpos = blockpos1)
					{
						blockpos1 = blockpos.offsetDown();
						
						if (this.world.getBlockState(blockpos).equals(this.portalBlock))
						{
							while (this.world.getBlockState(blockpos1 = blockpos.offsetDown()).equals(this.portalBlock))
							{
								blockpos = blockpos1;
							}
							
							double d1 = blockpos.distanceSq(blockpos4);
							
							if (d0 < 0.0D || d1 < d0)
							{
								d0 = d1;
								object = blockpos;
							}
						}
					}
				}
			}
		}
		
		if (d0 >= 0.0D)
		{
			if (flag1)
			{
				this.destinationCoordinateCache.add(k, new Teleporter.PortalPosition((BlockPos) object, this.world.getTotalWorldTime()));
				this.destinationCoordinateKeys.add(Long.valueOf(k));
			}
			
			double d4 = ((BlockPos) object).getX() + 0.5D;
			double d5 = ((BlockPos) object).getY() + 0.5D;
			double d6 = ((BlockPos) object).getZ() + 0.5D;
			EnumFacing enumfacing = null;
			
			if (this.world.getBlockState(((BlockPos) object).offsetWest()).equals(this.portalBlock))
			{
				enumfacing = EnumFacing.NORTH;
			}
			
			if (this.world.getBlockState(((BlockPos) object).offsetEast()).equals(this.portalBlock))
			{
				enumfacing = EnumFacing.SOUTH;
			}
			
			if (this.world.getBlockState(((BlockPos) object).offsetNorth()).equals(this.portalBlock))
			{
				enumfacing = EnumFacing.EAST;
			}
			
			if (this.world.getBlockState(((BlockPos) object).offsetSouth()).equals(this.portalBlock))
			{
				enumfacing = EnumFacing.WEST;
			}
			
			EnumFacing enumfacing1 = EnumFacing.getHorizontal(entity.getTeleportDirection());
			
			if (enumfacing != null)
			{
				EnumFacing enumfacing2 = enumfacing.rotateYCCW();
				BlockPos blockpos2 = ((BlockPos) object).offset(enumfacing);
				boolean flag2 = this.func_180265_a(blockpos2);
				boolean flag3 = this.func_180265_a(blockpos2.offset(enumfacing2));
				
				if (flag3 && flag2)
				{
					object = ((BlockPos) object).offset(enumfacing2);
					enumfacing = enumfacing.getOpposite();
					enumfacing2 = enumfacing2.getOpposite();
					BlockPos blockpos3 = ((BlockPos) object).offset(enumfacing);
					flag2 = this.func_180265_a(blockpos3);
					flag3 = this.func_180265_a(blockpos3.offset(enumfacing2));
				}
				
				float f6 = 0.5F;
				float f1 = 0.5F;
				
				if (!flag3 && flag2)
				{
					f6 = 1.0F;
				}
				else if (flag3 && !flag2)
				{
					f6 = 0.0F;
				}
				else if (flag3)
				{
					f1 = 0.0F;
				}
				
				d4 = ((BlockPos) object).getX() + 0.5D;
				d5 = ((BlockPos) object).getY() + 0.5D;
				d6 = ((BlockPos) object).getZ() + 0.5D;
				d4 += enumfacing2.getFrontOffsetX() * f6 + enumfacing.getFrontOffsetX() * f1;
				d6 += enumfacing2.getFrontOffsetZ() * f6 + enumfacing.getFrontOffsetZ() * f1;
				float f2 = 0.0F;
				float f3 = 0.0F;
				float f4 = 0.0F;
				float f5 = 0.0F;
				
				if (enumfacing == enumfacing1)
				{
					f2 = 1.0F;
					f3 = 1.0F;
				}
				else if (enumfacing == enumfacing1.getOpposite())
				{
					f2 = -1.0F;
					f3 = -1.0F;
				}
				else if (enumfacing == enumfacing1.rotateY())
				{
					f4 = 1.0F;
					f5 = -1.0F;
				}
				else
				{
					f4 = -1.0F;
					f5 = 1.0F;
				}
				
				double d2 = entity.motionX;
				double d3 = entity.motionZ;
				entity.motionX = d2 * f2 + d3 * f5;
				entity.motionZ = d2 * f4 + d3 * f3;
				entity.rotationYaw = yaw - enumfacing1.getHorizontalIndex() * 90 + enumfacing.getHorizontalIndex() * 90;
			}
			else
			{
				entity.motionX = entity.motionY = entity.motionZ = 0.0D;
			}
			
			entity.setLocationAndAngles(d4, d5, d6, entity.rotationYaw, entity.rotationPitch);
			return true;
		}
		else
			return false;
	}
	
	private boolean func_180265_a(BlockPos p_180265_1_)
	{
		return !this.world.isAirBlock(p_180265_1_) || !this.world.isAirBlock(p_180265_1_.offsetUp());
	}
	
	@Override
	public boolean makePortal(Entity p_85188_1_)
	{
		byte b0 = 16;
		double d0 = -1.0D;
		int i = MathHelper.floor_double(p_85188_1_.posX);
		int j = MathHelper.floor_double(p_85188_1_.posY);
		int k = MathHelper.floor_double(p_85188_1_.posZ);
		int l = i;
		int i1 = j;
		int j1 = k;
		int k1 = 0;
		int l1 = this.random.nextInt(4);
		int i2;
		double d1;
		int k2;
		double d2;
		int i3;
		int j3;
		int k3;
		int l3;
		int i4;
		int j4;
		int k4;
		int l4;
		int i5;
		double d3;
		double d4;
		
		for (i2 = i - b0; i2 <= i + b0; ++i2)
		{
			d1 = i2 + 0.5D - p_85188_1_.posX;
			
			for (k2 = k - b0; k2 <= k + b0; ++k2)
			{
				d2 = k2 + 0.5D - p_85188_1_.posZ;
				label271:
				
				for (i3 = this.world.getActualHeight() - 1; i3 >= 0; --i3)
				{
					if (this.world.isAirBlock(new BlockPos(i2, i3, k2)))
					{
						while (i3 > 0 && this.world.isAirBlock(new BlockPos(i2, i3 - 1, k2)))
						{
							--i3;
						}
						
						for (j3 = l1; j3 < l1 + 4; ++j3)
						{
							k3 = j3 % 2;
							l3 = 1 - k3;
							
							if (j3 % 4 >= 2)
							{
								k3 = -k3;
								l3 = -l3;
							}
							
							for (i4 = 0; i4 < 3; ++i4)
							{
								for (j4 = 0; j4 < 4; ++j4)
								{
									for (k4 = -1; k4 < 4; ++k4)
									{
										l4 = i2 + (j4 - 1) * k3 + i4 * l3;
										i5 = i3 + k4;
										int j5 = k2 + (j4 - 1) * l3 - i4 * k3;
										
										if (k4 < 0 && !this.world.getBlockState(new BlockPos(l4, i5, j5)).getBlock().getMaterial().isSolid() || k4 >= 0 && !this.world.isAirBlock(new BlockPos(l4, i5, j5)))
										{
											continue label271;
										}
									}
								}
							}
							
							d3 = i3 + 0.5D - p_85188_1_.posY;
							d4 = d1 * d1 + d3 * d3 + d2 * d2;
							
							if (d0 < 0.0D || d4 < d0)
							{
								d0 = d4;
								l = i2;
								i1 = i3;
								j1 = k2;
								k1 = j3 % 4;
							}
						}
					}
				}
			}
		}
		
		if (d0 < 0.0D)
		{
			for (i2 = i - b0; i2 <= i + b0; ++i2)
			{
				d1 = i2 + 0.5D - p_85188_1_.posX;
				
				for (k2 = k - b0; k2 <= k + b0; ++k2)
				{
					d2 = k2 + 0.5D - p_85188_1_.posZ;
					label219:
					
					for (i3 = this.world.getActualHeight() - 1; i3 >= 0; --i3)
					{
						if (this.world.isAirBlock(new BlockPos(i2, i3, k2)))
						{
							while (i3 > 0 && this.world.isAirBlock(new BlockPos(i2, i3 - 1, k2)))
							{
								--i3;
							}
							
							for (j3 = l1; j3 < l1 + 2; ++j3)
							{
								k3 = j3 % 2;
								l3 = 1 - k3;
								
								for (i4 = 0; i4 < 4; ++i4)
								{
									for (j4 = -1; j4 < 4; ++j4)
									{
										k4 = i2 + (i4 - 1) * k3;
										l4 = i3 + j4;
										i5 = k2 + (i4 - 1) * l3;
										
										if (j4 < 0 && !this.world.getBlockState(new BlockPos(k4, l4, i5)).getBlock().getMaterial().isSolid() || j4 >= 0 && !this.world.isAirBlock(new BlockPos(k4, l4, i5)))
										{
											continue label219;
										}
									}
								}
								
								d3 = i3 + 0.5D - p_85188_1_.posY;
								d4 = d1 * d1 + d3 * d3 + d2 * d2;
								
								if (d0 < 0.0D || d4 < d0)
								{
									d0 = d4;
									l = i2;
									i1 = i3;
									j1 = k2;
									k1 = j3 % 2;
								}
							}
						}
					}
				}
			}
		}
		
		int k5 = l;
		int j2 = i1;
		k2 = j1;
		int l5 = k1 % 2;
		int l2 = 1 - l5;
		
		if (k1 % 4 >= 2)
		{
			l5 = -l5;
			l2 = -l2;
		}
		
		if (d0 < 0.0D)
		{
			i1 = MathHelper.clamp_int(i1, 70, this.world.getActualHeight() - 10);
			j2 = i1;
			
			for (i3 = -1; i3 <= 1; ++i3)
			{
				for (j3 = 1; j3 < 3; ++j3)
				{
					for (k3 = -1; k3 < 3; ++k3)
					{
						l3 = k5 + (j3 - 1) * l5 + i3 * l2;
						i4 = j2 + k3;
						j4 = k2 + (j3 - 1) * l2 - i3 * l5;
						boolean flag = k3 < 0;
						this.world.setBlockState(new BlockPos(l3, i4, j4), flag ? this.frameBlock : Blocks.air.getDefaultState());
					}
				}
			}
		}
		
		IBlockState iblockstate = this.portalBlock.withProperty(BlockPortal.field_176550_a, l5 != 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z);
		
		for (j3 = 0; j3 < 4; ++j3)
		{
			for (k3 = 0; k3 < 4; ++k3)
			{
				for (l3 = -1; l3 < 4; ++l3)
				{
					i4 = k5 + (k3 - 1) * l5;
					j4 = j2 + l3;
					k4 = k2 + (k3 - 1) * l2;
					boolean flag1 = k3 == 0 || k3 == 3 || l3 == -1 || l3 == 3;
					this.world.setBlockState(new BlockPos(i4, j4, k4), flag1 ? this.frameBlock : iblockstate, 2);
				}
			}
			
			for (k3 = 0; k3 < 4; ++k3)
			{
				for (l3 = -1; l3 < 4; ++l3)
				{
					i4 = k5 + (k3 - 1) * l5;
					j4 = j2 + l3;
					k4 = k2 + (k3 - 1) * l2;
					this.world.notifyNeighborsOfStateChange(new BlockPos(i4, j4, k4), this.world.getBlockState(new BlockPos(i4, j4, k4)).getBlock());
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void removeStalePortalLocations(long time)
	{
		if (time % 100L == 0L)
		{
			Iterator iterator = this.destinationCoordinateKeys.iterator();
			long j = time - 600L;
			
			while (iterator.hasNext())
			{
				Long olong = (Long) iterator.next();
				PortalPosition portalposition = (PortalPosition) this.destinationCoordinateCache.getValueByKey(olong.longValue());
				
				if (portalposition == null || portalposition.lastUpdateTime < j)
				{
					iterator.remove();
					this.destinationCoordinateCache.remove(olong.longValue());
				}
			}
		}
	}
}
