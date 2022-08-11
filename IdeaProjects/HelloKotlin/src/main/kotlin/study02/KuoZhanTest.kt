package study02

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}
///////////////////////////////////////////////////////////////
class MC {
    fun foo() {
        println("成员函数")
    }
}

fun MC.foo() {
    println("扩展函数")
}
///////////////////////////////////////////////////////////////

fun Any?.toString(): String{
    if (this == null) return "null"

    return toString()
}
///////////////////////////////////////////////////////////////
// 对属性扩展
val <T> List<T>.lastIndex: Int
    get() = this.size - 1

///////////////////////////////////////////////////////////////
// 伴生对象扩展
class MyClass{
    companion object{}
}

fun MyClass.Companion.foo() {
    println("伴生对象的扩展函数")
}
val MyClass.Companion.no: Int
    get() = 10

fun main() {
    val list = mutableListOf<Any>(1, 2, 3, "4")
    list.swap(1, 3)
    println(list.toString())

    ///////////////////////////////////////////////////////////////
    // 优化成员函数
    val mc = MC()
    mc.foo()

    ///////////////////////////////////////////////////////////////
    val ml = null
    println(ml.toString())
    ///////////////////////////////////////////////////////////////

    println("lastIndex= ${list.lastIndex}")
    ///////////////////////////////////////////////////////////////

    println("MyClass.no: ${MyClass.no}")
    MyClass.foo()

}