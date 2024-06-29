package services;

import akka.actor.ActorSystem;
import akka.http.javadsl.model.HttpEntity;
import akka.http.javadsl.unmarshalling.Unmarshaller;
import akka.stream.alpakka.googlecloud.bigquery.javadsl.BigQuery;
import akka.stream.alpakka.googlecloud.bigquery.javadsl.jackson.BigQueryMarshallers;
import akka.stream.alpakka.googlecloud.bigquery.model.QueryResponse;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.util.concurrent.CompletionStage;

public class BigQueryJobImpl implements IBigQueryJob {

    @Override
    public CompletionStage<Long> runJob(){
        final ActorSystem system = ActorSystem.create("BigQuerySystem");
        String sqlQuery ="CREATE OR REPLACE TABLE covid_data.filtered_table AS\n" +
                        "SELECT \n" +
                        "date, country_name, subregion1_name, subregion2_name,\n" +
                        "new_confirmed, new_deceased,\n" +
                        "new_recovered, new_tested, cumulative_confirmed,\n" +
                        "cumulative_deceased, cumulative_recovered,cumulative_tested\n" +
                        "FROM `bigquery-public-data.covid19_open_data.covid19_open_data`\n" +
                        "WHERE RAND() > 0.5;";

        Unmarshaller<HttpEntity, QueryResponse<Object>> queryResponseUnmarshaller =
                BigQueryMarshallers.queryResponseUnmarshaller(Object.class);

        Source<Object, CompletionStage<QueryResponse<Object>>> centenariansDryRun = BigQuery.query(sqlQuery, false, false,queryResponseUnmarshaller);
        return centenariansDryRun
                        .to(Sink.ignore())
                        .run(system)
                        .thenApply(r -> r.getTotalBytesProcessed().getAsLong());
    }
}

