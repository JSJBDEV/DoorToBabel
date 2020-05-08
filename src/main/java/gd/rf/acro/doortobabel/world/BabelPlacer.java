package gd.rf.acro.doortobabel.world;

import net.fabricmc.fabric.api.dimension.v1.EntityPlacer;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class BabelPlacer {
    public static final EntityPlacer ENTERING = (teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        destination.setBlockState(new BlockPos(0,199,0), Blocks.POLISHED_ANDESITE.getDefaultState());
        return new BlockPattern.TeleportTarget(new Vec3d(0,200,0), Vec3d.ZERO, 0);
    };

}
