package org.exoplatform.addons.codefest.fteam.service.impl;

import org.exoplatform.addons.codefest.fteam.filter.TaskFilter;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.service.TaskService;
import org.exoplatform.addons.codefest.fteam.service.util.TaskManagementUtils;

import java.util.List;

/**
 * Created by marwen on 6/26/14.
 */
public class TaskServiceImpl implements TaskService
{

  private Node taskManagementRootNode = TaskManagementUtils.getTaskManagementRootNode();

  @Override
  public TaskBean getTask(int taskId)
  {
    Node task = taskManagementRootNode.getNode(taskId);
    TaskBean taskBean = new TaskBean();
    return TaskManagementUtils.nodeToBean(task, taskBean);
  }

  @Override
  public void addTask(TaskBean task)
  {
    Node taskNode = taskManagementRootNode.addNode(task.getId(), TaskManagementUtils.EXO_TASK);
    TaskManagementUtils.beanToNode(task, task);
    taskManagementRootNode.save();
  }

  @Override
  public void updateTask(TaskBean task)
  {
    Node taskNode = taskManagementRootNode.getNode(task.getId());
    TaskManagementUtils.nodeToBean(taskNode, task);
    taskManagementRootNode.save();
  }

  @Override
  public void removeTask(int id)
  {
    taskManagementRootNode.getNode(id).remove();
    taskManagementRootNode.save();
  }

  @Override
  public List<TaskBean> getAllTasks()
  {
    return null;
  }

  @Override
  public List<TaskBean> listByFilter(TaskFilter filter)
  {
    return null;
  }
}
