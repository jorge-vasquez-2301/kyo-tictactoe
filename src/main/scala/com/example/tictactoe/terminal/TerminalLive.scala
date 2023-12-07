package com.example.tictactoe.terminal

import kyo.*
import kyo.consoles.*

final class TerminalLive() extends Terminal:
  import TerminalLive.*

  override val getUserInput: String > Consoles = Consoles.readln

  override def display(frame: String): Unit > Consoles =
    Consoles.print(ansiClearScreen).andThen(Consoles.println(frame))

object TerminalLive:
  final val ansiClearScreen = "\u001b[H\u001b[2J"
