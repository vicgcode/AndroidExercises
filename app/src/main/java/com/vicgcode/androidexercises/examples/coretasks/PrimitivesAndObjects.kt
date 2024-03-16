package com.vicgcode.androidexercises.examples.coretasks

class PrimitivesAndObjects {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Create and compare Integer vals
            val int1 = Integer(1)
            val int2 = Integer(1)
            println("$int1, $int2 ${int1 == int2}") // true
            println("$int1, $int2 ${int1 === int2}") // false
            // Create and compare SMALL valueOf Integer vals
            val int3 = Integer.valueOf(1)
            val int4 = Integer.valueOf(1)
            println("$int3, $int4 ${int3 == int4}") // true
            println("$int3, $int4 ${int3 === int4}") // true
            // Create and compare BIG valueOf Integer vals
            val int5 = Integer.valueOf(128)
            val int6 = Integer.valueOf(128)
            println("$int5, $int6 ${int5 == int6}") // true
            println("$int5, $int6 ${int5 === int6}") // false
            // Create and compare Int vals
            val int7 = 1
            val int8 = 1
            println("$int7, $int8 ${int7 == int8}") // true
            println("$int7, $int8 ${int7 === int8}") // true

            println()

            val firstNormalInstance = NormalClass(3, "some text")
            val secondNormalInstance = NormalClass(3, "some text")
            val thirdNormalInstance = firstNormalInstance
            println("first instance = second instance is ${firstNormalInstance == secondNormalInstance}") // false
            println("first instance = third instance is ${firstNormalInstance == thirdNormalInstance}") // true
            println()
            firstNormalInstance.number = 4
            println("first instance number variable = ${firstNormalInstance.number}") // 4
            println("third instance number variable = ${thirdNormalInstance.number}") // 4

            val firstDataInstance = DataClass(3, "some text")
            val secondDataInstance = DataClass(3, "some text")
            val thirdDataInstance = DataClass(3, "different text")
            println("first instance = second instance is ${firstDataInstance == secondDataInstance}") // true
            println("first instance = third instance is ${firstDataInstance == thirdDataInstance}") // false

            val firstDataWithOutsideInstance = DataClassWithOutsideVal(3)
            println("first data instance = first outside instance is ${firstDataInstance.equals(firstDataWithOutsideInstance)}") // false
        }
    }
}

class NormalClass(var number: Int, val text: String) { }

data class DataClass(val number: Int, val text: String) { }

data class DataClassWithOutsideVal(val number: Int) {
    // val text не будет учтена в конструкторе при сравнении по equals и hashcode
    val text: String = "some text"
}
