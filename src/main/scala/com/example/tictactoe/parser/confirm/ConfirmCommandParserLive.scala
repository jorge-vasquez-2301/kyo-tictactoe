package com.example.tictactoe.parser.confirm

import com.example.tictactoe.domain.{ AppError, ConfirmCommand }
import kyo.*
import kyo.aborts.*

final case class ConfirmCommandParserLive() extends ConfirmCommandParser:
  def parse(input: String): ConfirmCommand > Aborts[AppError] =
    input match
      case "yes" => ConfirmCommand.Yes
      case "no"  => ConfirmCommand.No
      case _     => Aborts[AppError].fail(AppError.ParseError)
