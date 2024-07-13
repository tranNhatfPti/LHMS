/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TimeOfDay;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Timer {

    public static void main(String[] args) {
        try {
            // Khởi tạo Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // Bắt đầu Scheduler
            scheduler.start();

            // Định nghĩa công việc và liên kết với JobDetail
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                    .withIdentity("deleteContract", "group1") // Định danh cho công việc                  
                    .build();

            // Định nghĩa lịch trình (Trigger) cho công việc
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "ContractExpired") // Định danh cho Trigger
                    .startNow() // Bắt đầu lập lịch từ lúc này
                    .withSchedule(DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                            .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(0, 0)) // Chạy lúc 00:00 mỗi ngày
                            .withIntervalInHours(24) // Thực hiện lại sau mỗi 24 giờ
                            .onEveryDay())// Lặp lại mỗi ngày
                            .build();
                  

            // Lên lịch công việc với Trigger đã định nghĩaF
            scheduler.scheduleJob(jobDetail, trigger);

            //scheduler.shutdown(); // Dừng Scheduler nếu cần thiết
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        
        
    }
}
