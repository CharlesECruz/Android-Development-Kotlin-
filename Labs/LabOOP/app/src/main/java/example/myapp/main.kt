package example.myapp

import example.myapp.deco.makeDecorations

fun buildAquarium() {
    val myAquarium = Aquarium(width = 25, length = 25, height = 40)
    myAquarium.printSize()
    val myTower = Aquarium.TowerTank(diameter = 25, height = 40)
    myTower.printSize()
}

fun makeFish() {
    val shark = Shark()
    val pleco = Plecostomus()
    println("Shark: ${shark.color}")
    shark.eat()
    println("Plecostomus: ${pleco.color}")
    pleco.eat()
}
enum class Direction(val degrees: Int) {
    NORTH(0), SOUTH(180), EAST(90), WEST(270)
}
fun main() {
    //buildAquarium()
    //makeFish()
    //makeDecorations()

    println(Direction.EAST.name)
    println(Direction.EAST.ordinal)
    println(Direction.EAST.degrees)
}