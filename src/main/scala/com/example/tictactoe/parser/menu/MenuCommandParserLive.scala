package com.example.tictactoe.parser.menu

import com.example.tictactoe.domain.{ AppError, MenuCommand }
import kyo.*
import kyo.aborts.*
import kyo.envs.*
import kyo.layers.*

final class MenuCommandParserLive() extends MenuCommandParser:
  override def parse(input: String): MenuCommand > Aborts[AppError] =
    input match
      case "new game" => MenuCommand.NewGame
      case "resume"   => MenuCommand.Resume
      case "quit"     => MenuCommand.Quit
      case _          => Aborts[AppError].fail(AppError.ParseError)

object MenuCommandParserLive:
  val layer: Layer[Envs[MenuCommandParser], Any] = Envs[MenuCommandParser].layer(MenuCommandParserLive())
