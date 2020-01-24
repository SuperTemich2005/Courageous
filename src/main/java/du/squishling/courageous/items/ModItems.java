package du.squishling.courageous.items;

import net.minecraft.item.Food.Builder;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvents;

import java.util.ArrayList;

public class ModItems {

    public static ArrayList<Item> ITEMS = new ArrayList<Item>();

    public static final Item SLIMY_STEEL_BROADSWORD = new SwordBase("slimy_steel_broadsword", ModItemTiers.SLIMY_STEEL, 0, 0);

    public static final Item PEAR = new CustomFood("pear", new Builder().hunger(1).saturation(0.2f).fastToEat().build());
    public static final Item MAPLE_SYRUP = new CustomFood("maple_syrup", new Builder().hunger(1).saturation(0.4f).setAlwaysEdible().build()).drink();

}
