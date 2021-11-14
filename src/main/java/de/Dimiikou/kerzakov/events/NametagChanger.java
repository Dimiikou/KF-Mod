package de.Dimiikou.kerzakov.events;

import de.Dimiikou.kerzakov.commands.Trainingsmode;
import de.Dimiikou.kerzakov.config.KerzakovConfig;
import de.fuzzlemann.ucutils.Main;
import de.fuzzlemann.ucutils.utils.faction.Faction;
import de.fuzzlemann.ucutils.utils.faction.FactionPlayersList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
@SideOnly(Side.CLIENT)
public class NametagChanger {

    private static final Map<String, EntityPlayer> PLAYER_MAP = new HashMap<>();
    private static int tick;
    private static final Pattern STRIP_PREFIX_PATTERN = Pattern.compile("\\[[0-9A-Za-z]+]");
    private static final Pattern TEAM_LIST_PATTERN = Pattern.compile("^?(Zar'|Baron'|Kavaler|WinkelsMaid) ([a-zA-Z0-9_]+): Team?(1|2):([a-zA-Z0-9_-]+)$");
    private static final Map<String, Boolean> TEAM_LIST = new HashMap<>();
    //public static final List<String> HOSTAGE_TAKING_ENEMYS = new ArrayList<>();

    @SubscribeEvent
    public static void onNameFormat(PlayerEvent.NameFormat e) {
        if (!KerzakovConfig.NametagChanger) return;

        EntityPlayer p = e.getEntityPlayer();

        String userName = e.getUsername();
        String displayName = ScorePlayerTeam.formatPlayerName(p.getTeam(), userName);

        PLAYER_MAP.put(userName, p);

        if (displayName.contains("\247k")) return;

        String color = getPrefix(userName, p.getUniqueID());
        if (color == null) return;

        e.setDisplayname(color + userName);
    }

    @SubscribeEvent
    public static void onTeamListReceive(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        Matcher m = TEAM_LIST_PATTERN.matcher(msg);

        if (m.find()) {
            if (Integer.valueOf(m.group(3)) == 1) {
                String[] s = m.group(4).split("-");
                for (int i=0;i<s.length;i++) {
                    TEAM_LIST.put(s[i], false);
                }
                refreshAllDisplayNames();
                return;
            }

            String[] s = m.group(4).split("-");
            for (int i=0;i<s.length;i++) {
                TEAM_LIST.put(s[i], true);
            }
            refreshAllDisplayNames();
        }
    }

    // Danke Fuzzle haha
    @SubscribeEvent
    public static void onWorldTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.START) return;

        if (Main.MINECRAFT.world == null) return;
        if (tick++ != 20) return;

        List<EntityItem> items = Main.MINECRAFT.world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);

        for (EntityItem entityItem : items) {
            String name = entityItem.getCustomNameTag();
            if (name.startsWith("\2478")) continue; //Hitman Corpse

            String colorStrippedName = name.substring(2);
            String realName = colorStrippedName.substring(1);

            String color = null;

            if (Faction.getFactionOfPlayer() != null && FactionPlayersList.PLAYER_TO_FACTION_MAP.get(STRIP_PREFIX_PATTERN.matcher(realName).replaceAll("")) == Faction.getFactionOfPlayer()) {
                color = "\247" + KerzakovConfig.Farbcode;
            }
            /*else if (FactionPlayersList.PLAYER_TO_FACTION_MAP.get(STRIP_PREFIX_PATTERN.matcher(realName).replaceAll("")) == Faction.TRIADS) {
                color = "\247" + KerzakovConfig.Farbcode;
            }*/

            if (color == null) {
                if (name.startsWith("\2477")) continue;

                color = "\2477";
            }

            entityItem.setCustomNameTag(color + colorStrippedName);
        }

        tick = 0;
    }

    @SubscribeEvent
    public static void onStopTracking(PlayerEvent.StopTracking e) {
        EntityPlayer p = e.getEntityPlayer();
        PLAYER_MAP.remove(p.getName());
    }

    private static String getPrefix(String userName, UUID uniqueID) {

        if (Trainingsmode.trainingsmode.get()) {
            Boolean teamType = TEAM_LIST.get(userName);
            if (teamType != null) {
                if (teamType) {
                    return "\2476";
                } else {
                    return "\247d";
                }
            }
        }

        /*if (HOSTAGE_TAKING_ENEMYS.contains(userName)) {
            return "\2475";
        }*/

        if (Faction.getFactionOfPlayer() != null && FactionPlayersList.PLAYER_TO_FACTION_MAP.get(userName) == Faction.getFactionOfPlayer()) {
            return "\247" + KerzakovConfig.Farbcode;
        }
        /*else if (FactionPlayersList.PLAYER_TO_FACTION_MAP.get(userName) == Faction.TRIADS) {
            return "\247" + KerzakovConfig.Farbcode;
        }*/

        return null;
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
