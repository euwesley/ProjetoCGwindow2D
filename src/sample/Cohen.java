package sample;

import java.util.BitSet;

/**
 * Created by Wesley Anderson on 31/08/2015.
 *
 */
public class Cohen {
   /* private int acima = 0b1000,abaixo = 0b0100,esquerda = 0b0001,direita = 0b0010, dentro = 0b0000;
    double xmin,ymin,xmax,ymax;

// Compute the bit code for a point (x, y) using the clip rectangle
// bounded diagonally by (xmin, ymin), and (xmax, ymax)

// ASSUME THAT xmax, xmin, ymax and ymin are global constants.

    public Cohen(double xmin, double ymin, double xmax, double ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    int[][] ComputeOutCode(double x, double y)
    {
        int code[][] = new int[0][];

        code[0][0]=dentro;
        code[0][1]=dentro;          // initialised as being inside of clip window

        if (x < xmin)           // to the left of clip window
            code[0][0] = esquerda;
        else if (x > xmax)      // to the right of clip window
            code[0][0] = direita;
        if (y < ymin)           // below the clip window
            code[0][1]= abaixo;
        else if (y > ymax)      // above the clip window
            code[0][1] = acima;

        return code;
    }

    // Cohen–Sutherland clipping algorithm clips a line from
// P0 = (x0, y0) to P1 = (x1, y1) against a rectangle with
// diagonal from (xmin, ymin) to (xmax, ymax).
    void CohenSutherlandLineClipAndDraw(double x0, double y0, double x1, double y1)
    {
        // compute outcodes for P0, P1, and whatever point lies outside the clip rectangle
        int[][] outcode0 = ComputeOutCode(x0, y0);
        int[][] outcode1 = ComputeOutCode(x1, y1);
        boolean accept = false;

        while (true) {
            if (!(outcode0 | outcode1)) { // Bitwise OR is 0. Trivially accept and get out of loop
                accept = true;
                break;
            } else if (outcode0 & outcode1) { // Bitwise AND is not 0. Trivially reject and get out of loop
                break;
            } else {
                // failed both tests, so calculate the line segment to clip
                // from an outside point to an intersection with clip edge
                double x, y;

                // At least one endpoint is outside the clip rectangle; pick it.
                int outcodeOut = outcode0 ? outcode0 : outcode1;

                // Now find the intersection point;
                // use formulas y = y0 + slope * (x - x0), x = x0 + (1 / slope) * (y - y0)
                if (outcodeOut & acima) {           // point is above the clip rectangle
                    x = x0 + (x1 - x0) * (ymax - y0) / (y1 - y0);
                    y = ymax;
                } else if (outcodeOut & abaixo) { // point is below the clip rectangle
                    x = x0 + (x1 - x0) * (ymin - y0) / (y1 - y0);
                    y = ymin;
                } else if (outcodeOut & direita) {  // point is to the right of clip rectangle
                    y = y0 + (y1 - y0) * (xmax - x0) / (x1 - x0);
                    x = xmax;
                } else if (outcodeOut & esquerda) {   // point is to the left of clip rectangle
                    y = y0 + (y1 - y0) * (xmin - x0) / (x1 - x0);
                    x = xmin;
                }

                // Now we move outside point to intersection point to clip
                // and get ready for next pass.
                if (outcodeOut == outcode0) {
                    x0 = x;
                    y0 = y;
                    outcode0 = ComputeOutCode(x0, y0);
                } else {
                    x1 = x;
                    y1 = y;
                    outcode1 = ComputeOutCode(x1, y1);
                }
            }
        }
        if (accept) {
            // Following functions are left for implementation by user based on
            // their platform (OpenGL/graphics.h etc.)
            DrawRectangle(xmin, ymin, xmax, ymax);
            LineSegment(x0, y0, x1, y1);
        }
    }*/
}
