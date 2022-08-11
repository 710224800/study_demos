package study01
public fun sum(a: Int, b: Int): Int {
    return a + b
}

public fun sum2(a: Int, b: Int): Int = a + b

var sumlambda: (Int, Int) -> Int = {x, y -> x + y}

fun main() {
    println(sum2(3, 5))
    println(sumlambda(3, 8))

    val a = 1234_5678 + 1
    println(a)
    //=========================
    val b: Byte = 1
    val i: Int = b.toInt()

    val c: Char = '0'

    println("a=${c.code}")
    //=========================
    val array1 = arrayOf("a", "b", "c")
    val array2 = Array(3, { i -> i * 2 })

    println(array1[0])
    println(array2[1])
    println("=====================")
    for (item in array1) {
        print(item)
    }
    print("\n")

    for (i in array1.indices) {
        print(i)
    }
    print("\n")

    for ((index, value) in array1.withIndex()) {
        println("the element at $index is $value")
    }
    println("=====================")
    //=========================
    val str1 = "abcdefghijklmnopqrstuvwxyz"
    println(str1[2])

    val text = """
    |多行字符串
    |菜鸟教程
    |多行字符串
    |Runoob
    """.trimMargin()
    println(text)    // 前置空格删除了
    //=========================
    val price = """
    ${'$'}9.99
    """
    println(price)  // 求值结果为 $9.99
}