package com.respawntimer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
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

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRenderGui(RenderGuiEvent.Post event) {
        if (!TimerManager.isActive()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui) return;

        int seconds = TimerManager.getRemainingSeconds();
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String text = String.format("%d:%02d", minutes, secs);

        int color = TimerManager.isWarning() ? 0xFFFF3333 : 0xFFFFFFFF;
        int textWidth = mc.font.width(text);
        int screenWidth = event.getGuiGraphics().guiWidth();

        event.getGuiGraphics().drawString(mc.font, text, screenWidth - textWidth - 8, 8, color, true);
    }
}
