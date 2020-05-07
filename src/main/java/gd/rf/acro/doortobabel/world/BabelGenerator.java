package gd.rf.acro.doortobabel.world;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

public class BabelGenerator {

    public static void generate(World world, BlockPos start)
    {
        SOSpiral(200,world,start);

    }

    public static void makePlatformToGround(World world, BlockPos centre)
    {
        int myy = centre.getY();
        for (int i = centre.getX()-2; i <= centre.getX()+2; i++) {
            for (int j = centre.getZ()-2; j <= centre.getZ()+2; j++) {
                int thisy = myy;
                int topy = world.getTopY(Heightmap.Type.WORLD_SURFACE,i,j);
                while (thisy>=topy)
                {
                    world.setBlockState(new BlockPos(i,thisy,j), Blocks.POLISHED_ANDESITE.getDefaultState());
                    thisy--;
                }
            }
        }
    }
//https://stackoverflow.com/questions/3706219/algorithm-for-iterating-over-an-outward-spiral-on-a-discrete-2d-grid-from-the-or/3706260#3706260
    public static void SOSpiral(int points, World world, BlockPos pos)
        {
            // (di, dj) is a vector - direction in which we move right now
            int di = 1;
            int dj = 0;
            // length of current segment
            int segment_length = 1;

            int up = pos.getY();
            // current position (i, j) and how much of current segment we passed
            int i = 0;
            int j = 0;
            int segment_passed = 0;
            for (int k = 0; k < points; ++k) {
                // make a step, add 'direction' vector (di, dj) to current position (i, j)
                i += di;
                j += dj;
                ++segment_passed;
                System.out.println(i + " " + j);

                BlockPos struct = new BlockPos(pos.getX()+(i*5),up,pos.getZ()+(j*5));
                makePlatformToGround(world,struct);
                up++;
                if (segment_passed == segment_length) {
                    // done with current segment
                    segment_passed = 0;

                    // 'rotate' directions
                    int buffer = di;
                    di = -dj;
                    dj = buffer;

                    // increase segment length if necessary
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
}
