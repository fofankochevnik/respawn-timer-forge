package com.respawntimer;

import net.minecraft.client.Minecraft;
import java.util.UUID;

public class ClientHelper {
    public static void onDeath(UUID uuid) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.player.getUUID().equals(uuid)) {
            TimerManager.startTimer();
        }
    }
}
