package bg.sofia.uni.fmi.mjt.movies.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MoviesStreamInitializer {
    private static String[] movies = {
            "Harry Potter and the Prisoner of Azkaban (2004)/Grint, Rupert/Radcliffe, Daniel/Watson, Emma",
            "Lord of the Rings, The (1978)/Barrett, Stan/Bird, Norman/Bliss, Lucille/Buck, David",
            "Lord of the Rings: The Fellowship of the Ring, The (2001)/Astin, Sean/Baker, Sala/Bean, Sean/Blanchett, Cate/Bloom, Orlando/Lee, Christopher/McKellen, Ian/Mortensen, Viggo/Tyler, Liv/Wood, Elijah",
            "Lord of the Rings: The Return of the King, The (2003)/Astin, Sean/Baker, Sala/Bean, Sean/Blanchett, Cate/Bloom, Orlando/Lee, Christopher/McKellen, Ian/Mortensen, Viggo/Tyler, Liv/Wood, Elijah",
            "Lord of the Rings: The Two Towers, The (2002)/Astin, Sean/Baker, Sala/Bean, Sean/Blanchett, Cate/Bloom, Orlando/Lee, Christopher/McKellen, Ian/Mortensen, Viggo/Tyler, Liv/Wood, Elijah",
            "Mulan II (2003)/Ming-Na/Moseley, Mark/Osmond, Donny)/Salonga, Lea)/Wong, B.D.",
            "Dr. Dolittle 2 (2001)/Griffin, Mark/Irwin, Steve/Kudrow, Lisa/Muniz, Frankie/Murphy, Eddie/Schwarzenegger, Arnold",
            "Ice Age (2002)/Ackerman, Peter/Bowen, Andrea/Dillon, Denny/Hamilton, Josh/Krakowski, Jane",
            "Matilda (1978)/Avianca, Frank/Brill, Charlien",
            "Trainspotting (1996)/Bremner, Ewen/Carlyle, Robert/Macdonald, Kelly/McGregor, Ewan/McKidd, Kevin/Miller, Jonny Lee/Mullan, Peter/Welsh, Irvine" };

    public static InputStream initMovieStream() {

        return new ByteArrayInputStream(
                Arrays.stream(movies)
                .collect(Collectors.joining(System.lineSeparator()))
                .getBytes());

    }
}
