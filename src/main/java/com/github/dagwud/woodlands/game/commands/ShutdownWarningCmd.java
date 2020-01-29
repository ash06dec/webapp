package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.ArrayList;
import java.util.Collection;

public class ShutdownWarningCmd extends AbstractCmd
{
  @Override
  public void execute()
  {
    Collection<PlayerCharacter> toRetreat = new ArrayList<>();
    for (PlayerState playerState : GameStatesRegistry.allPlayerStates())
    {
      PlayerCharacter character = playerState.getPlayer().getActiveCharacter();
      if (shouldRetreat(character))
      {
        toRetreat.add(character);
      }
    }

    for (PlayerCharacter character : toRetreat)
    {
      try
      {
        MoveToLocationCmd move = new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE);
        CommandDelegate.execute(move);
      }
      catch (Exception e)
      {
        // Do NOT let this stop us retreating characters
      }
    }

    for (PlayerCharacter character : toRetreat)
    {
      SendMessageCmd msg = new SendMessageCmd(character.getPlayedBy().getChatId(), "You can feel changes coming in the breeze. Best you head back to the Village to take shelter");
      CommandDelegate.execute(msg);
    }
  }

  private boolean shouldRetreat(PlayerCharacter character)
  {
    if (character.getStats().getState() == EState.ALIVE)
    {
      if (character.getLocation() != ELocation.VILLAGE_SQUARE)
      {
        return true;
      }
    }
    return false;
  }
}
