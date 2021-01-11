package me.daqem.xlifehealthmod.modifiers;

import me.daqem.xlifehealthmod.capabilities.CapabilityEntityHealth;
import me.daqem.xlifehealthmod.utils.SendMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AutoApplyModifiers {

    private int count;

    private int getCount() {
        return count;
    }

    private void setCount(int count) {
        this.count = count;
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        player.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(h -> {
            if (h.getHealth() == 0) {
                if (!player.inventory.isEmpty()) {
                    h.setHealth((int) player.getMaxHealth());
                    XLifeModifiers.applyMaxHealthModifier(player, h.getHealth() - 20);
                } else {
                    int health = h.getHealth() + 2;
                    h.setHealth(health);
                    player.setHealth(player.getMaxHealth());
                    SendMessage.sendMessage(player, TextFormatting.YELLOW + "=====================================");
                    SendMessage.sendMessage(player, " ");
                    SendMessage.sendMessage(player, TextFormatting.GOLD + "Welcome to X Life!");
                    SendMessage.sendMessage(player, " ");
                    SendMessage.sendMessage(player, TextFormatting.WHITE + "You'll start off with only one heart.");
                    SendMessage.sendMessage(player, TextFormatting.WHITE + "Every time you die, you gain a heart.");
                    SendMessage.sendMessage(player, TextFormatting.WHITE + "When you die with 10 hearts, it is game over for you.");
                    SendMessage.sendMessage(player, " ");
                    SendMessage.sendMessage(player, TextFormatting.GOLD + "Good Luck!");
                    SendMessage.sendMessage(player, " ");
                    SendMessage.sendMessage(player, TextFormatting.YELLOW + "=====================================");
                }
                XLifeModifiers.applyMaxHealthModifier(player, h.getHealth() - 20);
            }
        });
    }
//    @SubscribeEvent
//    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
//        PlayerEntity player = (PlayerEntity) event.player.getEntity();
//        if (event.phase == TickEvent.Phase.START && event.side.isServer()) {
//            if (getCount() == 100) {
//                player.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(h -> {
//                    XLifeModifiers.applyMaxHealthModifier(player, h.getHealth() - 20);
//                });
//                setCount(0);
//            } else {
//                setCount(getCount() + 1);
//            }
//        }
//    }

}
