package clashsoft.cslib.minecraft.block;

import java.util.Random;

import clashsoft.cslib.minecraft.client.icon.IIconSupplier;
import clashsoft.cslib.minecraft.client.icon.grass.GrassIconSupplier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCustomGrass extends CustomBlock
{
	public Block[]			dirtBlocks;
	public int[]			dirtBlockMetadatas;
	
	public IIconSupplier	iconSupplier;
	
	public BlockCustomGrass(String name, String icon)
	{
		this(new String[] { name }, new String[] { icon });
	}
	
	public BlockCustomGrass(String[] names, Object icons)
	{
		super(Material.grass, names, null, null);
		this.setStepSound(Block.soundTypeGrass);
		this.setTickRandomly(true);
		this.setHardness(0.6F);
		
		this.names = names;
		this.iconSupplier = GrassIconSupplier.create(icons);
		
		this.dirtBlocks = new Block[names.length];
		this.dirtBlockMetadatas = new int[names.length];
	}
	
	public BlockCustomGrass setDirtBlocks(Block[] blocks, int[] metadata)
	{
		this.dirtBlocks = blocks;
		this.dirtBlockMetadatas = metadata;
		return this;
	}
	
	public BlockCustomGrass setDirtBlock(int metadata, Block block, int dirtBlockMetadata)
	{
		this.dirtBlocks[metadata] = block;
		this.dirtBlockMetadatas[metadata] = dirtBlockMetadata;
		return this;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.iconSupplier.registerIcons(iconRegister);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return this.iconSupplier.getIcon(metadata, side);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		Block dirt = this.dirtBlocks[metadata];
		int meta = this.dirtBlockMetadatas[metadata];
		return dirt.getItemDropped(meta, random, fortune);
	}
	
	@Override
	public int damageDropped(int metadata)
	{
		return this.dirtBlockMetadatas[metadata];
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (!world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			Block dirt = this.dirtBlocks[metadata];
			int dirtMetadata = this.dirtBlockMetadatas[metadata];
			int lightValue = world.getBlockLightValue(x, y + 1, z);
			int lightOpacity = world.getBlockLightOpacity(x, y + 1, z);
			
			if (lightValue < 4 && lightOpacity > 2 && dirt != null)
			{
				world.setBlock(x, y, z, dirt, dirtMetadata, 3);
			}
			else if (world.getBlockLightValue(x, y + 1, z) >= 9)
			{
				for (int l = 0; l < 4; ++l)
				{
					int x1 = x + random.nextInt(3) - 1;
					int y1 = y + random.nextInt(5) - 3;
					int z1 = z + random.nextInt(3) - 1;
					
					Block block = world.getBlock(x1, y1, z1);
					int blockMetadata = world.getBlockMetadata(x1, y1, z1);
					lightValue = world.getBlockLightValue(x1, y1 + 1, z1);
					lightOpacity = world.getBlockLightOpacity(x1, y1 + 1, z1);
					
					if (block == dirt && blockMetadata == dirtMetadata && lightValue >= 4 && lightOpacity <= 2)
					{
						world.setBlock(x1, y1, z1, this, metadata, 3);
					}
				}
			}
		}
	}
}
