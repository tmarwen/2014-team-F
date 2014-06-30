package org.exoplatform.addons.codefest.fteam.service.util;


import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.model.TaskStatus;
import org.exoplatform.addons.codefest.fteam.model.TaskType;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.RootContainer;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by eXo Platform MEA on 6/26/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class TaskManagementUtils
{
  private static final Log LOG = ExoLogger.getLogger("org.exoplatform.addons.codefest.fteam.service.util.TaskManagementUtils");

  //Default Workspace for Task Management Application
  public static final String DEFAULT_WORKSPACE = "collaboration";

  //Task Nodetype
  public static final String EXO_TASK = "exo:task";

  //Date formatter
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);

  //Task Properties
  public static final String TASK_ID = "exo:taskId";
  public static final String TASK_TYPE = "exo:taskType";
  public static final String TASK_DUE_DATE = "exo:taskDueDate";
  public static final String TASK_START_DATE = "exo:taskStartDate";
  public static final String TASK_DESCRIPTION = "exo:taskDescription";
  public static final String TASK_OWNER = "exo:taskOwner";
  public static final String TASK_ASSIGNEE = "exo:taskAssignee";
  public static final String TASK_STATUS = "exo:taskStatus";

  public static <T> T getService(Class<T> clazz)
  {
    ExoContainer container = ExoContainerContext.getCurrentContainer();
    if (container.getComponentInstanceOfType(clazz) == null)
    {
      String containerName = PortalContainer.getCurrentPortalContainerName();
      container = RootContainer.getInstance().getPortalContainer(containerName);
    }
    return clazz.cast(container.getComponentInstanceOfType(clazz));
  }

  public static SessionProvider getSystemSessionProvider()
  {
    SessionProviderService sessionProviderService = getService(SessionProviderService.class);
    return sessionProviderService.getSystemSessionProvider(null);
  }

  public static SessionProvider getUserSessionProvider()
  {
    SessionProviderService sessionProviderService = getService(SessionProviderService.class);
    return sessionProviderService.getSessionProvider(null);
  }

  public static ManageableRepository getRepository()
  {
    try
    {
      RepositoryService repositoryService = getService(RepositoryService.class);
      return repositoryService.getCurrentRepository();
    } catch (Exception e)
    {
      LOG.error("Exception while retrieving repository instance: ", e);
    }
    return null;
  }

  public static Session getSystemSession() throws RepositoryException
  {
    return getSystemSessionProvider().getSession(DEFAULT_WORKSPACE, getRepository());
  }

  public static Node getTaskManagementRootNode()
  {
    NodeHierarchyCreator nodeHierarchyCreator = getService(NodeHierarchyCreator.class);
    String path = nodeHierarchyCreator.getJcrPath("taskManagementRootNode");
    Session session;
    try
    {
      session = getSystemSessionProvider().getSession(DEFAULT_WORKSPACE, getRepository());
      return (Node) session.getItem("/" + path);
    } catch (RepositoryException e)
    {
      LOG.error(String.format("Exception while retrieving root Task Management Node, root node for %s workspace will be used instead...",
              DEFAULT_WORKSPACE),
          e);
      return null;
    }
  }

  public static TaskBean nodeToBean(Node task, TaskBean taskBean)
  {
    try
    {
      taskBean.setId(task.getProperty(TASK_ID).getString());
      taskBean.setOwner(task.getProperty(TASK_OWNER).getString());
      taskBean.setDueDate(parse(task.getProperty(TASK_DUE_DATE).getString()));
      taskBean.setStartDate(parse(task.getProperty(TASK_START_DATE).getString()));
      taskBean.setType(TaskType.valueOf(task.getProperty(TASK_TYPE).getString().toUpperCase()));
      taskBean.setStatus(TaskStatus.valueOf(task.getProperty(TASK_STATUS).getString().toUpperCase()));
      taskBean.setDescription(task.getProperty(TASK_DESCRIPTION).getString());
      taskBean.setAssignee(task.getProperty(TASK_ASSIGNEE).getString());
    } catch (RepositoryException e)
    {
      LOG.error("An error occurred while mapping Task JCR Node to a Bean: ", e);
    }
    return taskBean;
  }

  public static Node beanToNode(TaskBean task, Node taskNode)
  {
    try
    {
      taskNode.setProperty(TASK_ID, task.getId());
      taskNode.setProperty(TASK_OWNER, task.getOwner());
      taskNode.setProperty(TASK_DUE_DATE, format(task.getDueDate()));
      taskNode.setProperty(TASK_START_DATE, format(task.getStartDate()));
      taskNode.setProperty(TASK_TYPE, task.getType().name());
      taskNode.setProperty(TASK_STATUS, task.getStatus().name());
      taskNode.setProperty(TASK_DESCRIPTION, task.getDescription());
      taskNode.setProperty(TASK_ASSIGNEE, task.getAssignee());
    } catch (RepositoryException e)
    {
      LOG.error("An error occurred while mapping Task Bean to a JCR Node: ", e);
    }
    return taskNode;
  }

  public static Date parse(String date)
  {
    try
    {
      return dateFormat.parse(date.replace("-", "/"));
    } catch (ParseException e)
    {
      LOG.error("Error while parsing date: ", e);
      return null;
    }
  }

  public static String format(Date date)
  {
    return dateFormat.format(date);
  }

  public static List<TaskBean> filterUserTasks(List<TaskBean> tasks, String userName)
  {
    List<TaskBean> userTasks = new ArrayList<TaskBean>();

    for (TaskBean task : tasks)
    {
      if (task.getOwner().equals(userName) && task.getType().equals(TaskType.PERSONAL))
      {
        userTasks.add(task);
      }
      else if (task.getAssignee().equals(userName) && task.getType().equals(TaskType.PROJECT))
      {
        userTasks.add(task);
      }
    }
    return userTasks;
  }
}
