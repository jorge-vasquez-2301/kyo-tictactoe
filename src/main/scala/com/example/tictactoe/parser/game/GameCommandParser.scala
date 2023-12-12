package com.example.tictactoe.parser.game

import com.example.tictactoe.domain.*
import kyo.*
import kyo.aborts.*

trait GameCommandParser:
  def parse(input: String): GameCommand > Aborts[AppError]
