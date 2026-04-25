package com.respawntimer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraft.client.Minecraft;

@Mod(RespawnTimerMod.MODID)
public class RespawnTimerMod {

    public static final String MODID = "respawn_timer";

    public RespawnTimerMod(IEventBus modEventBus) {
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::registerOverlays);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void onClientSetup(FMLClientSetupEvent event) {}

    private void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("timer_hud", (gui, guiGraphics, deltaTracker) -> {
            if (!TimerManager.isActive()) return;
            Minecraft mc = Minecraft.getInstance();
            if (mc.options.hideGui) return;

            int seconds = TimerManager.getRemainingSeconds();
            int minutes = seconds / 60;
            int secs = seconds % 60;
            String text = String.format("%d:%02d", minutes, secs);

            int color = TimerManager.isWarning() ? 0xFFFF3333 : 0xFFFFFFFF;
            int textWidth = mc.font.width(text);
            int screenWidth = guiGraphics.guiWidth();

            guiGraphics.drawString(mc.font, text, screenWidth - textWidth - 8, 8, color, true);
        });
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
