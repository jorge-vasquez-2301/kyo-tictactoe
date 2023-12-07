package com.example.tictactoe.mode.game

import com.example.tictactoe.domain.State
import kyo.*
import kyo.ios.*

trait GameMode:
  def process(input: String, state: State.Game): State > IOs
  def render(state: State.Game): String
