package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;

public class StartCmd extends AbstractCmd
{
  private final int chatId;
  private final GameState gameState;

  StartCmd(GameState gameState, int chatId)
  {
    this.gameState = gameState;
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    gameState.setPlayer(new Player(chatId));
    gameState.getPlayer().setActiveCharacter(new GameCharacter());
    gameState.getPlayer().getActiveCharacter().setSetupComplete(false);
  }
}
