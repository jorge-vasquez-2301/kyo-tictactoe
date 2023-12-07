package com.example.tictactoe.view.menu

import com.example.tictactoe.domain.MenuFooterMessage

final class MenuViewLive() extends MenuView:
  val header: String =
    """
      | _____   __                             _______     ______          ______
      |/__  /  / /   ____ ___  _____  _____   /_  __(_)___/_  __/___ _____/_  __/___  ___
      |  / /  / /   / __ `/ / / / _ \/ ___/    / / / / ___// / / __ `/ ___// / / __ \/ _ \
      | / /__/ /___/ /_/ / /_/ /  __/ /       / / / / /__ / / / /_/ / /__ / / / /_/ /  __/
      |/____/_____/\__,_/\__, /\___/_/       /_/ /_/\___//_/  \__,_/\___//_/  \____/\___/
      |                 /____/
      |""".stripMargin

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
