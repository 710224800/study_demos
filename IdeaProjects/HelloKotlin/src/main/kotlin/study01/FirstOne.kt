package study01

class Greeter(val name: String) {
    fun greet() {
        println("hello, $name")
    }
}

fun main(args: Array<String>) {
    val greeter = Greeter("lyhao")
    greeter.greet()
}

