package com.chunkflow.mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.SpawnGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChunkFlowMod implements ModInitializer {

	public static final String MOD_ID = "chunkflow";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private final CaveGlitchHandler glitchHandler = new CaveGlitchHandler();

	@Override
	public void onInitialize() {
		LOGGER.info("[ChunkFlow] Initializing chunk optimization routines...");

		// "Reduces hostile-entity overhead" — cancels spawns for the vanilla
		// MONSTER spawn group. This covers most hostile mobs (zombies,
		// skeletons, creepers, spiders, endermen, witches, etc). A handful of
		// hostile-leaning mobs live in other spawn groups (e.g. some
		// raid-related entities) and would need their own check if you want
		// them blocked too.
		ServerLivingEntityEvents.ALLOW_SPAWN.register((entity, world, spawnReason, entityType, spawnData) -> {
			return entity.getType().getSpawnGroup() != SpawnGroup.MONSTER;
		});

		ServerTickEvents.END_SERVER_TICK.register(glitchHandler::onEndTick);

		LOGGER.info("[ChunkFlow] Ready.");
	}
}
