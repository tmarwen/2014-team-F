package org.exoplatform.addons.codefest.fteam.service.impl;

import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.model.TaskStatus;
import org.exoplatform.addons.codefest.fteam.model.TaskType;
import org.exoplatform.addons.codefest.fteam.service.TaskService;
import org.exoplatform.addons.codefest.fteam.service.util.TaskManagementUtils;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marwen on 6/26/14.
 */
public class TaskServiceImpl implements TaskService
{
  private static final Log LOG = ExoLogger.getLogger("org.exoplatform.addons.codefest.fteam.service.impl.TaskServiceImpl");

  @Override
  public TaskBean getTask(String taskId)
  {
    Node task;
    TaskBean taskBean = new TaskBean();
    try
    {
      Node taskManagementRootNode = TaskManagementUtils.getTaskManagementRootNode();
      task = taskManagementRootNode.getNode(taskId);
      taskBean = TaskManagementUtils.nodeToBean(task, taskBean);
      taskManagementRootNode.getSession().logout();
    } catch (RepositoryException e)
    {
      LOG.error("Error while retrieving task: ", e);
    }
    return taskBean;
  }

  @Override
  public void addTask(TaskBean task)
  {
    Node taskNode;
    try
    {
      Node taskManagementRootNode = TaskManagementUtils.getTaskManagementRootNode();
      taskNode = taskManagementRootNode.addNode(task.getId(), TaskManagementUtils.EXO_TASK);
      TaskManagementUtils.beanToNode(task, taskNode);
      taskManagementRootNode.save();
      taskManagementRootNode.getSession().logout();
    } catch (RepositoryException e)
    {
      LOG.error("Error while adding task: ", e);
    }
  }

  @Override
  public void updateTask(String id, TaskBean task)
  {
    Node taskNode;
    try
    {
      Node taskManagementRootNode = TaskManagementUtils.getTaskManagementRootNode();
      taskNode = taskManagementRootNode.getNode(id);
      TaskManagementUtils.nodeToBean(taskNode, task);
      taskManagementRootNode.save();
      taskManagementRootNode.getSession().logout();
    } catch (RepositoryException e)
    {
      LOG.error("Error while updating task: ", e);
    }
  }

  @Override
  public void removeTask(String taskId)
  {
    try
    {
      Node taskManagementRootNode = TaskManagementUtils.getTaskManagementRootNode();
      taskManagementRootNode.getNode(taskId).remove();
      taskManagementRootNode.save();
      taskManagementRootNode.getSession().logout();
    } catch (RepositoryException e)
    {
      LOG.error("Error while removing task: ", e);
    }
  }

  @Override
  public List<TaskBean> getAllTasks()
  {
    List<TaskBean> tasks = new ArrayList<TaskBean>();
    Node taskManagementRootNode = TaskManagementUtils.getTaskManagementRootNode();
    try
    {
      for (NodeIterator nodeIterator = taskManagementRootNode.getNodes(); nodeIterator.hasNext();)
      {
        Node node = nodeIterator.nextNode();
        tasks.add(TaskManagementUtils.nodeToBean(node, new TaskBean()));
      }
    } catch (RepositoryException e)
    {
      LOG.error("Error while loading all tasks: ", e);
    }
    finally
    {
      try
      {
        taskManagementRootNode.getSession().logout();
      } catch (RepositoryException e)
      {
        LOG.error("Error while closing session: ", e);
      }
    }
    return tasks;
  }

  @Override
  public Map<String, List<TaskBean>> listByFilter(TaskFilter filter)
  {
    Map<String, List<TaskBean>> tasks = new HashMap<String, List<TaskBean>>();
    List<TaskBean> allTasks = getAllTasks();

    List<TaskStatus> statusesToFilter = filter.getStatuses();
    List<TaskType> typesToFilter = filter.getTypes();

    if (!statusesToFilter.isEmpty())
    {
      for (TaskStatus status : statusesToFilter)
      {
        List<TaskBean> tasksFilter = new ArrayList<TaskBean>();
        for (TaskBean task : allTasks)
        {
          if (task.getStatus().equals(status))
            tasksFilter.add(task);
        }
        tasks.put(status.name(), tasksFilter);
      }
    }

    if (!typesToFilter.isEmpty())
    {
      for (TaskType type : typesToFilter)
      {
        List<TaskBean> tasksFilter = new ArrayList<TaskBean>();
        for (TaskBean task : allTasks)
        {
          if (task.getType().equals(type))
            tasksFilter.add(task);
        }
        tasks.put(type.name(), tasksFilter);
      }
    }

    return tasks;
  }
}
