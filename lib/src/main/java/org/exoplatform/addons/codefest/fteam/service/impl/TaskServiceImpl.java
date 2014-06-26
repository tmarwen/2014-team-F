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
    return null;
  }

  @Override
  public boolean addTask(TaskBean task)
  {
    Node project = taskManagementRootNode.addNode(task.getId(), TaskManagementUtils.EXO_TASK);
    project.setProperty(TaskManagementUtils.TASK_OWNER, task.getOwner());
    project.setProperty(TaskManagementUtils.TASK_DUE_DATE, task.getDueDate());
    project.setProperty(TaskManagementUtils.TASK_START_DATE, task.getStartDate());
    project.setProperty(TaskManagementUtils.TASK_TYPE, task.getType().name());
    project.setProperty(TaskManagementUtils.TASK_STATUS, task.getStatus().name());
    project.setProperty(TaskManagementUtils.TASK_DESCRIPTION, task.getDescription());
    project.setProperty(TaskManagementUtils.TASK_ASSIGNEE, task.getAssignee());
    taskManagementRootNode.save();
  }

  @Override
  public boolean updateTask(TaskBean task)
  {
    Node project = taskManagementRootNode.getNode(task.getId());
    project.setProperty(TaskManagementUtils.TASK_OWNER, task.getOwner());
    project.setProperty(TaskManagementUtils.TASK_DUE_DATE, task.getDueDate());
    project.setProperty(TaskManagementUtils.TASK_START_DATE, task.getStartDate());
    project.setProperty(TaskManagementUtils.TASK_TYPE, task.getType().name());
    project.setProperty(TaskManagementUtils.TASK_STATUS, task.getStatus().name());
    project.setProperty(TaskManagementUtils.TASK_DESCRIPTION, task.getDescription());
    project.setProperty(TaskManagementUtils.TASK_ASSIGNEE, task.getAssignee());
    taskManagementRootNode.save();
  }

  @Override
  public boo

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
