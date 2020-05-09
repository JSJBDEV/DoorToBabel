package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import gd.rf.acro.doortobabel.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class VirtualStairBlock extends Block {
    private boolean isUnlocked;
    public VirtualStairBlock(Settings settings,boolean unlocked) {
        super(settings);
        isUnlocked=unlocked;
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return Block.createCuboidShape(1d,0d,1d,15d,16d,15d);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient && isUnlocked)
        {
            BlockPos next = pos.down(15);
            if (pos.getY() < 20) {
                next = new BlockPos(pos.getX() + 1000, 200, pos.getZ() + 1000);
            }
            player.teleport(next.getX(),next.getY()+3,next.getZ());
            if(world.getBlockState(next.up()).getBlock()!=DoorToBabel.DUNGEON_ANCHOR)
            {
                Utils.spawnStructure((ServerWorld) world,next,"dungeon_entry");
                DungeonAnchorEntity entity = (DungeonAnchorEntity) world.getBlockEntity(pos.down().south(2).east(2));

                DungeonAnchorEntity newEntity = (DungeonAnchorEntity) world.getBlockEntity(next.up());
                newEntity.setupFloor(world,next.up(),entity.getMaxx()+1);
            }
        }
        if(player.getStackInHand(hand).getItem()== DoorToBabel.STAIR_KEY && !isUnlocked)
        {
            world.setBlockState(pos,DoorToBabel.VIRTUAL_STAIR_UNLOCKED.getDefaultState());
            player.setStackInHand(hand, ItemStack.EMPTY);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
