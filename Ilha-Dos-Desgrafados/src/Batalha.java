import java.util.Random;

public class Batalha {
    private int rodada;
    Random random = new Random(System.currentTimeMillis());

    // função de batalha, enquando o jogador ou a criatura estiver com vida a
    // batalha continua.
    // jogadas pares deixam o jogador atacar e jogadas impares deixam a criatura.
    // caso o jogador não tenha arma ele luta sem, aplicando seu proprio ataque
    // caso ele tenha arma o ataque será o da arma. também diminuimos a durabilidade
    // da arma a cada ataque
    // o dano equivale a um valor aleatorio do ataque total
    void batalha(Mapa mapa, Jogador jogador, Criatura criatura) {
        rodada = 0;
        while (jogador.vida > 0 && criatura.vida > 0) {
            if (rodada % 2 == 0) {
                int dano;
                if (jogador.arma == null) {
                    dano = random.nextInt(jogador.ataque);
                } else {
                    dano = random.nextInt(jogador.arma.dano);
                }

                int auxVida = criatura.vida;
                criatura.toma_dano(mapa,dano);
                System.out.println("Você deu [" + dano + "] de dano na criatura\n");
                if(auxVida-dano<=0){
                    System.out.println("Você derrotou o ["+criatura.nome+"]");
                    return;
                }
                System.out.println("ela ainda tem [" + criatura.vida + "] de vida");
                if (jogador.arma != null) {
                    jogador.arma.durabilidade -= 1;
                    if (jogador.arma.durabilidade == 0) {
                        jogador.solta_arma(mapa);
                        System.out.println("Oh não, sua arma quebrou!\n");
                    }
                }
            } else {
                int dano = random.nextInt(criatura.ataque);
                int auxVida = jogador.vida;
                jogador.toma_dano(dano);
                System.out.println("Você tomou [" + dano + "] de dano da criatura\n");
                if(auxVida-dano<=0){
                    return;
                }
            }
            rodada++;
        }

    }

    // batalha entre criaturas,setamosa a primeira criatura como menor e maior dano
    // verificamos se existem criaturas no mesmo vertice, e calculamos seus danos
    // a de menor dano toma dano de 100, e a de maior toma dano da menor, caso
    // existem outras elas
    // tomam dano da maior
    void batalha_criaturas(Mapa mapa, int vertice) {
        Criatura menor_dano = mapa.mapa.get(vertice).criaturas.get(0);
        Criatura maior_dano = mapa.mapa.get(vertice).criaturas.get(0);

        for (Criatura dano : mapa.mapa.get(vertice).criaturas) {
            if (menor_dano.ataque > dano.ataque) {
                menor_dano = dano;
            }
            if (maior_dano.ataque < dano.ataque) {
                maior_dano = dano;
            }
        }

        menor_dano.toma_dano(mapa, 100);
        maior_dano.toma_dano(mapa, menor_dano.ataque);
        if (mapa.mapa.get(vertice).criaturas.size() > 2) {
            for (Criatura intermediarias : mapa.mapa.get(vertice).criaturas) {
                if (intermediarias.nome != maior_dano.nome) {
                    intermediarias.toma_dano(mapa, maior_dano.ataque);
                    intermediarias.andar(mapa);
                }
            }
        }
    }
}
