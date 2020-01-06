package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class SetVarAction extends NativeAction
{
  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails)
  {
    String varSet = gameState.getVariables().lookupVariableValue("VarSet");
    String varName = "__" + varSet + "." + gameState.getVariables().lookupVariableValue("VarName");
    String varValue = gameState.getVariables().lookupVariableValue("VarValue");
    Variables result = new Variables("setvarparams", new HashMap<>());
    result.put(varName, varValue);
    return new InvocationResults(result);
  }
}
