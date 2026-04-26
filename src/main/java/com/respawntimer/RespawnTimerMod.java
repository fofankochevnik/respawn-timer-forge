package com.respawntimer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(RespawnTimerMod.MODID)
public class RespawnTimerMod {

    public static final String MODID = "respawn_timer";

    public RespawnTimerMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (FMLEnvironment.dist != Dist.CLIENT) return;
        if (!(event.getEntity() instanceof net.minecraft.world.entity.player.Player)) return;
        ClientHelper.onDeath(event.getEntity().getUUID());
    }
}
