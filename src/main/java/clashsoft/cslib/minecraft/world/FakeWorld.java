package clashsoft.cslib.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class FakeWorld implements IBlockAccess
{
	public static FakeWorld	instance	= new FakeWorld();
	
	public int				x;
	public int				y;
	public int				z;
	public Block			block		= Blocks.air;
	public int				metadata;
	public TileEntity		tileEntity;
	public BiomeGenBase		biome		= BiomeGenBase.plains;
	public int				brightness	= 15 << 20 | 15 << 4;
	
	public FakeWorld position(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public FakeWorld block(Block block, int metadata)
	{
		this.block = block;
		this.metadata = metadata;
		return this;
	}
	
	public FakeWorld block(Block block, int metadata, TileEntity tileEntity)
	{
		this.block = block;
		this.metadata = metadata;
		this.tileEntity = tileEntity;
		return this;
	}
	
	public FakeWorld biome(BiomeGenBase biome)
	{
		this.biome = biome;
		return this;
	}
	
	public FakeWorld brightness(int brightness)
	{
		this.brightness = brightness;
		return this;
	}
	
	public FakeWorld reset()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.block = Blocks.air;
		this.metadata = 0;
		this.tileEntity = null;
		this.biome = BiomeGenBase.plains;
		this.brightness = 15 << 20 | 15 << 4;
		return this;
	}
	
	@Override
	public Block getBlock(int x, int y, int z)
	{
		return this.x == x && this.y == y && this.z == z ? this.block : Blocks.air;
	}
	
	@Override
	public TileEntity getTileEntity(int x, int y, int z)
	{
		return this.x == x && this.y == y && this.z == z ? this.tileEntity : null;
	}
	
	@Override
	public int getLightBrightnessForSkyBlocks(int x, int y, int z, int max)
	{
		return this.brightness;
	}
	
	@Override
	public int getBlockMetadata(int x, int y, int z)
	{
		return this.x == x && this.y == y && this.z == z ? this.metadata : 0;
	}
	
	@Override
	public int isBlockProvidingPowerTo(int x, int y, int z, int side)
	{
		return this.x == x && this.y == y && this.z == z ? this.block.isProvidingWeakPower(this, x, y, z, side) : 0;
	}
	
	@Override
	public boolean isAirBlock(int x, int y, int z)
	{
		return this.x == x && this.y == y && this.z == z && this.block == Blocks.air;
	}
	
	@Override
	public BiomeGenBase getBiomeGenForCoords(int x, int z)
	{
		return this.biome;
	}
	
	@Override
	public int getHeight()
	{
		return 256;
	}
	
	@Override
	public boolean extendedLevelsInChunkCache()
	{
		return false;
	}
	
	@Override
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default)
	{
		return this.x == x && this.y == y && this.z == z ? this.block.isSideSolid(this, x, y, z, side) : _default;
	}
}
