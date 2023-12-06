package bullscows

import kotlin.random.Random

fun main() {
    val rng = Random.Default
    val chars = mutableListOf<String>()
    var randomNumber = ""

    println("Input the length of the secret code:")
    var input = readln()
    var randomNumberLength: Int

    try {
        randomNumberLength = input.toInt()
    } catch (e: NumberFormatException) {
        println("Error: \"${input}\" isn't a valid number.")
        return
    }

    if (randomNumberLength < 1) {
        println("Error: minimum length of the code is 1.")
        return
    }

    println("Input the number of possible symbols in the code:")
    input = readln()
    var characterSet: Int

    try {
        characterSet = input.toInt()
    } catch (e: NumberFormatException) {
        println("Error: \"${input}\" isn't a valid number.")
        return
    }

    if (characterSet > 36) {
        println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).")
        return
    } else if (characterSet < 1) {
        println("Error: minimum number of possible symbols in the code is 1.")
        return
    }


    if (randomNumberLength > characterSet) {
        println("Error: it's not possible to generate a code with a length of $randomNumberLength with $characterSet unique symbols.")
        return
    }

    for (i in 1..characterSet) {
        if (i - 1 > 9){
            chars.add((i + 86).toChar().toString())
        } else {
            chars.add((i - 1).toString())
        }
    }

    repeat(randomNumberLength) {
        val nextInt = rng.nextInt(chars.size)
        randomNumber += chars[nextInt]
        chars.remove(chars[nextInt])
    }

    val characters = when {
        characterSet < 10 -> "(0-${characterSet - 1})"
        characterSet == 10 -> "(0-9)"
        characterSet == 11 -> "(0-9, a)"
        else -> "(0-9, a-${(characterSet + 86).toChar()})"
    }

    val secret = "*".repeat(randomNumberLength)
    println("The secret is prepared: $secret $characters.")

    var guess: String

    var bulls: Int
    var cows: Int

    var turn = 1

    println("Okay, let's start a game!")

    while (true) {
        bulls = 0
        cows = 0

        println("Turn $turn:")
        guess = readln()

        for (i in randomNumber.indices) {
            for (j in guess.indices) {
                if (randomNumber[i] == guess[j]) {
                    if (i == j) {
                        bulls++
                    } else {
                        cows++
                    }
                }
            }
        }

        print("Grade: ")

        when {
            bulls == randomNumberLength -> {
                println("$bulls bulls.\nCongratulations! You guessed the secret code.")
                return
            }
            bulls > 0 && cows > 0 -> println("$bulls bull(s) and $cows cow(s).")
            bulls > 0 && cows == 0 -> println("$bulls bull(s).")
            bulls == 0 && cows > 0 -> println("$cows cow(s).")
            else -> println("None.")
        }
        turn++
    }
}
