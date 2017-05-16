/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.tasks;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.BaseView;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.BasePresenter;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 * 指定view和presenter之间的协议
 * contract 协议、合同
 */
public interface TasksContract {

    /**
     * 面向用户---由Fragment继承
     * TasksFragment实现此接口。接口方法对应"界面功能"
     */
    interface View extends BaseView<Presenter> {//这里的Presenter已经确定数据操作接口

        void setLoadingIndicator(boolean active);//indicator指示剂

        void showTasks(List<Task> tasks);

        void showAddTask();

        void showTaskDetailsUi(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showCompletedTasksCleared();

        void showLoadingTasksError();

        void showNoTasks();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();
    }

    /**
     * 面向数据
     * TasksPresenter实现此方法，接口方法对应"功能实现"
     */
    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        //加载任务：是否强制更新
        void loadTasks(boolean forceUpdate);

        //添加新任务
        void addNewTask();

        //打开任务详情
        void openTaskDetails(@NonNull Task requestedTask);

        //完成任务
        void completeTask(@NonNull Task completedTask);

        //激活任务
        void activateTask(@NonNull Task activeTask);

        //清除已完成任务
        void clearCompletedTasks();

        //设置过滤器：全部任务||已激活任务||已完成任务
        void setFiltering(TasksFilterType requestType);

        //获取当前过滤类型
        TasksFilterType getFiltering();
    }
}
