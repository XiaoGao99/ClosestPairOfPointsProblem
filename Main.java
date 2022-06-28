import java.text.DecimalFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Driver class for Program 2
 * 1. Read the file and assign values to the points array
 * 2. Use divide and conquer algo to compute the shortest path
 * 3. print out the shortest distance on every recursion call
 * 
 * pre : 1. File must be in the format of :
 * (a). The first line indicates number of points
 * (b). In the rest of file, each line contains two numbers,
 * seprated by one whitespace
 * (c). The file must be named as 'program2data.txt'
 * 2. The x and y axis within the range of double
 * 3. Each point is different.
 * post : The min distance is computed, and output the min distance at each
 * recursion call. Thus, the last line of the output will always be
 * the final result.
 * 
 * @author Xiao Gao
 *         Date: April 28 2022
 */
public class Main {
    /**
     * Main method
     * Call runTheProgram() and prints the outputs.
     * 
     * @pre : Same as the class precondition.
     * @post : Same as the class postcondition.
     */                                                                         
    public static void main(String[] args) {
        runTheProgram();
    }

    /**
     * A method to run the program, and prints out the output
     * 
     * @pre : Same as the class precondition.
     * @post : Same as the class precondition.
     */
    private static void runTheProgram() {
        Point[] pointsX = readFile();
        Point[] pointsY = readFile();
        Arrays.sort(pointsX, new PointXComparator());
        Arrays.sort(pointsY, new PointYComparator());
        divideConquer(pointsX, pointsY, 0, pointsX.length - 1);
    }

    /**
     * A helper method to read the file, and store them in array
     * 
     * @pre : The first line of file must be a number of numbers of points
     *      In the rest of file, each line contains two number,
     *      seprate by one whitespace
     * @post : A points array is generated
     * @return an array with each pair of points
     */
    private static Point[] readFile() {
        Scanner reader = null;
        // open the file
        try {
            File file = new File("program2data.txt");
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File out found!");
            e.printStackTrace();
            System.exit(0);
        }

        // Initialize the array
        int size = Integer.parseInt(reader.nextLine());
        Point[] points = new Point[size];

        // assign values
        for (int i = 0; i < size; i++) {
            double x = reader.nextDouble();
            double y = reader.nextDouble();
            points[i] = new Point(x, y);
        }
        reader.close();
        return points;
    }

    /**
     * A brutal force method to return the min distance of the closest
     * pair of points in a points array, also prints out the min distance.
     * 
     * @pre: points array exist
     * @post: the min distance in [startIdx, endIdx] is found
     * @param points   : the points array that sorted by x
     * @param startIdx : the start index of points(inclusive)
     * @param endIdx   : the end index of points(inclusive)
     * @return the min distance in range [startIdx, endIdx]
     */
    private static double brutalForce(Point[] points, int startIdx, int endIdx) {
        double min = Double.MAX_VALUE;
        double cur = Double.MAX_VALUE;
        for (int i = startIdx; i <= endIdx; i++) {
            for (int j = i + 1; j <= endIdx; j++) {
                cur = Point.computeDistance(points[i], points[j]);
                min = Math.min(min, cur);
            }
        }
        // print out the result
        outputHelper(startIdx, endIdx, min);
        return min;
    }

