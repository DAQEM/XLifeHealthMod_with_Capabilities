package me.daqem.xlifehealthmod.setup;

import me.daqem.xlifehealthmod.commands.XLifeCommands;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber
public class ServerSetup {

    @SubscribeEvent
    public void serverStarting(FMLServerStartingEvent event) {
        XLifeCommands.register(event.getCommandDispatcher());
    }
}
