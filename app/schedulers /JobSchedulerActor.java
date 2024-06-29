package schedulers ;

import org.apache.pekko.actor.UntypedAbstractActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.IBigQueryJob;

import javax.inject.Inject;

public class JobSchedulerActor extends UntypedAbstractActor {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    IBigQueryJob bigQueryJob;

    @Override
    public void onReceive(final Object message) {
        bigQueryJob.runJob()
                .whenComplete((input, exception) -> {
                    if (exception != null) {
                        log.error("Error when running job", exception);
                    } else {
                        log.info("Job was executed successfully: {} bytes was processed",input);
                    }
                });

    }
}