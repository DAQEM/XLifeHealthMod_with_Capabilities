package me.daqem.xlifehealthmod.capabilities;

import me.daqem.xlifehealthmod.XLifeHealthMod;
import me.daqem.xlifehealthmod.modifiers.XLifeModifiers;
import me.daqem.xlifehealthmod.utils.SendMessage;
import net.minecraft.block.SoundType;
import net.minecraft.client.audio.Sound;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class HealthEventHandler {

    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getObject();
            EntityHealthProvider provider = new EntityHealthProvider();
            event.addCapability(new ResourceLocation(XLifeHealthMod.MODID, "health"), provider);
            event.addListener(provider::invalidate);
            player.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(h -> {
                XLifeModifiers.applyMaxHealthModifier(player, h.getHealth() - 20);
            });
        }
    }

    public static void onPlayerDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(h -> {
                int health = h.getHealth() + 2;
                h.setHealth(health);
            });
            if (player.getServer() != null) {
                for (PlayerEntity players : player.getServer().getPlayerList().getPlayers()) {
                    player.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(h -> {
                        players.getEntityWorld().getWorld().playSound(null, players.getPosition(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.HOSTILE, 1F, 1F);
                        XLifeHealthMod.LOGGER.info(players.getGameProfile().getName());
                        if (h.getHealth() == 4) {
                            SendMessage.sendMessage(players, TextFormatting.RED + player.getGameProfile().getName() + " has 9 lives remaining...");
                        }
                        else if (h.getHealth() == 6) {
                            SendMessage.sendMessage(players, TextFormatting.RED + player.getGameProfile().getName() + " has 8 lives remaining...");
                        }
                        else if (h.getHealth() == 8) {
                            SendMessage.sendMessage(players, TextFormatting.RED + player.getGameProfile().getName() + " has 7 lives remaining...");
                        }
                        else if (h.getHealth() == 10) {
                            SendMessage.sendMessage(players, TextFormatting.RED + player.getGameProfile().getName() + " has 6 lives remaining...");
                        }
                        else if (h.getHealth() == 12) {
                            SendMessage.sendMessage(players, TextFormatting.RED + player.getGameProfile().getName() + " has 5 lives remaining...");
                        }
                        else if (h.getHealth() == 14) {
                            SendMessage.sendMessage(players, TextFormatting.RED + player.getGameProfile().getName() + " has 4 lives remaining...");
                        }
                        else if (h.getHealth() == 16) {
                            SendMessage.sendMessage(players, TextFormatting.RED + player.getGameProfile().getName() + " has 3 lives remaining...");
                        }
                        else if (h.getHealth() == 18) {
                            SendMessage.sendMessage(players, TextFormatting.RED + player.getGameProfile().getName() + " has 2 lives remaining...");
                        }
                        else if (h.getHealth() == 20) {
                            SendMessage.sendMessage(players, TextFormatting.RED + player.getGameProfile().getName() + " has 1 life remaining...");
                        } else {
                            SendMessage.sendMessage(players, TextFormatting.RED + "It's is  game over for " + player.getGameProfile().getName() + "...");
                            player.getServer().getCommandManager().handleCommand(player.getServer().getCommandSource().withPermissionLevel(4), "/ban " + player.getGameProfile().getName() + " Game Over!");
                            player.setGameType(GameType.SPECTATOR);
                        }
                    });
                }
            }
        }
    }

    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        PlayerEntity player = event.getPlayer();
        player.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(h -> {
            XLifeModifiers.applyMaxHealthModifier(player, h.getHealth() - 20);
        });
        if (player.getHealth() >= player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }
    }

    public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        PlayerEntity player = event.getPlayer();
        player.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(h -> {
            XLifeModifiers.applyMaxHealthModifier(player, h.getHealth() - 20);
        });
    }

    public static void onDeath(PlayerEvent.Clone event) {
        LazyOptional<IEntityHealth> capability = event.getOriginal().getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY);
        capability.ifPresent(oldStore -> {
            event.getEntityPlayer().getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(newStore -> {
                newStore.copyForRespawn((DefaultEntityHealth) oldStore);
            });
        });
    }
}
