package net.twcbmod.translator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordToNumberParserTest {

    @Test
    public void testBasicNumbers() {
        assertEquals("0", WordToNumberParser.parseNumbersInString("ноль"));
        assertEquals("1", WordToNumberParser.parseNumbersInString("один"));
        assertEquals("19", WordToNumberParser.parseNumbersInString("девятнадцать"));
        assertEquals("20", WordToNumberParser.parseNumbersInString("двадцать"));
        assertEquals("100", WordToNumberParser.parseNumbersInString("сто"));
        assertEquals("1000", WordToNumberParser.parseNumbersInString("тысяча"));
    }

    @Test
    public void testCompoundNumbers() {
        assertEquals("25", WordToNumberParser.parseNumbersInString("двадцать пять"));
        assertEquals("150", WordToNumberParser.parseNumbersInString("сто пятьдесят"));
        assertEquals("-150", WordToNumberParser.parseNumbersInString("минус сто пятьдесят"));
        assertEquals("123456789", WordToNumberParser.parseNumbersInString("сто двадцать три миллиона четыреста пятьдесят шесть тысяч семьсот восемьдесят девять"));
    }

    @Test
    public void testRelativeCoordinates() {
        assertEquals("~0", WordToNumberParser.parseNumbersInString("~ноль"));
        assertEquals("~0", WordToNumberParser.parseNumbersInString("~ ноль"));
        assertEquals("~-5", WordToNumberParser.parseNumbersInString("~минус пять"));
        assertEquals("~-5", WordToNumberParser.parseNumbersInString("~ минус пять"));
        assertEquals("^10", WordToNumberParser.parseNumbersInString("^десять"));
        assertEquals("^10", WordToNumberParser.parseNumbersInString("^ десять"));
    }

    @Test
    public void testCoordinatesInCommand() {
        assertEquals("teleport @a 0 100 0", WordToNumberParser.parseNumbersInString("teleport @a ноль сто ноль"));
        assertEquals("teleport @a ~ ~-5 ^10", WordToNumberParser.parseNumbersInString("teleport @a ~ ~минус пять ^десять"));
    }
}
