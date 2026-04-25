package com.respawntimer.mixin;

import com.respawntimer.TimerManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
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
    }
}
