package de.Dimiikou.kerzakov.events;

import de.Dimiikou.kerzakov.config.KerzakovConfig;
import de.fuzzlemann.ucutils.Main;
import de.fuzzlemann.ucutils.base.text.TextUtils;
import de.fuzzlemann.ucutils.utils.faction.Faction;
import de.fuzzlemann.ucutils.utils.faction.FactionPlayersList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
@SideOnly(Side.CLIENT)
public class NametagChanger {

    public static ArrayList<String> names = new ArrayList<>();
    private static final Map<String, EntityPlayer> PLAYER_MAP = new HashMap<>();
    private static int tick;
    private static final Pattern STRIP_PREFIX_PATTERN = Pattern.compile("\\[[0-9A-Za-z]+]");

    @SubscribeEvent
    public static void onNameFormat(PlayerEvent.NameFormat e) {
        if (!KerzakovConfig.NametagChanger) return;

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
        } else if (FactionPlayersList.PLAYER_TO_FACTION_MAP.get(userName) == Faction.TRIADS) {
            e.setDisplayname("\247" + KerzakovConfig.Farbcode + userName);
        }

        names.add(userName);
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
            if (name.startsWith("§8")) continue; //Hitman Corpse

            String colorStrippedName = name.substring(2);
            String realName = colorStrippedName.substring(1);

            String color = null;

            if (Faction.getFactionOfPlayer() != null && FactionPlayersList.PLAYER_TO_FACTION_MAP.get(STRIP_PREFIX_PATTERN.matcher(realName).replaceAll("")) == Faction.getFactionOfPlayer()) {
                color = "\247" + KerzakovConfig.Farbcode;
            } else if (FactionPlayersList.PLAYER_TO_FACTION_MAP.get(STRIP_PREFIX_PATTERN.matcher(realName).replaceAll("")) == Faction.TRIADS) {
                color = "\247" + KerzakovConfig.Farbcode;
            }

            if (color == null) {
                if (name.startsWith("§7")) continue;

                color = "§7";
            }

            entityItem.setCustomNameTag(color + colorStrippedName);
        }

        tick = 0;
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
