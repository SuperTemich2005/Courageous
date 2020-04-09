package co.uk.squishling.courageous.recipes;

import co.uk.squishling.courageous.recipes.ArchitectTable.ArchitectsTableRecipe;
import co.uk.squishling.courageous.recipes.distiller.DistillerRecipe;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<ArchitectsTableRecipe>> ARCHITECT = ModRecipes.RECIPE_SERIALIZERS.register("architect", () -> new ArchitectsTableRecipe.Serializer<>(ArchitectsTableRecipe::new));
    public static final RegistryObject<IRecipeSerializer<DistillerRecipe>> DISTILLER = ModRecipes.RECIPE_SERIALIZERS.register("distiller", () -> new DistillerRecipe.Serializer<>(DistillerRecipe::new));

}
