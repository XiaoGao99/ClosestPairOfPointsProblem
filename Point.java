import java.lang.Math;
import java.util.Comparator;

/** 
 * Class to represent a point with x and y axis.
 * Also presents with two compartor classes, 
 * one sorted on x value, the other sorted on y value
 * 
 * Functionality includes compute distance between two points.
 * @author Xiao Gao
 * Date : April 28 2022
 */
public class Point {
  private double x; // x axis of point
  private double y; // y axis of point

  /**
   * Default constructor
   * 
   * @pre: none
   * @post: initial instance variables to 0.0
   * 
   */
  public Point() {
    this.x = 0.0;
    this.y = 0.0;
  }

  /**
   * Constructor takes arguments
   * 
   * @pre: none
   * @post: all instance variables are initialized
   * @param x : the x value
   * @param y : the y value
   */
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Getter method for x
   * 
   * @pre: x has been initialized
   * @post: return the value of x
   * @return: the value of x
   */
  public double getX() {
    return this.x;
  }

  /**
   * Setter method for x
   * 
   * @pre : none 
   * @post : x is set to a new value
   * @param x : the value to be set for x
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Getter method for y
   * 
   * @pre: y has been initialized
   * @post: return the value of y
   * @return: the value of y
   */
  public double getY() {
    return this.y;
  }

  /**
   * Setter method for y
   * @pre : none
   * @post : y is set to a new value
   * @param y : the value to be set for y
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * A utility method to calculate the distance between two points
   * by using Euclidean distance formula
   * 
   * @pre : none
   * @post : The Euclidean distance between two points is computed
   * @param pointA : the first point
   * @param pointB : the second point
   * @return the distance between pointA and pointB
   */
  public static double computeDistance(Point pointA, Point pointB) {
    return Math.sqrt(
        Math.pow(pointA.x - pointB.x, 2) +
        Math.pow(pointA.y - pointB.y, 2));
  }
}


/**
 * Comparator for Point class,
 * sorted by the value of x
 */
class PointXComparator implements Comparator<Point> {
  /**
   * Compare by x value
   * @pre : none
   * @post : the comparision is done
   * @param pointA : the first point to compare
   * @param pointB : the second point to compare
   * @return 0 if pointA = pointB; less than 0 if pointA is less than pointB;
   *         greater than 0 if pointA is greater than pointB
   */
  @Override
  public int compare(Point pointA, Point pointB) {
    return Double.compare(pointA.getX(), pointB.getX());
  }
}

/**
 * Comparator for Point class,
 * soted by the value of y
 */
class PointYComparator implements Comparator<Point> {
    /**
   * Compare by y value
   * @pre : none
   * @post : The comparision is done.
   * @param pointA : the first point to compare
   * @param pointB : the second point to compare
   * @return 0 if pointA = pointB; less than 0 if pointA is less than pointB;
   *         greater than 0 if pointA is greater than pointB
   */
  @Override
  public int compare(Point pointA, Point pointB) {
    return Double.compare(pointA.getY(), pointB.getY());
  }
}
