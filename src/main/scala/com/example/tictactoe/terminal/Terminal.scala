package com.example.tictactoe.terminal

import kyo.*
import kyo.ios.*

trait Terminal:
  def getUserInput: String < IOs
  def display(frame: String): Unit < IOs
