package com.yaro.mccoords

import kotlin.math.sqrt

data class Point2d(val x: Int = 0, val z: Int = 0)

operator fun Point2d.plus(other: Point2d): Point2d {
    return Point2d(x + other.x, z + other.z)
}

operator fun Point2d.minus(other: Point2d): Point2d {
    return Point2d(x - other.x, z - other.z)
}

operator fun Point2d.times(scalar: Int): Point2d {
    return Point2d(x * scalar, z * scalar)
}

infix fun Point2d.dot(other: Point2d): Int {
    return x * other.x + z * other.z
}

fun Point2d.magnitude2(): Int {
    return x * x + z * z
}

fun Point2d.magnitude(): Double {
    return sqrt(this.magnitude2().toDouble())
}

