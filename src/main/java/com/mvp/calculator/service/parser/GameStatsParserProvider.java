package com.mvp.calculator.service.parser;

import com.mvp.calculator.model.GameType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GameStatsParserProvider {

    private static final BasketballGameStatsParser basketballGameStatsParser = new BasketballGameStatsParser();
    private static final HandballGameStatsParser handballGameStatsParser = new HandballGameStatsParser();

    public static GameStatsParser getParser(GameType gameType) {
        switch (gameType) {
            case BASKETBALL:
                return basketballGameStatsParser;
            case HANDBALL:
                return handballGameStatsParser;
            default:
                throw new IllegalArgumentException(String.format("Game type '%s' is not supported", gameType.getValue()));
        }
    }
}
