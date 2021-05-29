package de.Dimiikou.kerzakov.events;

import de.Dimiikou.kerzakov.Kerzakov;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class DeathEvent {

    private static Pattern DEATH_PATTERN = Pattern.compile("^Du bist nun für (\\d+) Minuten auf dem Friedhof\\.$");

    @SubscribeEvent
    public static void onDeathMessage(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        if (DEATH_PATTERN.matcher(msg).find()) {
            Kerzakov.DEAD = true;
            return;
        }

        if (msg.equalsIgnoreCase("Du lebst nun wieder.")) {
            Kerzakov.DEAD = false;
            return;
        }
    }
}
