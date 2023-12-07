package com.example.tictactoe.parser.menu

import com.example.tictactoe.domain.{ AppError, MenuCommand }
import kyo.*
import kyo.aborts.*

trait MenuCommandParser:
  def parse(input: String): MenuCommand > Aborts[AppError]
