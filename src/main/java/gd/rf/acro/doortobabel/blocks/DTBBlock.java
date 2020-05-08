package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class DTBBlock extends Block {
    public DTBBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient)
        {
            if(player.dimension== DimensionType.OVERWORLD)
            {
                player.changeDimension(DoorToBabel.BABEL);
            }
            else
            {
                player.changeDimension(DimensionType.OVERWORLD);
                player.teleport(0,100,0);
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
