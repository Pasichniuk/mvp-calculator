package com.mvp.calculator.service;

import com.mvp.calculator.exception.InvalidFileContentException;
import com.mvp.calculator.exception.UnsupportedGameTypeException;
import com.mvp.calculator.model.*;
import com.mvp.calculator.model.player.*;
import com.mvp.calculator.service.parser.GameStatsParserProvider;
import com.mvp.calculator.service.util.CsvFileUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MvpCalculatorService {

    public String calculateMvp(String dirPath) {
        CsvFileUtils.verifyNonCsvFilesAbsentInDirectory(dirPath); // requirement
        var fileNames = CsvFileUtils.getNamesOfCsvFilesInDirectory(dirPath);

        Map<String, Integer> playersRating = new HashMap<>();

        var gameStatsList = fileNames.stream()
                .map(CsvFileUtils::readDataFromCsv)
                .map(MvpCalculatorService::parseGameStats)
                .collect(Collectors.toList());

        gameStatsList.forEach(gameStats -> calculatePlayersRating(gameStats, playersRating));

        return Collections.max(playersRating.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private static GameStats parseGameStats(List<String[]> rawData) {
        if (CollectionUtils.isEmpty(rawData)) {
            throw new InvalidFileContentException("File is empty");
        }

        String[] firstRow = rawData.get(0);
        if (firstRow.length != 1) {
            throw new InvalidFileContentException("First row should contain only game type");
        }

        GameType type;
        String typeStr = firstRow[0];
        if (EnumUtils.isValidEnum(GameType.class, typeStr)) {
            type = GameType.valueOf(typeStr);
        } else {
            throw new InvalidFileContentException("Not able to resolve game type");
        }

        var parser = GameStatsParserProvider.getParser(type);
        return parser.parse(rawData);
    }

    private static void calculatePlayersRating(GameStats gameStats, Map<String, Integer> playersRating) {
        String winnerTeamName = getWinnerTeam(gameStats);
        Set<String> nicknames = new HashSet<>();

        for (var playerStats : gameStats.getPlayerGameStats()) {
            var nickname = playerStats.getNickname();
            if (!nicknames.add(nickname)) {
                var message = "One player may play in different teams and positions in different games, but not in the same game";
                throw new InvalidFileContentException(message);
            }

            var rating = getRating(gameStats.getType(), playerStats);

            if (winnerTeamName.equals(playerStats.getTeamName())) {
                rating += 10;
            }

            if (playersRating.containsKey(nickname)) {
                var totalPlayerRating = playersRating.get(nickname);
                rating += totalPlayerRating;
            }

            playersRating.put(nickname, rating);
        }
    }

    private static String getWinnerTeam(GameStats gameStats) {
        Map<String, Integer> teamPoints = new HashMap<>();

        for (var playerStats : gameStats.getPlayerGameStats()) {
            var teamName = playerStats.getTeamName();
            var points = getTeamPoints(gameStats.getType(), playerStats);

            if (teamPoints.containsKey(teamName)) {
                var totalTeamPoints = teamPoints.get(teamName);
                points += totalTeamPoints;
            }

            teamPoints.put(teamName, points);
        }

        return Collections.max(teamPoints.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private static int getRating(GameType type, PlayerGameStats playerStats) {
        switch (type) {
            case BASKETBALL:
                var basketballPlayerStats = (BasketballPlayerGameStats) playerStats;
                return 2 * basketballPlayerStats.getScoredPoints() + basketballPlayerStats.getRebounds() + basketballPlayerStats.getAssists();
            case HANDBALL:
                var handballPlayerStats = (HandballPlayerGameStats) playerStats;
                return 2 * handballPlayerStats.getGoalsMade() - handballPlayerStats.getGoalsReceived();
            default:
                throw new UnsupportedGameTypeException(type);
        }
    }

    private static int getTeamPoints(GameType type, PlayerGameStats playerStats) {
        switch (type) {
            case BASKETBALL:
                var basketballPlayerStats = (BasketballPlayerGameStats) playerStats;
                return basketballPlayerStats.getScoredPoints();
            case HANDBALL:
                var handballPlayerStats = (HandballPlayerGameStats) playerStats;
                return handballPlayerStats.getGoalsMade();
            default:
                throw new UnsupportedGameTypeException(type);
        }
    }
}
