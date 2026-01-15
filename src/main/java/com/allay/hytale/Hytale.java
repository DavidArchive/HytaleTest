package com.allay.hytale;

import com.allay.hytale.command.HelloCommand;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public class Hytale extends JavaPlugin {

    public Hytale(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        CommandRegistry commandRegistry = this.getCommandRegistry();
        commandRegistry.registerCommand(new HelloCommand());
    }

}
