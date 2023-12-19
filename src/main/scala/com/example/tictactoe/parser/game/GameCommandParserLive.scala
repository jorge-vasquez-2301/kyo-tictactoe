package com.example.tictactoe.parser.game

import com.example.tictactoe.domain.*
import kyo.*
import kyo.aborts.*
import kyo.envs.*
import kyo.layers.*
import zio.parser.*

final class GameCommandParserLive() extends GameCommandParser:
  def parse(input: String): GameCommand < Aborts[AppError] =
    command.parseString(input) match
      case Left(_)        => Aborts[AppError].fail(AppError.ParseError)
      case Right(command) => command

  private lazy val command: Parser[String, Char, GameCommand] =
    menu <> put

  private lazy val menu: Parser[String, Char, GameCommand] =
    Parser.string("menu", GameCommand.Menu)

  private lazy val put: Parser[String, Char, GameCommand] =
    for
      digit   <- Parser.digit
      command <- Field.make(digit) match
                   case Some(field) => Parser.succeed(GameCommand.Put(field))
                   case None        => Parser.fail(s"Invalid field value: $digit")
    yield command

object GameCommandParserLive:
  val layer: Layer[Envs[GameCommandParser], Any] = Envs[GameCommandParser].layer(GameCommandParserLive())
