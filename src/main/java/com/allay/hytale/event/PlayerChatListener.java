package com.allay.hytale.event;

import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;

public class PlayerChatListener {

    public static void onPlayerChat(PlayerChatEvent event) {
        PlayerRef playerRef = event.getSender();
        String content = event.getContent();

        event.setContent(content
                .replaceAll("<3", "❤")
        );

        System.out.println(PlayerChatListener.class.getSimpleName() + ": " + playerRef.getUsername() + " wrote " + content);
    }

}
