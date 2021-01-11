package me.daqem.xlifehealthmod.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityEntityHealth {

    @CapabilityInject(IEntityHealth.class)
    public static Capability<IEntityHealth> ENTITY_HEALTH_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IEntityHealth.class, new Storage(), DefaultEntityHealth::new);
    }

    public static class Storage implements Capability.IStorage<IEntityHealth> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<IEntityHealth> capability, IEntityHealth instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("health", instance.getHealth());
            return tag;
        }

        @Override
        public void readNBT(Capability<IEntityHealth> capability, IEntityHealth instance, Direction side, INBT nbt) {
            int health = ((CompoundNBT) nbt).getInt("health");
            instance.setHealth(health);
        }
    }
}
