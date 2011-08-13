package com.rakaienguard.seaboy1234.bukkit.SignBay;

import org.bukkit.Material;

public class ShopTools {
	public static int getMatID(String name) {
        int matID = -1;
        Material[] mat = Material.values();
        int temp = 9999;
        Material tmp = null;
        for (Material m : mat) {
            if (m.name().toLowerCase().replaceAll("_", "").startsWith(name.toLowerCase().replaceAll("_", "").replaceAll(" ", ""))) {
                if (m.name().length() < temp) {
                    tmp = m;
                    temp = m.name().length();
                }
            }
        }
        if (tmp != null) {
            matID = tmp.getId();
        }
        return matID;
    }
}
