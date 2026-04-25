package com.respawntimer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraft.client.Minecraft;

@Mod(RespawnTimerMod.MODID)
public class RespawnTimerMod {

    public static final String MODID = "respawn_timer";

    public RespawnTimerMod(IEventBus modEventBus) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof net.minecraft.world.entity.player.Player) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && event.getEntity().getUUID().equals(mc.player.getUUID())) {
                TimerManager.startTimer();
            }
        }
    }
}
