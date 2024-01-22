package com.mvp.calculator.service.parser;

import com.mvp.calculator.model.GameStats;

import java.util.List;

public interface GameStatsParser {
    GameStats parse(List<String[]> data);
}
