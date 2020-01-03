package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.game.commands.natives.NativeAction;
import com.github.dagwud.woodlands.gson.ParamMappings;

class NativeActionInvoker extends ActionInvoker
{
  private final NativeAction nativeAction;

  NativeActionInvoker(NativeAction action)
  {
    this.nativeAction = action;
  }

  @Override
  void verifyParameters(VariableStack parameters) throws ActionParameterException
  {
    nativeAction.verifyParameters(parameters);
  }

  @Override
  String getActionName()
  {
    return nativeAction.getClass().getSimpleName() + " (native)";
  }

  @Override
  Variables doInvoke(VariableStack context, ParamMappings outputMappings) throws ActionParameterException
  {
    nativeAction.verifyParameters(context);
    return nativeAction.invoke(context);
  }
}
