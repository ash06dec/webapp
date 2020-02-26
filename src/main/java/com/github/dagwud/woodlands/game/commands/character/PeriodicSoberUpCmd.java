package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.PeriodicCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.SoberUpCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class PeriodicSoberUpCmd extends PeriodicCmd<SoberUpCmd>
{
  private static final long serialVersionUID = 1L;
  
  private final int chatId;
  private final PlayerCharacter character;

  public PeriodicSoberUpCmd(PlayerCharacter character, int chatId)
  {
    super(Settings.SOBER_UP_DELAY_MS);
    this.character = character;
    this.chatId = chatId;
  }

  @Override
  protected boolean shouldRunSingle()
  {
    return character.isConscious();
  }

  @Override
  protected boolean shouldCancelPeriodicTimer()
  {
    return character.isDead() || !character.isActive();
  }

  @Override
  protected SoberUpCmd createSingleRunCmd()
  {
    return new SoberUpCmd(character, chatId);
  }

  @Override
  public String toString()
  {
    return "PeriodicSoberUpCmd[character=" + character.getName() + "]";
  }
}
