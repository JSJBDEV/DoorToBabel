package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import gd.rf.acro.doortobabel.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class DistributorBlock extends HorizontalFacingBlock {
    public DistributorBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.getBlockTickScheduler().schedule(pos,this,20);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        int pressure=0;
        BlockPos search=pos.up();
        while(world.getBlockState(search).getBlock() == DoorToBabel.WATER_COLLECTOR)
        {
            pressure+=2;
            if(world.isRaining())
            {
                pressure+=2;
            }
            search=search.up();
        }
        BlockPos duct = pos.add(state.get(Properties.HORIZONTAL_FACING).getVector());
        if(world.getBlockState(duct).getBlock()instanceof AqueductBlock)
        {
            BlockRotation rot = Utils.from3i(world.getBlockState(duct).get(Properties.HORIZONTAL_FACING).getVector());
            world.setBlockState(duct,DoorToBabel.AQUEDUCT_WATER.getDefaultState().rotate(rot));
            AqueductBlock block = (AqueductBlock) world.getBlockState(duct).getBlock();
            block.tellNeighbours(world,duct,pressure,world.getBlockState(duct).get(Properties.HORIZONTAL_FACING).getVector(),pos);
        }
        world.getBlockTickScheduler().schedule(pos,this,40);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return Block.createCuboidShape(4d,8d,4d,12d,16d,12d);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING,ctx.getPlayerFacing().getOpposite());
    }
}
