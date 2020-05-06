package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;

public class AqueductBlockEntity extends BlockEntity {
    private int speed;
    public AqueductBlockEntity() {
        super(DoorToBabel.AQUEDUCT_ENTITY);
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
        markDirty();
    }

    public int getSpeed() {
        return speed;
    }
}
