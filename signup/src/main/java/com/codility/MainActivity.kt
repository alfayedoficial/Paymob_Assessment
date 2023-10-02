package com.codility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

fun main() {
    // Example 1
    val blocks1 = intArrayOf(2, 6, 8, 5)
    println(solution(blocks1)) // Output: 3

    // Example 2
    val blocks2 = intArrayOf(1, 5, 5, 2, 6)
    println(solution(blocks2)) // Output: 4

    // Example 3
    val blocks3 = intArrayOf(1, 1)
    println(solution(blocks3)) // Output: 2
}
fun solution(blocks: IntArray): Int {
    val numBlocks = blocks.size
    val maxJump = IntArray(numBlocks)

    // Initialize maxJump with 1 for each block
    for (currentBlock in 0 until numBlocks) {
        maxJump[currentBlock] = 1
    }

    // Calculate the maximum jump for each block
    for (currentBlock in 1 until numBlocks) {
        for (prevBlock in 0 until currentBlock) {
            if (blocks[currentBlock] >= blocks[prevBlock]) {
                maxJump[currentBlock] = maxOf(maxJump[currentBlock], maxJump[prevBlock] + 1)
            }
        }
    }

    // Find the maximum value in maxJump
    var maxDistance = 0
    for (currentBlock in 0 until numBlocks) {
        maxDistance = maxOf(maxDistance, maxJump[currentBlock])
    }

    return maxDistance
}