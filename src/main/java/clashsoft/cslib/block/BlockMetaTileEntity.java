package clashsoft.cslib.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import clashsoft.cslib.logging.CSLog;

public class BlockMetaTileEntity extends CustomBlock implements ITileEntityProvider
{
	public Class[]	tileEntities;
	
	public BlockMetaTileEntity(Material material, String name, CreativeTabs creativeTab)
	{
		super(material, new String[] { name }, new CreativeTabs[] { creativeTab });
	}
	
	public BlockMetaTileEntity(Material material, String[] names, CreativeTabs[] creativeTabs)
	{
		super(material, names, creativeTabs);
	}
	
	public BlockMetaTileEntity setTileEntityClasses(Class<? extends TileEntity>... tileEntityClasses)
	{
		this.tileEntities = tileEntityClasses;
		return this;
	}
	
	public BlockMetaTileEntity setTileEntityClass(int metadata, Class<? extends TileEntity> tileEntityClass)
	{
		if (this.tileEntities == null)
		{
			this.tileEntities = new Class[this.names.length];
		}
		this.tileEntities[metadata] = tileEntityClass;
		return this;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return this.tileEntities[(int) state.getValue(typeProperty)] != null;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		Class tileEntityClass = this.tileEntities[(int) state.getValue(typeProperty)];
		if (tileEntityClass != null)
		{
			try
			{
				return (TileEntity) tileEntityClass.newInstance();
			}
			catch (Exception ex)
			{
				CSLog.error(ex);
			}
		}
		return null;
	}
	
	@Override
	public TileEntity createNewTileEntity(World paramWorld, int paramInt)
	{
		return null;
	}
}
