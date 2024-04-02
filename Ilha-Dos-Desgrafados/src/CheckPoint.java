public class CheckPoint {
  public Jogador save;
  public boolean ativo;
  // passamos uma cópida do jogador no estado em que ele encontrou
  // o chekpoint para a função salvando-o
  // e o objeto chek do jogador é ativado
  // em seguida, destivamos o checkpoint no vertice

  void salva_jogador(Jogador jogador, Mapa mapa) {
    this.save = jogador.clone();
    jogador.check.ativo = true;
    mapa.mapa.get(jogador.vertice).checkPoint = false;
    System.out.println("\nVocê encontrou um CheckPoint. O progresso foi salvo");
  }

  // para a ressussitação, recebemos o jogador e rele vai receber o estado
  // do mesmo ao passar pelo checkpoint. depois desativamos o objeto check do
  // jogador

  void ressucita(Jogador jogador) {
    if (jogador.check.ativo == true) {

      jogador.arma = this.save.arma;
      jogador.pocao = this.save.pocao;
      jogador.vertice = this.save.vertice;
      jogador.vida = this.save.vida;

      jogador.check.ativo = false;
      System.out.println("Você morreu e ressucitou no vertice [" + jogador.vertice + "]");
    } else {
      System.out.println("Você morreu e não tem checkPoits salvos. Fim de jogo");
      System.exit(0);
    }
  }
}