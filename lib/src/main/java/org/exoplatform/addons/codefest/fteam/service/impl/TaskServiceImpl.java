package org.exoplatform.addons.codefest.fteam.service.impl;

import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.service.TaskService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eXo Platform MEA on 26/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class TaskServiceImpl implements TaskService
{
  private static List<TaskBean> mockTasks = new ArrayList<TaskBean>();

  static {
    if (mockTasks.isEmpty())
    {
      TaskBean task1 = new TaskBean(1, "marwen", new Date(), "Description goes here...", 1);
      TaskBean task2 = new TaskBean(2, "ahmed", new Date(), "Description for task 2 goes here...", 3);
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
