package de.vepexlegit.labymodcheck;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "labymodcheck", version = "1.0")
public class LabyModCheck {
    public static Logger logger = LogManager.getLogger("LabyModCheck");

    public static String installed = EnumChatFormatting.GREEN + "[LabyMod Check] " + EnumChatFormatting.BOLD + "LabyMod Installed!";
    public static String notinstalled = EnumChatFormatting.GREEN + "[LabyMod Check] " + EnumChatFormatting.RED + "LabyMod Not Installed!";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onGuiMainMenuInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiMainMenu) {
            if (isLabyModActive()) {
                logger.info("LabyMod Installed!");
            } else {
                logger.info("LabyMod Not Installed!");
            }
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event) {
        if (event.world.isRemote && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (player.equals(FMLClientHandler.instance().getClient().thePlayer)) {
                if (isLabyModActive()) {
                    sendChatMessage(installed);
                } else {
                    sendChatMessage(notinstalled);
                }
            }
        }
    }

    public boolean isLabyModActive() {
        try {
            Class.forName("LabyMod");
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static void sendChatMessage(String message) {
        FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentText(message));
    }
}
