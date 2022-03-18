package connectfour

/* I don't think the modified version of board is getting passed to print*/
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
    var board = MutableList(column) { MutableList(row) { ' ' } }
    printBoard(player1, player2, board)
    //println("$row $column")
    while (true) {
        if (move(player1, 'o', board, player1, player2)) break
        if (move(player2, '*', board, player1, player2)) break
    }
}

private fun move(
    player: String, piece: Char, board: MutableList<MutableList<Char>>, player1: String, player2: String
): Boolean {
    var a = false
    println("$player's turn")
    while (true) {
        val input = readln()
        val number = input.toInt()
        if (input == "end") {
            a = true
        } else if (input.contains("[0-$column]")) {
            println("Incorrect column number")
            continue
        } else if (number >= column + 1) {
            print("The column number is out of range (1 -$column)")
            continue
        } else if (board[number - 1].last() == 'o' || board[number - 1].last() == '*') {
            println("The column is full")
            continue
        } else {
            for (i in 0..board.size) {
                if (board[i][board[i].lastIndex] == ' ') {
                    if (board[number - 1][i] == ' ') {
                        board[number - 1][i] = piece
                        break
                    }
                }
            }
            printBoard(player1, player2, board)
            println(board)
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

        } else if (line == "end") {
            break
        } else {
            val new = line.replace("\\s".toRegex(), "").toCharArray()
            if (new.size > 3) {
                val splitNumbers = line.replace("\\s".toRegex(), "")
                    .split("x")
                if (!regex.matches(new.joinToString(""))) {
                    print("Invalid input\n")
                    continue
                }
                if (splitNumbers[0].toInt() > 9) {
                    println("Board rows should be from 5 to 9")
                } else if (splitNumbers[1].toInt() > 9) {
                    println("Board columns should be from 5 to 9")
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
    name1: String, name2: String, board: MutableList<MutableList<Char>>
) {
    println("$name1 VS $name2")
    println("$row X $column board")
    var count = 0
    print(" ")
    repeat(column) {
        count++
        print("$count  ")
    }
    for (i in 0..row) {
        println()
        for (j in 0..column) {

            if (board[i].size < i && board[i][j] != ' ') {
                print("║${board[i][j]}* ")
            } else {
                print("║  ")
            }
        }
    }
    print("\n╚═")
    repeat(column - 1) {
        print("═╩═")
    }
    print("═╝\n")
}

