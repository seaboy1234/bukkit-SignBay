package com.rakaienguard.seaboy1234.bukkit.SignBay;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

public class ShopLogger {
	private static Logger l;
	private static Plugin p;
	public static void Load(Plugin plugin)
	{
		l = Logger.getLogger("Minecraft");
		p = plugin;
	}
	public static void info(String Message)
	{
		log(Level.INFO, Message);
	}
	public static void warning(String Message)
	{
		log(Level.WARNING, Message);
	}
	public static void error(String Message)
	{
		log(Level.SEVERE, Message);
	}
	private static void log(Level lvl, String Message)
	{
		l.log(lvl, p.getDescription().getName() + ": " + Message);
	}
	public static void error(Exception e) {
		StackTraceElement[] stackTrace = e.getStackTrace();
		error(e.toString());
		error(e.getLocalizedMessage());
		for(StackTraceElement ste: stackTrace)
		{
			error(ste.toString());
		}
	}
}
