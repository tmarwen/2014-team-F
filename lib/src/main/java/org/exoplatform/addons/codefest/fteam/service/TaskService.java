package org.exoplatform.addons.codefest.fteam.service;

import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;

import java.util.List;

/**
 * Created by eXo Platform MEA on 26/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public interface TaskService
{
  public TaskBean getTask(int taskId);

  public int addTask(TaskBean task);

  public List<TaskBean> listAll();

  List<TaskBean> listByFilter(TaskFilter filter);
}
