package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import gd.rf.acro.doortobabel.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

public class SpawnerBlock extends Block {
    public SpawnerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        SkeletonEntity boss = new SkeletonEntity(EntityType.SKELETON,world);
        boss.teleport(pos.getX(),pos.getY(),pos.getZ());
        boss.equipStack(EquipmentSlot.HEAD,new ItemStack(Items.PLAYER_HEAD));
        boss.equipStack(EquipmentSlot.CHEST,new ItemStack(Items.DIAMOND_CHESTPLATE));
        boss.equipStack(EquipmentSlot.LEGS,new ItemStack(Items.DIAMOND_LEGGINGS));
        boss.equipStack(EquipmentSlot.FEET,new ItemStack(Items.DIAMOND_BOOTS));
        if(RandomUtils.nextBoolean())
        {
            boss.equipStack(EquipmentSlot.MAINHAND,new ItemStack(Items.BOW));
        }
        else
        {
            boss.equipStack(EquipmentSlot.MAINHAND,new ItemStack(Items.DIAMOND_SWORD));
        }
        boss.equipStack(EquipmentSlot.OFFHAND,new ItemStack(DoorToBabel.STAIR_KEY));
        boss.setEquipmentDropChance(EquipmentSlot.OFFHAND,1);
        boss.setCustomName(new LiteralText(Utils.compoundName()).formatted(Formatting.AQUA));
        boss.setCustomNameVisible(true);
        boss.setHealth(300);
        world.spawnEntity(boss);

    }
}
