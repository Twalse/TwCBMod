package net.twcbmod.translator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComprehensiveTranslationTest {

    @Test
    public void testWorldAndBlockCommands() {
        assertEquals("fill 0 0 0 10 10 10 stone replace dirt",
                CommandPreprocessor.translateCommand("/заполнить ноль ноль ноль десять десять десять stone заменить dirt"));
        assertEquals("setblock ~ ~1 ~ stone",
                CommandPreprocessor.translateCommand("/сетблок ~ ~один ~ stone"));
        assertEquals("clone 0 0 0 10 10 10 20 20 20 replace normal",
                CommandPreprocessor.translateCommand("/клонировать ноль ноль ноль десять десять десять двадцать двадцать двадцать заменить нормальная"));
        assertEquals("fillbiome ~ ~ ~ ~10 ~10 ~10 minecraft:plains",
                CommandPreprocessor.translateCommand("/заполнитьбиом ~ ~ ~ ~десять ~десять ~десять minecraft:plains"));
        assertEquals("weather rain", CommandPreprocessor.translateCommand("/погода дождь"));
        assertEquals("weather thunder", CommandPreprocessor.translateCommand("/погода гроза"));
        assertEquals("time set day", CommandPreprocessor.translateCommand("/время установить день"));
        assertEquals("time set night", CommandPreprocessor.translateCommand("/время установить ночь"));
        assertEquals("difficulty hard", CommandPreprocessor.translateCommand("/сложность сложная"));
        assertEquals("gamerule keepInventory true", CommandPreprocessor.translateCommand("/правило keepInventory true"));
    }

    @Test
    public void testEntityAndPlayerCommands() {
        assertEquals("teleport @a 0 64 0", CommandPreprocessor.translateCommand("/телепортировать @все ноль шестьдесят четыре ноль"));
        assertEquals("summon zombie ~ ~ ~", CommandPreprocessor.translateCommand("/призвать zombie ~ ~ ~"));
        assertEquals("kill @e[type=zombie]", CommandPreprocessor.translateCommand("/убить @сущности[тип=zombie]"));
        assertEquals("give @p diamond 64", CommandPreprocessor.translateCommand("/выдать @игрок diamond шестьдесят четыре"));
        assertEquals("clear @s", CommandPreprocessor.translateCommand("/очистить @себя"));
        assertEquals("effect give @p speed 30 1", CommandPreprocessor.translateCommand("/эффект выдать @игрок speed тридцать 1"));
        assertEquals("experience add @p 100 points", CommandPreprocessor.translateCommand("/опыт добавить @игрок сто points"));
        assertEquals("gamemode survival @p", CommandPreprocessor.translateCommand("/режим выживание @игрок"));
        assertEquals("gamemode creative @s", CommandPreprocessor.translateCommand("/гм креатив @я"));
        assertEquals("gamemode adventure @r", CommandPreprocessor.translateCommand("/режим приключение @случайный"));
        assertEquals("gamemode spectator @a", CommandPreprocessor.translateCommand("/режим наблюдатель @все"));
    }

    @Test
    public void testExecuteChains() {
        assertEquals("execute as @p at @s positioned ~ 10 ~ run setblock ~ ~ ~ stone",
                CommandPreprocessor.translateCommand("/выполнить как @игрок в @себя позиция ~ десять ~ запустить установитьблок ~ ~ ~ stone"));
        assertEquals("execute if block 0 64 0 stone run say block is stone",
                CommandPreprocessor.translateCommand("/выполнить если блок ноль шестьдесят четыре ноль stone запустить сказать block is stone"));
    }

    @Test
    public void testVisualsAudioChat() {
        assertEquals("say \"Привет мир! 1 2 3\"", CommandPreprocessor.translateCommand("/сказать \"Привет мир! 1 2 3\""));
        assertEquals("tellraw @a \"Текст с запустить и ноль\"", CommandPreprocessor.translateCommand("/теллрав @все \"Текст с запустить и ноль\""));
        assertEquals("title @a title \"Заголовок\"", CommandPreprocessor.translateCommand("/титул @все title \"Заголовок\""));
        assertEquals("playsound entity.experience_orb.pickup master @a", CommandPreprocessor.translateCommand("/игратьзвук entity.experience_orb.pickup master @все"));
    }

    @Test
    public void testNumberWordCombinations() {
        assertEquals("0", WordToNumberParser.parseNumbersInString("ноль"));
        assertEquals("15", WordToNumberParser.parseNumbersInString("пятнадцать"));
        assertEquals("42", WordToNumberParser.parseNumbersInString("сорок два"));
        assertEquals("305", WordToNumberParser.parseNumbersInString("триста пять"));
        assertEquals("1234", WordToNumberParser.parseNumbersInString("тысяча двести тридцать четыре"));
        assertEquals("-100", WordToNumberParser.parseNumbersInString("минус сто"));
        assertEquals("~-20", WordToNumberParser.parseNumbersInString("~минус двадцать"));
        assertEquals("^50", WordToNumberParser.parseNumbersInString("^пятьдесят"));
    }
}
