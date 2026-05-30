package com.sda.threatintel.domain;

public class ReputationResult {
    private String indicator;
    private boolean malicious;
    private double score;
    private String source;
    private String verdict;

    public ReputationResult(String indicator, boolean malicious, double score, String source) {
        this.indicator = indicator;
        this.malicious = malicious;
        this.score = score;
        this.source = source;
        this.verdict = malicious ? "MALICIOUS" : (score > 0.3 ? "SUSPICIOUS" : "SAFE");
    }

    public String getIndicator() { return indicator; }
    public boolean isMalicious() { return malicious; }
    public double getScore() { return score; }
    public String getSource() { return source; }
    public String getVerdict() { return verdict; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String indicator;
        private boolean malicious;
        private double score;
        private String source;

        public Builder indicator(String indicator) { 
            this.indicator = indicator; 
            return this; 
        }
        public Builder malicious(boolean malicious) { 
            this.malicious = malicious; 
            return this; 
        }
        public Builder score(double score) { 
            this.score = score; 
            return this; 
        }
        public Builder source(String source) { 
            this.source = source; 
            return this; 
        }
        public ReputationResult build() {
            return new ReputationResult(indicator, malicious, score, source);
        }
    }
}