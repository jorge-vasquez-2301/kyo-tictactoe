package com.example.tictactoe.parser.menu

import com.example.tictactoe.domain.{AppError, MenuCommand}
import kyo.*
import kyo.aborts.*

final case class MenuCommandParserLive() extends MenuCommandParser:
  override def parse(input: String): MenuCommand > Aborts[AppError] =
    input match
      case "new game" => MenuCommand.NewGame
      case "resume"   => MenuCommand.Resume
      case "quit"     => MenuCommand.Quit
      case _          => Aborts[AppError].fail(AppError.ParseError)
