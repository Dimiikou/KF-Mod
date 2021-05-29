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

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class Einzahlen extends CommandBase implements IClientCommand {

    private static AtomicBoolean start = new AtomicBoolean(false);
    private static int money = 0;
    private static final Pattern REMOVE_PATTERNS = Pattern.compile("^====== ([a-zA-Z0-9_]+)'s Statistiken ======$" +
            "|^  - Level: (\\d+)$" +
            "|^  - Status: ?(Premium|Spieler)$" +
            "|^  - Inventar: (\\d+)/(\\d+)$" +
            "|^  - Wanted Punkte: (\\d+)/69$" +
            "|^  - Geld: (\\d+)\\$$" +
            "|^  - Verwarnungen: ?(0|1|2)/3 Warns$" +
            "|^  - Zeit seit PayDay: (\\d+)/60 Minuten$" +
            "|^  - Experience: (\\d+)/(\\d+) Exp$" +
            "|^  - Fraktion: .+$" +
            "|^  - Haus: ([0-9, ]+)$" +
            "|^  - Beruf: ([a-zA-Z ]+)$" +
            "|^  - Votepoints: (\\d+)$" +
            "|^  - Treuebonus: (\\d+) Punkte$");


    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() { return "einzahlen"; }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) return;
        if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
            EntityPlayerSP p = Minecraft.getMinecraft().player;

            start.set(true);
            p.sendChatMessage("/stats");

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (money>0) {
                        p.sendChatMessage("/bank einzahlen " + money);
                        money=0;
                    } else {
                        p.sendMessage(ColorMessage.getMSGwithPrefix("Du hast kein Geld auf der Hand."));
                    }
                    start.set(false);
                }
            }, 1500);

            return;
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    /*
    Chat event
     */

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        if(!Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) return;
        String msg = e.getMessage().getUnformattedText();
        String[] args = msg.split(" ");
        EntityPlayerSP p = Minecraft.getMinecraft().player;
        if (start.get()) {
            if (msg.contains("- Geld:")) {
                for (int i=0;i<args.length;i++) {
                    if (args[i].contains("$")) {
                        money = Integer.valueOf(args[i].replace("$", ""));
                    }
                }
            }

            if (REMOVE_PATTERNS.matcher(msg).find()) {
                e.setCanceled(true);
            }
        }

    }
}
