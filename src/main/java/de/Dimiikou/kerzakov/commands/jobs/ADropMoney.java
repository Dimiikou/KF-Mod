package de.Dimiikou.kerzakov.commands.jobs;

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

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class ADropMoney extends CommandBase implements IClientCommand {

    private static AtomicBoolean started = new AtomicBoolean(false);
    private static Pattern PREVIOUS_BALANCE = Pattern.compile("^  Vorheriger Kontostand: (\\d+)\\$$");
    private static Pattern BANK_VALUE_CHANGED = Pattern.compile("^  Auszahlung: (-\\d+)\\$$" +
            "|^  Einzahlung: (\\+\\d+)\\$$");
    private static Pattern NEW_BALANCE = Pattern.compile("^  Neuer Kontostand: (\\d+)\\$$");

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "adropmoney";
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

            if (started.get()) {
                p.sendMessage(ColorMessage.getMSGwithPrefix("Der Befehl wird derzeit bereits ausgeführt."));
                return;
            }

            started.set(true);

            p.sendChatMessage("/bank abbuchen 15000");

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {

                    p.sendChatMessage("/dropmoney");

                    Timer r = new Timer();
                    r.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            p.sendChatMessage("/bank einzahlen 15000");

                            Timer d = new Timer();
                            d.schedule(new TimerTask() {
                                @Override
                                public void run() {

                                    started.set(false);
                                    p.sendMessage(ColorMessage.getMSGwithPrefix("Du kannst ab jetzt weitergehen."));

                                }
                            }, 200);
                        }
                    }, 1500);
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
        if(!Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) return;
        String msg = e.getMessage().getUnformattedText();
        EntityPlayerSP p = Minecraft.getMinecraft().player;
        if (started.get()) {

            if (msg.equalsIgnoreCase("=== Kontoauszug ===")
            || msg.equalsIgnoreCase("=================")
            || PREVIOUS_BALANCE.matcher(msg).find()
            || BANK_VALUE_CHANGED.matcher(msg).find()
            || NEW_BALANCE.matcher(msg).find()) {
                e.setCanceled(true);
                return;
            }
        }
    }

}
