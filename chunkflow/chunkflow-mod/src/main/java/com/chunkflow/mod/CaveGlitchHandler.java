package com.chunkflow.mod;

import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

/**
 * Watches players and, when one is underground with no sky above them and
 * hasn't tripped this chunk's marker before, triggers the glitch generation
 * and cipher signs once. A STRUCTURE_VOID block at the chunk's center (an
 * invisible, no-collision marker) is used to remember "already glitched"
 * chunks — this survives world saves/restarts for free, no custom
 * PersistentState bookkeeping needed.
 */
public class CaveGlitchHandler {

	private static final int SCAN_INTERVAL_TICKS = 20; // once per second
	private static final int CAVE_Y_THRESHOLD = 40; // below this + no sky visible = "in a cave"
	private static final int MARKER_Y = 0;

	private int tickCounter = 0;

	public void onEndTick(MinecraftServer server) {
		tickCounter++;
		if (tickCounter < SCAN_INTERVAL_TICKS) {
			return;
		}
		tickCounter = 0;

		for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
			ServerWorld world = player.getServerWorld();
			BlockPos pos = player.getBlockPos();

			if (pos.getY() >= CAVE_Y_THRESHOLD) {
				continue;
			}
			if (world.isSkyVisible(pos)) {
				continue;
			}

			ChunkPos chunkPos = new ChunkPos(pos);
			BlockPos marker = new BlockPos(chunkPos.getStartX() + 8, MARKER_Y, chunkPos.getStartZ() + 8);

			if (world.getBlockState(marker).isOf(Blocks.STRUCTURE_VOID)) {
				continue; // this chunk already got its glitch
			}

			world.setBlockState(marker, Blocks.STRUCTURE_VOID.getDefaultState(), Block.NOTIFY_LISTENERS);

			GlitchGenerator.generate(world, pos);
			CipherSignPlacer.placeNearby(world, pos);

			ChunkFlowMod.LOGGER.info("[ChunkFlow] Chunk realignment triggered near {}", pos);
		}
	}
}
