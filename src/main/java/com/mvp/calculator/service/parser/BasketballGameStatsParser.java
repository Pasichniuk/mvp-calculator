package com.mvp.calculator.service.parser;

import com.mvp.calculator.exception.InvalidFileContentException;
import com.mvp.calculator.model.GameStats;
import com.mvp.calculator.model.GameType;
import com.mvp.calculator.model.player.BasketballPlayerGameStats;
import com.mvp.calculator.model.player.PlayerGameStats;

import java.util.List;

public class BasketballGameStatsParser extends GameStatsParser {

    @Override
    public GameStats parse(List<String[]> data) {
        return super.parse(GameType.BASKETBALL, data);
    }

    @Override
    protected void validateRow(String[] row) {
        if (row.length != 7) {
            throw new InvalidFileContentException("All rows must have 7 columns for game type 'Basketball'");
        }
    }

    @Override
    protected PlayerGameStats getPlayerGameStatsFromCsvRow(String[] row) {
        return BasketballPlayerGameStats.builder()
                .playerName(row[0])
                .nickname(row[1])
                .number(row[2])
                .teamName(row[3])
                .scoredPoints(Integer.parseInt(row[4]))
                .rebounds(Integer.parseInt(row[5]))
                .assists(Integer.parseInt(row[6]))
                .build();
    }
}
