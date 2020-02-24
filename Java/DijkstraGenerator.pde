Dijkstras pather;

void setup()
{
  size(800,600);
  pather = new Dijkstras(40,40);
  pather.turnOnPrinter();
}

void draw()
{
  pather.update();
}
