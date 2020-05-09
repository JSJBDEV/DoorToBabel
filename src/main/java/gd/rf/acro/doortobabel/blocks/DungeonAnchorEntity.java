package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

public class DungeonAnchorEntity extends BlockEntity {
    private int relx;
    private int relz;
    private int maxx;
    private int maxz;
    private int bossx;
    private int bossz;
    public DungeonAnchorEntity() {
        super(DoorToBabel.DUNGEON_ANCHOR_ENTITY);

    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putInt("relx",relx);
        tag.putInt("relz",relz);
        tag.putInt("maxx",maxx);
        tag.putInt("maxz",maxz);
        tag.putInt("bossx",bossx);
        tag.putInt("bossz",bossz);

        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        relx=tag.getInt("relx");
        relz=tag.getInt("relz");
        maxx=tag.getInt("maxx");
        maxz=tag.getInt("maxz");
        bossx=tag.getInt("bossx");
        bossz=tag.getInt("bossz");
    }

    public int getMaxx() {
        return maxx;
    }

    public int getMaxz() {
        return maxz;
    }

    public int getRelx() {
        return relx;
    }

    public int getRelz() {
        return relz;
    }

    public int getBossx() {
        return bossx;
    }

    public int getBossz() {
        return bossz;
    }

    public void setMaxx(int maxx) {
        this.maxx = maxx;
        markDirty();
    }

    public void setMaxz(int maxz) {
        this.maxz = maxz;
        markDirty();
    }

    public void setRelx(int relx) {
        this.relx = relx;
        markDirty();
    }

    public void setRelz(int relz) {
        this.relz = relz;
        markDirty();
    }

    public void setBossx(int bossx) {
        this.bossx = bossx;
        markDirty();
    }

    public void setBossz(int bossz) {
        this.bossz = bossz;
        markDirty();
    }
    public void makeBossCoords()
    {
        setBossx(nextIntNotZero(maxx));
        setBossz(nextIntNotZero(maxz));
        markDirty();
    }

    private static int nextIntNotZero(int max)
    {
        int out = RandomUtils.nextInt(0,max*2+1)-max;
        if(out==0)
        {
            return nextIntNotZero(max);
        }
        return out;
    }

    public void setupFloor(World world, BlockPos pos,int max)
    {
        setRelz(0);
        setRelx(0);
        setMaxz(max);
        setMaxx(max);
        makeBossCoords();
        System.out.println("done");
    }
}
