package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.gson.telegram.Update;

public class GameInstructionFactory
{
  private static GameInstructionFactory instance;

  public static GameInstructionFactory instance()
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
    instance = new GameInstructionFactory();
  }

  public AbstractCmd create(Update telegramUpdate, GameState gameState)
  {
    String cmd = telegramUpdate.message.text;

    SuspendableCmd waiting = gameState.getWaitingForInputCmd();
    if (waiting != null)
    {
      return new AcceptInputCmd(waiting, cmd);
    }

    if (cmd.equals("/start"))
    {
      return new StartCmd(gameState, telegramUpdate.message.chat.id);
    }
    if (cmd.equals("/new"))
    {
      return new PlayerSetupCmd(gameState);
    }

    int chatId = gameState.getPlayer().getChatId();
    GameCharacter activeCharacter = gameState.getActiveCharacter();
    if (cmd.equals("The Inn"))
    {
      return new MoveToLocationCmd(chatId, activeCharacter, ELocation.INN);
    }
    if (cmd.equals("The Tavern"))
    {
      return new MoveToLocationCmd(chatId, activeCharacter, ELocation.TAVERN);
    }
    if (cmd.equals("The Village") || cmd.equals("Village Square"))
    {
      return new MoveToLocationCmd(chatId, activeCharacter, ELocation.VILLAGE_SQUARE);
    }
    if (cmd.equals("Retrieve Items"))
    {
      return new RetrieveItemsCmd(chatId, activeCharacter);
    }
    if (cmd.equals("/me"))
    {
      return new ShowCharacterInfoCmd(chatId, activeCharacter);
    }
    if (cmd.equals("Buy Drinks"))
    {
      return new BuyDrinksCmd(chatId, activeCharacter);
    }
    if (cmd.equals("Short Rest"))
    {
      return new ShortRestCmd(chatId, activeCharacter);
    }

    return new SendMessageCmd(chatId, "I'm not sure what you mean... perhaps try /help?");
  }

}
