package com.rakaienguard.seaboy1234.bukkit.SignBay;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.Location;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SignBay extends JavaPlugin {

	private ShopListener sl = new ShopListener(this);
	private ShopBlockListener sbl = new ShopBlockListener(this);
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnable() {
		ShopLogger.Load(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Type.SIGN_CHANGE, sbl, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_INTERACT, sl, Priority.Normal, this);
		pm.registerEvent(Type.BLOCK_BREAK, sbl, Priority.Highest, this);
		ShopLogger.info("Enabled.");
		SetupDatabase();
	}
	public void AddShop(int cur, String curName, int Price, Location loc, String player)
	{
		Shop s = new Shop();
		s.setCurId(cur);
		s.setCurName(curName);
		s.setItemId(0);
		s.setPrice(Price);
		s.setX(loc.getBlockX());
		s.setY(loc.getBlockY());
		s.setZ(loc.getBlockZ());
		s.setWorld(loc.getWorld().getName());
		s.setOwner(player);
		s.setStock(0);
		s.setCount(0);
		s.setBalance(0);
		getDatabase().save(s);
	}
	public void SetupDatabase()
	{
		try{
			getDatabase().find(Shop.class).findRowCount();
		}catch(PersistenceException e){
			installDDL();
		}
	}
	@Override
	public List<Class<?>> getDatabaseClasses() {
		ArrayList<Class<?>> list = new ArrayList<Class<?>>();
		list.add(Shop.class);
		return list;
		
	}

	public void SetSelling(String ShopID, int itemCode, int count)
	{
		Shop s = getDatabase().find(Shop.class).where().ieq("id", ShopID).findUnique();
		s.setItemId(itemCode);
		s.setCount(count);
		s.setStock(count);
		getDatabase().save(s);
	}
	public Shop GetShop(Location loc)
	{
		String x = String.valueOf(loc.getBlockX());
		String y = String.valueOf(loc.getBlockY());
		String z = String.valueOf(loc.getBlockZ());
		String world = String.valueOf(loc.getWorld().getName());
		Shop s = getDatabase().find(Shop.class).where().ieq("x", x).ieq("y", y).ieq("z", z).ieq("world", world).findUnique();
		return s;
	}

}
