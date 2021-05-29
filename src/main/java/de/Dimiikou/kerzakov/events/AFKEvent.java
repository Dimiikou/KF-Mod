package de.Dimiikou.kerzakov.events;

import de.Dimiikou.kerzakov.Kerzakov;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class AFKEvent {

    @SubscribeEvent
    public static void onAFKMessageReceive(ClientChatReceivedEvent e) {
        if (e.getMessage().getUnformattedText().contains("Du bist nun im AFK")) {
            Kerzakov.AFK = true;
            return;
        } else if (e.getMessage().getUnformattedText().contains("Du bist nun nicht mehr im AFK")) {
            Kerzakov.AFK = false;
            return;
        }
    }
}
