package org.exoplatform.addons.codefest.fteam.service.util;


import org.exoplatform.addons.codefest.fteam.model.TaskBean;

/**
 * Created by marwen on 6/26/14.
 */
public class TaskManagementUtils
{
  private static final Log LOG = ExoLogger.getLogger("org.exoplatform.addons.codefest.fteam.service.util.TaskManagementUtils");

  //Default Workspace for Task Management Application
  public static final String DEFAULT_WORKSPACE = "collaboration";

  //Task Nodetype
  public static final String EXO_TASK = "exo:task";

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
      containerName = PortalContainer.getCurrentPortalContainerName();
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

  public static Session getSystemSession()
  {
    return getSystemSessionProvider().getSession(DEFAULT_WORKSPACE, getRepository());
  }

  public static Node getTaskManagementRootNode() throws RepositoryException
  {
    NodeHierarchyCreator nodeHierarchyCreator = getService(NodeHierarchyCreator.class);
    String path = nodeHierarchyCreator.getJcrPath("taskManagementRootNode");
    Session session = getSystemSessionProvider().getSession(DEFAULT_WORKSPACE, getRepository());
    return (Node) session.getItem(path);
  }

  public static TaskBean nodeToBean(Node task, TaskBean taskBean)
  {
    taskBean.setOwner(task.getProperty(TASK_OWNER));
    taskBean.setDueDate(task.getProperty(TASK_DUE_DATE));
    taskBean.setStartDate(task.getProperty(TASK_START_DATE));
    taskBean.setType(task.getProperty(TASK_TYPE));
    taskBean.setStatus(task.getProperty(TASK_STATUS));
    taskBean.setDescription(task.getProperty(TASK_DESCRIPTION));
    taskBean.setAssignee(task.getProperty(TASK_ASSIGNEE));
    return taskBean;
  }

  public static Node beanToNode(TaskBean task, Node taskNode)
  {
    taskNode.setProperty(TASK_OWNER, task.getOwner());
    taskNode.setProperty(TASK_DUE_DATE, task.getDueDate());
    taskNode.setProperty(TASK_START_DATE, task.getStartDate());
    taskNode.setProperty(TASK_TYPE, task.getType().name());
    taskNode.setProperty(TASK_STATUS, task.getStatus().name());
    taskNode.setProperty(TASK_DESCRIPTION, task.getDescription());
    taskNode.setProperty(TASK_ASSIGNEE, task.getAssignee());
    return taskNode;
  }
}
