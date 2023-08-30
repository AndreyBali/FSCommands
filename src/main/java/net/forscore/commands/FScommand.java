package net.forscore.commands;

import com.google.common.collect.Lists;
import net.forscore.commands.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;


import java.io.File;
import java.util.*;

public class FScommand extends AbstractCommand {
    public FScommand(Main plugin){
        super("fs");
        this.plugin = plugin;
    }
    private final Main plugin;
    HashMap<String, Integer> eventM = new HashMap<String, Integer>();


    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage("§cUse /"+label+" [progress, takePage, sign]\n");
            return;
        }

        if (args[0].equalsIgnoreCase("progress")) progress(sender, label, args);
        else if(args[0].equalsIgnoreCase("disablePlugin")) disablePlugin(sender,args);
        else if(args[0].equalsIgnoreCase("takePage")) takePage(sender,args);
        else if(args[0].equalsIgnoreCase("sign")) subscribe(sender);
//        else if(args[0].equalsIgnoreCase("setColor")) setColor(sender);
        else if (args[0].equalsIgnoreCase("tell")) tell(sender, label, args);
        //else if(args[0].equalsIgnoreCase("debug")) debug(sender,args);
        else if(args[0].equalsIgnoreCase("customModelData")) customModelData(sender,args);
        else if(args[0].equalsIgnoreCase("openEnd")) customModelData(sender,args);
        else if(args[0].equalsIgnoreCase("closeEnd")) customModelData(sender,args);
        else sender.sendMessage("§cUse /"+label+" [progress, takePage, sign]\n");

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = Lists.newArrayList("progress","takePage","sign");
        if(sender.isOp() || sender.hasPermission("fs.customModelData"))
            arguments.add("customModelData");//customModelData
//        if(sender.isOp()) arguments.add("setColor");

        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) result.add(a);
            }
            return result;
        }
        if(args.length==2){
            if(args[0].equalsIgnoreCase("config")){
                for (String a : Lists.newArrayList("fireworkVillagers","customAnvilSymbols","reload")) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase())) result.add(a);
                }
                return result;
            }
        }
        return Lists.newArrayList();
    }
    public void tell(CommandSender sender, String label, String[] args){
        String argss = "";
        for(String arg : args){
            argss = argss +" "+ arg;
        }
        Bukkit.getServer().dispatchCommand(sender,"msg"+argss);
    }

    public void customModelData(CommandSender sender,String args[]){
        if(sender.isOp() || sender.hasPermission("fs.customModelData")){
            Player p = (Player) sender;
            ItemStack item = p.getInventory().getItemInMainHand();
            if(item.getType().isAir()) return;
            Integer md = Integer.parseInt(args[1]);
            ItemMeta meta = item.getItemMeta();
            meta.setCustomModelData(md);
            item.setItemMeta(meta);
            p.getInventory().setItemInMainHand(item);
        }
    }


//    public void debug(CommandSender sender,String[] args){
//        sender.sendMessage("§cUse /"+"fs"+" [cooldown, progress, takePage, sign, setColor]\n");
//        if(args[1].equalsIgnoreCase("list")) sender.sendMessage(Bukkit.getOnlinePlayers()+"");
//        if(args[1].equalsIgnoreCase("nameList")){
//            String names = "Players: ";
//            for(Player p: Bukkit.getOnlinePlayers()){
//                names = names+p.getName()+", ";
//            }
//            sender.sendMessage(names);
//        }
//        if(args[1].equalsIgnoreCase("near")){
//            if(args.length == 3){
//                String names = "Players: ";
//                for(Player p: Bukkit.getOnlinePlayers()){
//                    Player player = (Player)sender;
//                    if(Achievements.squaredDistance(player.getLocation(),p.getLocation())< Integer.parseInt(args[2]))
//                        names = names+p.getName()+", ";
//                }
//                sender.sendMessage(names);
//            }
//            else {
//                String names = "Players: ";
//                for(Player p: Bukkit.getOnlinePlayers()){
//                    Player player = (Player)sender;
//                    if(Achievements.squaredDistance(player.getLocation(),p.getLocation())< 100)
//                        names = names+p.getName()+", ";
//                }
//                sender.sendMessage(names);
//            }
//        }
//
//    }

