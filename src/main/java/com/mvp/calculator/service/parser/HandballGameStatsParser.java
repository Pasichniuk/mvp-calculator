package com.mvp.calculator.service.parser;

import com.mvp.calculator.exception.InvalidFileContentException;
import com.mvp.calculator.model.GameStats;
import com.mvp.calculator.model.GameType;
import com.mvp.calculator.model.player.HandballPlayerGameStats;
import com.mvp.calculator.model.player.PlayerGameStats;

import java.util.List;

public class HandballGameStatsParser extends GameStatsParser {

    @Override
    public GameStats parse(List<String[]> data) {
        return super.parse(GameType.HANDBALL, data);
    }

    @Override
    protected void validateRow(String[] row) {
        if (row.length != 6) {
            throw new InvalidFileContentException("All rows must have 6 columns for game type 'Handball'");
        }
    }

    @Override
    protected PlayerGameStats getPlayerGameStatsFromCsvRow(String[] row) {
        return HandballPlayerGameStats.builder()
                .playerName(row[0])
                .nickname(row[1])
                .number(row[2])
                .teamName(row[3])
                .goalsMade(Integer.parseInt(row[4]))
                .goalsReceived(Integer.parseInt(row[5]))
                .build();
    }
}
