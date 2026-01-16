package com.allay.hytale;

import com.allay.hytale.command.HelloCommand;
import com.allay.hytale.config.BlockBreakConfig;
import com.allay.hytale.system.BreakBlockSystem;
import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;

import javax.annotation.Nonnull;

public class Plugin extends JavaPlugin {

    private final Config<BlockBreakConfig> config;

    public Plugin(@Nonnull JavaPluginInit init) {
        super(init);

        this.config = this.withConfig("BlockBreakConfig", BlockBreakConfig.CODEC);
    }

    @Override
    protected void setup() {
        this.config.save();

        CommandRegistry commandRegistry = this.getCommandRegistry();
        commandRegistry.registerCommand(new HelloCommand());

        ComponentRegistryProxy<EntityStore> entityStore = this.getEntityStoreRegistry();
        entityStore.registerSystem(new BreakBlockSystem(config));
    }

}
