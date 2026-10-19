package net.twcbmod.translator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandPreprocessorTest {

    @Test
    public void testFullCommandTranslation() {
        assertEquals("teleport @a 0 64 0", CommandPreprocessor.translateCommand("/телепортировать @все ноль шестьдесят четыре ноль"));
        assertEquals("teleport @a 0 64 0", CommandPreprocessor.translateCommand("телепортировать @все ноль шестьдесят четыре ноль"));
    }

    @Test
    public void testComplexExecuteChain() {
        assertEquals("execute as @e[type=zombie] at @s run setblock ~ 0 ~ stone",
                CommandPreprocessor.translateCommand("/выполнить как @сущности[тип=zombie] в @себя запустить установитьблок ~ ноль ~ stone"));
    }

    @Test
    public void testFillWithRelativeWordCoordinatesAndBlockModes() {
        assertEquals("fill ~ ~-5 ~10 ~10 100 ~10 air replace stone",
                CommandPreprocessor.translateCommand("/заполнить ~ ~минус пять ~десять ~десять сто ~десять air заменить stone"));
    }

    @Test
    public void testQuotedStringPreservation() {
        assertEquals("say \"Привет один два\"",
                CommandPreprocessor.translateCommand("/сказать \"Привет один два\""));
        assertEquals("tellraw @a \"Текст с запустить и один\"",
                CommandPreprocessor.translateCommand("/теллрав @все \"Текст с запустить и один\""));
    }

    @Test
    public void testSelectorTranslation() {
        assertEquals("kill @e[type=cow,limit=5,sort=nearest]",
                CommandPreprocessor.translateCommand("/убить @сущности[тип=cow,лимит=пять,сортировка=ближайшие]"));
    }

    @Test
    public void testGamemodeTranslation() {
        assertEquals("gamemode survival @p", CommandPreprocessor.translateCommand("/режим выживание @игрок"));
        assertEquals("gamemode creative @s", CommandPreprocessor.translateCommand("/гм креатив @себя"));
    }

    @Test
    public void testBackwardsCompatibility() {
        assertEquals("teleport @a 0 64 0", CommandPreprocessor.translateCommand("/teleport @a 0 64 0"));
        assertEquals("execute as @p run say hello", CommandPreprocessor.translateCommand("/execute as @p run say hello"));
    }
}
