package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import gd.rf.acro.doortobabel.Utils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class AqueductBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public AqueductBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));

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
        return Block.createCuboidShape(0d,7d,0d,16d,11d,16d);
    }



    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new AqueductBlockEntity();
    }
    //to be called after the first duct has water in it
    public void tellNeighbours(World world, BlockPos pos, int speed, Vec3i dir,BlockPos origin)
    {
        if(world.getBlockState(pos.add(dir)).getBlock()instanceof ChestBlock && world.getBlockState(origin.down()).getBlock()instanceof ChestBlock)
        {
            //move items from the chest about the origin pos to this chest
            ChestBlockEntity input = (ChestBlockEntity) world.getBlockEntity(origin.down());
            ChestBlockEntity output = (ChestBlockEntity) world.getBlockEntity(pos.add(dir));
            ItemStack item = ItemStack.EMPTY;
            for (int i = 0; i < input.getInvSize(); i++) {
                if(input.getInvStack(i)!= ItemStack.EMPTY)
                {
                    item=input.getInvStack(i);
                    input.setInvStack(i,ItemStack.EMPTY);
                    break;
                }
            }
            if(item!=ItemStack.EMPTY)
            {
                for (int i = 0; i < output.getInvSize(); i++) {
                    if(output.getInvStack(i)==ItemStack.EMPTY)
                    {
                        output.setInvStack(i,item);
                        break;
                    }
                }
            }
        }
        if(world.getBlockState(pos).getBlock()==DoorToBabel.AQUEDUCT_WATER || world.getBlockState(pos).getBlock()==DoorToBabel.AQUEDUCT_ROT_DROP_WATER || world.getBlockState(pos).getBlock()==DoorToBabel.AQUEDUCT_CORNER_WATER)
        {
            AqueductBlockEntity entity = (AqueductBlockEntity) world.getBlockEntity(pos);
            entity.setSpeed(speed);
            Block block=world.getBlockState(pos.add(dir)).getBlock();
            Vec3i thisRot = world.getBlockState(pos).get(Properties.HORIZONTAL_FACING).getVector();
            Vec3i thatRot = null;
            if(block instanceof AqueductBlock)
            {
                thatRot = world.getBlockState(pos.add(dir)).get(Properties.HORIZONTAL_FACING).getVector();
            }

            if((block==DoorToBabel.AQUEDUCT || block==DoorToBabel.AQUEDUCT_WATER) && entity.getSpeed()>1 && (dir.equals(thatRot) || dir.getY()<0))
            {
                BlockRotation rot = Utils.from3i(world.getBlockState(pos.add(dir)).get(Properties.HORIZONTAL_FACING).getVector());
                world.setBlockState(pos.add(dir),DoorToBabel.AQUEDUCT_WATER.getDefaultState().rotate(rot));
               AqueductBlock next = (AqueductBlock) world.getBlockState(pos.add(dir)).getBlock();
               next.tellNeighbours(world,pos.add(dir),speed-1,world.getBlockState(pos.add(dir)).get(Properties.HORIZONTAL_FACING).getVector(),origin);
            }
            if(((block==DoorToBabel.AQUEDUCT_ROT_DROP || block==DoorToBabel.AQUEDUCT_ROT_DROP_WATER) && entity.getSpeed()>1) && (dir.equals(thatRot) || dir.getY()<0))
            {
                BlockRotation rot = Utils.from3i(world.getBlockState(pos.add(dir)).get(Properties.HORIZONTAL_FACING).getVector());
                world.setBlockState(pos.add(dir),DoorToBabel.AQUEDUCT_ROT_DROP_WATER.getDefaultState().rotate(rot));

                AqueductBlock next = (AqueductBlock) world.getBlockState(pos.add(dir)).getBlock();
                next.tellNeighbours(world,pos.add(dir),speed,new Vec3i(0,-1,0),origin);
            }
            if((block==DoorToBabel.AQUEDUCT_CORNER || block==DoorToBabel.AQUEDUCT_CORNER_WATER) && entity.getSpeed()>1 && Utils.getValidFacings(thatRot).contains(dir))
            {
                BlockRotation rot = Utils.from3i(world.getBlockState(pos.add(dir)).get(Properties.HORIZONTAL_FACING).getVector());
                world.setBlockState(pos.add(dir),DoorToBabel.AQUEDUCT_CORNER_WATER.getDefaultState().rotate(rot));
                AqueductBlock next = (AqueductBlock) world.getBlockState(pos.add(dir)).getBlock();
                next.tellNeighbours(world,pos.add(dir),speed-1,Utils.cornerPieceRedirect(thisRot,thatRot),origin);
            }

        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(player.getStackInHand(hand).getItem()== Items.GLASS_BOTTLE && !world.isClient)
        {
            AqueductBlockEntity entity = (AqueductBlockEntity) world.getBlockEntity(pos);
            player.sendMessage(new LiteralText("the speed here is "+entity.getSpeed()+"b/i"));
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(random.nextBoolean() && !world.isRaining())
        {
            BlockRotation rot = Utils.from3i(world.getBlockState(pos).get(Properties.HORIZONTAL_FACING).getVector());
            if(state.getBlock()==DoorToBabel.AQUEDUCT_WATER)
            {
                world.setBlockState(pos,DoorToBabel.AQUEDUCT.getDefaultState().rotate(rot));
            }
            if(state.getBlock()==DoorToBabel.AQUEDUCT_CORNER_WATER)
            {
                world.setBlockState(pos,DoorToBabel.AQUEDUCT_CORNER.getDefaultState().rotate(rot));
            }
            if(state.getBlock()==DoorToBabel.AQUEDUCT_ROT_DROP_WATER)
            {
                world.setBlockState(pos,DoorToBabel.AQUEDUCT_ROT_DROP.getDefaultState().rotate(rot));
            }
        }
    }
}
