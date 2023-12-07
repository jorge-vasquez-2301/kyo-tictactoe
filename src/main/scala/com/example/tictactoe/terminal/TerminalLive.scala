package com.example.tictactoe.terminal

import kyo.*
import kyo.consoles.*
import kyo.ios.*

final class TerminalLive() extends Terminal:
  import TerminalLive.*

  override val getUserInput: String > IOs = Consoles.run(Consoles.readln)

  override def display(frame: String): Unit > IOs =
    Consoles.run(Consoles.print(ansiClearScreen).map(_ => Consoles.println(frame)))

object TerminalLive:
  final val ansiClearScreen = "\u001b[H\u001b[2J"
