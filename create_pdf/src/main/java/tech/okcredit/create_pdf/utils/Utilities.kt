package tech.okcredit.create_pdf.utils

object Utilities {
    /**
     * Generate random number between two numbers
     *
     * @param lower from number
     * @param upper to number
     * @return a random number
     */
    fun generateRandomNumber(lower: Int, upper: Int): Int {
        return (Math.random() * (upper + 1 - lower)).toInt() + lower
    }
}