    /**
     * A helper method to find the minimum distance of the
     * closest pair of points in the strip.
     * 
     * @pre : 1. strip array must be sorted based on y
     *      2. strip array only contains valid points.
     * @post : The minimum distance is found.
     * @param strip    : an array stores all the points that have 
     *                   closer distance to the mid line (the divide line) than 
     *                   distance. Which is (this.x - mid.x) < distance.
     *                   Also, must be sorted based on y.
     * @param size     : The number of elements in strip
     *                   This is needed as I predefined the size of strip array 
     *                   as the size of points array.
     * @param distance : The maximum distance the returned value can be.
     * @param start    : The start index on the recursion(inclusive)
     * @param end      : The end index on the recursion(inclusive)
     * @return the minimum distance of the closest pair of points in the strip,
     *         or param distance if distance is smaller.
     */
    private static double minInStrip(Point[] strip, int size, double distance,
            int start, int end) {
        double min = distance;

        // this is an O(n) operation,
        // since the inner loop will run at most 7 times
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size &&
                    (strip[j].getY() - strip[i].getY()) < min; j++) {
                min = Math.min(min, Point.computeDistance(strip[i], strip[j]));
            }
        }
        // print out the result
        outputHelper(start, end, min);
        return min;
    }

    /**
     * A method that uses divide and conquer algo to find the
     * min distance of the closest pair of points in points array,
     * also prints out the min distance.
     * 
     * @pre : pointsX and pointsY must be sorted
     * @post: the min distance is found
     * @param pointsX  : the points array sorted by x
     * @param pointsY  : the points array sorted by y
     * @param startIdx : the start index(inclusive)
     * @param endIdx   : the end index(inclusive)
     * @return the min distance of the closest pair of points
     */
    private static double divideConquer(Point[] pointsX,
            Point[] pointsY, int startIdx, int endIdx) {

        int size = endIdx - startIdx + 1;
        // less than 3 points, use brutal force
        if (size <= 3) {
            return brutalForce(pointsX, startIdx, endIdx);
        }

        // find the mid index and mid point
        int mid = startIdx + (endIdx - startIdx) / 2;
        Point midPoint = pointsX[mid];

        // split pointsY into left and right subarrays
        // *note* : the split is base on the mid line, which is x axis
        // since the pointsY array is sorted by y, the subarrays
        // will be also sorted by y.

        // the points on left of the mid line
        int leftSize = (size % 2 == 0) ? size / 2 : size / 2 + 1;
        Point[] pointsYLeft = new Point[leftSize];
        // the points on right of the mid line
        Point[] pointsYRight = new Point[size - leftSize];
        int left = 0, right = 0;
        for (int i = 0; i < size; i++) {
            if (((pointsY[i].getX() < midPoint.getX())
                    || ((pointsY[i].getX() == midPoint.getX())
                            && pointsY[i].getY() <= midPoint.getY()))
                    && left < pointsYLeft.length) {
                pointsYLeft[left] = pointsY[i];
                left++;
            } else {
                pointsYRight[right] = pointsY[i];
                right++;
            }
        }

        // recursively call, and keep the min distance
        double leftDistance = 
                    divideConquer(pointsX, pointsYLeft, startIdx, mid);
        double rightDistance = 
                    divideConquer(pointsX, pointsYRight, mid + 1, endIdx);
        double distance = Math.min(leftDistance, rightDistance);

        // add the points that closer to L to the strip
        Point[] strip = new Point[size];
        int stripIdx = 0;
        for (int i = 0; i < strip.length; i++) {
            if (Math.abs(pointsY[i].getX() - midPoint.getX()) < distance) {
                strip[stripIdx] = pointsY[i];
                stripIdx++;
            }
        }
        return minInStrip(strip, stripIdx, distance, startIdx, endIdx);
    }

    /**
     * A helper method to print the desired output format
     * 
     * @pre : endIdx >= startIdx 
     * @post : A fomatted output is generated
     * @param startIdx : the start index of the 'divide'
     * @param endIdx   : the end index of the 'divide'
     * @param distance : the shortest distance in the range of 
     *                   [startIdx, endIdx]
     */
    private static void outputHelper(int startIdx, int endIdx, double distance) {
        DecimalFormat df = new DecimalFormat("#.####");
        StringBuilder sb = new StringBuilder();
        sb.append("D[");
        sb.append(startIdx);
        sb.append(',');
        sb.append(endIdx);
        sb.append("]: ");
        sb.append(df.format(distance));
        System.out.println(sb.toString());
    }
}
