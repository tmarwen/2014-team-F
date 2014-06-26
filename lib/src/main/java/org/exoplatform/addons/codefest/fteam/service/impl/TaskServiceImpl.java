package org.exoplatform.addons.codefest.fteam.service.impl;

import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.service.TaskService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eXo Platform MEA on 26/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class TaskServiceImpl implements TaskService
{
  private List<TaskBean> mockTasks = new ArrayList<TaskBean>();

  @Override
  public TaskBean getTask(int taskId)
  {
    return null;
  }

  @Override
  public int addTask(TaskBean task)
  {
    return 0;
  }

  @Override
  public List<TaskBean> listAll()
  {
    return null;
  }

  @Override
  public List<TaskBean> listByFilter(TaskFilter filter)
  {
    return null;
  }
}
