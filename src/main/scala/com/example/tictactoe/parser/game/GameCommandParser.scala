package com.example.tictactoe.parser.game

import com.example.tictactoe.domain.{ AppError, GameCommand }
import kyo.*
import kyo.aborts.*

trait GameCommandParser:
  def parse(input: String): GameCommand > Aborts[AppError]
