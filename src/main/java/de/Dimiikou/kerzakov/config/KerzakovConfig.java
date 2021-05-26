package de.Dimiikou.kerzakov.config;

import de.Dimiikou.kerzakov.Kerzakov;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod;

@Config(modid = Kerzakov.MODID, name = Kerzakov.NAME)
@Mod.EventBusSubscriber
public class KerzakovConfig {

    @Config.Name("NametagChanger")
    @Config.Comment("Aktiviere ob du Fraktions und B\u00fcndnissnamen Farblich gekennzeichnet haben m\u00f6chtest.")
    public static boolean NametagChanger = false;

    @Config.Name("Farbcode")
    @Config.Comment("Stelle die Farbe für die Namen deiner B\u00fcndnisspartner ein.")
    public static String Farbcode = "9";
}
