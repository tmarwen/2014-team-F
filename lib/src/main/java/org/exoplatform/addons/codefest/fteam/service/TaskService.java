package org.exoplatform.addons.codefest.fteam.service;

import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;

import javax.jcr.ItemExistsException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import java.util.List;

/**
 * Created by eXo Platform MEA on 26/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public interface TaskService
{
  public TaskBean getTask(long taskId) throws RepositoryException;

  public void addTask(TaskBean task) throws RepositoryException;

  public void updateTask(TaskBean task) throws RepositoryException;

  public void removeTask(long id) throws RepositoryException;

  public List<TaskBean> getAllTasks() throws RepositoryException;

  java.util.Map<String, List<TaskBean>> listByFilter(TaskFilter filter) throws RepositoryException;
}
