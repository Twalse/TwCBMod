package net.twcbmod.gui;

import net.twcbmod.translator.CommandTranslationDictionary;

import java.util.HashMap;
import java.util.Map;

public class CommandUsageDatabase {

    public static class CommandInfo {
        public final String schema;
        public final String description;

        public CommandInfo(String schema, String description) {
            this.schema = schema;
            this.description = description;
        }
    }

    private static final Map<String, CommandInfo> INFO_MAP = new HashMap<>();

    static {
        put("teleport", "/телепортировать <кого> <x> <y> <z>", "Перемещает игрока или сущность в указанные координаты или к другой цели");
        put("give", "/выдать <кому> <предмет> [количество]", "Выдает указанный предмет и количество игроку");
        put("setblock", "/установитьблок <x> <y> <z> <блок>", "Устанавливает указанный блок в заданных координатах");
        put("fill", "/заполнить <x1> <y1> <z1> <x2> <y2> <z2> <блок>", "Заполняет область указанным блоком");
        put("summon", "/призвать <сущность> [x] [y] [z]", "Спавнит выбранную сущность в мире");
        put("kill", "/убить <кого>", "Уничтожает выбранных игроков или сущностей");
        put("clear", "/очистить <кого> [предмет] [количество]", "Очищает инвентарь игрока");
        put("effect", "/эффект <выдать|очистить> <кому> <эффект> [время] [уровень]", "Управляет эффектами состояния сущностей");
        put("gamemode", "/режим <выживание|креатив|приключение|наблюдатель> [кому]", "Изменяет режим игры игрока");
        put("time", "/время <установить|добавить> <день|ночь|число>", "Управляет временем суток в мире");
        put("weather", "/погода <ясно|дождь|гроза> [время]", "Изменяет погодные условия в мире");
        put("execute", "/выполнить <как|в|если|...> запустить <команда>", "Выполняет команду с расширенными условиями и контекстом");
        put("scoreboard", "/табло <цели|игроки> ...", "Управляет системами очков и счетчиков");
        put("say", "/сказать <сообщение>", "Отправляет сообщение всем игрокам в чат");
        put("title", "/титул <кому> <title|subtitle|actionbar> <текст>", "Отображает крупный текст на экране игрока");
        put("difficulty", "/сложность <мирная|легкая|нормальная|сложная>", "Устанавливает уровень сложности игры");
        put("gamerule", "/правило <имя> [значение]", "Просматривает или изменяет игровые правила мира");
    }

    private static void put(String vanillaRoot, String schema, String description) {
        CommandInfo info = new CommandInfo(schema, description);
        INFO_MAP.put(vanillaRoot, info);
    }

    public static CommandInfo getInfo(String commandName) {
        if (commandName == null || commandName.isEmpty()) return null;
        String vanilla = CommandTranslationDictionary.getRootCommand(commandName);
        if (vanilla == null) {
            vanilla = commandName.toLowerCase();
        }
        return INFO_MAP.get(vanilla);
    }
}
