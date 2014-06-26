package org.exoplatform.addons.codefest.fteam.service.impl;

import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.model.TaskStatus;
import org.exoplatform.addons.codefest.fteam.model.TaskType;
import org.exoplatform.addons.codefest.fteam.service.TaskService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eXo Platform MEA on 26/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class TaskMockServiceImpl implements TaskService
{
  private static List<TaskBean> mockTasks = new ArrayList<TaskBean>();

  static {
    if (mockTasks.isEmpty())
    {
      TaskBean task1 = new TaskBean(1, "marwen", new Date(), new Date(), TaskType.PERSONAL, TaskStatus.TODO, "Description goes here...");
      TaskBean task2 = new TaskBean(2, "ahmed", new Date(), new Date(), TaskType.PERSONAL, TaskStatus.TODO, "Description for task 2 goes here...");
      mockTasks.add(task1);
      mockTasks.add(task2);
    }
  }

  @Override
  public TaskBean getTask(int taskId)
  {
    return mockTasks.get(taskId);
  }

  @Override
  public boolean addTask(TaskBean task)
  {
    return mockTasks.add(task);
  }

  @Override
  public List<TaskBean> getAllTasks()
  {
    return mockTasks;
  }

  @Override
  public List<TaskBean> listByFilter(TaskFilter filter)
  {
    return null;
  }
}
