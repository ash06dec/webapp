package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.gson.game.Creature;

public abstract class GenerateAutomaticEncounterCmd extends GenerateEncounterCmd
{
  private static final long serialVersionUID = 1L;

  public GenerateAutomaticEncounterCmd(PlayerState playerState, ELocation location, double minDifficulty, double maxDifficulty, String creatureType)
  {
    super(playerState, location, minDifficulty, maxDifficulty, creatureType);
  }

  @Override
  protected boolean shouldHaveEncounter()
  {
    ChanceCalculatorCmd chance = new ChanceCalculatorCmd(getPlayerState().getActiveCharacter().getParty().getPercentChanceOfEncounter());
    CommandDelegate.execute(chance);
    return chance.getResult();
  }

  @Override
  Encounter createEncounter(Party party, Creature enemy)
  {
    return new Encounter(party, enemy);
  }

  @Override
  protected void scheduleFirstRound(Encounter encounter)
  {
    EncounterRoundCmd cmd = new AutomaticEncounterRoundCmd(getPlayerState(), Settings.DELAY_BETWEEN_ROUNDS_MS);
    CommandDelegate.execute(cmd);
  }
}
