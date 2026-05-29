package com.sda.threatintel.adapter;

import com.sda.threatintel.domain.IndicatorType;
import com.sda.threatintel.domain.ReputationResult;

// PATTERN: Adapter (Target Interface)
// RATIONALE: Defines the unified interface that all threat intel providers must implement
public interface ThreatIntelProvider {
    ReputationResult checkReputation(String indicator, IndicatorType type);
}