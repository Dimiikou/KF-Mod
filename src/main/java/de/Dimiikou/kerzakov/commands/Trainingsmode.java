package de.Dimiikou.kerzakov.commands;

import de.Dimiikou.kerzakov.events.NametagChanger;
import de.Dimiikou.kerzakov.utils.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Trainingsmode extends CommandBase implements IClientCommand {

    public static AtomicBoolean trainingsmode = new AtomicBoolean(false);

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "trainingsmode";
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

            if (args.length==1) {
                if (args[0].equalsIgnoreCase("on")) {
                    trainingsmode.set(true);
                    p.sendMessage(ColorMessage.getMSGwithPrefix("Der Trainingsmode ist nun aktiviert"));
                    NametagChanger.refreshAllDisplayNames();
                    return;
                } else if (args[0].equalsIgnoreCase("off")) {
                    trainingsmode.set(false);
                    p.sendMessage(ColorMessage.getMSGwithPrefix("Der Trainingsmode ist nun deaktiviert"));
                    NametagChanger.refreshAllDisplayNames();
                    return;
                }
                p.sendMessage(ColorMessage.getMSGwithPrefix("/trainingsmode §7<§eon§8|§eoff§7>§8."));
            } else {
                p.sendMessage(ColorMessage.getMSGwithPrefix("/trainingsmode §7<§eon§8|§eoff§7>§8."));
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    public List getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        if (args.length == 1) { return Arrays.asList("on", "off"); }

        return new ArrayList();
    }
}
