package me.anton.sickcore.games.defaults.heads;

import me.anton.sickcore.games.defaults.all.HeadDBAPI;
import me.anton.sickcore.api.utils.common.string.EnumUtils;
import me.anton.sickcore.api.utils.common.string.StringUtils;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class MobHeads {

    HeadDatabaseAPI headAPI = new HeadDBAPI().getApi();

    public ItemStack getMobHead(Entity entity){
        int id = 0;

        switch (entity.getType()){
            case WANDERING_TRADER -> {
                id = 25676;
                break;
            }
            case ZOMBIE_VILLAGER -> {
                id = 27597;
                break;
            }
            case SKELETON_HORSE -> {
                id = 6013;
                break;
            }
            case ELDER_GUARDIAN -> {
                id = 25357;
                break;
            }
            case VINDICATOR -> {
                id = 28323;
                break;
            }
            case MAGMA_CUBE -> {
                id = 5421;
                break;
            }
            case IRON_GOLEM -> {
                id = 341;
                break;
            }
            case VILLAGER -> {
                Villager villager = (Villager) entity;
                switch (villager.getProfession()){
                    case NONE -> {
                        id = 42949;
                        break;
                    }
                    case MASON -> {
                        id = 32914;
                        break;
                    }
                    case CLERIC -> {
                        id = 28910;
                        break;
                    }
                    case FARMER -> {
                        id = 32881;
                        break;
                    }
                    case NITWIT -> {
                        id = 23089;
                        break;
                    }
                    case ARMORER -> {
                        id = 32853;
                        break;
                    }
                    case BUTCHER -> {
                        id = 32860;
                        break;
                    }
                    case FLETCHER -> {
                        id = 23759;
                        break;
                    }
                    case SHEPHERD -> {
                        id = 32930;
                        break;
                    }
                    case FISHERMAN -> {
                        id = 32888;
                        break;
                    }
                    case LIBRARIAN -> {
                        id = 32903;
                        break;
                    }
                    case TOOLSMITH -> {
                        id = 32937;
                        break;
                    }
                    case WEAPONSMITH -> {
                        id = 32944;
                        break;
                    }
                    case CARTOGRAPHER -> {
                        id = 32867;
                        break;
                    }
                    case LEATHERWORKER -> {
                        id = 32902;
                        break;
                    }
                }
            }
            case PILLAGER -> {
                id = 25149;
                break;
            }
            case GUARDIAN -> {
                id = 3135;
                break;
            }
            case ENDERMAN -> {
                id = 318;
                break;
            }
            case SNOWMAN -> {
                id = 24080;
                break;
            }
            case SHULKER -> {
                id = 38317;
                break;
            }
            case RAVAGER -> {
                id = 28196;
                break;
            }
            case PHANTOM -> {
                id = 41060;
                break;
            }
            case DROWNED -> {
                id = 41025;
                break;
            }
            case ZOGLIN -> {
                id = 36388;
                break;
            }
            case WITHER -> {
                id = 45024;
                break;
            }
            case TURTLE -> {
                id = 17929;
                break;
            }
            case EVOKER -> {
                id = 3862;
                break;
            }
            case WITCH -> {
                id = 35861;
                break;
            }
            case STRAY -> {
                id = 22401;
                break;
            }
            case SLIME -> {
                id = 22210;
                break;
            }
            case GHAST -> {
                id = 321;
                break;
            }
            case BLAZE -> {
                id = 47778;
                break;
            }
            case HUSK -> {
                id = 37860;
                break;
            }
            case VEX -> {
                id = 3080;
                break;
            }
            case FOX -> {
                Fox fox = (Fox) entity;
                switch (fox.getFoxType()){
                    case RED -> {
                        id = 26327;
                        break;
                    }
                    case SNOW -> {
                        id = 26329;
                        break;
                    }
                }
            }
            case BEE -> {
                id = 31264;
                break;
            }
            case AXOLOTL -> {
                Axolotl axolotl = (Axolotl) entity;
                switch (axolotl.getVariant()){
                    case BLUE -> {
                        id = 46414;
                        break;
                    }
                    case GOLD -> {
                        id = 41590;
                        break;
                    }
                    case CYAN -> {
                        id = 48001;
                        break;
                    }
                    case LUCY -> {
                        id = 45783;
                        break;
                    }
                    case WILD -> {
                        id = 41591;
                        break;
                    }
                }
            }
            case SHEEP -> {
                Sheep sheep = (Sheep) entity;
                switch (sheep.getColor()){
                    case CYAN -> {
                        id = 3907;
                        break;
                    }
                    case BLUE -> {
                        id = 3909;
                        break;
                    }
                    case MAGENTA -> {
                        id = 3900;
                        break;
                    }
                    case LIGHT_GRAY -> {
                        id = 3906;
                        break;
                    }
                    case LIGHT_BLUE -> {
                        id = 3916;
                        break;
                    }
                    case BLACK -> {
                        id = 3913;
                        break;
                    }
                    case GRAY -> {
                        id = 3905;
                        break;
                    }
                    case GREEN -> {
                        id = 3911;
                        break;
                    }
                    case RED -> {
                        id = 3912;
                        break;
                    }
                    case WHITE -> {
                        id = 334;
                        break;
                    }
                    case YELLOW -> {
                        id = 3902;
                        break;
                    }
                    case LIME -> {
                        id = 3903;
                        break;
                    }
                    case PINK -> {
                        id = 3915;
                        break;
                    }
                    case BROWN -> {
                        id = 3910;
                        break;
                    }
                    case ORANGE -> {
                        id = 3899;
                        break;
                    }
                    case PURPLE -> {
                        id = 3980;
                        break;
                    }
                }
            }
            case BAT -> {
                id = 26033;
                break;
            }
            case CAT -> {
                Cat cat = (Cat) entity;
                switch (cat.getCatType()){
                    case WHITE -> {
                        id = 39024;
                        break;
                    }
                    case RED -> {
                        id = 38983;
                        break;
                    }
                    case BLACK ->{
                        id = 39088;
                        break;
                    }
                    case TABBY -> {
                        id = 39040;
                        break;
                    }
                    case CALICO -> {
                        id = 38905;
                        break;
                    }
                    case JELLIE -> {
                        id = 38968;
                        break;
                    }
                    case PERSIAN -> {
                        id = 38953;
                        break;
                    }
                    case RAGDOLL -> {
                        id = 39072;
                        break;
                    }
                    case SIAMESE -> {
                        id = 39056;
                        break;
                    }
                    case ALL_BLACK -> {
                        id = 38933;
                        break;
                    }
                    case BRITISH_SHORTHAIR -> {
                        id = 38921;
                        break;
                    }
                }
            }
            case COD -> {
                id = 17898;
                break;
            }
            case COW -> {
                id = 22866;
                break;
            }
            case PIG -> {
                id = 3377;
                break;
            }
            case GOAT -> {
                id = 42452;
                break;
            }
            case MULE -> {
                id = 8918;
                break;
            }
            case WOLF -> {
                id = 38471;
                break;
            }
            case HORSE -> {
                Horse horse = (Horse) entity;
                switch (horse.getColor()){
                    case WHITE -> {
                        id = 34340;
                        break;
                    }
                    case BROWN -> {
                        id = 14418;
                        break;
                    }
                    case GRAY -> {
                        id = 43021;
                        break;
                    }
                    case BLACK -> {
                        id = 34342;
                        break;
                    }
                    case CREAMY -> {
                        id = 34339;
                        break;
                    }
                    case CHESTNUT -> {
                        id = 34344;
                        break;
                    }
                    case DARK_BROWN -> {
                        id = 42534;
                        break;
                    }
                }
            }
            case LLAMA -> {
                Llama llama = (Llama) entity;
                switch (llama.getColor()){
                    case CREAMY -> {
                        id = 26964;
                        break;
                    }
                    case GRAY -> {
                        id = 3930;
                        break;
                    }
                    case BROWN -> {
                        id = 3929;
                        break;
                    }
                    case WHITE -> {
                        id = 3931;
                        break;
                    }
                }
            }
            case PANDA -> {
                id = 23593;
                break;
            }
            case SQUID -> {
                id = 338;
                break;
            }
            case DONKEY -> {
                id = 38017;
                break;
            }
            case HOGLIN -> {
                id = 34783;
                break;
            }
            case OCELOT -> {
                id = 47969;
                break;
            }
            case PARROT -> {
                Parrot parrot = (Parrot) entity;
                switch (parrot.getVariant()){
                    case GRAY -> {
                        id = 6536;
                        break;
                    }
                    case RED -> {
                        id = 39666;
                        break;
                    }
                    case GREEN -> {
                        id = 25351;
                        break;
                    }
                    case BLUE -> {
                        id = 6816;
                        break;
                    }
                    case CYAN -> {
                        id = 6537;
                        break;
                    }
                }
            }
            case PIGLIN -> {
                id = 34891;
                break;
            }
            case RABBIT -> {
                id = 43626;
                break;
            }
            case SALMON -> {
                id = 17809;
                break;
            }
            case SPIDER -> {
                id = 32706;
                break;
            }
            case CHICKEN -> {
                id = 336;
                break;
            }
            case DOLPHIN -> {
                id = 16799;
                break;
            }
            case STRIDER -> {
                id = 48214;
                break;
            }
            case ENDERMITE -> {
                id = 18427;
                break;
            }
            case GLOW_SQUID -> {
                id = 42563;
                break;
            }
            case POLAR_BEAR -> {
                id = 4177;
                break;
            }
            case PUFFERFISH -> {
                id = 45707;
                break;
            }
            case SILVERFISH -> {
                id = 3936;
                break;
            }
            case CAVE_SPIDER -> {
                id = 32707;
                break;
            }
            case MUSHROOM_COW -> {
                MushroomCow cow = (MushroomCow) entity;
                switch (cow.getVariant()){
                    case RED -> {
                        id = 339;
                        break;
                    }
                    case BROWN -> {
                        id = 26552;
                        break;
                    }
                }
            }
            case PIGLIN_BRUTE -> {
                id = 40777;
                break;
            }
            case TRADER_LLAMA -> {
                TraderLlama llama = (TraderLlama) entity;
                switch (llama.getColor()){
                    case BROWN -> {
                        id = 26960;
                        break;
                    }
                    case GRAY -> {
                        id = 26962;
                        break;
                    }
                    case WHITE -> {
                        id = 26961;
                        break;
                    }
                    case CREAMY -> {
                        id = 26963;
                        break;
                    }
                }
            }
            case TROPICAL_FISH -> {
                id = 30233;
                break;
            }
            case ZOMBIFIED_PIGLIN -> {
                id = 324;
                break;
            }
        }

        String name = "";
        if (entity.getType() == EntityType.VILLAGER){
            Villager villager = (Villager) entity;
            name = StringUtils.capitalize(EnumUtils.toName(villager.getProfession()));
        }else if (entity.getType() == EntityType.WOLF){
            Fox wolf = (Fox) entity;
            name = StringUtils.capitalize(EnumUtils.toName(wolf.getFoxType())) + " Fox";
        }else if (entity.getType() == EntityType.AXOLOTL){
            Axolotl axolotl = (Axolotl) entity;
            name = StringUtils.capitalize(EnumUtils.toName(axolotl.getVariant())) + " Axolotl";
        }else if (entity.getType() == EntityType.SHEEP){
            Sheep sheep = (Sheep) entity;
            name = StringUtils.capitalize(EnumUtils.toName(sheep.getColor())) + " Sheep";
        }else if (entity.getType() == EntityType.CAT){
            Cat cat = (Cat) entity;
            name = StringUtils.capitalize(EnumUtils.toName(cat.getCatType())) + " Cat";
        }else if (entity.getType() == EntityType.HORSE){
            Horse cat = (Horse) entity;
            name = StringUtils.capitalize(EnumUtils.toName(cat.getColor())) + " Horse";
        }else if (entity.getType() == EntityType.LLAMA){
            Llama cat = (Llama) entity;
            name = StringUtils.capitalize(EnumUtils.toName(cat.getColor())) + " LLama";
        }else if (entity.getType() == EntityType.PARROT){
            Parrot cat = (Parrot) entity;
            name = StringUtils.capitalize(EnumUtils.toName(cat.getVariant())) + " Parrot";
        }else if (entity.getType() == EntityType.MUSHROOM_COW){
            MushroomCow cat = (MushroomCow) entity;
            name = StringUtils.capitalize(EnumUtils.toName(cat.getVariant())) + " Mushroom Cow";
        }else if (entity.getType() == EntityType.TRADER_LLAMA){
            TraderLlama cat = (TraderLlama) entity;
            name = StringUtils.capitalize(EnumUtils.toName(cat.getColor())) + " Trader LLama";
        }else {
            name = StringUtils.capitalize(EnumUtils.toName(entity.getType()));
        }

        ItemStack head = headAPI.getItemHead(String.valueOf(id));

        ItemBuilder itemBuilder = new ItemBuilder(head).setName("§e" + name).addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return itemBuilder.build();
    }

    public ItemStack getSpecialHead(Entity entity){
        int id = 0;
        switch (entity.getType()){
            case SHEEP -> id = 31476;
        }
        ItemStack head = headAPI.getItemHead(String.valueOf(id));

        ItemBuilder itemBuilder = new ItemBuilder(head).setName("§5" + EnumUtils.toName(entity.getType())).addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return itemBuilder.build();
    }

}
