package de.Dimiikou.kerzakov.commands;

import de.Dimiikou.kerzakov.utils.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.xml.Atom;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class Reichensteuer  extends CommandBase implements IClientCommand {

    private static int i = 0;
    private static AtomicBoolean started = new AtomicBoolean(false);

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "reichensteuer";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) return;
        if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
            EntityPlayerSP p = Minecraft.getMinecraft().player;
            if (started.get()) return;

            started.set(true);

            p.sendChatMessage("/bank info");

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (i > 0) {
                        p.sendChatMessage("/bank abbuchen " + i);
                    } else {
                        p.sendMessage(ColorMessage.getMSGwithPrefix("Dein Kontostand ist bereits auf §e100.000§7."));
                    }

                }
            }, 1500);
            return;
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if (!Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) return;
        String msg = e.getMessage().getUnformattedText();
        EntityPlayerSP p = Minecraft.getMinecraft().player;
        if (!started.get()) return;

        if (msg.equalsIgnoreCase("Fehler: Gib ein Betrag an.")) {
            started.set(false);
        }
        if (msg.contains("Ihr Bankguthaben betr\u00e4gt:")) {
            String[] s = msg.split(" ");
            String es = s[3];
            es = es.replace("+", "").replace("$", "");
            i = Integer.valueOf(es) - 100000;
            started.set(false);
            e.setCanceled(true);
        }
        if (msg.equalsIgnoreCase("=== Kontoauszug ===")
                || msg.equalsIgnoreCase("=================")) {
            e.setCanceled(true);
        }

    }
}
