import java.io.Serializable;

public class Conta implements Serializable {
    private String nome;
    private double saldo;

    public Conta(String nome) {
        this.nome = nome;
        this.saldo = 0.0;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public synchronized boolean depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            return true;
        }
        return false;
    }

    public synchronized boolean sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }
}
