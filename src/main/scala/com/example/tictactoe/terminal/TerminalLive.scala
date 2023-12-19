package com.example.tictactoe.terminal

import kyo.*
import kyo.consoles.*
import kyo.ios.*
import kyo.envs.*
import kyo.layers.*

final class TerminalLive() extends Terminal:
  import TerminalLive.*

  override val getUserInput: String < IOs = Consoles.readln

  override def display(frame: String): Unit < IOs =
    Consoles.print(ansiClearScreen).andThen(Consoles.println(frame))

object TerminalLive:
  final val ansiClearScreen = "\u001b[H\u001b[2J"

  val layer: Layer[Envs[Terminal], Any] = Envs[Terminal].layer(TerminalLive())
