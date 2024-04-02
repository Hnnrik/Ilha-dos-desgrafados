
abstract class Entidade {
    public String nome;
    protected int vida;
    protected int ataque;
    protected int vertice;

    Batalha batalha = new Batalha();

    public Entidade(String nome, int vida, int ataque, int vertice) {
        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;
        this.vertice = vertice;
    }

    @Override
    public String toString() {
        return "Entidade [nome=" + nome + ", vida=" + vida + ", ataque=" + ataque + "]";
    }

}
