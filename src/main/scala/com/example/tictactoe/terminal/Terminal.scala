package com.example.tictactoe.terminal

import kyo.*
import kyo.consoles.*

trait Terminal:
  def getUserInput: String > Consoles
  def display(frame: String): Unit > Consoles
