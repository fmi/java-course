package bg.sofia.uni.fmi.mjt.tagger;


import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaggerTest {

    private static final String SAMPLE_DICTIONARY_INPUT = """
            Dubai,United Arab Emirates
            Sharjah,United Arab Emirates
            Ajman,United Arab Emirates
            Zaranj,Afghanistan
            Taloqan,Afghanistan
            Shahrak,Afghanistan
            Maymana,Afghanistan
            Kushk,Afghanistan
            Kunduz,Afghanistan
            """;

    private static Tagger tagger;

    @Before
    public void setUp() throws IOException {
        Reader citiesCountries = new StringReader(SAMPLE_DICTIONARY_INPUT);
        tagger = new Tagger(citiesCountries);
    }

    @Test
    public void testTagCitiesEmptyTextFile() throws IOException {
        Reader emptyText = new StringReader("");
        Writer outputWriter = new StringWriter();

        tagger.tagCities(emptyText, outputWriter);

        assertTrue("Empty text should remain the same after tagging", 
                outputWriter.toString().isEmpty());
    }

    @Test
    public void testTagCitiesTextFileWithoutCities() throws IOException {
        final String inputWithNoCities = "no cities present here :)";
        Reader inputReader = new StringReader(inputWithNoCities);
        Writer outputWriter = new StringWriter();

        tagger.tagCities(inputReader, outputWriter);

        assertEquals("Text with no cities should remain the same after tagging", 
                inputWithNoCities, outputWriter.toString());
    }


    @Test
    public void testTagCitiesTextFileWithOneCityNoPunctuation() throws IOException {
        final String input = "hello, world! Maymana is a city in Afghanistan";
        final String expected = "hello, world! <city country=\"Afghanistan\">Maymana</city> is a city in Afghanistan";

        Reader inputReader = new StringReader(input);
        Writer outputWriter = new StringWriter();

        tagger.tagCities(inputReader, outputWriter);

        assertEquals("Text with single city without punctuation should be tagged correctly",
                expected, outputWriter.toString());
    }


    @Test
    public void testTagCitiesTextFileWithOneCityWithPunctuation() throws IOException {
        final String input = "hello, world! ,Maymana! is a city in Afghanistan";
        final String expected = "hello, world! ,<city country=\"Afghanistan\">Maymana</city>! is a city in Afghanistan";

        Reader inputReader = new StringReader(input);
        Writer outputWriter = new StringWriter();

        tagger.tagCities(inputReader, outputWriter);

        assertEquals("Text with single city with punctuation should be tagged correctly",
                expected, outputWriter.toString());
    }

    @Test
    public void testTagCitiesTextFileMultipleLines() throws IOException {
        final String input = String.format("hello, world! Maymana is a city in Afghanistan%none more city: Taloqan");
        final String expected = String.format(
                "hello, world! <city country=\"Afghanistan\">Maymana</city> is a city in Afghanistan%n" +
                        "one more city: <city country=\"Afghanistan\">Taloqan</city>");

        Reader inputReader = new StringReader(input);
        Writer outputWriter = new StringWriter();

        tagger.tagCities(inputReader, outputWriter);

        assertEquals("Text with multiple lines should be tagged correctly",
                expected, outputWriter.toString());
    }

    @Test
    public void testGetNMostTaggedCities() throws IOException {
        final String input = String.format("hello, world! ,Maymana! is a city in Afghanistan%none more city: Taloqan%none more city: Maymana");
        final List<String> expected = Collections.singletonList("Maymana");

        Reader inputReader = new StringReader(input);
        Writer outputWriter = new StringWriter();

        tagger.tagCities(inputReader, outputWriter);
        List<String> actual = new ArrayList<>(tagger.getNMostTaggedCities(1));

        assertEquals("Tagged cities should be sorted correctly",
                expected, actual);
    }

    @Test
    public void testGetAllTaggedCitiesDuplicateCity() throws IOException {
        final String input = String.format("hello, world! ,Maymana! is a city in Afghanistan%none more city: Taloqan%none more city: Maymana");
        final List<String> expected = Arrays.asList("Taloqan", "Maymana");

        Reader inputReader = new StringReader(input);
        Writer outputWriter = new StringWriter();

        tagger.tagCities(inputReader, outputWriter);
        List<String> actual = new ArrayList<>(tagger.getAllTaggedCities());

        assertEquals("Tagged cities should be recorded correctly",
                expected, actual);
    }

    @Test
    public void testGetAllTaggedCitiesDuplicateCityDifferentCapitalization() throws IOException {
        final String input = String.format("hello, world! ,Maymana! is a city in Afghanistan%none more city: Taloqan%none more city: MaYmaNA");
        final List<String> expected = Arrays.asList("Taloqan", "Maymana");

        Reader inputReader = new StringReader(input);
        Writer outputWriter = new StringWriter();

        tagger.tagCities(inputReader, outputWriter);
        List<String> actual = new ArrayList<>(tagger.getAllTaggedCities());

        assertEquals("Same cities with different capitalization should be counted as one city",
                expected, actual);
    }

    @Test
    public void testGetAllTagsCount() throws IOException {
        final String input = String.format("hello, world! ,Maymana! is a city in Afghanistan%none more city: Taloqan%none more city: Maymana");
        final long expected = 3;

        Reader inputReader = new StringReader(input);
        Writer outputWriter = new StringWriter();

        tagger.tagCities(inputReader, outputWriter);
        long actual = tagger.getAllTagsCount();

        assertEquals("Tagged cities should be counted correctly",
                expected, actual);
    }

}
