package bg.sofia.uni.fmi.mjt.space;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import bg.sofia.uni.fmi.mjt.space.algorithm.Rijndael;
import bg.sofia.uni.fmi.mjt.space.algorithm.SymmetricBlockCipher;
import bg.sofia.uni.fmi.mjt.space.exception.TimeFrameMismatchException;
import bg.sofia.uni.fmi.mjt.space.mission.Mission;
import bg.sofia.uni.fmi.mjt.space.mission.MissionStatus;
import bg.sofia.uni.fmi.mjt.space.rocket.Rocket;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.crypto.SecretKey;

public class MJTSpaceScanner implements SpaceScannerAPI {
    private final Collection<Mission> missions = new HashSet<>();
    private final Collection<Rocket> rockets = new HashSet<>();
    private final SymmetricBlockCipher symmetricBlockCipher;

    public MJTSpaceScanner(Reader missionsReader, Reader rocketsReader, SecretKey secretKey) {
        load(missionsReader, this.missions, Mission::of);
        load(rocketsReader, this.rockets, Rocket::of);
        this.symmetricBlockCipher = new Rijndael(secretKey);
    }

    private <T> void load(Reader sourceReader, Collection<T> destinationCollection, Function<CSVRecord, T> of) {
        try (BufferedReader reader = new BufferedReader(sourceReader)) {
            for (CSVRecord record : CSVFormat.DEFAULT.parse(reader).getRecords().stream().skip(1).toList()) {
                destinationCollection.add(of.apply(record));
            }
        } catch (IOException e) {
            throw new RuntimeException("BUM");
        }
    }

    @Override
    public Collection<Mission> getAllMissions() {
        return missions;
    }

    @Override
    public Collection<Mission> getAllMissions(MissionStatus missionStatus) {
        validateNotNull(missionStatus, "missionStatus");

        return missions.stream()
                .filter(m -> m.missionStatus().equals(missionStatus))
                .collect(Collectors.toSet());
    }

    @Override
    public String getCompanyWithMostSuccessfulMissions(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("null");
        }

        if (to.isBefore(from)) {
            throw new TimeFrameMismatchException("Invalid time frame");
        }

        var r = missions.stream()
                .filter(mission -> mission.date().isAfter(from) && mission.date().isBefore(to))
                .filter(mission -> mission.missionStatus().equals(MissionStatus.SUCCESS)).toList();
        System.out.println(r.size());

