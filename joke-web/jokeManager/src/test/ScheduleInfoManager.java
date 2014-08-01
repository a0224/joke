//package test;
//
//import org.quartz.Scheduler;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Repository;
//
//import freemarker.core.Environment;
//
///**
// * 
// * 
// * Description: 计时器工具类
// * 
// * <pre>
// * Modification History:
// * Date         Author      Version     Description
// * ------------------------------------------------------------------
// * 2013-7-19    lanzhongliang   1.0      1.0 Version
// * </pre>
// */
//@Repository("scheduleInfoManager")
//@Scope("prototype")
//public class ScheduleInfoManager {
//	private static Scheduler scheduler;// 调度器
//	static {
//		scheduler = (Scheduler) Environment.getInstance().getBean(
//				"schedulerFactoryBean");
//	}
//
//	/**
//	 * 
//	 * 
//	 * Description: 启动一个自定义的job
//	 * 
//	 * @param schedulingJob
//	 *            自定义的job
//	 * @param paramsMap
//	 *            传递给job执行的数据
//	 * @param isStateFull
//	 *            是否是一个同步定时任务，true：同步，false：异步
//	 * @return 成功则返回true，否则返回false
//	 * @Author lanzhongliang
//	 * @Create 2013-7-19 下午03:57:22
//	 */
//	public static boolean enableCronSchedule(ScheduleJobEntity schedulingJob,
//			JobDataMap paramsMap, boolean isStateFull) {
//		if (schedulingJob == null) {
//			return false;
//		}
//		try {
//			CronTrigger trigger = (CronTrigger) scheduler
//					.getTrigger(schedulingJob.getTriggerName(),
//							schedulingJob.getJobGroup());
//			if (null == trigger) {// 如果不存在该trigger则创建一个
//				JobDetail jobDetail = null;
//				if (isStateFull) {
//					jobDetail = new JobDetail(schedulingJob.getJobId(),
//							schedulingJob.getJobGroup(),
//							schedulingJob.getStateFulljobExecuteClass());
//				} else {
//					jobDetail = new JobDetail(schedulingJob.getJobId(),
//							schedulingJob.getJobGroup(),
//							schedulingJob.getJobExecuteClass());
//				}
//				jobDetail.setJobDataMap(paramsMap);
//				trigger = new CronTrigger(schedulingJob.getTriggerName(),
//						schedulingJob.getJobGroup(),
//						schedulingJob.getCronExpression());
//				scheduler.scheduleJob(jobDetail, trigger);
//			} else {// Trigger已存在，那么更新相应的定时设置
//				trigger.setCronExpression(schedulingJob.getCronExpression());
//				scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(),
//						trigger);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * 
//	 * 
//	 * Description: 禁用一个job
//	 * 
//	 * @param jobId
//	 *            需要被禁用的job的ID
//	 * @param jobGroupId
//	 *            需要被禁用的jobGroupId
//	 * @return 成功则返回true，否则返回false
//	 * @Author lanzhongliang
//	 * @Create 2013-7-19 下午04:03:24
//	 */
//	public static boolean disableSchedule(String jobId, String jobGroupId) {
//		if (StringUtil.isBlank(jobId) || StringUtil.isBlank(jobGroupId)) {
//			return false;
//		}
//		try {
//			Trigger trigger = getJobTrigger(jobId, jobGroupId);
//			if (null != trigger) {
//				scheduler.deleteJob(jobId, jobGroupId);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * 
//	 * Description: 得到job的详细信息
//	 * 
//	 * @param jobId
//	 *            job的ID
//	 * @param jobGroupId
//	 *            job的组ID
//	 * @return job的详细信息,如果job不存在则返回null
//	 * @Author lanzhongliang
//	 * @Create 2013-7-19 下午04:07:08
//	 */
//	public static JobDetail getJobDetail(String jobId, String jobGroupId) {
//		if (StringUtil.isBlank(jobId) || StringUtil.isBlank(jobGroupId)) {
//			return null;
//		}
//		try {
//			return scheduler.getJobDetail(jobId, jobGroupId);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 * 
//	 * 
//	 * Description: 得到job对应的Trigger
//	 * 
//	 * @param jobId
//	 *            job的ID
//	 * @param jobGroupId
//	 *            job的组ID
//	 * @return job的Trigger,如果Trigger不存在则返回null
//	 * @Author lanzhongliang
//	 * @Create 2013-7-19 下午04:09:00
//	 */
//	public static Trigger getJobTrigger(String jobId, String jobGroupId) {
//		if (jobId.equals("") || jobGroupId.equals("") || null == jobId
//				|| jobGroupId == null) {
//			return null;
//		}
//		try {
//			return scheduler.getTrigger(jobId + "Trigger", jobGroupId);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//}