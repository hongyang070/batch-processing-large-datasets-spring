package com.techshard.batch.task;

import com.techshard.batch.configuration.NotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: YuHongYang
 * @Date: 2022-06-24 10:52
 */
@Component
public class JobExec {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    private NotificationListener listener;
    @Autowired
    @Qualifier("stepTable1")
    private Step stepTable1;

    @Scheduled(fixedRate = 1000*10)
    public void importVoltageJob() throws Exception{
       Job job= jobBuilderFactory.get("importVoltageJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(stepTable1)
                .end()
                .build();
// 启动任务
        jobLauncher.run(job, new JobParameters());
    }

}
