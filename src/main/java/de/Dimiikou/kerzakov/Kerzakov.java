package de.Dimiikou.kerzakov;

import de.Dimiikou.kerzakov.commands.Checkafk;
import de.Dimiikou.kerzakov.commands.Einzahlen;
import de.Dimiikou.kerzakov.commands.Reichensteuer;
import de.Dimiikou.kerzakov.commands.Trainingsmode;
import de.Dimiikou.kerzakov.commands.drugs.Eigenbedarf;
import de.Dimiikou.kerzakov.commands.drugs.Gifteigenbedarf;
import de.Dimiikou.kerzakov.commands.drugs.giftdrug.GiftGras;
import de.Dimiikou.kerzakov.commands.drugs.giftdrug.GiftKoks;
import de.Dimiikou.kerzakov.commands.drugs.giftdrug.GiftLSD;
import de.Dimiikou.kerzakov.commands.drugs.giftdrug.GiftMeth;
import de.Dimiikou.kerzakov.commands.drugs.selldrug.GrasCommand;
import de.Dimiikou.kerzakov.commands.drugs.selldrug.KoksCommand;
import de.Dimiikou.kerzakov.commands.drugs.selldrug.LSDCommand;
import de.Dimiikou.kerzakov.commands.drugs.selldrug.MethCommand;
import de.Dimiikou.kerzakov.commands.jobs.ADropMoney;
import de.Dimiikou.kerzakov.events.AFKEvent;
import de.Dimiikou.kerzakov.events.DeathEvent;
import de.Dimiikou.kerzakov.events.KarmaMessage;
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
    public static final String VERSION = "1.0.1";
    public static String prefix = "\247fKer\2471za\2474kov \2478\u25CF\2477 ";
    private static Logger logger;
    public static Minecraft minecraft = Minecraft.getMinecraft();

    public static File config;
    public static int discountDay = 10;

    public static boolean AFK = false;
    public static boolean DEAD = false;

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

        ClientCommandHandler.instance.registerCommand(new Checkafk());
        ClientCommandHandler.instance.registerCommand(new Reichensteuer());
        ClientCommandHandler.instance.registerCommand(new Einzahlen());
        ClientCommandHandler.instance.registerCommand(new ADropMoney());

        ClientCommandHandler.instance.registerCommand(new Trainingsmode());

        MinecraftForge.EVENT_BUS.register(new ADropMoney());
        MinecraftForge.EVENT_BUS.register(new Reichensteuer());
        MinecraftForge.EVENT_BUS.register(new Einzahlen());

        MinecraftForge.EVENT_BUS.register(new NametagChanger());
        MinecraftForge.EVENT_BUS.register(new AFKEvent());
        MinecraftForge.EVENT_BUS.register(new DeathEvent());
        MinecraftForge.EVENT_BUS.register(new KarmaMessage());
        MinecraftForge.EVENT_BUS.register(new Gifteigenbedarf());


    }
}
