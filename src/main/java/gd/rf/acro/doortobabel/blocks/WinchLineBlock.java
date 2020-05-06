package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import gd.rf.acro.doortobabel.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class WinchLineBlock extends HorizontalFacingBlock {
    private int delay=20;
    private boolean springLoaded;
    public WinchLineBlock(Settings settings, int ndelay, boolean spring) {
        super(settings);
        delay=ndelay;
        springLoaded=spring;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(world.getBlockState(pos.south()).getBlock()== DoorToBabel.WATERWHEEL_BLOCK)
        {
            doWinch(state,world,pos);
            world.getBlockTickScheduler().schedule(pos,this,delay);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getStackInHand(hand)== ItemStack.EMPTY)
        {
            player.fallDistance=0;
            player.setVelocity(0,0,0);
            player.teleport(pos.getX()+0.5,pos.getY()-0.5,pos.getZ()+0.5);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
    public void doWinch(BlockState state, ServerWorld world, BlockPos pos)
    {
        if(world.getBlockState(pos.down()).getBlock()!=Blocks.AIR)
        {
            Vec3i dir = state.get(Properties.HORIZONTAL_FACING).getVector();
            BlockState move = world.getBlockState(pos.down());
            world.setBlockState(pos.down().add(dir),move);
            world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());

            if(world.getBlockState(pos.add(dir)).getBlock()instanceof WinchLineBlock)
            {
                WinchLineBlock block = (WinchLineBlock) world.getBlockState(pos.add(dir)).getBlock();
                block.doWinch(world.getBlockState(pos.add(dir)),world,pos.add(dir));
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING,ctx.getPlayerFacing().getOpposite());
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return Block.createCuboidShape(0d,6d,0d,16d,10d,16d);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if(springLoaded)
        {
            Vec3i dir = state.get(Properties.HORIZONTAL_FACING).getVector();
            BlockPos current = pos.add(dir);
            placer.playSound(SoundEvents.BLOCK_PISTON_EXTEND,1,1);
            while (world.getBlockState(current).getBlock()==Blocks.AIR && itemStack.getCount()>1)
            {
                world.setBlockState(current,DoorToBabel.WINCH_LINE.getDefaultState().rotate(Utils.from3i(dir)));
                current=current.add(dir);
                itemStack.decrement(1);
            }
        }
    }
}
