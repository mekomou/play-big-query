package schedulers ;


import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobScheduler {
    private final ActorRef taskActor;
    private final ActorSystem actorSystem;
    private final ExecutionContext executionContext;

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    public JobScheduler(
            @Named("job-scheduler-actor") ActorRef taskActor,
            ActorSystem actorSystem,
            ExecutionContext executionContext) {
        this.taskActor = taskActor;
        this.actorSystem = actorSystem;
        this.executionContext = executionContext;

        this.initialize();
        log.info("akka scheduler has started...");
    }

    private void initialize() {
        long wait = TimeUnit.DAYS.toMillis(1) - System.currentTimeMillis();
        actorSystem
                .scheduler()
                .scheduleAtFixedRate(
                        Duration.create(wait, TimeUnit.MILLISECONDS),
                        Duration.create(1, TimeUnit.DAYS),
                        taskActor,
                        "hello",
                        executionContext,
                        ActorRef.noSender());
    }
}
