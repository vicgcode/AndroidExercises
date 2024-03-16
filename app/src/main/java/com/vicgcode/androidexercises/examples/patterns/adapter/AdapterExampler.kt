package com.vicgcode.androidexercises.examples.patterns.adapter


/**
We need a class that implements interface. We have class Person and interface Visibler,
but Person don't implement Visibler.
We can create Adapter, that have instance of Person and implements Visibler.
 */
fun main() {
    val person = Person("John", 25)
    person.sayHello()

    val eye = Teenager(person)
    eye.isYoung()
}

class Person(val name: String, val age: Int) {
    fun sayHello() {
        println("Hello, my name is $name. I am $age years old.")
    }
}

interface Visibler {
    fun isYoung()
}

// Adapter
class Teenager(private val person: Person) : Visibler {

    override fun isYoung() {
        person.sayHello()
        if (person.age in 13..19) {
            println("I am a teenager")
        } else {
            println("I am not a teenager")
        }
    }
}
