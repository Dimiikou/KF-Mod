package de.Dimiikou.kerzakov.events;

import de.Dimiikou.kerzakov.Kerzakov;
import de.Dimiikou.kerzakov.config.KerzakovConfig;
import de.Dimiikou.kerzakov.utils.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class KarmaMessage {

    static int karmaa = 7;
    static int karma = 0;
    static boolean karmacheck = false;
    private static Pattern KARMA_PATTERN = Pattern.compile("^\\[Karma] Du hast ein Karma von ?(-|\\+)(\\d+).$");


    @SubscribeEvent
    public static void onChat(ClientChatReceivedEvent e) {
        if (KerzakovConfig.ChangeKarmaMessage) {
            String msg = e.getMessage().getUnformattedText();
            String[] s = msg.split(" ");
            EntityPlayerSP p = Minecraft.getMinecraft().player;
            if (Kerzakov.AFK || Kerzakov.DEAD) return;

            if (msg.contains("[Karma]") && !msg.contains("von")) {
                p.sendChatMessage("/karma");

                karmacheck=true;
                e.setMessage(null);

                karma = Integer.parseInt(s[1].replace("+", ""));

            }

            if (KARMA_PATTERN.matcher(msg).find()) {
                if (karmacheck) {
                    karmaa = Integer.valueOf(s[6].replace(".", "").replace("+", ""));
                    if (karmaa >= 0) {
                        e.setMessage(ColorMessage.getMSG("§8[§9Karma§8] §b" + karma + " Karma §8(§b+" + karmaa + "§8/§b100§8)"));
                    } else {
                        e.setMessage(ColorMessage.getMSG("§8[§9Karma§8] §b" + karma + " Karma §8(§b" + karmaa + "§8/§b100§8)"));
                    }
                    karmacheck=false;
                }

            }
        }
    }
}
