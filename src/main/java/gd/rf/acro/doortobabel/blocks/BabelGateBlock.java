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
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

public class BabelGateBlock extends HorizontalFacingBlock {
    private static final String[] rooms = {"chest_1","chest_2","chest_3","fountain_1","spawner_1","spawner_2","spawner_3","spawner_4","tree_1","tree_2"};
    public BabelGateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            Vec3i t = state.get(Properties.HORIZONTAL_FACING).getOpposite().getVector();
            DungeonAnchorEntity entity = (DungeonAnchorEntity) world.getBlockEntity(pos.add(t).add(t).add(t).add(t).down(2));

            if(Utils.canGenerateAnotherRoom(state.get(Properties.HORIZONTAL_FACING).getVector(),entity.getRelx(),entity.getRelz(),entity.getMaxx(),entity.getMaxz())) //if we are at an edge
            {
                if(world.getBlockState(pos.add(state.get(Properties.HORIZONTAL_FACING).getVector()).down(2)).getBlock()==Blocks.AIR)
                {
                    Vec3i vec = state.get(Properties.HORIZONTAL_FACING).getVector();

                    if(entity.getRelx()+vec.getX()==entity.getBossx() && entity.getRelz()+vec.getZ()==entity.getBossz())
                    {
                        Utils.spawnStructure((ServerWorld) world,pos.down(3).add(vec).add(vec).add(vec).add(vec),"boss_room");
                    }
                    else
                    {
                        Utils.spawnStructure((ServerWorld) world,pos.down(3).add(vec).add(vec).add(vec).add(vec),rooms[RandomUtils.nextInt(0,rooms.length)]);
                    }

                    DungeonAnchorEntity entityNew = (DungeonAnchorEntity) world.getBlockEntity(pos.add(vec).add(vec).add(vec).add(vec).down(2));
                    entityNew.setMaxx(entity.getMaxx());
                    entityNew.setMaxz(entity.getMaxz());
                    entityNew.setBossx(entity.getBossx());
                    entityNew.setBossz(entity.getBossz());
                    entityNew.setRelx(entity.getRelx()+vec.getX());
                    entityNew.setRelz(entity.getRelz()+vec.getZ());

                    System.out.println("bossx "+entityNew.getBossx());
                    System.out.println("bossz "+entityNew.getBossz());
                }
                player.playSound(SoundEvents.BLOCK_IRON_DOOR_OPEN,1,1);
                world.setBlockState(pos,Blocks.AIR.getDefaultState());
                world.setBlockState(pos.up(),Blocks.AIR.getDefaultState());
                world.setBlockState(pos.down(),Blocks.AIR.getDefaultState());

            }
            else
            {
                player.sendMessage(new LiteralText("There doesn't seem to be any more rooms past this gate...."));
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
