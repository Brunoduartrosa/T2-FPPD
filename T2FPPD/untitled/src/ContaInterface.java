import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContaInterface extends Remote {
    public boolean abrirConta(String nome) throws RemoteException;
    public boolean fecharConta(String nome) throws RemoteException;
    public double consultarSaldo(String nome) throws RemoteException;
    public boolean depositar(String nome, double valor) throws RemoteException;
    public boolean sacar(String nome, double valor) throws RemoteException;
}
