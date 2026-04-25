package com.respawntimer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.client.event.RegisterGuiLayersEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

@Mod(RespawnTimerMod.MODID)
public class RespawnTimerMod {

    public static final String MODID = "respawn_timer";

    public RespawnTimerMod(IEventBus modEventBus) {
        modEventBus.addListener(this::registerGuiLayers);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
            ResourceLocation.fromNamespaceAndPath(MODID, "timer_hud"),
            new TimerHudOverlay()
        );
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
