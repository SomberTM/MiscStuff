import java.util.*;

Node[][] grid;
int cols, rows;
int w, h;
int INFINITY = 999999999;
boolean init;
PriorityQueue<Node> Queue;
Node currentSource;

void setup()
{
  init = false;
  size(800,600);
  cols = rows = 40;
  w = width / cols;
  h = height / rows;
  grid = new Node[rows][cols];
  Queue = new PriorityQueue<Node>(cols * rows);
  
  for ( int j = 0; j < cols; j++ ) {
    for ( int i = 0; i < rows; i++ ) {
      grid[i][j] = new Node(i, j);
    }
  }
  
  Dijkstras(grid[floor(random(rows-1))][floor(random(cols-1))]);  
  
  /*
  Queue.add(new Node(1,1));
  Node n = Queue.peek();
  print(n.x, n.y);
  */
  
}

void Dijkstras(Node source)
{
  
  currentSource = source;
  for ( int j = 0; j < cols; j++ ) {
    for ( int i = 0; i < rows; i++ ) {
      grid[i][j].score = INFINITY;
      grid[i][j].path.clear();
      if ( grid[i][j].accessible ) 
      {
        grid[i][j].setColor(color(255,255,255));
      }
    }
  }
  
  if ( !init )
  {
    makeObstacles();
    init = !init;
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
    
    //print(current.i, current.j);
    //current.setColor(color(0,255,255));
    Queue.remove(current);  
    
  }
  
  //print(grid[20][20].path);
  drawPath(grid[39][39]);
  
}

void drawPath(Node goal)
{
  for ( Node n : goal.path )
  {
    n.setColor(color(0,255,0));
  }
}

void makeObstacles()
{
  for ( int i = 0; i < rows; i++) {
     for ( int j = 0; j < cols; j++ ) {
        if ( random(0,5) <= 1 ) {
          grid[i][j].setColor(color(255,0,0));
          grid[i][j].accessible = false;
        }
     }    
  }
}

void draw()
{
  
  for ( int j = 0; j < rows; j++ ) {
    for ( int i = 0; i < cols; i++ ) {
      grid[i][j].show();
    }
  }
  
  if ( mousePressed )
  {
    Node source = grid[floor(mouseX/w)][floor(mouseY/h)];
    Dijkstras(source);
  }
  
}



class Node implements Comparable
{
  
   ArrayList<Node> path;
   int score;
   int i, j;
   color c;
   boolean accessible;
  
   public Node(int i, int j)
   {
     this.i = i; this.j = j;
     this.score = INFINITY;
     this.path = new ArrayList<Node>();
     accessible = true; 
     if ( accessible )
     {
       c = color(255,255,255);
     } else {
       c = color(255,0,0);
     }
   }
   
   public void show()
   {
     stroke(0);
     fill(c);
     rect(i * w, j * h, w, h); 
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

void keyPressed()
{
   if ( key == 'a' )
   {
     for ( int i = 0; i < rows; i++ ) {
       for ( int j = 0; j < cols; j++ ) {
         grid[i][j].accessible = true;
         grid[i][j].setColor(color(255,255,255));
       }
     }
     makeObstacles();  
     Dijkstras(currentSource);
   }
}
