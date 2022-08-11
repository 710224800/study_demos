package study02
// 扩展声明为成员
open class ID{
}

class ID1: ID(){
}

open class IC{
    open fun ID.foo() {
        println("ID.foo in IC")
    }

    open fun ID1.foo() {
        println("ID1.foo in IC")
    }

    fun caller(d: ID){
        d.foo()
    }
}

class IC1: IC() {
    override fun ID.foo() {
        println("ID.foo in IC1")
    }

    override fun ID1.foo() {
        println("ID1.foo in IC1")
    }
}

fun main(){
    IC().caller(ID())
    IC1().caller(ID())
    IC().caller(ID1())
}