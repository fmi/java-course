package bg.sofia.uni.fmi.mjt.peaks;

import bg.sofia.uni.fmi.mjt.peaks.model.Peak;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PeakExplorer {

    private List<Peak> peaks = null;

    public PeakExplorer(String datasetPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(datasetPath))) {
            peaks = reader.lines().map(Peak::createPeak).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Peak> getPeaks() {
        return peaks;
    }

    /**
     * Определя броя на върховете, които са по-високи от N метра
     */
    public long countPeaksTallerThan(int n) {
        return peaks.stream()
                .filter(peak -> peak.getHeight() > n)
                .count();
    }

    /**
     * Определя височината на най-ниския връх, който никога не е бил изкачван
     */
    public double shortestNotAscended() {
        return peaks.stream()
                .filter(peak -> peak.getFirstAscent() == 0)
                .mapToDouble(Peak::getHeight)
                .min()
                .getAsDouble();
    }

    /**
     * Определя средния брой изкачвания на върховете в топ N (включително)
     * Връща 0.0, ако няма изкачен връх в интервала.
     */
    public double avgAscentsTopN(int n) {
        return peaks.stream()
                .filter(peak -> peak.getPos() <= n)
                .mapToInt(Peak::getTotalAscents)
                .average()
                .orElse(0);
    }

    /**
     * Определя най-високия връх, изкачен за пръв път през дадена година.
     * Ако няма върхове, изкачени през тази година, се хвърля java.lang.IllegalArgumentException.
     */
    public Peak getHighestAscentPerYear(int year) {
        return peaks.stream()
                .filter(p -> p.getFirstAscent() == year)
                .max(Comparator.comparing(Peak::getHeight))
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Определя имената на върховете, които не са част от Хималаите
     * (която и да е планина, съдържащa 'Himalaya' в името си),
     * подредени по изпъкналост (от най-висок към най-нисък)
     */
    public List<String> getNonHimalayaNamesByProminence() {
        return peaks.stream()
                .filter(p -> !p.getRange().contains("Himalaya"))
                .sorted(Comparator.comparing(Peak::getProminence).reversed())
                .map(Peak::getName)
                .collect(Collectors.toList());
    }

    /**
     * Определя името на планинската верига, която има най-много върхове в топ N (включително)
     * Ако няколко планински вериги имат равен резултат, няма значение името на коя от тях ще се върне.
     */
    public String getRangeWithMostPeaks(int n) {
        Map<String, Long> result =
                peaks.stream()
                        .filter(p -> p.getPos() <= n)
                        .collect(Collectors.groupingBy(Peak::getRange, Collectors.counting()));

        return result.entrySet().stream()
                .max(Comparator.comparing(x -> x.getValue()))
                .get().getKey();
    }

}
