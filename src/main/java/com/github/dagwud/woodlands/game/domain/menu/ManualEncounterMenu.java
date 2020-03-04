package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;
import com.github.dagwud.woodlands.game.commands.ICommand;
import com.github.dagwud.woodlands.game.domain.ELocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManualEncounterMenu extends ActiveSpellsMenu
{
  private static final long serialVersionUID = 1L;

  public ManualEncounterMenu(String planningDuration, ELocation location)
  {
    setPrompt("What will you do? Battle commences in " + planningDuration);
    ICommand[] options = produceOptions(location.getMenu());
    setOptions(options);
  }

  private ICommand[] produceOptions(GameMenu baseMenu)
  {
    List<ICommand> options = new ArrayList<>();
    Collections.addAll(options, baseMenu.getOptions());
    options.add(ECommand.ATTACK);
    return options.toArray(new ICommand[0]);
  }
}
