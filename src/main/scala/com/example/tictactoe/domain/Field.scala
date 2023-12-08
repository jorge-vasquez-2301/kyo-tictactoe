package com.example.tictactoe.domain

import scala.util.Try

enum Field:
  case NorthWest
  case North
  case NorthEast
  case West
  case Center
  case East
  case SouthWest
  case South
  case SouthEast

object Field:
  def make(value: Char): Option[Field] =
    Try(Field.fromOrdinal(value.toInt - 48)).toOption
