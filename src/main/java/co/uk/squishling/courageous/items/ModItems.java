package co.uk.squishling.courageous.items;

import net.minecraft.item.Food.Builder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.*;

import java.util.ArrayList;

public class ModItems {

    public static ArrayList<Item> ITEMS = new ArrayList<Item>();

    public static final Item PEAR = new CustomFood("pear", 4, 0.4f);
    public static final Item ORANGE = new CustomFood("orange",3, 0.3f);
    public static final Item PLUM = new CustomFood("plum",3, 0.3f);
    public static final Item LEMON = new CustomFood("lemon", 1, 0.15f);
    public static final Item AVOCADO = new CustomFood("avocado", 2, 0.1f);
    public static final Item KIWI_FRUIT = new CustomFood("kiwi_fruit", 3, 0.35f);

    public static final Item MAPLE_SYRUP = new CustomFood("maple_syrup", new Builder().hunger(1).saturation(0.4f).setAlwaysEdible().effect(new EffectInstance(Effects.SPEED, 60), 0).build(), Items.GLASS_BOTTLE).drink();

    public static final Item MUD_BALL = new ItemBase("mud_ball");

}
