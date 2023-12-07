package com.example.tictactoe.parser.confirm

import com.example.tictactoe.domain.{ AppError, ConfirmCommand }
import kyo.*
import kyo.aborts.*

trait ConfirmCommandParser:
  def parse(input: String): ConfirmCommand > Aborts[AppError]
