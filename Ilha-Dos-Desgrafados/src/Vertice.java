import java.util.ArrayList;

public class Vertice {

    ArrayList<Integer> adjacentes = new ArrayList<Integer>();
    ArrayList<Criatura> criaturas = new ArrayList<Criatura>();
    public Obstaculo obstaculo;
    public Arma arma;
    public int tesouro = 0;
    public int cura;
    public boolean checkPoint;

    @Override
    public String toString() {
        return "Vertice [adjacentes=" + adjacentes + ", criaturas=" + criaturas + ", obstaculo=" + obstaculo + ", arma="
                + arma + ", tesouro=" + tesouro + ", cura=" + cura + ", checkPoint=" + checkPoint + "]";
    }

}
