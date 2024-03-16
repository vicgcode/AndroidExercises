package com.vicgcode.androidexercises.examples.patterns.adapter


/**
We need a class that implements interface. We have class Person and interface Visibler,
but Person don't implement Visibler.
We can create Adapter, that have instance of Person and implements Visibler.
 */

class AdapterExampler {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val firstPerson = Person("John", 25)
            firstPerson.sayHello()
            val firstAdapter = TeenagerAdapter(firstPerson)
            firstAdapter.isYoung()

            val secondPerson = Person("Julia", 13)
            secondPerson.sayHello()
            val secondAdapter = TeenagerAdapter(secondPerson)
            secondAdapter.isYoung()
        }
    }
}

class Person(val name: String, val age: Int) {
    fun sayHello() {
        println("Hello, my name is $name. I am $age years old.")
    }
}

interface Visualizer {
    fun isYoung()
}

// Adapter
class TeenagerAdapter(private val person: Person) : Visualizer {

    override fun isYoung() {
        if (person.age in 13..19) {
            println("I am ${person.name} and I am a teenager")
        } else {
            println("I am ${person.name} and I am not a teenager")
        }
    }
}
