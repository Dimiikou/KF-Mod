package de.Dimiikou.kerzakov.commands.drugs;

import de.fuzzlemann.ucutils.common.udf.data.faction.drug.DrugQuality;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Timer;
import java.util.TimerTask;

@SideOnly(Side.CLIENT)
public class Eigenbedarf extends CommandBase implements IClientCommand {
    public static int drugamount = 15;
    public static DrugQuality koksQuality = DrugQuality.GOOD;
    public static DrugQuality grasQuality = DrugQuality.GOOD;

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "eigenbedarf";
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
            p.sendChatMessage("/dbank get Gras " + drugamount + " " + grasQuality.getId());

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendChatMessage("/dbank get Koks " + drugamount + " " + koksQuality.getId());

                }
            }, 1000);
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
