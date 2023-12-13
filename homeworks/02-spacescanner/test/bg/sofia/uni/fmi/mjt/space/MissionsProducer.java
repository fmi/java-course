package bg.sofia.uni.fmi.mjt.space;

public class MissionsProducer {
    public static final int[] IDS_OF_MISSIONS_WITH_STATUS_SUCCESS = new int[]{0, 1, 3, 6, 7, 8, 9, 10, 11, 13, 14};
    public static final int[] IDS_OF_MISSIONS_WITH_STATUS_FAILURE = new int[]{4, 5, 12};
    public static final int[] IDS_OF_MISSIONS_WITH_STATUS_PARTIAL_FAILURE = new int[]{2};
    public static final int[] IDS_OF_MISSIONS_WITH_STATUS_PRELAUNCH_FAILURE = new int[]{15};
    private static final String MISSIONS_FILE_CONTENT = """
            Unnamed: 0,Company Name,Location,Datum,Detail,Status Rocket," Rocket",Status Mission
            0,VKS RF,"Site 32/2, Plesetsk Cosmodrome1, Russia","Fri Jan 30, 2009",Tsyklon-3 | Koronas Foton,StatusRetired,"100000.0",Success
            1,VKS RF,"Site 32/2, Plesetsk Cosmodrome1, Russia","Sun Dec 30, 2001",Tsyklon-3 | Cosmos 2384 to 2386 & Gonets 10 to 12,StatusRetired,,Success
            2,VKS RF,"Site 32/2, Plesetsk Cosmodrome, Russia","Fri Dec 24, 2004",Tsyklon-3 | Sich 1M & Micron 1,StatusRetired,"300000.0",Partial Failure
            3,VKS RF,"Site 32/2, Plesetsk Cosmodrome, Russia","Fri Dec 28, 2001",Tsyklon-3 | Cosmos 2384 to 2386 & Gonets 10 to 12,StatusRetired,"100.0",Success
            4,VKS RF,"Site 32/2, Plesetsk Cosmodrome, Russia","Fri Dec 28, 2001",Tsyklon-3 | Cosmos 2384 to 2386 & Gonets 10 to 12,StatusRetired,"1000.0",Failure
            5,KCST,"Pad 1, Tonghae Satellite Launching Ground, North Korea","Sun Apr 05, 2009",Unha-2 | Kwangmyngsng-2,StatusRetired,"1.0",Failure
            6,Roscosmos,"Site 45/1, Baikonur Cosmodrome, Kazakhstan","Tue Nov 08, 2011",Zenit-2 FG | Fobos-Grunt & Yinghuo 1,StatusActive,"400000.0",Success
            7,VKS RF,"Site 45/1, Baikonur Cosmodrome, Kazakhstan","Fri Jun 29, 2007",Zenit-2 | Cosmos 2428,StatusRetired,,Success
            8,Arianespace,"ELA-3, Guiana Space Centre, French Guiana, France","Sat Dec 18, 2004","Ariane 5 G+ | Helios 2A, Essaim-1,2,3,4, PARASOL, Nanosat 01",StatusRetired,"190.0 ",Success
            9,Arianespace,"ELA-3, Guiana Space Centre, French Guiana, France","Sun Jul 18, 2004",Ariane 5 G+ | Anik F2,StatusRetired,"190.0 ",Success
            10,Arianespace,"ELA-3, Guiana Space Centre, French Guiana, France","Wed Jun 11, 2003",Ariane 5 G | Optus C1 & BSAT-2c,StatusRetired,"4.0",Success
            11,Arianespace,"ELA-3, Guiana Space Centre, French Guiana, France","Wed Apr 09, 2003",Ariane 5 G | INSAT-3A & Galaxy 12,StatusRetired,,Success
            12,Landspace,"Site 95, Jiuquan Satellite Launch Center, China","Sat Oct 27, 2018",ZhuQue-1 | CCTV Future-1,StatusRetired,,Failure
            13,Northrop,"LP-0A, Wallops Flight Facility, Virginia, USA","Fri Sep 13, 2013",Antares 110 | CRS Orb-D1,StatusRetired,"80.0 ",Success
            14,Northrop,"LP-0A, Wallops Flight Facility, Virginia, USA","Sun Apr 21, 2013",Antares 110 | Antares A-ONE,StatusRetired,"80.0 ",Success
            15,ISA,"Imam Khomeini Spaceport, Semnan Space Center, Iran","Thu Aug 29, 2019",Safir-1B+ | Nahid-1,StatusActive,,Prelaunch Failure
            """;

    public static String getMissionsFileContent() {
        return MISSIONS_FILE_CONTENT;
    }

    public static int[] getMissionIdsByCountry(Country country) {
        return switch (country) {
            case RUSSIA -> new int[]{0, 1, 2, 3, 4};
            case NORTH_KOREA -> new int[]{5};
            case CHINA -> new int[]{12};
            case KAZAKHSTAN -> new int[]{6, 7};
            case FRANCE -> new int[]{8, 9, 10, 11};
            case IRAN -> new int[]{15};
            case USA -> new int[]{13, 14};
        };
    }
}
