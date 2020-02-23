package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.character.PeriodicSoberUpCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.inventory.DropItemCmd;
import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.HealingBlast;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;

import com.github.dagwud.woodlands.game.domain.trinkets.*;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.trinkets.consumable.*;

public class PatchCharacterCmd extends AbstractCmd
{
  private final PlayerCharacter character;

  public PatchCharacterCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    Stat stat = character.getStats().getStrength();
    fixNegativeStats(stat, character);
  }

  void fixNegativeStats(Stat stat, String statName)
  {
    if (stat.getTotal() < 0)
    {
      stat.clearBonuses();
      CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "PATCH: negative " + statName + " has been undone for " + character.getName()));
      CommandDelegate.execute(new SendMessageCmd(character.getPlayedBy().getChatId(), "PATCH: negative " + statName + " has been undone"));
    }
  }

}