        return missions.stream()
                .filter(mission -> mission.date().isAfter(from) && mission.date().isBefore(to))
                .filter(mission -> mission.missionStatus().equals(MissionStatus.SUCCESS))
                .collect(Collectors.groupingBy(Mission::company, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    @Override
    public Map<String, Collection<Mission>> getMissionsPerCountry() {
        return missions.stream()
                .collect(Collectors.groupingBy((
                        m -> {
                            String[] tokens = m.location().split(",");
                            return tokens[tokens.length - 1].trim();
                        }
                ), Collectors.toCollection(HashSet::new)));
    }

    @Override
    public List<Mission> getTopNLeastExpensiveMissions(int n, MissionStatus missionStatus, RocketStatus rocketStatus) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be at least 1");
        }

        validateNotNull(missionStatus, "missionStatus");
        validateNotNull(rocketStatus, "rocketStatus");

        return missions.stream()
                .filter(mission -> mission.missionStatus().equals(missionStatus))
                .filter(mission -> mission.rocketStatus().equals(rocketStatus))
                .filter(mission -> mission.cost().isPresent())
                .sorted(Comparator.comparingDouble(mission -> mission.cost().orElse(0.0)))
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getMostDesiredLocationForMissionsPerCompany() {
        Map<String, Map<String, Long>> missionsCountPerCompanyAndLocation = missions.stream()
                .collect(Collectors.groupingBy(Mission::company,
                        Collectors.groupingBy(Mission::location, Collectors.counting())));

        return missionsCountPerCompanyAndLocation.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .orElse(Map.entry("", Long.valueOf("0")))
                                .getKey()
                ));
    }

    @Override
    public Map<String, String> getLocationWithMostSuccessfulMissionsPerCompany(LocalDate from, LocalDate to) {
        validateNotNull(from, "from");
        validateNotNull(to, "to");

        if (to.isBefore(from)) {
            throw new TimeFrameMismatchException("Invalid time frame");
        }

        var missionsCountPerCompanyAndLocation = missions.stream()
                .filter(m -> m.date().isAfter(from)
                        && m.date().isBefore(to)
                        && m.missionStatus() == MissionStatus.SUCCESS)
                .collect(Collectors.groupingBy(Mission::company,
                        Collectors.groupingBy(Mission::location, Collectors.counting())));

        return missionsCountPerCompanyAndLocation.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .orElseThrow(() -> new NoSuchElementException(
                                        "There is a company with location without a successful mission"))
                                .getKey()
                ));
    }

    @Override
    public Collection<Rocket> getAllRockets() {
        return rockets;
    }

    @Override
    public List<Rocket> getTopNTallestRockets(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be at least 1");
        }

        return rockets.stream()
                .filter(rocket -> rocket.height().isPresent())
                .sorted(Comparator.comparing(rocket -> rocket.height().orElse(0.0), Comparator.reverseOrder()))
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Optional<String>> getWikiPageForRocket() {
        return rockets.stream()
                .collect(Collectors.toMap(Rocket::name, Rocket::wiki));
    }

    @Override
    public List<String> getWikiPagesForRocketsUsedInMostExpensiveMissions(int n, MissionStatus missionStatus, RocketStatus rocketStatus) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be at least 1");
        }

        validateNotNull(missionStatus, "missionStatus");
        validateNotNull(rocketStatus, "rocketStatus");

        Set<String> rocketNames = missions.stream()
                .filter(m -> m.missionStatus() == missionStatus && m.rocketStatus() == rocketStatus && m.cost().isPresent())
                .sorted((m1, m2) -> {
                    if (m1.cost().get() > m2.cost().get()) {
                        return -1;
                    } else if (m1.cost().get().equals(m2.cost().get())) {
                        return 0;
                    }
                    return 1;
                })
                .map(m -> m.detail().rocketName())
                .distinct()
                .limit(n)
                .collect(Collectors.toSet());

        return rockets.stream()
                .filter(r -> rocketNames.contains(r.name()) && r.wiki().isPresent())
                .map(r -> r.wiki().get())
                .toList();
    }

    @Override
    public void saveMostReliableRocket(OutputStream outputStream, LocalDate from, LocalDate to) throws Exception {
        validateNotNull(outputStream, "outputStream");
        validateNotNull(from, "from");
        validateNotNull(to, "to");

        if (to.isBefore(from)) {
            throw new TimeFrameMismatchException("Invalid time frame");
        }

        Map<String, List<Mission>> result = missions.stream()
                .filter(mission -> mission.date().isAfter(from) && mission.date().isBefore(to))
                .collect(Collectors.groupingBy(mission -> mission.detail().rocketName(), Collectors.mapping(mission -> mission, Collectors.toList())));

        Map<String, Double> reliabilityMap = result.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> calculateReliability(entry.getValue())));

        String mostReliableRocket = reliabilityMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

        symmetricBlockCipher.encrypt(stringToInputStream(mostReliableRocket), outputStream);
    }

    private InputStream stringToInputStream(String str) {
        byte[] bytes = str.getBytes();
        return new ByteArrayInputStream(bytes);
    }

    private double calculateReliability(List<Mission> missions) {
        if (missions.isEmpty()) {
            return 0.0;
        }

        long successfulMissions = getNumberOfMissionsWithStatus(MissionStatus.SUCCESS);
        long failedMissions = getNumberOfMissionsWithStatus(MissionStatus.FAILURE);
        long partialFailureMissions = getNumberOfMissionsWithStatus(MissionStatus.PARTIAL_FAILURE);
        long preLaunchFailureMissions = getNumberOfMissionsWithStatus(MissionStatus.PRELAUNCH_FAILURE);

        long total = successfulMissions * 2 + failedMissions + partialFailureMissions + preLaunchFailureMissions;

        return (double) total / missions.size();
    }

    private long getNumberOfMissionsWithStatus(MissionStatus missionStatus) {
        return missions.stream().filter(mission -> mission.missionStatus().equals(missionStatus)).count();
    }

    private void validateNotNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentException(name + "is null");
        }
    }
}
