package gd.rf.acro.doortobabel.world;

import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;

public class BabelChunkGenerator extends ChunkGenerator<ChunkGeneratorConfig> {
    public BabelChunkGenerator(IWorld world, BiomeSource biomeSource, ChunkGeneratorConfig config) {
        super(world, biomeSource, config);
    }

    @Override
    public void buildSurface(ChunkRegion chunkRegion, Chunk chunk) {

    }

    @Override
    public int getSpawnHeight() {
        return 200;
    }

    @Override
    public void populateNoise(IWorld world, Chunk chunk) {

    }

    @Override
    public int getHeightOnGround(int x, int z, Heightmap.Type heightmapType) {
        return 0;
    }

    @Override
    public void generateFeatures(ChunkRegion region) {
        //no features
    }
}
