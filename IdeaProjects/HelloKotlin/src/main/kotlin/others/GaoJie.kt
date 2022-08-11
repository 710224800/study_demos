package others

fun main() {
    val fruits = listOf("cherry", "blueberry", "citrus", "apple", "apricot", "banana", "coconut")

    var evenFruits = fruits.groupingBy {
        it.first()
    }
        .fold({ key, _ -> key to mutableListOf<String>() },
            { _, accumulator, element ->
                accumulator.also { (_, list) -> if (element.length % 2 == 0) list.add(element) }
            })

    val sorted = evenFruits.values.sortedBy { it.first }
    println(evenFruits) // [(a, []), (b, [banana]), (c, [cherry, citrus])]
//sampleEnd
}