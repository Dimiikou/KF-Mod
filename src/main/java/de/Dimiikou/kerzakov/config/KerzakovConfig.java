package de.Dimiikou.kerzakov.config;

import de.Dimiikou.kerzakov.Kerzakov;
import de.Dimiikou.kerzakov.events.NametagChanger;
import de.Dimiikou.kerzakov.utils.ColorMessage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

@Config(modid = Kerzakov.MODID, name = Kerzakov.NAME)
@Mod.EventBusSubscriber
public class KerzakovConfig {

    @Config.Name("NametagChanger")
    @Config.Comment("Aktiviere ob du Fraktions und B\u00fcndnissnamen Farblich gekennzeichnet haben m\u00f6chtest.")
    public static boolean NametagChanger = false;

    @Config.Name("Farbcode")
    @Config.Comment("Stelle die Farbe für die Namen deiner B\u00fcndnisspartner ein.")
    public static String Farbcode = "9";

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent e) {
        if (e == null || e.getModID().equals(Kerzakov.MODID)) {
            String previousFarbCode = Farbcode;

            ConfigManager.sync(Kerzakov.MODID, Config.Type.INSTANCE);
            Pattern colorCodes = Pattern.compile("^[a-f0-9]$");
            if ((!previousFarbCode.isEmpty()) && colorCodes.matcher(Farbcode).find()) {
                de.Dimiikou.kerzakov.events.NametagChanger.refreshAllDisplayNames();
            } else {
                Farbcode = previousFarbCode;
                ConfigManager.sync(Kerzakov.MODID, Config.Type.INSTANCE);
                Minecraft.getMinecraft().player.sendMessage(ColorMessage.getMSGwithPrefix("Gültige farben sind: §00 §11 §22 §33 §44 §55 §66 §77 §88 §99 §aa §bb §cc §dd §ee §ff"));
                e.setCanceled(true);
            }
        }
    }
}
