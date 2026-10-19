package net.twcbmod.translator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandTranslationDictionary {

    private static final Map<String, String> ROOT_COMMANDS = new HashMap<>();
    private static final Map<String, String> SUBCOMMAND_KEYWORDS = new HashMap<>();
    private static final Map<String, String> TARGET_SELECTORS = new HashMap<>();
    private static final Map<String, String> SELECTOR_ARG_KEYS = new HashMap<>();
    private static final Map<String, String> SELECTOR_ARG_VALUES = new HashMap<>();

    static {
        // --- Root Commands ---
        // World & Blocks
        putRoot("fill", "заполнить", "залить");
        putRoot("setblock", "установитьблок", "сетблок", "блок");
        putRoot("clone", "клонировать", "скопировать");
        putRoot("fillbiome", "заполнитьбиом", "биом");
        putRoot("place", "разместить", "структура");
        putRoot("setworldspawn", "спавнмира", "точкаспавнамира");
        putRoot("weather", "погода");
        putRoot("time", "время");
        putRoot("difficulty", "сложность");
        putRoot("gamerule", "правило", "игровоеправило");
        putRoot("worldborder", "границамира", "граница");
        putRoot("forceload", "прогрузчик", "грузитьчанки");
        putRoot("seed", "сид", "зерно");
        putRoot("tick", "тик");

        // Entities & Players
        putRoot("teleport", "телепортировать", "телепорт", "тп");
        putRoot("summon", "призвать", "спавн");
        putRoot("kill", "убить", "уничтожить");
        putRoot("give", "выдать", "дать");
        putRoot("clear", "очистить", "очиститьинвентарь");
        putRoot("effect", "эффект");
        putRoot("enchant", "зачаровать", "чары");
        putRoot("experience", "опыт");
        putRoot("gamemode", "режим", "гм");
        putRoot("defaultgamemode", "режимпоумолчанию");
        putRoot("attribute", "атрибут");
        putRoot("damage", "урон");
        putRoot("ride", "оседлать", "ехать");
        putRoot("heal", "исцелить");
        putRoot("spectate", "наблюдать");
        putRoot("spawnpoint", "точкаспавна", "спавнпоинт");
        putRoot("spreadplayers", "раскидатьигроков", "распределить");
        putRoot("tag", "тег", "метка");
        putRoot("team", "команда", "тима");
        putRoot("advancement", "достижение", "ачивка");
        putRoot("recipe", "рецепт");
        putRoot("item", "предмет");
        putRoot("loot", "лут", "добыча");

        // Logic, Data & Automation
        putRoot("execute", "выполнить", "исполнить");
        putRoot("scoreboard", "табло", "счетчик", "скорборд");
        putRoot("data", "данные", "нбт");
        putRoot("function", "функция");
        putRoot("return", "вернуть", "возврат");
        putRoot("schedule", "расписание", "таймер");
        putRoot("trigger", "триггер");
        putRoot("random", "рандом", "случайно");
        putRoot("datapack", "датапак");

        // Visuals, Audio & Chat
        putRoot("particle", "частицы", "партиклы");
        putRoot("playsound", "звук", "игратьзвук");
        putRoot("stopsound", "стопзвук", "остановитьзвук");
        putRoot("title", "титул", "титры", "экранныйтекст");
        putRoot("tellraw", "текстjson", "теллрав");
        putRoot("say", "сказать", "объявить");
        putRoot("msg", "лс", "шепнуть", "сообщение");
        putRoot("teammsg", "чаткоманды");
        putRoot("me", "я", "действие");
        putRoot("bossbar", "боссбар", "полосабосса");

        // Server, Utility & Admin
        putRoot("locate", "найти", "локация");
        putRoot("reload", "перезагрузить");
        putRoot("help", "помощь", "справка");
        putRoot("list", "список", "игроки");
        putRoot("op", "оп", "датьоп");
        putRoot("deop", "деоп", "забратьоп");
        putRoot("kick", "кик", "выгнать");
        putRoot("ban", "бан", "забанить");
        putRoot("ban-ip", "банапи");
        putRoot("banlist", "банлист", "списокбанов");
        putRoot("pardon", "разбан", "разбанить");
        putRoot("pardon-ip", "разбанапи");
        putRoot("whitelist", "белыйсписок", "вайтлист");
        putRoot("stop", "стоп", "выключитьсервер");
        putRoot("publish", "опубликовать", "открытьдлясети");
        putRoot("setidletimeout", "таймаутафк");
        putRoot("transfer", "перенести", "трансфер");
        putRoot("debug", "отладка", "дебаг");
        putRoot("perf", "производительность");
        putRoot("jfr", "профилирование");

        // --- Subcommands & Keyword Literals ---
        // Execute keywords
        SUBCOMMAND_KEYWORDS.put("как", "as");
        SUBCOMMAND_KEYWORDS.put("в", "at");
        SUBCOMMAND_KEYWORDS.put("наместе", "at");
        SUBCOMMAND_KEYWORDS.put("позиция", "positioned");
        SUBCOMMAND_KEYWORDS.put("поворот", "rotated");
        SUBCOMMAND_KEYWORDS.put("лицомк", "facing");
        SUBCOMMAND_KEYWORDS.put("выровнять", "align");
        SUBCOMMAND_KEYWORDS.put("якорь", "anchored");
        SUBCOMMAND_KEYWORDS.put("измерение", "in");
        SUBCOMMAND_KEYWORDS.put("на", "on");
        SUBCOMMAND_KEYWORDS.put("если", "if");
        SUBCOMMAND_KEYWORDS.put("еслине", "unless");
        SUBCOMMAND_KEYWORDS.put("блок", "block");
        SUBCOMMAND_KEYWORDS.put("блоки", "blocks");
        SUBCOMMAND_KEYWORDS.put("сущность", "entity");
        SUBCOMMAND_KEYWORDS.put("счет", "score");
        SUBCOMMAND_KEYWORDS.put("совпадает", "matches");
        SUBCOMMAND_KEYWORDS.put("сохранить", "store");
        SUBCOMMAND_KEYWORDS.put("результат", "result");
        SUBCOMMAND_KEYWORDS.put("успех", "success");
        SUBCOMMAND_KEYWORDS.put("запустить", "run");

        // Gamemodes
        SUBCOMMAND_KEYWORDS.put("выживание", "survival");
        SUBCOMMAND_KEYWORDS.put("творческий", "creative");
        SUBCOMMAND_KEYWORDS.put("креатив", "creative");
        SUBCOMMAND_KEYWORDS.put("приключение", "adventure");
        SUBCOMMAND_KEYWORDS.put("наблюдатель", "spectator");
        SUBCOMMAND_KEYWORDS.put("спектатор", "spectator");

        // Time & Weather
        SUBCOMMAND_KEYWORDS.put("установить", "set");
        SUBCOMMAND_KEYWORDS.put("добавить", "add");
        SUBCOMMAND_KEYWORDS.put("день", "day");
        SUBCOMMAND_KEYWORDS.put("ночь", "night");
        SUBCOMMAND_KEYWORDS.put("полдень", "noon");
        SUBCOMMAND_KEYWORDS.put("полночь", "midnight");
        SUBCOMMAND_KEYWORDS.put("ясно", "clear");
        SUBCOMMAND_KEYWORDS.put("дождь", "rain");
        SUBCOMMAND_KEYWORDS.put("гроза", "thunder");

        // Difficulty
        SUBCOMMAND_KEYWORDS.put("мирная", "peaceful");
        SUBCOMMAND_KEYWORDS.put("легкая", "easy");
        SUBCOMMAND_KEYWORDS.put("нормальная", "normal");
        SUBCOMMAND_KEYWORDS.put("сложная", "hard");

        // Block/Effect operations
        SUBCOMMAND_KEYWORDS.put("заменить", "replace");
        SUBCOMMAND_KEYWORDS.put("разрушить", "destroy");
        SUBCOMMAND_KEYWORDS.put("оставить", "keep");
        SUBCOMMAND_KEYWORDS.put("полый", "hollow");
        SUBCOMMAND_KEYWORDS.put("контур", "outline");
        SUBCOMMAND_KEYWORDS.put("выдать", "give");
        SUBCOMMAND_KEYWORDS.put("очистить", "clear");

        // Scoreboard & Data
        SUBCOMMAND_KEYWORDS.put("цели", "objectives");
        SUBCOMMAND_KEYWORDS.put("игроки", "players");
        SUBCOMMAND_KEYWORDS.put("удалить", "remove");
        SUBCOMMAND_KEYWORDS.put("сбросить", "reset");
        SUBCOMMAND_KEYWORDS.put("получить", "get");
        SUBCOMMAND_KEYWORDS.put("слить", "merge");
        SUBCOMMAND_KEYWORDS.put("изменить", "modify");

        // Target Selectors
        TARGET_SELECTORS.put("@все", "@a");
        TARGET_SELECTORS.put("@игрок", "@p");
        TARGET_SELECTORS.put("@ближайший", "@p");
        TARGET_SELECTORS.put("@случайный", "@r");
        TARGET_SELECTORS.put("@себя", "@s");
        TARGET_SELECTORS.put("@я", "@s");
        TARGET_SELECTORS.put("@сущности", "@e");

        // Selector Argument Keys
        SELECTOR_ARG_KEYS.put("тип", "type");
        SELECTOR_ARG_KEYS.put("дистанция", "distance");
        SELECTOR_ARG_KEYS.put("лимит", "limit");
        SELECTOR_ARG_KEYS.put("тег", "tag");
        SELECTOR_ARG_KEYS.put("имя", "name");
        SELECTOR_ARG_KEYS.put("режим", "gamemode");
        SELECTOR_ARG_KEYS.put("сортировка", "sort");

        // Selector Argument Values
        SELECTOR_ARG_VALUES.put("ближайшие", "nearest");
        SELECTOR_ARG_VALUES.put("дальние", "furthest");
        SELECTOR_ARG_VALUES.put("случайные", "random");
        SELECTOR_ARG_VALUES.put("произвольные", "arbitrary");
        SELECTOR_ARG_VALUES.put("выживание", "survival");
        SELECTOR_ARG_VALUES.put("творческий", "creative");
        SELECTOR_ARG_VALUES.put("креатив", "creative");
        SELECTOR_ARG_VALUES.put("приключение", "adventure");
        SELECTOR_ARG_VALUES.put("наблюдатель", "spectator");
        SELECTOR_ARG_VALUES.put("спектатор", "spectator");
    }

    private static void putRoot(String vanilla, String... aliases) {
        for (String alias : aliases) {
            ROOT_COMMANDS.put(alias.toLowerCase(), vanilla);
        }
    }

    public static String getRootCommand(String alias) {
        if (alias == null) return null;
        return ROOT_COMMANDS.get(alias.toLowerCase());
    }

    public static String getSubcommandKeyword(String word) {
        if (word == null) return null;
        return SUBCOMMAND_KEYWORDS.get(word.toLowerCase());
    }

    public static String getTargetSelector(String selector) {
        if (selector == null) return null;
        return TARGET_SELECTORS.get(selector.toLowerCase());
    }

    public static String getSelectorArgKey(String key) {
        if (key == null) return null;
        return SELECTOR_ARG_KEYS.get(key.toLowerCase());
    }

    public static String getSelectorArgValue(String value) {
        if (value == null) return null;
        return SELECTOR_ARG_VALUES.get(value.toLowerCase());
    }

    public static Map<String, String> getRootCommandsMap() {
        return Collections.unmodifiableMap(ROOT_COMMANDS);
    }

    public static Map<String, String> getSubcommandKeywordsMap() {
        return Collections.unmodifiableMap(SUBCOMMAND_KEYWORDS);
    }

    public static Map<String, String> getTargetSelectorsMap() {
        return Collections.unmodifiableMap(TARGET_SELECTORS);
    }
}
