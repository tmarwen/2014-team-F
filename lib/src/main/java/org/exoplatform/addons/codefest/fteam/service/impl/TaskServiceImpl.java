package org.exoplatform.addons.codefest.fteam.service.impl;

import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.model.TaskStatus;
import org.exoplatform.addons.codefest.fteam.model.TaskType;
import org.exoplatform.addons.codefest.fteam.service.TaskService;
import org.exoplatform.addons.codefest.fteam.service.util.TaskManagementUtils;

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

  private Node taskManagementRootNode = TaskManagementUtils.getTaskManagementRootNode();

  @Override
  public TaskBean getTask(long taskId) throws RepositoryException
  {
    Node task = taskManagementRootNode.getNode(String.valueOf(taskId));
    TaskBean taskBean = new TaskBean();
    return TaskManagementUtils.nodeToBean(task, taskBean);
  }

  @Override
  public void addTask(TaskBean task) throws RepositoryException
  {
    Node taskNode = taskManagementRootNode.addNode(String.valueOf(task.getId()),
        TaskManagementUtils.EXO_TASK);
    TaskManagementUtils.beanToNode(task, taskNode);
    taskManagementRootNode.save();
  }

  @Override
  public void updateTask(TaskBean task) throws RepositoryException
  {
    Node taskNode = taskManagementRootNode.getNode(String.valueOf(task.getId()));
    TaskManagementUtils.nodeToBean(taskNode, task);
    taskManagementRootNode.save();
  }

  @Override
  public void removeTask(long taskId) throws RepositoryException
  {
    taskManagementRootNode.getNode(String.valueOf(taskId)).remove();
    taskManagementRootNode.save();
  }

  @Override
  public List<TaskBean> getAllTasks() throws RepositoryException
  {
    List<TaskBean> tasks = new ArrayList<TaskBean>();
    for (NodeIterator nodeIterator = taskManagementRootNode.getNodes(); nodeIterator.hasNext();)
    {
      Node node = nodeIterator.nextNode();
      tasks.add(TaskManagementUtils.nodeToBean(node, new TaskBean()));
    }
    return tasks;
  }

  @Override
  public Map<String, List<TaskBean>> listByFilter(TaskFilter filter) throws RepositoryException
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
