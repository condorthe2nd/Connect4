package connectfour

var row = 6
var column = 7

fun main() {
    val regex = Regex("""^\s*\d+\s*[xX]\s*\d+\s*$""")
    println("Connect Four")
    println("First player's name:")
    val player1 = readln()
    println("Second player's name:")
    val player2 = readln()

    createBoard(regex)
    var board = MutableList(column + 1) { MutableList(row + 1) { ' ' } }

    println("$player1 VS $player2")
    println("$row X $column board")
    printBoard(board)
    //println("$row $column")
    while (true) {
        if (move(player1, 'o', board)) break
        if (move(player2, '*', board)) break
    }
    println("Game Over!")
}

private fun move(
    player: String, piece: Char, board: MutableList<MutableList<Char>>
): Boolean {
    var a = false

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
            //  println(board)
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

