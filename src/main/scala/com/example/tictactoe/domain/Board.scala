package com.example.tictactoe.domain

import scala.util.Try

object Board:

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
    def make(value: String): Option[Field] =
      value.toIntOption.flatMap(v => Try(Field.fromOrdinal(v)).toOption)

  val wins: Set[Set[Field]] =
    val horizontalWins = Set(
      Set(0, 1, 2),
      Set(3, 4, 5),
      Set(6, 7, 8)
    )

    val verticalWins = Set(
      Set(0, 3, 6),
      Set(1, 4, 7),
      Set(2, 5, 8)
    )

    val diagonalWins = Set(
      Set(0, 4, 8),
      Set(2, 4, 6)
    )

    (horizontalWins ++ verticalWins ++ diagonalWins).map(w => w.map(i => Field.make(i.toString).get))
