/*
 * Copyright 2013 eXo Platform SAS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.exoplatform.addons.codefest.fteam;

import juzu.Action;
import juzu.Path;
import juzu.Response;
import juzu.Route;
import juzu.View;
import org.exoplatform.addons.codefest.fteam.model.TaskBean;
import org.exoplatform.addons.codefest.fteam.service.TaskService;
import org.exoplatform.addons.codefest.fteam.service.mapper.TaskBeanMapper;

import javax.inject.Inject;
import java.io.IOException;

public class Controller
{

  @Inject
  @Path("index.gtmpl")
  org.exoplatform.addons.codefest.fteam.templates.index index;

  @Inject
  @Path("task.gtmpl")
  org.exoplatform.addons.codefest.fteam.templates.task task;

  @Inject
  TaskService taskService;

  @View
  public Response.Content index() throws IOException
  {
    return index.ok();
  }

  @View
  @Route("/task")
  public Response.Content task(String taskId)
  {
    TaskBean taskBean = taskService.getTask(taskId);
    return task.with().task(taskBean).ok();
  }

  @Action
  @Route("/addTask/{taskId}")
  public Response.View addTask(String taskId,
                               String taskDescription,
                               String taskDueDate,
                               String taskStartDate,
                               String taskType,
                               String taskAssignee,
                               String taskProject,
                               String taskTrigger)
  {
    TaskBean taskBean = TaskBeanMapper.mapStringifiedTask(taskId, taskDueDate, taskStartDate, taskType, taskDescription, taskAssignee, taskProject, taskTrigger);
    taskService.addTask(taskBean);
    return Controller_.task(taskBean.getId());
  }

}
