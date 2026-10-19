package net.twcbmod.translator;

import net.twcbmod.autocomplete.FuzzyCommandMatcher;
import net.twcbmod.highlighter.CommandSyntaxHighlighter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Objective1And2NewFeaturesTest {

    @Test
    public void testTrailingSpaceAndNormalisation() {
        assertEquals("give @a diamond 1", CommandPreprocessor.translateCommand("give @a diamond один "));
        assertEquals("give @a diamond 1", CommandPreprocessor.translateCommand("give   @a   diamond   один  "));
    }

    @Test
    public void testContextualTargetsAndRegistry() {
        assertEquals("give @s diamond 1", CommandPreprocessor.translateCommand("/выдать себе алмаз один"));
        assertEquals("give @a golden_apple 10", CommandPreprocessor.translateCommand("/дать @всем золотое_яблоко десять"));
        assertEquals("setblock ~ ~ ~ stone", CommandPreprocessor.translateCommand("/установитьблок ~ ~ ~ камень"));
        assertEquals("summon lightning_bolt", CommandPreprocessor.translateCommand("/призвать молния"));
        assertEquals("effect give @s speed 10 1", CommandPreprocessor.translateCommand("/эффект выдать @себя скорость десять 1"));
    }

    @Test
    public void testFuzzyCommandMatcher() {
        List<String> matches = FuzzyCommandMatcher.findMatches("телепортивать");
        assertFalse(matches.isEmpty());
        assertTrue(matches.contains("телепортировать"));
    }

    @Test
    public void testSyntaxHighlighterCategoriesAndTokens() {
        assertEquals(CommandSyntaxHighlighter.COLOR_WORLD_BLOCKS, CommandSyntaxHighlighter.getRootCommandColor("заполнить"));
        assertEquals(CommandSyntaxHighlighter.COLOR_PLAYERS_ENTITIES, CommandSyntaxHighlighter.getRootCommandColor("телепортировать"));
        assertEquals(CommandSyntaxHighlighter.COLOR_LOGIC_AUTOMATION, CommandSyntaxHighlighter.getRootCommandColor("выполнить"));
        assertEquals(CommandSyntaxHighlighter.COLOR_COMBAT_DAMAGE, CommandSyntaxHighlighter.getRootCommandColor("убить"));
        assertEquals(CommandSyntaxHighlighter.COLOR_VISUALS_AUDIO, CommandSyntaxHighlighter.getRootCommandColor("сказать"));
        assertEquals(CommandSyntaxHighlighter.COLOR_SERVER_ADMIN, CommandSyntaxHighlighter.getRootCommandColor("сложность"));
    }
}
