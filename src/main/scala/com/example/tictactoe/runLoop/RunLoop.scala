package com.example.tictactoe.runLoop

import com.example.tictactoe.domain.State
import kyo.*
import kyo.ios.*
import kyo.options.*

trait RunLoop:
  def step(state: State): State > (Options & IOs)
