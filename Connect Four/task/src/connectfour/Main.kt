package connectfour

var row = 6
var column = 7
var player1Points: Int = 0
var player2Points: Int = 0
var numberOfGames: Int = 0


fun main() {
    play()
}

private fun play() {
    //regex to check input
    val regex = Regex("""^\s*\d+\s*[xX]\s*\d+\s*$""")

    println("Connect Four")
    //get first player name
    println("First player's name:")
    val player1 = readln()
    //get second player name
    println("Second player's name:")
    val player2 = readln()
    //instantiate board
    createBoard(regex)
    val board = MutableList(column) { MutableList(row) { ' ' } }

    loop1@ while (true) {
        println(
            """Do you want to play single or multiple games?
For a single game, input 1 or press Enter
Input a number of games:"""
        )

        val entry = readln()

        if (entry.isEmpty()) {
            numberOfGames = 1
            break
        } else if (entry.toIntOrNull() == null) {
            println("Invalid input")
            continue@loop1
        } else if (entry.toInt() < 1) {
            println("Invalid input")
            continue@loop1
        } else {
            numberOfGames = entry.toInt()
            break
        }
    }
    println("$player1 VS $player2")
    println("$row X $column board")
    if (numberOfGames > 1)
        println("Total $numberOfGames games")
    else {
        println("Single game")
    }
    // printBoard(board)
    //play game
    var counter = 1
    var isSingleGame = true
    if (numberOfGames > 1)
        isSingleGame = false
    loop@ while (numberOfGames > 0) {
        //receive move input until player inputs "end"
        if (!isSingleGame) {
            println("Game #$counter")
        }

        printBoard(board)


        if (counter == 1 || counter == 3 || counter == 5) {
            while (true) {
                if (move(player1, 'o', board)) break@loop
                if (checkWin(board, player1, player2)) break
                if (move(player2, '*', board)) break
                if (checkWin(board, player1, player2)) break
            }
        } else {
            while (true) {
                if (move(player2, '*', board)) break
                if (checkWin(board, player1, player2)) break
                if (move(player1, 'o', board)) break
                if (checkWin(board, player1, player2)) break
            }
        }
        counter++
        numberOfGames--
        println("Score")
        println("$player1: $player1Points $player2: $player2Points")
        resetList(board)
    }
    println("Game Over!")
}

fun checkWin(board: MutableList<MutableList<Char>>, player1: String, player2: String): Boolean {
    //check if board is full
    if (!board.flatten().contains(' ')) {
        println("It is a draw")
        player1Points++
        player2Points++
        return true
    }
    //check horizontal
    for (i in 0 until row) {
        for (j in 0 until column - 3) {
            if (board[j][i] == 'o' && board[j + 1][i] == 'o' && board[j + 2][i] == 'o' && board[j + 3][i] == 'o') {
                println("Player $player1 won")
                player1Points += 2
                return true
            }
            if (board[j][i] == '*' && board[j + 1][i] == '*' && board[j + 2][i] == '*' && board[j + 3][i] == '*') {
                println("Player $player2 won")
                player2Points += 2
                return true
            }
        }
    }
    //check vertical
    for (i in 0 until row - 3) {
        for (j in 0 until column) {
            if (board[j][i] == 'o' && board[j][i + 1] == 'o' && board[j][i + 2] == 'o' && board[j][i + 3] == 'o') {
                println("Player $player1 won")
                player1Points += 2
                return true
            }
            if (board[j][i] == '*' && board[j][i + 1] == '*' && board[j][i + 2] == '*' && board[j][i + 3] == '*') {
                println("Player $player2 won")
                player2Points += 2
                return true
            }
        }
    }
    //check diagonal
    for (i in 0 until row - 3) {
        for (j in 0 until column - 3) {
            if (board[j][i] == 'o' && board[j + 1][i + 1] == 'o' && board[j + 2][i + 2] == 'o' && board[j + 3][i + 3] == 'o') {
                println("Player $player1 won")
                player1Points += 2
                return true
            }
            if (board[j][i] == '*' && board[j + 1][i + 1] == '*' && board[j + 2][i + 2] == '*' && board[j + 3][i + 3] == '*') {
                println("Player $player2 won")
                player2Points += 2
                return true
            }
        }
    }
    //check diagonal
    for (i in 0 until row - 3) {
        for (j in 3 until column) {
            if (board[j][i] == 'o' && board[j - 1][i + 1] == 'o' && board[j - 2][i + 2] == 'o' && board[j - 3][i + 3] == 'o') {
                println("Player $player1 won")
                player1Points += 2
                return true
            }
            if (board[j][i] == '*' && board[j - 1][i + 1] == '*' && board[j - 2][i + 2] == '*' && board[j - 3][i + 3] == '*') {
                println("Player $player2 won")
                player2Points += 2
                return true
            }
        }
    }
    return false
}

private fun move(
    player: String, piece: Char, board: MutableList<MutableList<Char>>
): Boolean {
    //variable that's false if player inputs "end"
    var a = false
    //receive move input
    outer@ while (true) {
        println("$player's turn:")
        val input = readln()
        val number = input.toIntOrNull()
        if (input == "end") {
            a = true
        } else if (number == null) {
            println("Incorrect column number")
            continue
        } else if (number < 1 || number > column) {
            println("The column number is out of range (1 - $column)")
            continue
        } else {
            //enter the actual move
            for (i in row - 1 downTo 0) {
                if (board[number - 1][0] == ' ') {
                    if (board[number - 1][i] == ' ') {
                        board[number - 1][i] = piece
                        break
                    }
                } else {
                    println("Column $number is full")
                    continue@outer
                }
            }
            printBoard(board)
        }
        break
    }
    return a
}

private fun createBoard(regex: Regex) {
    while (true) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val line = readln()
        if (line.isEmpty()) {
            //have added for clarification
            row = 6
            column = 7
        } else if (line == "end") {
            break
        } else {
            val new = line.replace("\\s".toRegex(), "").toCharArray()
            if (new.size > 3) {
                val splitNumbers = line.replace("\\s".toRegex(), "").split("x")
                if (!regex.matches(new.joinToString(""))) {
                    print("Invalid input\n")
                    continue
                }
                if (splitNumbers[0].toInt() > 9) {
                    println("Board rows should be from 5 to 9")
                    continue
                } else if (splitNumbers[1].toInt() > 9) {
                    println("Board columns should be from 5 to 9")
                    continue
                }
                continue
            }
            if (regex.matches(new.joinToString(""))) {
                row = new[0].digitToInt()
                column = new[2].digitToInt()
                if (row !in 5..9) {
                    println("Board rows should be from 5 to 9")
                    continue
                } else if (column !in 5..9) {
                    println("Board columns should be from 5 to 9")
                    continue
                }
            } else {
                print("Invalid input\n")
                continue
            }
        }
        break
    }
}

private fun printBoard(
    board: MutableList<MutableList<Char>>
) {
    var count = 0
    print(" ")
    repeat(column) {
        count++
        print("$count ")
    }
    for (i in 0 until row) {
        println()
        for (j in 0..column) {
            if (j != column && i != row) {
                print("║${board[j][i]}")
            } else {
                print("║ ")
            }
        }
    }
    print("\n╚")
    repeat(column - 1) {
        print("═╩")
    }
    print("═╝\n")
}

private fun resetList(board: MutableList<MutableList<Char>>) {
    for (i in 0 until row) {
        for (j in 0 until column) {
            board[i][j] = ' '
        }
    }
}

