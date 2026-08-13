package ledtech.modid.block;

import java.util.function.Function;

import ledtech.modid.LedTech;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;

import net.minecraft.resources.ResourceKey;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

    public static final Block RED_LED = register(
            "red_led",
            LedBlock::new,
            BlockBehaviour.Properties.of()
                    .lightLevel(state -> state.getValue(LedBlock.LIT) ? 15 : 0)
                    .noOcclusion(),
            true
    );


    private static Block register(
            String name,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties properties,
            boolean registerItem
    ) {

        ResourceKey<Block> blockKey =
                ResourceKey.create(
                        Registries.BLOCK,
                        LedTech.id(name)
                );


        Block block =
                factory.apply(
                        properties.setId(blockKey)
                );


        if (registerItem) {

            ResourceKey<Item> itemKey =
                    ResourceKey.create(
                            Registries.ITEM,
                            LedTech.id(name)
                    );


            BlockItem blockItem =
                    new BlockItem(
                            block,
                            new Item.Properties()
                                    .setId(itemKey)
                                    .useBlockDescriptionPrefix()
                    );


            Registry.register(
                    BuiltInRegistries.ITEM,
                    itemKey,
                    blockItem
            );
        }


        return Registry.register(
                BuiltInRegistries.BLOCK,
                blockKey,
                block
        );
    }


    public static void initialize() {
        LedTech.LOGGER.info("Registering LED Tech blocks");

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.REDSTONE_BLOCKS)
                .register(creativeTab -> creativeTab.accept(RED_LED.asItem()));
    }
}