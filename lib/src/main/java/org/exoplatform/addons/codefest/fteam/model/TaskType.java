package org.exoplatform.addons.codefest.fteam.model;

/**
 * Created by marwen on 6/26/14.
 */
public enum TaskType
{
  PERSONAL(0),
  PROJECT(1);

  private int type;

  TaskType(int i)
  {
    type = i;
  }

  public boolean equalsType(int someType){
    return someType == type;
  }

  public String toString(){
    return name();
  }
}
