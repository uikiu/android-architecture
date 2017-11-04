package com.android001.service.jobScheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
* class design：
* @author android001
* created at 2017/10/25 上午11:22
*/

public class MyJobSchedulerService extends JobService{

    /**
     *
     * @param params
     * @return false已经没有可要执行的任务，true有需要处理的工作（在单独线程上）
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private void onJobFinish(){
        jobFinished();
    }
}
