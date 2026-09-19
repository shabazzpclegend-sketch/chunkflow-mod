package com.chunkflow.mod;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

/**
 * Places a sign carrying a Caesar-cipher clue near a glitch site. The
 * plaintext below is the actual source of truth — only the shifted
 * ciphertext ever gets written into the world, so the plaintext never
 * ships in any player-visible form.
 *
 * Sign text is set via the vanilla `/data merge block` command run through
 * the server console command source, rather than the internal sign
 * block-entity API — that internal API's exact method names have shifted
 * between recent versions, while the command syntax is stable and
 * documented, so this is the safer bet if you're chasing a compile error.
 */
public class CipherSignPlacer {

	private static final int SHIFT = 7;
	private static final String[] PLAINTEXT_LINES = {
		"IT WAS NEVER SUPPOSED",
		"TO GROW THIS TALL"
	};

	public static void placeNearby(ServerWorld world, BlockPos near) {
		BlockPos signPos = near.add(1, 1, 1);

		world.setBlockState(signPos, Blocks.OAK_SIGN.getDefaultState(), Block.NOTIFY_LISTENERS);

		String line1 = CaesarCipher.encode(PLAINTEXT_LINES[0], SHIFT);
		String line2 = CaesarCipher.encode(PLAINTEXT_LINES[1], SHIFT);

		String command = String.format(
			"data merge block %d %d %d {front_text:{messages:['\"%s\"','\"%s\"','\"\"','\"\"']}}",
			signPos.getX(), signPos.getY(), signPos.getZ(),
			escape(line1), escape(line2)
		);

		world.getServer().getCommandManager()
			.executeWithPrefix(world.getServer().getCommandSource(), command);
	}

	private static String escape(String s) {
		return s.replace("\\", "\\\\").replace("\"", "\\\"");
	}
}
