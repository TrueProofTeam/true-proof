package com.trueproof.core.logic;

public class Interpolation {
    /**
     * Performs linear interpolation between two points (x1, y1), and (x2, y2), returning a y-value
     * for the given value of x.
     * @param x1 The x-coordinate of the first interpolation point
     * @param y1 The y-coordinate of the first interpolation point
     * @param x2 The x-coordinate of the second interpolation point
     * @param y2 The y-coordinate of the second interpolation point
     * @param x The x-value at which we wish to estimate the y-value
     * @return The interpolated y-value at the given x-value
     */
    static double linear(double x1, double y1, double x2, double y2, double x) {
        final double dx = x2 - x1;
        final double dy = y2 - y1;

        return y1 + (x - x1) * dy / dx;
    }

    /**
     * Performs a two-dimensional interpolation given the values of a function f(x, y) on three
     * corners of a grid. The interpolation is found using values on three points:
     *      p00 = (x0, y0), value = z00
     *      p01 = (x0, y1), value = z01
     *      p10 = (x1, y0), value = z10
     *
     * This is the method of interpolation used in the example literature from the Code of Federal
     * Regulations. Its accuracy assumes that the function being interpolated is roughly linear in
     * both dimensions.
     * @param x0 The x-value of the left side of the interpolation rectangle
     * @param x1 The x-value of the right side of the interpolation rectangle
     * @param y0 The y-value of the bottom side of the interpolation rectangle
     * @param y1 The y-value of the top side of the interpolation rectangle
     * @param z00 The z-value at the bottom-left corner of the interpolation rectangle (x0, y0)
     * @param z01 The z-value at the top-left corner of the interpolation rectangle (x0, y1)
     * @param z10 The z-value at the bottom-right corner of the interpolation rectangle (x1, y0)
     * @param x The x-value of the desired interpolation point
     * @param y The y-value of the desired interpolation point
     * @return The interpolated value at (x, y)
     */
    static double simple2D(double x0,
                           double x1,
                           double y0,
                           double y1,
                           double z00,
                           double z01,
                           double z10,
                           double x,
                           double y) {
        // Perform linear interpolation along the bottom border of the rectangle
        double zxy0 = linear(x0, z00, x1, z10, x);

        // Perform linear interpolation along the left border of the rectangle
        double zx0y = linear(y0, z00, y1, z01, y);

        // Sums the respective differences from both linear interpolations to the bottom-left value
        return zxy0 + zx0y - z00;
    }

    /**
     * Performs bilinear interpolation given the values of a function f(x, y) on four corners of a
     * grid. The interpolation is given values on four points that form a rectangle:
     *      p00 = (x0, y0), value = z00
     *      p01 = (x0, y1), value = z01
     *      p10 = (x1, y0), value = z10
     *      p11 = (x1, y1), value = z11
     *
     * This method should be technically more accurate than the simple 2D interpolation method, but
     * is unlikely to differ by more than 0.001 for any realistic set of inputs.
     * @param x0 The x-value of the left side of the interpolation rectangle
     * @param x1 The x-value of the right side of the interpolation rectangle
     * @param y0 The y-value of the bottom side of the interpolation rectangle
     * @param y1 The y-value of the top side of the interpolation rectangle
     * @param z00 The z-value at the bottom-left corner of the interpolation rectangle (x0, y0)
     * @param z01 The z-value at the top-left corner of the interpolation rectangle (x0, y1)
     * @param z10 The z-value at the bottom-right corner of the interpolation rectangle (x1, y0)
     * @param z11 The z-value at the top-right corner of the interpolation rectangle (x1, y1)
     * @param x The x-value of the desired interpolation point
     * @param y The y-value of the desired interpolation point
     * @return The interpolated value at (x, y)
     */
    static double bilinear(double x0,
                    double x1,
                    double y0,
                    double y1,
                    double z00,
                    double z01,
                    double z10,
                    double z11,
                    double x,
                    double y) {
        // Perform linear interpolation along the bottom border of the rectangle
        double zxy0 = linear(x0, z00, x1, z10, x);
        // Perform linear interpolation along the top border of the rectangle
        double zxy1 = linear(x0, z01, x1, z11, x);
        // Perform linear interpolation between the two points given
        return linear(y0, zxy0, y1, zxy1, y);
    }
}
