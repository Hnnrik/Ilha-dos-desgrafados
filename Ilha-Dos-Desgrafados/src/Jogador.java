import java.util.Random;
import java.util.Scanner;

public class Jogador extends Entidade implements Cloneable{
  public Arma arma;
  public int tesouro;
  public int pocao = 0;
  public CheckPoint check = new CheckPoint();
  Random random = new Random(System.currentTimeMillis());;

  public Jogador(String nome, int vida, int ataque) {
    super(nome, vida, 10, 0);
  }

  // Nessa função primeiro verificamos se ele ainda tem tempo, se sim
  // selecionamos aleatoriamente um dos vertices adjacentes ao atual vertice do
  // jogador, e fazemos o jogador andar para ele
  // depois fazemos todas as verificações sobre o vertice
  // verificamos se o tesouro está nele, se sim recebe uma porcentagem do tesouro
  // correspondente a vida atual - atque da sua arma caso tenha uma
  // se for a praia e possuir tesouro ele vence.
  // se for um vertice que seja um chekpoint, ele chama a salva_jogador para
  // salvar esse vertice
  // se tiver uma arma ele pode pega-la, e caso ele ja possua uma, pode fazer a
  // troca e deixar sua antiga no vertice.
  // se ouver uma porção ele pode pega-la
  // se ouver um obstaculo ele toma dano
  // se tiver uma criatura ele pode batalhar ou fugir
  void anda(Mapa mapa) {
    Scanner scanAnda = new Scanner(System.in);

    if (mapa.tempo == 0) {
      System.out.println("O seu tempo acabou. Fim de jogo.");
      scanAnda.close();
      System.exit(0);
    }
    
    this.vertice = mapa.mapa.get(this.vertice).adjacentes.get(random.nextInt(mapa.mapa.get(vertice).adjacentes.size()));
    System.out.println("\nVocê está no vertice:" + this.vertice + "");
    System.out.println("Sua vida atual é de [" + this.vida + "]");
    if (mapa.mapa.get(vertice).tesouro != 0) {
      if (this.arma != null) {
        this.tesouro = (this.vida - this.arma.dano) * ((mapa.mapa.get(vertice).tesouro) / 100);
      } else {
        this.tesouro = (this.vida) * ((mapa.mapa.get(vertice).tesouro) / 100);
      }
      System.out.println("Parabéns, você encontrou o tesouro, agora volte para o barco sem sofrer dano.");
      mapa.mapa.get(29).tesouro = 0;
    }
    if (vertice == 0 && this.tesouro != 0) {
      System.out.println("Parabéns, venceu");
      System.exit(0);
    }
    if (mapa.mapa.get(vertice).checkPoint == true) {
      this.check.salva_jogador(this, mapa);
    }
    if (mapa.mapa.get(vertice).arma != null) {
      System.out.println("Existe uma arma nesta área.");
      if (this.arma != null) {
        System.out.println("Sua arma atual é " + this.arma.toString());
        System.out.println("A nova arma é " + mapa.mapa.get(vertice).arma.toString());
      }
      System.out.println("Deseja pega-la? S-1 N-0");

      int armaRes = scanAnda.nextInt();

      if (armaRes == 1) {
        Arma a = mapa.mapa.get(vertice).arma;
        mapa.mapa.get(vertice).arma = this.arma;
        this.arma = a;
        System.out.println("você recebeu a " + this.arma.toString());
      }
    }
    if (mapa.mapa.get(vertice).cura != 0) {
      System.out.println("Existe uma poção nesta área.");
      if (this.pocao != 0) {
        System.out.println("Sua porção atual cura " + this.pocao);
        System.out.println("A nova cura " + mapa.mapa.get(vertice).cura);
      }

      System.out.println("Deseja pega-la? S-1 N-0");

      int pocaoRes = scanAnda.nextInt();
      if (pocaoRes == 1) {
        System.out.println("você recebeu a nova poção");
        this.pocao =  mapa.mapa.get(vertice).cura;
      }
    }

    if (mapa.mapa.get(vertice).obstaculo != null) {
      System.out.println("Oh não, aqui tem um " + mapa.mapa.get(vertice).obstaculo.toString());
      System.out.println("Você tomará [" + mapa.mapa.get(vertice).obstaculo.dano + "] de dano");
      this.toma_dano(mapa.mapa.get(vertice).obstaculo.dano);
    }

    if (mapa.mapa.get(vertice).criaturas.size() != 0) {
      System.out.println("Oh não, aqui tem uma criatura\n" + mapa.mapa.get(vertice).criaturas.get(0).ficha());
      System.out.println("Deseja lutar ou correr? 1-Lutar 0-correr");

      int lutaRes = scanAnda.nextInt();

      if (lutaRes == 1) {
        this.batalha.batalha(mapa, this, mapa.mapa.get(vertice).criaturas.get(0));
      } else {
        int dano = random.nextInt(mapa.mapa.get(this.vertice).criaturas.get(0).ataque);
        System.out.println("Você decidiu fugir, mas ainda tomará [" + dano + "] de dano");
        this.toma_dano(dano);
        mapa.tempo--;
        this.anda(mapa);
      }
    }
    mapa.tempo--;

  }

  void toma_dano(int dano) {
    this.vida -= dano;
    if (tesouro != 0) {
      this.tesouro -= this.tesouro * (dano / 100);
    }
    if (this.vida <= 0) {
      this.check.ressucita(this);
    }
  }

  // passamos a arma para o vertice que ele se encontra, se houver durabilidade
  // ainda
  // setamos para null a arma do jogador
  void solta_arma(Mapa mapa) {
    if (this.arma.durabilidade > 0) {
      mapa.mapa.get(this.vertice).arma = this.arma;
    }
    this.arma = null;
  }

  void usa_pocao() {
    if (this.pocao != 0) {
      this.vida += this.pocao;
      if (this.vida > 100) {
        this.vida = 100;
      }
      this.pocao=0;
    } else {
      System.out.println("Você não possui uma poção.");
    }

  }
  @Override
  public Jogador clone() {
      try {
          return (Jogador) super.clone();
      } catch (CloneNotSupportedException e) {
          // Lidar com a exceção, se necessário
          return null;
      }
  }
  public void ficha() {
    if (this.arma == null) {
      System.out.println("+----ficha---->\n" + "| Vida: {" + this.vida + "}\n" + "| Dano Base: {" + this.ataque + "}\n"
          + "| Arma: {nenhuma}\n" + "| Vertice: {" + this.vertice + "}\n" + "| Poção de cura: {" + this.pocao + "}\n"
          + "| Tesouro: {" + this.tesouro + "}\n" + "+------------->\n");
    } else {
      System.out.println("+----ficha---->\n" + "| Vida: {" + this.vida + "}\n" + "| Dano Base: {" + this.ataque + "}\n"
          + "| Arma: {" + this.arma.toString() + "}\n" + "| Vertice: {" + this.vertice + "}\n" + "Poção de cura: {"
          + this.pocao + "}\n" + "| Tesouro: {" + this.tesouro + "}\n" + "+------------->\n");
    }
  }
}