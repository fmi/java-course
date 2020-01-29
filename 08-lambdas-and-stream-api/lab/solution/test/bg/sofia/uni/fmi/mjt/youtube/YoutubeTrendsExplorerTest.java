package bg.sofia.uni.fmi.mjt.youtube;

import bg.sofia.uni.fmi.mjt.youtube.model.TrendingVideo;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class YoutubeTrendsExplorerTest {

    private static List<TrendingVideo> trendingVideos;
    private static YoutubeTrendsExplorer explorer;

    @BeforeClass
    public static void setUp() throws IOException {
        InputStream videosStream = initVideoStream();

        try (InputStreamReader isr = new InputStreamReader(videosStream);
             BufferedReader reader = new BufferedReader(isr)) {

            trendingVideos = reader.lines()
                    .skip(1)
                    .map(TrendingVideo::createTrendingVideo)
                    .collect(Collectors.toList());
        }

        InputStream explorerStream = initVideoStream();
        explorer = new YoutubeTrendsExplorer(explorerStream);
    }

    @Test
    public void testIfExistingDatasetIsLoadedCorrectly() {
        String assertMessage =
                "Number of videos in the YoutubeTrendsExplorer does not match the number of videos in the dataset.";
        int expected = trendingVideos.size();
        int actual = explorer.getTrendingVideos().size();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindIdOfLeastLikedVideo() {
        String assertMessage = "Did not determine correctly the least liked video.";
        String expected = "1";
        String actual = explorer.findIdOfLeastLikedVideo();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindIdOfMostLikedLeastDislikedVideo() {
        String assertMessage = "Did not determine correctly the video with the biggest number of likes minus dislikes.";
        String expected = "5";
        String actual = explorer.findIdOfMostLikedLeastDislikedVideo();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindIdOfMostTrendingVideo() {
        String assertMessage = "Did not determine correctly the ID of the most frquently trending video.";
        String expected = "2";
        String actual = explorer.findIdOfMostTrendingVideo();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindIdOfMostTaggedVideo() {
        String assertMessage = "Did not determine correctly the most tagged video.";
        String expected = "3";
        String actual = explorer.findIdOfMostTaggedVideo();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindDistinctTitlesOfTop3VideosByViews() {
        String assertMessage = "Did not determine correctly the distinct titles of the top 3 videos by views.";
        List<String> expected = List.of("Title 3", "Title 5", "Title 4");
        List<String> actual = explorer.findDistinctTitlesOfTop3VideosByViews();

        assertEquals(assertMessage,
                expected.stream().collect(Collectors.joining()), actual.stream().collect(Collectors.joining()));
    }

    @Test
    public void testFindTitleOfFirstVideoTrendingBefore100KViews() {
        String assertMessage =
                "Did not determine correctly the first (earliest published) video trending before it got 100K views.";
        String expected = "Title 2";
        String actual = explorer.findTitleOfFirstVideoTrendingBefore100KViews();

        assertEquals(assertMessage, expected, actual);
    }

    public static InputStream initVideoStream() {

        String[] videos = {
                "video_id\ttrending_date\ttitle\tpublish_time\ttags\tviews\tlikes\tdislikes\t",
                "1\t17.01.12\tTitle 1\t2017-11-25T18:55:42.000Z\ttravel|\"fun\"\t5\t5\t0",
                "2\t17.01.12\tTitle 2\t2017-01-07T10:55:42.000Z\t\"math\"|\"exam\"\t150000\t50\t10",
                "3\t17.01.12\tTitle 3\t2016-01-07T10:55:42.000Z\t\"show\"|\"food\"|tv\t1150000\t500\t100",
                "4\t17.01.12\tTitle 4\t2019-01-07T10:55:42.000Z\t\"garden\"\t160000\t500\t700",
                "5\t17.01.12\tTitle 5\t2019-12-04T13:30:00.000Z\t\"birthday\"|\"party\"\t1000000\t100000\t0",
                "2\t17.01.12\tTitle 2\t2017-01-07T10:55:42.000Z\t\"math\"|\"exam\"\t15000\t5\t1",
                "4\t17.01.12\tTitle 4\t2019-01-07T10:55:42.000Z\t\"garden\"\t160000\t500\t700",
                "2\t17.01.12\tTitle 2\t2017-01-07T10:55:42.000Z\t\"math\"|\"exam\"\t150000\t50\t10",
        };

        return new ByteArrayInputStream(
                Arrays.stream(videos)
                        .collect(Collectors.joining(System.lineSeparator()))
                        .getBytes());

    }

}
