package pl.shop.plugin.data;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.shop.plugin.utils.ChatUtil;
import pl.shop.plugin.utils.ItemBuilder;

import java.util.function.Consumer;

public final class Item {

    private final String name;
    private final int cost;
    private final ItemStack item, raw;

    public Item(ConfigurationSection cs) {
        this.name = cs.getString("name");
        this.cost = cs.getInt("cost");
        ItemBuilder builder = new ItemBuilder(Material.getMaterial(cs.getString("material")), cs.getInt("amount"), (short) cs.getInt("data"));
        this.raw = builder.build();
        for (String s : cs.getStringList("enchantments")) {
            String[] splitted = s.split(":");
            builder.addEnchantment(Enchantment.getByName(splitted[0]), Integer.parseInt(splitted[1]));
        }
        this.item = builder.setTitle(cs.getString("title")).addLores(cs.getStringList("lore")).build();
    }


    public ItemStack getItem() {
        return item;
    }

    public Consumer<Player> getOnMouseClicked() {
        return player -> {
            player.getInventory().removeItem(new ItemStack(Material.EMERALD, cost));
            player.getInventory().addItem(raw);
            player.sendTitle(ChatUtil.fixColor("&8>> &9&lSKLEP &8<<"), ChatUtil.fixColor(String.format("&8>> &7Kupiles &e%d &7%s", raw.getAmount(), name)));
        };
    }
}
