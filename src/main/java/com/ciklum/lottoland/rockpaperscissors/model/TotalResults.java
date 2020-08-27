package com.ciklum.lottoland.rockpaperscissors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Pojo that defines all played rounds stats
 */
@AllArgsConstructor
@Getter
@ToString
public class TotalResults {
      private long totalRounds;
      private long totalWins;
      private long totalLoses;
      private long totalDraws;
}
