package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;

public class WaterWheelBlockEntity extends BlockEntity {
    private int speed = 0;
    public WaterWheelBlockEntity() {
        super(DoorToBabel.WATERWHEEL_BLOCK_ENTITY);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        speed=tag.getInt("speed");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("speed",speed);
        return tag;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
