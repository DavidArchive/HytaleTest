package com.allay.hytale.system;

import com.allay.hytale.config.BlockBreakConfig;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.protocol.SoundCategory;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Arrays;

public class BreakBlockSystem extends EntityEventSystem<EntityStore, BreakBlockEvent> {

    private final Config<BlockBreakConfig> config;

    public BreakBlockSystem(Config<BlockBreakConfig> config) {
        super(BreakBlockEvent.class);
        this.config = config;
    }

    /**
     * index = the row of the entity inside this chunk
     * <p>
     * Archetype = a unique combination of components<br>
     * e.g. Player + Health + Inventory
     * <p>
     * ArchetypeChunk = a chunk of entities that all share one archetype
     * <p>
     * Chunk rows:<br>
     * index 0 = entity A (Player, Health, Inventory)<br>
     * index 1 = entity B (Player, Health, Inventory)
     */

    @Override
    public void handle(
            int index,
            @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
            @Nonnull Store<EntityStore> store,
            @Nonnull CommandBuffer<EntityStore> commandBuffer,
            @Nonnull BreakBlockEvent event) {

        // get the reference inside this chunk
        Ref<EntityStore> entityStoreRef = archetypeChunk.getReferenceTo(index);

        // get the player component for this entity (guaranteed by the query)
        Player player = store.getComponent(entityStoreRef, Player.getComponentType());
        if (player == null) return;

        // get config and check block type
        BlockBreakConfig blockBreakConfig = config.get();
        if (Arrays.stream(blockBreakConfig.getAllowedBlocks()).noneMatch(id -> id.equalsIgnoreCase(event.getBlockType().getId()))) return;

        // get block location
        Vector3i blockLocation = event.getTargetBlock();

        // spawn particles (x3)
        for (int i = 0; i < 3; i++) {
            ParticleUtil.spawnParticleEffect(
                    blockBreakConfig.getParticleId(),
                    new Vector3d(blockLocation.x + i, blockLocation.y, blockLocation.z + i),
                    store
            );
        }

        // play sound
        SoundUtil.playSoundEvent2d(SoundEvent.getAssetMap().getIndex(blockBreakConfig.getSoundId()), SoundCategory.SFX, store);

        // send message
        player.sendMessage(Message.raw("ahhhh...").color(Color.RED).bold(true));
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }

}
