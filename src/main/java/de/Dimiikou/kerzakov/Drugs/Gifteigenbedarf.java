package de.Dimiikou.kerzakov.Drugs;

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

import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class Gifteigenbedarf extends CommandBase implements IClientCommand {

    private boolean checkweed = false;
    private static Pattern DEAL_PATTERNS = Pattern.compile("^\\[Deal] (?:\\[UC])*([a-zA-Z0-9_]+) hat den Deal angenommen\\.$" +
            "|^\\[Deal] (?:\\[UC])*([a-zA-Z0-9_]+) hat den Deal abgelehnt\\.$");

    private String target = "";

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "gifteigenbedarf";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) return;
        if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
            if (args.length == 1) {
                target = args[0];
                EntityPlayerSP p = Minecraft.getMinecraft().player;

                p.sendChatMessage("/selldrug " + args[0] + " Koks " + Eigenbedarf.koksQuality.getId() + " " + Eigenbedarf.drugamount + " 1");
                checkweed = true;
            }

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
        if (!checkweed) return;
        if (DEAL_PATTERNS.matcher(msg).find()) {
            p.sendChatMessage("/selldrug " + target + " Gras " + Eigenbedarf.grasQuality.getId() + " " + Eigenbedarf.drugamount + " 1");
            checkweed = false;
        }
    }
}