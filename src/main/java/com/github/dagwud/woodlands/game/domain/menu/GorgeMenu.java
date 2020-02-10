package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class GorgeMenu extends ActiveSpellsMenu
{
  private static final long serialVersionUID = 1L;

  public GorgeMenu()
  {
    setPrompt("<i>This is the Gorge. Here be dragons!</i>");
    setOptions(ECommand.DEEP_WOODS);
  }
}
