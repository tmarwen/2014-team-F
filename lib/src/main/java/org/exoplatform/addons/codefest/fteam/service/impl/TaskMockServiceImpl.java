package org.exoplatform.addons.codefest.fteam.service.impl;

import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.model.TaskStatus;
import org.exoplatform.addons.codefest.fteam.model.TaskType;
import org.exoplatform.addons.codefest.fteam.service.TaskService;

import javax.jcr.RepositoryException;
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
      TaskBean task1 = new TaskBean("fistTask", "marwen", new Date(), new Date(), TaskType.PERSONAL, TaskStatus.TODO, "Description goes here...", "marwen");
      TaskBean task2 = new TaskBean("secondTask", "ahmed", new Date(), new Date(), TaskType.PERSONAL, TaskStatus.TODO, "Description for task 2 goes here...", "space");
      mockTasks.add(task1);
      mockTasks.add(task2);
    }
  }

  @Override
  public TaskBean getTask(String taskId)
  {
    return mockTasks.get(Integer.parseInt(taskId));
  }

  @Override
  public void addTask(TaskBean task)
  {
    int taskId = mockTasks.size();
    task.setId(String.valueOf(taskId));
    mockTasks.add(task);
  }

  @Override
  public void updateTask(String id, TaskBean task)
  {

  }

  @Override
  public void removeTask(String id)
  {

  }

  @Override
  public List<TaskBean> getAllTasks()
  {
    return mockTasks;
  }

  @Override
  public java.util.Map<String, List<TaskBean>> listByFilter(TaskFilter filter)
  {
    return null;
  }
}
