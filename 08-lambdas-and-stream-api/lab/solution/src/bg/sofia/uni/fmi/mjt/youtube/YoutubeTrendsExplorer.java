package bg.sofia.uni.fmi.mjt.youtube;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class YoutubeTrendsExplorer {

    private static final int HUNDRED_THOUSAND_VIEWS = 100_000;
    private static final int TOP_THREE = 3;

    private List<TrendingVideo> trendingVideos;

    /**
     * Loads the dataset from the given {@code dataInput} stream.
     */
    public YoutubeTrendsExplorer(InputStream dataInput) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataInput))) {

            trendingVideos = reader.lines()
                    .skip(1)
                    .map(TrendingVideo::createTrendingVideo)
                    .collect(Collectors.toList());

            System.out.println("Videos loaded: " + trendingVideos.size());

        } catch (IOException e) {
            throw new IllegalArgumentException("Could not load dataset", e);
        }
    }

    /**
     * Returns all videos loaded from the dataset.
     **/
    public Collection<TrendingVideo> getTrendingVideos() {
        return Collections.unmodifiableList(trendingVideos);
    }

    public String findIdOfLeastLikedVideo() {
        return trendingVideos.stream()
                .min(Comparator.comparingLong(TrendingVideo::getLikes))
                .get()
                .getId();
    }

    public String findIdOfMostLikedLeastDislikedVideo() {
        return trendingVideos.stream()
                .max(Comparator.comparingLong(v -> v.getLikes() - v.getDislikes()))
                .get()
                .getId();
    }

    public String findIdOfMostTrendingVideo() {
        Map<String, Long> trendingVideosById =
                trendingVideos.stream().collect(Collectors.groupingBy(TrendingVideo::getId, Collectors.counting()));

        return trendingVideosById.entrySet()
                .stream()
                .max(Comparator.comparing(x -> x.getValue()))
                .get()
                .getKey();
    }

    public String findIdOfMostTaggedVideo() {
        return trendingVideos.stream()
                .max(Comparator.comparingInt(v -> v.getTags().size()))
                .get()
                .getId();
    }

    public List<String> findDistinctTitlesOfTop3VideosByViews() {
        return trendingVideos.stream()
                .sorted(Comparator.comparing(TrendingVideo::getViews).reversed())
                .distinct()
                .limit(TOP_THREE)
                .map(v -> v.getTitle())
                .collect(Collectors.toList());
    }

    public String findTitleOfFirstVideoTrendingBefore100KViews() {
        return trendingVideos.stream()
                .filter(v -> v.getViews() < HUNDRED_THOUSAND_VIEWS)
                .min(Comparator.comparing(TrendingVideo::getPublishDate))
                .get()
                .getTitle();
    }

    public static void main(String[] args) throws IOException {
        var explorer = new YoutubeTrendsExplorer(Files.newInputStream(Path.of("USvideos.txt")));
        System.out.println(explorer.findIdOfMostTrendingVideo());
        System.out.println(explorer.findIdOfMostLikedLeastDislikedVideo());
        System.out.println(explorer.findIdOfLeastLikedVideo());
        System.out.println(explorer.findIdOfMostTaggedVideo());
        System.out.println(explorer.findDistinctTitlesOfTop3VideosByViews());
        System.out.println(explorer.findTitleOfFirstVideoTrendingBefore100KViews());
    }

}
