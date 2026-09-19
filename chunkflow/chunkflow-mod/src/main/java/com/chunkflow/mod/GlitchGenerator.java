package com.chunkflow.mod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

/**
 * Code-generated "unnatural" cluster of oversized, mismatched tree columns.
 * Seeded off the chunk position so a given chunk always glitches the same
 * way (handy if you want to screenshot/tune it, or players compare notes).
 */
public class GlitchGenerator {

	private static final Block[] LOGS = {
		Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.DARK_OAK_LOG, Blocks.BIRCH_LOG
	};
	private static final Block[] LEAVES = {
		Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES
	};

	public static void generate(ServerWorld world, BlockPos center) {
		long seed = ChunkPos.toLong(new ChunkPos(center));
		Random random = Random.create(seed);

		int stacks = 6 + random.nextInt(6); // 6-11 columns
		for (int i = 0; i < stacks; i++) {
			int dx = random.nextInt(13) - 6; // -6..6
			int dz = random.nextInt(13) - 6;
			BlockPos base = center.add(dx, 0, dz);

			int height = 15 + random.nextInt(26); // 15-40 tall, absurdly oversized
			// 1-in-10 columns lie sideways instead of standing up straight
			Direction.Axis axis = random.nextInt(10) == 0
				? (random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z)
				: Direction.Axis.Y;

			Block log = LOGS[random.nextInt(LOGS.length)];
			BlockState logState = log.getDefaultState().with(Properties.AXIS, axis);

			for (int y = 0; y < height; y++) {
				world.setBlockState(base.up(y), logState, Block.NOTIFY_LISTENERS);
			}

			Block leaves = LEAVES[random.nextInt(LEAVES.length)];
			BlockState leafState = leaves.getDefaultState().with(Properties.PERSISTENT, true);

			// A ragged, partly-missing leaf cap — floats a block or two off
			// the top of the column rather than sitting neatly on it.
			int leafRadius = 2;
			for (int lx = -leafRadius; lx <= leafRadius; lx++) {
				for (int lz = -leafRadius; lz <= leafRadius; lz++) {
					if (random.nextInt(3) == 0) {
						continue;
					}
					BlockPos leafPos = base.add(lx, height + random.nextInt(3), lz);
					world.setBlockState(leafPos, leafState, Block.NOTIFY_LISTENERS);
				}
			}
		}
	}
}
