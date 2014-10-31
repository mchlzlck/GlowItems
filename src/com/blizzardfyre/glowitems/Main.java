package com.blizzardfyre.glowitems;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public void onEnable() {
		getCommand("glow").setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "Only players can do that.");
			return true;
		}
		if (!sender.hasPermission("glowitems.glow")) {
			sender.sendMessage(ChatColor.DARK_RED + "You don't have permission.");
			return true;
		}
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("glow")) {
			player.setItemInHand(addGlow(player.getItemInHand()));
			player.sendMessage(ChatColor.GOLD + "Your item has been glowed");
		} else if (cmd.getName().equalsIgnoreCase("deglow")) {
			player.setItemInHand(removeGlow(player.getItemInHand()));
			player.sendMessage(ChatColor.GOLD + "Your item has been deglowed");
		}
		return true;
	}

	public static org.bukkit.inventory.ItemStack addGlow(org.bukkit.inventory.ItemStack item) {
		net.minecraft.server.v1_7_R4.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}
		if (tag == null) {
			tag = nmsStack.getTag();
		}
		NBTTagList ench = new NBTTagList();
		tag.set("ench", ench);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}

	public static org.bukkit.inventory.ItemStack removeGlow(org.bukkit.inventory.ItemStack item) {
		return new org.bukkit.inventory.ItemStack(item);
	}
}
