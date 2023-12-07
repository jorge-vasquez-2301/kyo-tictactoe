package com.example.tictactoe.parser.game

import atto.Atto.*
import atto.Parser
import com.example.tictactoe.domain.Board.Field
import com.example.tictactoe.domain.{ AppError, GameCommand }
import kyo.*
import kyo.aborts.*

final case class GameCommandParserLive() extends GameCommandParser:
  def parse(input: String): GameCommand > Aborts[AppError] =
    command.parse(input).done.option.getOrElse(Aborts[AppError].fail(AppError.ParseError))

  private lazy val command: Parser[GameCommand] =
    choice(menu, put)

  private lazy val menu: Parser[GameCommand] =
    (string("menu") <~ endOfInput) >| GameCommand.Menu

  private lazy val put: Parser[GameCommand] =
    (int <~ endOfInput).flatMap { value =>
      Field
        .make(value.toString)
        .fold(err[GameCommand](s"Invalid field value: $value"))(field => ok(field).map(GameCommand.Put(_)))
    }
