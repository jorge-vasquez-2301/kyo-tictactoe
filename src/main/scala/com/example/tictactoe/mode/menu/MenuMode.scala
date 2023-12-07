package com.example.tictactoe.mode.menu

import com.example.tictactoe.domain.State

trait MenuMode:
  def process(input: String, state: State.Menu): State
  def render(state: State.Menu): String
