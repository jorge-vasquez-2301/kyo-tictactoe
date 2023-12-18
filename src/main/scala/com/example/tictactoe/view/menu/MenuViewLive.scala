package com.example.tictactoe.view.menu

import com.example.tictactoe.domain.MenuFooterMessage

import kyo.envs.*
import kyo.layers.*

final class MenuViewLive() extends MenuView:
  val header: String =
    """
      | ___  __        ___    ___ ________          _________  ___  ________ _________  ________  ________ _________  ________  _______      
      |\  \|\  \     |\  \  /  /|\   __  \        |\___   ___\\  \|\   ____\\___   ___\\   __  \|\   ____\\___   ___\\   __  \|\  ___ \     
      |\ \  \/  /|_   \ \  \/  / | \  \|\  \       \|___ \  \_\ \  \ \  \___\|___ \  \_\ \  \|\  \ \  \___\|___ \  \_\ \  \|\  \ \   __/|    
      | \ \   ___  \   \ \    / / \ \  \\\  \           \ \  \ \ \  \ \  \       \ \  \ \ \   __  \ \  \       \ \  \ \ \  \\\  \ \  \_|/__  
      |  \ \  \\ \  \   \/  /  /   \ \  \\\  \           \ \  \ \ \  \ \  \____   \ \  \ \ \  \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \_|\ \ 
      |   \ \__\\ \__\__/  / /      \ \_______\           \ \__\ \ \__\ \_______\  \ \__\ \ \__\ \__\ \_______\  \ \__\ \ \_______\ \_______\
      |    \|__| \|__|\___/ /        \|_______|            \|__|  \|__|\|_______|   \|__|  \|__|\|__|\|_______|   \|__|  \|_______|\|_______|
      |              \|___|/                                                                                                                                                                                                                                                       
    """.stripMargin

  def content(isSuspended: Boolean): String =
    val commands =
      if isSuspended then List("new game", "resume", "quit")
      else List("new game", "quit")
    commands
      .map(cmd => s"* $cmd")
      .mkString("\n")

  def footer(message: MenuFooterMessage): String =
    message match
      case MenuFooterMessage.Empty          => ""
      case MenuFooterMessage.InvalidCommand => "Invalid command. Try again."

object MenuViewLive:
  val layer: Layer[Envs[MenuView], Any] = Envs[MenuView].layer(MenuViewLive())
