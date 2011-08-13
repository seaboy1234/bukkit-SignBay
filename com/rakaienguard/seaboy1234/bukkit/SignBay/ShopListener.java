package com.rakaienguard.seaboy1234.bukkit.SignBay;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.block.CraftSign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

public class ShopListener extends PlayerListener{

	private SignBay plugin;
	public ShopListener(SignBay p){plugin = p;}
	@SuppressWarnings("deprecation")
	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK)return;
		Player p = event.getPlayer();
		Shop s = plugin.GetShop(event.getClickedBlock().getLocation());
		if(s == null)
		{
			return;
		}
		Sign block = new CraftSign(event.getClickedBlock());
		
		if(s.getOwner().equalsIgnoreCase(p.getName()))
		{
			event.setCancelled(true);
			if(p.getItemInHand().getTypeId() == 0 && s.getItemId() == 0)
			{
				p.sendMessage("Right click me with a stack of blocks or items to add items to my stock.");
				return;
			}
			if(s.getItemId() == 0)
			{
				plugin.SetSelling(String.valueOf(s.getId()), p.getItemInHand().getTypeId(), p.getItemInHand().getAmount());
				s = plugin.GetShop(event.getClickedBlock().getLocation());
				block.setLine(0, s.getOwner());
				block.setLine(1, p.getItemInHand().getType().toString());
				block.setLine(2, s.getCount() + " for " + s.getPrice() + " " + s.getCurName());
				block.setLine(3, s.getStock() + " items.");
				block.update();
				p.setItemInHand(null);
			}
			else if (p.getItemInHand().getTypeId() == s.getItemId())
			{
				s.setStock(s.getStock() + p.getItemInHand().getAmount());
				block.setLine(3, s.getStock() + " items.");
				block.update();
				event.getPlayer().setItemInHand(null);
				plugin.getDatabase().save(s);
			}
			else
			{
				for(int i = s.getBalance(); i > 0; i -= 64)
				{
					if(i >= 64)
						block.getWorld().dropItemNaturally(block.getBlock().getLocation(), new ItemStack(s.getCurId(), 64));
					else
						block.getWorld().dropItemNaturally(block.getBlock().getLocation(), new ItemStack(s.getCurId(), i));
				}
				s.setBalance(0);
				plugin.getDatabase().save(s);
			}
		}
		else
		{
			if(s.getItemId() == 0)return;
			int itemAmount = 0;
			for(ItemStack i : p.getInventory().getContents())
			{
				if(i == null)continue;
				if(i.getTypeId() == s.getCurId())
				{
					itemAmount += i.getAmount();
				}
			}
			itemAmount -= s.getPrice();
			if(itemAmount < 0)
			{
				p.sendMessage(ChatColor.RED + "You do not have enough " + s.getCurName() + " to buy this item.");
				return;
			}
			if(s.getStock() < s.getCount())
			{
				p.sendMessage(ChatColor.RED + "The shop does not have the required stock to sell items.  :(");
				Player pl = plugin.getServer().getPlayer(s.getOwner());
				if(pl != null)
				{
					pl.sendMessage("One of your shops is out of stock.");
				}
				return;
			}
			int amountDue = s.getPrice();
			for(ItemStack i : p.getInventory().getContents())
			{
				if(i == null)continue;
				if(i.getTypeId() != s.getCurId() || i.getTypeId() == 0)continue;
				if(amountDue <= 0)break;
				if(amountDue < 64)
				{
					if(i.getAmount() > amountDue)
					{
						i.setAmount(i.getAmount() - amountDue);
						amountDue = 0;
					}
					else if(i.getAmount() <= amountDue)
					{
						amountDue -= i.getAmount();
						p.getInventory().remove(i);
					}
					else
					{
						p.getInventory().remove(i);
					}
					
				}
				else
				{
					p.getInventory().remove(i);
					amountDue -= 64;
				}
			}
			p.updateInventory();
			block.getWorld().dropItemNaturally(block.getBlock().getLocation(), new ItemStack(s.getItemId(), s.getCount()));
			s.setBalance(s.getBalance() + s.getPrice());
			s.setStock(s.getStock() - s.getCount());
			block.setLine(3, s.getStock() + " items");
			block.update();
			plugin.getDatabase().save(s);
		}
	}
}
