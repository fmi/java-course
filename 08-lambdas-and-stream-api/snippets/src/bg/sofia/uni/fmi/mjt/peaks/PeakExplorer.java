package bg.sofia.uni.fmi.mjt.peaks;

import bg.sofia.uni.fmi.mjt.peaks.model.Peak;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PeakExplorer {

    private final List<Peak> peaks;

    public PeakExplorer(Reader dataInput) {
        try (var reader = new BufferedReader(dataInput)) {
            peaks = reader.lines().map(Peak::of).toList();
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from the file", e);
        }
    }

    private static void optionalUsageExample(Optional<String> optionalPeakName) {
        System.out.println("Optional peak name is empty: " + optionalPeakName.isEmpty());
        System.out.println("Optional peak name is present: " + optionalPeakName.isPresent());
        System.out.println("Peak name: " + optionalPeakName.get());
    }

    public static void main(String[] args) throws FileNotFoundException {
        PeakExplorer peakExplorer =
            new PeakExplorer(new FileReader("./src/bg/sofia/uni/fmi/mjt/peaks/resources/mountains.txt"));

        System.out.println("Count of peaks higher than n: " + peakExplorer.countPeaksTallerThan(5000));

        System.out.println("Get the lowest peak not being ascended: " + peakExplorer.shortestNotAscended());

        System.out.println("Get average ascents in top n peaks: " + peakExplorer.avgAscentsTopN(20));

        System.out.println("Highest ascended peek for year: " + peakExplorer.getHighestAscentForYear(1960));

        /*
                System.out.println("Highest ascended peek for year: " +
                        peakExplorer.getHighestAscentForYear(2021)); // should result in IllegalArgumentException
        */

        System.out.println(
            "Peaks with not Himalaya names by prominence: " + peakExplorer.getNonHimalayaNamesByProminence());

        System.out.println(
            "Get the mountain range which has the most peaks in top n: " + peakExplorer.getRangeWithMostPeaks(20));

        System.out.println("Get the year with most first ascents: " + peakExplorer.getYearWithMostFirstAscents());

        System.out.println("Get the earliest ascended peaks: " + peakExplorer.getEarliestAscendedPeaks(5));

        // Optional example
        System.err.println("Optional should be present");
        Optional<String> optionalPeakName1 = peakExplorer.getPeakAscendedInYearWithHighestProminence(1960);
        optionalUsageExample(optionalPeakName1);

        System.err.println("Optional should not be present");
        Optional<String> optionalPeakName2 = peakExplorer.getPeakAscendedInYearWithHighestProminence(2021);
        // optionalUsageExample(optionalPeakName2); // this results in java.util.NoSuchElementException
    }

    public List<Peak> getPeaks() {
        return peaks;
    }

    /**
     * Определя броя на върховете, които са по-високи от N метра
     */
    public long countPeaksTallerThan(int n) {
        return peaks.stream()
            .filter(peak -> peak.height() > n)
            .count();
    }

    /**
     * Определя височината на най-ниския връх, който никога не е бил изкачван
     */
    public double shortestNotAscended() {
        return peaks.stream()
            .filter(peak -> peak.firstAscent() == 0)
            .mapToDouble(Peak::height)
            .min()
            .getAsDouble();
    }

    /**
     * Определя средния брой изкачвания на върховете в топ N (включително)
     * Връща 0.0, ако няма изкачен връх в интервала.
     */
    public double avgAscentsTopN(int n) {
        return peaks.stream()
            .filter(peak -> peak.pos() <= n)
            .mapToInt(Peak::totalAscents)
            .average()
            .orElse(0);
    }

    /**
     * Определя най-високия връх, изкачен за пръв път през дадена година.
     * Ако няма върхове, изкачени през тази година, се хвърля java.lang.IllegalArgumentException.
     */
    public Peak getHighestAscentForYear(int year) {
        return peaks.stream()
            .filter(p -> p.firstAscent() == year)
            .max(Comparator.comparingDouble(Peak::height))
            .orElseThrow(() -> new IllegalArgumentException("No peaks have been ascended in " + year));
    }

    /**
     * Определя имената на върховете, които не са част от Хималаите
     * (която и да е планина, съдържащa 'Himalaya' в името си),
     * подредени по изпъкналост (от най-висок към най-нисък)
     */
    public List<String> getNonHimalayaNamesByProminence() {
        return peaks.stream()
            .filter(p -> !p.range().contains("Himalaya"))
            .sorted(Comparator.comparing(Peak::prominence).reversed())
            .map(Peak::name)
            .toList();
    }

    /**
     * Определя името на планинската верига, която има най-много върхове в топ N (включително)
     * Ако няколко планински вериги имат равен резултат, няма значение името на коя от тях ще се върне.
     */
    public String getRangeWithMostPeaks(int n) {
        Map<String, Long> result =
            peaks.stream()
                .filter(p -> p.pos() <= n)
                .collect(Collectors.groupingBy(Peak::range, Collectors.counting()));

        return result.entrySet().stream()
            .max(Comparator.comparing(x -> x.getValue()))
            .get().getKey();
    }

    /**
     * Определя броя на върховете, които са били изкачени за първи път през всяка отделна година и
     * след това връща годината, в която има най-много изкачени върхове.
     */
    public int getYearWithMostFirstAscents() {
        Map<Integer, Long> result =
            peaks.stream()
                .collect(Collectors.groupingBy(Peak::firstAscent, Collectors.counting()));

        return result.entrySet().stream()
            .max(Comparator.comparing(x -> x.getValue()))
            .get()
            .getKey();
    }

    /**
     * Определя n-те най-рано изкачени върха и върнете имената им като един единствен String,
     * разделени със запетаи
     */
    public String getEarliestAscendedPeaks(int n) {
        return peaks.stream()
            .filter(p -> p.firstAscent() > 0)
            .sorted(Comparator.comparing(Peak::firstAscent))
            .limit(n)
            .map(Peak::name)
            .collect(Collectors.joining(", "));
    }

    /**
     * Определя името на върха с най-голяма изпъкналост, изкачен през конкретна година
     */
    public Optional<String> getPeakAscendedInYearWithHighestProminence(int year) {
        return peaks.stream()
            .filter(p -> p.firstAscent() == year)
            .max(Comparator.comparing(Peak::prominence))
            .map(Peak::name); // method of java.util.Optional
                              // do not confuse it with map() in java.util.stream.Stream
    }

}
