//package net.forscore.commands;
//
//
//import org.bukkit.Bukkit;
//import org.bukkit.Material;
//import org.bukkit.entity.HumanEntity;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.inventory.ClickType;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.event.inventory.InventoryType;
//import org.bukkit.event.player.PlayerPortalEvent;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemFlag;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//public class GUIManager implements Listener {
//    public static Map<String,ItemStack> inventories = new HashMap<>();
//    private static Inventory inv;
//    public GUIManager(){
//        inv = Bukkit.createInventory(null, 54, "Изменить цвет");
//        initializeItems();
//    }
//    public void initializeItems() {
//
//        ItemStack backgroundItem = createGuiItem(Material.GRAY_STAINED_GLASS_PANE,"");
//        for (int i = 0; i < 10; i++) inv.setItem(i, backgroundItem); //creating first line and 1 in second
//        inv.setItem(10,createGuiItem(Material.PURPLE_WOOL,"§5Тёмно-фиолетовый цвет"));
//        inv.setItem(11,createGuiItem(Material.MAGENTA_WOOL,"§dФиолетовый цвет"));
//        inv.setItem(12,createGuiItem(Material.BLUE_WOOL,"§1Тёмно-синий цвет"));
//        inv.setItem(13,createGuiItem(Material.LIGHT_BLUE_CONCRETE,"§9Синий цвет"));
//        inv.setItem(14,createGuiItem(Material.LIGHT_BLUE_WOOL,"§bГолубой цвет"));
//        inv.setItem(15,createGuiItem(Material.CYAN_WOOL,"§3Бирюзовый цвет"));
//        inv.setItem(16,createGuiItem(Material.GREEN_WOOL,"§2Тёмно-зелёный цвет"));
//        inv.setItem(17,backgroundItem);
//
//        inv.setItem(18,backgroundItem);
//        inv.setItem(19,backgroundItem);
//        inv.setItem(20,createGuiItem(Material.LIME_WOOL,"§aЛаймовый цвет"));
//        inv.setItem(21,createGuiItem(Material.YELLOW_WOOL,"§eЖёлтый цвет"));
//        inv.setItem(22,createGuiItem(Material.ORANGE_WOOL,"§6Золотой (оранжевый) цвет"));
//        inv.setItem(23,createGuiItem(Material.RED_WOOL,"§cКрасный цвет"));
//        inv.setItem(24,createGuiItem(Material.NETHER_WART_BLOCK,"§4Тёмно-красный цвет"));
//        inv.setItem(25,backgroundItem);
//        inv.setItem(26,backgroundItem);
//
//        inv.setItem(27,backgroundItem);
//        inv.setItem(28,backgroundItem);
//        inv.setItem(29,createGuiItem(Material.BLACK_WOOL,"§0Чёрный цвет"));
//        inv.setItem(30,createGuiItem(Material.GRAY_WOOL,"§8Серый цвет"));
//        inv.setItem(31,backgroundItem);
//        inv.setItem(32,createGuiItem(Material.LIGHT_GRAY_WOOL,"§7Светло-серый цвет"));
//        inv.setItem(33,createGuiItem(Material.WHITE_WOOL,"§fБелый цвет"));
//        inv.setItem(34,backgroundItem);
//        inv.setItem(35,backgroundItem);
//
//        inv.setItem(36,backgroundItem);
//        inv.setItem(37,backgroundItem);
//        inv.setItem(38,createGuiItem(Material.PAPER,"§l§r§lЖирный"));
//        inv.setItem(39,createGuiItem(Material.PAPER,"§m§r§mЗачёркнутый"));
//        inv.setItem(40,createGuiItem(Material.PAPER,"§n§r§nПодчёркнутый"));
//        inv.setItem(41,createGuiItem(Material.PAPER,"§o§r§oКурсив"));
//        inv.setItem(42,createGuiItem(Material.BARRIER,"§cСбросить цвет и шрифт"));
//        for (int i = 43; i < 54; i++) inv.setItem(i, backgroundItem);
//    }
//
//    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
//        final ItemStack item = new ItemStack(material, 1);
//        final ItemMeta meta = item.getItemMeta();
//        if(!(material == Material.GRAY_STAINED_GLASS_PANE
//                || material == Material.BARRIER ))  meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
//
//        meta.setDisplayName(name);
//        item.setItemMeta(meta);
//        if(lore == null) return item;
//        meta.setLore(Arrays.asList(lore));
//        item.setItemMeta(meta);
//        return item;
//    }
//
//    // You can open the inventory with this
//    public static void openInventory(HumanEntity entity) {
//        //entity.closeInventory();
//        ItemStack item = entity.getInventory().getItemInMainHand();
//        if(item.getType() == Material.AIR){
//            entity.sendMessage("Your hand is empty!");
//            return;
//        }
//        if(!item.getItemMeta().hasDisplayName()){
//            entity.sendMessage("Please rename your item first");
//            return;
//        }
//        inventories.put(entity.getName(),item);
//        entity.openInventory(inv);
//    }
//    public static boolean allowEnd = true;
//    @EventHandler
//    public void onTeleport(PlayerPortalEvent event){
//        if(allowEnd) return;
//        if(event.getTo().getWorld().getName().equalsIgnoreCase("world_end")||
//                event.getTo().getWorld().getName().equalsIgnoreCase("the_end")){
//            event.setCancelled(true);
//        }
//    }
//    // Check for clicks on items
//    @EventHandler
//    public void onInventoryClick(final InventoryClickEvent event) {
//        String name = event.getWhoClicked().getName();
//        if (!inventories.containsKey(name)) return;
//        if (!event.getInventory().equals(inv)) return;
//        event.setCancelled(true);
//        final ItemStack clickedItem = event.getCurrentItem();
//        if (clickedItem == null || clickedItem.getType().isAir()) return;
//        final Player p = (Player) event.getWhoClicked();
//
//        if (clickedItem.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)
//                && clickedItem.getItemMeta().getDisplayName().charAt(0) == '§') {
//            ItemStack item = inventories.get(name);
//            ItemMeta meta = item.getItemMeta();
//            meta.setDisplayName(clickedItem.getItemMeta().getDisplayName().substring(0, 2) + meta.getDisplayName());
//            item.setItemMeta(meta);
//            inventories.put(name, item);
//            event.getWhoClicked().closeInventory();
//            event.getWhoClicked().openInventory(inv);
//        }
//        if (clickedItem.getType() == Material.BARRIER) {
//            ItemStack item = inventories.get(name);
//            ItemMeta meta = item.getItemMeta();
//            while (meta.getDisplayName().charAt(0) == '§') {
//                meta.setDisplayName(meta.getDisplayName().substring(2));
//            }
//            item.setItemMeta(meta);
//            inventories.put(name, item);
//            event.getWhoClicked().closeInventory();
//            event.getWhoClicked().openInventory(inv);
//        }
//    }
//
//    @EventHandler
//    public void repairBook(InventoryClickEvent event){
//        if(event.getSlotType() == InventoryType.SlotType.RESULT) return;
//        if(event.getClick() != ClickType.MIDDLE) return;
//        if(event.getCursor() == null) return;
//        if(event.getCurrentItem() == null) return;
//        ItemStack cursor = event.getCursor();
//        ItemStack currentItem = event.getCurrentItem();
//
//        if(cursor.getType() != Material.PAPER) return;
//        if(currentItem.getType() != Material.WRITABLE_BOOK) return;
//        ItemMeta meta = currentItem.getItemMeta();
//
//        if(meta.hasCustomModelData()) {
//            int md = meta.getCustomModelData();
//            if(md >= 6) return;
//            md++;
//
//            meta.setCustomModelData(md);
//            meta.setLore(Arrays.asList("","§r§fПрочность: "+md+" / 6"));
//        }
//        else return;
//
//        currentItem.setItemMeta(meta);
//
//        if(cursor.getAmount()>1) cursor.setAmount(cursor.getAmount()-1);
//        else {
//            event.getWhoClicked().setItemOnCursor(null);
//        }
//    }
//}