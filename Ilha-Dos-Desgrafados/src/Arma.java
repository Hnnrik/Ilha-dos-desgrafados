public class Arma {
  public String nome;
  public int dano;
  public int durabilidade = 3;
  public String descricao;

  public Arma(String nome, int dano, String descricao) {
    this.nome = nome;
    this.dano = dano;
    this.descricao = descricao;
  }

  @Override
  public String toString() {
    return (nome + "\n||Ataque {" + dano + "}]\n" + "||[Durabilidade {" + durabilidade + "}]\n" + "||[Decrição {"+ descricao + "}]");
  }
}