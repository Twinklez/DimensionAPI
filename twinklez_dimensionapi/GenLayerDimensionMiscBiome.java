package net.minecraft.src;

public class GenLayerDimensionMiscBiome extends GenLayer
{
    public GenLayerDimensionMiscBiome(long l, GenLayer genlayer, WorldProviderBase worldproviderbase)
    {
        super(l);
        parent = genlayer;
		worldProvider = worldproviderbase;
    }

    public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int i = par1 - 1;
        int j = par2 - 1;
        int k = par3 + 2;
        int l = par4 + 2;
        int ai[] = parent.getInts(i, j, k, l);
        int ai1[] = IntCache.getIntCache(par3 * par4);

        for (int i1 = 0; i1 < par4; i1++)
        {
            for (int j1 = 0; j1 < par3; j1++)
            {
                int k1 = ai[j1 + 0 + (i1 + 0) * k];
                int l1 = ai[j1 + 2 + (i1 + 0) * k];
                int i2 = ai[j1 + 0 + (i1 + 2) * k];
                int j2 = ai[j1 + 2 + (i1 + 2) * k];
                int k2 = ai[j1 + 1 + (i1 + 1) * k];
                initChunkSeed(j1 + par1, i1 + par2);

				BiomeGenBase biome = worldProvider.setMiscellaneousBiomes(this);
                if (biome != null)
                {
                    ai1[j1 + i1 * par3] = biome.biomeID;
                }
                else
                {
                    ai1[j1 + i1 * par3] = k2;
                }
            }
        }

        return ai1;
    }
	
	private WorldProviderBase worldProvider;
}
