package com.example.myfirstapplication
import java.lang.Exception

class InvalidProductDataException(message: String) : Exception(message)

data class Purchase(
    val name: String?,
    val quantity: Int,
    val pricePerUnit: Double
) {
    val totalPrice: Double
        get() = quantity * pricePerUnit
}

fun main() {
    var purchaseCount: Int? = null
    while (purchaseCount == null || purchaseCount <= 0) {
        println("Enter the number of purchases:")
        purchaseCount = readLine()?.toIntOrNull()
        if (purchaseCount == null || purchaseCount <= 0) {
            println("Invalid number! Try again.")
        }
    }

    val purchases = mutableListOf<Purchase>()
    var i = 1

    while (i <= purchaseCount) {
        try {
            println("\nPurchase $i")

            print("Product name (can be null): ")
            val name = readLine()?.takeIf { it.isNotBlank() }

            print("Quantity: ")
            val quantity = readLine()?.toIntOrNull()
                ?: throw InvalidProductDataException("Quantity must be a number")

            print("Price per unit: ")
            val price = readLine()?.toDoubleOrNull()
                ?: throw InvalidProductDataException("Price must be a number")

            if (quantity < 0 || price < 0) {
                throw InvalidProductDataException("Quantity or price cannot be negative")
            }

            purchases.add(Purchase(name, quantity, price))
            i++

        } catch (e: InvalidProductDataException) {
            println("Error: ${e.message}")
        } catch (e: Exception) {
            println("Unknown error: ${e.message}")
        }
    }

    println("\nReport:")

    if (purchases.isEmpty()) {
        println("No purchases have been added.")
        return
    }

    val mostExpensive = purchases.maxByOrNull { it.totalPrice }
    val totalSum = purchases.sumOf { it.totalPrice }
    val countOverThousand = purchases.count { it.totalPrice > 1000 }

    println("The most expensive purchase: ${mostExpensive?.name ?: "No name"} (${mostExpensive?.totalPrice} UAH)")
    println("Total amount: %.2f UAH".format(totalSum))
    println("Number of products over 1000 UAH: $countOverThousand")
}
