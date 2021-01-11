package me.daqem.xlifehealthmod.setup;

import me.daqem.xlifehealthmod.XLifeHealthMod;
import me.daqem.xlifehealthmod.capabilities.CapabilityEntityHealth;
import me.daqem.xlifehealthmod.capabilities.HealthEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = XLifeHealthMod.MODID)
public class ModSetup {

    public static void init(final FMLCommonSetupEvent event) {
        CapabilityEntityHealth.register();

        MinecraftForge.EVENT_BUS.addListener(HealthEventHandler::onAttachCapabilitiesEvent);
        MinecraftForge.EVENT_BUS.addListener(HealthEventHandler::onDimensionChange);
        MinecraftForge.EVENT_BUS.addListener(HealthEventHandler::onPlayerDeath);
        MinecraftForge.EVENT_BUS.addListener(HealthEventHandler::onDeath);
        MinecraftForge.EVENT_BUS.addListener(HealthEventHandler::onRespawn);
    }
}
