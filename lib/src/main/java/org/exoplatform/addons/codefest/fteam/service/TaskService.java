package org.exoplatform.addons.codefest.fteam.service;

import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;

import javax.inject.Named;
import javax.jcr.ItemExistsException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import java.util.List;
import java.util.Map;

/**
 * Created by eXo Platform MEA on 26/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
@Named("TaskService")
public interface TaskService
{
  public TaskBean getTask(String taskId);

  public void addTask(TaskBean task);

  public void updateTask(String id, TaskBean task);

  public void removeTask(String id);

  public List<TaskBean> getAllTasks();

  public Map<String, List<TaskBean>> listByFilter(TaskFilter filter);

  public List<TaskBean> getTasksByStatus(String status);

  public List<TaskBean> getTasksByType(String type);

  public List<TaskBean> getProjectTasks(String projectName);

}
