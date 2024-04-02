import java.util.Random;

public class Criatura extends Entidade {
  Random random = new Random(System.currentTimeMillis());;

  public Criatura(String nome, int vida, int ataque, int vertice) {
    super(nome, vida, ataque, vertice);
  }

  // percorre o mapa, e em cada vertice coloca a criaturas daquele vertice para
  // andar
  // chamando a função de andaCriatura
  void andar(Mapa mapa) {
    for (Vertice v : mapa.mapa) {
      v.criaturas.get(0).andaCriatura(mapa);
    }
  }

  // remove a criatura do vertice que ela esta atualmente, e coloca ela em um
  // vertice adjacente a ele de forma aleatoria
  // o novo vertice não pode ser a praia (index 0), o vertice do tesouro ou um
  // chekpoint
  // caso no novo vertice exista outra criatura, ocorre uma batalha
  void andaCriatura(Mapa mapa) {
    mapa.mapa.get(this.vertice).criaturas.remove(0);
    do {
      this.vertice = mapa.mapa.get(vertice).adjacentes.get(random.nextInt(mapa.mapa.get(this.vertice).adjacentes.size()));
    } while (this.vertice == 0 || this.vertice == 29 || mapa.mapa.get(this.vertice).checkPoint == true);
    mapa.mapa.get(this.vertice).criaturas.add(this);

    if (mapa.mapa.get(this.vertice).criaturas.size() > 1) {
      this.batalha.batalha_criaturas(mapa, this.vertice);
    }
  }

  // diminui a vida da criatura, passando o dano q ela tomou,
  // caso sua vida zere, removemos a criatura do vertice atual, colocamos ela em
  // um novo vertice com a vida regenerada, porém menor
  void toma_dano(Mapa mapa, int dano) {
    this.vida -= dano;
    if (this.vida <= 0) {
      mapa.mapa.get(this.vertice).criaturas.remove(0);
      this.vida = 30;
      this.renascer(mapa);
    }
  }

  // selecionamos um vertice aleatorio, e adicionamos a criatura nele
  void renascer(Mapa mapa) {
    do {
      this.vertice = random.nextInt(mapa.mapa.size());
    } while (this.vertice == 0 || this.vertice == 29 || mapa.mapa.get(this.vertice).checkPoint == true);
    mapa.mapa.get(this.vertice).criaturas.add(this);
  }

  String ficha() {
    return ("+----Ficha---->\n" + "| Nome: {" + this.nome + "}\n" + "| Vida: {" + this.vida + "}\n" + "| Dano: {"
        + this.ataque + "}\n" + "+------------->\n");
  }
}