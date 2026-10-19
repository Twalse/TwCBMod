package net.twcbmod.translator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RussianRegistryDictionary {

    private static final Map<String, String> REGISTRY_MAP = new HashMap<>();

    static {
        // --- Items & Materials ---
        put("diamond", "алмаз", "алмазы", "алмаза");
        put("emerald", "изумруд", "изумруды", "изумруда");
        put("gold_ingot", "золото", "золотой_слиток", "золотые_слитки");
        put("iron_ingot", "железо", "железный_слиток", "железные_слитки");
        put("netherite_ingot", "незерит", "незеритовый_слиток");
        put("coal", "уголь", "угля");
        put("redstone", "редстоун", "красная_пыль");
        put("lapis_lazuli", "лазурит");
        put("quartz", "кварц");
        put("stick", "палка", "палки", "палочка");
        put("leather", "кожа", "кожу");
        put("feather", "перо", "перья");
        put("book", "книга", "книгу", "книги");
        put("paper", "бумага", "бумагу");
        put("slime_ball", "слизь", "сгусток_слизи");
        put("blaze_rod", "огненный_стержень", "стержень_ифрита");
        put("ender_pearl", "жемчуг_эндера", "эндер_жемчуг", "жемчужину_эндера");
        put("ender_eye", "око_эндера", "глаз_эндера");
        put("nether_star", "звезда_незера", "звезда_ада");
        put("totem_of_undying", "тотем", "тотем_бессмертия");
        put("elytra", "элитры");
        put("saddle", "седло");
        put("name_tag", "бирка", "бирку");
        put("lead", "поводок");
        put("bucket", "ведро");
        put("water_bucket", "ведро_воды");
        put("lava_bucket", "ведро_лавы");
        put("flint_and_steel", "огниво");
        put("compass", "компас");
        put("clock", "часы");
        put("fishing_rod", "удочка", "удочку");
        put("shears", "ножницы");
        put("shield", "щит");
        put("bow", "лук");
        put("crossbow", "арбалет");
        put("arrow", "стрела", "стрелы", "стрелу");
        put("trident", "трезубец");
        put("mace", "булава", "булаву");

        // --- Tools & Weapons ---
        put("diamond_sword", "алмазный_меч");
        put("netherite_sword", "незеритовый_меч");
        put("iron_sword", "железный_меч");
        put("golden_sword", "золотой_меч");
        put("stone_sword", "каменный_меч");
        put("wooden_sword", "деревянный_меч");

        put("diamond_pickaxe", "алмазная_кирка", "алмазную_кирку");
        put("netherite_pickaxe", "незеритовая_кирка", "незеритовая_кирка");
        put("iron_pickaxe", "железная_кирка", "железную_кирку");

        put("diamond_axe", "алмазный_топор");
        put("diamond_shovel", "алмазная_лопата", "алмазную_лопату");
        put("diamond_hoe", "алмазная_мотыга", "алмазную_мотыгу");

        // --- Armor ---
        put("diamond_helmet", "алмазный_шлем");
        put("diamond_chestplate", "алмазный_нагрудник");
        put("diamond_leggings", "алмазные_поножи");
        put("diamond_boots", "алмазные_ботинки");

        put("netherite_helmet", "незеритовый_шлем");
        put("netherite_chestplate", "незеритовый_нагрудник");
        put("netherite_leggings", "незеритовые_поножи");
        put("netherite_boots", "незеритовые_ботинки");

        put("iron_helmet", "железный_шлем");
        put("iron_chestplate", "железный_нагрудник");
        put("iron_leggings", "железные_поножи");
        put("iron_boots", "железные_ботинки");

        // --- Food ---
        put("apple", "яблоко", "яблоки");
        put("golden_apple", "золотое_яблоко");
        put("enchanted_golden_apple", "зачарованное_золотое_яблоко");
        put("bread", "хлеб");
        put("cooked_beef", "стейк", "жареная_говядина", "жареную_говядину");
        put("cooked_porkchop", "жареная_свинина", "жареную_свинину");
        put("cooked_chicken", "жареная_курица", "жареную_курицу");
        put("carrot", "морковь", "морковка");
        put("golden_carrot", "золотая_морковь", "золотую_морковь");
        put("potato", "картофель", "картошка");
        put("baked_potato", "печеный_картофель");
        put("melon_slice", "арбуз", "ломтик_арбуза");
        put("cake", "торт");
        put("cookie", "печенье");
        put("pumpkin_pie", "тыквенный_пирог");
        put("rotten_flesh", "гнилая_плоть");

        // --- Blocks ---
        put("air", "воздух");
        put("stone", "камень");
        put("cobblestone", "булыжник");
        put("dirt", "земля", "землю");
        put("grass_block", "дерн", "блок_травы", "трава");
        put("sand", "песок");
        put("gravel", "гравий");
        put("glass", "стекло");
        put("oak_log", "дуб", "дубовое_бревно");
        put("oak_planks", "доски", "дубовые_доски");
        put("bricks", "кирпичи");
        put("stone_bricks", "каменные_кирпичи");
        put("obsidian", "обсидиан");
        put("bedrock", "бедрок", "коренная_порода");
        put("water", "вода", "воду");
        put("lava", "лава", "лаву");
        put("fire", "огонь");
        put("tnt", "тнт", "динамит");
        put("command_block", "командный_блок");
        put("redstone_block", "редстоун_блок", "блок_редстоуна");
        put("diamond_block", "алмазный_блок");
        put("gold_block", "золотой_блок");
        put("iron_block", "железный_блок");
        put("emerald_block", "изумрудный_блок");
        put("netherite_block", "незеритовый_блок");
        put("glowstone", "светокамень");
        put("sea_lantern", "морской_фонарь");
        put("torch", "факел");
        put("lantern", "фонарь");
        put("chest", "сундук");
        put("barrel", "бочка", "бочку");
        put("crafting_table", "верстак");
        put("furnace", "печь");
        put("anvil", "наковальня", "наковальню");
        put("enchanting_table", "стол_зачаровывания");
        put("beacon", "маяк");
        put("sponge", "губка", "губку");
        put("ice", "лед");
        put("snow_block", "снег", "блок_снега");
        put("white_wool", "шерсть", "белая_шерсть");
        put("white_concrete", "бетон", "белый_бетон");
        put("barrier", "барьер");
        put("light", "свет");

        // --- Entities ---
        put("zombie", "зомби");
        put("skeleton", "скелет");
        put("creeper", "крипер");
        put("spider", "паук");
        put("enderman", "эндермен");
        put("witch", "ведьма", "ведьму");
        put("slime", "слизень");
        put("ghast", "гаст");
        put("blaze", "ифрит");
        put("wither_skeleton", "визер_скелет");
        put("piglin", "пиглин");
        put("hoglin", "хоглин");
        put("shulker", "шалкер");
        put("warden", "хранитель", "варден");
        put("wither", "визер", "иссушитель");
        put("ender_dragon", "дракон", "эндер_дракон");
        put("villager", "житель", "крестьянин");
        put("iron_golem", "железный_голем");
        put("snow_golem", "снежный_голем");
        put("cow", "корова", "корову");
        put("pig", "свинья", "свинью");
        put("sheep", "овца", "овцу");
        put("chicken", "курица", "курицу");
        put("horse", "лошадь");
        put("wolf", "волк", "волка");
        put("cat", "кошка", "кошку", "кот");
        put("fox", "лиса", "лису");
        put("bee", "пчела", "пчелу");
        put("axolotl", "аксолотль");
        put("frog", "лягушка", "лягушку");
        put("camel", "верблюд");
        put("sniffer", "нюхач");
        put("armadillo", "броненосец");
        put("breeze", "вихрь", "бриз");
        put("lightning_bolt", "молния", "молнию");
        put("armor_stand", "стойка_для_брони");
        put("boat", "лодка", "лодку");
        put("minecart", "вагонетка", "вагонетку");
        put("experience_orb", "опыт_сфера", "сфера_опыта");

        // --- Status Effects ---
        put("speed", "скорость");
        put("slowness", "медлительность");
        put("haste", "спешка");
        put("mining_fatigue", "утомление");
        put("strength", "сила");
        put("instant_health", "мгновенное_лечение", "исцеление");
        put("instant_damage", "мгновенный_урон");
        put("jump_boost", "прыгучесть");
        put("nausea", "тошнота");
        put("regeneration", "регенерация");
        put("resistance", "сопротивление");
        put("fire_resistance", "огнестойкость");
        put("water_breathing", "водное_дыхание");
        put("invisibility", "невидимость");
        put("blindness", "слепота");
        put("night_vision", "ночное_зрение");
        put("hunger", "голод");
        put("weakness", "слабость");
        put("poison", "отравление");
        put("wither", "иссушение");
        put("health_boost", "прилив_здоровья");
        put("absorption", "поглощение");
        put("saturation", "насыщенность");
        put("glowing", "свечение");
        put("levitation", "левитация");
        put("luck", "удача");
        put("unluck", "невезение");
        put("slow_falling", "плавное_падение");
        put("darkness", "тьма");
    }

    private static void put(String vanillaId, String... russianNames) {
        for (String name : russianNames) {
            REGISTRY_MAP.put(name.toLowerCase(Locale.ROOT), vanillaId);
        }
    }

    public static String getVanillaId(String russianName) {
        if (russianName == null) return null;
        return REGISTRY_MAP.get(russianName.toLowerCase(Locale.ROOT));
    }

    public static Map<String, String> getRegistryMap() {
        return Collections.unmodifiableMap(REGISTRY_MAP);
    }
}
