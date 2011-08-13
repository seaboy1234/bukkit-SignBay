package com.rakaienguard.seaboy1234.bukkit.SignBay;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.block.CraftSign;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

public class ShopBlockListener extends BlockListener {

	private SignBay plugin;
	public ShopBlockListener(SignBay p){plugin = p;}
	@Override
	public void onSignChange(SignChangeEvent event) {
		if(event.getLine(0).equalsIgnoreCase("[shop]"))
		{
			String paymentType = "Iron";
			String paymentCode = "i";
			int paymentId = 265;
			if(event.getLine(1).toLowerCase().endsWith("i"))
			{
				paymentCode = "i";
				paymentType = "Iron";
				paymentId = 265;
			}
			else if(event.getLine(1).toLowerCase().endsWith("g"))
			{
				paymentCode = "g";
				paymentType = "Gold";
				paymentId = 266;
			}
			else if(event.getLine(1).toLowerCase().endsWith("d"))
			{
				paymentCode = "d";
				paymentType = "Diamonds";
				paymentId = 264;
			}
			int paymentAmount = 10;
			try
			{
				paymentAmount = Integer.parseInt(event.getLine(1).replace(paymentCode, ""));
			}
			catch(Exception e)
			{
				event.getPlayer().sendMessage(ChatColor.RED + "Please input only a number and the payment type(i for Iron,g for Gold, or d for Diamonds)");
				event.getPlayer().sendMessage(ChatColor.RED + "For example: 10i");
			}
			event.setLine(0, event.getPlayer().getName());
			event.setLine(1, paymentAmount + " " + paymentType);
			event.setLine(2, "Right click me");
			event.setLine(3, "with something");
			plugin.AddShop(paymentId, paymentType, paymentAmount, event.getBlock().getLocation(), event.getPlayer().getName());
		}
	}
	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		Shop s = plugin.GetShop(event.getBlock().getLocation());
		if(s == null) return;
		if(s.getOwner().equalsIgnoreCase(event.getPlayer().getName()))
		{
			for(int i = s.getStock(); i > 0; i -= 64)
			{
				if(i >= 64)
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(s.getItemId(), 64));
				else
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(s.getItemId(), i));
			}
			for(int i = s.getBalance(); i > 0; i -= 64)
			{
				if(i >= 64)
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(s.getCurId(), 64));
				else
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(s.getCurId(), i));
			}
			plugin.getDatabase().delete(s);
			event.getPlayer().sendMessage("Sign shop removed.");
		}
		else
		{
			Sign block = new CraftSign(event.getBlock());
			event.setCancelled(true);
			if(s.getItemId() != 0)
			{
				block.setLine(0, s.getOwner());
				block.setLine(1, Material.getMaterial(s.getItemId()).toString());
				block.setLine(2, s.getCount() + " for " + s.getPrice() + " " + s.getCurName());
				block.setLine(3, s.getStock() + " items.");
			}
			else
			{
				block.setLine(0, s.getOwner());
				block.setLine(1, s.getPrice() + " " + s.getCurName());
				block.setLine(2, "Right click me");
				block.setLine(3, "with something");
			}
			block.update();
			event.getPlayer().sendMessage("Stop trying to grief " + s.getOwner() + "'s shops.");
		}
	}

}
