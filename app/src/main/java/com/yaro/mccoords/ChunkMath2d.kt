package com.yaro.mccoords

class ChunkMath2d {
    companion object {
        /**
         * Converts a block coordinate to a chunk coordinate.
         *
         * @param block The block coordinate to convert.
         * @return The corresponding chunk coordinate.
         */
        fun blockToChunk(block: Point2d): Point2d {
            return Point2d(
                block.x shr 4,
                block.z shr 4
            )
        }

        /**
         * Calculates the minimum block coordinate of a given chunk.
         *
         * @param chunk The chunk coordinate.
         * @return The minimum block coordinate of the chunk.
         */
        fun getChunkMinBlock(chunk: Point2d): Point2d {
            return Point2d(
                chunk.x shl 4,
                chunk.z shl 4
            )
        }

        /**
         * Calculates the maximum block coordinate of a given chunk.
         *
         * @param chunk The chunk coordinate.
         * @return The maximum block coordinate of the chunk.
         */
        fun getChunkMaxBlock(chunk: Point2d): Point2d {
            return Point2d(
                (chunk.x + 1 shl 4) - 1,
                (chunk.z + 1 shl 4) - 1
            )
        }
    }
}