//    public void setColor(CommandSender sender){
//        if(sender.isOp()) plugin.menu(sender);
//    }

    public void takePage(CommandSender sender, String[] args){
        if(args.length !=2) {
            sender.sendMessage("Use /fs takePage <page number>");
            return;
        }
        Player p = (Player)sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if(item.getType() != Material.WRITABLE_BOOK) {
            sender.sendMessage("It isn't a book in your hand!");
            return;
        }
        BookMeta meta = (BookMeta)item.getItemMeta();
        if(meta.getPageCount()<Integer.parseInt(args[1])) {
            sender.sendMessage("There is no such page in your book!");
            return;
        }
        if(meta.hasCustomModelData()) {
            int md = meta.getCustomModelData()-1;
            meta.setCustomModelData(md);
            meta.setLore(Arrays.asList("","§r§fПрочность: "+md+" / 6"));
        }
        else {
            meta.setCustomModelData(5);
            meta.setLore(Arrays.asList("","§r§fПрочность: 5 / 6"));
        }

        item.setItemMeta(meta);
        if(meta.getCustomModelData()==0){
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            p.sendMessage("У вашей книги закончились листочки!");
        }

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        String page = meta.getPage(Integer.parseInt(args[1]));
        List<String> stringPage = new ArrayList<>(Arrays.asList(page.split("\n")));
        int i = 0;
        for (String str : stringPage)
        {
            stringPage.set(i, "§r§f"+str);
            i++;
        }
        stringPage.add(0,"");
        paperMeta.setLore(stringPage);
        paperMeta.setDisplayName("§rВырванный листочек");
        paperMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        paper.setItemMeta(paperMeta);
        p.getInventory().addItem(paper);
    }

    public void subscribe(CommandSender sender){
        Player p = (Player)sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if(item.getType() != Material.PAPER) {
            sender.sendMessage("Подписать можно только вырванный листочек");
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)) {
            sender.sendMessage("Подписать можно только вырванный листочек");
            return;
        }
        List<String> badLore = meta.getLore();
        List<String> lore = new ArrayList<>();
        if(badLore != null) lore.addAll(badLore);

        for (int j = lore.size(); j <= 15; j++) {
            lore.add("");
        }
        lore.add("§r§fПодпись: §7" + p.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
    public void disablePlugin(CommandSender sender, String[] args){
        if(!sender.isOp()){
            sender.sendMessage("§cЭту команду может использовать только оператор!");
            return;
        }
        if(args.length==2)
            if(args[1].equalsIgnoreCase("confirm")){
                System.out.println(sender.getName() +" is disabling "+plugin.getName()+" plugin!");
                sender.sendMessage(plugin.getName()+" будет выключен через 10 тиков." );
                Bukkit.getScheduler().runTaskLater(plugin, ()->{ Bukkit.getPluginManager().disablePlugin(plugin); },10);
            }
        sender.sendMessage("Вы действительно хотите выключить плагин "+plugin.getName()+"? (Для его включения нужно будет перезапустить сервер)");
        sender.sendMessage("Если да, то напишите: /fs disablePlugin confirm");
    }

    public void progress(CommandSender sender, String label, String[] args){
        Bukkit.getServer().dispatchCommand(sender,"fsadv progress");
        //return "";
//        File prg_file = new File(plugin.getDataFolder() + File.separator + "/progress/" + sender.getName() +".yml");
//        FileConfiguration prg = YamlConfiguration.loadConfiguration(prg_file);
//        int witherCount = prg.getInt("wither.spawncount");
//        List<String> playerCount = prg.getStringList("player.killed");
//
//        return "Убито визеров: " + witherCount + ", Убитые игроки: "+ playerCount;
    }
}
