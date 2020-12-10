package bg.sofia.uni.fmi.mjt.tagger;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tagger {

    private final Map<String, String> cities;
    private final Map<String, Integer> taggedCities;
    private long taggedCitiesCount;

    public Tagger(Reader citiesReader) throws IOException {
        cities = new HashMap<>();
        taggedCities = new HashMap<>();
        taggedCitiesCount = 0;
        loadCities(citiesReader);
    }

    public void tagCities(Reader text, Writer output) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(text);
        BufferedWriter bufferedWriter = new BufferedWriter(output);

        String inputLine;
        boolean isNotFirstLine = false;
        while ((inputLine = bufferedReader.readLine()) != null) {

            if (isNotFirstLine) {
                bufferedWriter.newLine();
            }

            String[] words = inputLine.split("\\s+");
            String outputLine = inputLine;
            for (String word : words) {
                String strippedWord = word
                        .replaceAll("[^a-zA-Z ]+", "")
                        .replaceAll("\\s+", " ")
                        .trim();

                if (!strippedWord.isEmpty()) {
                    String cityNameLowerCase = strippedWord.toLowerCase();
                    String cityNameLowerCaseCapitalFirst = cityNameLowerCase.substring(0, 1).toUpperCase()
                            + cityNameLowerCase.substring(1).toLowerCase();

                    if (cities.containsKey(cityNameLowerCaseCapitalFirst)) {
                        String country = cities.get(cityNameLowerCaseCapitalFirst);
                        String replacement = String.format("<city country=\"%s\">%s</city>", country, strippedWord);
                        outputLine = outputLine.replaceAll(strippedWord, replacement);
                        Integer taggedCityCount = taggedCities.get(cityNameLowerCaseCapitalFirst);
                        taggedCityCount = taggedCityCount != null ? taggedCityCount + 1 : 1;
                        taggedCities.put(cityNameLowerCaseCapitalFirst, taggedCityCount);
                        taggedCitiesCount++;
                    }
                }
            }

            bufferedWriter.write(outputLine);
            isNotFirstLine = true;
            bufferedWriter.flush();
        }
    }

    public Collection<String> getNMostTaggedCities(int n) {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(taggedCities.entrySet());
        sortedEntries.sort(new MapComparator());

        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(sortedEntries.get(i).getKey());
        }

        return result;
    }

    public Collection<String> getAllTaggedCities() {
        return taggedCities.keySet();
    }

    public long getAllTagsCount() {
        return taggedCitiesCount;
    }

    private void loadCities(Reader citiesReader) throws IOException {
        BufferedReader reader = new BufferedReader(citiesReader);

        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            String cityNameLowerCase = tokens[0].toLowerCase();
            String cityNameLowerCaseCapitalFirst = cityNameLowerCase.substring(0, 1).toUpperCase()
                    + cityNameLowerCase.substring(1).toLowerCase();

            cities.put(cityNameLowerCaseCapitalFirst, tokens[1]);
        }
    }

    private class MapComparator implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            Integer firstValue = taggedCities.get(o1.getKey());
            firstValue = firstValue != null ? firstValue : 0;

            Integer secondValue = taggedCities.get(o2.getKey());
            secondValue = secondValue != null ? secondValue : 0;

            return Integer.compare(secondValue, firstValue);
        }
    }
}
