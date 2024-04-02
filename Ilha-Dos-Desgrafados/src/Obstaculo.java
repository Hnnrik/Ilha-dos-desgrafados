public class Obstaculo {
  public String nome;
  public int dano;

  public Obstaculo(String nome, int dano) {
    this.nome = nome;
    this.dano = dano;
  }

  @Override
  public String toString() {
    return nome + ", dano={" + dano + "}";
  }
}