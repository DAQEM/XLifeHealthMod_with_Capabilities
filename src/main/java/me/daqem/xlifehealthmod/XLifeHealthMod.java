package me.daqem.xlifehealthmod;

import me.daqem.xlifehealthmod.capabilities.CapabilityEntityHealth;
import me.daqem.xlifehealthmod.capabilities.IEntityHealth;
import me.daqem.xlifehealthmod.modifiers.AutoApplyModifiers;
import me.daqem.xlifehealthmod.setup.ModSetup;
import me.daqem.xlifehealthmod.setup.ServerSetup;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(XLifeHealthMod.MODID)
public class XLifeHealthMod {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "xlifehealthmod";

    public static LazyOptional<IEntityHealth> getXLifeHandler(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY);
    }

    public XLifeHealthMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);

        MinecraftForge.EVENT_BUS.register(new AutoApplyModifiers());
        MinecraftForge.EVENT_BUS.register(new ServerSetup());
    }
}
