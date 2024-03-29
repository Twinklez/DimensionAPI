package net.minecraft.src;

public class GenLayerDimensionBorderBiome extends GenLayer
{
    public GenLayerDimensionBorderBiome(long l, GenLayer genlayer, WorldProviderBase worldproviderbase)
    {
        super(l);
        parent = genlayer;
		worldProvider = worldproviderbase;
    }

    public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int ai[] = parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
        int ai1[] = IntCache.getIntCache(par3 * par4);

        for (int i = 0; i < par4; i++)
        {
            for (int j = 0; j < par3; j++)
            {
				initChunkSeed(j + par1, i + par2);
                int k = ai[j + 1 + (i + 1) * (par3 + 2)];

                int l = ai[j + 1 + ((i + 1) - 1) * (par3 + 2)];
                int k1 = ai[j + 1 + 1 + (i + 1) * (par3 + 2)];
                int j2 = ai[((j + 1) - 1) + (i + 1) * (par3 + 2)];
                int i3 = ai[j + 1 + (i + 1 + 1) * (par3 + 2)];

				BiomeGenBase biome1 = worldProvider.setBorderBiomes(BiomeGenBase.biomeList[k], BiomeGenBase.biomeList[l], this);
                BiomeGenBase biome2 = worldProvider.setBorderBiomes(BiomeGenBase.biomeList[k], BiomeGenBase.biomeList[k1], this);
				BiomeGenBase biome3 = worldProvider.setBorderBiomes(BiomeGenBase.biomeList[k], BiomeGenBase.biomeList[j2], this);
				BiomeGenBase biome4 = worldProvider.setBorderBiomes(BiomeGenBase.biomeList[k], BiomeGenBase.biomeList[i3], this);
				if (biome1 != null)
                {
                    ai1[j + i * par3] = biome1.biomeID;
                } else
				if (biome2 != null)
                {
                    ai1[j + i * par3] = biome2.biomeID;
                } else
				if (biome3 != null)
                {
                    ai1[j + i * par3] = biome3.biomeID;
                } else
				if (biome4 != null)
                {
                    ai1[j + i * par3] = biome4.biomeID;
                } else
                {
                    ai1[j + i * par3] = k;
                }
            }
        }

        return ai1;
    }
	
	private WorldProviderBase worldProvider;
}
