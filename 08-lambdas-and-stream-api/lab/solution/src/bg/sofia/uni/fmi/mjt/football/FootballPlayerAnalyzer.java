package bg.sofia.uni.fmi.mjt.football;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FootballPlayerAnalyzer {

    private static final int RATING_VARIANCE = 3;
    private final List<Player> players;

    public FootballPlayerAnalyzer(Reader reader) {
        try (var br = new BufferedReader(reader)) {
            players = br.lines()//
                    .skip(1)//
                    .map(Player::of)//
                    .toList();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not load dataset", ex);
        }
    }

    public List<Player> getAllPlayers() {
        return List.copyOf(players);
    }

    public Set<String> getAllNationalities() {
        return players.stream()//
                .map(Player::nationality)//
                .collect(Collectors.toUnmodifiableSet());
    }

    public Player getHighestPaidPlayerByNationality(String nationality) {
        if (null == nationality) {
            throw new IllegalArgumentException("Provided nationality cannot be null");
        }

        return players.stream()//
                .filter(p -> p.nationality().equals(nationality))//
                .max(Comparator.comparingLong(Player::wageEuro))//
                .orElseThrow(NoSuchElementException::new);
    }

    public Map<Position, Set<Player>> groupByPosition() {
        return players.stream()//
                .flatMap(this::mapPlayerToPositionEntry)//
                .collect(Collectors.toUnmodifiableMap(SimpleEntry::getKey, this::newSetFromPlayerEntry,
                        this::mergeSets));
    }

    private Stream<SimpleEntry<Position, Player>> mapPlayerToPositionEntry(Player player) {
        return player.positions()//
                .stream()//
                .map(pos -> new AbstractMap.SimpleEntry<>(pos, player));
    }

    private Set<Player> newSetFromPlayerEntry(SimpleEntry<Position, Player> playerEntry) {
        Set<Player> playerSet = new HashSet<>();
        playerSet.add(playerEntry.getValue());

        return playerSet;
    }

    private Set<Player> mergeSets(Set<Player> set1, Set<Player> set2) {
        set1.addAll(set2);

        return set1;
    }

    public Optional<Player> getTopProspectPlayerForPositionInBudget(Position position, long budget) {
        if (null == position) {
            throw new IllegalArgumentException("Provided position cannot be null");
        }
        if (budget < 0) {
            throw new IllegalArgumentException("Provided budget cannot be negative");
        }

        return players.stream()//
                .filter(p -> p.positions().contains(position))//
                .filter(p -> p.valueEuro() <= budget)//
                .max(Comparator.comparingDouble(this::calculatePlayerProspectRating));
    }

    private double calculatePlayerProspectRating(Player player) {
        return ((double) player.overallRating() + player.potential()) / player.age();
    }

    public Set<Player> getSimilarPlayers(Player player) {
        if (null == player) {
            throw new IllegalArgumentException("Provided player cannot be null");
        }

        return players.stream()//
                .filter(p -> canPlayWithSameFoot(p, player))//
                .filter(p -> canPlaySamePosition(p, player))//
                .filter(p -> isSimilarRating(p, player))//
                .collect(Collectors.toUnmodifiableSet());
    }

    private boolean canPlayWithSameFoot(Player player, Player targetPlayer) {
        return player.preferredFoot() == targetPlayer.preferredFoot();
    }

    private boolean canPlaySamePosition(Player player, Player targetPlayer) {
        return player.positions()//
                .stream()//
                .anyMatch(pos -> targetPlayer.positions().contains(pos));
    }

    private boolean isSimilarRating(Player player, Player targetPlayer) {
        int overallRating = player.overallRating();
        int targetOverallRating = targetPlayer.overallRating();

        return (targetOverallRating - RATING_VARIANCE) <= overallRating
                && overallRating <= (targetOverallRating + RATING_VARIANCE);
    }

    public Set<Player> getPlayersByFullNameKeyword(String keyword) {
        if (null == keyword) {
            throw new IllegalArgumentException("Provided keyword cannot be null");
        }

        return players.stream()//
                .filter(p -> p.fullName().contains(keyword))//
                .collect(Collectors.toUnmodifiableSet());
    }

}
