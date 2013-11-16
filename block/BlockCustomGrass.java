package clashsoft.clashsoftapi.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockCustomGrass extends Block implements ICustomBlock
{
	public String[]	names, topIconNames, sideIconNames, bottomIconNames;
	public int[]	dirtBlockIDs;
	public int[]	dirtBlockMetadatas;
	
	public Icon[]	topIcons, sideIcons, bottomIcons;
	
	public BlockCustomGrass(int blockID, String name, String topIcon, String sideIcon, String bottomIcon)
	{
		this(blockID, new String[] { name }, new String[] { topIcon }, new String[] { sideIcon }, new String[] { bottomIcon });
	}
	
	public BlockCustomGrass(int blockID, String[] names, String[] topIcons, String[] sideIcons, String[] bottomIcons)
	{
		super(blockID, Material.grass);
		this.names = names;
		this.topIconNames = topIcons;
		this.sideIconNames = sideIcons;
		this.bottomIconNames = bottomIcons;
		
		this.dirtBlockIDs = new int[names.length];
		this.dirtBlockMetadatas = new int[names.length];
	}
	
	public BlockCustomGrass setDirtBlocks(int[] ids, int[] metadata)
	{
		this.dirtBlockIDs = ids;
		this.dirtBlockMetadatas = metadata;
		return this;
	}
	
	public BlockCustomGrass setDirtBlock(int metadata, int dirtBlockID, int dirtBlockMetadata)
	{
		this.dirtBlockIDs[metadata] = dirtBlockID;
		this.dirtBlockMetadatas[metadata] = dirtBlockMetadata;
		return this;
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.topIcons = new Icon[topIconNames.length];
		this.sideIcons = new Icon[sideIconNames.length];
		this.bottomIcons = new Icon[bottomIconNames.length];
		
		for (int i = 0; i < topIconNames.length; i++)
		{
			this.topIcons[i] = par1IconRegister.registerIcon(this.topIconNames[i]);
			this.sideIcons[i] = par1IconRegister.registerIcon(this.sideIconNames[i]);
			this.bottomIcons[i] = par1IconRegister.registerIcon(this.bottomIconNames[i]);
		}
	}
	
	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int metadata)
	{
		return side == 1 ? this.topIcons[metadata] : (side == 0 ? this.bottomIcons[metadata] : this.sideIcons[metadata]);
	}
	
	@Override
	public void addNames()
	{
		for (int i = 0; i < this.names.length; i++)
		{
			LanguageRegistry.addName(new ItemStack(this, 1, i), this.names[i]);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list)
	{
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (!world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			int dirtID = this.dirtBlockIDs[metadata];
			int dirtMetadata = this.dirtBlockMetadatas[metadata];
			
			if (world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlockLightOpacity(x, y + 1, z) > 2)
				world.setBlock(x, y, z, dirtID, dirtMetadata, 3);
			
			else if (world.getBlockLightValue(x, y + 1, z) >= 9)
			{
				for (int l = 0; l < 4; ++l)
				{
					int i1 = x + random.nextInt(3) - 1;
					int j1 = y + random.nextInt(5) - 3;
					int k1 = z + random.nextInt(3) - 1;
					int l1 = world.getBlockId(i1, j1 + 1, k1);
					
					if (world.getBlockId(i1, j1, k1) == dirtID && world.getBlockMetadata(i1, j1, k1) == dirtMetadata && world.getBlockLightValue(i1, j1 + 1, k1) >= 4 && world.getBlockLightOpacity(i1, j1 + 1, k1) <= 2)
					{
						world.setBlock(i1, j1, k1, this.blockID, metadata, 3);
					}
				}
			}
		}
	}
}
