package com.example.tictactoe.mode.confirm

import com.example.tictactoe.domain.State

trait ConfirmMode:
  def process(input: String, state: State.Confirm): State
  def render(state: State.Confirm): String
