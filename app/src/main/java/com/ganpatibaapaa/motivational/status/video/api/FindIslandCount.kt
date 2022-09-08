package com.ganpatibaapaa.motivational.status.video.api

import kotlin.jvm.JvmStatic
import java.util.*

class FindIslandCount {

    /**
      call function to get total islands in grid
     */
    fun calculateTotalIslands(grid: Array<CharArray>): Int {
        val h = grid.size
        if (h == 0) return 0
        val l: Int = grid[0].size
        var islands = 0
        val visited = Array(grid.size) {
            BooleanArray(
                grid[0].size
            )
        }
        for (i in 0 until h) {
            for (j in 0 until l) {
                visited[i][j] = false
            }
        }
        val queue: Queue<String> = LinkedList()
        for (i in 0 until h) {
            for (j in 0 until l) {
                if (!visited[i][j] && grid[i][j] == '1') {
                    queue.add("$i,$j")
                    BFSAlgo(queue, grid, visited)
                    islands++
                }
            }
        }
        return islands
    }

    /**
     *  BFS Algorithm Logic
     */

    fun BFSAlgo(queue: Queue<String>, grid: Array<CharArray>, visited: Array<BooleanArray>) {
        val H = grid.size
        val L: Int = grid[0].size
        while (!queue.isEmpty()) {
            val x = queue.remove()
            val row = x.split(",").toTypedArray()[0].toInt()
            val col = x.split(",").toTypedArray()[1].toInt()

            // check grid cell boundary and visited and check is 0 or 1
            if (row < 0 || col < 0 || row >= H || col >= L || visited[row][col] || grid[row][col] != '1') continue
            visited[row][col] = true
            queue.add(row.toString() + "," + (col - 1)) //go left
            queue.add(row.toString() + "," + (col + 1)) //go right
            queue.add((row - 1).toString() + "," + col) //go up
            queue.add((row + 1).toString() + "," + col) //go down
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var grid = arrayOf(
                charArrayOf('1', '1', '1', '1', '0'),
                charArrayOf('1', '1', '0', '1', '0'),
                charArrayOf('1', '1', '0', '1', '0'),
                charArrayOf('1', '1', '0', '0', '0'),
                charArrayOf('0', '0', '0', '0', '0')
            )
            val noOfIslands = FindIslandCount()
            println("No of Islands: " + noOfIslands.calculateTotalIslands(grid))
            grid = arrayOf(
                charArrayOf('1', '1', '0', '0', '0'),
                charArrayOf('1', '1', '0', '0', '0'),
                charArrayOf('0', '0', '1', '0', '0'),
                charArrayOf('0', '0', '0', '1', '1')
            )
            println("No of Islands: " + noOfIslands.calculateTotalIslands(grid))
        }
    }
}