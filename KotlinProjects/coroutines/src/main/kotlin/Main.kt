class ListNumber<T : Number> : List<T>{
    override val size: Int
        get() = TODO("Not yet implemented")

    override fun get(index: Int): T {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<T> {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<T> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<T> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: T): Int {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: T): Int {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(element: T): Boolean {
        TODO("Not yet implemented")
    }

}

fun main() {
    val numbers = ListNumber<Long>()
    val numbers2 = ListNumber<Int>()
    val numbers3 = ListNumber<String>()


    println("Values: " + numbers)
    println("Values: " + numbers)
    println("Values: " + numbers)
}