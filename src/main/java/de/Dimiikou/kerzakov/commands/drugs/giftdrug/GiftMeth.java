package de.Dimiikou.kerzakov.commands.drugs.giftdrug;

import com.google.common.collect.Lists;
import de.Dimiikou.kerzakov.utils.ColorMessage;
import de.fuzzlemann.ucutils.common.udf.data.faction.drug.DrugQuality;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GiftMeth extends CommandBase implements IClientCommand {

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "giftmeth";
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

            if (args.length == 3) {
                String player = args[0];
                int amount = Integer.valueOf(args[1]);

                if (!(Integer.valueOf(args[2]) >= 0) || !(Integer.valueOf(args[2]) < 4)) {
                    p.sendMessage(ColorMessage.getMSGwithPrefix("§7Bitte gib einen der folgenen Reinheitsgrade an:"));
                    p.sendMessage(ColorMessage.getMSG(" §8\u25CF §e0"));
                    p.sendMessage(ColorMessage.getMSG(" §8\u25CF §e1"));
                    p.sendMessage(ColorMessage.getMSG(" §8\u25CF §e2"));
                    p.sendMessage(ColorMessage.getMSG(" §8\u25CF §e3"));
                    return;
                }

                DrugQuality drugQuality = DrugQuality.HIGH;
                if (Integer.valueOf(args[2]) == 0) drugQuality = DrugQuality.HIGH;
                if (Integer.valueOf(args[2]) == 1) drugQuality = DrugQuality.GOOD;
                if (Integer.valueOf(args[2]) == 2) drugQuality = DrugQuality.MEDIUM;
                if (Integer.valueOf(args[2]) == 3) drugQuality = DrugQuality.BAD;

                if (!(amount > 0)) {
                    p.sendMessage(ColorMessage.getMSGwithPrefix("Du kannst erst Drogen ab einem Gramm verkaufen."));
                    return;
                }

                p.sendChatMessage("/selldrug " + player + " Meth " + drugQuality.getId() + " " + amount + " 1");

            } else {
                p.sendMessage(ColorMessage.getMSGwithPrefix("/giftmeth §7<§eSpielername§7> <§eMenge§7> <§eReinheit§7>§8."));
            }

            return;
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    public List getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        NetHandlerPlayClient connection = Minecraft.getMinecraft().player.connection;
        List<NetworkPlayerInfo> playerInfo = new ArrayList(connection.getPlayerInfoMap());
        List<String> playerList = Lists.<String>newArrayList();
        if (args.length == 1) {
            for (int i = 0; i < playerInfo.size(); ++i) {
                if (i < playerInfo.size()) {
                    playerList.add(playerInfo.get(i).getGameProfile().getName());
                }
            }
            return CommandBase.getListOfStringsMatchingLastWord(args, playerList);
        }
        if (args.length == 3) {
            ArrayList<String> list = new ArrayList<>();
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("3");
            return list;
        }
        return new ArrayList();
    }

}
