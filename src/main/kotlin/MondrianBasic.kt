import kotlin.random.Random
import java.io.File

/**
 * MondrianBasic class generates Mondrian-style art and saves it as an SVG file.
 *
 * @property canvasWidth The width of the canvas.
 * @property canvasHeight The height of the canvas.
 */
class MondrianBasic(private val canvasWidth: Int, private val canvasHeight: Int) {
    private val svgHeader = """<svg width="$canvasWidth" height="$canvasHeight">"""
    private val svgFooter = "</svg>"

    /**
     * Generates Mondrian-style art recursively and saves it as an SVG file.
     */
    fun generateArt() {
        val svgContent = generateArtRecursively(0, 0, canvasWidth, canvasHeight)
        val fullSvg = "$svgHeader$svgContent$svgFooter"
        File("mondrian_svg.html").writeText(fullSvg)
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
            // Split into 4 regions
            val verticalLine = Random.nextInt(x + width / 3, x + 2 * width / 3)
            val horizontalLine = Random.nextInt(y + height / 3, y + 2 * height / 3)

            val topLeft = generateArtRecursively(x, y, verticalLine - x, horizontalLine - y)
            val topRight = generateArtRecursively(verticalLine, y, x + width - verticalLine, horizontalLine - y)
            val bottomLeft = generateArtRecursively(x, horizontalLine, verticalLine - x, y + height - horizontalLine)
            val bottomRight = generateArtRecursively(verticalLine, horizontalLine, x + width - verticalLine, y + height - horizontalLine)

            "<rect x=\"$x\" y=\"$y\" width=\"$width\" height=\"$height\" stroke=\"black\" fill=\"white\" />" +
                    "$topLeft$topRight$bottomLeft$bottomRight"
        } else {
            // Do not split, fill with random color
            val fill = if (Random.nextDouble() < 1.0 / 4) randomColor() else "white"
            "<rect x=\"$x\" y=\"$y\" width=\"$width\" height=\"$height\" stroke=\"black\" fill=\"$fill\" />"
        }
    }

    /**
     * Generates a random color for filling the region.
     *
     * @return A random color string.
     */
    private fun randomColor(): String {
        val colors = listOf("red", "blue", "yellow")
        return colors.random()
    }
}

/**
 * The main function creates an instance of MondrianBasic and generates Mondrian-style art.
 */
fun main() {
    val generator = MondrianBasic(800, 600)
    generator.generateArt()
}
