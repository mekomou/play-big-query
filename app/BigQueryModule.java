
import com.google.inject.AbstractModule;
import play.libs.pekko.PekkoGuiceSupport;
import schedulers.JobScheduler;
import schedulers.JobSchedulerActor;


public class BigQueryModule extends AbstractModule implements PekkoGuiceSupport {

    @Override
    protected void configure() {
        this.bindActor(JobSchedulerActor.class, "job-scheduler-actor");
        this.bind(JobScheduler.class).asEagerSingleton();
    }
}
