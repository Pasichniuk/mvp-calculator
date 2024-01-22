package com.mvp.calculator.service;

import com.mvp.calculator.exception.InvalidFileContentException;
import com.mvp.calculator.exception.UnsupportedGameTypeException;
import com.mvp.calculator.model.*;
import com.mvp.calculator.model.player.*;
import com.mvp.calculator.service.parser.GameStatsParserProvider;
import com.mvp.calculator.service.util.CsvFileUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MvpCalculatorService {

    public void calculateMvp(List<String> fileNames) {
        Map<String, Integer> playersRating = new HashMap<>();

        var gameStatsList = fileNames.stream()
                .map(CsvFileUtils::readDataFromCsv)
                .map(this::parseGameStats)
                .collect(Collectors.toList());

        gameStatsList.forEach(gameStats -> calculatePlayersRating(gameStats, playersRating));

        var mvp = Collections.max(playersRating.entrySet(), Map.Entry.comparingByValue()).getKey();

        System.err.printf("%n======= MVP is %s =======%n%n", mvp);
    }

    private GameStats parseGameStats(List<String[]> rawData) {
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

    private void calculatePlayersRating(GameStats gameStats, Map<String, Integer> playersRating) {
        for (var playerStats : gameStats.getPlayerGameStats()) {
            var nickname = playerStats.getNickname();
            var rating = getRating(gameStats.getType(), playerStats);

            // TODO: add 10 points if team won

            if (playersRating.containsKey(nickname)) {
                var totalPlayerRating = playersRating.get(nickname);
                rating = totalPlayerRating + rating;
            }

            playersRating.put(nickname, rating);
        }
    }

    private int getRating(GameType type, PlayerGameStats playerStats) {
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
}
