Dijkstras d;

void setup()
{
  size(800,600);
  d = new Dijkstras(5,5);
  print(d.grid[0][0]);
}

void draw()
{
  d.update();
}
