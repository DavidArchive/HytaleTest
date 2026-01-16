package com.allay.hytale.system;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.DropItemEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DropItemSystem extends EntityEventSystem<EntityStore, DropItemEvent.Drop> {

    public DropItemSystem() {
        super(DropItemEvent.Drop.class);
    }

    @Override
    public void handle(
            int index,
            @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
            @Nonnull Store<EntityStore> store,
            @Nonnull CommandBuffer<EntityStore> commandBuffer,
            @Nonnull DropItemEvent.Drop event) {

        Ref<EntityStore> entityStoreRef = archetypeChunk.getReferenceTo(index);

        Player player = store.getComponent(entityStoreRef, Player.getComponentType());
        if (player == null) return;

        player.sendMessage(Message.raw("dropped: " + event.getItemStack().getItemId()));
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }

}
