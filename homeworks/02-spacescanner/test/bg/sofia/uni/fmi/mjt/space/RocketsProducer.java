package bg.sofia.uni.fmi.mjt.space;

public class RocketsProducer {
    private static final String ROCKETS_FILE_CONTENT = """
            "",Name,Wiki,Rocket Height
            0,Tsyklon-3,https://en.wikipedia.org/wiki/Tsyklon-3,39.0 m
            1,Unha-2,https://en.wikipedia.org/wiki/Unha,28.0 m
            2,Zenit-2 FG,https://en.wikipedia.org/wiki/Zenit_%28rocket_family%29,57.0 m
            3,Antares 110,https://en.wikipedia.org/wiki/Antares_(rocket),40.5 m
            4,ZhuQue-1,https://en.wikipedia.org/wiki/LandSpace,19.0 m
            5,Ariane 5 G,https://en.wikipedia.org/wiki/Ariane_5,52.0 m
            6,Ariane 5 G+,https://en.wikipedia.org/wiki/Ariane_5,""";

    public static String getRocketsFileContent() {
        return ROCKETS_FILE_CONTENT;
    }
}
