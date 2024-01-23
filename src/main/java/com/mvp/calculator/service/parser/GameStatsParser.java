package com.mvp.calculator.service.parser;

import com.mvp.calculator.model.GameStats;
import com.mvp.calculator.model.GameType;
import com.mvp.calculator.model.player.PlayerGameStats;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GameStatsParser {

    public GameStats parse(GameType type, List<String[]> data) {
        List<PlayerGameStats> playerGameStats = data.stream().skip(1).map(row -> {
            validateRow(row);
            return getPlayerGameStatsFromCsvRow(row);
        }).collect(Collectors.toUnmodifiableList());

        return GameStats.builder()
                .type(type)
                .playerGameStats(playerGameStats)
                .build();
    }

    public abstract GameStats parse(List<String[]> data);

    protected abstract void validateRow(String[] row);

    protected abstract PlayerGameStats getPlayerGameStatsFromCsvRow(String[] row);
}
