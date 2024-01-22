import java.io.File
import kotlin.random.Random

/**
 * MondrianCustom class generates customized Mondrian-style art and saves it as an SVG file.
 *
 * @property canvasWidth The width of the canvas.
 * @property canvasHeight The height of the canvas.
 */
class MondrianCustom(private val canvasWidth: Int, private val canvasHeight: Int) {
    private val svgHeader = """<svg width="$canvasWidth" height="$canvasHeight">"""
    private val svgFooter = "</svg>"

    /**
     * Generates Mondrian-style art recursively and saves it as an SVG file.
     */
    fun generateArt() {
        val svgContent = generateArtRecursively(0, 0, canvasWidth, canvasHeight)
        val fullSvg = "$svgHeader$svgContent$svgFooter"
        File("mondrian_custom_svg.html").writeText(fullSvg)
    }

    /**
     * Recursively generates Mondrian-style art for the given region.
     *
     * @param x The x-coordinate of the region.
     * @param y The y-coordinate of the region.
     * @param width The width of the region.
     * @param height The height of the region.
     * @return The SVG content for the region.
     */
    private fun generateArtRecursively(x: Int, y: Int, width: Int, height: Int): String {
        // Define the threshold for splitting
        val splitThreshold = 60

        // Decision on splitting
        val shouldSplit = when {
            (width > canvasWidth / 2 && height > canvasHeight / 2) -> true
            (width > splitThreshold && height > splitThreshold) ->
                Random.nextDouble() < 2.0 / 3 && Random.nextDouble() < 2.0 / 3
            else -> false
        }

        return if (shouldSplit) {
            // Split into 4 regions or occasionally into 3 regions
            val splitIntoThree = Random.nextBoolean() && Random.nextBoolean()

            val verticalLine1 = Random.nextInt(x + width / 4, x + 3 * width / 4)
            val verticalLine2 = if (splitIntoThree) Random.nextInt(x + width / 4, x + 3 * width / 4) else 0
            val horizontalLine1 = Random.nextInt(y + height / 4, y + 3 * height / 4)
            val horizontalLine2 = if (splitIntoThree) Random.nextInt(y + height / 4, y + 3 * height / 4) else 0

            val topLeft = generateArtRecursively(x, y, verticalLine1 - x, horizontalLine1 - y)
            val topRight =
                if (splitIntoThree) generateArtRecursively(verticalLine1, y, verticalLine2 - verticalLine1, horizontalLine1 - y)
                else generateArtRecursively(verticalLine1, y, x + width - verticalLine1, horizontalLine1 - y)
            val topCenter = if (splitIntoThree) generateArtRecursively(verticalLine2, y, x + width - verticalLine2, horizontalLine1 - y) else ""
            val bottomLeft =
                if (splitIntoThree) generateArtRecursively(x, horizontalLine1, verticalLine1 - x, horizontalLine2 - horizontalLine1)
                else generateArtRecursively(x, horizontalLine1, verticalLine1 - x, y + height - horizontalLine1)
            val bottomRight =
                if (splitIntoThree) generateArtRecursively(verticalLine1, horizontalLine1, verticalLine2 - verticalLine1, horizontalLine2 - horizontalLine1)
                else generateArtRecursively(verticalLine1, horizontalLine1, x + width - verticalLine1, y + height - horizontalLine1)
            val bottomCenter = if (splitIntoThree) generateArtRecursively(verticalLine2, horizontalLine1, x + width - verticalLine2, y + height - horizontalLine1) else ""

            "<rect x=\"$x\" y=\"$y\" width=\"$width\" height=\"$height\" stroke=\"black\" fill=\"white\" />" +
                    "$topLeft$topRight$topCenter$bottomLeft$bottomRight$bottomCenter"
        } else {
            // Do not split, fill with random color or pattern
            val fill = if (Random.nextDouble() < 0.75) randomColor() else randomPattern()
            "<rect x=\"$x\" y=\"$y\" width=\"$width\" height=\"$height\" stroke=\"black\" fill=\"$fill\" />"
        }
    }

    /**
     * Generates a random color for filling the region.
     *
     * @return A random color string.
     */
    private fun randomColor(): String {
        val colors = listOf("red", "blue", "yellow", "green", "orange", "purple", "pink", "gold")
        return colors.random()
    }

    /**
     * Generates a random pattern for filling the region.
     *
     * @return A random pattern string.
     */
    private fun randomPattern(): String {
        val patterns = listOf("stripes", "dots", "crosshatch")
        return patterns.random()
    }
}

/**
 * The main function creates an instance of MondrianCustom and generates Mondrian-style art.
 */
fun main() {
    val generator = MondrianCustom(800, 600)
    generator.generateArt()
}
