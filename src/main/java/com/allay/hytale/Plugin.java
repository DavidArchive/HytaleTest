package com.allay.hytale;

import com.allay.hytale.command.HelloCommand;
import com.allay.hytale.config.BlockBreakConfig;
import com.allay.hytale.event.PlayerChatListener;
import com.allay.hytale.system.BreakBlockSystem;
import com.allay.hytale.system.ChangeGameModeSystem;
import com.allay.hytale.system.DropItemSystem;
import com.allay.hytale.system.PlaceBlockSystem;
import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;

import javax.annotation.Nonnull;

public class Plugin extends JavaPlugin {

    private final Config<BlockBreakConfig> blockBreakConfig;

    public Plugin(@Nonnull JavaPluginInit init) {
        super(init);

        this.blockBreakConfig = this.withConfig(BlockBreakConfig.class.getSimpleName(), BlockBreakConfig.CODEC);
    }

    @Override
    protected void setup() {
        this.blockBreakConfig.save();

        CommandRegistry commandRegistry = this.getCommandRegistry();
        commandRegistry.registerCommand(new HelloCommand());

        EventRegistry eventRegistry = this.getEventRegistry();
        eventRegistry.registerGlobal(PlayerChatEvent.class, PlayerChatListener::onPlayerChat);

        ComponentRegistryProxy<EntityStore> entityStore = this.getEntityStoreRegistry();
        entityStore.registerSystem(new BreakBlockSystem(blockBreakConfig));
        entityStore.registerSystem(new ChangeGameModeSystem());
        entityStore.registerSystem(new DropItemSystem());
        entityStore.registerSystem(new PlaceBlockSystem());
    }

}
