package de.Dimiikou.kerzakov.commands;

import de.Dimiikou.kerzakov.Kerzakov;
import de.Dimiikou.kerzakov.utils.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

public class Checkafk extends CommandBase implements IClientCommand {

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "checkafk";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) return;
        if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("unicacity.de")) {
            if (Kerzakov.AFK) {
                sender.sendMessage(ColorMessage.getMSGwithPrefix("Du bist AFK."));
                return;
            }

            sender.sendMessage(ColorMessage.getMSGwithPrefix("Du bist nicht AFK."));
            return;
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
