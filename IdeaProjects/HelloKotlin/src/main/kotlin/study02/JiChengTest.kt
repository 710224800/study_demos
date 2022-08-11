package study02

open class A{
    open fun f() {
        println("A")
    }
    fun a() {
        println("a")
    }

    open val x: Int
        get() { return 0}

    open var y: Int = 0
        get() { return 0 }
        set(value) {field = value + 1}
}

interface B {
    fun f() {
        println("B")
    }
    fun b() {
        println("b")
    }
}

class C : A(), B {
    override fun f() {
        super<A>.f()
        super<B>.f()
    }

    override var x: Int = 0
//    override val y: Int = 0 //Val-property cannot override var-property
}

fun main() {
    val c = C()
    c.f()
    c.x = 10

    println(c.x)
}