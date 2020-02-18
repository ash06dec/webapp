package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.*;
import com.github.dagwud.woodlands.game.commands.character.CastSpellPromptCmd;
import com.github.dagwud.woodlands.game.commands.core.*;
import com.github.dagwud.woodlands.game.commands.inventory.DropItemCmd;
import com.github.dagwud.woodlands.game.commands.inventory.EquipItemCmd;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;
import com.github.dagwud.woodlands.game.domain.menu.GameMenu;
import com.github.dagwud.woodlands.game.domain.spells.SpellAbilities;
import com.github.dagwud.woodlands.gson.telegram.Update;

public class CommandFactory
{
  private static CommandFactory instance;

  public static CommandFactory instance()
  {
    if (null == instance)
    {
      createInstance();
    }
    return instance;
  }

  private synchronized static void createInstance()
  {
    if (instance != null)
    {
      return;
    }
    instance = new CommandFactory();
  }

  public AbstractCmd create(Update telegramUpdate, PlayerState playerState)
  {
    int chatId = telegramUpdate.message.chat.id;
    String cmd = telegramUpdate.message.text;
    if (cmd == null)
    {
      return new SendMessageCmd(chatId, "(・_・ヾ");
    }

    SuspendableCmd waiting = playerState.getWaitingForInputCmd();
    if (waiting != null)
    {
      return new AcceptInputCmd(waiting, cmd);
    }

    if (playerState.getActiveCharacter() != null)
    {
      // check if the player is trying to use a spell
      SpellAbilities spellAbilities = playerState.getActiveCharacter().getSpellAbilities();
      for (SingleCastSpell singleCastSpell : spellAbilities.getKnownActiveSpell())
      {
        if (cmd.equals(singleCastSpell.getSpellName()) && isValidMenuOption(cmd, playerState.getCurrentMenu(), playerState))
        {
          return new CastSpellPromptCmd(chatId, playerState.getActiveCharacter(), singleCastSpell);
        }
      }
    }

    ECommand by = ECommand.by(cmd);

    if (by != null && (!by.isMenuCmd() || isValidMenuOption(cmd, playerState.getCurrentMenu(), playerState)))
    {
      return by.build(playerState.getActiveCharacter(), chatId);
    }

    if (cmd.matches("/[Dd][LRW0-9]+"))
    {
      // Drop:
      String dropIndex = cmd.substring("/d".length());
      return new DropItemCmd(playerState.getActiveCharacter(), chatId, dropIndex);
    }
    if (cmd.matches("/[Ee][0-9]+"))
    {
      // Equip:
      String dropIndex = cmd.substring("/e".length());
      return new EquipItemCmd(playerState.getActiveCharacter(), chatId, dropIndex, true);
    }

    if (playerState.getActiveCharacter() != null && playerState.getActiveCharacter().getParty() != null)
    {
      return new SpeakCmd(playerState.getActiveCharacter(), cmd);
    }

    return new SendMessageCmd(chatId, "I'm not sure what you mean... perhaps try /help?");
  }

  private boolean isValidMenuOption(String cmd, GameMenu currentMenu, PlayerState playerState)
  {
    return currentMenu != null && currentMenu.containsOption(cmd, playerState);
  }

}
