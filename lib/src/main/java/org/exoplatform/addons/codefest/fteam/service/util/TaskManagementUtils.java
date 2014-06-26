package org.exoplatform.addons.codefest.fteam.service.util;


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
}
