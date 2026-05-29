package com.sda.threatintel.adapter;

import com.sda.threatintel.domain.IndicatorType;
import com.sda.threatintel.domain.ReputationResult;

public interface ThreatIntelProvider {
    ReputationResult checkReputation(String indicator, IndicatorType type);
}