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
      Set(1, 2, 3),
      Set(4, 5, 6),
      Set(7, 8, 9)
    )

    val verticalWins = Set(
      Set(1, 4, 7),
      Set(2, 5, 8),
      Set(3, 6, 9)
    )

    val diagonalWins = Set(
      Set(1, 5, 9),
      Set(3, 5, 7)
    )

    (horizontalWins ++ verticalWins ++ diagonalWins).map(w => w.map(i => Field.make(i.toString).get))
