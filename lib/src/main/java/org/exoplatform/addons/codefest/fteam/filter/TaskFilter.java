package org.exoplatform.addons.codefest.fteam.filter;

import org.exoplatform.addons.codefest.fteam.model.TaskStatus;
import org.exoplatform.addons.codefest.fteam.model.TaskType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eXo Platform MEA on 26/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class TaskFilter
{

  private List<TaskType> types;
  private List<TaskStatus> statuses;

  public TaskFilter(TaskStatus status)
  {
    statuses = new ArrayList<TaskStatus>();
    statuses.add(status);
  }

  public TaskFilter(TaskType type)
  {
    types = new ArrayList<TaskType>();
    types.add(type);
  }

  public TaskFilter(List<TaskType> types, List<TaskStatus> statuses)
  {
    this.types = types;
    this.statuses = statuses;
  }

  public List<TaskType> getTypes()
  {
    return types;
  }

  public List<TaskStatus> getStatuses()
  {
    return statuses;
  }
}
