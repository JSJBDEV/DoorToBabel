package gd.rf.acro.doortobabel.world;

import gd.rf.acro.doortobabel.DoorToBabel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.awt.*;

public class BabelDimension extends Dimension {
    public BabelDimension(World world, DimensionType type) {
        super(world, type, 0);
    }



    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        return DoorToBabel.BABEL_CHUNK_GENERATOR.create(world,
                BiomeSourceType.FIXED.
                        applyConfig(BiomeSourceType.FIXED.getConfig(world.getLevelProperties())
                                .setBiome(Biomes.PLAINS)),DoorToBabel.BABEL_CHUNK_GENERATOR.createSettings());
    }

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos chunkPos, boolean checkMobSpawnValidity) {
        return null;
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int x, int z, boolean checkMobSpawnValidity) {
        return null;
    }

    @Override
    public float getSkyAngle(long timeOfDay, float tickDelta) {
        return 0;
    }

    @Override
    public boolean hasVisibleSky() {
        return false;
    }

    @Override
    public Vec3d getFogColor(float skyAngle, float tickDelta) {
        return new Vec3d(0,0,0);
    }

    @Override
    public boolean canPlayersSleep() {
        return false;
    }

    @Override
    public boolean isFogThick(int x, int z) {
        return false;
    }

    @Override
    public DimensionType getType() {
        return DoorToBabel.BABEL;
    }
}
