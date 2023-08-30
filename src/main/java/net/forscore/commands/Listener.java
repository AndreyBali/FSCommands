package net.forscore.commands;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void repairBook(InventoryClickEvent event){
        if(event.getSlotType() == InventoryType.SlotType.RESULT) return;
        if(event.getClick() != ClickType.MIDDLE) return;
        if(event.getCursor() == null) return;
        if(event.getCurrentItem() == null) return;
        ItemStack cursor = event.getCursor();
        ItemStack currentItem = event.getCurrentItem();

        if(cursor.getType() != Material.PAPER) return;
        if(currentItem.getType() != Material.WRITABLE_BOOK) return;
        ItemMeta meta = currentItem.getItemMeta();

        if(meta.hasCustomModelData()) {
            int md = meta.getCustomModelData();
            if(md >= 6) return;
            md++;

            meta.setCustomModelData(md);
            meta.setLore(Arrays.asList("","§r§fПрочность: "+md+" / 6"));
        }
        else return;

        currentItem.setItemMeta(meta);

        if(cursor.getAmount()>1) cursor.setAmount(cursor.getAmount()-1);
        else {
            event.getWhoClicked().setItemOnCursor(null);
        }
    }

}
