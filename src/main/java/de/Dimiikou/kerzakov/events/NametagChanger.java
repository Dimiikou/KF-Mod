package de.Dimiikou.kerzakov.events;

import de.Dimiikou.kerzakov.config.KerzakovConfig;
import de.fuzzlemann.ucutils.utils.faction.Faction;
import de.fuzzlemann.ucutils.utils.faction.FactionPlayersList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Mod.EventBusSubscriber
@SideOnly(Side.CLIENT)
public class NametagChanger {

    public static ArrayList<String> names = new ArrayList<>();
    private static final Map<String, EntityPlayer> PLAYER_MAP = new HashMap<>();

    @SubscribeEvent
    public static void onNameFormat(PlayerEvent.NameFormat e) {
        EntityPlayer p = e.getEntityPlayer();

        String userName = e.getUsername();
        String displayName = ScorePlayerTeam.formatPlayerName(p.getTeam(), userName);

        PLAYER_MAP.put(userName, p);

        if (displayName.contains("\247k")) {
            return;
        }
        Faction playersFaction = Faction.getFactionOfPlayer();

        if (playersFaction != null && FactionPlayersList.PLAYER_TO_FACTION_MAP.get(userName) == playersFaction) {
            e.setDisplayname("\247" + KerzakovConfig.Farbcode + userName);
        }

        if (Faction.TRIADS != null && FactionPlayersList.PLAYER_TO_FACTION_MAP.get(userName) == Faction.TRIADS) {
            e.setDisplayname("\247" + KerzakovConfig.Farbcode + userName);
        }

        names.add(userName);
    }

    @SubscribeEvent
    public static void onStopTracking(PlayerEvent.StopTracking e) {
        EntityPlayer p = e.getEntityPlayer();
        names.remove(p.getName());
        PLAYER_MAP.remove(p.getName());
    }

    public static void refreshAllDisplayNames() {
        for (Iterator<EntityPlayer> iterator = PLAYER_MAP.values().iterator(); iterator.hasNext(); ) {
            EntityPlayer entityPlayer = iterator.next();
            if (entityPlayer == null) {
                iterator.remove();
                return;
            }

            entityPlayer.refreshDisplayName();
        }
    }

    public static void refreshDisplayName(String userName) {
        EntityPlayer entityPlayer = PLAYER_MAP.get(userName);
        if (entityPlayer == null) {
            PLAYER_MAP.remove(userName);
            return;
        }

        entityPlayer.refreshDisplayName();
    }
}
