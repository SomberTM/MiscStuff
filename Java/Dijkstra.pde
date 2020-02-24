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
  boolean isPrinting;
  
  public Dijkstras(int rows, int cols)
  {
    this.rows = rows; this.cols = cols;
    this.w = width/rows; this.h = height/cols;
    grid = new Node[rows][cols];
    Queue = new PriorityQueue<Node>(rows * cols);
    isPrinting = false;
    NewBoard();
    obstacles();
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
  
  private void obstacles()
  {
    for ( int i = 0; i < rows; i++) {
     for ( int j = 0; j < cols; j++ ) {
        if ( random(0,100) <= 20 ) {
          grid[i][j].accessible = false;
        }
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
        if ( grid[i][j].accessible ) grid[i][j].setColor(color(255,255,255));
      }
    }
    
    source.score = 0;
    source.path.add(source);
    
    Queue.add(source);
    
    while ( Queue.peek() != null )
    {
      Node current = Queue.peek();
      
      if ( current.i > 0 )
      {
         current.check(grid[current.i-1][current.j]); 
      }
      if ( current.j > 0 )
      {
         current.check(grid[current.i][current.j-1]);
      }
      if ( current.i < rows-1 )
      {
          current.check(grid[current.i+1][current.j]);
      }
      if ( current.j < cols-1 )
      {
          current.check(grid[current.i][current.j+1]);
      }
      
      Queue.remove(current);  
    }
    
    pather(end);
    
  }
  
  private void turnOnPrinter()
  {
    isPrinting = true;
  }
  
  private void pather(Node goal)
  {
    if ( goal.path.contains(currentSource) )
    {
      if ( isPrinting )
      {
        println("{" + " [" + currentSource.i + ", " + currentSource.j + "] -> [" + currentEnd.i + ", " + currentEnd.j + "] } PATH:" ,goal.path);
        println(" ");
      }
      currentSource.setColor(color(255,255,0));
      currentEnd.setColor(color(255,255,0));
      for ( Node n : goal.path )
      {
        n.setColor(color(0,255,0)); 
      }
    } else {
      currentSource.setColor(color(255,0,255));
      currentEnd.setColor(color(255,0,255));
    }
    
  }
  
  private void mouseHandler()
  {
    if ( mousePressed ) 
    {  
      if ( mouseInScreen() && mouseButton == LEFT ) 
      {
        Node newSource = grid[floor(mouseX/w)][floor(mouseY/h)];
        currentSource = newSource;
      } else if ( mouseInScreen() && mouseButton == RIGHT ) {
        Node newEnd = grid[floor(mouseX/w)][floor(mouseY/h)];
        currentEnd = newEnd;
      }
    }
  }
  
  public void update()
  {
    mouseHandler();
    Process(currentSource, currentEnd);
    for ( int i = 0; i < rows; i++ ) {
      for ( int j = 0; j < cols; j++ ) {
        grid[i][j].show();
      }
    }    
  }
  
  class Node implements Comparable
  {
    
    int i, j;
    ArrayList<Node> path;
    int score;
    color c;
    boolean accessible;
    
    public Node(int i, int j)
    {
      this.i = i; this.j = j;
      path = new ArrayList<Node>();
      score = INFINITY;
      c = color(255,255,255);
      accessible = true;
    }
    
    public void show()
    {
      if ( !accessible )
      {
        this.c = color(255,0,0);
      }
      stroke(0);
      fill(c);
      rect( i * w, j * h, w, h);
    }
    
    public void setColor(color c)
    {
      this.c = c;
    }
       
    public void check(Node neighbor)
    {
      if ( accessible ) {
        if ( neighbor.score > ( this.score + 1) )
        {
          neighbor.score = this.score + 1;
          Queue.add(neighbor);
          
          ArrayList<Node> copy = new ArrayList<Node>();
          for ( Node n : this.path )
          {
            copy.add(n);
          }
          neighbor.path = copy;
          
          neighbor.path.add(neighbor);
        }
      }
    }    
    
    public int compareTo(Object obj)
    {     
     Node other = (Node) obj;
     return (this.score - other.score);
    }
    
    public String toString()
    {
     return "(" + i + ", " + j + ")";
    }
  }

}

private boolean mouseInScreen()
{
  return (mouseX < width && mouseX > 0 && mouseY < height && mouseY > 0 );
}
