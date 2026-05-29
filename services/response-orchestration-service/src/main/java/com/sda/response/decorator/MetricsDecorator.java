package com.sda.response.decorator;

import com.sda.response.domain.ActionOutcome;
import com.sda.response.domain.ResponseContext;
import com.sda.response.executor.ResponseAction;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

// PATTERN: Decorator (Concrete Decorator 3)
// RATIONALE: MetricsDecorator collects performance metrics about action execution.
//            Tracks success rates, response times, and action frequencies.
public class MetricsDecorator extends ResponseActionDecorator {
    
    // Metrics storage
    private static Map<String, MetricData> metrics = new ConcurrentHashMap<>();
    
    private static class MetricData {
        long count;
        long successCount;
        long totalDuration;
        
        void record(boolean success, long duration) {
            count++;
            if (success) successCount++;
            totalDuration += duration;
        }
        
        double getSuccessRate() {
            return count == 0 ? 0 : (successCount * 100.0) / count;
        }
        
        double getAverageDuration() {
            return count == 0 ? 0 : totalDuration / count;
        }
    }
    
    public MetricsDecorator(ResponseAction wrappedAction) {
        super(wrappedAction);
    }
    
    @Override
    public ActionOutcome execute(ResponseContext context) {
        String actionName = getType().name();
        long startTime = System.currentTimeMillis();
        
        System.out.println("  📊 [METRICS] Starting metric collection for: " + actionName);
        
        // Execute the wrapped action
        ActionOutcome outcome = wrappedAction.execute(context);
        
        long duration = System.currentTimeMillis() - startTime;
        
        // Record metrics
        MetricData data = metrics.computeIfAbsent(actionName, k -> new MetricData());
        data.record(outcome.isSuccess(), duration);
        
        System.out.println("  📊 [METRICS] " + actionName + " - Duration: " + duration + "ms");
        System.out.println("  📊 [METRICS] Success Rate: " + String.format("%.1f", data.getSuccessRate()) + "%");
        System.out.println("  📊 [METRICS] Avg Duration: " + String.format("%.1f", data.getAverageDuration()) + "ms");
        System.out.println("  📊 [METRICS] Total Executions: " + data.count);
        
        return outcome;
    }
    
    // Get metrics report
    public static void printMetricsReport() {
        System.out.println("\n========== METRICS REPORT ==========");
        for (Map.Entry<String, MetricData> entry : metrics.entrySet()) {
            MetricData data = entry.getValue();
            System.out.printf("  %-20s | Count: %-5d | Success: %4.1f%% | Avg: %6.1fms\n",
                entry.getKey(), data.count, data.getSuccessRate(), data.getAverageDuration());
        }
        System.out.println("====================================\n");
    }
}