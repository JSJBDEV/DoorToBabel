package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import gd.rf.acro.doortobabel.Utils;
import gd.rf.acro.doortobabel.world.BabelGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BabelGateBlock extends HorizontalFacingBlock {
    public BabelGateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            if(world.getBlockState(pos.add(state.get(Properties.HORIZONTAL_FACING).getVector())).getBlock()!= DoorToBabel.BABEL_GATE)
            {
                Vec3i vec = state.get(Properties.HORIZONTAL_FACING).getVector();
                Utils.spawnStructure((ServerWorld) world,pos.down(3).add(vec).add(vec).add(vec).add(vec),"room_template");
                for (int i = pos.getX()-1; i < pos.getX()+2; i++) {
                    for (int j = pos.getY()-1; j < pos.getY()+2; j++) {
                            world.setBlockState(pos,Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING,ctx.getPlayerFacing());
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return Block.createCuboidShape(1d,1d,1d,14d,14d,14d);
    }
}
