package services;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;

@ImplementedBy(BigQueryJobImpl.class)
public interface IBigQueryJob {
    CompletionStage<Long> runJob();
}
