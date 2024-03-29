package net.minecraft.src;

import java.util.Random;

public class TeleporterCustom extends Teleporter
{

    public TeleporterCustom(int i, int j)
    {
		this(i, 0, j, 0, false, 127);
    }
	
	public TeleporterCustom(int i, int j, int k, int l, boolean flag, int i1)
	{
		frameID = i;
		frameMetadata = j;
		portalID = k;
		portalMetadata = l;
		portalMetaSensitivity = flag;
		heightLimit = i1;
		if(heightLimit > 255)
		{
			heightLimit = 255;
		}
	}

    public void placeInPortal(World par1World, Entity par2Entity)
    {
        if (placeInExistingPortal(par1World, par2Entity))
        {
            return;
        }
        else
        {
            createPortal(par1World, par2Entity);
            placeInExistingPortal(par1World, par2Entity);
            return;
        }
    }

    public boolean placeInExistingPortal(World par1World, Entity par2Entity)
    {
        char c = '\200';
        double d = -1D;
        int i = 0;
        int j = 0;
        int k = 0;
        int l = MathHelper.floor_double(par2Entity.posX);
        int i1 = MathHelper.floor_double(par2Entity.posZ);

        for (int j1 = l - c; j1 <= l + c; j1++)
        {
            double d1 = ((double)j1 + 0.5D) - par2Entity.posX;

            for (int j2 = i1 - c; j2 <= i1 + c; j2++)
            {
                double d3 = ((double)j2 + 0.5D) - par2Entity.posZ;

                for (int k2 = heightLimit; k2 >= 0; k2--)
                {
                    if (par1World.getBlockId(j1, k2, j2) != portalID || (portalMetaSensitivity && par1World.getBlockMetadata(j1, k2, j2) != portalMetadata))
                    {
                        continue;
                    }

                    for (; par1World.getBlockId(j1, k2 - 1, j2) == portalID; k2--) { }

                    double d5 = ((double)k2 + 0.5D) - par2Entity.posY;
                    double d7 = d1 * d1 + d5 * d5 + d3 * d3;

                    if (d < 0.0D || d7 < d)
                    {
                        d = d7;
                        i = j1;
                        j = k2;
                        k = j2;
                    }
                }
            }
        }

        if (d >= 0.0D)
        {
            int k1 = i;
            int l1 = j;
            int i2 = k;
            double d2 = (double)k1 + 0.5D;
            double d4 = (double)l1 + 0.5D;
            double d6 = (double)i2 + 0.5D;

            if (par1World.getBlockId(k1 - 1, l1, i2) == portalID && (!portalMetaSensitivity || par1World.getBlockMetadata(k1 - 1, l1, i2) == portalMetadata))
            {
                d2 -= 0.5D;
            }

            if (par1World.getBlockId(k1 + 1, l1, i2) == portalID && (!portalMetaSensitivity || par1World.getBlockMetadata(k1 + 1, l1, i2) == portalMetadata))
            {
                d2 += 0.5D;
            }

            if (par1World.getBlockId(k1, l1, i2 - 1) == portalID && (!portalMetaSensitivity || par1World.getBlockMetadata(k1, l1, i2 - 1) == portalMetadata))
            {
                d6 -= 0.5D;
            }

            if (par1World.getBlockId(k1, l1, i2 + 1) == portalID && (!portalMetaSensitivity || par1World.getBlockMetadata(k1, l1, i2 + 1) == portalMetadata))
            {
                d6 += 0.5D;
            }

            par2Entity.setLocationAndAngles(d2, d4, d6, par2Entity.rotationYaw, 0.0F);
            par2Entity.motionX = par2Entity.motionY = par2Entity.motionZ = 0.0D;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Create a new portal near an entity.
     */
    public boolean createPortal(World par1World, Entity par2Entity)
    {
        byte byte0 = 16;
        double d = -1D;
        int i = MathHelper.floor_double(par2Entity.posX);
        int j = MathHelper.floor_double(par2Entity.posY);
        int k = MathHelper.floor_double(par2Entity.posZ);
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int l1 = random.nextInt(4);

        for (int i2 = i - byte0; i2 <= i + byte0; i2++)
        {
            double d1 = ((double)i2 + 0.5D) - par2Entity.posX;

            for (int j3 = k - byte0; j3 <= k + byte0; j3++)
            {
                double d3 = ((double)j3 + 0.5D) - par2Entity.posZ;

                for (int k4 = heightLimit; k4 >= 0; k4--)
                {
                    if (!par1World.isAirBlock(i2, k4, j3))
                    {
                        continue;
                    }

                    for (; k4 > 0 && par1World.isAirBlock(i2, k4 - 1, j3); k4--) { }

                    label0:

                    for (int k5 = l1; k5 < l1 + 4; k5++)
                    {
                        int l6 = k5 % 2;
                        int i8 = 1 - l6;

                        if (k5 % 4 >= 2)
                        {
                            l6 = -l6;
                            i8 = -i8;
                        }

                        for (int j9 = 0; j9 < 3; j9++)
                        {
                            for (int k10 = 0; k10 < 4; k10++)
                            {
                                for (int l11 = -1; l11 < 4; l11++)
                                {
                                    int j12 = i2 + (k10 - 1) * l6 + j9 * i8;
                                    int l12 = k4 + l11;
                                    int j13 = (j3 + (k10 - 1) * i8) - j9 * l6;

                                    if (l11 < 0 && !par1World.getBlockMaterial(j12, l12, j13).isSolid() || l11 >= 0 && !par1World.isAirBlock(j12, l12, j13))
                                    {
                                        break label0;
                                    }
                                }
                            }
                        }

                        double d5 = ((double)k4 + 0.5D) - par2Entity.posY;
                        double d7 = d1 * d1 + d5 * d5 + d3 * d3;

                        if (d < 0.0D || d7 < d)
                        {
                            d = d7;
                            l = i2;
                            i1 = k4;
                            j1 = j3;
                            k1 = k5 % 4;
                        }
                    }
                }
            }
        }

        if (d < 0.0D)
        {
            for (int j2 = i - byte0; j2 <= i + byte0; j2++)
            {
                double d2 = ((double)j2 + 0.5D) - par2Entity.posX;

                for (int k3 = k - byte0; k3 <= k + byte0; k3++)
                {
                    double d4 = ((double)k3 + 0.5D) - par2Entity.posZ;

                    for (int l4 = heightLimit; l4 >= 0; l4--)
                    {
                        if (!par1World.isAirBlock(j2, l4, k3))
                        {
                            continue;
                        }

                        for (; l4 > 0 && par1World.isAirBlock(j2, l4 - 1, k3); l4--) { }

                        label1:

                        for (int l5 = l1; l5 < l1 + 2; l5++)
                        {
                            int i7 = l5 % 2;
                            int j8 = 1 - i7;

                            for (int k9 = 0; k9 < 4; k9++)
                            {
                                for (int l10 = -1; l10 < 4; l10++)
                                {
                                    int i12 = j2 + (k9 - 1) * i7;
                                    int k12 = l4 + l10;
                                    int i13 = k3 + (k9 - 1) * j8;

                                    if (l10 < 0 && !par1World.getBlockMaterial(i12, k12, i13).isSolid() || l10 >= 0 && !par1World.isAirBlock(i12, k12, i13))
                                    {
                                        break label1;
                                    }
                                }
                            }

                            double d6 = ((double)l4 + 0.5D) - par2Entity.posY;
                            double d8 = d2 * d2 + d6 * d6 + d4 * d4;

                            if (d < 0.0D || d8 < d)
                            {
                                d = d8;
                                l = j2;
                                i1 = l4;
                                j1 = k3;
                                k1 = l5 % 2;
                            }
                        }
                    }
                }
            }
        }

        int k2 = k1;
        int l2 = l;
        int i3 = i1;
        int l3 = j1;
        int i4 = k2 % 2;
        int j4 = 1 - i4;

        if (k2 % 4 >= 2)
        {
            i4 = -i4;
            j4 = -j4;
        }

        if (d < 0.0D)
        {
            if (i1 < 70)
            {
                i1 = 70;
            }

            if (i1 > 118)
            {
                i1 = 118;
            }

            i3 = i1;

            for (int i5 = -1; i5 <= 1; i5++)
            {
                for (int i6 = 1; i6 < 3; i6++)
                {
                    for (int j7 = -1; j7 < 3; j7++)
                    {
                        int k8 = l2 + (i6 - 1) * i4 + i5 * j4;
                        int l9 = i3 + j7;
                        int i11 = (l3 + (i6 - 1) * j4) - i5 * i4;
                        boolean flag = j7 < 0;
						if(flag)
						{
							par1World.setBlockAndMetadataWithNotify(k8, l9, i11, frameID, frameMetadata);
						} else
						{
							par1World.setBlockWithNotify(k8, l9, i11, 0);
						}
                    }
                }
            }
        }

        for (int j5 = 0; j5 < 4; j5++)
        {
            par1World.editingBlocks = true;

            for (int j6 = 0; j6 < 4; j6++)
            {
                for (int k7 = -1; k7 < 4; k7++)
                {
                    int l8 = l2 + (j6 - 1) * i4;
                    int i10 = i3 + k7;
                    int j11 = l3 + (j6 - 1) * j4;
                    boolean flag1 = j6 == 0 || j6 == 3 || k7 == -1 || k7 == 3;
					if(flag1)
					{
						par1World.setBlockAndMetadataWithNotify(l8, i10, j11, frameID, frameMetadata);
					} else
					{
						par1World.setBlockAndMetadataWithNotify(l8, i10, j11, portalID, portalMetadata);
					}
                }
            }

            par1World.editingBlocks = false;

            for (int k6 = 0; k6 < 4; k6++)
            {
                for (int l7 = -1; l7 < 4; l7++)
                {
                    int i9 = l2 + (k6 - 1) * i4;
                    int j10 = i3 + l7;
                    int k11 = l3 + (k6 - 1) * j4;
                    par1World.notifyBlocksOfNeighborChange(i9, j10, k11, par1World.getBlockId(i9, j10, k11));
                }
            }
        }

        return true;
    }
	
	private int frameID;
	private int frameMetadata;
	private int portalID;
	private int portalMetadata;
	private boolean portalMetaSensitivity;
	private int heightLimit;
	private Random random = new Random();
}
