package org.exoplatform.addons.codefest.fteam.model;

/**
 * Created by marwen on 6/26/14.
 */
public enum TaskStatus
{
  TODO("todo"),
  DONE("done"),
  INPROGRESS("inprogress");

  private final String status;

  TaskStatus(String status)
  {
    this.status = status;
  }

  public boolean equalsStatus(String someStatus){
    return (someStatus == null) ? false : status.equals(someStatus);
  }

  public String toString(){
    return status;
  }
}
