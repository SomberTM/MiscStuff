import java.util.*;

int INFINITY = 999999999;

class Dijkstras
{
   
  int rows, cols;
  int w, h;
  Node[][] grid;
  PriorityQueue<Node> Queue;
  Node currentSource;
  Node currentEnd;
  
  public Dijkstras(int rows, int cols)
  {
    this.rows = rows; this.cols = cols;
    this.w = width/rows; this.h = height/cols;
    grid = new Node[rows][cols];
    NewBoard();
    Process(grid[0][0], grid[rows-1][cols-1]);
  }
  
  private void NewBoard()
  {
    
    for ( int i = 0; i < rows; i++ ) {
      for ( int j = 0; j < cols; j++ ) {
        grid[i][j] = new Node(i, j);
      }
    }
    
  }
  
  private void Process(Node source, Node end)
  {
    
    currentSource = source;
    currentEnd = end;
    
    for ( int i = 0; i < rows; i++ ) {
      for ( int j = 0; j < cols; j++ ) {
        grid[i][j].score = INFINITY;
        grid[i][j].path.clear();
      }
    }
    
  }
  
  public void update()
  {
    Process(currentSource, currentEnd);
    for ( int i = 0; i < rows; i++ ) {
      for ( int j = 0; j < cols; j++ ) {
        grid[i][j].show();
      }
    }
    
  }
  
  class Node
  {
    
    int i, j;
    ArrayList<Node> path;
    int score;
    color c;
    
    public Node(int i, int j)
    {
      this.i = i; this.j = j;
      path = new ArrayList<Node>();
      score = INFINITY;
      c = color(255,255,255);
    }
    
    public void show()
    {
      stroke(0);
      fill(c);
      rect( i * w, j * h, w, h);
    }
    
    public String toString()
    {
     return "(" + i + ", " + j + ")";
    }
    
  }

}
