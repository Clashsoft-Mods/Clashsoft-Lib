package clashsoft.cslib.world;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;

public abstract class CustomBiomeLayer extends GenLayer
{
	public CustomBiomeLayer(long seed, GenLayer genlayer)
	{
		super(seed);
		this.parent = genlayer;
	}
	
	public CustomBiomeLayer(long seed)
	{
		super(seed);
	}
	
	public GenLayer[] generate(long seed, WorldType worldType)
	{
		GenLayer biomes = this;
		int biomeSize = this.getBiomeSize(seed, worldType);
		biomes = GenLayerZoom.magnify(seed, biomes, biomeSize);
		GenLayer voronoiZoom = new GenLayerVoronoiZoom(seed, biomes);
		
		biomes.initWorldGenSeed(seed);
		voronoiZoom.initWorldGenSeed(seed);
		
		return new GenLayer[] { biomes, voronoiZoom };
	}
	
	@Override
	public int[] getInts(int x, int z, int width, int depth)
	{
		BiomeGenBase[] biomes = this.getBiomes();
		
		int[] dest = IntCache.getIntCache(width * depth);
		
		for (int dz = 0; dz < depth; dz++)
		{
			for (int dx = 0; dx < width; dx++)
			{
				this.initChunkSeed(dx + x, dz + z);
				dest[dx + dz * width] = biomes[this.nextInt(biomes.length)].biomeID;
			}
		}
		return dest;
	}
	
	public abstract int getBiomeSize(long seed, WorldType worldType);
	
	public abstract BiomeGenBase[] getBiomes();
}
