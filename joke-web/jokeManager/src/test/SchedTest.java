//package test;
//
//import static org.quartz.JobBuilder.newJob;
//import static org.quartz.TriggerBuilder.newTrigger;
//import static org.quartz.CronScheduleBuilder.cronSchedule;
//
//import org.quartz.CronTrigger;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.SchedulerFactory;
//import org.quartz.impl.StdSchedulerFactory;
//
//public class SchedTest {
//	
//	public void test() throws SchedulerException{
//		SchedulerFactory sf = new StdSchedulerFactory();
//		Scheduler sched = sf.getScheduler();
//
//		JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1")
//				.build();
//		
//		JobDetail job1 = newJob(SimpleJob.class).withIdentity("job2", "group1")
//				.build();
//
//		CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1")
//				.withSchedule(cronSchedule("0/5 * * * * ?")).build();
//		
//		CronTrigger trigger2 = newTrigger().withIdentity("trigger2", "group1")
//				.withSchedule(cronSchedule("0/5 * * * * ?")).build();
//
//		sched.scheduleJob(job, trigger);
//		sched.scheduleJob(job1, trigger2);
//		sched.start();
//	}
//
//	public static void main(String[] args) throws SchedulerException {
//		SchedTest ss = new SchedTest();
//		ss.test();
//
//	}
//
//}
