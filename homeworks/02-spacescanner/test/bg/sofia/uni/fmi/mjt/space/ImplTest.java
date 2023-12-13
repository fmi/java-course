package bg.sofia.uni.fmi.mjt.space;

import bg.sofia.uni.fmi.mjt.space.algorithm.Rijndael;
import bg.sofia.uni.fmi.mjt.space.algorithm.SymmetricBlockCipher;
import bg.sofia.uni.fmi.mjt.space.exception.TimeFrameMismatchException;
import bg.sofia.uni.fmi.mjt.space.mission.Mission;
import bg.sofia.uni.fmi.mjt.space.mission.MissionStatus;
import bg.sofia.uni.fmi.mjt.space.rocket.Rocket;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class ImplTest {
    private static final int SECRET_KEY_LENGTH = 128;
    private static final List<Mission> missions = new ArrayList<>();
    private static final List<Rocket> rockets = new ArrayList<>();
    private static final String ROCKETS_FILE_CONTENT = RocketsProducer.getRocketsFileContent();
    private static final String MISSIONS_FILE_CONTENT = MissionsProducer.getMissionsFileContent();
    private static final String EMPTY_FILE_CONTENT = "";
    private static MJTSpaceScanner MJTSpaceScanner;
    private static MJTSpaceScanner emptyMJTSpaceScanner;
    private static SymmetricBlockCipher symmetricBlockCipher;

    @BeforeAll
    static void setUp() throws NoSuchAlgorithmException {
        SecretKey key = generateKey(SECRET_KEY_LENGTH);

        MJTSpaceScanner = new MJTSpaceScanner(new StringReader(MISSIONS_FILE_CONTENT), new StringReader(ROCKETS_FILE_CONTENT), key);
        emptyMJTSpaceScanner = new MJTSpaceScanner(new StringReader(EMPTY_FILE_CONTENT), new StringReader(EMPTY_FILE_CONTENT), key);
        symmetricBlockCipher = new Rijndael(key);

        load(new StringReader(MISSIONS_FILE_CONTENT), missions, Mission::of);
        load(new StringReader(ROCKETS_FILE_CONTENT), rockets, Rocket::of);
    }

    @Test
    void testGetAllMissionsWhenNone() {
        Collection<Mission> actual = emptyMJTSpaceScanner.getAllMissions();

        assertEquals(0, actual.size(), "there should not be any missions");
    }

    @Test
    void testGetAllMissions() {
        Collection<Mission> actual = MJTSpaceScanner.getAllMissions();

        assertEquals(16, actual.size(), "there should be 16 missions");
        assertContainsAll(missions, actual);
    }

    @Test
    void testGetAllMissionsWithNullStatusExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getAllMissions(null),
                "expected IllegalArgumentException when missionStatus is null");
    }

    @Test
    void testGetAllMissionsWithStatusSuccessWhenNone() {
        Collection<Mission> actualSuccessfulMissions = emptyMJTSpaceScanner.getAllMissions(MissionStatus.SUCCESS);

        assertEquals(0, actualSuccessfulMissions.size(), "there should not be any successful missions");
    }

    @Test
    void testGetAllMissionsWithStatusFailureWhenNone() {
        Collection<Mission> actualFailedMissions = emptyMJTSpaceScanner.getAllMissions(MissionStatus.FAILURE);

        assertEquals(0, actualFailedMissions.size(), "there should not be any failed missions");
    }

    @Test
    void testGetAllMissionsWithStatusPartialFailureWhenNone() {
        Collection<Mission> actualPartiallyFailedMissions = emptyMJTSpaceScanner.getAllMissions(MissionStatus.PARTIAL_FAILURE);

        assertEquals(0, actualPartiallyFailedMissions.size(), "there should not be any partially failed missions");
    }

    @Test
    void testGetAllMissionsWithStatusPreLaunchFailureWhenNone() {
        Collection<Mission> actualPreLaunchFailedMissions = emptyMJTSpaceScanner.getAllMissions(MissionStatus.PRELAUNCH_FAILURE);

        assertEquals(0, actualPreLaunchFailedMissions.size(), "there should not be any prelaunch failed missions");
    }

    @Test
    void testGetAllMissionsWithStatusSuccess() {
        Collection<Mission> expected = getMissionsById(MissionsProducer.IDS_OF_MISSIONS_WITH_STATUS_SUCCESS);
        Collection<Mission> actual = MJTSpaceScanner.getAllMissions(MissionStatus.SUCCESS);

        assertEquals(11, actual.size(), "there should be 11 missions with status success");
        assertContainsAll(expected, actual);
    }

    @Test
    void testGetAllMissionsWithStatusFailure() {
        Collection<Mission> expected = getMissionsById(MissionsProducer.IDS_OF_MISSIONS_WITH_STATUS_FAILURE);
        Collection<Mission> actual = MJTSpaceScanner.getAllMissions(MissionStatus.FAILURE);

        assertEquals(3, actual.size(), "there should be 3 missions with status failure");
        assertContainsAll(expected, actual);
    }

    @Test
    void testGetAllMissionsWithStatusPartialFailure() {
        Collection<Mission> expected = getMissionsById(MissionsProducer.IDS_OF_MISSIONS_WITH_STATUS_PARTIAL_FAILURE);
        Collection<Mission> actual = MJTSpaceScanner.getAllMissions(MissionStatus.PARTIAL_FAILURE);

        assertEquals(1, actual.size(), "there should be 1 mission with status partial failure");
        assertContainsAll(expected, actual);
    }

    @Test
    void testGetAllMissionsWithStatusPreLaunchFailure() {
        Collection<Mission> expected = getMissionsById(MissionsProducer.IDS_OF_MISSIONS_WITH_STATUS_PRELAUNCH_FAILURE);
        Collection<Mission> actual = MJTSpaceScanner.getAllMissions(MissionStatus.PRELAUNCH_FAILURE);

        assertEquals(1, actual.size(), "there should be 1 mission with status prelaunch failure");
        assertContainsAll(expected, actual);
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsWhenFromIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        emptyMJTSpaceScanner.getCompanyWithMostSuccessfulMissions(null, LocalDate.now()),
                "expected IllegalArgumentException when from is null");
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsWhenToIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        emptyMJTSpaceScanner.getCompanyWithMostSuccessfulMissions(LocalDate.now(), null),
                "expected IllegalArgumentException when to is null");
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsWhenBothFromAndToIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        emptyMJTSpaceScanner.getCompanyWithMostSuccessfulMissions(null, null),
                "expected IllegalArgumentException when to is null");
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsWhenThereAreNone() {
        String actual = emptyMJTSpaceScanner.getCompanyWithMostSuccessfulMissions(LocalDate.now(), LocalDate.now());

        assertTrue(actual.isEmpty(), "no company has successful missions when there are no missions");
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsInInvalidTimeFrameExpectedTimeFrameMisMatchException() {
        LocalDate from = LocalDate.now();
        LocalDate to = from.minusDays(1);

        assertThrows(TimeFrameMismatchException.class, () ->
                        MJTSpaceScanner.getCompanyWithMostSuccessfulMissions(from, to),
                "invalid time frame");
    }

    @Test
    void testGetCompanyWithMostSuccessfulMissionsInValidTimeFrame() {
        LocalDate from = LocalDate.of(2000, 1, 1);
        LocalDate to = LocalDate.of(2020, 1, 1);

        String expected = "VKS RF";
        String actual = MJTSpaceScanner.getCompanyWithMostSuccessfulMissions(from, to);

        assertEquals(expected, actual, "expected company differs from actual");
    }

    @Test
    void testGetMissionsPerCountryWhenThereAreNone() {
        Map<String, Collection<Mission>> actual = emptyMJTSpaceScanner.getMissionsPerCountry();

        assertEquals(0, actual.size(), "there should be no missions per country when there are no missions");
    }

    @Test
    void testGetMissionsPerCountry() {
        Map<String, Collection<Mission>> actualMissionsPerCountry = MJTSpaceScanner.getMissionsPerCountry();

        assertEquals(actualMissionsPerCountry.size(), 7, "there should be missions for 7 countries");
        assertContainsAllForCountry(actualMissionsPerCountry, Country.RUSSIA);
        assertContainsAllForCountry(actualMissionsPerCountry, Country.CHINA);
        assertContainsAllForCountry(actualMissionsPerCountry, Country.NORTH_KOREA);
        assertContainsAllForCountry(actualMissionsPerCountry, Country.KAZAKHSTAN);
        assertContainsAllForCountry(actualMissionsPerCountry, Country.USA);
        assertContainsAllForCountry(actualMissionsPerCountry, Country.IRAN);
        assertContainsAllForCountry(actualMissionsPerCountry, Country.FRANCE);
    }

    private void assertContainsAllForCountry(Map<String, Collection<Mission>> missionsPerCountry, Country country) {
        Collection<Mission> expectedMissions = getMissionsById(MissionsProducer.getMissionIdsByCountry(country));

        assertTrue(missionsPerCountry.containsKey(Country.toString(country))
                        && missionsPerCountry.get(Country.toString(country)).containsAll(expectedMissions)
                        && expectedMissions.containsAll(missionsPerCountry.get(Country.toString(country))),
                "actual missions for " + Country.toString(country) + " differ from expected");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsWhenNIsNegativeExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getTopNLeastExpensiveMissions(-4, MissionStatus.SUCCESS, RocketStatus.STATUS_RETIRED),
                "n should be at least 1");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsWhenNIsZeroExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getTopNLeastExpensiveMissions(0, MissionStatus.SUCCESS, RocketStatus.STATUS_RETIRED),
                "n should be at least 1");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsWhenMissionStatusIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getTopNLeastExpensiveMissions(1, null, RocketStatus.STATUS_ACTIVE),
                "mission status should not be null");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsWhenRocketStatusIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getTopNLeastExpensiveMissions(1, MissionStatus.SUCCESS, null),
                "rocket status should not be null");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsMissionStatusSuccessRocketStatusActiveTop1() {
        Collection<Mission> expected = getMissionsById(new int[]{6});
        Collection<Mission> actual = MJTSpaceScanner.getTopNLeastExpensiveMissions(1, MissionStatus.SUCCESS, RocketStatus.STATUS_ACTIVE);

        assertIterableEquals(expected, actual, "actual differs from expected");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsMissionStatusSuccessRocketStatusRetiredTop3() {
        Collection<Mission> expected = getMissionsById(new int[]{10, 13, 14});
        Collection<Mission> actual = MJTSpaceScanner.getTopNLeastExpensiveMissions(3, MissionStatus.SUCCESS, RocketStatus.STATUS_RETIRED);

        assertIterableEquals(expected, actual, "actual differs from expected");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsMissionStatusFailureRocketStatusActiveTop1() {
        Collection<Mission> expected = getMissionsById(new int[]{});
        Collection<Mission> actual = MJTSpaceScanner.getTopNLeastExpensiveMissions(1, MissionStatus.FAILURE, RocketStatus.STATUS_ACTIVE);

        assertIterableEquals(expected, actual, "actual differs from expected");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsMissionStatusFailureRocketStatusRetiredTop2() {
        Collection<Mission> expected = getMissionsById(new int[]{5, 4});
        Collection<Mission> actual = MJTSpaceScanner.getTopNLeastExpensiveMissions(2, MissionStatus.FAILURE, RocketStatus.STATUS_RETIRED);

        assertIterableEquals(expected, actual, "actual differs from expected");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsMissionStatusPartialFailureRocketStatusActiveTop1() {
        Collection<Mission> expected = getMissionsById(new int[]{});
        Collection<Mission> actual = MJTSpaceScanner.getTopNLeastExpensiveMissions(1, MissionStatus.PARTIAL_FAILURE, RocketStatus.STATUS_ACTIVE);

        assertIterableEquals(expected, actual, "actual differs from expected");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsMissionStatusPartialFailureRocketStatusRetiredTop1() {
        Collection<Mission> expected = getMissionsById(new int[]{2});
        Collection<Mission> actual = MJTSpaceScanner.getTopNLeastExpensiveMissions(1, MissionStatus.PARTIAL_FAILURE, RocketStatus.STATUS_RETIRED);

        assertIterableEquals(expected, actual, "actual differs from expected");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsMissionStatusPreLaunchFailureRocketStatusActiveTop1() {
        Collection<Mission> expected = getMissionsById(new int[]{});
        Collection<Mission> actual = MJTSpaceScanner.getTopNLeastExpensiveMissions(1, MissionStatus.PRELAUNCH_FAILURE, RocketStatus.STATUS_RETIRED);

        assertIterableEquals(expected, actual, "actual differs from expected");
    }

    @Test
    void testGetTopNLeastExpensiveMissionsMissionStatusPreLaunchFailureRocketStatusRetiredTop1() {
        Collection<Mission> expected = getMissionsById(new int[]{});
        Collection<Mission> actual = MJTSpaceScanner.getTopNLeastExpensiveMissions(1, MissionStatus.PRELAUNCH_FAILURE, RocketStatus.STATUS_RETIRED);

        assertIterableEquals(expected, actual, "actual differs from expected");
    }

    @Test
    void testGetMostDesiredLocationForMissionsPerCompanyWhenNone() {
        Map<String, String> actual = emptyMJTSpaceScanner.getMostDesiredLocationForMissionsPerCompany();
        assertEquals(0, actual.size(), "the should not be most desired location when there are no missions");
    }

    @Test
    void testGetMostDesiredLocationForMissionsPerCompany() {
        Map<String, String> expected = Map.of("VKS RF", "Site 32/2, Plesetsk Cosmodrome, Russia",
                "KCST", "Pad 1, Tonghae Satellite Launching Ground, North Korea",
                "Roscosmos", "Site 45/1, Baikonur Cosmodrome, Kazakhstan",
                "Arianespace", "ELA-3, Guiana Space Centre, French Guiana, France",
                "Landspace", "Site 95, Jiuquan Satellite Launch Center, China",
                "Northrop", "LP-0A, Wallops Flight Facility, Virginia, USA",
                "ISA", "Imam Khomeini Spaceport, Semnan Space Center, Iran");

        Map<String, String> actual = MJTSpaceScanner.getMostDesiredLocationForMissionsPerCompany();

        assertEquals(expected.size(), actual.size(), "actual size differs from expected size");
        assertEquals(expected, actual, "maps should be the same");
    }

    @Test
    void testGetLocationWithMostSuccessfulMissionsPerCompanyWhenFromIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getLocationWithMostSuccessfulMissionsPerCompany(null, LocalDate.now()),
                "expected IllegalArgumentException when from is null");
    }

    @Test
    void testGetLocationWithMostSuccessfulMissionsPerCompanyWhenToIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getLocationWithMostSuccessfulMissionsPerCompany(LocalDate.now(), null),
                "expected IllegalArgumentException when to is null");
    }

    @Test
    void testGetLocationWithMostSuccessfulMissionsPerCompanyWhenBothFromAndToIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getLocationWithMostSuccessfulMissionsPerCompany(null, null),
                "expected IllegalArgumentException when to is null");
    }

    @Test
    void testGetLocationWithMostSuccessfulMissionsPerCompanyWhenFromIsBeforeToExpectedTimeFrameMisMatchException() {
        LocalDate from = LocalDate.now();
        LocalDate to = from.minusDays(1);

        assertThrows(TimeFrameMismatchException.class, () ->
                        MJTSpaceScanner.getLocationWithMostSuccessfulMissionsPerCompany(from, to),
                "expected IllegalArgumentException when to is null");
    }

    @Test
    void testGetLocationWithMostSuccessfulMissionsPerCompany() {
        Map<String, String> expected = Map.of("Roscosmos", "Site 45/1, Baikonur Cosmodrome, Kazakhstan",
                "Northrop", "LP-0A, Wallops Flight Facility, Virginia, USA");

        LocalDate from = LocalDate.of(2010, 1, 1);
        LocalDate to = LocalDate.of(2020, 1, 1);
        Map<String, String> actual = MJTSpaceScanner.getLocationWithMostSuccessfulMissionsPerCompany(from, to);

        assertEquals(expected, actual, "maps should be the same");
    }

    @Test
    void testGetAllRocketsWhenNone() {
        Collection<Rocket> actual = emptyMJTSpaceScanner.getAllRockets();

        assertEquals(0, actual.size(), "there should not be any rockets");
    }

    @Test
    void testGetAllRockets() {
        Collection<Rocket> actual = MJTSpaceScanner.getAllRockets();

        assertEquals(7, actual.size(), "there should be 7 rockets");
        assertContainsAll(rockets, actual);
    }

    @Test
    void getTopNTallestRocketsWhenNIsNegativeExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getTopNTallestRockets(-4),
                "n should be at least 1");
    }

    @Test
    void getTopNTallestRocketsWhenNIsZeroExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getTopNTallestRockets(0),
                "n should be at least 1");
    }

    @Test
    void getTopNTallestRocketsTop5() {
        List<Rocket> expected = getRocketsById(new int[]{2, 5, 3, 0, 1});
        List<Rocket> actual = MJTSpaceScanner.getTopNTallestRockets(5);

        assertIterableEquals(expected, actual);
    }

    @Test
    void testGetWikiPageForRocketWhenNone() {
        Map<String, Optional<String>> actual = emptyMJTSpaceScanner.getWikiPageForRocket();

        assertEquals(0, actual.size(), "there should not be any rockets");
    }

    @Test
    void testGetWikiPageForRocket() {
        Map<String, Optional<String>> expected = Map.of(
                "Antares 110", Optional.of("https://en.wikipedia.org/wiki/Antares_(rocket)"),
                "Ariane 5 G+", Optional.of("https://en.wikipedia.org/wiki/Ariane_5"),
                "Ariane 5 G", Optional.of("https://en.wikipedia.org/wiki/Ariane_5"),
                "Zenit-2 FG", Optional.of("https://en.wikipedia.org/wiki/Zenit_%28rocket_family%29"),
                "ZhuQue-1", Optional.of("https://en.wikipedia.org/wiki/LandSpace"),
                "Tsyklon-3", Optional.of("https://en.wikipedia.org/wiki/Tsyklon-3"),
                "Unha-2", Optional.of("https://en.wikipedia.org/wiki/Unha")
        );
        Map<String, Optional<String>> actual = MJTSpaceScanner.getWikiPageForRocket();

        assertEquals(7, actual.size(), "there should be 7 wikis for rockets");
        assertEquals(expected, actual, "expected differs from actual");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsWhenNIsNegativeExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(-4, MissionStatus.SUCCESS, RocketStatus.STATUS_ACTIVE),
                "expected IllegalArgumentException when n is negative");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsWhenNIsZeroExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(0, MissionStatus.SUCCESS, RocketStatus.STATUS_ACTIVE),
                "expected IllegalArgumentException when n is negative");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsWhenMissionStatusIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(1, null, RocketStatus.STATUS_ACTIVE),
                "expected IllegalArgumentException when n is negative");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsWhenRocketStatusIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(1, MissionStatus.SUCCESS, null),
                "expected IllegalArgumentException when n is negative");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsWhenBothMissionAndRocketStatusAreNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(1, null, null),
                "expected IllegalArgumentException when n is negative");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsMissionStatusSuccessRocketStatusActiveTop1() {
        List<String> expected = List.of("https://en.wikipedia.org/wiki/Zenit_%28rocket_family%29");
        List<String> actual = MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(1, MissionStatus.SUCCESS, RocketStatus.STATUS_ACTIVE);

        assertIterableEquals(expected, actual, "should be the same");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsMissionStatusSuccessRocketStatusRetiredTop2() {
        List<String> expected = List.of("https://en.wikipedia.org/wiki/Ariane_5", "https://en.wikipedia.org/wiki/Tsyklon-3");
        List<String> actual = MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(2, MissionStatus.SUCCESS, RocketStatus.STATUS_RETIRED);

        assertIterableEquals(expected, actual, "should be the same");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsMissionStatusFailureRocketStatusActiveTop1() {
        List<String> expected = List.of();
        List<String> actual = MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(1, MissionStatus.FAILURE, RocketStatus.STATUS_ACTIVE);

        assertIterableEquals(expected, actual, "should be the same");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsMissionStatusFailureRocketStatusRetiredTop2() {
        List<String> expected = List.of("https://en.wikipedia.org/wiki/Unha", "https://en.wikipedia.org/wiki/Tsyklon-3");
        List<String> actual = MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(2, MissionStatus.FAILURE, RocketStatus.STATUS_RETIRED);

        assertIterableEquals(expected, actual, "should be the same");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsMissionStatusPartialFailureRocketStatusActiveTop1() {
        List<String> expected = List.of();
        List<String> actual = MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(1, MissionStatus.PARTIAL_FAILURE, RocketStatus.STATUS_ACTIVE);

        assertIterableEquals(expected, actual, "should be the same");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsMissionStatusPartialFailureRocketStatusRetiredTop1() {
        List<String> expected = List.of("https://en.wikipedia.org/wiki/Tsyklon-3");
        List<String> actual = MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(1, MissionStatus.PARTIAL_FAILURE, RocketStatus.STATUS_RETIRED);

        assertIterableEquals(expected, actual, "should be the same");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsMissionStatusPreLaunchFailureRocketStatusActiveTop1() {
        List<String> expected = List.of();
        List<String> actual = MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(1, MissionStatus.PRELAUNCH_FAILURE, RocketStatus.STATUS_ACTIVE);

        assertIterableEquals(expected, actual, "should be the same");
    }

    @Test
    void testGetWikiPagesForRocketsUsedInMostExpensiveMissionsMissionStatusPreLaunchFailureRocketStatusRetiredTop1() {
        List<String> expected = List.of();
        List<String> actual = MJTSpaceScanner.getWikiPagesForRocketsUsedInMostExpensiveMissions(1, MissionStatus.PRELAUNCH_FAILURE, RocketStatus.STATUS_RETIRED);

        assertIterableEquals(expected, actual, "should be the same");
    }

    @Test
    void testSaveMostReliableRocketWhenOutputStreamIsNullExpectedIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.saveMostReliableRocket(null, LocalDate.now(), LocalDate.now()),
                "outputStream should not be null");
    }

    @Test
    void testSaveMostReliableRocketWhenFromIsNullExpectedIllegalArgumentException() {
        OutputStream mockOutputStream = new ByteArrayOutputStream();

        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.saveMostReliableRocket(mockOutputStream, null, LocalDate.now()),
                "outputStream should not be null");
    }

    @Test
    void testSaveMostReliableRocketWhenToIsNullExpectedIllegalArgumentException() {
        OutputStream mockOutputStream = new ByteArrayOutputStream();

        assertThrows(IllegalArgumentException.class, () ->
                        MJTSpaceScanner.saveMostReliableRocket(mockOutputStream, LocalDate.now(), null),
                "outputStream should not be null");
    }

    @Test
    void testSaveMostReliableRocketWhenInvalidTimeFrameExpectedTimeFrameMisMatchException() {
        LocalDate from = LocalDate.now();
        LocalDate to = from.minusDays(1);
        OutputStream mockOutputStream = new ByteArrayOutputStream();

        assertThrows(TimeFrameMismatchException.class, () ->
                        MJTSpaceScanner.saveMostReliableRocket(mockOutputStream, from, to),
                "outputStream should not be null");
    }

    @Test
    void testSaveMostReliableRocketEncryptCorrect() {
        LocalDate from = LocalDate.of(2001, 12, 28);
        LocalDate to = LocalDate.of(2001, 12, 31);
        ByteArrayOutputStream resultOutputStream = new ByteArrayOutputStream();
        OutputStream resultOutputStreamDecrypted = new ByteArrayOutputStream();

        try {
            MJTSpaceScanner.saveMostReliableRocket(resultOutputStream, from, to);
            InputStream resultToDecrypt = new ByteArrayInputStream(resultOutputStream.toByteArray());
            symmetricBlockCipher.decrypt(resultToDecrypt, resultOutputStreamDecrypted);

            String mostReliableRocket = resultOutputStreamDecrypted.toString();
            String expectedMostReliableRocket = "Tsyklon-3";
            assertEquals(expectedMostReliableRocket, mostReliableRocket,
                    "decrypted rocket is not the expected most reliable one");

        } catch (Exception e) {
            fail("saveMostReliableRocket should not throw an Exception when all arguments are correct.");
        }
    }

    @Test
    void testSaveMostReliableRocketEncryptNoRocket() {
        LocalDate from = LocalDate.of(2001, 12, 26);
        LocalDate to = LocalDate.of(2001, 12, 27);
        ByteArrayOutputStream resultOutputStream = new ByteArrayOutputStream();
        OutputStream resultOutputStreamDecrypted = new ByteArrayOutputStream();

        try {
            MJTSpaceScanner.saveMostReliableRocket(resultOutputStream, from, to);
            InputStream resultToDecrypt = new ByteArrayInputStream(resultOutputStream.toByteArray());
            symmetricBlockCipher.decrypt(resultToDecrypt, resultOutputStreamDecrypted);

            String mostReliableRocket = resultOutputStreamDecrypted.toString();
            String expectedMostReliableRocket = "";
            assertEquals(expectedMostReliableRocket, mostReliableRocket,
                    "decrypted rocket is not the expected most reliable one");

        } catch (Exception e) {
            fail("saveMostReliableRocket should not throw an Exception when all arguments are correct.");
        }
    }

    private List<Mission> getMissionsById(int[] ids) {
        List<Mission> result = new ArrayList<>();

        for (int id : ids) {
            result.add(missions.get(id));
        }

        return result;
    }

    private List<Rocket> getRocketsById(int[] ids) {
        List<Rocket> result = new ArrayList<>();

        for (int id : ids) {
            result.add(rockets.get(id));
        }

        return result;
    }

    private static <T> void load(Reader sourceReader, Collection<T> destinationCollection, Function<CSVRecord, T> of) {
        try (BufferedReader reader = new BufferedReader(sourceReader)) {
            for (CSVRecord record : CSVFormat.DEFAULT.parse(reader).getRecords().stream().skip(1).toList()) {
                destinationCollection.add(of.apply(record));
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot load CSV records");
        }
    }

    private static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey originalKey = keyGenerator.generateKey();
        return originalKey;
    }

    private <T> void assertContainsAll(Collection<T> expected, Collection<T> actual) {
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected),
                "actual differs from expected");
    }
}
