package de.Dimiikou.kerzakov.utils;

import de.Dimiikou.kerzakov.Kerzakov;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ColorMessage {

    public static TextComponentString getMSG(String msg) {
        if (msg.contains("§")) {
            msg = msg.replace("§", "\247");
        }
        if (msg.contains("ä")) {
            msg = msg.replace("ä", "\u00e4");
        }
        if (msg.contains("Ä")) {
            msg = msg.replace("Ä", "\u00c4");
        }
        if (msg.contains("ö")) {
            msg = msg.replace("ö", "\u00f6");
        }
        if (msg.contains("Ö")) {
            msg = msg.replace("Ö", "\u00d6");
        }
        if (msg.contains("ü")) {
            msg = msg.replace("ü", "\u00fc");
        }
        if (msg.contains("Ü")) {
            msg = msg.replace("Ü", "\u00dc");
        }
        if (msg.contains("ß")) {
            msg = msg.replace("ß", "\u00df");
        }
        return new TextComponentString(msg);
    }

    public static TextComponentString getMSGwithPrefix(String msg) {
        if (msg.contains("§")) {
            msg = msg.replace("§", "\247");
        }
        if (msg.contains("ä")) {
            msg = msg.replace("ä", "\u00e4");
        }
        if (msg.contains("Ä")) {
            msg = msg.replace("Ä", "\u00c4");
        }
        if (msg.contains("ö")) {
            msg = msg.replace("ö", "\u00f6");
        }
        if (msg.contains("Ö")) {
            msg = msg.replace("Ö", "\u00d6");
        }
        if (msg.contains("ü")) {
            msg = msg.replace("ü", "\u00fc");
        }
        if (msg.contains("Ü")) {
            msg = msg.replace("Ü", "\u00dc");
        }
        if (msg.contains("ß")) {
            msg = msg.replace("ß", "\u00df");
        }
        return new TextComponentString(Kerzakov.prefix + msg);
    }

    public static String getString(String msg) {
        if (msg.contains("§")) {
            msg = msg.replace("§", "\247");
        }
        if (msg.contains("ä")) {
            msg = msg.replace("ä", "\u00e4");
        }
        if (msg.contains("Ä")) {
            msg = msg.replace("Ä", "\u00c4");
        }
        if (msg.contains("ö")) {
            msg = msg.replace("ö", "\u00f6");
        }
        if (msg.contains("Ö")) {
            msg = msg.replace("Ö", "\u00d6");
        }
        if (msg.contains("ü")) {
            msg = msg.replace("ü", "\u00fc");
        }
        if (msg.contains("Ü")) {
            msg = msg.replace("Ü", "\u00dc");
        }
        if (msg.contains("ß")) {
            msg = msg.replace("ß", "\u00df");
        }
        return msg;
    }
}