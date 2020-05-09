package gd.rf.acro.doortobabel.world;

import gd.rf.acro.doortobabel.DoorToBabel;
import gd.rf.acro.doortobabel.Utils;
import gd.rf.acro.doortobabel.blocks.DungeonAnchorEntity;
import net.fabricmc.fabric.api.dimension.v1.EntityPlacer;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class BabelPlacer {
    private static final BlockPos ONE99 = new BlockPos(0,199,0);
    public static final EntityPlacer ENTERING = (teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        if(destination.getBlockState(ONE99.up()).getBlock()!= DoorToBabel.DUNGEON_ANCHOR){
            Utils.spawnStructure(destination,ONE99,"dungeon_entry");
            DungeonAnchorEntity entity = (DungeonAnchorEntity) destination.getBlockEntity(ONE99.up());
            entity.setupFloor(destination,ONE99.up(),1);
        }
        return new BlockPattern.TeleportTarget(new Vec3d(1,203,1), Vec3d.ZERO, 0);
    };

}
