package de.Dimiikou.kerzakov;

import de.Dimiikou.kerzakov.Drugs.Eigenbedarf;
import de.Dimiikou.kerzakov.Drugs.Gifteigenbedarf;
import de.Dimiikou.kerzakov.Drugs.giftdrug.GiftGras;
import de.Dimiikou.kerzakov.Drugs.giftdrug.GiftKoks;
import de.Dimiikou.kerzakov.Drugs.giftdrug.GiftLSD;
import de.Dimiikou.kerzakov.Drugs.giftdrug.GiftMeth;
import de.Dimiikou.kerzakov.Drugs.selldrug.GrasCommand;
import de.Dimiikou.kerzakov.Drugs.selldrug.KoksCommand;
import de.Dimiikou.kerzakov.Drugs.selldrug.LSDCommand;
import de.Dimiikou.kerzakov.Drugs.selldrug.MethCommand;
import de.Dimiikou.kerzakov.events.NametagChanger;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Kerzakov.MODID, name = Kerzakov.NAME, version = Kerzakov.VERSION, clientSideOnly = true)
@SideOnly(Side.CLIENT)
public class Kerzakov {
    public static final String MODID = "kerzakov";
    public static final String NAME = "Kerzakov";
    public static final String VERSION = "1.0.0";
    public static String prefix = "\247fKer\2471za\2474kov \2478\u25CF\2477 ";
    private static Logger logger;
    public static Minecraft minecraft = Minecraft.getMinecraft();

    public static File config;
    public static int discountDay = 10;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ConfigManager.sync(MODID, Config.Type.INSTANCE);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new GrasCommand());
        ClientCommandHandler.instance.registerCommand(new KoksCommand());
        ClientCommandHandler.instance.registerCommand(new MethCommand());
        ClientCommandHandler.instance.registerCommand(new LSDCommand());

        ClientCommandHandler.instance.registerCommand(new GiftGras());
        ClientCommandHandler.instance.registerCommand(new GiftKoks());
        ClientCommandHandler.instance.registerCommand(new GiftMeth());
        ClientCommandHandler.instance.registerCommand(new GiftLSD());

        ClientCommandHandler.instance.registerCommand(new Eigenbedarf());
        ClientCommandHandler.instance.registerCommand(new Gifteigenbedarf());

        MinecraftForge.EVENT_BUS.register(new NametagChanger());
    }
}
