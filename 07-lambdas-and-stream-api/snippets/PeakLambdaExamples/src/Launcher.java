import bg.sofia.uni.fmi.mjt.movies.PeakExplorer;

public class Launcher {

    public static final String MOUNTAINS_FILE_PATH = "./mountains.txt";

    public static void main(String[] args) {
        final PeakExplorer peakStats = new PeakExplorer(MOUNTAINS_FILE_PATH);

        System.out.println(peakStats.countPeaksTallerThan(7000));
        System.out.println(peakStats.shortestNotAscended());
        System.out.println(peakStats.avgAscentsTopN(5));
        System.out.println(peakStats.getHighestAscentPerYear(1986));
        System.out.println(peakStats.getNonHimalayaNamesByProminence());
        System.out.println(peakStats.getRangeWithMostPeaks(10));

    }
}
