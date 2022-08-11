package study02

class Person /*constructor*/ (sex: Int, var firstName: String) {
    init {
        println("init firstName is $firstName, sex is $sex")
    }
    constructor(sex: Int, firstName: String, parent: String) : this(sex, firstName) {
        println("次构造函数：parent=$parent")
    }
    fun sayHello() {
        println("hello! my name is $firstName")
        println("my lastName=$lastName age=$age")
    }

    var lastName: String = "zhang"
        get() {
            return field.uppercase()
        }
        set
    var age: Int = 10
        get() {
            return field * 3
        }
        set(value) {
            field = value / 2
        }
}

fun main() {
    val person = Person(1,"小明", "小明爹")
    person.age = 100

    person.sayHello()


}