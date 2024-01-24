The high-level algorithm for generating mondrian art should work like this. Given a rectangle (called “region” in this project), we decide to do exactly one of the following things.

1.	Split the region into 4 smaller regions using a horizontal line and a vertical line. Recurse on each subregion.
2.	Split the region into 2 smaller regions of equal height, positioned side by side. Recurse on each subregion.
3.	Split the region into 2 smaller regions of equal width, one positioned on top of another. Recurse on each subregion.
4.	Do no splitting. Just draw the region with black outline and fill it with a random color.

This program implements this algorithm and outputs the generated mondrian art on a HTML file using HTML SVG which defines vector-based graphics in XML format.
