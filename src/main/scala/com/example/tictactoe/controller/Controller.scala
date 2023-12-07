package com.example.tictactoe.controller

import com.example.tictactoe.domain.State
import kyo.*
import kyo.ios.*
import kyo.options.*

trait Controller:
  def process(input: String, state: State): State > (Options & IOs)
  def render(state: State): String
