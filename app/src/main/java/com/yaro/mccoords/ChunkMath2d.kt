package com.yaro.mccoords

class ChunkMath2d {
    companion object
    {
        fun blockToChunk(block: Point2d): Point2d {
            return Point2d(
                block.x shr 4,
                block.z shr 4
            )
        }

        fun chunkToMinBlock(chunk: Point2d): Point2d {
            return Point2d(
                chunk.x shl 4,
                chunk.z shl 4
            )
        }

        fun chunkToMaxBlock(chunk: Point2d): Point2d {
            return Point2d(
                (chunk.x + 1 shl 4) - 1,
                (chunk.z + 1 shl 4) - 1
            )
        }
    }
}