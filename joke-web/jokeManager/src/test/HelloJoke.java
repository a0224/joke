package test;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 一个简单的quartz调用job
 * @author 123
 *
 */
public class HelloJoke implements Job {

    private static Logger _log = LoggerFactory.getLogger(HelloJoke.class);

    public HelloJoke() {
    }

    public void execute(JobExecutionContext context)
        throws JobExecutionException {
        _log.info("Hello World! - " + new Date());
    }

}