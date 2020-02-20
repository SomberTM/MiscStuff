import java.util.*;

Node[][] grid;
int cols, rows;
int w, h;
int INFINITY = 999999999;
boolean init;
PriorityQueue<Node> Queue;
Node currentSource;
Node currentEnd;

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
  
  Dijkstras(grid[0][0], grid[rows-1][cols-1]);  
}

void Dijkstras(Node source, Node end)
{
  
  currentEnd = end;
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
    
    Queue.remove(current);  
    
  }
  
  drawPath(end);
  
}

void drawPath(Node goal)
{
  
  if ( goal.path.contains(currentSource) ) {
    for ( Node n : goal.path )
    {
      n.setColor(color(0,255,0));
      if ( n == currentSource || n == currentEnd )
      {
        n.setColor(color(255,255,0));
      }
    }
  } else {
    currentSource.setColor(color(0,100,255));
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
    if ( mouseButton == LEFT ) {
      if ( mouseX >= 0 && mouseY >= 0 && mouseX <= width && mouseY <= height ) 
      {
        Node source = grid[floor(mouseX/w)][floor(mouseY/h)];
        if ( source.accessible )
        {
          Dijkstras(source, currentEnd);
        }
      }
    }
    
    if ( mouseButton == RIGHT ) {
      if ( mouseX >= 0 && mouseY >= 0 && mouseX <= width && mouseY <= height ) 
      {
        Node end = grid[floor(mouseX/w)][floor(mouseY/h)];
        if ( end.accessible )
        {
          Dijkstras(currentSource, end);
        }
      }
    }
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
     Dijkstras(currentSource, currentEnd);
   }
   
   //Absolute Fucking Mess
   if ( key == 'g' )
   {
     if ( mouseX >= 0 && mouseY >= 0 && mouseX <= width && mouseY <= height ) 
     {
       grid[floor(mouseX/w)][floor(mouseY/h)].accessible = !grid[floor(mouseX/w)][floor(mouseY/h)].accessible;
       if ( !grid[floor(mouseX/w)][floor(mouseY/h)].accessible )
       {
         grid[floor(mouseX/w)][floor(mouseY/h)].setColor(color(255,0,0));
       } else {
         grid[floor(mouseX/w)][floor(mouseY/h)].setColor(color(255,255,255));
       }
     }
   }
}
